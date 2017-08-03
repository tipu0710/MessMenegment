package com.example.tsult.messmenegment.AddMealPkg;

/**
 * Created by tsult on 07-Jul-17.
 */

public class Meal {
    private int day;
    private int mId;
    private String mName;
    private String date;
    private int meal;
    private String identifier;

    public Meal(int day, int mId, String mName, String date, int meal, String identifier) {
        this.day = day;
        this.mId = mId;
        this.mName = mName;
        this.date = date;
        this.meal = meal;
        this.identifier = identifier;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public int getMeal() {
        return meal;
    }

    public void setMeal(int meal) {
        this.meal = meal;
    }
}
