package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class Photo(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("filename") val photoUrl: String? = null
) {
}