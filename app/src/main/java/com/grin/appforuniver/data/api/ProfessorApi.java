package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.model.schedule.Professors;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfessorApi {
    @GET("professor/all")
    Call<List<Professors>> getProfessors();

    @GET("professor/{id}/classes")
    Call<List<Classes>> getProfessorSchedule(@Path("id") int id);
}
