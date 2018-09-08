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
import com.example.sonushahuji.a36ghrd.Model.DepartmentStructure;
import com.example.sonushahuji.a36ghrd.Model.HomeModel;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.adapter.DepartmentAdapter;
import com.example.sonushahuji.a36ghrd.adapter.HomeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_FRAGMENTHOME;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_NOTIFICATIONS;
//import static com.example.sonushahuji.a36ghrd.Constants.Constants.accessKey;

public class FragmentHome extends Fragment
{
    View v;
    private RecyclerView myrecyclerview;
    private List<HomeModel> lstContact;

    // Session Manager Class
    SessionManager session;

    public FragmentHome()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.activity_fragmenthome,container,false);
        myrecyclerview=(RecyclerView) v.findViewById(R.id.home_recyclerview);
        HomeAdapter homeAdapter = new HomeAdapter(getContext(),lstContact);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(homeAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getContext()); //getApplicationContext()
        session.checkLogin();
        lstContact = new ArrayList<>();

        HashMap<String, String> user = session.getUserDetails();
        final String accessKey = user.get(SessionManager.ACCESS_KEY);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NOTIFICATIONS,

                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONArray jsonArray= new JSONArray(response);
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                //System.out.println("Inside for loop"+jsonArray);
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                 HomeModel item =
                                        new HomeModel
                                                (
                                                        jsonObject1.getString("type"),
                                                        jsonObject1.getString("deptName"),
                                                        jsonObject1.getString("title"),
                                                        jsonObject1.getString("message"),
                                                        jsonObject1.getString("dateOfCreation").substring(0,10),
                                                        jsonObject1.getString("link")
                                                );
                                //System.out.println("hi"+item);
                                lstContact.add(item);
                                //System.out.println("alok"+lstContact);

                            }

                            HomeAdapter dapartmentAdapter = new HomeAdapter(getContext(),lstContact);
                            myrecyclerview.setAdapter(dapartmentAdapter);
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

