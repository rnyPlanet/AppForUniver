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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.userInterface.UserInterface;
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

        UserInterface userInterface = ServiceGenerator.createService(UserInterface.class);

//        Call<List<Classes>> callAll = userInterface.getScheduleAll(PreferenceUtils.getUserToken((context)));
        Call<List<Classes>> callMonday = userInterface.getScheduleMonday(PreferenceUtils.getUserToken((context)));
        Call<List<Classes>> callTuesday = userInterface.getScheduleTuesday(PreferenceUtils.getUserToken((context)));
        Call<List<Classes>> callWednesday = userInterface.getScheduleWednesday(PreferenceUtils.getUserToken((context)));
        Call<List<Classes>> callThursday = userInterface.getScheduleThursday(PreferenceUtils.getUserToken((context)));
        Call<List<Classes>> callFriday = userInterface.getScheduleFriday(PreferenceUtils.getUserToken((context)));
//        Call<List<Classes>> callSaturday = userInterface.getScheduleSaturday(PreferenceUtils.getUserToken((context)));

//        callAll.enqueue(new Callback<List<Classes>>() {
//            @Override
//            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
//                if (response.isSuccessful())
//                    if (response.body() != null) {
//                        List<Classes> classesAll = new ArrayList<>();
//                        classesAll.addAll(response.body());
//                        logcatListClasses(classesAll);
//                    }
//            }
//
//            @Override
//            public void onFailure(Call<List<Classes>> call, Throwable t) {
//
//            }
//        });
        callMonday.enqueue(new Callback<List<Classes>>() {
            Classes.Place place = Classes.Place.MONDAY;

            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.isSuccessful())
                    if (response.body() != null) {
                        List<Classes> classes = new ArrayList<>(response.body());
                        logcatListClasses(classes, place);
                        getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerMonday, new ScheduleDayFragment(place, classes))
                                .commit();
                    }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                logcatFailureGetClasses(place, context, t);
            }
        });
        callTuesday.enqueue(new Callback<List<Classes>>() {
            Classes.Place place = Classes.Place.TUESDAY;

            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.isSuccessful())
                    if (response.body() != null) {
                        List<Classes> classes = new ArrayList<>(response.body());
                        logcatListClasses(classes, place);
                        getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerTuesday, new ScheduleDayFragment(place, classes))
                                .commit();
                    }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                logcatFailureGetClasses(place, context, t);
            }
        });
        callWednesday.enqueue(new Callback<List<Classes>>() {
            Classes.Place place = Classes.Place.WEDNESDAY;

            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.isSuccessful())
                    if (response.body() != null) {
                        List<Classes> classes = new ArrayList<>(response.body());
                        logcatListClasses(classes, place);
                        getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerWednesday, new ScheduleDayFragment(place, classes))
                                .commit();
                    }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                logcatFailureGetClasses(place, context, t);
            }
        });
        callThursday.enqueue(new Callback<List<Classes>>() {
            Classes.Place place = Classes.Place.THURSDAY;

            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.isSuccessful())
                    if (response.body() != null) {
                        List<Classes> classes = new ArrayList<>(response.body());
                        logcatListClasses(classes, place);
                        getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerThursday, new ScheduleDayFragment(place, classes))
                                .commit();
                    }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                logcatFailureGetClasses(place, context, t);
            }
        });
        callFriday.enqueue(new Callback<List<Classes>>() {
            Classes.Place place = Classes.Place.FRIDAY;

            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.isSuccessful())
                    if (response.body() != null) {
                        List<Classes> classes = new ArrayList<>(response.body());
                        logcatListClasses(classes, place);
                        getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerFriday, new ScheduleDayFragment(place, classes))
                                .commit();
                    }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                logcatFailureGetClasses(place, context, t);
            }
        });
//        callSaturday.enqueue(new Callback<List<Classes>>() {
//            @Override
//            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Classes>> call, Throwable t) {
//
//            }
//        });
    }

    private void logcatListClasses(List<Classes> list, Classes.Place place) {
        for (Classes classes : list) {
//            Log.d(TAG, place.toString() + '\t' + classes.toString());
        }
    }

    private void logcatFailureGetClasses(Classes.Place place, Context context, Throwable t) {
        String message = "ERROR: " + place.toString() + '\t' + Objects.requireNonNull(t.getMessage());
        Log.d(TAG, message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
