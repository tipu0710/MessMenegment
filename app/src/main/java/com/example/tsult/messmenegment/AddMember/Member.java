package com.example.tsult.messmenegment.AddMember;

/**
 * Created by tsult on 14-Jun-17.
 */

public class Member {
    private String mName;
    private String mPhone;
    private String nEmail;
    private int mId;
    private String identifier;
    private boolean isTrue;

    public Member() {
    }

    public Member(int mId, String mName, String mPhone, String nEmail, String identifier, boolean isTrue) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.nEmail = nEmail;
        this.mId = mId;
        this.identifier = identifier;
        this.isTrue = isTrue;
    }

    public Member(String mName, String mPhone, String nEmail, String identifier) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.nEmail = nEmail;
        this.identifier = identifier;
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

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getnEmail() {
        return nEmail;
    }

    public void setnEmail(String nEmail) {
        this.nEmail = nEmail;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }
}
