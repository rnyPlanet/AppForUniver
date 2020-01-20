package com.grin.appforuniver.data.model.schedule;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;

public class LessonTime {
    
    @SerializedName("id")
    private Integer mId;

    @SerializedName("position")
    private Integer mType;
    
    @SerializedName("startTime")
    private String mStartTime;
    
    @SerializedName("endTime")
    private String mEndTime;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public Integer getType() {
        return mType;
    }

    public void setType(Integer mType) {
        this.mType = mType;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    @Override
    public String toString() {
        return "LessonTime{" +
                "mId=" + mId +
                ", mType=" + mType +
                ", mStartTime=" + mStartTime +
                ", mEndTime=" + mEndTime +
                '}';
    }
}
