package com.grin.appforuniver.data.WebServices;


import com.grin.appforuniver.data.model.user.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserInterface {

    @GET("users/me")
//    Call<User> getMe(@Header("Authorization") String token);
    Call<User> getMe();


}
