package com.grin.appforuniver.data.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("username")
    private String mUsername;

    @SerializedName("patronymic")
    private String mPatronymic;
    @SerializedName("firstName")
    private String mFirstName;
    @SerializedName("lastName")
    private String mLastName;

    @SerializedName("email")
    private String mEmail;
    @SerializedName("password")
    private String mPassword;

    @SerializedName("roles")
    private List<Role> mRoles;

    @SerializedName("status")
    private Status mStatus;

    @SerializedName("photo")
    private Photo mPhoto;

    @SerializedName("telefon1")
    private String mTelefon1;
    @SerializedName("telefon2")
    private String mTelefon2;

    public User() {
    }

    public User(Integer id, String username, String patronymic, String firstName, String lastName, String email,
                String password, List<Role> roles, Status status, Department department, Photo photo, Posada posada,
                String telefon1, String telefon2) {
        this.mId = id;
        this.mUsername = username;
        this.mPatronymic = patronymic;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mEmail = email;
        this.mPassword = password;
        this.mRoles = roles;
        this.mStatus = status;
        this.mPhoto = photo;
        this.mTelefon1 = telefon1;
        this.mTelefon2 = telefon2;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPatronymic() {
        return mPatronymic;
    }

    public String getShortFormPatronymic() {
        return mPatronymic.charAt(0) + ".";
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getShortFormFirstName() {
        return mFirstName.charAt(0) + ".";
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public List<Role> getRoles() {
        return mRoles;
    }

    public Status getStatus() {
        return mStatus;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public String getTelefon1() {
        return mTelefon1;
    }

    public String getTelefon2() {
        return mTelefon2;
    }

    public String getFullFIO() {
        return mLastName + " " + mFirstName + " " + mPatronymic;
    }

    public String getShortFIO() {
        return mLastName + " " + getShortFormFirstName() + " " + getShortFormPatronymic();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + mId +
                ", photo=" + mPhoto +
                ", status ='" + mStatus + '\'' +
                ", prizvishe ='" + mPatronymic + '\'' +
                ", name='" + mFirstName + '\'' +
                ", otchestvo='" + mLastName + '\'' +
                ", username='" + mUsername + '\'' +
                ", password='" + mPassword + '\'' +
                ", email='" + mEmail + '\'' +
                ", telefon1='" + mTelefon1 + '\'' +
                ", telefon2='" + mTelefon2 + '\'' +
                ", roles='" + mRoles + '\'' +
                '}';
    }
}
