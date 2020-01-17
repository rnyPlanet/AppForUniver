package com.grin.appforuniver.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.service.AuthService;
import com.grin.appforuniver.data.service.UserService;
import com.grin.appforuniver.utils.PreferenceUtils;

import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public final String TAG = LoginActivity.class.getSimpleName();

    private AuthService mAuthService;
    private UserService mUserService;
    private ProgressDialog mProgressBar;

    @BindView(R.id.activity_login_username_et)
    TextInputLayout usernameTIL;
    @BindView(R.id.activity_login_password_et)
    TextInputLayout passwordTIL;
    @BindView(R.id.password_field)
    AppCompatEditText passwordField;

    @BindView(R.id.login_activity_constraint)
    ConstraintLayout loginPageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthService = AuthService.getService();
        mUserService = UserService.getService();
        PreferenceUtils.setContext(getApplicationContext());
        PreferenceUtils.saveUserToken(null);

        mProgressBar = new ProgressDialog(LoginActivity.this);

        if (PreferenceUtils.getUsername() != null & PreferenceUtils.getPassword() != null) {
            mProgressBar.show();
            loginUser(PreferenceUtils.getUsername(), PreferenceUtils.getPassword());
        } else {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
            passwordField.setOnEditorActionListener((textView, actionId, keyEvent) -> {
                if (actionId == EditorInfo.IME_ACTION_SEND) logIn();
                return false;
            });

            //
            // Change header visibility
            //

            loginPageActivity.post(() -> {
                int height = loginPageActivity.getHeight();
                loginPageActivity.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
                    if ((bottom - top) == height) {
                        changeHeaderItemsVisible("visible");
                    } else {
                        changeHeaderItemsVisible("gone");
                    }
                });
            });
        }
    }

    private void loginUser(String username, String password) {
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(username, password);
        mAuthService.requestToken(authenticationRequestDto, new AuthService.OnRequstTokenListener() {
            @Override
            public void onRequestTokenSuccess(Call<Map<Object, Object>> call, Response<Map<Object, Object>> response) {
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
                    Toasty.error(Objects.requireNonNull(getApplicationContext()), getString(R.string.fail_username_OR_acc_is_NOTACTIVE), Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onRequestTokenFailed(Call<Map<Object, Object>> call, Throwable t) {
                mProgressBar.dismiss();
                if (Objects.requireNonNull(t.getMessage()).contains("Failed to connect to /194.9.70.244:8075")) {
                    PreferenceUtils.saveUserToken(null);
                    Toasty.error(Objects.requireNonNull(getApplicationContext()), getString(R.string.check_your_internet_connection), Toasty.LENGTH_LONG, true).show();
                }
            }
        });

    }

    private void getMe() {
        mUserService.requestCurrentUserProfile(new UserService.OnRequestCurrentUserProfileListener() {
            @Override
            public void onRequestCurrentUserProfileSuccess(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && !PreferenceUtils.getUserToken().isEmpty()) {
                        PreferenceUtils.saveUser(response.body());
                        PreferenceUtils.saveUserRoles(response.body().getRoles());

                        mProgressBar.dismiss();

                        Intent intent = new Intent(getApplicationContext(), NavigationDrawer.class);
                        startActivity(intent);

                        finish();
                    }
                }
            }

            @Override
            public void onRequestCurrentUserProfileFailed(Call<User> call, Throwable t) {
                Toasty.error(Objects.requireNonNull(getApplicationContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
                mProgressBar.dismiss();
            }
        });
    }

    private boolean validateLoginInput() {
        String loginInput = Objects.requireNonNull(usernameTIL.getEditText()).getText().toString().trim();

        if (loginInput.isEmpty()) {
            usernameTIL.setError(getString(R.string.field_cant_be_empty));
            return false;
        } else {
            usernameTIL.setError(null);
            return true;
        }

    }

    private boolean validatePasswordInput() {
        String passwordInput = Objects.requireNonNull(passwordTIL.getEditText()).getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passwordTIL.setError(getString(R.string.field_cant_be_empty));
            return false;
        } else {
            passwordTIL.setError(null);
            return true;
        }

    }

    @OnClick(R.id.activity_login_login_btn)
    void logIn() {
        if (validateLoginInput() & validatePasswordInput()) {
            mProgressBar.setMessage(getString(R.string.checking));
            mProgressBar.show();

            loginUser(Objects.requireNonNull(usernameTIL.getEditText()).getText().toString(),
                    Objects.requireNonNull(passwordTIL.getEditText()).getText().toString());

        }
    }

    // Visual settings
    private void changeHeaderItemsVisible(String visibility) {
        TextView login_title = findViewById(R.id.login_title);
        ImageView university_logo = findViewById(R.id.university_logo);

        switch (visibility) {
            case "gone":
                login_title.setVisibility(View.GONE);
                university_logo.setVisibility(View.GONE);
                break;
            case "visible":
                login_title.setVisibility(View.VISIBLE);
                university_logo.setVisibility(View.VISIBLE);
                break;
        }
    }

}
