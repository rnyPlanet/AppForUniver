package com.grin.appforuniver.data.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grin.appforuniver.data.model.user.Role;
import com.grin.appforuniver.data.model.user.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferenceUtils {

    public PreferenceUtils() {}

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

    public static boolean saveUserRoles(List<Role> roles, Context context) {
        Gson gson = new Gson();
        ArrayList<String> arrPackage = new ArrayList<>();
        for (Role role : roles) { arrPackage.add(role.getName()); }
        String json = gson.toJson(arrPackage);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.USER_ROLES_KEY, json);
        prefsEditor.apply();
        return true;
    }
    public static List<String> getUserRoles(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        List<String> arrPackageData;
        Gson gson = new Gson();
        String json = prefs.getString(Constants.USER_ROLES_KEY, "");

        Type type = new TypeToken<List<String>>() { }.getType();

        arrPackageData = gson.fromJson(json, type);

        return arrPackageData;
    }

}

