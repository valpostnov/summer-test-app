package com.postnov.android.summerschoolapp.utils;


public final class Const {

    public static final String BROADCAST_ACTION = "com.postnov.android.summerschoolapp.BROADCAST";
    public static final String EXTENDED_DATA_STATUS = "com.postnov.android.summerschoolapp.STATUS";
    public static final String LOADED_ARTISTS_COUNT = "com.postnov.android.summerschoolapp.COUNT";


    // all artists was downloaded
    public static final int STATE_ACTION_ALL_DOWNLOADED = 1;

    // Bad Request
    public static final int STATE_ACTION_UNKNOWN = -1;

    // Bad Request
    public static final int STATE_ACTION_400 = 400;

    // Not Found
    public static final int STATE_ACTION_404 = 404;

    // Gateway Timeout
    public static final int STATE_ACTION_504 = 504;

    // Service Unavailable
    public static final int STATE_ACTION_503 = 503;

    // OK
    public static final int STATE_ACTION_200 = 200;

}
