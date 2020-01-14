package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationRequestDto;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ConsultationApi {

    @GET("consultation/{id}")
    Call<Consultation> getConsultationById(@Path("id") int id);

    @GET("consultation/all")
    Call<List<Consultation>> getAllConsultations();

    @GET("consultation/my")
    Call<List<Consultation>> getMyConsultation();

    @GET("consultation/mySubscriptions")
    Call<List<Consultation>> mySubscriptions();

    @POST("consultation/{id}/subscribe")
    Call<Void> subscribeOnConsultationById(@Path("id") int id);

    @PUT("consultation/{id}/unsubscribe")
    Call<Void> unsubscribeOnConsultationById(@Path("id") int id);

    @POST("consultation/create")
    Call<Consultation> createConsultation(@Body ConsultationRequestDto consultationRequestDto);

    @PUT("consultation/{id}/update")
    Call<Void> updateConsultation(@Path("id") int id, @Body ConsultationRequestDto consultationRequestDto);

    @PUT("consultation/{id}/delete")
    Call<Void> deleteConsultation(@Path("id") int id);

    @GET("consultation/{id}/statusConsultation")
    Call<Map<Object, Boolean>> statusConsultation(@Path("id") int id);
}
