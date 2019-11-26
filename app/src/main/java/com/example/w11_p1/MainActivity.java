package com.example.w11_p1;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    Tab1Fragment tab1;
    Tab2Fragment tab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        tab1 = new Tab1Fragment();
        tab2 = new Tab2Fragment();
        adapter.addFragment(tab1, "Play");
        adapter.addFragment(tab2, "Leaderboard");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "tab1", tab1);
        getSupportFragmentManager().putFragment(outState, "tab2", tab2);
    }
    @Override
    public void onRestoreInstanceState(Bundle inState){
        tab1 = (Tab1Fragment) getSupportFragmentManager().getFragment(inState,"tab1");
        tab2 = (Tab2Fragment) getSupportFragmentManager().getFragment(inState,"tab2");
        super.onSaveInstanceState(inState);

    }
}