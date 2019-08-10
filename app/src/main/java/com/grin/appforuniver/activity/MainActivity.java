package com.grin.appforuniver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.authInterface.AuthInterface;
import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.data.utils.Constants;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_username_et)
    TextInputLayout usernameTIL;
    @BindView(R.id.activity_main_password_et)
    TextInputLayout passwordTIL;
    @BindView(R.id.activity_main_login_btn)
    Button lobinBtn;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(MainActivity.this);

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

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        loginUser(usernameTIL.getEditText().getText().toString(), passwordTIL.getEditText().getText().toString());
    }

    private void loginUser(String username, String password) {
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
                                Constants.setUserToken(item.getValue().toString());
                            }
                        }
                        PreferenceUtils.saveUsername(username, getApplicationContext());
                        PreferenceUtils.savePassword(password, getApplicationContext());
                        progressDialog.dismiss();
                        Toasty.success(MainActivity.this, "Log in!", Toast.LENGTH_SHORT, true).show();
//                        Toast.makeText(getApplicationContext(), "Log in!", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
//                        startActivity(intent);
//                        finish();

                    }
                } else {
//                    Toast.makeText(getApplicationContext(), "Fail username OR acc is NOT ACTIVE", Toast.LENGTH_LONG).show();
                    Toasty.error(MainActivity.this, "Fail username OR acc is NOT ACTIVE", Toast.LENGTH_SHORT, true).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Map<Object, Object>> call, Throwable t) {
                Toasty.error(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT, true).show();
                progressDialog.dismiss();
            }
        });

        progressDialog.dismiss();
    }


}
