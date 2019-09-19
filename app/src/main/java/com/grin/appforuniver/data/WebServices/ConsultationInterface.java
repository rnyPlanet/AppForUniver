package com.grin.appforuniver.data.WebServices;


import com.grin.appforuniver.data.model.consultation.Сonsultation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ConsultationInterface {

    @GET("consultation/all")
    Call<List<Сonsultation>> getcConsultation(@Header("Authorization") String token);

}
