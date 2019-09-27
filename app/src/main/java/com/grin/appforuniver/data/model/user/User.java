package com.grin.appforuniver.data.model.user;

import com.google.gson.annotations.SerializedName;
import com.grin.appforuniver.data.model.consultation.Consultation;

import java.util.Collection;
import java.util.List;

public class User {
    /*
     {
        "id": 1,
        "username": "test",
        "patronymic": "Vik",
        "firstName": "Stas",
        "lastName": "Grin",
        "email": "test@",
        "password": "$2a$04$wGeMpt/3dXK410PUZfUKDe2d8f4weqjU/SrRpq/kHjZvG7E.dpH7O",
        "roles": [
            {
                "id": 1,
                "name": "ROLE_ADMIN"
            },
            {
                "id": 3,
                "name": "ROLE_USER"
            }
        ],
        "status": "ACTIVE",
        "department": {
            "id": 2,
            "pkeyTypeDepartment": null,
            "pkeyBoss": null,
            "name": "Деканат факультету компютерних наук",
            "emeil": null,
            "telefon": null
        },
        "photo": {
            "id": 1,
            "photo": null
        },
        "posada": {
            "id": 2,
            "postVykl": "в.о. професора, доктор наук"
        },
        "telefon1": "fsa",
        "telefon2": "asdf"
    }
    */

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

    @SerializedName("department")
    private Department mDepartment;

    @SerializedName("photo")
    private Photo mPhoto;

    @SerializedName("posada")
    private Posada mPosada;

    @SerializedName("telefon1")
    private String mTelefon1;
    @SerializedName("telefon2")
    private String mTelefon2;


    @SerializedName("subscribe")
    private Collection<Consultation> subscribe;

    @SerializedName("created")
    private Collection<Consultation> created;


    public User () {}

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
        this.mDepartment = department;
        this.mPhoto = photo;
        this.mPosada = posada;
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

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPatronymic() {
        return mPatronymic;
    }

    public void setPatronymic(String patronymic) {
        this.mPatronymic = patronymic;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public List<Role> getRoles() {
        return mRoles;
    }

    public void setRoles(List<Role> roles) {
        this.mRoles = roles;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        this.mStatus = status;
    }

    public Department getDepartment() { return mDepartment; }

    public void setDepartment(Department department) { this.mDepartment = department; }

    public Photo getPhoto() { return mPhoto; }

    public void setPhoto(Photo photo) { this.mPhoto = photo; }

    public Posada getPosada() {
        return mPosada;
    }

    public void setPosada(Posada posada) {
        this.mPosada = posada;
    }

    public String getTelefon1() {
        return mTelefon1;
    }

    public void setTelefon1(String telefon1) {
        this.mTelefon1 = telefon1;
    }

    public String getTelefon2() {
        return mTelefon2;
    }

    public void setTelefon2(String telefon2) {
        this.mTelefon2 = telefon2;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + mId +
                ", department=" + mDepartment +
                ", photo=" + mPhoto +
                ", posada=" + mPosada +
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
