package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName

data class LessonTime(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("position") var positionInDay: Int? = null,
        @SerializedName("startTime") var startTime: String? = null,
        @SerializedName("endTime") var endTime: String? = null
) {
}