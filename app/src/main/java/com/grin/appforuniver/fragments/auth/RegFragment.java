package com.grin.appforuniver.fragments.auth;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.activity.LoginActivity;
import com.grin.appforuniver.data.WebServices.AuthInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.data.model.dto.RegisterRequestDto;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegFragment extends Fragment {

    public final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.fragment_reg_userName_et)
    TextInputLayout userNameTIL;
    @BindView(R.id.fragment_reg_patronymic_et)
    TextInputLayout patronymicTIL;
    @BindView(R.id.fragment_reg_firstName_et)
    TextInputLayout firstNameTIL;
    @BindView(R.id.fragment_reg_lastName_et)
    TextInputLayout lastNameTIL;
    @BindView(R.id.fragment_reg_email_et)
    TextInputLayout emailTIL;
    @BindView(R.id.fragment_reg_password_et)
    TextInputLayout passwordTIL;

    private ProgressDialog mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reg, container, false);

        ButterKnife.bind(this, view);

        mProgressBar = new ProgressDialog(getContext());

        return view;
    }

    private boolean validateLoginInput() {
        String loginInput = Objects.requireNonNull(userNameTIL.getEditText()).getText().toString().trim();

        if (loginInput.isEmpty()) {
            userNameTIL.setError("Field can't be empty");
            return false;
        } else {
            userNameTIL.setError(null);
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
    private boolean validatePatronymicInput() {
        String patronymicInput = Objects.requireNonNull(patronymicTIL.getEditText()).getText().toString().trim();

        if (patronymicInput.isEmpty()) {
            patronymicTIL.setError("Field can't be empty");
            return false;
        } else {
            patronymicTIL.setError(null);
            return true;
        }
    }
    private boolean validateFirstNameInput() {
        String firstNameInput = Objects.requireNonNull(firstNameTIL.getEditText()).getText().toString().trim();

        if (firstNameInput.isEmpty()) {
            firstNameTIL.setError("Field can't be empty");
            return false;
        } else {
            firstNameTIL.setError(null);
            return true;
        }
    }
    private boolean validateLastNameInput() {
        String lastNameInput = Objects.requireNonNull(lastNameTIL.getEditText()).getText().toString().trim();

        if (lastNameInput.isEmpty()) {
            lastNameTIL.setError("Field can't be empty");
            return false;
        } else {
            lastNameTIL.setError(null);
            return true;
        }
    }
    private boolean validateEmailInput() {
        String emailInput = Objects.requireNonNull(emailTIL.getEditText()).getText().toString().trim();

        if (emailInput.isEmpty()) {
            emailTIL.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailTIL.setError("Please enter a valid email address");
            return false;
        } else {
            emailTIL.setError(null);
            return true;
        }
    }

    private void regUser(String username, String patronumic, String firstName, String lastName, String email, String password) {
        AuthInterface regInterface = ServiceGenerator.createService(AuthInterface.class);
        RegisterRequestDto authenticationRequestDto = new RegisterRequestDto(username, patronumic, firstName, lastName, email, password);

        Call<User> call = regInterface.regUser(authenticationRequestDto);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null) {
                    Toasty.info(getContext(), response.body().toString(), Toasty.LENGTH_SHORT).show();
                    mProgressBar.dismiss();
                }
                mProgressBar.dismiss();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toasty.info(getContext(), t.getMessage(), Toasty.LENGTH_SHORT).show();
                mProgressBar.dismiss();
            }
        });
    }

    @OnClick(R.id.fragment_reg_reg_btn)
    void logIn() {
        if (validateLoginInput() & validatePasswordInput() & validatePatronymicInput() & validateFirstNameInput() & validateLastNameInput() & validateEmailInput()) {
            mProgressBar.setMessage("Checking...");
            mProgressBar.show();

            regUser(userNameTIL.getEditText().getText().toString(),
                    patronymicTIL.getEditText().getText().toString(),
                    firstNameTIL.getEditText().getText().toString(),
                    lastNameTIL.getEditText().getText().toString(),
                    emailTIL.getEditText().getText().toString(),
                    passwordTIL.getEditText().getText().toString());

//            Toasty.info(getContext(), "reg", Toasty.LENGTH_SHORT).show();

        }
    }

}
