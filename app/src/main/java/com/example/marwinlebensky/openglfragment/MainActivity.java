package com.example.marwinlebensky.openglfragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Util.currentActivity = this;
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
        {
            public void onBackStackChanged()
            {
                int backCount = getSupportFragmentManager().getBackStackEntryCount();
                if (backCount == 0)
                {
                    finish();
                }
            }
        });

        if (savedInstanceState == null)
        {
            OpenGLFragment oglf = new OpenGLFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, oglf, "hi").addToBackStack(null).commit();
        }
    }
}