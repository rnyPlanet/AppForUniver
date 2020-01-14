package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.model.schedule.Groups;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GroupApi {

    @GET("groups/{id}")
    Call<Groups> getGroupById(@Path("id") int id);

    @GET("groups/all")
    Call<List<Groups>> getAllGroups();
}
