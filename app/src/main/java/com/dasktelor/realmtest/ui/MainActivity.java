package com.dasktelor.realmtest.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.dasktelor.realmtest.R;
import com.dasktelor.realmtest.data.Operation;
import com.dasktelor.realmtest.databinding.ActivityMainBinding;
import com.dasktelor.realmtest.databinding.DialogAddBinding;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        binding.recyclerView.setAdapter(viewModel.getAdapter(this, operation -> {
            Log.d("MainActivity", "click");
        }));

        binding.fabAdd.setOnClickListener(view -> {
            DialogAddBinding dialogAddBinding = DialogAddBinding.inflate(getLayoutInflater());

            new AlertDialog.Builder(this).
                    setView(dialogAddBinding.getRoot()).
                    setTitle("Operation").
                    setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            viewModel.addOperation(new Operation(1234, dialogAddBinding.amountEditText.getText().toString()));
                        }
                    }).
                    create().
                    show();
        });


        registerForContextMenu(binding.recyclerView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.operation_context, menu);
    }
    public void onDeleteContextMenu(MenuItem item){
        viewModel.deleteOperation(viewModel.getContextClickOperation());
        Log.d("MainActivity", "click delete");
    }
    public void onDuplicateContextMenu(MenuItem item){
        viewModel.addOperation(viewModel.getContextClickOperation());
    }
}