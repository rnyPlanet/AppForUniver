package com.grin.appforuniver.data.WebServices.authInterface;

import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthInterface {

    @POST("auth/login")
    Call<Map<Object, Object>> loginUser(@Body AuthenticationRequestDto authenticationRequestDto);

}
