package com.postnov.android.summerschoolapp.feature;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.postnov.android.summerschoolapp.App;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.utils.IPreferencesManager;
import com.postnov.android.summerschoolapp.utils.Utils;

import static com.postnov.android.summerschoolapp.utils.PreferencesManager.YA_SERVICE_RUNNING_STATE;

public class YaService extends Service
{
    private static final int NOTIFY_ID = 1;
    private final HeadsetPlugReceiver headsetPlugReceiver = new HeadsetPlugReceiver();
    private IPreferencesManager preferencesManager;

    public YaService() {}

    @Override
    public void onCreate()
    {
        super.onCreate();
        preferencesManager = App.from(this).getPreferencesManager();
        preferencesManager.setBoolean(YA_SERVICE_RUNNING_STATE, true);
        registerReceiver(headsetPlugReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
    }

    @Override
    public void onDestroy()
    {
        preferencesManager.setBoolean(YA_SERVICE_RUNNING_STATE, false);
        unregisterReceiver(headsetPlugReceiver);
        setNotificationVisibility(false);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }

    private void setNotificationVisibility(boolean visible)
    {
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (visible)
        {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
            notification.setSmallIcon(R.mipmap.ic_launcher);
            notification.setContentTitle(getString(R.string.headset_plug));
            notification.addAction(R.drawable.ic_music, getString(R.string.music), createPIntent("ru.yandex.music"));
            notification.addAction(R.drawable.ic_radio, getString(R.string.radio), createPIntent("ru.yandex.radio"));
            notifyManager.notify(NOTIFY_ID, notification.build());
        }

        else notifyManager.cancel(NOTIFY_ID);
    }

    private PendingIntent createPIntent(String packageName)
    {
        String appUrl = Utils.concatStrings(getString(R.string.play_link), packageName);
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);

        if (intent == null) intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));

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
                    setNotificationVisibility(true);
                    break;

                case STATE_UNPLUG:
                    setNotificationVisibility(false);
                    break;
            }
        }
    }
}
