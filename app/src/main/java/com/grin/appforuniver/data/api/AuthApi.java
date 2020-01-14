package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.data.model.dto.RegisterRequestDto;
import com.grin.appforuniver.data.model.user.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("auth/login")
    Call<Map<Object, Object>> loginUser(@Body AuthenticationRequestDto authenticationRequestDto);

    @POST("auth/reg")
    Call<User> regUser(@Body RegisterRequestDto registerRequestDto);
}
