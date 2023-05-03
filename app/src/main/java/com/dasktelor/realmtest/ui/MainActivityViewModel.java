package com.dasktelor.realmtest.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;

import com.dasktelor.realmtest.data.Operation;
import com.dasktelor.realmtest.db.RealmDatabase;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivityViewModel extends AndroidViewModel {
    private final RealmDatabase mDatabase;
    private OperationRealmAdapter mAdapter;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        mDatabase = new RealmDatabase(
                new  RealmConfiguration.Builder().
                name(Realm.DEFAULT_REALM_NAME).
                schemaVersion(0).
                allowWritesOnUiThread(true).
                deleteRealmIfMigrationNeeded().
                build());

        //mDatabase.clear(Operation.class);
    }
    public OperationRealmAdapter getAdapter(LifecycleOwner owner, OperationAdapterListener listener) {
        if (mAdapter != null)
            return mAdapter;

        mAdapter = new OperationRealmAdapter(mDatabase.getOperations("sender"), listener, owner);
        return mAdapter;
    }
    public void addOperation(Operation operation){
        mDatabase.insert(new Operation(operation));
    }
    public void deleteOperation(Operation operation){
        mDatabase.deleteById(operation.getId());
    }
    public Operation getContextClickOperation(){
        return mAdapter.getContextClickOperation();
    }
}
