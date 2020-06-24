package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class TypeClasses(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("type") var type: String? = null
) {
}