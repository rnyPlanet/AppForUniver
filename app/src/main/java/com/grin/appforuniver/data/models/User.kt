package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("username") var username: String? = null,
        @SerializedName("patronymic") var patronymic: String? = null,
        @SerializedName("firstName") var firstName: String? = null,
        @SerializedName("lastName") var lastName: String? = null,
        @SerializedName("email") var email: String? = null,
        @SerializedName("password") var password: String? = null,
        @SerializedName("roles") var roles: List<Role>? = null,
        @SerializedName("status") var status: Status? = null,
        @SerializedName("photo") var photo: Photo? = null,
        @SerializedName("telephone_1") var telefon1: String? = null,
        @SerializedName("telephone_2") var telefon2: String? = null
) {

    val shortFormPatronymic: String
        get() = patronymic?.let { it[0].toString() + "." } ?: ""

    val shortFormFirstName: String
        get() = firstName?.let { it[0].toString() + "." } ?: ""

    val fullFIO: String
        get() = "$lastName $firstName $patronymic"

    val shortFIO: String
        get() = "$lastName $shortFormFirstName $shortFormPatronymic"

    val fullFI: String
        get() = "$lastName $firstName"

    val shortFI: String
        get() = "$lastName $shortFormFirstName"
}