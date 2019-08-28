package com.grin.appforuniver.data.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.grin.appforuniver.R;
import com.grin.appforuniver.activity.LaunchActivity;
import com.grin.appforuniver.activity.LoginActivity;
import com.grin.appforuniver.activity.NavigationDrawer;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.authInterface.AuthInterface;
import com.grin.appforuniver.data.WebServices.userInterface.UserInterface;
import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.data.model.user.User;

import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUtils {

    /**
     * menthod gets and store token
     */
    public static void loginUser(String username, String password, Activity activity) {

        Context context = activity.getApplicationContext();

        AuthInterface authInterface = ServiceGenerator.createService(AuthInterface.class);

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(username, password);
        Call<Map<Object, Object>> call = authInterface.loginUser(authenticationRequestDto);

        if (activity instanceof LoginActivity) {
            activity.findViewById(R.id.network_error_view).setVisibility(View.GONE);
            activity.findViewById(R.id.activity_login_ll).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.activity_login_login_btn).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.activity_login_sign_in_btn).setVisibility(View.VISIBLE);
        }

        call.enqueue(new Callback<Map<Object, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<Object, Object>> call, @NonNull Response<Map<Object, Object>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        for (Map.Entry<Object, Object> item : response.body().entrySet()) {
                            if (item.getKey().equals("token")) {
                                PreferenceUtils.saveUserToken(item.getValue().toString(), context);
                            }
                        }

                        if (PreferenceUtils.getUsername(context) == null) {
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
            public void onFailure(@NonNull Call<Map<Object, Object>> call, @NonNull Throwable t) {
                if (t.getMessage().contains("Failed to connect to /194.9.70.244:8075")) {
                    if (activity instanceof LoginActivity) {
                        activity.findViewById(R.id.network_error_view).setVisibility(View.VISIBLE);
                        activity.findViewById(R.id.activity_login_ll).setVisibility(View.GONE);
                        activity.findViewById(R.id.activity_login_login_btn).setVisibility(View.GONE);
                        activity.findViewById(R.id.activity_login_sign_in_btn).setVisibility(View.GONE);
                    } else if (activity instanceof LaunchActivity) {
                        activity.findViewById(R.id.network_error_view).setVisibility(View.VISIBLE);
                        activity.findViewById(R.id.activity_launch_tv).setVisibility(View.VISIBLE);
                    }
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
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && !PreferenceUtils.getUserToken(context).isEmpty()) {
                        PreferenceUtils.saveUser(response.body(), context);
                        PreferenceUtils.saveUserRoles(response.body().getRoles(), context);

                        Intent intent = new Intent(context, NavigationDrawer.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toasty.error(context, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

}
