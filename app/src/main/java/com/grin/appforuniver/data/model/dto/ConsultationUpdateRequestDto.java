package com.grin.appforuniver.data.model.dto;

import com.google.gson.annotations.SerializedName;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.model.user.User;

import java.util.Date;

public class ConsultationUpdateRequestDto {

    @SerializedName("idCreatedUser")
    private int mIdCreatedUser;

    @SerializedName("idRoom")
    private int mIdRoom;

    @SerializedName("date_of_passage")
    private Date date_of_passage;

}
