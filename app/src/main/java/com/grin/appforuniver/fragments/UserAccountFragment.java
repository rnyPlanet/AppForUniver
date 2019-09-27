package com.grin.appforuniver.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.grin.appforuniver.R;
import com.grin.appforuniver.activity.LoginActivity;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.UserInterface;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAccountFragment extends Fragment {

    public final String TAG = UserAccountFragment.class.getSimpleName();

    @BindView(R.id.user_account_detail_progress)
    ProgressBar detail_progress;

    @BindView(R.id.user_account_userinfo_rl)
    ConstraintLayout userinfo_rl;

    @BindView(R.id.user_account_username_ll)
    LinearLayout username_ll;
    @BindView(R.id.user_account_username_tv)
    TextView username_tv;

    @BindView(R.id.user_account_email_ll)
    LinearLayout email_ll;
    @BindView(R.id.user_account_email_tv)
    TextView email_tv;

    @BindView(R.id.user_account_department_ll)
    LinearLayout department_ll;
    @BindView(R.id.user_account_department_tv)
    TextView department_tv;

    @BindView(R.id.user_account_posada_ll)
    LinearLayout posada_ll;
    @BindView(R.id.user_account_posada_tv)
    TextView posada_tv;

    @BindView(R.id.user_account_phone1_ll)
    LinearLayout telefon1_ll;
    @BindView(R.id.user_account_phone1_tv)
    TextView telefon1_tv;

    @BindView(R.id.material_text_button)
    Button logout_ll;
    //    LinearLayout logout_ll;
    private View mView;
    private Unbinder mUnbinder;
    private User mUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user_account, container, false);

        getActivity().setTitle(R.string.menu_user_account);

        mUnbinder = ButterKnife.bind(this, mView);

        getMe(mView.getContext());

        return mView;
    }

    private void getMe(Context context) {
        UserInterface userInterface = ServiceGenerator.createService(UserInterface.class);

        Call<User> call = userInterface.getMe(PreferenceUtils.getUserToken());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && !PreferenceUtils.getUserToken().isEmpty()) {
                        mUser = response.body();

                        if (mUser.getFirstName() != null && !mUser.getFirstName().isEmpty()) {
                            username_ll.setVisibility(View.VISIBLE);
                            username_tv.setText(mUser.getFirstName() + " " + mUser.getLastName());
                        }

                        if (mUser.getEmail() != null && !mUser.getEmail().isEmpty()) {
                            email_ll.setVisibility(View.VISIBLE);
                            email_tv.setText(mUser.getEmail());
                        }

                        if (mUser.getDepartment() != null) {
                            department_ll.setVisibility(View.VISIBLE);
                            department_tv.setText(mUser.getDepartment().getName());
                        } else {
                            department_ll.setVisibility(View.GONE);
                        }

                        if (mUser.getPosada() != null) {
                            posada_ll.setVisibility(View.VISIBLE);
                            posada_tv.setText(mUser.getPosada().getPostVykl());
                        } else {
                            posada_ll.setVisibility(View.GONE);
                        }

                        if (mUser.getTelefon1() != null && !mUser.getTelefon1().isEmpty()) {
                            telefon1_ll.setVisibility(View.VISIBLE);
                            telefon1_tv.setText(mUser.getTelefon1());
                        } else {
                            telefon1_ll.setVisibility(View.GONE);
                        }

                        detail_progress.setVisibility(View.GONE);
                        userinfo_rl.setVisibility(View.VISIBLE);
                        logout_ll.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toasty.error(context, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @OnClick(R.id.material_text_button)
    void onClickLogout() {
        PreferenceUtils.saveUsername(null);
        PreferenceUtils.savePassword(null);
        PreferenceUtils.saveUser(null);
        Intent intent = new Intent(mView.getContext(), LoginActivity.class);
        startActivity(intent);
        ((Activity) mView.getContext()).finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
