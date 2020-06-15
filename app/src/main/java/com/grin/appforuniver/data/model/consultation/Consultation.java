
package com.grin.appforuniver.data.model.consultation;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.model.user.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Consultation {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("createdUser")
    private User mCreatedUser;

    @SerializedName("room")
    private Rooms mRoom;

    @SerializedName("dateOfPassage")
    private Date dateOfPassage;

    @SerializedName("description")
    private String description;

    private List<User> usersCollection;

    public Integer getId() {
        return mId;
    }

    public User getCreatedUser() {
        return mCreatedUser;
    }

    public Rooms getRoom() {
        return mRoom;
    }

    public String getDateOfPassage() {

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat("dd.MM.yyyy");
        output.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d = null;
        try {
            d = sdf.parse(dateOfPassage.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert d != null;
        return output.format(d);
    }

    public String getDateAndTimeOfPassage() {

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        output.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d = null;
        try {
            d = sdf.parse(dateOfPassage.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert d != null;
        return output.format(d);
    }

    // Return only time
    public String getTimeOfPassage() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat("HH:mm");
        output.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d = null;
        try {
            d = sdf.parse(dateOfPassage.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d != null;
        return output.format(d);
    }

    public String getDescription() {
        return description;
    }

    public List<User> getUsersCollection() {
        return usersCollection;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "mId=" + mId +
                ", mCreatedUser=" + mCreatedUser +
                ", mRoom=" + mRoom +
                ", dateOfPassage=" + dateOfPassage +
                ", description='" + description + '\'' +
                ", usersCollection=" + usersCollection +
                '}';
    }
}
