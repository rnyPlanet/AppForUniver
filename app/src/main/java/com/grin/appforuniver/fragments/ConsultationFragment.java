package com.grin.appforuniver.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.userInterface.UserInterface;
import com.grin.appforuniver.data.model.consultation.Сonsultation;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationFragment extends Fragment {

    public final String TAG = ConsultationFragment.class.getSimpleName();

    private View mView;
    private Unbinder mUnbinder;

    private List<Сonsultation> mConsultationList = new ArrayList<>();

//    @BindView(R.id.fragment_consultation_tv) TextView tv;
    @BindView(R.id.fragment_consultation_rv) RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_consultation, container, false);

        getActivity().setTitle(R.string.menu_consultation);

        mUnbinder = ButterKnife.bind(this, mView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getConsultation();

        return mView;
    }

    private void getConsultation() {

        UserInterface userInterface = ServiceGenerator.createService(UserInterface.class);

        Call<List<Сonsultation>> call = userInterface.getcConsultation(PreferenceUtils.getUserToken(getContext()));
        call.enqueue(new Callback<List<Сonsultation>>() {
            @Override
            public void onResponse(Call<List<Сonsultation>> call, Response<List<Сonsultation>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for (Сonsultation classes : response.body()) {
//                            stringBuilder.append(classes.toString());
//                        }
//                        tv.setText(stringBuilder);
//                        Log.d(TAG, "\n"+stringBuilder.toString());
                        mConsultationList.addAll(response.body());
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
