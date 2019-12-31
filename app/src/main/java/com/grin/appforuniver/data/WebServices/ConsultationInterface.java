package com.grin.appforuniver.data.WebServices;

import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationRequestDto;

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

    @GET("consultation/my")
    Call<List<Consultation>> getMyConsultation();

    @GET("consultation/mySubscriptions")
    Call<List<Consultation>> mySubscriptions();

    @GET("consultation/isCanUpdate/{id}")
    Call<Boolean> isCanUpdateConsultation(@Path("id") int id);

    @GET("consultation/{id}")
    Call<Consultation> getConsultationById(@Path("id") int id);

    @GET("consultation/isCanSubscribe/{id}")
    Call<Boolean> isCanSubscribe(@Path("id") int id);

    @POST("consultation/subscribe/{id}")
    Call<Void> subscribeOnConsultationById(@Path("id") int id);

    @PUT("consultation/unsubscribe/{id}")
    Call<Void> unsubscribeOnConsultationById(@Path("id") int id);


    @PUT("consultation/update/{id}")
    Call<Void> updateConsultation(@Path("id") int id, @Body ConsultationRequestDto consultationRequestDto);

    @POST("consultation/create")
    Call<Consultation> createConsultation(@Body ConsultationRequestDto consultationRequestDto);

    @PUT("consultation/delete/{id}")
    Call<Void> delete(@Path("id") int id);


}
