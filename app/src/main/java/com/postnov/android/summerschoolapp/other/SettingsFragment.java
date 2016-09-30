package com.postnov.android.summerschoolapp.other;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.postnov.android.summerschoolapp.App;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.base.BaseFragment;
import com.postnov.android.summerschoolapp.feature.YaService;
import com.postnov.android.summerschoolapp.utils.IPreferencesManager;

import butterknife.BindView;

import static com.postnov.android.summerschoolapp.utils.PreferencesManager.HEADSET_FEATURE_STATE;

public class SettingsFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener
{
    private IPreferencesManager preferencesManager;

    @BindView(R.id.setting_hf_checkbox)
    CheckBox headsetFeatureCheckBox;

    public static SettingsFragment newInstance()
    {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        preferencesManager = App.get(this).getPreferencesManager();
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_settings;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        toolbarProvider().updateToolbar(getString(R.string.action_settings), true);

        headsetFeatureCheckBox.setOnCheckedChangeListener(this);
        headsetFeatureCheckBox.setChecked(getHeadsetFeatureState());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        setHeadsetFeatureState(isChecked);
        Activity activity = getActivity();
        Intent intent = new Intent(activity, YaService.class);

        if (isChecked) activity.startService(intent);
        else activity.stopService(intent);
    }

    private boolean getHeadsetFeatureState()
    {
        return preferencesManager.getBoolean(HEADSET_FEATURE_STATE);
    }

    private void setHeadsetFeatureState(boolean state)
    {
        preferencesManager.setBoolean(HEADSET_FEATURE_STATE, state);
    }
}
