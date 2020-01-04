package com.grin.appforuniver.data.WebServices;


import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.user.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserInterface {
    @GET("user/my_account")
    Call<User> getMyAccount();

    @GET("user/my_account/student")
    Call<User> getMyAccountStudent();

    @GET("user/my_account/professor")
    Call<Professors> getMyAccountProfessor();
}
