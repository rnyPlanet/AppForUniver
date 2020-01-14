package com.grin.appforuniver.fragments.schedule;

import com.grin.appforuniver.data.api.ScheduleApi;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.model.schedule.Groups;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.model.schedule.Subject;
import com.grin.appforuniver.utils.PreferenceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.grin.appforuniver.utils.Constants.Place;
import static com.grin.appforuniver.utils.Constants.Roles.ROLE_TEACHER;
import static com.grin.appforuniver.utils.Constants.TypesOfClasses;
import static com.grin.appforuniver.utils.Constants.Week;

public class ScheduleFiltrationManager {
    private int subject;
    private String type;
    private int professorId;
    private int roomId;
    private int groupId;
    private String place;
    private String week;
    private Callback<List<Classes>> callbackRetrofitSchedule;

    public ScheduleFiltrationManager(Callback<List<Classes>> callbackRetrofitSchedule) {
        this.callbackRetrofitSchedule = callbackRetrofitSchedule;
    }

    private void initializeParameters(Subject subject, TypesOfClasses type, Professors professor, Rooms room, Groups group, Place place, Week week) {
        this.subject = (subject != null) ? subject.getId() : -1;
        this.type = (type != null) ? type.toString() : null;
        this.professorId = (professor != null) ? professor.getId() : -1;
        this.roomId = (room != null) ? room.getId() : -1;
        this.groupId = (group != null) ? group.getmId() : -1;
        this.place = (place != null) ? place.toString() : null;
        this.week = (week != null) ? week.toString() : null;
    }

    public void getSchedule(Subject subject, TypesOfClasses type, Professors professor, Rooms room, Groups group, Place place, Week week) {
        initializeParameters(subject, type, professor, room, group, place, week);

        ScheduleApi scheduleApi = ServiceGenerator.createService(ScheduleApi.class);
        Call<List<Classes>> call;

        if (this.subject == -1 & this.type == null & this.professorId == -1 &
                this.roomId == -1 & this.groupId == -1 & this.place == null &
                this.week == null) {
            call = scheduleApi.getScheduleCurrentUser();
            if (PreferenceUtils.getUserRoles().contains(ROLE_TEACHER.toString())){
                //Fix for teacher schedule display normally
                this.professorId=0;
            }
        } else {
            call = scheduleApi.getScheduleByCriteria(
                    this.subject,
                    this.type,
                    this.professorId,
                    this.roomId,
                    this.groupId,
                    this.place,
                    this.week, -1, null);
        }
        if (callbackRetrofitSchedule != null)
            call.enqueue(callbackRetrofitSchedule);
    }

    public boolean isProfessorsSchedule() {
        return professorId != -1 & groupId == -1;
    }
}
