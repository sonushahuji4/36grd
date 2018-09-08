package com.example.sonushahuji.a36ghrd.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentApplyForm;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentDepartment_All;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentDepartment_Eligible;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.adapter.ViewPageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_Confirm;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.deptId;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.scid;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.sessDeptId;

public class XYZ extends AppCompatActivity
{
    // Session Manager Class
    SessionManager session;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    private Toolbar toolbar;

    TextView mobile, password;
    String keyrecievd;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xyz);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        fab=(FloatingActionButton) findViewById(R.id.fab);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter=new ViewPageAdapter(getSupportFragmentManager());


        //adding fragments here
        adapter.AddFragment(new FragmentApplyForm(), "Upload All details");
        //adapter.AddFragment(new AppliedSchemesActivty(), "View All Schemes");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> user = session.getUserDetails();
                final String accessKey = user.get(SessionManager.ACCESS_KEY);


                //startActivity(new Intent(getContext(),HomeDashboard.class));
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Confirm,

                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //System.out.println("Response");
                                    // System.out.println("Inside onresponse");
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject obj = jsonArray.getJSONObject(0);
                                        String messgae = obj.getString("status");
                                        //Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                        //adding....
                                        String code = obj.getString("code");
                                        System.out.println("hhhhhhhh"+code);
                                        if (code.equals("true")) {
                                            Toast.makeText(XYZ.this, messgae, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(XYZ.this, ShowSchemes.class));

                                        } else if (code.equals("false")) {
                                           Toast.makeText(XYZ.this, messgae, Toast.LENGTH_LONG).show();
                                         //    obj.getString("message");
                                        }

                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("Erroorr..");
                                    System.out.println("finderror" + error);
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("deptId", sessDeptId);
                            param.put("scId", scid);
                            param.put("accessKey",accessKey);
                            return param;
                        }
                    };
                    MySingleton.getInstance(XYZ.this).addToRequestQueue(stringRequest);




                System.out.print("Clicked!!!");
            }

        });

    }
}
