package com.grin.appforuniver.data.service;

import com.google.gson.GsonBuilder;
import com.grin.appforuniver.data.api.GroupApi;
import com.grin.appforuniver.data.models.Groups;
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

public class GroupService {
    private static final String TAG = GroupService.class.getSimpleName();

    public void requestGroupById(int id, OnRequestGroupListener l) {
        Call<Groups> getGroupById = buildApi(buildClient()).getGroupById(id);
        getGroupById.enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(Call<Groups> call, Response<Groups> response) {
                if (l != null) {
                    l.onRequestGroupSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Groups> call, Throwable t) {
                if (l != null) {
                    l.onRequestGroupFailed(call, t);
                }
            }
        });
    }

    public void requestAllGroups(final OnRequestGroupListListener l) {
        Call<List<Groups>> getAllGroups = buildApi(buildClient()).getAllGroups();
        getAllGroups.enqueue(new Callback<List<Groups>>() {
            @Override
            public void onResponse(Call<List<Groups>> call, Response<List<Groups>> response) {
                if (l != null) {
                    l.onRequestGroupListSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Groups>> call, Throwable t) {
                if (l != null) {
                    l.onRequestGroupListFailed(call, t);
                }
            }
        });
    }

    public static GroupService getService() {
        return new GroupService();
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(AuthInterceptor.getInstance())
                .build();
    }

    private GroupApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .create()))
                .build()
                .create((GroupApi.class));
    }

    public interface OnRequestGroupListener {
        void onRequestGroupSuccess(Call<Groups> call, Response<Groups> response);

        void onRequestGroupFailed(Call<Groups> call, Throwable t);
    }

    public interface OnRequestGroupListListener {
        void onRequestGroupListSuccess(Call<List<Groups>> call, Response<List<Groups>> response);

        void onRequestGroupListFailed(Call<List<Groups>> call, Throwable t);
    }
}
