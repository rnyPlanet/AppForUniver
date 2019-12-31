package com.grin.appforuniver.data.WebServices;

import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.model.schedule.Professors;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfessorInterface {
    @GET("professor/all")
    Call<List<Professors>> getProfessors();

    @GET("professor/classes/{id}")
    Call<List<Classes>> getProfessorSchedule(@Path("id") int id);
}
