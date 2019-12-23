
package com.grin.appforuniver.data.model.consultation;

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

import java.util.Date;
import java.util.List;

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

    public Integer getId() {
        return mId;
    }

    public User getCreatedUser() {
        return mCreatedUser;
    }

    public Rooms getRoom() {
        return mRoom;
    }

    public Date getDateOfPassage() {
        return dateOfPassage;
    }

    public String getDescription() {
        return description;
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

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            fio = itemView.findViewById(R.id.list_item_consultation_FIO_tv);
            room = itemView.findViewById(R.id.list_item_consultation_roomNum_tv);
            dateOfPassage = itemView.findViewById(R.id.list_item_consultation_dateOfPassage_tv);
            item_consultation = itemView.findViewById(R.id.list_item_consultation);
        }

        @Override
        public void bindView(@NotNull Consultation item, @NotNull List<Object> list) {
            fio.setText(item.mCreatedUser.getLastName() + " " + item.mCreatedUser.getFirstName() + " " + item.mCreatedUser.getPatronymic());
            room.setText(itemView.getResources().getString(R.string.consultation_activity_room) + item.mRoom.getName());
            dateOfPassage.setText(item.dateOfPassage.toString());
        }

        @Override
        public void unbindView(@NotNull Consultation item) {
            fio.setText(null);
        }
    }
}
