package com.example.sonushahuji.a36ghrd.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.sonushahuji.a36ghrd.Fragments.FragemntChatbot;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentDepartment;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentDepartment_All;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentDepartment_Eligible;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentEligible;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentHome;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentNotification;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.adapter.ViewPageAdapter;

public class DepartmentDashboard extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_dashboard);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter=new ViewPageAdapter(getSupportFragmentManager());


        //adding fragments here

        adapter.AddFragment(new FragmentDepartment_All(), "All");
        adapter.AddFragment(new FragmentDepartment_Eligible(), "Eligible");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
