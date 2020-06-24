package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.models.AccessToken;
import com.grin.appforuniver.data.models.User;
import com.grin.appforuniver.data.models.dto.RegisterRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("oauth/token")
    @FormUrlEncoded
    Call<AccessToken> refreshAccessToken(@Field("refresh_token") String refresh_token,
                                         @Field("grant_type") String grant_type);

    @POST("oauth/token")
    @FormUrlEncoded
    Call<AccessToken> loginUser(@Field("username") String username,
                                @Field("password") String password,
                                @Field("grant_type") String grant_type);

    @POST("auth/reg")
    Call<User> regUser(@Body RegisterRequestDto registerRequestDto);
}
