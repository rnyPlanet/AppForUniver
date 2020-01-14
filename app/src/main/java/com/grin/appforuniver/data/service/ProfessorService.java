package com.grin.appforuniver.data.service;

import com.google.gson.GsonBuilder;
import com.grin.appforuniver.data.api.ProfessorApi;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.tools.AuthInterceptor;
import com.grin.appforuniver.utils.Constants;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfessorService {

    public void requestProfessorById(int id, OnRequestProfessorListener l) {
        Call<Professors> getProfessorById = buildApi(buildClient()).getProfessorById(id);
        getProfessorById.enqueue(new Callback<Professors>() {
            @Override
            public void onResponse(Call<Professors> call, Response<Professors> response) {
                if (l != null) {
                    l.onRequestProfessorSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Professors> call, Throwable t) {
                if (l != null) {
                    l.onRequestProfessorFailed(call, t);
                }
            }
        });
    }

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
                .addInterceptor(new AuthInterceptor())
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

    public interface OnRequestProfessorListener {
        void onRequestProfessorSuccess(Call<Professors> call, Response<Professors> response);

        void onRequestProfessorFailed(Call<Professors> call, Throwable t);
    }

    public interface OnRequestProfessorListListener {
        void onRequestProfessorListSuccess(Call<List<Professors>> call, Response<List<Professors>> response);

        void onRequestProfessorListFailed(Call<List<Professors>> call, Throwable t);
    }
}
