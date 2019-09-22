package com.grin.appforuniver.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ConsultationInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.UserInterface;
import com.grin.appforuniver.data.model.consultation.Сonsultation;
import com.grin.appforuniver.data.utils.PreferenceUtils;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import kotlin.jvm.functions.Function4;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationFragment extends Fragment {

    public final String TAG = ConsultationFragment.class.getSimpleName();

    private View mView;
    private Unbinder mUnbinder;

    private ItemAdapter<Сonsultation> mItemAdapter = new ItemAdapter<>();
    private FastAdapter mFastAdapter = null;

    @BindView(R.id.fragment_consultation_rv) RecyclerView recyclerView;
    @BindView(R.id.fragment_consultation_fab) FloatingActionButton floatingActionButton;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_consultation, container, false);
        getActivity().setTitle(R.string.menu_consultation);
        mUnbinder = ButterKnife.bind(this, mView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mFastAdapter = FastAdapter.with(mItemAdapter);

        recyclerView.setAdapter(mFastAdapter);

        getConsultation();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && floatingActionButton.getVisibility() == View.VISIBLE) {
                    floatingActionButton.hide();
                } else if (dy < 0 && floatingActionButton.getVisibility() != View.VISIBLE) {
                    floatingActionButton.show();
                }
            }
        });

        if(PreferenceUtils.getUserRoles(getContext()).contains("ROLE_TEACHER")) {
            floatingActionButton.setVisibility(View.VISIBLE);
        }


        return mView;
    }

    private void getConsultation() {

        ConsultationInterface consultationInterface = ServiceGenerator.createService(ConsultationInterface.class);

        Call<List<Сonsultation>> call = consultationInterface.getMyConsultation(PreferenceUtils.getUserToken(getContext()));
        call.enqueue(new Callback<List<Сonsultation>>() {
            @Override
            public void onResponse(Call<List<Сonsultation>> call, Response<List<Сonsultation>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mItemAdapter.add(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Сonsultation>> call, Throwable t) {
                Toasty.error(getContext(), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
