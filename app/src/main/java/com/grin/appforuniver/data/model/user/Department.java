package com.grin.appforuniver.data.model.user;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Department {

    @SerializedName("id")
    private Integer mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("shortName")
    private String mShortName;
    @SerializedName("emeil")
    private String mEmeil;
    @SerializedName("telefon")
    private String mTelefon;

    Department(Integer id, String name, String shortName, String emeil, String telefon) {
        this.mId = id;
        this.mName = name;
        this.mShortName = shortName;
        this.mEmeil = emeil;
        this.mTelefon = telefon;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getShortName() {
        return mShortName;
    }

    public void setShortName(String mShortName) {
        this.mShortName = mShortName;
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
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mShortName='" + mShortName + '\'' +
                ", mEmeil='" + mEmeil + '\'' +
                ", mTelefon='" + mTelefon + '\'' +
                '}';
    }
}
