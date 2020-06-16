package com.grin.appforuniver.data.api;

import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationRequestDto;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ConsultationApi {

    @GET("consultations/{id}")
    Call<Consultation> getConsultationById(@Path("id") int id);

    @GET("consultations/all")
    Call<List<Consultation>> getAllConsultations();

    @GET("consultations/my")
    Call<List<Consultation>> getMyConsultation();

    @GET("consultations/mySubscriptions")
    Call<List<Consultation>> mySubscriptions();

    @POST("consultations/{id}/subscribe")
    Call<Void> subscribeOnConsultationById(@Path("id") int id);

    @PUT("consultations/{id}/unsubscribe")
    Call<Void> unsubscribeOnConsultationById(@Path("id") int id);

    @POST("consultations/create")
    Call<Consultation> createConsultation(@Body ConsultationRequestDto consultationRequestDto);

    @PUT("consultations/{id}/update")
    Call<Void> updateConsultation(@Path("id") int id, @Body ConsultationRequestDto consultationRequestDto);

    @DELETE("consultations/{id}/delete")
    Call<Void> deleteConsultation(@Path("id") int id);

    @GET("consultations/{id}/statusConsultation")
    Call<Map<Object, Boolean>> statusConsultation(@Path("id") int id);
}
