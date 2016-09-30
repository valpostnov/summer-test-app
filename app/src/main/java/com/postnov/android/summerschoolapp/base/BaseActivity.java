package com.postnov.android.summerschoolapp.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.postnov.android.summerschoolapp.App;
import com.postnov.android.summerschoolapp.BuildConfig;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.artists.ArtistsFragment;
import com.postnov.android.summerschoolapp.artists.DetailsFragment;
import com.postnov.android.summerschoolapp.artists.interfaces.FragmentsInteractor;
import com.postnov.android.summerschoolapp.artists.interfaces.ToolbarProvider;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.feature.YaService;
import com.postnov.android.summerschoolapp.other.AboutFragment;
import com.postnov.android.summerschoolapp.other.SettingsFragment;
import com.postnov.android.summerschoolapp.utils.IPreferencesManager;
import com.postnov.android.summerschoolapp.utils.Utils;

import static com.postnov.android.summerschoolapp.utils.PreferencesManager.HEADSET_FEATURE_STATE;
import static com.postnov.android.summerschoolapp.utils.PreferencesManager.YA_SERVICE_RUNNING_STATE;

public class BaseActivity extends AppCompatActivity implements ToolbarProvider, FragmentsInteractor
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        if (savedInstanceState == null)
        {
            replaceFragmentWithoutBackStack(ArtistsFragment.newInstance());
        }

        IPreferencesManager preferencesManager = App.get(this).getPreferencesManager();

        if (preferencesManager.getBoolean(HEADSET_FEATURE_STATE)
                && !preferencesManager.getBoolean(YA_SERVICE_RUNNING_STATE))
        {
            startService(new Intent(this, YaService.class));
        }
    }

    public void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void replaceFragmentWithoutBackStack(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void updateToolbar(String title, boolean hasBackButton)
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(hasBackButton);
    }

    @Override
    public void updateToolbar(String title)
    {
        updateToolbar(title, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_about:
                replaceFragment(AboutFragment.newInstance());
                return true;

            case R.id.action_feedback:
                sendFeedback();
                return true;

            case R.id.action_settings:
                replaceFragment(SettingsFragment.newInstance());
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
        String subject = getString(R.string.email_subject);
        String body = Utils.concatStrings(getString(R.string.version), BuildConfig.VERSION_NAME);
        String recipient = Utils.concatStrings("mailto:", getString(R.string.email_address));

        Intent feedbackIntent = new Intent();
        feedbackIntent.setAction(Intent.ACTION_SENDTO);
        feedbackIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        feedbackIntent.putExtra(Intent.EXTRA_TEXT, body);
        feedbackIntent.setData(Uri.parse(recipient));

        startActivity(Intent.createChooser(feedbackIntent, getString(R.string.support_chooser_text)));
    }

    @Override
    public void openDetails(Artist artist) {
        replaceFragment(DetailsFragment.newInstance(artist));
    }
}
