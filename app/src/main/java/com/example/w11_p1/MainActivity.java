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
    Tab3Fragment tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        tab1 = new Tab1Fragment();
        tab2 = new Tab2Fragment();
        tab3 = new Tab3Fragment();
        adapter.addFragment(tab1, "Play");
        adapter.addFragment(tab2, "Leaderboard");
        adapter.addFragment(tab3, "Profile");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "tab1", tab1);
        getSupportFragmentManager().putFragment(outState, "tab2", tab2);
        getSupportFragmentManager().putFragment(outState, "tab3", tab3);
    }
    @Override
    public void onRestoreInstanceState(Bundle inState){
        tab1 = (Tab1Fragment) getSupportFragmentManager().getFragment(inState,"tab1");
        tab2 = (Tab2Fragment) getSupportFragmentManager().getFragment(inState,"tab2");
        tab3 = (Tab3Fragment) getSupportFragmentManager().getFragment(inState,"tab3");
        super.onSaveInstanceState(inState);

    }
}