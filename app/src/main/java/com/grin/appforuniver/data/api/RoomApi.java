package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationRequestDto;
import com.grin.appforuniver.data.model.schedule.Rooms;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RoomApi {

    @GET("room/all")
    Call<List<Rooms>> getRooms();

}
