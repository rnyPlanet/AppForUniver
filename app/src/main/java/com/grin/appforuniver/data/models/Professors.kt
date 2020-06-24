package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class Professors(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("posada") var posada: Posada? = null,
        @SerializedName("department") var department: Department? = null,
        @SerializedName("user") var user: User? = null
) {
}