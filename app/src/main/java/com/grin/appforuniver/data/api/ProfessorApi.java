package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.model.schedule.Professors;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProfessorApi {

    @GET("professor/all")
    Call<List<Professors>> getProfessors();
}
