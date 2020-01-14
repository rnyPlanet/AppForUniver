package com.grin.appforuniver.data.api;


import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.user.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApi {
    @GET("user/my_account")
    Call<User> getMyAccount();

    @GET("user/my_account/student")
    Call<User> getMyAccountStudent();

    @GET("user/my_account/professor")
    Call<Professors> getMyAccountProfessor();
}
