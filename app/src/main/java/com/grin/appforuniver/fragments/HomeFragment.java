package com.grin.appforuniver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.grin.appforuniver.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    public final String TAG = HomeFragment.class.getSimpleName();

    private View mView;

    private Unbinder mUnbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().setTitle(R.string.mainPageOfUniversity);

        WebView webView = mView.findViewById(R.id.fragment_home_wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://chmnu.edu.ua/");

        mUnbinder = ButterKnife.bind(this, mView);

        return mView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
