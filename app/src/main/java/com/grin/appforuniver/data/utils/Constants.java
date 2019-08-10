package com.grin.appforuniver.data.utils;

public class Constants {

    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";

    public static final String BASE_URL = "http://194.9.70.244:8075/api/v1/";

    private static String USER_TOKEN = "";
    public static void setUserToken(String userToken) { USER_TOKEN = userToken; }
    public static String getUserToken() { return USER_TOKEN; }

    private static boolean _isUserLogin = false;
    public static void setIsUserLogin(boolean isUserLogin) { _isUserLogin = isUserLogin; }
    public static boolean getIsUserLogin() { return _isUserLogin; }

}
