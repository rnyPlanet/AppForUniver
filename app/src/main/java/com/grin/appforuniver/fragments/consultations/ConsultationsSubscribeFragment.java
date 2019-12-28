package com.grin.appforuniver.fragments.consultations;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.grin.appforuniver.R;
import com.grin.appforuniver.activity.ConsultationActivity;
import com.grin.appforuniver.data.WebServices.ConsultationInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationsSubscribeFragment extends Fragment {

    public final String TAG = ConsultationsSubscribeFragment.class.getSimpleName();

    private View mView;
    private Unbinder mUnbinder;

    private ItemAdapter<Consultation> mItemAdapter = new ItemAdapter<>();
    private FastAdapter mFastAdapter = null;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_consultations_subscribe, container, false);

        recyclerView = mView.findViewById(R.id.fragment_consultations_subscribe_rv);
        progressBar = mView.findViewById(R.id.fragment_consultations_subscribe_pb);

        mUnbinder = ButterKnife.bind(this, mView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVisibility(View.INVISIBLE);

        mFastAdapter = FastAdapter.with(mItemAdapter);

        mFastAdapter.setOnClickListener((mView, adapter, item, position) -> {
                    Intent intent = new Intent(getContext(), ConsultationActivity.class);
                    intent.putExtra("Consultation", new Gson().toJson(item));
                    startActivity(intent);
                    return false;
                }
        );

        recyclerView.setAdapter(mFastAdapter);
        return mView;
    }

    private void getSubscribeConsultations() {

        ConsultationInterface consultationInterface = ServiceGenerator.createService(ConsultationInterface.class);

        Call<List<Consultation>> call = consultationInterface.mySubscriptions();
        call.enqueue(new Callback<List<Consultation>>() {
            @Override
            public void onResponse(@NonNull Call<List<Consultation>> call, @NonNull Response<List<Consultation>> response) {
                if (response.isSuccessful()) {
                    mItemAdapter.clear();
                    if (response.body() != null) {
                        mItemAdapter.add(response.body());
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Consultation>> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        getSubscribeConsultations();
    }

}
