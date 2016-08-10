package com.postnov.android.summerschoolapp.artists.interfaces;


import android.app.Fragment;

/**
 * Created by platon on 08.08.2016.
 */
public interface FragmentTransactionManager
{
    void replaceFragmentWithoutBackStack(Fragment fragment);
    void replaceFragment(Fragment fragment);
    void removeFragment(Fragment fragment);
}
