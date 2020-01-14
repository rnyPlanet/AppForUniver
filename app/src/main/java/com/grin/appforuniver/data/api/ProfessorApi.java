package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.model.schedule.Professors;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfessorApi {

    @GET("professors/{id}")
    Call<Professors> getProfessorById(@Path("id") int id);

    @GET("professors/all")
    Call<List<Professors>> getProfessors();
}
