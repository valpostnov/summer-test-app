package com.postnov.android.summerschoolapp.about;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.postnov.android.summerschoolapp.BuildConfig;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.artists.ArtistsActivity;
import com.postnov.android.summerschoolapp.utils.Utils;

public class AboutFragment extends Fragment
{
    public AboutFragment() {}

    public static AboutFragment newInstance()
    {
        return new AboutFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ((ArtistsActivity) getActivity()).setupActionBar(getString(R.string.about_fragmnet), true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView version = (TextView) view.findViewById(R.id.app_version);
        version.setText(Utils.concatStrings(getString(R.string.version), BuildConfig.VERSION_NAME));

        return view;
    }
}
