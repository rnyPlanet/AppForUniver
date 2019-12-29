package com.grin.appforuniver.data.WebServices;

import com.grin.appforuniver.data.model.schedule.Groups;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GroupInterface {
    @GET("group/all")
    Call<List<Groups>> getAllGroups();
}
