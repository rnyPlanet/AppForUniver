package com.grin.appforuniver.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.grin.appforuniver.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AdminFragment extends Fragment {

    public final String TAG = AdminFragment.class.getSimpleName();

    private View mView;
    private AdminViewModel viewModel;
    private Unbinder mUnbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_admin, container, false);
        viewModel = ViewModelProviders.of(this).get(AdminViewModel.class);

        mUnbinder = ButterKnife.bind(this, mView);

        return mView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
