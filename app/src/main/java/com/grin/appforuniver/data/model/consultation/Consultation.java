
package com.grin.appforuniver.data.model.consultation;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.model.user.User;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Consultation extends AbstractItem<Consultation.ViewHolder> {

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
        } catch (ParseException e) { e.printStackTrace(); }

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
        } catch (ParseException e) { e.printStackTrace(); }

        assert d != null;
        return output.format(d);
    }

    // Return only time
    public String getTimeOfPassage(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat("HH:mm");
        output.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d = null;
        try {
            d = sdf.parse(dateOfPassage.toString());
        } catch (ParseException e) { e.printStackTrace(); }
        assert d != null;
        return output.format(d);
    }

    public String getDescription() {
        return description;
    }

    public List<User> getUsersCollection() {
        return usersCollection;
    }

    @NotNull
    @Override
    public ViewHolder getViewHolder(@NotNull View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_consultation;
    }

    @Override
    public int getType() {
        return R.id.list_item_consultation;
    }

    public class ViewHolder extends FastAdapter.ViewHolder<Consultation> {

        RelativeLayout item_consultation;
        TextView fio;
        TextView dateOfPassage;
        TextView room;
        TextView time;

        private ViewHolder(@NotNull View itemView) {
            super(itemView);

            fio = itemView.findViewById(R.id.list_item_consultation_FIO_tv);
            room = itemView.findViewById(R.id.list_item_consultation_roomNum_tv);
            dateOfPassage = itemView.findViewById(R.id.list_item_consultation_dateOfPassage_tv);
            item_consultation = itemView.findViewById(R.id.list_item_consultation);
            time = itemView.findViewById(R.id.list_item_consultation_time_tv);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void bindView(@NotNull Consultation item, @NotNull List<Object> list) {
            fio.setText(item.mCreatedUser.getLastName() + " " + item.mCreatedUser.getFirstName() + " " + item.mCreatedUser.getPatronymic());
            room.setText(item.mRoom.getName());
            dateOfPassage.setText(item.getDateOfPassage());
            time.setText(item.getTimeOfPassage());
        }

        @Override
        public void unbindView(@NotNull Consultation item) {
            fio.setText(null);
        }
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
