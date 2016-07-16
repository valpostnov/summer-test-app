package com.postnov.android.summerschoolapp.artists;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.postnov.android.summerschoolapp.BuildConfig;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.about.AboutActivity;

public class ArtistsActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        if (savedInstanceState == null)
        {
            addFragment(ArtistsFragment.newInstance(), false);
        }
    }

    public void addFragment(Fragment fragment, boolean addToBackStack)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_fragment, fragment);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    public void setupActionBar(String title, boolean hasBackButton)
    {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(hasBackButton);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                setupActionBar(getString(R.string.app_name), false);
                return true;

            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;

            case R.id.action_feedback:
                sendFeedback();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void sendFeedback()
    {
        String recipient = getString(R.string.email_address);
        String subject = getString(R.string.email_subject);
        String body = String.format("%s %s", getString(R.string.email_body), BuildConfig.VERSION_NAME);

        Intent feedbackIntent = new Intent();
        feedbackIntent.setAction(Intent.ACTION_SENDTO);
        feedbackIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        feedbackIntent.putExtra(Intent.EXTRA_TEXT, body);
        feedbackIntent.setData(Uri.parse("mailto:" + recipient));

        startActivity(Intent.createChooser(feedbackIntent, getString(R.string.support_chooser_text)));
    }
}
