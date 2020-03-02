package com.grin.appforuniver.utils;

public class Constants {
    public static final String API_BASE_URL = "http://194.9.70.244:8075/api/v1/";
    public static final String LOGIN_URL = "http://194.9.70.244:8075/";

    public static final String BASIC_AUTH_LOGIN = "CHMNU-client";
    public static final String BASIC_AUTH_PASSWORD = "CHMNU";

    public enum Place {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, POOL}

    public enum Week {FIRST, SECOND, BOTH}

    public enum Subgroup {FIRST, SECOND, BOTH}

    public enum Roles {ROLE_ADMIN, ROLE_SCHEDULER, ROLE_USER, ROLE_TEACHER}
}
