package com.grin.appforuniver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.grin.appforuniver.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ConsultationFragment extends Fragment {

    public final String TAG = ConsultationFragment.class.getSimpleName();

    private View mView;

    private Unbinder mUnbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_consultation, container, false);

        getActivity().setTitle(R.string.menu_consultation);

        mUnbinder = ButterKnife.bind(this, mView);

        return mView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
