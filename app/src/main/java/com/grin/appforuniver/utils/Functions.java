package com.grin.appforuniver.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class Functions {

    public static void changeMainFragment(FragmentActivity fragmentActivity, Fragment fragment) {
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                //.replace(R.id.main_conteiner, fragment)
                .commit();
    }
}
