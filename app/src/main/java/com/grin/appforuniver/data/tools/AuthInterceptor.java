package com.grin.appforuniver.data.tools;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request;
        if (AuthManager.getInstance().getAccessToken() == null) {
            request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "")
                    .build();
        } else {
            request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer_" + AuthManager.getInstance().getAccessToken())
                    .build();
        }
        return chain.proceed(request);
    }
}
