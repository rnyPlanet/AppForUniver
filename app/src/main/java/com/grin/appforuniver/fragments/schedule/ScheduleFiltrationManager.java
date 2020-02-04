package com.grin.appforuniver.fragments.schedule;

import com.grin.appforuniver.data.model.schedule.Groups;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.model.schedule.Subject;
import com.grin.appforuniver.data.model.schedule.TypeClasses;
import com.grin.appforuniver.data.service.ScheduleService;
import com.grin.appforuniver.data.tools.AuthManager;

import java.util.ArrayList;
import java.util.List;

import static com.grin.appforuniver.utils.Constants.Place;
import static com.grin.appforuniver.utils.Constants.Roles.ROLE_TEACHER;
import static com.grin.appforuniver.utils.Constants.Week;

public class ScheduleFiltrationManager {

    private ScheduleService mScheduleService;
    private ScheduleService.OnRequestScheduleListener mScheduleListener;
    private Subject subject;
    private TypeClasses type;
    private Professors professor;
    private Rooms room;
    private Groups group;
    private Place place;
    private Week week;

    private boolean isProfessorSchedule;

    private ScheduleFiltrationManager(Builder builder) {
        mScheduleService = ScheduleService.getService();
        subject = builder.subject;
        type = builder.type;
        professor = builder.professor;
        room = builder.room;
        group = builder.group;
        place = builder.place;
        week = builder.week;
        mScheduleListener = builder.listener;
    }

    public static class Builder {
        private ScheduleService.OnRequestScheduleListener listener;
        private Subject subject = null;
        private TypeClasses type = null;
        private Professors professor = null;
        private Rooms room = null;
        private Groups group = null;
        private Place place = null;
        private Week week = null;

        public Builder(ScheduleService.OnRequestScheduleListener listener) {
            if (listener != null) {
                this.listener = listener;
            } else {
                throw new NullPointerException("Required not null listener");
            }
        }

        public Builder filtrationBySubject(Subject subject) {
            this.subject = subject;
            return this;
        }

        public Builder filtrationByType(TypeClasses typeClasses) {
            this.type = typeClasses;
            return this;
        }

        public Builder filtrationByProfessor(Professors professor) {
            this.professor = professor;
            return this;
        }

        public Builder filtrationByRoom(Rooms room) {
            this.room = room;
            return this;
        }

        public Builder filtrationByGroup(Groups group) {
            this.group = group;
            return this;
        }

        public Builder filtrationByPlace(Place place) {
            this.place = place;
            return this;
        }

        public Builder filtrationByWeek(Week week) {
            this.week = week;
            return this;
        }

        public ScheduleFiltrationManager build() {
            return new ScheduleFiltrationManager(this);
        }
    }

    public void getSchedule() {
        int subjectId = (subject != null) ? subject.getId() : -1;
        int typeClassesId = (type != null) ? type.getId() : -1;
        int professorId = (professor != null) ? professor.getId() : -1;
        int roomId = (room != null) ? room.getId() : -1;
        int groupId = (group != null) ? group.getmId() : -1;
        String placeStr = (place != null) ? place.toString() : null;
        String weekStr = (week != null) ? week.toString() : null;

        if (subjectId == -1 & typeClassesId == -1 & professorId == -1 &
                roomId == -1 & groupId == -1 & placeStr == null &
                weekStr == null) {
            //Fix for teacher schedule display normally
            isProfessorSchedule = AuthManager.getInstance().getUserRoles().contains(ROLE_TEACHER.toString());

            mScheduleService.requestScheduleCurrentUser(mScheduleListener);
        } else {
            isProfessorSchedule = professorId != -1;
            mScheduleService.requestScheduleByCriteria(
                    subjectId,
                    typeClassesId,
                    professorId,
                    roomId,
                    groupId,
                    placeStr,
                    weekStr,
                    -1,
                    null,
                    mScheduleListener);
        }
    }

    public List<Object> getFilterItems() {
        List<Object> listFilterItems = new ArrayList<>();
        if (subject != null) listFilterItems.add(subject);
        if (type != null) listFilterItems.add(type);
        if (professor != null) listFilterItems.add(professor);
        if (room != null) listFilterItems.add(room);
        if (group != null) listFilterItems.add(group);
        if (place != null) listFilterItems.add(place);
        if (week != null) listFilterItems.add(week);
        return listFilterItems;
    }

    public boolean isProfessorsSchedule() {
        return isProfessorSchedule;
    }
}
