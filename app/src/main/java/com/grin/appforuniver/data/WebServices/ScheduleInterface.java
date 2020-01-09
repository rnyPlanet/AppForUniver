package com.grin.appforuniver.data.WebServices;

import com.grin.appforuniver.data.model.schedule.Classes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ScheduleInterface {

    @GET("schedule/classes/criteria")
    Call<List<Classes>> getScheduleByCriteria(@Query("subjectId") int subject,
                                              @Query("type") String type,
                                              @Query("professorId") int professorId,
                                              @Query("roomId") int roomId,
                                              @Query("groupId") int groupId,
                                              @Query("place") String place,
                                              @Query("week") String week,
                                              @Query("indexInDay") int indexInDay,
                                              @Query("additionalRequirements") String additionalRequirements);

    @GET("schedule/classes/user/all")
    Call<List<Classes>> getScheduleCurrentUser();
}
