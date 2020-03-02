package com.grin.appforuniver.data.service;

import com.google.gson.GsonBuilder;
import com.grin.appforuniver.data.api.UserApi;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.tools.AuthInterceptor;
import com.grin.appforuniver.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private Call call;

    public void requestCurrentUserProfile(OnRequestCurrentUserProfileListener l) {
        Call<User> getCurrentUserAccount = buildApi(buildClient()).getMyAccount();
        getCurrentUserAccount.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (l != null) {
                    l.onRequestCurrentUserProfileSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (l != null) {
                    l.onRequestCurrentUserProfileFailed(call, t);
                }
            }
        });
        call = getCurrentUserAccount;
    }

    public void requestCurrentUserStudentProfile(OnRequestCurrentUserStudentProfileListener l) {
        Call<User> getCurrentUserStudentProfile = buildApi(buildClient()).getMyAccountStudent();
        getCurrentUserStudentProfile.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (l != null) {
                    l.onRequestCurrentUserStudentProfileSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (l != null) {
                    l.onRequestCurrentUserStudentProfileFailed(call, t);
                }
            }
        });
        call = getCurrentUserStudentProfile;
    }

    public void requestCurrentUserProfessorProfile(OnRequestCurrentUserProfessorProfileListener l) {
        Call<Professors> getCurrentUserProfessorProfile = buildApi(buildClient()).getMyAccountProfessor();
        getCurrentUserProfessorProfile.enqueue(new Callback<Professors>() {
            @Override
            public void onResponse(Call<Professors> call, Response<Professors> response) {
                if (l != null) {
                    l.onRequestCurrentUserProfessorProfileSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Professors> call, Throwable t) {
                if (l != null) {
                    l.onRequestCurrentUserProfessorProfileFailed(call, t);
                }
            }
        });
        call = getCurrentUserProfessorProfile;
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

    public static UserService getService() {
        return new UserService();
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    private UserApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .create()))
                .build()
                .create((UserApi.class));
    }

    public interface OnRequestCurrentUserProfileListener {
        void onRequestCurrentUserProfileSuccess(Call<User> call, Response<User> response);

        void onRequestCurrentUserProfileFailed(Call<User> call, Throwable t);
    }

    public interface OnRequestCurrentUserStudentProfileListener {
        void onRequestCurrentUserStudentProfileSuccess(Call<User> call, Response<User> response);

        void onRequestCurrentUserStudentProfileFailed(Call<User> call, Throwable t);
    }

    public interface OnRequestCurrentUserProfessorProfileListener {
        void onRequestCurrentUserProfessorProfileSuccess(Call<Professors> call, Response<Professors> response);

        void onRequestCurrentUserProfessorProfileFailed(Call<Professors> call, Throwable t);
    }
}
