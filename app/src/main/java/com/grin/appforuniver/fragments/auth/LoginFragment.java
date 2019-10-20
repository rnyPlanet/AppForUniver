package com.grin.appforuniver.fragments.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.activity.LoginActivity;
import com.grin.appforuniver.activity.NavigationDrawer;
import com.grin.appforuniver.data.WebServices.AuthInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.UserInterface;
import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    public final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.activity_login_username_et)
    TextInputLayout usernameTIL;
    @BindView(R.id.activity_login_password_et)
    TextInputLayout passwordTIL;

    private ProgressDialog mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        mProgressBar = new ProgressDialog(getContext());

        return view;
    }

    private void loginUser(String username, String password) {
        AuthInterface authInterface = ServiceGenerator.createService(AuthInterface.class);
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(username, password);

        Call<Map<Object, Object>> call = authInterface.loginUser(authenticationRequestDto);
        call.enqueue(new Callback<Map<Object, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<Object, Object>> call, @NonNull Response<Map<Object, Object>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        for (Map.Entry<Object, Object> item : response.body().entrySet()) {
                            if (item.getKey().equals("token")) {
                                PreferenceUtils.saveUserToken(item.getValue().toString());
                            }
                        }

                        if (PreferenceUtils.getUsername() == null) {
                            PreferenceUtils.saveUsername(username);
                            PreferenceUtils.savePassword(password);
                        }

                        getMe();

                    }
                } else {
                    mProgressBar.dismiss();
                    PreferenceUtils.saveUserToken(null);
                    Toasty.error(Objects.requireNonNull(getContext()), "Fail username OR acc is NOT ACTIVE", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<Object, Object>> call, @NonNull Throwable t) {
                mProgressBar.dismiss();
                if (Objects.requireNonNull(t.getMessage()).contains("Failed to connect to /194.9.70.244:8075")) {
                    PreferenceUtils.saveUserToken(null);
                    Toasty.error(Objects.requireNonNull(getContext()), "Check your internet connection!", Toasty.LENGTH_LONG, true).show();
                }
            }
        });

    }

    private void getMe() {
        UserInterface userInterface = ServiceGenerator.createService(UserInterface.class);

//        Call<User> call = userInterface.getMe(PreferenceUtils.getUserToken());
        Call<User> call = userInterface.getMe();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && !PreferenceUtils.getUserToken().isEmpty()) {
                        PreferenceUtils.saveUser(response.body());
                        PreferenceUtils.saveUserRoles(response.body().getRoles());

                        mProgressBar.dismiss();

                        Intent intent = new Intent();
                        Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);

                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private boolean validateLoginInput() {
        String loginInput = Objects.requireNonNull(usernameTIL.getEditText()).getText().toString().trim();

        if (loginInput.isEmpty()) {
            usernameTIL.setError("Field can't be empty");
            return false;
        } else {
            usernameTIL.setError(null);
            return true;
        }

    }

    private boolean validatePasswordInput() {
        String passwordInput = Objects.requireNonNull(passwordTIL.getEditText()).getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passwordTIL.setError("Field can't be empty");
            return false;
        } else {
            passwordTIL.setError(null);
            return true;
        }

    }

    private void checkConnection(boolean isConnected) {
        if (!isConnected) {
//            try_to_login_with_192_168_0_1.setVisibility(View.VISIBLE);
//            networkErrorView.setVisibility(View.VISIBLE);
        } else {
//            try_to_login_with_192_168_0_1.setVisibility(View.GONE);
//            networkErrorView.setVisibility(View.GONE);

            String sharedUsername = PreferenceUtils.getUsername();
            String sharedUserPassword = PreferenceUtils.getPassword();

            if (sharedUsername != null && !sharedUsername.isEmpty() && sharedUserPassword != null && !sharedUserPassword.isEmpty()) {
                loginUser(sharedUsername, sharedUserPassword);
            }
        }

    }

    @OnClick(R.id.activity_login_login_btn)
    void logIn() {
        if (validateLoginInput() & validatePasswordInput()) {
            mProgressBar.setMessage("Checking...");
            mProgressBar.show();

            loginUser(Objects.requireNonNull(usernameTIL.getEditText()).getText().toString(),
                    Objects.requireNonNull(passwordTIL.getEditText()).getText().toString());


        }
    }

}
