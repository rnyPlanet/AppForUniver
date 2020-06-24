package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class Subject(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("fullName") var fullName: String? = null,
        @SerializedName("shortName") var shortName: String? = null
) {
}