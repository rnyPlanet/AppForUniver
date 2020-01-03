package com.grin.appforuniver.data.WebServices;

import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ScheduleInterface {

    @GET("schedule/classes/user/criteria")
    Call<List<Classes>> getSchedulePlace(@Query("place") Constants.Place place);

    @GET("schedule/classes/user/all")
    Call<List<Classes>> getScheduleCurrentUser();
}
