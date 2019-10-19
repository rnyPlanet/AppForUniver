package com.grin.appforuniver.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class RegisterRequestDto {

    @SerializedName("username")
    private String mUsername;
    @SerializedName("patronymic")
    private String mPatronumic;
    @SerializedName("firstName")
    private String mFirstName;
    @SerializedName("lastName")
    private String mLastName;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("password")
    private String mPassword;

    public RegisterRequestDto(String mUsername, String mPatronumic, String mFirstName, String mLastName, String mEmail, String mPassword) {
        this.mUsername = mUsername;
        this.mPatronumic = mPatronumic;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPatronumic() {
        return mPatronumic;
    }

    public void setPatronumic(String mPatronumic) {
        this.mPatronumic = mPatronumic;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
