package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class Rooms(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("countPlaces") var countPlaces: Short? = null,
        @SerializedName("countPCs") var countPCs: Short? = null,
        @SerializedName("isBlocked") var isBlocked: Short? = null,
        @SerializedName("type") var type: TypeRoom? = null
) {
}