package com.dasktelor.realmtest.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.dasktelor.realmtest.data.Operation;
import com.dasktelor.realmtest.databinding.OperationItemBinding;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class OperationRealmAdapter extends RealmRecyclerViewAdapter<Operation, OperationRealmAdapter.OperationViewHolder> {
    private final OrderedRealmCollection<Operation> mData;
    private final OperationAdapterListener mListener;
    private final LifecycleOwner mOwner;
    private Operation mContextClickOperation;

    public OperationRealmAdapter(OrderedRealmCollection<Operation> data, OperationAdapterListener listener, LifecycleOwner owner) {
        super(data, true);

        mData = data;
        mListener = listener;
        mOwner = owner;
    }

    @NonNull
    @Override
    public OperationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        OperationViewHolder holder = new OperationViewHolder(OperationItemBinding.inflate(inflater));

        holder.getBinding().getRoot().setOnClickListener(view -> {
            mListener.clickOnItem(mData.get(holder.getAdapterPosition()));
        });

        holder.getBinding().getRoot().setOnCreateContextMenuListener((contextMenu, view, contextMenuInfo) -> {
            mContextClickOperation = mData.get(holder.getAdapterPosition());
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OperationViewHolder holder, int position) {
        holder.getBinding().setLifecycleOwner(mOwner);
        holder.getBinding().setOperation(mData.get(position));
    }
    public Operation getContextClickOperation(){
        return mContextClickOperation;
    }
    public static class OperationViewHolder extends RecyclerView.ViewHolder{
        private final OperationItemBinding mBinding;
        public OperationViewHolder(@NonNull OperationItemBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }
        public OperationItemBinding getBinding(){
            return mBinding;
        }
    }
}
