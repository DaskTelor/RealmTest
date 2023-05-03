package com.dasktelor.realmtest.db;

import androidx.annotation.NonNull;

import com.dasktelor.realmtest.data.Operation;

import java.util.Comparator;

import io.realm.ImportFlag;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class RealmDatabase {
    private final RealmConfiguration mRealmConfiguration;
    private final Realm mRealm;

    public RealmDatabase(RealmConfiguration realmConfiguration){
        mRealmConfiguration = realmConfiguration;
        mRealm = Realm.getInstance(mRealmConfiguration);
    }
    public void insert(Operation operation){
        operation.setId(this.getOperationNexId());
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insert(operation);
            }
        });
    }
    public RealmResults<Operation> getOperations(String sortField){
        return mRealm.where(Operation.class).sort(sortField).findAll();
    }

    public void deleteById(long id){
        Operation operation = mRealm.where(Operation.class).equalTo("id", id).findFirst();
        if (operation == null)
            return;
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                operation.deleteFromRealm();
            }
        });
    }
    public void clear(Class<? extends RealmModel> table){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.delete(table);
            }
        });
    }
    public long getOperationNexId(){
        Number number = mRealm.where(Operation.class).findAll().max("id");
        if (number == null)
            return 0;
        return number.longValue() + 1;
    }

}
