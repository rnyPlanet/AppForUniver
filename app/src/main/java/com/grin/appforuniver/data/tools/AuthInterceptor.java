package com.grin.appforuniver.data.tools;

import android.content.Intent;

import com.grin.appforuniver.App;
import com.grin.appforuniver.activities.LoginActivity;
import com.grin.appforuniver.data.model.AccessToken;
import com.grin.appforuniver.data.service.AuthService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer " + AuthManager.getInstance().getAccessToken().access_token)
                .build();


        Response response = chain.proceed(request);
        if (response.code() == 401) {
            retrofit2.Response<AccessToken> refreshResponse = AuthService.getService().requestRefreshingToken(AuthManager.getInstance().getAccessToken().refresh_token);
            if (refreshResponse != null && refreshResponse.code() == 200) {
                AuthManager.getInstance().writeAccessToken(refreshResponse.body());
                request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer " + AuthManager.getInstance().getAccessToken().access_token)
                        .build();
                response.close();
                response = chain.proceed(request);
            } else {
                AuthManager.getInstance().logout();
                Intent intent = new Intent(App.getInstance(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                App.getInstance().startActivity(intent);
            }
        }
        return response;
    }
}
