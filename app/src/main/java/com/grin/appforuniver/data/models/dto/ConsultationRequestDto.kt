package com.grin.appforuniver.data.models.dto

import com.google.gson.annotations.SerializedName

data class ConsultationRequestDto(
        @SerializedName("idCreatedUser") var idCreatedUser: Int? = null,
        @SerializedName("idRoom") var idRoom: Int? = null,
        @SerializedName("dateOfPassage") var dateOfPassage: String? = null,
        @SerializedName("description") var description: String? = null
) {
}