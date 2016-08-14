package com.postnov.android.summerschoolapp.other;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by platon on 14.08.2016.
 */
public class RecyclerScrollListener extends RecyclerView.OnScrollListener
{
    private ScrollHelperAdapter scrollHelperAdapter;

    public RecyclerScrollListener(ScrollHelperAdapter scrollHelperAdapter)
    {
        super();
        this.scrollHelperAdapter = scrollHelperAdapter;
    }

    @Override
    public void onScrollStateChanged(RecyclerView rv, int newState)
    {
        super.onScrollStateChanged(rv, newState);

        switch (newState)
        {
            case RecyclerView.SCROLL_STATE_DRAGGING:

                final int itemCount = rv.getAdapter().getItemCount();
                final int childCount = rv.getChildCount();

                for (int i = 0; i < childCount; i++)
                {
                    final View child = rv.getChildAt(i);
                    final int adapterPosition = rv.getChildAdapterPosition(child);

                    if (adapterPosition == itemCount - 1)
                    {
                        scrollHelperAdapter.onLoadMore(itemCount);
                    }
                }

                break;
        }
    }
}
