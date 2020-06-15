package com.grin.appforuniver.ui.home;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.grin.appforuniver.databinding.FragmentHomeBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public final String TAG = HomeFragment.class.getSimpleName();
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setThreadPolicy();
        parseUniversityMainPage();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
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
