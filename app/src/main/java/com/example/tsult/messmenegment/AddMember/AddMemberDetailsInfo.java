package com.example.tsult.messmenegment.AddMember;

/**
 * Created by Tipu on 9/12/2017.
 */

public class AddMemberDetailsInfo {
    private String mName;
    private String mPhone;
    private int contactId;
    private boolean isChecked;

    public AddMemberDetailsInfo() {
    }

    public AddMemberDetailsInfo(String mName, String mPhone, boolean isChecked) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.isChecked = isChecked;
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

    public int getContactId() {
        return contactId;
    }

    public boolean isCheckedContact() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
