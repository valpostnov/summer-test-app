package com.postnov.android.summerschoolapp.artists;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.postnov.android.summerschoolapp.BuildConfig;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.about.AboutFragment;

public class ArtistsActivity extends Activity
{
    private final HeadsetPlugReceiver mReceiver = new HeadsetPlugReceiver();

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

    @Override
    protected void onResume()
    {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(mReceiver);
        showNotification(false);
    }

    public void addFragment(Fragment fragment, boolean addToBackStack)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_fragment, fragment);
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
                return true;

            case R.id.action_about:
                addFragment(AboutFragment.newInstance(), true);
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

    private void showNotification(boolean show)
    {
        int notificationId = 1;
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (show)
        {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);

            notification.setSmallIcon(R.mipmap.ic_launcher);
            notification.setContentTitle(getString(R.string.headset_plug));
            notification.addAction(R.drawable.ic_music, getString(R.string.music), createPendingIntent("ru.yandex.music"));
            notification.addAction(R.drawable.ic_radio, getString(R.string.radio), createPendingIntent("ru.yandex.radio"));

            notifyManager.notify(notificationId, notification.build());
            return;
        }

        notifyManager.cancel(notificationId);
    }

    private PendingIntent createPendingIntent(String packageName)
    {
        String appUrl = getString(R.string.play_link) + packageName;

        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);

        if (intent == null)
        {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private class HeadsetPlugReceiver extends BroadcastReceiver
    {
        private static final int STATE_UNPLUG = 0;
        private static final int STATE_PLUG = 1;
        private static final String EXTRA_STATE = "state";

        @Override
        public void onReceive(Context context, Intent intent)
        {
            int state = intent.getExtras().getInt(EXTRA_STATE);
            switch (state)
            {
                case STATE_PLUG:
                    showNotification(true);
                    break;

                case STATE_UNPLUG:
                    showNotification(false);
                    break;
            }
        }
    }
}
