package com.grin.appforuniver.data.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.grin.appforuniver.data.model.user.User;

public class PreferenceUtils {

    public PreferenceUtils() {
    }

    public static boolean saveUsername(String username, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.USERNAME_KEY, username);
        prefsEditor.apply();
        return true;
    }

    public static String getUsername(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.USERNAME_KEY, null);
    }

    public static boolean savePassword(String password, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.PASSWORD_KEY, password);
        prefsEditor.apply();
        return true;
    }

    public static String getPassword(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.PASSWORD_KEY, null);
    }

    public static boolean saveUser(User user, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(Constants.SAVE_USER_KEY, json);
        prefsEditor.apply();
        return true;
    }

    public static User getSaveUser(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String strUser = prefs.getString(Constants.SAVE_USER_KEY, null);
        User user = gson.fromJson(strUser, User.class);

        return user;
    }


    public static boolean saveUserToken(String token, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.USER_TOKEN_KEY, "Bearer_" + token);
        prefsEditor.apply();
        return true;
    }

    public static String getUserToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.USER_TOKEN_KEY, null);
    }


}
