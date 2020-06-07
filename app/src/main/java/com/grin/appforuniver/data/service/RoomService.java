package com.grin.appforuniver.data.service;

import com.google.gson.GsonBuilder;
import com.grin.appforuniver.data.api.RoomApi;
import com.grin.appforuniver.data.model.schedule.Rooms;
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

public class RoomService {
    private static final String TAG = RoomService.class.getSimpleName();

    public void requestRoomById(int id, OnRequestRoomListener l) {
        Call<Rooms> getRoomById = buildApi(buildClient()).getRoomById(id);
        getRoomById.enqueue(new Callback<Rooms>() {
            @Override
            public void onResponse(Call<Rooms> call, Response<Rooms> response) {
                if (l != null) {
                    l.onRequestRoomSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Rooms> call, Throwable t) {
                if (l != null) {
                    l.onRequestRoomFailed(call, t);
                }
            }
        });
    }

    public void requestAllRooms(final OnRequestRoomListListener l) {
        Call<List<Rooms>> getAllRooms = buildApi(buildClient()).getRooms();
        getAllRooms.enqueue(new Callback<List<Rooms>>() {
            @Override
            public void onResponse(Call<List<Rooms>> call, Response<List<Rooms>> response) {
                if (l != null) {
                    l.onRequestRoomListSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Rooms>> call, Throwable t) {
                if (l != null) {
                    l.onRequestRoomListFailed(call, t);
                }
            }
        });
    }

    public static RoomService getService() {
        return new RoomService();
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(AuthInterceptor.getInstance())
                .build();
    }

    private RoomApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .create()))
                .build()
                .create((RoomApi.class));
    }

    public interface OnRequestRoomListener {
        void onRequestRoomSuccess(Call<Rooms> call, Response<Rooms> response);

        void onRequestRoomFailed(Call<Rooms> call, Throwable t);
    }

    public interface OnRequestRoomListListener {
        void onRequestRoomListSuccess(Call<List<Rooms>> call, Response<List<Rooms>> response);

        void onRequestRoomListFailed(Call<List<Rooms>> call, Throwable t);
    }
}
