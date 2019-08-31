/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.schedule;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * @author Йцукен
 */
@Data
public class Classes {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("subject")
    private String mSubject;

    @SerializedName("type")
    private String mType;


    public enum Place {POOL, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;}

    @SerializedName("place")
    private Place mPlace;

    @SerializedName("indexInDay")
    private int mIndexInDay;

    @SerializedName("additionalRequirements")
    private String mAdditionalRequirements;

    public enum Week {FIRST, SECOND;}

    @SerializedName("week")
    private Week mWeek;

    @SerializedName("assignedGroupID")
    private Groups mAssignedGroupID;

    @SerializedName("professorID")
    private Professors mProfessorID;

    @SerializedName("roomID")
    private Rooms mRoomID;

    public enum Subgroup {FIRST, SECOND, BOTH;}

    @SerializedName("subgroup")
    private Subgroup mSubgroup;

    public Integer getId() {
        return mId;
    }

    public String getSubject() {
        return mSubject;
    }

    public String getType() {
        return mType;
    }

    public Place getPlace() {
        return mPlace;
    }

    public int getIndexInDay() {
        return mIndexInDay;
    }

    public String getAdditionalRequirements() {
        return mAdditionalRequirements;
    }

    public Week getWeek() {
        return mWeek;
    }

    public Groups getAssignedGroupID() {
        return mAssignedGroupID;
    }

    public Professors getProfessorID() {
        return mProfessorID;
    }

    public Rooms getRoomID() {
        return mRoomID;
    }

    public Subgroup getSubgroup() {
        return mSubgroup;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "mId=" + mId + '\t' +
                ", mSubject='" + mSubject + '\'' + '\t' +
                ", mType='" + mType + '\'' + '\t' +
                ", mPlace=" + mPlace + '\t' +
                ", mIndexInDay=" + mIndexInDay + '\t' +
                ", mAdditionalRequirements='" + mAdditionalRequirements + '\'' + '\t' +
                ", mWeek=" + mWeek + '\t' +
                ", mAssignedGroupID=" + mAssignedGroupID + '\t' +
                ", mProfessorID=" + mProfessorID + '\t' +
                ", mRoomID=" + mRoomID + '\t' +
                ", mSubgroup=" + mSubgroup + '\t' +
                "}\n";
    }
}
