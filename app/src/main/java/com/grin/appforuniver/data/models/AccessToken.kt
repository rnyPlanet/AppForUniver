package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class AccessToken(
        @SerializedName("access_token") var accessToken: String? = null,
        @SerializedName("token_type") var tokenType: String? = null,
        @SerializedName("refresh_token") var refreshToken: String? = null,
        @SerializedName("expires_in") var expiresIn: Int? = null,
        @SerializedName("scope") var scope: String? = null,
        @SerializedName("jti") var jti: String? = null
) {
}