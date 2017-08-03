package com.example.tsult.messmenegment.AddDepositPkg;

/**
 * Created by tsult on 16-Jul-17.
 */

public class Deposit {
    private int depositId;
    private int mId;
    private int money;
    private String date;
    private String identifier;

    public Deposit(int mId, int money, String date, String identifier) {
        this.mId = mId;
        this.money = money;
        this.date = date;
        this.identifier = identifier;
    }

    public Deposit(int depositId, int mId, int money, String date, String identifier) {
        this.depositId = depositId;
        this.mId = mId;
        this.money = money;
        this.date = date;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getDepositId() {
        return depositId;
    }

    public void setDepositId(int depositId) {
        this.depositId = depositId;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
