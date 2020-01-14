package com.grin.appforuniver.data.service;

import com.google.gson.GsonBuilder;
import com.grin.appforuniver.data.api.ConsultationApi;
import com.grin.appforuniver.data.model.consultation.Consultation;
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

public class ConsultationService {

    public void requestAllConsultation() {
        Call<List<Consultation>> getAllConsultation = buildApi(buildClient()).getAllConsultations();
        getAllConsultation.enqueue(new Callback<List<Consultation>>() {
            @Override
            public void onResponse(Call<List<Consultation>> call, Response<List<Consultation>> response) {

            }

            @Override
            public void onFailure(Call<List<Consultation>> call, Throwable t) {

            }
        });
    }

    public void requestMyConsultation() {
        Call<List<Consultation>> getMyConsultation = buildApi(buildClient()).getMyConsultation();
        getMyConsultation.enqueue(new Callback<List<Consultation>>() {
            @Override
            public void onResponse(Call<List<Consultation>> call, Response<List<Consultation>> response) {

            }

            @Override
            public void onFailure(Call<List<Consultation>> call, Throwable t) {

            }
        });
    }

    public static ConsultationService getService() {
        return new ConsultationService();
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

    private ConsultationApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .create()))
                .build()
                .create((ConsultationApi.class));
    }
}
