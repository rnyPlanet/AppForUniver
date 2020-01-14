package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.model.schedule.Groups;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GroupApi {
    @GET("group/all")
    Call<List<Groups>> getAllGroups();
}
