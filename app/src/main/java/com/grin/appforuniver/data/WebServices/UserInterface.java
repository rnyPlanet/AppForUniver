package com.grin.appforuniver.data.WebServices;


import com.grin.appforuniver.data.model.consultation.Сonsultation;
import com.grin.appforuniver.data.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserInterface {

    @GET("users/me")
    Call<User> getMe(@Header("Authorization") String token);

    @GET("users/consultation/all")
    Call<List<Сonsultation>> getcConsultation(@Header("Authorization") String token);
}
