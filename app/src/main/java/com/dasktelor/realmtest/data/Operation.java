package com.dasktelor.realmtest.data;

import io.realm.RealmObject;

public class Operation extends RealmObject {
    private double amount;
    private String sender;
    private boolean isIncome;
    private boolean date;
    private long id;
    public Operation() {
        this(0, "");
    }
    public Operation(Operation operation) {
        this(operation.getAmount(), operation.getSender());
    }
    public Operation(double amount, String sender) {
        this.amount = amount;
        this.sender = sender;
        id = -1;
    }

    public double getAmount() {
        return amount;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getSender() {
        return sender;
    }

}
