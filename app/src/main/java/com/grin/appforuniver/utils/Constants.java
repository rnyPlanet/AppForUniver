package com.grin.appforuniver.utils;

public class Constants {

    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String SAVE_USER_KEY = "save_user";
    public static final String USER_TOKEN_KEY = "user_token";
    public static final String USER_ROLES_KEY = "user_roles";

    public static final String BASE_URL = "http://194.9.70.244:8075/api/v1/";

    public enum Place {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, POOL}

    public enum Week {FIRST, SECOND, BOTH}

    public enum Subgroup {FIRST, SECOND, BOTH}

    public enum Roles {ROLE_ADMIN, ROLE_SCHEDULER, ROLE_USER, ROLE_TEACHER}
}
