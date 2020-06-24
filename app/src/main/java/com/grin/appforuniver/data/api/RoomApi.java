package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.models.Rooms;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RoomApi {

    @GET("rooms/{id}")
    Call<Rooms> getRoomById(@Path("id") int id);

    @GET("rooms/all")
    Call<List<Rooms>> getRooms();
}
