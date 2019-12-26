package com.grin.appforuniver.data.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ConsultationRequestDto {

    @SerializedName("idCreatedUser")
    private int mIdCreatedUser;

    @SerializedName("idRoom")
    private int mIdRoom;

    @SerializedName("dateOfPassage")
    private String dateOfPassage;

    @SerializedName("description")
    private String description;

    public ConsultationRequestDto(int idCreatedUser, int idRoom, String dateOfPassage, String description) {
        this.mIdCreatedUser = idCreatedUser;
        this.mIdRoom = idRoom;
        this.dateOfPassage = dateOfPassage;
        this.description = description;
    }
}
