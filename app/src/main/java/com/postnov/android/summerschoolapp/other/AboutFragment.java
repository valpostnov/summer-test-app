package com.postnov.android.summerschoolapp.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.postnov.android.summerschoolapp.BuildConfig;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.artists.interfaces.ToolbarProvider;
import com.postnov.android.summerschoolapp.base.BaseFragment;
import com.postnov.android.summerschoolapp.utils.Utils;

public class AboutFragment extends BaseFragment
{
    public static AboutFragment newInstance()
    {
        return new AboutFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ToolbarProvider toolbarProvider = getToolbarProvider();
        if (toolbarProvider != null) toolbarProvider.updateToolbar(getString(R.string.about_fragment), true);
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_about;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        TextView version = (TextView) view.findViewById(R.id.app_version);
        version.setText(Utils.concatStrings(getString(R.string.version), BuildConfig.VERSION_NAME));
    }
}
