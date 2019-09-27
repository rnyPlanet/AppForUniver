/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.consultation;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.grin.appforuniver.R;
import com.grin.appforuniver.activity.ConsultationActivity;
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
    private Rooms idRoom;
    
    @SerializedName("dateOfPassage")
    private Date date_of_passage;

    @NotNull
    @Override
    public ViewHolder getViewHolder(@NotNull View view) { return new ViewHolder(view); }

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
        TextView lastName;
        TextView firstName;
        TextView middleName;
        TextView email;
        TextView room;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            lastName = itemView.findViewById(R.id.list_item_consultation_lastName_tv);
            firstName = itemView.findViewById(R.id.list_item_consultation_firstName_tv);
            middleName = itemView.findViewById(R.id.list_item_consultation_middleName_tv);
            email = itemView.findViewById(R.id.list_item_consultation_email_tv);
            room = itemView.findViewById(R.id.list_item_consultation_roomNum_tv);
            item_consultation = itemView.findViewById(R.id.list_item_consultation);
        }

        @Override
        public void bindView(@NotNull Consultation item, @NotNull List<Object> list) {
            lastName.setText(item.mCreatedUser.getLastName());
            firstName.setText(item.mCreatedUser.getFirstName());
            middleName.setText(item.mCreatedUser.getPatronymic());
            email.setText(item.mCreatedUser.getEmail());
            String roomName = itemView.getResources().getString(R.string.consultation_activity_room) + " " + item.idRoom.getName();
            room.setText(roomName);
            item_consultation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), "asdf", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(itemView.getContext(), ConsultationActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public void unbindView(@NotNull Consultation item) {
            lastName.setText(null);
            firstName.setText(null);
            middleName.setText(null);
            email.setText(null);
        }
    }
}
