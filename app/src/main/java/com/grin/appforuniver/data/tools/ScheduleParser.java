package com.grin.appforuniver.data.tools;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.models.Classes;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;
import com.grin.appforuniver.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ScheduleParser {
    private static final String TAG = "ScheduleParser";
    private List<Classes> input;
    private boolean isProfessorsSchedule;
    private List<ScheduleStandardTypeModel> output;

    private boolean bothSubgroup_firstWeek;
    private boolean bothSubgroup_secondWeek;
    private boolean bothSubgroup_bothWeek;
    private boolean firstSubgroup_firstWeek;
    private boolean firstSubgroup_secondWeek;
    private boolean firstSubgroup_bothWeek;
    private boolean secondSubgroup_firstWeek;
    private boolean secondSubgroup_secondWeek;
    private boolean secondSubgroup_bothWeek;

    public ScheduleParser(List<Classes> source, boolean isProfessorSchedule) {
        this.input = source;
        this.isProfessorsSchedule = isProfessorSchedule;
        output = new ArrayList<>();
    }

    public ScheduleParser parse() {
        for (Constants.Place place : Constants.Place.values()) {
            if (place == Constants.Place.POOL) {
                continue;
            }
            addDaySeparator(place);
            boolean isWeekendDay = false;
            for (int i = 1; i <= 6; i++) {
                List<Classes> classesInPair = getClassesInPair(place, i);
                if (classesInPair.size() > 0) {
                    isWeekendDay = true;
                    int typeView;
                    if (isProfessorsSchedule) {
                        typeView = setDataInLayoutProfessors(classesInPair);
                    } else {
                        typeView = setDataInLayout(classesInPair);
                    }
                    output.add(new ScheduleStandardTypeModel(typeView,
                            place, i, classesInPair));
                }
            }
            if (!isWeekendDay) {
                output.add(new ScheduleStandardTypeModel(R.layout.schedule_weekend_day,
                        place, -1, null));
            }
        }
        return this;
    }

    public List<ScheduleStandardTypeModel> getParsedSchedule() {
        return output;
    }

    private List<Classes> getClassesInPair(Constants.Place place, int numberPair) {
        List<Classes> classesInPairs = new ArrayList<>();

        for (Classes classes : input) {
            if (classes.getPlace() == place && classes.getPositionInDay().getId() == numberPair) {
                if (isProfessorsSchedule) {
                    //Требуется для корректного отображения предметов преподавателя
                    classes.setSubgroup(Constants.Subgroup.BOTH);
                }
                classesInPairs.add(classes);
            }
        }
        return classesInPairs;
    }

    private void addDaySeparator(Constants.Place place) {
        output.add(new ScheduleStandardTypeModel(R.layout.schedule_day_separator,
                place, -1, null));
    }

    private int setDataInLayout(List<Classes> mListClasses) {
        firstSubgroup_firstWeek = false;
        firstSubgroup_secondWeek = false;
        firstSubgroup_bothWeek = false;
        secondSubgroup_firstWeek = false;
        secondSubgroup_secondWeek = false;
        secondSubgroup_bothWeek = false;
        bothSubgroup_firstWeek = false;
        bothSubgroup_secondWeek = false;
        bothSubgroup_bothWeek = false;
        for (Classes classes : mListClasses) {
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.FIRST, Constants.Week.FIRST)) {
                firstSubgroup_firstWeek = true;
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.FIRST, Constants.Week.SECOND)) {
                firstSubgroup_secondWeek = true;
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.FIRST, Constants.Week.BOTH)) {
                firstSubgroup_bothWeek = true;
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.SECOND, Constants.Week.FIRST)) {
                secondSubgroup_firstWeek = true;
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.SECOND, Constants.Week.SECOND)) {
                secondSubgroup_secondWeek = true;
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.SECOND, Constants.Week.BOTH)) {
                secondSubgroup_bothWeek = true;
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.BOTH, Constants.Week.FIRST)) {
                bothSubgroup_firstWeek = true;
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.BOTH, Constants.Week.SECOND)) {
                bothSubgroup_secondWeek = true;
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.BOTH, Constants.Week.BOTH)) {
                bothSubgroup_bothWeek = true;
            }
        }

        return getLayoutId();
    }

    private int setDataInLayoutProfessors(List<Classes> mListClasses) {
        firstSubgroup_firstWeek = false;
        firstSubgroup_secondWeek = false;
        firstSubgroup_bothWeek = false;
        secondSubgroup_firstWeek = false;
        secondSubgroup_secondWeek = false;
        secondSubgroup_bothWeek = false;
        //
        bothSubgroup_firstWeek = false;
        bothSubgroup_secondWeek = false;
        bothSubgroup_bothWeek = false;
        for (Classes classes : mListClasses) {
            if (classes.compareToWeek(Constants.Week.FIRST) || classes.compareToWeek(Constants.Week.SECOND)) {
                bothSubgroup_firstWeek = true;
                bothSubgroup_secondWeek = true;
            }
            if (classes.compareToWeek(Constants.Week.BOTH)) {
                bothSubgroup_bothWeek = true;
            }
        }

        return getLayoutId();
    }

    private int getLayoutId() {
        if (bothSubgroup_bothWeek) {
            return R.layout.schedule_single_type_1;
        }
        // столбец 2*1
        if (firstSubgroup_bothWeek) {
            // layout 2,5
            if (secondSubgroup_bothWeek) {
                return R.layout.schedule_single_type_2;
            } else {
                return R.layout.schedule_single_type_5;
            }
        }
        if (secondSubgroup_bothWeek) {
            // layout 2,6
            if (firstSubgroup_bothWeek) {
                return R.layout.schedule_single_type_2;
            } else {
                return R.layout.schedule_single_type_6;
            }
        }
        //строка 1*2
        if (bothSubgroup_firstWeek) {
            // layout 4,7
            if (bothSubgroup_secondWeek) {
                return R.layout.schedule_single_type_4;
            } else {
                return R.layout.schedule_single_type_7;
            }
        }
        if (bothSubgroup_secondWeek) {
            // layout 4,8
            if (bothSubgroup_firstWeek) {
                return R.layout.schedule_single_type_4;
            } else {
                return R.layout.schedule_single_type_8;
            }
        }

        if (firstSubgroup_firstWeek) {
            // layout 3,6,8
            if (firstSubgroup_secondWeek & secondSubgroup_bothWeek) {
                return R.layout.schedule_single_type_6;
            } else if (secondSubgroup_firstWeek & bothSubgroup_secondWeek) {
                return R.layout.schedule_single_type_8;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }
        if (firstSubgroup_secondWeek) {
            // layout 3,6,7
            if (firstSubgroup_firstWeek & secondSubgroup_bothWeek) {
                return R.layout.schedule_single_type_6;
            } else if (secondSubgroup_secondWeek & bothSubgroup_firstWeek) {
                return R.layout.schedule_single_type_7;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }

        if (secondSubgroup_firstWeek) {
            // layout 3,5,8
            if (secondSubgroup_secondWeek & firstSubgroup_bothWeek) {
                return R.layout.schedule_single_type_5;
            } else if (firstSubgroup_firstWeek & bothSubgroup_secondWeek) {
                return R.layout.schedule_single_type_8;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }
        if (secondSubgroup_secondWeek) {
            // layout 3,5,7
            if (secondSubgroup_firstWeek & firstSubgroup_bothWeek) {
                return R.layout.schedule_single_type_5;
            } else if (firstSubgroup_secondWeek & bothSubgroup_firstWeek) {
                return R.layout.schedule_single_type_7;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }
        return R.layout.schedule_single_type_9;
    }

}
