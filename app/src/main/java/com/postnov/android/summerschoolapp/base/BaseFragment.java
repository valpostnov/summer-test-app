package com.postnov.android.summerschoolapp.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.postnov.android.summerschoolapp.artists.ArtistsActivity;
import com.postnov.android.summerschoolapp.artists.interfaces.FragmentTransactionManager;
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
    private FragmentTransactionManager fragmentTransactionManager;
    private ArtistsActivity activity = (ArtistsActivity) getActivity();

    protected abstract int getLayout();

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (activity != null)
        {
            toolbarProvider = (ToolbarProvider) getActivity();
            fragmentTransactionManager = (FragmentTransactionManager) getActivity();
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
        fragmentTransactionManager = null;
        super.onDetach();
    }

    protected FragmentTransactionManager getFragmentTransactionManager()
    {
        return fragmentTransactionManager;
    }

    protected ToolbarProvider getToolbarProvider()
    {
        return toolbarProvider;
    }
}
