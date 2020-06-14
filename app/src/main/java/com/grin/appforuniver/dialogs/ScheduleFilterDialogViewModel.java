package com.grin.appforuniver.dialogs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grin.appforuniver.data.model.schedule.Groups;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.service.GroupService;
import com.grin.appforuniver.data.service.ProfessorService;
import com.grin.appforuniver.data.service.RoomService;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ScheduleFilterDialogViewModel extends ViewModel {

    private MutableLiveData<List<Professors>> professorsLiveData;
    private MutableLiveData<List<Rooms>> roomsLiveData;
    private MutableLiveData<List<Groups>> groupsLiveData;
    private MutableLiveData<String> errorMessageLiveData;

    private GroupService mGroupService;
    private ProfessorService mProfessorService;
    private RoomService mRoomService;

    public ScheduleFilterDialogViewModel() {
        mGroupService = GroupService.getService();
        mProfessorService = ProfessorService.getService();
        mRoomService = RoomService.getService();

        professorsLiveData = new MutableLiveData<>();
        roomsLiveData = new MutableLiveData<>();
        groupsLiveData = new MutableLiveData<>();
        errorMessageLiveData = new MutableLiveData<>();
        setProfessorsLiveData();
        setRoomsLiveData();
        setGroupsLiveData();
    }

    public void setProfessorsLiveData() {
        mProfessorService.requestAllProfessors(new ProfessorService.OnRequestProfessorListListener() {
            @Override
            public void onRequestProfessorListSuccess(Call<List<Professors>> call, Response<List<Professors>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Professors> professors = response.body();
                        Collections.sort(professors, (professors1, t1) -> String.CASE_INSENSITIVE_ORDER.compare(professors1.getUser().getShortFIO(), t1.getUser().getShortFIO()));
                        professorsLiveData.setValue(professors);
                    }
                }
            }

            @Override
            public void onRequestProfessorListFailed(Call<List<Professors>> call, Throwable t) {
                errorMessageLiveData.setValue(t.getMessage());
            }
        });
    }

    public void setRoomsLiveData() {
        mRoomService.requestAllRooms(new RoomService.OnRequestRoomListListener() {
            @Override
            public void onRequestRoomListSuccess(Call<List<Rooms>> call, Response<List<Rooms>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Rooms> rooms = response.body();
                        Collections.sort(rooms, (room1, t1) -> String.CASE_INSENSITIVE_ORDER.compare(room1.getName(), t1.getName()));
                        roomsLiveData.setValue(rooms);
                    }
                }
            }

            @Override
            public void onRequestRoomListFailed(Call<List<Rooms>> call, Throwable t) {
                errorMessageLiveData.setValue(t.getMessage());
            }
        });
    }

    public void setGroupsLiveData() {
        mGroupService.requestAllGroups(new GroupService.OnRequestGroupListListener() {
            @Override
            public void onRequestGroupListSuccess(Call<List<Groups>> call, Response<List<Groups>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Groups> groups = response.body();
                        Collections.sort(groups, (group1, t1) -> String.CASE_INSENSITIVE_ORDER.compare(group1.getmName(), t1.getmName()));
                        groupsLiveData.setValue(groups);
                    }
                }
            }

            @Override
            public void onRequestGroupListFailed(Call<List<Groups>> call, Throwable t) {
                errorMessageLiveData.setValue(t.getMessage());
            }
        });
    }

    public LiveData<List<Professors>> getProfessorsLiveData() {
        return professorsLiveData;
    }

    public LiveData<List<Rooms>> getRoomsLiveData() {
        return roomsLiveData;
    }

    public LiveData<List<Groups>> getGroupsLiveData() {
        return groupsLiveData;
    }

    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }
}
