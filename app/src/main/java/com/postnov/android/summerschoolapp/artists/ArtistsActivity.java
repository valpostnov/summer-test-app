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
import android.widget.Toolbar;

import com.postnov.android.summerschoolapp.BuildConfig;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.about.AboutFragment;
import com.postnov.android.summerschoolapp.artists.interfaces.FragmentTransactionManager;
import com.postnov.android.summerschoolapp.artists.interfaces.ToolbarProvider;
import com.postnov.android.summerschoolapp.feature.YaService;
import com.postnov.android.summerschoolapp.other.SettingsFragment;
import com.postnov.android.summerschoolapp.utils.PreferencesManager;
import com.postnov.android.summerschoolapp.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.postnov.android.summerschoolapp.utils.PreferencesManager.*;

public class ArtistsActivity extends Activity implements ToolbarProvider, FragmentTransactionManager
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        if (savedInstanceState == null)
        {
            showFragment(ArtistsFragment.newInstance());
        }

        PreferencesManager mPreferencesManager = PreferencesManager.getManager();

        if (mPreferencesManager.getBoolean(HEADSET_FEATURE_STATE)
                && !mPreferencesManager.getBoolean(YA_SERVICE_RUNNING_STATE))
        {
            startService(new Intent(this, YaService.class));
        }
    }

    @Override
    public void showFragment(Fragment fragment, boolean toBackStack)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_fragment, fragment);
        if (toBackStack) transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public void showFragment(Fragment fragment)
    {
        showFragment(fragment, false);
    }

    @Override
    public void removeFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    @Override
    public void updateToolbar(String title, boolean hasBackButton)
    {
        ActionBar actionBar = getActionBar();
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
                showFragment(AboutFragment.newInstance(), true);
                return true;

            case R.id.action_feedback:
                sendFeedback();
                return true;

            case R.id.action_settings:
                showFragment(SettingsFragment.newInstance(), true);
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
}
