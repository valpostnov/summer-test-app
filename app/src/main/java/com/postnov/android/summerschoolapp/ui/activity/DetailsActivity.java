package com.postnov.android.summerschoolapp.ui.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.ui.fragments.ArtistsFragment;
import com.postnov.android.summerschoolapp.ui.fragments.DetailsArtistFragment;


public class DetailsActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            DetailsArtistFragment fragment = new DetailsArtistFragment();
            transaction.replace(R.id.content_details_fragment, fragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                finishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
