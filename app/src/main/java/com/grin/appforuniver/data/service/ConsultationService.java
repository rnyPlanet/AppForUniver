package com.grin.appforuniver.data.service;

import com.google.gson.GsonBuilder;
import com.grin.appforuniver.data.api.ConsultationApi;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationRequestDto;
import com.grin.appforuniver.utils.Constants;
import com.grin.appforuniver.utils.PreferenceUtils;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationService {

    public void requestConsultationById(int id, final OnRequestConsultationListener l) {
        Call<Consultation> getConsultationById = buildApi(buildClient()).getConsultationById(id);
        getConsultationById.enqueue(new Callback<Consultation>() {
            @Override
            public void onResponse(Call<Consultation> call, Response<Consultation> response) {
                if (l != null) {
                    l.onRequestConsultationSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Consultation> call, Throwable t) {
                if (l != null) {
                    l.onRequestConsultationFailed(call, t);
                }
            }
        });
    }

    public void requestAllConsultation(final OnRequestConsultationListListener l) {
        Call<List<Consultation>> getAllConsultations = buildApi(buildClient()).getAllConsultations();
        getAllConsultations.enqueue(new Callback<List<Consultation>>() {
            @Override
            public void onResponse(Call<List<Consultation>> call, Response<List<Consultation>> response) {
                if (l != null) {
                    l.onRequestConsultationListSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Consultation>> call, Throwable t) {
                if (l != null) {
                    l.onRequestConsultationListFailed(call, t);
                }
            }
        });
    }

    public void requestMyConsultations(final OnRequestConsultationListListener l) {
        Call<List<Consultation>> getMyConsultations = buildApi(buildClient()).getMyConsultation();
        getMyConsultations.enqueue(new Callback<List<Consultation>>() {
            @Override
            public void onResponse(Call<List<Consultation>> call, Response<List<Consultation>> response) {
                if (l != null) {
                    l.onRequestConsultationListSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Consultation>> call, Throwable t) {
                if (l != null) {
                    l.onRequestConsultationListFailed(call, t);
                }
            }
        });
    }

    public void requestSubscribedConsultations(final OnRequestConsultationListListener l) {
        Call<List<Consultation>> getSubscribedConsultations = buildApi(buildClient()).mySubscriptions();
        getSubscribedConsultations.enqueue(new Callback<List<Consultation>>() {
            @Override
            public void onResponse(Call<List<Consultation>> call, Response<List<Consultation>> response) {
                if (l != null) {
                    l.onRequestConsultationListSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Consultation>> call, Throwable t) {
                if (l != null) {
                    l.onRequestConsultationListFailed(call, t);
                }
            }
        });
    }

    public void setSubscribeStatus(int id, boolean isSubscribe, final OnSetSubscribeStatusListener l) {
        Call<Void> setStatusSubscribe = isSubscribe ?
                buildApi(buildClient()).unsubscribeOnConsultationById(id) : buildApi(buildClient()).subscribeOnConsultationById(id);
        setStatusSubscribe.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (l != null) {
                    l.onSetSubscribeConsultationSuccess(!isSubscribe, call, response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (l != null) {
                    l.onSetSubscribeConsultationFailed(!isSubscribe, call, t);
                }
            }
        });
    }

    public void createConsultation(ConsultationRequestDto consultation, final OnCreateConsultationListener l) {
        Call<Consultation> createConsultation = buildApi(buildClient()).createConsultation(consultation);
        createConsultation.enqueue(new Callback<Consultation>() {
            @Override
            public void onResponse(Call<Consultation> call, Response<Consultation> response) {
                if (l != null) {
                    l.onCreateConsultationSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Consultation> call, Throwable t) {
                if (l != null) {
                    l.onCreateConsultationFailed(call, t);
                }
            }
        });
    }

    public void updateConsultation(int id, ConsultationRequestDto consultation, final OnUpdateConsultationListener l) {
        Call<Void> updateConsultation = buildApi(buildClient()).updateConsultation(id, consultation);
        updateConsultation.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (l != null) {
                    l.onUpdateConsultationSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (l != null) {
                    l.onUpdateConsultationFailed(call, t);
                }
            }
        });
    }

    public void deleteConsultation(int id, OnDeleteConsultationListener l) {
        Call<Void> deleteConsultation = buildApi(buildClient()).deleteConsultation(id);
        deleteConsultation.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (l != null) {
                    l.onDeleteConsultationSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (l != null) {
                    l.onDeleteConsultationFailed(call, t);
                }
            }
        });
    }

    public void requestStatusConsultation(int id, OnRequestStatusConsultationListener l) {
        Call<Map<Object, Boolean>> statusConsultation = buildApi(buildClient()).statusConsultation(id);
        statusConsultation.enqueue(new Callback<Map<Object, Boolean>>() {
            @Override
            public void onResponse(Call<Map<Object, Boolean>> call, Response<Map<Object, Boolean>> response) {
                if (l != null) {
                    l.onRequestStatusConsultationSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Map<Object, Boolean>> call, Throwable t) {
                if (l != null) {
                    l.onRequestStatusConsultationFailed(call, t);
                }
            }
        });
    }

    public static ConsultationService getService() {
        return new ConsultationService();
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", (PreferenceUtils.getUserToken() == null) ? "" : PreferenceUtils.getUserToken())
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

    private ConsultationApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .create()))
                .build()
                .create((ConsultationApi.class));
    }

    public interface OnRequestConsultationListListener {
        void onRequestConsultationListSuccess(Call<List<Consultation>> call, Response<List<Consultation>> response);

        void onRequestConsultationListFailed(Call<List<Consultation>> call, Throwable t);
    }

    public interface OnRequestConsultationListener {
        void onRequestConsultationSuccess(Call<Consultation> call, Response<Consultation> response);

        void onRequestConsultationFailed(Call<Consultation> call, Throwable t);
    }

    public interface OnSetSubscribeStatusListener {
        void onSetSubscribeConsultationSuccess(boolean statusSubscribe, Call<Void> call, Response<Void> response);

        void onSetSubscribeConsultationFailed(boolean statusSubscribe, Call<Void> call, Throwable t);
    }

    public interface OnCreateConsultationListener {
        void onCreateConsultationSuccess(Call<Consultation> call, Response<Consultation> response);

        void onCreateConsultationFailed(Call<Consultation> call, Throwable t);
    }

    public interface OnUpdateConsultationListener {
        void onUpdateConsultationSuccess(Call<Void> call, Response<Void> response);

        void onUpdateConsultationFailed(Call<Void> call, Throwable t);
    }

    public interface OnDeleteConsultationListener {
        void onDeleteConsultationSuccess(Call<Void> call, Response<Void> response);

        void onDeleteConsultationFailed(Call<Void> call, Throwable t);
    }

    public interface OnRequestStatusConsultationListener {
        void onRequestStatusConsultationSuccess(Call<Map<Object, Boolean>> call, Response<Map<Object, Boolean>> response);

        void onRequestStatusConsultationFailed(Call<Map<Object, Boolean>> call, Throwable t);
    }
}
