/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.schedule;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

import static com.grin.appforuniver.utils.Constants.Place;
import static com.grin.appforuniver.utils.Constants.Subgroup;
import static com.grin.appforuniver.utils.Constants.Week;

@Data
public class Classes {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("subject")
    private Subject mSubject;

    @SerializedName("type")
    private TypeClasses mType;

    @SerializedName("place")
    private Place mPlace;

    @SerializedName("positionInDay")
    private LessonTime mPositionInDay;

    @SerializedName("additionalRequirements")
    private String mAdditionalRequirements;

    @SerializedName("week")
    private Week mWeek;

    @SerializedName("group")
    private Groups mAssignedGroup;

    @SerializedName("professor")
    private Professors mProfessor;

    @SerializedName("room")
    private Rooms mRoom;

    @SerializedName("subgroup")
    private Subgroup mSubgroup;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public Subject getSubject() {
        return mSubject;
    }

    public void setSubject(Subject mSubject) {
        this.mSubject = mSubject;
    }

    public TypeClasses getType() {
        return mType;
    }

    public void setType(TypeClasses mType) {
        this.mType = mType;
    }

    public Place getPlace() {
        return mPlace;
    }

    public void setPlace(Place mPlace) {
        this.mPlace = mPlace;
    }

    public LessonTime getPositionInDay() {
        return mPositionInDay;
    }

    public void setPositionInDay(LessonTime mPositionInDay) {
        this.mPositionInDay = mPositionInDay;
    }

    public String getAdditionalRequirements() {
        return mAdditionalRequirements;
    }

    public void setAdditionalRequirements(String mAdditionalRequirements) {
        this.mAdditionalRequirements = mAdditionalRequirements;
    }

    public Week getWeek() {
        return mWeek;
    }

    public void setWeek(Week mWeek) {
        this.mWeek = mWeek;
    }

    public Groups getAssignedGroup() {
        return mAssignedGroup;
    }

    public void setAssignedGroup(Groups mAssignedGroup) {
        this.mAssignedGroup = mAssignedGroup;
    }

    public Professors getProfessor() {
        return mProfessor;
    }

    public void setProfessor(Professors mProfessor) {
        this.mProfessor = mProfessor;
    }

    public Rooms getRoom() {
        return mRoom;
    }

    public void setRoom(Rooms mRoom) {
        this.mRoom = mRoom;
    }

    public Subgroup getSubgroup() {
        return mSubgroup;
    }

    public void setSubgroup(Subgroup mSubgroup) {
        this.mSubgroup = mSubgroup;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "mId=" + mId +
                ", mSubject='" + mSubject + '\'' +
                ", mType='" + mType + '\'' +
                ", mPlace=" + mPlace +
                ", mPositionInDay=" + mPositionInDay +
                ", mAdditionalRequirements='" + mAdditionalRequirements + '\'' +
                ", mWeek=" + mWeek +
                ", mAssignedGroup=" + mAssignedGroup +
                ", mProfessor=" + mProfessor +
                ", mRoom=" + mRoom +
                ", mSubgroup=" + mSubgroup +
                '}';
    }
}
