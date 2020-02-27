package com.grin.appforuniver.data.service;

import com.google.gson.GsonBuilder;
import com.grin.appforuniver.data.api.AuthApi;
import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.utils.Constants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthService {

    public void requestToken(AuthenticationRequestDto user, OnRequstTokenListener l) {
        Call<Map<Object, Object>> getToken = buildApi().loginUser(user);
        getToken.enqueue(new Callback<Map<Object, Object>>() {
            @Override
            public void onResponse(Call<Map<Object, Object>> call, Response<Map<Object, Object>> response) {
                if (l != null) {
                    l.onRequestTokenSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Map<Object, Object>> call, Throwable t) {
                if (l != null) {
                    l.onRequestTokenFailed(call, t);
                }
            }
        });
    }

    public static AuthService getService() {
        return new AuthService();
    }

    private AuthApi buildApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .create()))
                .build()
                .create((AuthApi.class));
    }

    public interface OnRequstTokenListener {
        void onRequestTokenSuccess(Call<Map<Object, Object>> call, Response<Map<Object, Object>> response);

        void onRequestTokenFailed(Call<Map<Object, Object>> call, Throwable t);
    }
}
