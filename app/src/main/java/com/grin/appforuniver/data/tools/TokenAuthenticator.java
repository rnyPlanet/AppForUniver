package com.grin.appforuniver.data.tools;

import com.grin.appforuniver.utils.Constants;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
        Request request = response.request();
        return firstAuthentication(request);
    }

    private Request firstAuthentication(Request request) throws IOException {
        String header = request.header("Authorization");
        if (header != null) {
            // Логин и пароль неверны
            return null;
        } else {
            return request.newBuilder()
                    .header("Authorization", Credentials.basic(Constants.BASIC_AUTH_LOGIN, Constants.BASIC_AUTH_PASSWORD))
                    .build();
        }
    }
}
