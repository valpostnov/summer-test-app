package com.postnov.android.summerschoolapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.postnov.android.summerschoolapp.artists.interfaces.FragmentsInteractor;
import com.postnov.android.summerschoolapp.artists.interfaces.ToolbarProvider;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by platon on 06.08.2016.
 */
public abstract class BaseFragment extends Fragment
{
    private Unbinder unbinder;
    private ToolbarProvider toolbarProvider;
    private FragmentsInteractor fragmentsInteractor;

    protected abstract int getLayout();

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        BaseActivity activity = (BaseActivity) context;

        if (activity != null)
        {
            toolbarProvider = activity;
            fragmentsInteractor = activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle)
    {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView()
    {
        if (unbinder != null) unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach()
    {
        toolbarProvider = null;
        fragmentsInteractor = null;
        super.onDetach();
    }

    protected FragmentsInteractor fragmentsInteractor()
    {
        return fragmentsInteractor;
    }

    protected ToolbarProvider toolbarProvider()
    {
        return toolbarProvider;
    }
}
