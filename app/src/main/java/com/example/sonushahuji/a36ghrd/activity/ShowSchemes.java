package com.example.sonushahuji.a36ghrd.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.sonushahuji.a36ghrd.Fragments.AppliedSchemesActivty;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentApplyForm;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.adapter.ViewPageAdapter;

public class ShowSchemes extends AppCompatActivity
{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schemes);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter=new ViewPageAdapter(getSupportFragmentManager());

        adapter.AddFragment(new AppliedSchemesActivty(), "Schemes");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
