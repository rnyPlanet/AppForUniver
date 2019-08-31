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

    @GET("users/classes/all")
    Call<List<Classes>> getSchedule(@Header("Authorization") String token);

}
