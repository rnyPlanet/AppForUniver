package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class Role(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") val name: String? = null
) {
}