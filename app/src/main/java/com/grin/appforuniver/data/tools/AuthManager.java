package com.grin.appforuniver.data.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grin.appforuniver.App;
import com.grin.appforuniver.data.model.AccessToken;
import com.grin.appforuniver.data.model.user.Role;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.service.UserService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AuthManager implements UserService.OnRequestCurrentUserProfileListener {

    public static final String PREFERENCE_NAME = "authorize_manager";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PASSWORD_HASHED = "password";
    private static final String KEY_USER_ROLES = "user_roles";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";

    private static AuthManager instance;

    private AccessToken access_token;
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    private boolean authorized;
    private User me;
    private UserService mUserService;

    private AuthManager() {
        SharedPreferences sharedPreferences = App.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String jsonToken = sharedPreferences.getString(KEY_TOKEN, null);
        if (jsonToken != null) {
            Type typeAccess = new TypeToken<AccessToken>() {
            }.getType();
            this.access_token = new Gson().fromJson(jsonToken, typeAccess);
        }

        this.authorized = this.access_token != null;

        if (authorized) {
            this.id = sharedPreferences.getInt(KEY_ID, -1);
            this.username = sharedPreferences.getString(KEY_USERNAME, null);
            this.firstName = sharedPreferences.getString(KEY_FIRST_NAME, null);
            this.lastName = sharedPreferences.getString(KEY_LAST_NAME, null);
            this.email = sharedPreferences.getString(KEY_EMAIL, null);

            String json = sharedPreferences.getString(KEY_USER_ROLES, "");
            Type type = new TypeToken<List<String>>() {
            }.getType();
            this.roles = new Gson().fromJson(json, type);
        }

        this.me = null;
        this.mUserService = UserService.getService();
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

    public AccessToken getAccessToken() {
        return access_token;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getUserRoles() {
        return roles;
    }

    public User getUser() {
        return me;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void writeUserInfo(User me) {
        this.me = me;
        this.id = me.getId();
        this.username = me.getUsername();
        this.firstName = me.getFirstName();
        this.lastName = me.getLastName();
        this.email = me.getEmail();

        ArrayList<String> arrPackage = new ArrayList<>();
        for (Role role : me.getRoles()) {
            arrPackage.add(role.getName());
        }
        String json = new Gson().toJson(arrPackage);
        SharedPreferences.Editor prefsEditor = App.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(KEY_USER_ROLES, json);
        prefsEditor.putInt(KEY_ID, me.getId());
        prefsEditor.putString(KEY_USERNAME, me.getUsername());
        prefsEditor.putString(KEY_FIRST_NAME, me.getFirstName());
        prefsEditor.putString(KEY_LAST_NAME, me.getLastName());
        prefsEditor.putString(KEY_EMAIL, me.getEmail());
        prefsEditor.apply();

        this.roles = arrPackage;
    }

    public void writeAccessToken(AccessToken token) {
        if (token != null) {
            SharedPreferences.Editor editor = App.getInstance()
                    .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
            String json = new Gson().toJson(token);
            editor.putString(KEY_TOKEN, json);
            editor.apply();

            access_token = token;
            authorized = true;
        } else {
            SharedPreferences.Editor editor = App.getInstance()
                    .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(KEY_TOKEN, null);
            editor.apply();

            access_token = null;
            authorized = false;
        }
    }

    public void requestPersonalProfile() {
        if (authorized) {
            mUserService.requestCurrentUserProfile(this);
        }
    }

    public void cancelRequest() {
        mUserService.cancel();
    }

    public void logout() {
//        service.cancel();

        SharedPreferences.Editor editor = App.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, null);
        editor.putString(KEY_ID, null);
        editor.putString(KEY_USERNAME, null);
        editor.putString(KEY_FIRST_NAME, null);
        editor.putString(KEY_LAST_NAME, null);
        editor.putString(KEY_EMAIL, null);
        editor.putString(KEY_PASSWORD, null);
        editor.putString(KEY_PASSWORD_HASHED, null);
        editor.putString(KEY_USER_ROLES, null);

        editor.apply();

        this.access_token = null;
        this.id = -1;
        this.username = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.roles = null;
        this.authorized = false;

        this.me = null;
    }

    //request user profile
    @Override
    public void onRequestCurrentUserProfileSuccess(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
            writeUserInfo(response.body());
        } else if (isAuthorized()) {
            mUserService.requestCurrentUserProfile(this);
        }
    }

    @Override
    public void onRequestCurrentUserProfileFailed(Call<User> call, Throwable t) {
        if (isAuthorized()) {
            mUserService.requestCurrentUserProfile(this);
        }
    }
}
