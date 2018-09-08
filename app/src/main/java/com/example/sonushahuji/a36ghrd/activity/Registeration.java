package com.example.sonushahuji.a36ghrd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.sonushahuji.a36ghrd.Constants.Constants;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_REGISTER;

public class Registeration extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText edittextemailid,edittextfirstname,edittextpincode,edittextsurname,edittextaddress,edittextcity,edittextfamily,edittextearning,edittextsalary,edittextpassword,edittextconfirmpassword;
    private Button registeruser;
    private EditText dob,setdate;
    private Spinner edittextcaste,edittextreligion,edittextoccupation;
    private TextView edittextmobile;
    private TextView loginlink;
    private RadioButton male,female,other;
    private RadioGroup gender_options;
    private ProgressDialog progressDialog;
    AlertDialog.Builder builder;

    //additional option
    boolean isGenderSelected;
    String stringGenderOptions;
    String date;
    String choice,choosecaste,optionselected,caste,religion,occupation;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        edittextemailid=(EditText) findViewById(R.id.edittextemailid);
        edittextfirstname = (EditText) findViewById(R.id.edittextfirstname);
        edittextsurname = (EditText) findViewById(R.id.edittextsurname);
        dob = (EditText) findViewById(R.id.dob);
        gender_options = (RadioGroup) findViewById(R.id.gender_options);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        other = (RadioButton) findViewById(R.id.other);
        edittextaddress = (EditText) findViewById(R.id.edittextaddress);
        edittextpincode=(EditText) findViewById(R.id.edittextpincode);
        edittextcaste = (Spinner) findViewById(R.id.edittextcaste);
        edittextreligion = (Spinner) findViewById(R.id.edittextreligion);
        edittextmobile = (TextView) findViewById(R.id.edittextmobile);
        edittextcity = (EditText) findViewById(R.id.edittextcity);
        edittextfamily = (EditText) findViewById(R.id.edittextfamily);
        edittextearning = (EditText) findViewById(R.id.edittextearning);
        edittextsalary = (EditText) findViewById(R.id.edittextsalary);
        edittextoccupation = (Spinner) findViewById(R.id.edittextoccupation);
        edittextpassword = (EditText) findViewById(R.id.edittextpassword);
        edittextconfirmpassword = (EditText) findViewById(R.id.edittextconfirmpassword);
        registeruser = (Button) findViewById(R.id.registeruser);

        progressDialog = new ProgressDialog(this);
        Bundle extras = getIntent().getExtras();
        choice = extras.getString("contactNo");
        edittextmobile.setText(choice);
        builder = new AlertDialog.Builder(Registeration.this);
        //progressDialog.setMessage();
        registeruser.setOnClickListener(this);
        ;
        chooseOptions();// for gender selection

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.caste,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edittextcaste.setAdapter(adapter);
        edittextcaste.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterreligion = ArrayAdapter.createFromResource(this,R.array.religion,android.R.layout.simple_spinner_dropdown_item);
        adapterreligion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edittextreligion.setAdapter(adapterreligion);
        edittextreligion.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapteroccupations = ArrayAdapter.createFromResource(this,R.array.occupations,android.R.layout.simple_spinner_dropdown_item);
        adapteroccupations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edittextoccupation.setAdapter(adapteroccupations);
        edittextoccupation.setOnItemSelectedListener(this);


        Calendar mCurrentDate = Calendar.getInstance();
        final int day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
        final int month=mCurrentDate.get(Calendar.MONTH);
        final int year=mCurrentDate.get(Calendar.YEAR);
        //final month=month+1;

        //date of birth
        dob.setText("Date of Birth");
        dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Registeration.this,
                        new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day)
                            {
                                month=month+1;
                                //String date=getInt().toString(dob);
                                dob.setText(day+"/"+month+"/"+year);
                                date=day+"/"+month+"/"+year;
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });
    }


    public void onClick(View view)
    {
        if(view == registeruser)
        {
            register();
        }
        if(view==loginlink)
        {
            startActivity(new Intent(Registeration.this,LoginActivity.class));
            //finish();
        }
    }


    public void chooseOptions()
    {

        gender_options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.male:
                        stringGenderOptions = "male";
                        isGenderSelected = true;
                        System.out.println(stringGenderOptions);
                        //register_gender.setError(null);
                        break;

                    case R.id.female:
                        stringGenderOptions = "female";
                        isGenderSelected = true;
                        System.out.println(stringGenderOptions);
                        break;

                    case R.id.other:
                        stringGenderOptions = "other";
                        isGenderSelected = true;
                        System.out.println(stringGenderOptions);
                        //register_gender.setError(null);
                        break;
                }
            }
        });
    }

    private  void register()
    {

        final String firstname = edittextfirstname.getText().toString().trim();
        final String surname = edittextsurname.getText().toString().trim();
        final String address = edittextaddress.getText().toString().trim();
        final String mobile = edittextmobile.getText().toString().trim();
        final String city = edittextcity.getText().toString().trim();
        final String family = edittextfamily.getText().toString().trim();
        final String earning = edittextearning.getText().toString().trim();
        final String salary = edittextsalary.getText().toString().trim();
        final String password = edittextpassword.getText().toString().trim();
        final String emailid = edittextemailid.getText().toString().trim();
        final String pincode= edittextpincode.getText().toString().trim();
        final String confirmpassword = edittextconfirmpassword.getText().toString().trim();

        if(emailid.equals("") || firstname.equals("") || surname.equals("") || address.equals("") || caste.equals("") || religion.equals("") || mobile.equals("") || city.equals("") || family.equals("") || earning.equals("") || salary.equals("")|| occupation.equals("") || password.equals("") || confirmpassword.equals("") || pincode.equals("") )
        {
            builder.setTitle("Parameter missing");
            builder.setMessage("Fill all the details");
            displayAlert("input_error");
        }
        else
        {
            if(!(password.equals(confirmpassword)))
            {
                builder.setTitle("Error");
                builder.setMessage("Passwords are not matching..");
                displayAlert("input_error");

            }
            else
            {

                //validation ......

                awesomeValidation.addValidation(Registeration.this, R.id.edittextfirstname, "[a-zA-Z\\s]+", R.string.name_error);
                awesomeValidation.addValidation(Registeration.this, R.id.edittextsurname, "[a-zA-Z\\s]+", R.string.name_error);
                awesomeValidation.addValidation(Registeration.this, R.id.edittextaddress, "[a-zA-Z\\s]+", R.string.address_error);
                awesomeValidation.addValidation(Registeration.this, R.id.edittextmobile, RegexTemplate.TELEPHONE, R.string.phone_error);
                awesomeValidation.addValidation(Registeration.this, R.id.edittextcity, "[a-zA-Z\\s]+", R.string.city_error);
                String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
                awesomeValidation.addValidation(Registeration.this, R.id.edittextpassword, regexPassword, R.string.password_error);
                awesomeValidation.addValidation(Registeration.this, R.id.edittextconfirmpassword, R.id.edittextpassword, R.string.confirm_password);
                awesomeValidation.addValidation(Registeration.this, R.id.edittextemailid, android.util.Patterns.EMAIL_ADDRESS, R.string.emailid_error);
                String pinCode = "^\\d{6,}$";
                awesomeValidation.addValidation(Registeration.this, R.id.edittextpincode, pinCode, R.string.pincode_error);


                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_REGISTER,
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
                                    String message = jsonObject.getString("status");
                                    System.out.println("chhhhhh  "+code);
                                    if(code.equals("true"))
                                    {
                                        Toast.makeText(Registeration.this,message,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Registeration.this,LoginActivity.class);
                                        intent.putExtra("contactNo", choice);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if(code.equals("false"))
                                    {
                                        builder.setTitle("Error");
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
                                System.out.println(error);
                                Toast.makeText(Registeration.this,"Error from Registeration",Toast.LENGTH_LONG).show();
                                error.printStackTrace();
                            }
                        })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String,String> param = new HashMap<>();

                        param.put("firstName",firstname);
                        param.put("lastName",surname);
                        param.put("dob",date);
                        param.put("sex", stringGenderOptions);
                        param.put("pincode",pincode);
                        param.put("address",address);
                        param.put("casteId",caste);
                        param.put("religionId",religion);
                        param.put("contactNo",choice);
                        param.put("city",city);
                        param.put("membersPerFamily",family);
                        param.put("noOfEarnings",earning);
                        param.put("salary",salary);
                        param.put("occupation",occupation);
                        param.put("password",password);
                        param.put("email",emailid);
                        return param;
                    }
                };
                MySingleton.getInstance(Registeration.this).addToRequestQueue(stringRequest);
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
                if(code.equals("input_error"))
                {

                    edittextpassword.setText("");
                    edittextconfirmpassword.setText("");

                }
                else if(code.equals("true"))
                {
                    finish();
                }
                else if(code.equals("false"))
                {
                   edittextfirstname.setText("");
                    edittextsurname.setText("");
                    dob.setText("");
                    edittextaddress.setText("");
                    edittextpincode.setText("");
                    //edittextcaste.setText("");
                    //edittextreligion.setText("");
                    //edittextmobile.setText("");
                    edittextcity.setText("");
                    edittextfamily.setText("");
                    edittextearning.setText("");
                    edittextsalary.setText("");
                    //edittextoccupation.setText("");
                    edittextpassword.setText("");
                    edittextconfirmpassword.setText("");

                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        if(adapterView.getId()==R.id.edittextcaste)
        {

            choosecaste=adapterView.getItemAtPosition(i).toString();
            optionselected=choosecaste.toLowerCase();
            caste="1";
            if(optionselected.equals("general"))
            {
                caste="1";
            }
            else if(optionselected.equals("sc"))
            {
                caste="2";
            }
            else if(optionselected.equals("obc"))
            {
                caste="3";
            }
            else if(optionselected.equals("sbc"))
            {
                caste="4";
            }
            else if(optionselected.equals("nt"))
            {
                caste="5";
            }
            else if(optionselected.equals("vjnt"))
            {
                caste="6";
            }
        }
        if(adapterView.getId()==R.id.edittextreligion)
        {
            String religionoption=adapterView.getItemAtPosition(i).toString();
            String religionselected=religionoption.toLowerCase();
            religion="1";
            if(religionselected.equals("hindu"))
            {
                religion="1";
            }
            else if(religionselected.equals("christian"))
            {
                religion="2";
            }
            else if(religionselected.equals("muslim"))
            {
                religion="3";
            }
            else if(religionselected.equals("sikh"))
            {
                religion="4";
            }
        }


        if(adapterView.getId()==R.id.edittextoccupation)
        {

            String occupationoption=adapterView.getItemAtPosition(i).toString();
            String optionoccupation=occupationoption.toLowerCase();
            occupation="1";
            if(optionoccupation.equals("professional"))
            {
                occupation="1";
            }
            else if(optionoccupation.equals("student"))
            {
                occupation="2";
            }
            else if(optionoccupation.equals("labour"))
            {
                occupation="3";
            }
            else if(optionoccupation.equals("farmer"))
            {
                occupation="4";
            }
            else if(optionoccupation.equals("self-employed"))
            {
                occupation="5";
            }
            else if(optionoccupation.equals("others"))
            {
                occupation="6";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }



}



