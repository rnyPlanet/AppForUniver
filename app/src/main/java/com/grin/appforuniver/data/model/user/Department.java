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

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public Integer getPkeyTypeDepartment() {
        return mPkeyTypeDepartment;
    }

    public void setPkeyTypeDepartment(Integer pkeyTypeDepartment) {
        this.mPkeyTypeDepartment = pkeyTypeDepartment;
    }

    public Integer getPkeyBoss() {
        return mPkeyBoss;
    }

    public void setPkeyBoss(Integer pkeyBoss) {
        this.mPkeyBoss = pkeyBoss;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getEmeil() {
        return mEmeil;
    }

    public void setEmeil(String emeil) {
        this.mEmeil = emeil;
    }

    public String getTelefon() {
        return mTelefon;
    }

    public void setTelefon(String telefon) {
        this.mTelefon = telefon;
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
