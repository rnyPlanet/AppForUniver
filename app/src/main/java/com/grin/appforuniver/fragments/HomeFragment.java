package com.grin.appforuniver.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    public final String TAG = HomeFragment.class.getSimpleName();

    private View mView;

    private Unbinder mUnbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle(R.string.menu_home);

        mUnbinder = ButterKnife.bind(this, mView);

        parseUniversityMainPage();

        return mView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    private void parseUniversityMainPage(){
        // Magic will be here 😊
    }
}
