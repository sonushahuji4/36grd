package com.example.sonushahuji.a36ghrd.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.Fragments.AppliedSchemesActivty;
import com.example.sonushahuji.a36ghrd.Fragments.FragemntChatbot;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentDepartment;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentEligible;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentHome;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentNotification;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.adapter.ViewPageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_DEPARTMENTS;

public class HomeDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    private Toolbar toolbar;

    TextView mobile, password;
    String keyrecievd;
    TextView username;

    // Session Manager Class
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dashboard);

        // Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter=new ViewPageAdapter(getSupportFragmentManager());

        //adding fragments here

        adapter.AddFragment(new FragmentDepartment(), "Departments");
        adapter.AddFragment(new FragmentHome(), "Notifications");
        adapter.AddFragment(new FragemntChatbot(), "Help");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        //tabLayout.getTabAt(1).setIcon(R.drawable.departmenticon);
        //tabLayout.getTabAt(0).setIcon(R.drawable.finalhomeicon2);
        //tabLayout.getTabAt(2).setIcon(R.drawable.notificationicon2);
        //tabLayout.getTabAt(3).setIcon(R.drawable.help);
        //tabLayout.getTabAt(4).setIcon(R.drawable.eligible1);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HashMap<String, String> user = session.getUserDetails();
        final String accessKey = user.get(SessionManager.ACCESS_KEY);
        final String name =user.get(SessionManager.NAME);
        final String email =user.get(SessionManager.EMAIL);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.appleid_scheme)
        {
            Intent myIntent = new Intent(this, ShowSchemes.class);
            startActivity(myIntent);
           // startActivity(new Intent(HomeDashboard.this,AppliedSchemesActivty.class));
        } else if (id == R.id.nav_manage)
        {
            session.logoutUser();
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);

        }
        else if (id == R.id.nav_share)
        {
            Intent myIntent = new Intent(this, ChatRoomActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}