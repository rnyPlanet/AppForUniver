package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class Posada(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("shortPostProfessor") val shortPostProfessor: String? = null,
        @SerializedName("fullPostProfessor") val fullPostProfessor: String? = null
) {
}