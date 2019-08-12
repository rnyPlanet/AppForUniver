package com.grin.appforuniver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.utils.Constants;
import com.grin.appforuniver.data.utils.LoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_username_et)
    TextInputLayout usernameTIL;
    @BindView(R.id.activity_main_password_et)
    TextInputLayout passwordTIL;
    @BindView(R.id.activity_main_login_btn)
    Button lobinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.colorWhiteStatusBar));
        }
    }

    private boolean validateLoginInput() {
        String loginInput = usernameTIL.getEditText().getText().toString().trim();

        if(loginInput.isEmpty()) {
            usernameTIL.setError("Field can't be empty");
            return false;
        } else {
            usernameTIL.setError(null);
            return true;
        }

    }
    private boolean validatePasswordInput() {
        String passwordInput = passwordTIL.getEditText().getText().toString().trim();

        if(passwordInput.isEmpty()) {
            passwordTIL.setError("Field can't be empty");
            return false;
        } else {
            passwordTIL.setError(null);
            return true;
        }

    }

    public void logIn(View v) {
        if(!validateLoginInput() | !validatePasswordInput()) {
            return;
        }

//        loginUser(usernameTIL.getEditText().getText().toString(), passwordTIL.getEditText().getText().toString());
        LoginUtils.loginUser(usernameTIL.getEditText().getText().toString(), passwordTIL.getEditText().getText().toString(), MainActivity.this);
        LoginUtils.getMe(MainActivity.this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(Constants.USERNAME_KEY, usernameTIL.getEditText().getText().toString());
        outState.putString(Constants.PASSWORD_KEY, passwordTIL.getEditText().getText().toString());

        super.onSaveInstanceState(outState);
    }
}
