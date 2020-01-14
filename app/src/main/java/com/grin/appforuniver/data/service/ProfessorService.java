package com.grin.appforuniver.data.service;

import com.google.gson.GsonBuilder;
import com.grin.appforuniver.data.api.ProfessorApi;
import com.grin.appforuniver.data.model.schedule.Professors;
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

public class ProfessorService {

    public void requestAllProfessors(final OnRequestProfessorListListener l) {
        Call<List<Professors>> getAllProfessors = buildApi(buildClient()).getProfessors();
        getAllProfessors.enqueue(new Callback<List<Professors>>() {
            @Override
            public void onResponse(Call<List<Professors>> call, Response<List<Professors>> response) {
                if (l != null) {
                    l.onRequestProfessorListSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Professors>> call, Throwable t) {
                if (l != null) {
                    l.onRequestProfessorListFailed(call, t);
                }
            }
        });
    }

    public static ProfessorService getService() {
        return new ProfessorService();
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", (PreferenceUtils.getUserToken() == null) ? "" : PreferenceUtils.getUserToken())
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

    private ProfessorApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .create()))
                .build()
                .create((ProfessorApi.class));
    }

    public interface OnRequestProfessorListListener {
        void onRequestProfessorListSuccess(Call<List<Professors>> call, Response<List<Professors>> response);

        void onRequestProfessorListFailed(Call<List<Professors>> call, Throwable t);
    }
}
