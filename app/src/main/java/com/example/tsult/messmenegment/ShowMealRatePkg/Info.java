package com.example.tsult.messmenegment.ShowMealRatePkg;

/**
 * Created by Tipu on 5/10/2017.
 */

public class Info {
    String identifier;
    boolean isSaved;

    public Info() {
    }

    public Info(String identifier, boolean isSaved) {
        this.identifier = identifier;
        this.isSaved = isSaved;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
