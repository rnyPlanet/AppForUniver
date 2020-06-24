package com.grin.appforuniver.data.models

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class Consultation(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("createdUser") val createdUser: User? = null,
        @SerializedName("room") val room: Rooms? = null,
        @SerializedName("dateOfPassage") val dateOfPassage: Date? = null,
        @SerializedName("description") val description: String? = null,

        val usersCollection: List<User>? = null
) {
    fun getDateOfEvent(): String? {
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        @SuppressLint("SimpleDateFormat") val output = SimpleDateFormat("dd.MM.yyyy")
        output.timeZone = TimeZone.getTimeZone("GMT")
        var date: Date? = null
        try {
            date = sdf.parse(dateOfPassage.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date?.let { output.format(date) } ?: ""
    }

    fun getDateAndTimeOfEvent(): String? {
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        @SuppressLint("SimpleDateFormat") val output = SimpleDateFormat("dd.MM.yyyy HH:mm")
        output.timeZone = TimeZone.getTimeZone("GMT")
        var date: Date? = null
        try {
            date = sdf.parse(dateOfPassage.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date?.let { output.format(date) } ?: ""

    }

    // Return only time
    fun getTimeOfEvent(): String? {
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        @SuppressLint("SimpleDateFormat") val output = SimpleDateFormat("HH:mm")
        output.timeZone = TimeZone.getTimeZone("GMT")
        var date: Date? = null
        try {
            date = sdf.parse(dateOfPassage.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date?.let { output.format(date) } ?: ""
    }
}