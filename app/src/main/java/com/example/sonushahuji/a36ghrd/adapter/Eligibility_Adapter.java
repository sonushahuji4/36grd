package com.example.sonushahuji.a36ghrd.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.Model.AllModel;
import com.example.sonushahuji.a36ghrd.Model.Eligibility;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.activity.DepartmentDashboard;
import com.example.sonushahuji.a36ghrd.activity.Instantview;
import com.example.sonushahuji.a36ghrd.activity.XYZ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.FEEDBACK;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.deptId;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.elg_id;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.scid;

public class Eligibility_Adapter extends RecyclerView.Adapter<Eligibility_Adapter.MyViewHolder>
{
    // Session Manager Class
    SessionManager session;

    Context mContext;
    List<Eligibility> mData;//List<Class name > define data;
    String message;
    public Eligibility_Adapter(Context mContext, List<Eligibility> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v;                                    //name of xml file
        v= LayoutInflater.from(mContext).inflate(R.layout.list_eligibility,viewGroup,false);
        MyViewHolder vHolder= new MyViewHolder(v);
        session = new SessionManager(mContext);
        session.checkLogin();

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i)
    {
        myViewHolder.elg_title.setText(mData.get(i).getElg_title());
        myViewHolder.elg_description.setText(mData.get(i).getElg_description());
        myViewHolder.elg_link.setText(mData.get(i).getElg_link());
        myViewHolder.elg_instance_view.setText(mData.get(i).getElg_instance_view());
        myViewHolder.elg_date.setText(mData.get(i).getElg_date());
        myViewHolder.link.setText(mData.get(i).getLink());

        //Intent intent = new Intent(mContext, DepartmentDashboard.class);
        //System.out.println("elgsonu"+String.valueOf(mData.get(i).getElg_id()));
        deptId = String.valueOf(mData.get(i).getElg_id());
        //mContext.startActivity(intent);
       /* myViewHolder.elg_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
.
               *//* Intent intent = new Intent(mContext, XYZ.class);
                mContext.startActivity(intent);*//*
                Intent intent = new Intent(mContext, DepartmentDashboard.class);
                System.out.println("elgsonu"+String.valueOf(mData.get(i).getElg_id()));
                deptId = String.valueOf(mData.get(i).getElg_id());
                mContext.startActivity(intent);
            }
        });*/
        myViewHolder.elg_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                scid = String.valueOf(mData.get(i).getElg_id());
                Intent intent = new Intent(mContext, XYZ.class);
                mContext.startActivity(intent);
            }
        });

        myViewHolder.elg_instance_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                scid = String.valueOf(mData.get(i).getElg_id());
                System.out.print("dingdong"+scid);
                Intent intent = new Intent(mContext, Instantview.class);
                mContext.startActivity(intent);
            }
        });
        myViewHolder.feedbacksend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                scid = String.valueOf(mData.get(i).getElg_id());
                System.out.println("Do something...");
                final AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
                final View mview=LayoutInflater.from(mContext).inflate(R.layout.feedback_dialog,null);
                final EditText feedback=(EditText) mview.findViewById(R.id.feedback);
                ImageView sendfeedbackbtn=(ImageView) mview.findViewById(R.id.sendfeedbackbtn);
                sendfeedbackbtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //feedback = edittextmobile.getText().toString().trim();
                        if(!feedback.getText().toString().isEmpty())
                        {
                            message=feedback.getText().toString().trim();
                            System.out.println("message1234"+message);
                            //Toast.makeText(mContext,"sending feedback",Toast.LENGTH_LONG).show();

                            HashMap<String, String> user = session.getUserDetails();
                            final String accessKey = user.get(SessionManager.ACCESS_KEY);

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, FEEDBACK,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response)
                                        {
                                            try
                                            {
                                                JSONArray jsonArray= new JSONArray(response);
                                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                                String code = jsonObject.getString("code");
                                                String message = jsonObject.getString("status");

                                                if(code.equals("true"))
                                                {
                                                    Toast.makeText(mContext,message,Toast.LENGTH_LONG).show();
                                                    //String contactNo = jsonObject.getString("contactNo");
                                                    Intent intent = new Intent(mContext,DepartmentDashboard.class);
                                                    mContext.startActivity(intent);


                                                    //mContext.finish();

                                                }
                                                else if(code.equals("false"))
                                                {
                                                    Toast.makeText(mContext,"Error while sending feedback",Toast.LENGTH_LONG).show();
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

                                            Toast.makeText(mContext,"Bad connectivity",Toast.LENGTH_LONG).show();
                                            error.printStackTrace();

                                        }
                                    })
                            {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError
                                {
                                    Map<String,String> param = new HashMap<>();
                                    param.put("scId",scid);
                                    param.put("accessKey",accessKey);
                                    param.put("feedback",message);
                                    return param;
                                }
                            };
                            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

                        }
                        else
                        {
                            Toast.makeText(mContext,"Error while sending feedback",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setView(mview);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView elg_title;
        private TextView elg_description;
        private TextView elg_link;
        private Button elg_instance_view,elg_apply;
        private TextView elg_date;
        private ImageView feedbacksend;
        private TextView  link;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            elg_title=(TextView) itemView.findViewById(R.id.elg_title);
            elg_description=(TextView) itemView.findViewById(R.id.elg_description);
            elg_link=(TextView) itemView.findViewById(R.id.elg_link);
            elg_instance_view=(Button) itemView.findViewById(R.id.elg_instance_view);
            elg_date=(TextView) itemView.findViewById(R.id.elg_date);
            elg_apply=(Button) itemView.findViewById(R.id.elg_apply);
            link=(TextView) itemView.findViewById(R.id.link);
            feedbacksend=(ImageView) itemView.findViewById(R.id.feedbacksend);
        }
    }
}
