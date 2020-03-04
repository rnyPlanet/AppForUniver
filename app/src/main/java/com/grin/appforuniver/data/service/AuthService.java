package com.grin.appforuniver.data.service;

import com.google.gson.GsonBuilder;
import com.grin.appforuniver.data.api.AuthApi;
import com.grin.appforuniver.data.model.AccessToken;
import com.grin.appforuniver.data.tools.TokenAuthenticator;
import com.grin.appforuniver.utils.Constants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthService {
    private static final String TAG = "AuthService";

    public void requestAccessToken(String username, String password, OnRequestTokenListener l) {
        Call<AccessToken> getToken = buildApi().loginUser(username, password, "password");
        getToken.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (l != null) {
                    l.onRequestTokenSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                if (l != null) {
                    l.onRequestTokenFailed(call, t);
                }
            }
        });
    }

    public Response<AccessToken> requestRefreshingToken(String refreshToken) throws IOException {
        Call<AccessToken> getToken = buildApi().refreshAccessToken(refreshToken, "refresh_token");
        return getToken.execute();
    }

    public static AuthService getService() {
        return new AuthService();
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .authenticator(TokenAuthenticator.getInstance())
                .build();
    }

    private AuthApi buildApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.LOGIN_URL)
                .client(buildClient())
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .create()))
                .build()
                .create((AuthApi.class));
    }

    public interface OnRequestTokenListener {
        void onRequestTokenSuccess(Call<AccessToken> call, Response<AccessToken> response);

        void onRequestTokenFailed(Call<AccessToken> call, Throwable t);
    }
}
