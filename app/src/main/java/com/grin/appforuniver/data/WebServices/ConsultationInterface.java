package com.grin.appforuniver.data.WebServices;


import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationUpdateRequestDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ConsultationInterface {

    @GET("consultation/all")
    Call<List<Consultation>> getAllConsultations();

    @GET("consultation/myConsultation")
    Call<List<Consultation>> getMyConsultation();

    @GET("consultation/subscribe")
    Call<List<Consultation>> getSubscribeConsultations();

    @GET("consultation/isCanUpdate/{id}")
    Call<Boolean> isCanUpdateConsultation(@Path("id") int id);

    @GET("consultation/{id}")
    Call<Consultation> getConsultationById(@Path("id") int id);

    @GET("consultation/isCanSubscribe/{id}")
    Call<Boolean> isCanSubscribe(@Path("id") int id);


    @PUT("consultation/update")
    Call<Consultation> updateConsultation(@Body ConsultationUpdateRequestDto consultationUpdateRequestDto);

    @POST("consultation/addСonsultation")
    Call<Consultation> createConsultation(@Body ConsultationUpdateRequestDto consultationUpdateRequestDto);

    @POST("consultation/follow/{id}")
    Call<Boolean> subscribeOnConsultationById(@Path("id") int id);

    @PUT("consultation/unfollow/{id}")
    Call<Boolean> unsubscribeOnConsultationById(@Path("id") int id);


}
