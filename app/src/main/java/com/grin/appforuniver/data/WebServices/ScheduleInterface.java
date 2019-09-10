package com.grin.appforuniver.data.WebServices;

import com.grin.appforuniver.data.model.schedule.Classes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ScheduleInterface {

    @GET("schedule/classes/user/criteria")
    Call<List<Classes>> getSchedulePlace(@Header("Authorization") String token,
                                         @Query("place") Classes.Place place);

    @GET("schedule/classes/user/all")
    Call<List<Classes>> getScheduleAll(@Header("Authorization") String token);


}
