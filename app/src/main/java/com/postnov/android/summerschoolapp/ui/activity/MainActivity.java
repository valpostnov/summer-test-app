package com.postnov.android.summerschoolapp.ui.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.ui.fragments.ArtistsFragment;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            ArtistsFragment fragment = new ArtistsFragment();
            transaction.replace(R.id.content_fragment, fragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
    }
}
