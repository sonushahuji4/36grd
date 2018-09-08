package com.example.sonushahuji.a36ghrd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentDepartment_All;
import com.example.sonushahuji.a36ghrd.Model.AllModel;
import com.example.sonushahuji.a36ghrd.Model.DepartmentStructure;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.activity.DepartmentDashboard;
import com.example.sonushahuji.a36ghrd.activity.XYZ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_FRAGMENTHOME;
//import static com.example.sonushahuji.a36ghrd.Constants.Constants.accessKey;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.deptId;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.sessDeptId;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.MyViewHolder>
{

    Context mContext;
    List<DepartmentStructure> mData;
    Button clickedButton;
    SessionManager session;
    public DepartmentAdapter(Context mContext, List<DepartmentStructure> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }

    //done


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v;                                    //name of xml file
        v= LayoutInflater.from(mContext).inflate(R.layout.list_department,viewGroup,false);
        MyViewHolder vHolder= new MyViewHolder(v);
        session = new SessionManager(mContext);
        session.checkLogin();
        return vHolder;
    }
//done


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i)
    {


        //myViewHolder.department_id.setText(mData.get(i).getDepartment_id());
        myViewHolder.department_name.setText(mData.get(i).getDepartment_name());
        myViewHolder.getDepartment_description.setText(mData.get(i).getGetDepartment_description());
        myViewHolder.follow.setText(mData.get(i).getSubscribe());
        //myViewHolder.getDepartment_photo.setText
        byte[] decodedString = Base64.decode(mData.get(i).getGetDepartment_photo(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        myViewHolder.getDepartment_photo.setImageBitmap(decodedByte);
        myViewHolder.list_department.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(mContext, DepartmentDashboard.class);
                System.out.println("scId"+String.valueOf(mData.get(i).getDepartment_id()));
                deptId = String.valueOf(mData.get(i).getDepartment_id());
                sessDeptId= deptId;
                mContext.startActivity(intent);
            }
        });
        myViewHolder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                //code
                System.out.print("yesss!");
                deptId = String.valueOf(mData.get(i).getDepartment_id());
                clickedButton = (Button)view;
                HashMap<String, String> user = session.getUserDetails();
                final String accessKey = user.get(SessionManager.ACCESS_KEY);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FRAGMENTHOME+"subscribe",
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
                                        String follow = jsonObject1.getString("follow");
                                        System.out.println("jayesh"+follow);
                                        if(follow.equals("1"))
                                            clickedButton.setText("Unfollow");
                                        else
                                            clickedButton.setText("Follow");
                                    }

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
                        param.put("deptId",deptId);
                        System.out.print("accessKey123"+accessKey);
                        return param;
                    }
                };

                MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);




            }
        });

    }

    //done

    @Override
    public int getItemCount()

    {
        return mData.size();
    }

    //problem here

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //private TextView department_id;
        private TextView department_name;
        private TextView getDepartment_description;
       private ImageView getDepartment_photo;
        //private ImageView img;
        private  Button follow;
        private LinearLayout list_department;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //department_id=(TextView) itemView.findViewById(R.id.department_id);
            department_name=(TextView) itemView.findViewById(R.id.department_name);
            getDepartment_description=(TextView) itemView.findViewById(R.id.getDepartment_description);
            getDepartment_photo=(ImageView) itemView.findViewById(R.id.getDepartment_photo);
            list_department=(LinearLayout) itemView.findViewById(R.id.list_department);
            follow=(Button) itemView.findViewById(R.id.follow);
        }
    }

}
