package com.grin.appforuniver.data.model.user;


import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Posada {

    @SerializedName("id")
    private int mId;
    @SerializedName("shortPostProfessor")
    private String mShortPostProfessor;
    @SerializedName("fullPostProfessor")
    private String mFullPostProfessor;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getShortPostProfessor() {
        return mShortPostProfessor;
    }

    public void setShortPostProfessor(String mShortPostProfessor) {
        this.mShortPostProfessor = mShortPostProfessor;
    }

    public String getFullPostProfessor() {
        return mFullPostProfessor;
    }

    public void setFullPostProfessor(String mFullPostProfessor) {
        this.mFullPostProfessor = mFullPostProfessor;
    }

    @Override
    public String toString() {
        return "Posada{" +
                "mId=" + mId +
                ", mShortPostProfessor='" + mShortPostProfessor + '\'' +
                ", mFullPostProfessor='" + mFullPostProfessor + '\'' +
                '}';
    }
}
