package com.postnov.android.summerschoolapp.utils;

/**
 * Created by platon on 09.08.2016.
 */
public interface IPreferencesManager
{
    boolean getBoolean(String key);
    void setBoolean(String key, boolean value);
}
