package com.grin.appforuniver.data.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;

public class Functions {

    public static void changeMainFragment(FragmentActivity fragmentActivity , Fragment fragment) {
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                //.replace(R.id.main_conteiner, fragment)
                .commit();
    }

    public static class Schedule {
        public static boolean compareSubgroupAndWeek(Classes classes, Constants.Subgroup subgroup, Constants.Week week){
            return classes.getSubgroup() == subgroup && classes.getWeek() == week;
        }
    }

}
