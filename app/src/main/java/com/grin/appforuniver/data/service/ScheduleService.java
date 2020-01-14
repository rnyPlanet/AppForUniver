package com.grin.appforuniver.data.service;

import com.google.gson.GsonBuilder;
import com.grin.appforuniver.data.api.ScheduleApi;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.tools.AuthInterceptor;
import com.grin.appforuniver.utils.Constants;
import com.grin.appforuniver.utils.PreferenceUtils;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScheduleService {

    public void requestScheduleByCriteria(int subject, String type, int professorId, int roomId, int groupId, String place, String week, int indexInDay, String additionalRequirements, final OnRequestScheduleListener l) {
        Call<List<Classes>> getScheduleByCriteria = buildApi(buildClient())
                .getScheduleByCriteria(subject, type, professorId, roomId, groupId, place, week, indexInDay, additionalRequirements);
        getScheduleByCriteria.enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (l != null) {
                    l.onRequestScheduleSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                if (l != null) {
                    l.onRequestScheduleFailed(call, t);
                }
            }
        });
    }

    public void requestScheduleCurrentUser(final OnRequestScheduleListener l) {
        Call<List<Classes>> getSchedule = buildApi(buildClient()).getScheduleCurrentUser();
        getSchedule.enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (l != null) {
                    l.onRequestScheduleSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                if (l != null) {
                    l.onRequestScheduleFailed(call, t);
                }
            }
        });
    }

    public static ScheduleService getService() {
        return new ScheduleService();
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    private ScheduleApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .create()))
                .build()
                .create((ScheduleApi.class));
    }

    public interface OnRequestScheduleListener {
        void onRequestScheduleSuccess(Call<List<Classes>> call, Response<List<Classes>> response);

        void onRequestScheduleFailed(Call<List<Classes>> call, Throwable t);
    }
}
