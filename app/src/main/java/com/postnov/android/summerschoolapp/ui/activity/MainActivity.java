package com.postnov.android.summerschoolapp.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.postnov.android.summerschoolapp.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
