package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class Department(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") val name: String? = null,
        @SerializedName("shortName") val shortName: String? = null,
        @SerializedName("emeil") val emeil: String? = null,
        @SerializedName("telefon") val telefon: String? = null
) {
}