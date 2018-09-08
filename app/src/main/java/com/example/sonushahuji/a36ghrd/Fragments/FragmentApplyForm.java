package com.example.sonushahuji.a36ghrd.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sonushahuji.a36ghrd.Model.DepartmentStructure;
import com.example.sonushahuji.a36ghrd.Model.Form_Model;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.EndPoints;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.Singleton.VolleyMultipartRequest;
import com.example.sonushahuji.a36ghrd.activity.HomeDashboard;
import com.example.sonushahuji.a36ghrd.adapter.DepartmentAdapter;
import com.example.sonushahuji.a36ghrd.adapter.FormAdapter;
import com.example.sonushahuji.a36ghrd.interfaces.OnBrowseClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_Confirm;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_DEPARTMENTS;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_FILE;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_FRAGMENTDEPARTMENT;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.deptId;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.scid;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.sessDeptId;

public class FragmentApplyForm extends Fragment implements OnBrowseClick {
    View v;
    FloatingActionButton fab;
    private RecyclerView myrecyclerview;
    private List<Form_Model> lstContact;
    OnBrowseClick mContext;
    public FragmentApplyForm(){}
    SessionManager session;
    String accessKey;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v=inflater.inflate(R.layout.activity_fragmentapplyform,container,false);
        myrecyclerview=(RecyclerView) v.findViewById(R.id.form_recyclerview);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        FormAdapter formAdapter=new FormAdapter(getContext(),lstContact,this);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(formAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getContext());
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        accessKey = user.get(SessionManager.ACCESS_KEY);
        lstContact = new ArrayList<>();
        //System.out.println("hello");
        mContext = this;
       // fab.setOnClickListener((View.OnClickListener) this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FILE,

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
                                Form_Model item =
                                        new Form_Model
                                                (
                                                        jsonObject1.getString("documentName")
                                                );
                                //System.out.println("hi"+item);
                                lstContact.add(item);
                                //System.out.println("alok"+lstContact);

                            }

                            FormAdapter formAdapter = new FormAdapter(getContext(),lstContact,mContext);
                            myrecyclerview.setAdapter(formAdapter);
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
                param.put("deptId",sessDeptId);
                System.out.println("FragmentApplyFormsonu123"+sessDeptId);
                param.put("scId",scid);
                param.put("accessKey",accessKey);
                return param;
            }
        };
        MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public void buttonClick(int docId, String docName)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,12345);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 12345 && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                //calling the method uploadBitmap to upload image
                uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void uploadBitmap(final Bitmap bitmap)
    {

        //getting the tag from the edittext
        final String tags = "uploadedFile";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            //String messgae=obj.getString("status");
                            //Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            //adding....
                            //String code=obj.getString("code");
                           /* if(code=="true")
                            {
*//*
                                fab.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(getContext(),HomeDashboard.class));
                                    }
                                });

*//*
                               //flag=1;
                            }
                            else if(code=="false")
                            {
                                Toast.makeText(getContext(),messgae, Toast.LENGTH_LONG).show();
                                //obj.getString("message")
                            }

                           */ //startActivity(new Intent(getContext(),HomeDashboard.class));
                            //flag=1;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", tags);
                params.put("deptId",sessDeptId);
                System.out.println("browserfilesonu"+deptId);
                params.put("scId",scid);
                params.put("accessKey",accessKey);
                //params.put("tags", );
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


}
