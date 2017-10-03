package com.example.tsult.messmenegment.AddBazarPkg;

/**
 * Created by tsult on 10/2/2017.
 */

public class BazaarerDetails {
    private String mName;
    private int mId;
    private String mPhone;
    private String mEmail;
    private boolean check;

    public BazaarerDetails(String mName, int mId, String mPhone, String mEmail, boolean check) {
        this.mName = mName;
        this.mId = mId;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
        this.check = check;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
