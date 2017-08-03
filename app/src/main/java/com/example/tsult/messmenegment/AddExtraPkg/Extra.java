package com.example.tsult.messmenegment.AddExtraPkg;

/**
 * Created by tsult on 17-Jul-17.
 */

public class Extra {
    private int eId;
    private String description;
    private int amount;
    private String identifier;

    public Extra(int eId, String description, int amount, String identifier) {
        this.eId = eId;
        this.description = description;
        this.amount = amount;
        this.identifier = identifier;
    }

    public Extra(String description, int amount, String identifier) {
        this.description = description;
        this.amount = amount;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int geteId() {
        return eId;
    }

    public void seteId(int eId) {
        this.eId = eId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
