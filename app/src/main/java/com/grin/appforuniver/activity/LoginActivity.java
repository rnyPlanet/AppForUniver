package com.grin.appforuniver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.utils.Constants;
import com.grin.appforuniver.data.utils.LoginUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    public final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.activity_login_username_et)
    TextInputLayout usernameTIL;
    @BindView(R.id.activity_login_password_et)
    TextInputLayout passwordTIL;

    private Boolean mIfFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorWhite));
        }
    }

    private boolean validateLoginInput() {
        String loginInput = Objects.requireNonNull(usernameTIL.getEditText()).getText().toString().trim();

        if(loginInput.isEmpty()) {
            usernameTIL.setError("Field can't be empty");
            return false;
        } else {
            usernameTIL.setError(null);
            return true;
        }

    }
    private boolean validatePasswordInput() {
        String passwordInput = Objects.requireNonNull(passwordTIL.getEditText()).getText().toString().trim();

        if(passwordInput.isEmpty()) {
            passwordTIL.setError("Field can't be empty");
            return false;
        } else {
            passwordTIL.setError(null);
            return true;
        }

    }

    @OnClick(R.id.activity_login_login_btn)
    public void logIn() {
        if(!validateLoginInput() | !validatePasswordInput()) {
            return;
        }

        LoginUtils.loginUser(Objects.requireNonNull(usernameTIL.getEditText()).getText().toString(), Objects.requireNonNull(passwordTIL.getEditText()).getText().toString(), LoginActivity.this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(Constants.USERNAME_KEY, Objects.requireNonNull(usernameTIL.getEditText()).getText().toString());
        outState.putString(Constants.PASSWORD_KEY, Objects.requireNonNull(passwordTIL.getEditText()).getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!mIfFirstLoad) {
            logIn();
        }

        mIfFirstLoad = false;

    }

}
