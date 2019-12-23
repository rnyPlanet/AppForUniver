package com.grin.appforuniver.data.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grin.appforuniver.data.model.user.Role;
import com.grin.appforuniver.data.model.user.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferenceUtils {
    public static Context context;

    public PreferenceUtils() {
    }

    public static boolean saveUsername(String username) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.USERNAME_KEY, username);
        prefsEditor.apply();
        return true;
    }

    public static String getUsername() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.USERNAME_KEY, null);
    }

    public static boolean savePassword(String password) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.PASSWORD_KEY, password);
        prefsEditor.apply();
        return true;
    }

    public static String getPassword() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.PASSWORD_KEY, null);
    }

    public static boolean saveUser(User user) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(Constants.SAVE_USER_KEY, json);
        prefsEditor.apply();
        return true;
    }

    public static User getSaveUser() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String strUser = prefs.getString(Constants.SAVE_USER_KEY, null);

        return gson.fromJson(strUser, User.class);
    }

    public static boolean saveUserToken(String token) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.USER_TOKEN_KEY, token == null || token.isEmpty() ? "" : "Bearer_" + token);
        prefsEditor.apply();
        return true;
    }

    public static String getUserToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.USER_TOKEN_KEY, null);
    }

    public static boolean saveUserRoles(List<Role> roles) {
        Gson gson = new Gson();
        ArrayList<String> arrPackage = new ArrayList<>();
        for (Role role : roles) {
            arrPackage.add(role.getName());
        }
        String json = gson.toJson(arrPackage);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.USER_ROLES_KEY, json);
        prefsEditor.apply();
        return true;
    }

    public static List<String> getUserRoles() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        List<String> arrPackageData;
        Gson gson = new Gson();
        String json = prefs.getString(Constants.USER_ROLES_KEY, "");

        Type type = new TypeToken<List<String>>() {
        }.getType();

        arrPackageData = gson.fromJson(json, type);

        return arrPackageData;
    }

}

