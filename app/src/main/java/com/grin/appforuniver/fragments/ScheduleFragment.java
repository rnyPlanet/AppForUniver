package com.grin.appforuniver.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {
    private View mView;
    private List<Classes> mListClasses = new ArrayList<>();
    private ConstraintLayout[] pairs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_schedule, container, false);
        pairs = new ConstraintLayout[]{
                mView.findViewById(R.id.pair), mView.findViewById(R.id.pair2),
                mView.findViewById(R.id.pair3), mView.findViewById(R.id.pair4),
                mView.findViewById(R.id.pair5), mView.findViewById(R.id.pair6)
        };
        getActivity().setTitle(R.string.menu_schedule);
        getSchedule(mView.getContext(), inflater);
        return mView;
    }


    private void getSchedule(Context context, LayoutInflater inflater) {
        UserInterface userInterface = ServiceGenerator.createService(UserInterface.class);

        Call<List<Classes>> call = userInterface.getSchedule(PreferenceUtils.getUserToken(context));
        call.enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mListClasses.addAll(response.body());
                        setDataInLayout(inflater);

//                        StringBuilder stringBuilder = new StringBuilder();
//                        for (Classes classes : mListClasses) {
//                            stringBuilder.append(classes.toString());
//                        }
//                        Log.d("ScheduleFragment", "\n"+stringBuilder.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                Toasty.error(context, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private boolean bothSubgroup_firstWeek;
    private boolean bothSubgroup_secondWeek;
    private boolean bothSubgroup_bothWeek;
    private boolean firstSubgroup_firstWeek;
    private boolean firstSubgroup_secondWeek;
    private boolean firstSubgroup_bothWeek;
    private boolean secondSubgroup_firstWeek;
    private boolean secondSubgroup_secondWeek;
    private boolean secondSubgroup_bothWeek;

    private String bothSubgroup_firstWeekStr;
    private String bothSubgroup_secondWeekStr;
    private String bothSubgroup_bothWeekStr;
    private String firstSubgroup_firstWeekStr;
    private String firstSubgroup_secondWeekStr;
    private String firstSubgroup_bothWeekStr;
    private String secondSubgroup_firstWeekStr;
    private String secondSubgroup_secondWeekStr;
    private String secondSubgroup_bothWeekStr;


    private void setDataInLayout(LayoutInflater inflater) {
        for (int i = 0; i < 6; i++) {
            bothSubgroup_firstWeek = false;
            bothSubgroup_secondWeek = false;
            bothSubgroup_bothWeek = false;
            firstSubgroup_firstWeek = false;
            firstSubgroup_secondWeek = false;
            firstSubgroup_bothWeek = false;
            secondSubgroup_firstWeek = false;
            secondSubgroup_secondWeek = false;
            secondSubgroup_bothWeek = false;


            for (Classes classes : mListClasses) {
                if (classes.getPlace() == Classes.Place.MONDAY) {
                    if (classes.getIndexInDay() == i) {
//                        if (classes.getSubgroup() == Classes.Subgroup.BOTH && classes.getWeek() == Classes.Week.BOTH) {
//                            bothSubgroup_bothWeek = true;
//                            bothSubgroup_bothWeek = classes.getSubject();
//                        }
                        if (classes.getSubgroup() == Classes.Subgroup.BOTH && classes.getWeek() == Classes.Week.FIRST) {
                            bothSubgroup_firstWeek = true;
                            bothSubgroup_firstWeekStr = classes.getSubject();
                        }
                        if (classes.getSubgroup() == Classes.Subgroup.BOTH && classes.getWeek() == Classes.Week.SECOND) {
                            bothSubgroup_secondWeek = true;
                            bothSubgroup_secondWeekStr = classes.getSubject();
                        }
//                        if (classes.getSubgroup() == Classes.Subgroup.FIRST && classes.getWeek() == Classes.Week.BOTH) {
//                            firstSubgroup_bothWeek = true;
//                            firstSubgroup_bothWeekStr = classes.getSubject();
//                        }
                        if (classes.getSubgroup() == Classes.Subgroup.FIRST && classes.getWeek() == Classes.Week.FIRST) {
                            firstSubgroup_firstWeek = true;
                            firstSubgroup_firstWeekStr = classes.getSubject();
                        }
                        if (classes.getSubgroup() == Classes.Subgroup.FIRST && classes.getWeek() == Classes.Week.SECOND) {
                            firstSubgroup_secondWeek = true;
                            firstSubgroup_secondWeekStr = classes.getSubject();
                        }
//                        if (classes.getSubgroup() == Classes.Subgroup.SECOND && classes.getWeek() == Classes.Week.BOTH) {
//                            secondSubgroup_bothWeek = true;
//                            secondSubgroup_bothWeekStr = classes.getSubject();
//                        }
                        if (classes.getSubgroup() == Classes.Subgroup.SECOND && classes.getWeek() == Classes.Week.FIRST) {
                            secondSubgroup_firstWeek = true;
                            secondSubgroup_firstWeekStr = classes.getSubject();
                        }
                        if (classes.getSubgroup() == Classes.Subgroup.SECOND && classes.getWeek() == Classes.Week.SECOND) {
                            secondSubgroup_secondWeek = true;
                            secondSubgroup_secondWeekStr = classes.getSubject();
                        }
                        Log.d("ScheduleFragment", "----");
                        Log.d("ScheduleFragment", "bothSubgroup_firstWeek: " + bothSubgroup_firstWeek);
                        Log.d("ScheduleFragment", "bothSubgroup_secondWeek: " + bothSubgroup_secondWeek);
                        Log.d("ScheduleFragment", "bothSubgroup_bothWeek: " + bothSubgroup_bothWeek);
                        Log.d("ScheduleFragment", "firstSubgroup_firstWeek: " + firstSubgroup_firstWeek);
                        Log.d("ScheduleFragment", "firstSubgroup_secondWeek: " + firstSubgroup_secondWeek);
                        Log.d("ScheduleFragment", "firstSubgroup_bothWeek: " + firstSubgroup_bothWeek);
                        Log.d("ScheduleFragment", "secondSubgroup_firstWeek: " + secondSubgroup_firstWeek);
                        Log.d("ScheduleFragment", "secondSubgroup_secondWeek: " + secondSubgroup_secondWeek);
                        Log.d("ScheduleFragment", "secondSubgroup_bothWeek: " + secondSubgroup_bothWeek);
                        Log.d("ScheduleFragment", "Data: " + classes.toString());


                    }
                }
            }
            int layoutId = getLayoutId();
            if (layoutId == R.layout.schedule_single_type_9) {
                inflater.inflate(R.layout.schedule_single_type_9, pairs[i]);
                TextView number_pair = pairs[i].findViewById(R.id.number_pair);
                number_pair.setText(String.valueOf(i + 1));
            } else {
                inflater.inflate(layoutId, pairs[i]);
                TextView number_pair = pairs[i].findViewById(R.id.number_pair);
                number_pair.setText(String.valueOf(i + 1));

                if (bothSubgroup_firstWeek) {
                    TextView bothSubgroup_firstWeek = pairs[i].findViewById(R.id.both_group_first_week);
                    bothSubgroup_firstWeek.setText(bothSubgroup_firstWeekStr);
                }
                if (bothSubgroup_secondWeek) {
                    TextView bothSubgroup_secondWeek = pairs[i].findViewById(R.id.both_group_second_week);
                    bothSubgroup_secondWeek.setText(bothSubgroup_secondWeekStr);
                }
                if (bothSubgroup_bothWeek) {
                    TextView bothSubgroup_bothWeek = pairs[i].findViewById(R.id.both_group_both_week);
                    bothSubgroup_bothWeek.setText(bothSubgroup_bothWeekStr);
                }
                if (firstSubgroup_firstWeek) {
                    TextView firstSubgroup_firstWeek = pairs[i].findViewById(R.id.first_subgroup_first_week);
                    firstSubgroup_firstWeek.setText(firstSubgroup_firstWeekStr);
                }
                if (firstSubgroup_secondWeek) {
                    TextView firstSubgroup_secondWeek = pairs[i].findViewById(R.id.first_subgroup_second_week);
                    firstSubgroup_secondWeek.setText(firstSubgroup_secondWeekStr);
                }
                if (firstSubgroup_bothWeek) {
                    TextView firstSubgroup_bothWeek = pairs[i].findViewById(R.id.first_subgroup_both_week);
                    firstSubgroup_bothWeek.setText(firstSubgroup_bothWeekStr);
                }
                if (secondSubgroup_firstWeek) {
                    TextView secondSubgroup_firstWeek = pairs[i].findViewById(R.id.second_subgroup_first_week);
                    secondSubgroup_firstWeek.setText(secondSubgroup_firstWeekStr);
                }
                if (secondSubgroup_secondWeek) {
                    TextView secondSubgroup_secondWeek = pairs[i].findViewById(R.id.second_subgroup_second_week);
                    secondSubgroup_secondWeek.setText(secondSubgroup_secondWeekStr);
                }
                if (secondSubgroup_bothWeek) {
                    TextView secondSubgroup_bothWeek = pairs[i].findViewById(R.id.second_subgroup_both_week);
                    secondSubgroup_bothWeek.setText(secondSubgroup_bothWeekStr);
                }
            }
        }
    }

    private int getLayoutId() {
        if (bothSubgroup_bothWeek) {
            Log.d("ScheduleFragment", "EXIT LAYOUT 1\t 1");
            return R.layout.schedule_single_type_1;
        }
        // столбец 2*1
        if (firstSubgroup_bothWeek) {
            // layout 2,5
            if (secondSubgroup_bothWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 2\t 2");
                return R.layout.schedule_single_type_2;
            } else {
                Log.d("ScheduleFragment", "EXIT LAYOUT 5\t 3");
                return R.layout.schedule_single_type_5;
            }
        }
        if (secondSubgroup_bothWeek) {
            // layout 2,6
            if (firstSubgroup_bothWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 2\t 4");
                return R.layout.schedule_single_type_2;
            } else {
                Log.d("ScheduleFragment", "EXIT LAYOUT 6\t 5");
                return R.layout.schedule_single_type_6;
            }
        }
        //строка 1*2
        if (bothSubgroup_firstWeek) {
            // layout 4,7
            if (bothSubgroup_secondWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 4\t 6");
                return R.layout.schedule_single_type_4;
            } else {
                Log.d("ScheduleFragment", "EXIT LAYOUT 7\t 7");
                return R.layout.schedule_single_type_7;
            }
        }
        if (bothSubgroup_secondWeek) {
            // layout 4,8
            if (bothSubgroup_firstWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 4\t 8");
                return R.layout.schedule_single_type_4;
            } else {
                Log.d("ScheduleFragment", "EXIT LAYOUT 8\t 9");
                return R.layout.schedule_single_type_8;
            }
        }

        if (firstSubgroup_firstWeek) {
            // layout 3,6,8
            if (firstSubgroup_secondWeek || secondSubgroup_bothWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 6\t 10");
                return R.layout.schedule_single_type_6;
            } else if (secondSubgroup_firstWeek || bothSubgroup_secondWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 8\t 11");
                return R.layout.schedule_single_type_8;
            } else {
                Log.d("ScheduleFragment", "EXIT LAYOUT 3\t 12");
                return R.layout.schedule_single_type_3;
            }
        }
        if (firstSubgroup_secondWeek) {
            // layout 3,6,7
            if (firstSubgroup_firstWeek || secondSubgroup_bothWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 6\t 13");
                return R.layout.schedule_single_type_6;
            } else if (secondSubgroup_secondWeek || bothSubgroup_firstWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 7\t 14");
                return R.layout.schedule_single_type_7;
            } else {
                Log.d("ScheduleFragment", "EXIT LAYOUT 3\t 15");
                return R.layout.schedule_single_type_3;
            }
        }

        if (secondSubgroup_firstWeek) {
            // layout 3,5,8
            if (secondSubgroup_secondWeek || firstSubgroup_bothWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 5\t 16");
                return R.layout.schedule_single_type_5;
            } else if (firstSubgroup_firstWeek || bothSubgroup_secondWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 8\t 17");
                return R.layout.schedule_single_type_8;
            } else {
                Log.d("ScheduleFragment", "EXIT LAYOUT 3\t 18");
                return R.layout.schedule_single_type_3;
            }
        }
        if (secondSubgroup_secondWeek) {
            // layout 3,5,7
            if (secondSubgroup_firstWeek || firstSubgroup_bothWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 5\t 19");
                return R.layout.schedule_single_type_5;
            } else if (firstSubgroup_secondWeek || bothSubgroup_firstWeek) {
                Log.d("ScheduleFragment", "EXIT LAYOUT 7\t 20");
                return R.layout.schedule_single_type_7;
            } else {
                Log.d("ScheduleFragment", "EXIT LAYOUT 3\t 21");
                return R.layout.schedule_single_type_3;
            }
        }
        Log.d("ScheduleFragment", "EXIT LAYOUT FAIL\t 22");
        return R.layout.schedule_single_type_9;
    }
}
