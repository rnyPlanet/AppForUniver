package com.grin.appforuniver.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.grin.appforuniver.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

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

        setThreadPolicy();
        parseUniversityMainPage();

        return mView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void setThreadPolicy() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void parseUniversityMainPage() {
        new Thread(new Runnable() {

            Document doc;

            ArrayList<String> newsCardTitles = new ArrayList<>();
            ArrayList<String> newsCardImagesUrl = new ArrayList<>();
            ArrayList<String> newsCardLinks = new ArrayList<>();

            @Override
            public void run() {
                try {
                    doc = Jsoup.connect("https://chmnu.edu.ua/category/zapisi/novini/").timeout(6000).get();
                    Elements newsCardsBody = doc.select("div.category-standart-raid");

                    for (Element newsCardBody : newsCardsBody) {
                        for (Element cardData : newsCardBody.select("div.category-standart-block")) {
                            String newsCardTitle = cardData.select("div.title").text();
                            newsCardTitles.add(newsCardTitle);

                            String img_url = cardData.select("img").attr("src");
                            newsCardImagesUrl.add(img_url);

                            String newsLink = cardData.select("div.title > a").attr("href");
                            newsCardLinks.add(newsLink);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
