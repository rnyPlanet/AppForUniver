package com.grin.appforuniver.data.model.user;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
class Department {

    /*
    department": {
        "id": 2,
        "pkeyTypeDepartment": null,
        "pkeyBoss": null,
        "name": "Деканат факультету компютерних наук",
        "emeil": null,
        "telefon": null
    }
    */

    @SerializedName("id")
    private Integer mId;
    @SerializedName("pkeyTypeDepartment")
    private Integer mPkeyTypeDepartment;
    @SerializedName("pkeyBoss")
    private Integer mPkeyBoss;
    @SerializedName("name")
    private String mName;
    @SerializedName("emeil")
    private String mEmeil;
    @SerializedName("telefon")
    private String mTelefon;

    Department(Integer id, Integer pkeyTypeDepartment, Integer pkeyBoss, String name, String emeil, String telefon) {
        this.mId = id;
        this.mPkeyTypeDepartment = pkeyTypeDepartment;
        this.mPkeyBoss = pkeyBoss;
        this.mName = name;
        this.mEmeil = emeil;
        this.mTelefon = telefon;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public Integer getmPkeyTypeDepartment() {
        return mPkeyTypeDepartment;
    }

    public void setmPkeyTypeDepartment(Integer mPkeyTypeDepartment) {
        this.mPkeyTypeDepartment = mPkeyTypeDepartment;
    }

    public Integer getmPkeyBoss() {
        return mPkeyBoss;
    }

    public void setmPkeyBoss(Integer mPkeyBoss) {
        this.mPkeyBoss = mPkeyBoss;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmeil() {
        return mEmeil;
    }

    public void setmEmeil(String mEmeil) {
        this.mEmeil = mEmeil;
    }

    public String getmTelefon() {
        return mTelefon;
    }

    public void setmTelefon(String mTelefon) {
        this.mTelefon = mTelefon;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + mId +
                ", pkeyTypeDepartment=" + mPkeyTypeDepartment +
                ", pkeyBoss=" + mPkeyBoss +
                ", name='" + mName + '\'' +
                ", emeil='" + mEmeil + '\'' +
                ", telefon='" + mTelefon + '\'' +
                '}';
    }
}
