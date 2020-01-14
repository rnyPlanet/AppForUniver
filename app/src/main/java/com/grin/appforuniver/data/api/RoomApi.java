package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.model.schedule.Rooms;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RoomApi {

    @GET("room/all")
    Call<List<Rooms>> getRooms();
}
