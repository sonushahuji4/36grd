package com.example.sonushahuji.a36ghrd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_OTP;

public class OTP extends AppCompatActivity implements View.OnClickListener
{
private EditText otpinput;
private Button otpbtn;
private TextView checkotp;
String keyrecievd;
android.app.AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpinput=(EditText) findViewById(R.id.otpinput);
        otpbtn=(Button) findViewById(R.id.otpbtn);
        checkotp=(TextView) findViewById(R.id.checkotp);
        otpbtn.setOnClickListener(this);
        builder = new android.app.AlertDialog.Builder(OTP.this);
        Bundle bundle= getIntent().getExtras();
        keyrecievd=bundle.getString("sacredkey");
        System.out.print("yooo"+keyrecievd);

    }

    @Override
    public void onClick(View view)
    {
        if(view==otpbtn)
        {
           final String useropt=otpinput.getText().toString().trim();
           if(useropt.equals(""))
           {
               builder.setTitle("Parameter missing");
               builder.setMessage("Fill all the details");
               displayAlert("input_error");
           }
           else
           {
               StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_OTP,
                       new Response.Listener<String>()
                       {
                           @Override
                           public void onResponse(String response)
                           {

                               try
                               {
                                   JSONArray jsonArray= new JSONArray(response);
                                   JSONObject  jsonObject = jsonArray.getJSONObject(0);
                                   String code = jsonObject.getString("code");
                                   String message = jsonObject.getString("message");
                                   if(code.equals("false"))
                                   {
                                       builder.setTitle("OTP");
                                       builder.setMessage(message);
                                       displayAlert(code);
                                   }
                                   else if(code.equals("true"))
                                   {
                                       builder.setTitle("OTP");
                                       builder.setMessage(message);
                                       displayAlert(code);

                                       Intent intent = new Intent(OTP.this,LoginActivity.class);
                                       //Bundle bundle = new Bundle();
                                       //bundle.putString("mobile",jsonObject.getString("contactNo"));
                                       //intent.putExtras(bundle);
                                       startActivity(intent);
                                       finish();
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

                           }
                       })
               {
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError
                   {
                       Map<String,String> param = new HashMap<>();
                       param.put("otp",useropt);
                       param.put("contactNo",keyrecievd);
                       //param.put("contactNo",mobile);
                       return param;
                   }
               };
               MySingleton.getInstance(OTP.this).addToRequestQueue(stringRequest);
           }
        }

    }


    public void displayAlert(final String code)
    {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
