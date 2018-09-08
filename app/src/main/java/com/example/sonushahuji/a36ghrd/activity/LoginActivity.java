package com.example.sonushahuji.a36ghrd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.Constants.Constants;
import com.example.sonushahuji.a36ghrd.PhoneAuthActivity;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SharedPrefManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_LOGIN;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText edittextmobile, edittextpassword;
    private Button loginuser;
    private TextView edittextregisterlink;
    AlertDialog.Builder builder;
    private String mobile;
    private String password,choice;


    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        edittextmobile = (EditText) findViewById(R.id.edittextmobile);
        edittextpassword = (EditText) findViewById(R.id.edittextpassword);
        loginuser = (Button) findViewById(R.id.loginuser);
        edittextregisterlink = (TextView) findViewById(R.id.edittextregisterlink);
       try {
           Bundle extras = getIntent().getExtras();
           choice = extras.getString("contactNo");
           edittextmobile.setText(choice);
       }
       catch (Exception e){

       }
        builder = new AlertDialog.Builder(LoginActivity.this);

           loginuser.setOnClickListener(this);
        edittextregisterlink.setOnClickListener(this);

    }

    public void onClick(View view) {
        if (view == loginuser) {
            userLogin();
        }

        if (view == edittextregisterlink) {
            startActivity(new Intent(this, PhoneAuthActivity.class));
            finish();
        }
    }

    private void userLogin()
    {
         mobile = edittextmobile.getText().toString().trim();
         password = edittextpassword.getText().toString().trim();


        if (mobile.equals("") || password.equals("")) {
            builder.setTitle("Parameter missing");
            builder.setMessage("Kindly fill all details");
            displayAlert("input_error");
        }
        else
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            try
                            {
                                JSONArray jsonArray= new JSONArray(response);
                                JSONObject  jsonObject = jsonArray.getJSONObject(0);

                                String code = jsonObject.getString("code");
                                String message = jsonObject.getString("status");

                                if(code.equals("true"))
                                {
                                    String accessKey = jsonObject.getString("accessKey");
                                    String firstName = jsonObject.getString("firstName");
                                    String lastName = jsonObject.getString("lastName");
                                    String email = jsonObject.getString("email");
                                    String contactNo = jsonObject.getString("contactNo");

                                    session.createLoginSession(contactNo, firstName+" "+lastName,email,accessKey);
                                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this,HomeDashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else if(code.equals("false"))
                                {
                                    builder.setTitle("Login Response");
                                    builder.setMessage(message);
                                    displayAlert(code);

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

                            Toast.makeText(LoginActivity.this,"Bad connectivity",Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String,String> param = new HashMap<>();
                    param.put("password",password);
                    param.put("contactNo",mobile);
                    return param;
                }
            };
            MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
        }

    }
    public void displayAlert(final String code)
    {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

                    edittextmobile.setText("");
                    edittextpassword.setText("");

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
