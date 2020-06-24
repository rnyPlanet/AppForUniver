package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class Groups(
        @SerializedName("id")  val id: Int? = null,
        @SerializedName("name")  val name: String? = null,
        @SerializedName("excludedDay")  val excludedDay: String? = null,
        @SerializedName("size")  val size: Short? = null
) {
}