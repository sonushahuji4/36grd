package com.example.sonushahuji.a36ghrd.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.adapter.ViewPageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.INSTANCEVIEW;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_LOGIN;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_SUMMERIZER;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.deptId;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.instantdata;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.scid;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.sessDeptId;

public class Instantview extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;
    // Session Manager Class
    SessionManager session;
    private Toolbar toolbar;



    private TextView instanceview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instantview);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter=new ViewPageAdapter(getSupportFragmentManager());


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        instanceview = (TextView) findViewById(R.id.instanceview);
        /*instanceview.setText(instantdata);*/
        HashMap<String, String> user = session.getUserDetails();
        final String accessKey = user.get(SessionManager.ACCESS_KEY);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SUMMERIZER+"summarizer",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONArray jsonArray= new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            instanceview.setText(jsonObject.getString("data"));

                            /*jsonObject1.getString("instant"),*/

                        }
                        catch (JSONException e)
                        {

                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> param = new HashMap<>();
                param.put("accessKey",accessKey);
                param.put("deptId",sessDeptId);
                param.put("scId",scid);

                return param;
            }
        };
        MySingleton.getInstance(Instantview.this).addToRequestQueue(stringRequest);

    }

}
