package com.grin.appforuniver.data.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grin.appforuniver.App;
import com.grin.appforuniver.data.model.user.Role;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.service.AuthService;
import com.grin.appforuniver.data.service.UserService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AuthManager {

    public static final String PREFERENCE_NAME = "authorize_manager";
    private static final String KEY_USER_TOKEN = "user_token";
    private static final String KEY_ID = "id";
    private static final String KEY_LOGIN_DATA = "login_data";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PASSWORD_HASHED = "password";
    private static final String KEY_USER_ROLES = "user_roles";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static AuthManager instance;
    private String access_token;
    private int id;
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String password_hached;
    private List<String> roles;
    private boolean authorized;
    private boolean isLoginData;
    private User me;
    private UserService mUserService;
    private AuthService mAuthService;
    private AuthManager() {
        SharedPreferences sharedPreferences = App.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

//        this.access_token = sharedPreferences.getString(KEY_USER_TOKEN, null);
//        this.authorized = !TextUtils.isEmpty(access_token);
        this.isLoginData = sharedPreferences.getBoolean(KEY_LOGIN_DATA, false);

        if (isLoginData) {
            this.id = sharedPreferences.getInt(KEY_ID, -1);
            this.username = sharedPreferences.getString(KEY_USERNAME, null);
            this.first_name = sharedPreferences.getString(KEY_FIRST_NAME, null);
            this.last_name = sharedPreferences.getString(KEY_LAST_NAME, null);
            this.email = sharedPreferences.getString(KEY_EMAIL, null);
            this.password = sharedPreferences.getString(KEY_PASSWORD, null);
            this.password_hached = sharedPreferences.getString(KEY_PASSWORD_HASHED, null);

            String json = sharedPreferences.getString(KEY_USER_ROLES, "");
            Type type = new TypeToken<List<String>>() {
            }.getType();
            this.roles = new Gson().fromJson(json, type);
        }

        this.me = null;
        this.mUserService = UserService.getService();
        this.mAuthService = AuthService.getService();
    }

    public static AuthManager getInstance() {
        if (instance == null) {
            synchronized (AuthManager.class) {
                if (instance == null) {
                    instance = new AuthManager();
                }
            }
        }
        return instance;
    }

    public int getID() {
        return id;
    }

    public String getAccessToken() {
        return access_token;
    }

    public List<String> getUserRoles() {
        return roles;
    }

    public boolean isLoginData() {
        return isLoginData;
    }

    public void writeUserInfo(User me) {
        this.me = me;
        this.username = me.getUsername();
        this.first_name = me.getFirstName();
        this.last_name = me.getLastName();
        this.email = me.getEmail();
        this.password_hached = me.getPassword();


        ArrayList<String> arrPackage = new ArrayList<>();
        for (Role role : me.getRoles()) {
            arrPackage.add(role.getName());
        }
        String json = new Gson().toJson(arrPackage);
        SharedPreferences.Editor prefsEditor = App.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(KEY_USER_ROLES, json);
        prefsEditor.apply();

        this.roles = arrPackage;
    }

    public void writeAccessToken(String token) {
        if (token != null) {
            SharedPreferences.Editor editor = App.getInstance()
                    .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(KEY_USER_TOKEN, token);
            editor.apply();

            access_token = token;
            authorized = true;
        } else {
            SharedPreferences.Editor editor = App.getInstance()
                    .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(KEY_USER_TOKEN, null);
            editor.apply();

            access_token = null;
            authorized = false;
        }
    }

    public void writeDataLogin(String username, String password) {
        SharedPreferences.Editor prefsEditor = App.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(KEY_USERNAME, username);
        prefsEditor.putString(KEY_PASSWORD, password);
        prefsEditor.putBoolean(KEY_LOGIN_DATA, true);
        prefsEditor.apply();
    }

    public void requestPersonalProfile() {
        if (authorized) {
            mUserService.requestCurrentUserProfile(new UserService.OnRequestCurrentUserProfileListener() {
                @Override
                public void onRequestCurrentUserProfileSuccess(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        writeUserInfo(response.body());
                    }
                }

                @Override
                public void onRequestCurrentUserProfileFailed(Call<User> call, Throwable t) {
                }
            });

        }
    }

    public void logout() {
//        service.cancel();

        SharedPreferences.Editor editor = App.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_USER_TOKEN, null);
        editor.putString(KEY_ID, null);
        editor.putString(KEY_USERNAME, null);
        editor.putString(KEY_FIRST_NAME, null);
        editor.putString(KEY_LAST_NAME, null);
        editor.putString(KEY_EMAIL, null);
        editor.putString(KEY_PASSWORD, null);
        editor.putString(KEY_PASSWORD_HASHED, null);
        editor.putString(KEY_USER_ROLES, null);

        editor.putString(KEY_LOGIN_DATA, null);

        editor.apply();

        this.access_token = null;
        this.id = -1;
        this.username = null;
        this.first_name = null;
        this.last_name = null;
        this.email = null;
        this.password = null;
        this.password_hached = null;
        this.roles = null;
        this.authorized = false;

        this.me = null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User getUser() {
        return me;
    }
}
