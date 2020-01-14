package com.grin.appforuniver.data.tools;

import com.grin.appforuniver.utils.PreferenceUtils;

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
        request = chain.request()
                .newBuilder()
                .addHeader("Authorization", (PreferenceUtils.getUserToken() == null) ? "" : PreferenceUtils.getUserToken())
                .build();
        return chain.proceed(request);
    }
}
