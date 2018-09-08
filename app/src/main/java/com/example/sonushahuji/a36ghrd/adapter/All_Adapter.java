package com.example.sonushahuji.a36ghrd.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
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
import com.example.sonushahuji.a36ghrd.Fragments.FragmentApplyForm;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentDepartment_All;
import com.example.sonushahuji.a36ghrd.Fragments.FragmentHome;
import com.example.sonushahuji.a36ghrd.Model.AllModel;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.activity.DepartmentDashboard;
import com.example.sonushahuji.a36ghrd.activity.HomeDashboard;
import com.example.sonushahuji.a36ghrd.activity.Instantview;
import com.example.sonushahuji.a36ghrd.activity.LoginActivity;
import com.example.sonushahuji.a36ghrd.activity.XYZ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.FEEDBACK;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.INSTANCEVIEW;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.INSTANCEVIEW_ID;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_LOGIN;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.deptId;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.scid;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.sessDeptId;
public class All_Adapter extends RecyclerView.Adapter<All_Adapter.MyViewHolder>
{

    // Session Manager Class
    SessionManager session;

    String message;
    Context mContext;
    List<AllModel> mData;//List<Class name > define data;

    public All_Adapter(Context mContext, List<AllModel> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v;                                    //name of xml file
        v= LayoutInflater.from(mContext).inflate(R.layout.activity_department_all,viewGroup,false);
        MyViewHolder vHolder= new MyViewHolder(v);
        session = new SessionManager(mContext);
        session.checkLogin();

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i)
    {
        myViewHolder.all_title.setText(mData.get(i).getAll_title());
        myViewHolder.all_description.setText(mData.get(i).getAll_description());
        myViewHolder.all_link.setText(mData.get(i).getAll_link());
        myViewHolder.all_link.setMovementMethod(LinkMovementMethod.getInstance());
        /*myViewHolder.instanceview.setText(mData.get(i).getAll_instanceview());*/
        myViewHolder.date.setText(mData.get(i).getAll_date());
        myViewHolder.date2.setText(mData.get(i).getAll_date2());
        myViewHolder.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                scid = String.valueOf(mData.get(i).getScId());
                Intent intent = new Intent(mContext, XYZ.class);
                mContext.startActivity(intent);
            }
        });

        myViewHolder.instanceview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                scid = String.valueOf(mData.get(i).getScId());
                System.out.print("dingdong"+scid);
                Intent intent = new Intent(mContext, Instantview.class);
                mContext.startActivity(intent);
            }
        });
        myViewHolder.feedbacksend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                scid = String.valueOf(mData.get(i).getScId());
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
                                                System.out.println("allllloooo"+code);
                                                System.out.println("allllloooo86868"+message);

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
                                    param.put("deptID",deptId);
                                    param.put("feedback",message);
                                    param.put("accessKey",accessKey);
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

        private TextView all_title;
        private TextView all_description;
        private TextView all_link;
        private Button instanceview,apply;
        private TextView date;
        private TextView date2;
        private ImageView feedbacksend;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            all_title=(TextView) itemView.findViewById(R.id.all_title);
            all_description=(TextView) itemView.findViewById(R.id.all_description);
            all_link=(TextView) itemView.findViewById(R.id.all_link);
            //all_link.setMovementMethod(LinkMovementMethod.getInstance());
            instanceview=(Button) itemView.findViewById(R.id.instanceview);
            date=(TextView) itemView.findViewById(R.id.date);
            date2=(TextView) itemView.findViewById(R.id.date2);
            apply=(Button) itemView.findViewById(R.id.apply);
            feedbacksend=(ImageView) itemView.findViewById(R.id.feedbacksend);

        }
    }
}
