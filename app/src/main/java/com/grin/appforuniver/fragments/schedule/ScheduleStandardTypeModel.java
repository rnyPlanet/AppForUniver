package com.grin.appforuniver.fragments.schedule;

import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.utils.Constants;

import java.util.List;

public class ScheduleStandardTypeModel {
    public int typeItem;
    public Constants.Place place;
    public int positionInDay;
    public List<Classes> classes;

    public ScheduleStandardTypeModel(int typeItem, Constants.Place place, int positionInDay, List<Classes> classes) {
        this.typeItem = typeItem;
        this.place = place;
        this.positionInDay = positionInDay;
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "ScheduleStandardTypeModel{" +
                "place=" + place +
                ", typeItem=" + typeItem +
                ", positionInDay=" + positionInDay +
                ", classes=" + classes +
                '}';
    }
}
