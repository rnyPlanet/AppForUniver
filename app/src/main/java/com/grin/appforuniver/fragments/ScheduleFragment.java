package com.grin.appforuniver.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ScheduleInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.utils.PreferenceUtils;
import com.grin.appforuniver.fragments.schedule.ScheduleDayFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_schedule, container, false);
        getActivity().setTitle(R.string.menu_schedule);
        getSchedule(mView.getContext(), inflater);
        return mView;
    }

    private void getSchedule(Context context, LayoutInflater inflater) {

        ScheduleInterface scheduleInterface = ServiceGenerator.createService(ScheduleInterface.class);
        int[] idContainers = new int[]{
                R.id.containerSaturday,
                R.id.containerMonday,
                R.id.containerTuesday,
                R.id.containerWednesday,
                R.id.containerThursday,
                R.id.containerFriday
        };
        int counter = 0;
        for (Classes.Place place : Classes.Place.values()) {
            Call<List<Classes>> call = scheduleInterface.getSchedulePlace(PreferenceUtils.getUserToken(context), place);
            int finalCounter = counter;
            call.enqueue(new Callback<List<Classes>>() {
                @Override
                public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                    if (response.isSuccessful())
                        if (response.body() != null) {
                            List<Classes> classes = new ArrayList<>(response.body());
                            logcatListClasses(classes, place);
                            getChildFragmentManager()
                                    .beginTransaction()
                                    .replace(idContainers[finalCounter], new ScheduleDayFragment(place, classes))
                                    .commit();
                        }
                }
                @Override
                public void onFailure(Call<List<Classes>> call, Throwable t) {
                    logcatFailureGetClasses(place, context, t);
                }
            });
            counter++;
        }
    }

    private void logcatListClasses(List<Classes> list, Classes.Place place) {
        for (Classes classes : list) {
            Log.d(TAG, place.toString() + '\t' + classes.toString());
        }
    }

    private void logcatFailureGetClasses(Classes.Place place, Context context, Throwable t) {
        String message = "ERROR: " + place.toString() + '\t' + Objects.requireNonNull(t.getMessage());
        Log.d(TAG, message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
