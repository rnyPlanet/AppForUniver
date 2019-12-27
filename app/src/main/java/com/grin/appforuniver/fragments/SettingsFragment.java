package com.grin.appforuniver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.grin.appforuniver.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsFragment extends Fragment {

    @BindView(R.id.app_version)
    TextView appVersion;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_settings, container, false);
        Objects.requireNonNull(getActivity()).setTitle(R.string.menu_settings);
        Unbinder mUnbinder = ButterKnife.bind(this, mView);
        return mView;
    }

}
