package com.grin.appforuniver.data.model.user;

import com.google.gson.annotations.SerializedName;
import com.grin.appforuniver.data.model.user.Role;

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

    private Integer id;

    private String username;

    private String patronymic;
    private String firstName;
    private String lastName;

    private String email;
    private String password;

    @SerializedName("roles")
    private List<Role> roles;

    private Status status;

    @SerializedName("department")
    private Department department;

    @SerializedName("photo")
    private Photo photo;

    @SerializedName("posada")
    private Posada posada;

    private String telefon1;
    private String telefon2;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", department=" + department +
                ", photo=" + photo +
                ", posada=" + posada +
                ", status ='" + status + '\'' +
                ", prizvishe ='" + patronymic + '\'' +
                ", name='" + firstName + '\'' +
                ", otchestvo='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telefon1='" + telefon1 + '\'' +
                ", telefon2='" + telefon2 + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
