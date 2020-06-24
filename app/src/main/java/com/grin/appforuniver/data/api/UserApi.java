package com.grin.appforuniver.data.api;


import com.grin.appforuniver.data.models.Professors;
import com.grin.appforuniver.data.models.User;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserApi {

    @GET("users/my_account")
    Call<User> getMyAccount();

    @GET("users/my_account/student")
    Call<User> getMyAccountStudent();

    @GET("users/my_account/professor")
    Call<Professors> getMyAccountProfessor();

    @Multipart
    @POST("photo/current_user/set_avatar")
    Call<Void> setAvatarProfile(@Part MultipartBody.Part photo);
}
