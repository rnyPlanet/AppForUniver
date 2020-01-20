package com.grin.appforuniver.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.grin.appforuniver.R;
import com.grin.appforuniver.activities.LoginActivity;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.service.UserService;
import com.grin.appforuniver.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;

import static com.grin.appforuniver.utils.Constants.Roles.ROLE_TEACHER;

public class UserAccountFragment extends Fragment {

    public final String TAG = UserAccountFragment.class.getSimpleName();
    private UserService mUserService;
    @BindView(R.id.user_account_detail_progress)
    ProgressBar detail_progress;

    @BindView(R.id.user_account_userinfo_rl)
    ConstraintLayout userinfo_rl;

    @BindView(R.id.user_account_header)
    ImageView user_account_header;

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

    @BindView(R.id.user_account_detail_log_out_button)
    Button logout_btn;

    private View mView;
    private Unbinder mUnbinder;
    private User mUser;
    private Professors mProfessor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mUserService = UserService.getService();
        mView = inflater.inflate(R.layout.fragment_user_account, container, false);

        Objects.requireNonNull(getActivity()).setTitle(R.string.menu_user_account);

        mUnbinder = ButterKnife.bind(this, mView);

        getMyAccount(mView.getContext());

        return mView;
    }

    private void getMyAccount(Context context) {
        mUserService.requestCurrentUserProfile(new UserService.OnRequestCurrentUserProfileListener() {
            @Override
            public void onRequestCurrentUserProfileSuccess(Call<User> call, Response<User> response) {
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

                        if (PreferenceUtils.getUserRoles().contains(ROLE_TEACHER.toString())) {
                            getMyAccountProfessor(context);
                        }

                        if (mUser.getTelefon1() != null && !mUser.getTelefon1().isEmpty()) {
                            telefon1_ll.setVisibility(View.VISIBLE);
                            telefon1_tv.setText(mUser.getTelefon1());
                        } else {
                            telefon1_ll.setVisibility(View.GONE);
                        }

                        detail_progress.setVisibility(View.GONE);
                        userinfo_rl.setVisibility(View.VISIBLE);
                        logout_btn.setVisibility(View.VISIBLE);
                        user_account_header.setVisibility(View.VISIBLE);
                        email_ll.setVisibility(View.VISIBLE);
                        telefon1_ll.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onRequestCurrentUserProfileFailed(Call<User> call, Throwable t) {
                Toasty.error(context, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void getMyAccountProfessor(Context context) {
        mUserService.requestCurrentUserProfessorProfile(new UserService.OnRequestCurrentUserProfessorProfileListener() {
            @Override
            public void onRequestCurrentUserProfessorProfileSuccess(Call<Professors> call, Response<Professors> response) {
                if (response.body() != null && !PreferenceUtils.getUserToken().isEmpty()) {
                    mProfessor = response.body();

                    if (mProfessor.getPosada() != null) {
                        posada_ll.setVisibility(View.VISIBLE);
                        posada_tv.setText(mProfessor.getPosada().getFullPostProfessor());
                    } else {
                        posada_ll.setVisibility(View.GONE);
                    }

                    if (mProfessor.getDepartment() != null) {
                        department_ll.setVisibility(View.VISIBLE);
                        department_tv.setText(mProfessor.getDepartment().getName());
                    } else {
                        department_ll.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onRequestCurrentUserProfessorProfileFailed(Call<Professors> call, Throwable t) {
                Toasty.error(context, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void getMyAccountStudent(Context context) {
    }

    @OnClick(R.id.user_account_detail_log_out_button)
    void onClickLogout() {
        logOut();

        Objects.requireNonNull(getActivity()).startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void logOut() {
        PreferenceUtils.saveUsername(null);
        PreferenceUtils.savePassword(null);
        PreferenceUtils.saveUser(null);
        PreferenceUtils.saveUserRoles(new ArrayList<>());
        PreferenceUtils.saveUserToken(null);
    }

}
