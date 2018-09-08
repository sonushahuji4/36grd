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
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.Model.AllModel;
import com.example.sonushahuji.a36ghrd.Model.HomeModel;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.adapter.All_Adapter;
import com.example.sonushahuji.a36ghrd.adapter.HomeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_FRAGMENTHOME;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.deptId;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.sessDeptId;

public class FragmentDepartment_All extends Fragment
{

    private EditText feedback;
    private ImageView sendfeedbackbtn;


    View v;
    private RecyclerView myrecyclerview;
    private List<AllModel> lstContact;

    // Session Manager Class
    SessionManager session;

    public FragmentDepartment_All()
    {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.activity_eligibility,container,false);
        myrecyclerview=(RecyclerView) v.findViewById(R.id.elg_recyclerview);
        feedback=(EditText) v.findViewById(R.id.feedback);
        sendfeedbackbtn=(ImageView) v.findViewById(R.id.sendfeedbackbtn);
        All_Adapter all_adapter = new All_Adapter(getContext(),lstContact);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(all_adapter);
        return v;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
       // String deptId = getArguments().getString("deptID");
        //System.out.println("sonu");

        // Session class instance
        session = new SessionManager(getContext()); //getApplicationContext()
        session.checkLogin();
        System.out.println("YOOO"+deptId);
        //String deptId = edt_name.getText().getStringExtra("deptId");
        lstContact = new ArrayList<>();

        HashMap<String, String> user = session.getUserDetails();
        final String accessKey = user.get(SessionManager.ACCESS_KEY);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FRAGMENTHOME+"allSchemes",

                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        System.out.println("sonu");
                        // System.out.println("Inside onresponse");
                        try
                        {
                            JSONArray jsonArray= new JSONArray(response);
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                System.out.println("jayesh"+jsonArray);
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                AllModel item =
                                        new  AllModel
                                                (

                                                        jsonObject1.getString("schemeName"),
                                                        jsonObject1.getString("schemeDescription"),
                                                        jsonObject1.getString("link"),
                                                        jsonObject1.getString("startDate"),
                                                        /*jsonObject1.getString("instant"),*/
                                                        jsonObject1.getString("endDate"),
                                                        jsonObject1.getString("scId")
                                                        );
                                //System.out.println("hi"+item);
                                lstContact.add(item);
                                //System.out.println("alok"+lstContact);

                            }

                            All_Adapter all_adapter = new All_Adapter(getContext(),lstContact);
                            myrecyclerview.setAdapter(all_adapter);
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
                System.out.println("FragementAll1234sonu"+deptId);
                param.put("deptId",sessDeptId);
                System.out.print("accessKey123"+accessKey);
                return param;
            }
        };

        MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);

    }



}
