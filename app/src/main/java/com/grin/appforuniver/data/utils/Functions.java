package com.grin.appforuniver.data.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.grin.appforuniver.R;

public class Functions {

    public static void changeMainFragment(FragmentActivity fragmentActivity , Fragment fragment) {
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                //.replace(R.id.main_conteiner, fragment)
                .commit();
    }

}
