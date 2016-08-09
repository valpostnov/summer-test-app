package com.postnov.android.summerschoolapp.other;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.artists.interfaces.ToolbarProvider;
import com.postnov.android.summerschoolapp.base.BaseFragment;
import com.postnov.android.summerschoolapp.feature.YaService;
import com.postnov.android.summerschoolapp.utils.PreferencesManager;

import butterknife.BindView;

import static com.postnov.android.summerschoolapp.utils.PreferencesManager.HEADSET_FEATURE_STATE;

public class SettingsFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener
{
    private PreferencesManager preferencesManager;

    @BindView(R.id.setting_hf_checkbox)
    CheckBox headsetFeatureCheckBox;

    public static Fragment newInstance()
    {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        preferencesManager = PreferencesManager.getManager();
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
        ToolbarProvider toolbarProvider = getToolbarProvider();
        if (toolbarProvider != null)
            toolbarProvider.updateToolbar(getString(R.string.action_settings), true);

        headsetFeatureCheckBox.setOnCheckedChangeListener(this);
        headsetFeatureCheckBox.setChecked(getHeadsetFeatureState());
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
        return preferencesManager.getBoolean(HEADSET_FEATURE_STATE);
    }

    private void setHeadsetFeatureState(boolean state)
    {
        preferencesManager.setBoolean(HEADSET_FEATURE_STATE, state);
    }
}
