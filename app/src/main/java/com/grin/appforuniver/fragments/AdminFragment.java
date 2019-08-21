package com.grin.appforuniver.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.grin.appforuniver.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AdminFragment extends Fragment {

    public final String TAG = AdminFragment.class.getSimpleName();

    private View mView;

    private Unbinder mUnbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_admin, container, false);

        getActivity().setTitle(R.string.admin);

        mUnbinder = ButterKnife.bind(this, mView);

        return mView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
