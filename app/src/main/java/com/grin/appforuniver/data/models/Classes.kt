package com.grin.appforuniver.data.models

import com.google.gson.annotations.SerializedName
import com.grin.appforuniver.utils.Constants.*

data class Classes(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("subject") var subject: Subject? = null,
        @SerializedName("type") var type: TypeClasses? = null,
        @SerializedName("place") var place: Place? = null,
        @SerializedName("positionInDay") var positionInDay: LessonTime? = null,
        @SerializedName("additionalRequirements") var additionalRequirements: String? = null,
        @SerializedName("week") var week: Week? = null,
        @SerializedName("group") var assignedGroup: Groups? = null,
        @SerializedName("professor") var professor: Professors? = null,
        @SerializedName("room") var room: Rooms? = null,
        @SerializedName("subgroup") var subgroup: Subgroup? = null
) {
    fun compareToWeek(week: Week): Boolean {
        return this.week == week
    }

    fun compareToSubgroup(subgroup: Subgroup): Boolean {
        return this.subgroup == subgroup
    }

    fun compareToSubgroupAndWeek(subgroup: Subgroup, week: Week): Boolean {
        return this.subgroup == subgroup && this.week == week
    }
}