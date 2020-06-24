package com.grin.appforuniver.data.models.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
        @SerializedName("username") var username: String? = null,
        @SerializedName("patronymic") var uatronumic: String? = null,
        @SerializedName("firstName") var mFirstName: String? = null,
        @SerializedName("lastName") var mLastName: String? = null,
        @SerializedName("email") var mEmail: String? = null,
        @SerializedName("password") var uassword: String? = null
) {
}