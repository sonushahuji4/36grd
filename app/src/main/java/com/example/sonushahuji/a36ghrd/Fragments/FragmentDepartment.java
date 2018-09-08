package com.example.sonushahuji.a36ghrd.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.Model.DepartmentStructure;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.adapter.DepartmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_DEPARTMENTS;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_FRAGMENTDEPARTMENT;
//import static com.example.sonushahuji.a36ghrd.Constants.Constants.accessKey;

//import com.example.sonushahuji.a36ghrd.adapter.DapartmentAdapter;


public class FragmentDepartment extends Fragment
{
    View v;
    private RecyclerView myrecyclerview;
    private List<DepartmentStructure> lstContact;
    // Session Manager Class
    SessionManager session;

    public FragmentDepartment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v=inflater.inflate(R.layout.activity_fragmentdepartment,container,false);
        myrecyclerview=(RecyclerView) v.findViewById(R.id.recyclerview);
        DepartmentAdapter recyclerViewAdapter=new DepartmentAdapter(getContext(),lstContact);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Session class instance
        session = new SessionManager(getContext()); //getApplicationContext()
        session.checkLogin();
        lstContact = new ArrayList<>();
        //System.out.println("hello");

        HashMap<String, String> user = session.getUserDetails();
        final String accessKey = user.get(SessionManager.ACCESS_KEY);
        System.out.print("acc4556767"+accessKey);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_DEPARTMENTS,

                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //System.out.println("Response");
                        // System.out.println("Inside onresponse");
                        try
                        {
                            JSONArray jsonArray= new JSONArray(response);
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                //System.out.println("Inside for loop"+jsonArray);
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                DepartmentStructure item =
                                        new DepartmentStructure
                                                (
                                                        jsonObject1.getString("deptName"),
                                                        jsonObject1.getString("description"),
                                                        jsonObject1.getString("deptId"),
                                                        jsonObject1.getString("image"),
                                                        jsonObject1.getString("subscribe")

                                                );
                                //System.out.println("hi"+item);
                                lstContact.add(item);
                                //System.out.println("alok"+lstContact);

                            }

                            DepartmentAdapter dapartmentAdapter = new DepartmentAdapter(getContext(),lstContact);
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
                System.out.println("accessKey123"+accessKey);
                return param;
            }
        };
        MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);

    }

}
