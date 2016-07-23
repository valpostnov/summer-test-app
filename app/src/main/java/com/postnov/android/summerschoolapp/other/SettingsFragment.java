package com.postnov.android.summerschoolapp.other;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.artists.ArtistsActivity;
import com.postnov.android.summerschoolapp.feature.YaService;
import com.postnov.android.summerschoolapp.utils.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.postnov.android.summerschoolapp.utils.PreferencesManager.*;

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener
{
    private PreferencesManager mPreferencesManager;
    private Unbinder mUnbinder;

    @BindView(R.id.setting_hf_checkbox)
    CheckBox mHeadsetFeatureCheckBox;

    public static Fragment newInstance()
    {
        return new SettingsFragment();
    }

    public SettingsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPreferencesManager = new PreferencesManager(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((ArtistsActivity) getActivity()).setupActionBar(getString(R.string.action_settings), true);
        mHeadsetFeatureCheckBox.setOnCheckedChangeListener(this);
        mHeadsetFeatureCheckBox.setChecked(getHeadsetFeatureState());
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        if (mUnbinder != null)
        {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        setHeadsetFeatureState(isChecked);
        if (isChecked)
        {
            getActivity().startService(new Intent(getActivity(), YaService.class));
        }
        else
        {
            getActivity().stopService(new Intent(getActivity(), YaService.class));
        }
    }

    private boolean getHeadsetFeatureState()
    {
        return mPreferencesManager.getBoolean(HEADSET_FEATURE_STATE);
    }

    private void setHeadsetFeatureState(boolean state)
    {
        mPreferencesManager.setBoolean(HEADSET_FEATURE_STATE, state);
    }
}
