package com.postnov.android.summerschoolapp.ui.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.ui.fragments.ArtistsFragment;
import com.postnov.android.summerschoolapp.utils.Const;
import com.postnov.android.summerschoolapp.utils.Utils;

import static com.postnov.android.summerschoolapp.utils.Const.*;


public class MainActivity extends Activity {

    private DownloadStateReceiver mDownloadStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter statusIntentFilter = new IntentFilter(BROADCAST_ACTION);
        statusIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        mDownloadStateReceiver = new DownloadStateReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mDownloadStateReceiver, statusIntentFilter);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            ArtistsFragment fragment = new ArtistsFragment();
            transaction.replace(R.id.content_fragment, fragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        if (mDownloadStateReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mDownloadStateReceiver);
            mDownloadStateReceiver = null;
        }
        super.onDestroy();
    }

    private class DownloadStateReceiver extends BroadcastReceiver {

        private DownloadStateReceiver() {}

        @Override
        public void onReceive(Context context, Intent intent) {
            int statusCode = intent.getIntExtra(EXTENDED_DATA_STATUS, STATE_ACTION_UNKNOWN);

            switch (statusCode)
            {
                case STATE_ACTION_ALL_DOWNLOADED:
                    Utils.showToast(MainActivity.this, getString(R.string.status_not_entries));
                    break;
                case STATE_ACTION_UNKNOWN:
                    Utils.showToast(MainActivity.this, getString(R.string.status_unknown));
                    break;
                case STATE_ACTION_400:
                    Utils.showToast(MainActivity.this, getString(R.string.status_400));
                    break;
                case STATE_ACTION_404:
                    Utils.showToast(MainActivity.this, getString(R.string.status_404));
                    break;
                case STATE_ACTION_503:
                    Utils.showToast(MainActivity.this, getString(R.string.status_503));
                    break;
                case STATE_ACTION_504:
                    Utils.showToast(MainActivity.this, getString(R.string.status_504));
                    break;
            }
        }
    }
}
