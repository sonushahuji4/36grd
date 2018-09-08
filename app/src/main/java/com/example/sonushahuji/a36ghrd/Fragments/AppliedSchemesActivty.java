package com.example.sonushahuji.a36ghrd.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.Model.AppliedSchemes;
import com.example.sonushahuji.a36ghrd.Model.Form_Model;
import com.example.sonushahuji.a36ghrd.Model.HomeModel;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.adapter.Applied_Adapter;
import com.example.sonushahuji.a36ghrd.adapter.HomeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_APPLIED_SCHEME;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_FRAGMENTHOME;
//import static com.example.sonushahuji.a36ghrd.Constants.Constants.accessKey;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.appliedschemes;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.deptId;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.scid;

public class AppliedSchemesActivty extends Fragment
{
    View v;
    private RecyclerView myrecyclerview;
    private List<AppliedSchemes> lstContact;
    // Session Manager Class
    SessionManager session;

    public AppliedSchemesActivty()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v=inflater.inflate(R.layout.activity_applied,container,false);
        myrecyclerview=(RecyclerView) v.findViewById(R.id.recyclerview);
        Applied_Adapter applied_adapter = new Applied_Adapter(getContext(),lstContact);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(applied_adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        lstContact = new ArrayList<>();
        // Session Manager
        session = new SessionManager(getContext());
        session.checkLogin();

//System.out.println("mobilesonu");
        HashMap<String, String> user = session.getUserDetails();
        final String accessKey = user.get(SessionManager.ACCESS_KEY);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,appliedschemes,

                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        System.out.println("890765deresponse");
                        try
                        {
                            JSONArray jsonArray= new JSONArray(response);
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                //System.out.println("mobilesonuinsideresponse");
                                System.out.println("mobilesonuinsideresponse"+jsonArray);
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                System.out.println("kukroja"+jsonObject1);
                                AppliedSchemes item =
                                        new AppliedSchemes
                                                (
                                                        jsonObject1.getString("appId"),
                                                        jsonObject1.getString("deptName"),
                                                        jsonObject1.getString("schemeName"),
                                                        jsonObject1.getString("applicationStatus"),
                                                        jsonObject1.getString("date"),
                                                        jsonObject1.getString("others"),
                                                        jsonObject1.getString("scId")
                                                );
                                //System.out.println("hi"+item);
                                lstContact.add(item);
                                //System.out.println("alok"+lstContact);

                            }

                            Applied_Adapter applied_adapter = new Applied_Adapter(getContext(),lstContact);
                            myrecyclerview.setAdapter(applied_adapter);
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
                        System.out.println("Erroorr..");
                        System.out.println("finderror"+error);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> param = new HashMap<>();
                param.put("accessKey",accessKey);
                return param;
            }
        };

        MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);

    }
}
