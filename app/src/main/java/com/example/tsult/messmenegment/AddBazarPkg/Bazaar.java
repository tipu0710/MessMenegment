package com.example.tsult.messmenegment.AddBazarPkg;

/**
 * Created by tsult on 22-Jun-17.
 */

public class Bazaar {
    private int mId;
    private String mName;
    private String date;
    private int cost;
    private byte[] memo;
    private int bId;
    private String identifier;

    public Bazaar(int bId, int mId, String mName, String date, int cost, byte[] memo, String identifier) {
        this.mId = mId;
        this.mName = mName;
        this.date = date;
        this.cost = cost;
        this.memo = memo;
        this.bId = bId;
        this.identifier = identifier;
    }

    public Bazaar(int mId, String mName, String date, int cost, byte[] memo,  String identifier) {
        this.mId = mId;
        this.mName = mName;
        this.date = date;
        this.cost = cost;
        this.memo = memo;
        this.identifier = identifier;
    }

    public Bazaar() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public int getmId() {
        return mId;
    }
    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public byte[] getMemo() {
        return memo;
    }

    public void setMemo(byte[] memo) {
        this.memo = memo;
    }
}
