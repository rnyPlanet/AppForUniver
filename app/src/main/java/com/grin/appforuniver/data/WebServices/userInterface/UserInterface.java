package com.grin.appforuniver.data.WebServices.userInterface;

import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserInterface {

    @GET("users/me")
    Call<User> getMe(@Header("Authorization") String token);

    @GET("users/classes/criteria?place=MONDAY")
    Call<List<Classes>> getScheduleMonday(@Header("Authorization") String token);

    @GET("users/classes/criteria?place=TUESDAY")
    Call<List<Classes>> getScheduleTuesday(@Header("Authorization") String token);

    @GET("users/classes/criteria?place=WEDNESDAY")
    Call<List<Classes>> getScheduleWednesday(@Header("Authorization") String token);

    @GET("users/classes/criteria?place=THURSDAY")
    Call<List<Classes>> getScheduleThursday(@Header("Authorization") String token);

    @GET("users/classes/criteria?place=FRIDAY")
    Call<List<Classes>> getScheduleFriday(@Header("Authorization") String token);

    @GET("users/classes/all")
    Call<List<Classes>> getScheduleAll(@Header("Authorization") String token);

}
