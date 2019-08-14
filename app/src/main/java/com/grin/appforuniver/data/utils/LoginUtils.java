package com.grin.appforuniver.data.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.grin.appforuniver.activity.LoginActivity;
import com.grin.appforuniver.activity.NavigationDrawer;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.authInterface.AuthInterface;
import com.grin.appforuniver.data.WebServices.userInterface.UserInterface;
import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.data.model.user.User;

import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUtils {

    /**
     * menthod gets and store token
     * @param username
     * @param password
     */
    public static void loginUser(String username, String password, Context context) {

        AuthInterface authInterface = ServiceGenerator.createService(AuthInterface.class);

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(username, password);
        Call<Map<Object, Object>> call = authInterface.loginUser(authenticationRequestDto);

        call.enqueue(new Callback<Map<Object, Object>>() {
            @Override
            public void onResponse(Call<Map<Object, Object>> call, Response<Map<Object, Object>> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        for(Map.Entry<Object, Object> item : response.body().entrySet()) {
                            if(item.getKey().equals("token")) {
                                PreferenceUtils.saveUserToken(item.getValue().toString(), context);
                            }
                        }

                        if(PreferenceUtils.getUsername(context) == null) {
                            PreferenceUtils.saveUsername(username, context);
                            PreferenceUtils.savePassword(password, context);
                        }
                        getMe(context);
                    }
                } else {
                    Toasty.error(context, "Fail username OR acc is NOT ACTIVE", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<Map<Object, Object>> call, Throwable t) {
                if(t.getMessage().equals("Failed to connect to /194.9.70.244:8075")) {
                    Toasty.warning(context, "Login with http://192.168.0.1 OR check your internet connection", Toast.LENGTH_LONG, true).show();
                }
            }
        });

    }

    /**
     * megthod gets current login user and save him
     */
    private static void getMe(Context context) {
        UserInterface userInterface = ServiceGenerator.createService(UserInterface.class);

        Call<User> call = userInterface.getMe(PreferenceUtils.getUserToken(context));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null && !PreferenceUtils.getUserToken(context).isEmpty()) {
                        PreferenceUtils.saveUser(response.body(), context);
                        Intent intent = new Intent(context, NavigationDrawer.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toasty.error(context, t.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

}
