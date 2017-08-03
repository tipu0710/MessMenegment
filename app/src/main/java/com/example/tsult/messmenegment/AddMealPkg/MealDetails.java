package com.example.tsult.messmenegment.AddMealPkg;

/**
 * Created by tsult on 04-Jul-17.
 */

public class MealDetails {
    private String mName;
    private int mealNumber;
    private int mId;
    private String identifier;

    public MealDetails(String mName, int mealNumber, int mId, String identifier) {
        this.mName = mName;
        this.mealNumber = mealNumber;
        this.mId = mId;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getMealNumber() {
        return mealNumber;
    }

    public void setMealNumber(int mealNumber) {
        this.mealNumber = mealNumber;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
