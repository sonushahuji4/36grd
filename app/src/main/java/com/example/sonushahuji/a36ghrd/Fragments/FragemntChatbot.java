package com.example.sonushahuji.a36ghrd.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.Model.AllModel;
import com.example.sonushahuji.a36ghrd.Model.Message;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.adapter.All_Adapter;
import com.example.sonushahuji.a36ghrd.adapter.DepartmentAdapter;
import com.example.sonushahuji.a36ghrd.adapter.MessageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.CHATBOTURL;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.URL_FRAGMENTHOME;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.deptId;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.sessDeptId;

public class FragemntChatbot extends Fragment {
    // Session Manager Class
    SessionManager session;
    String usermessage;
    String username;
    String accessKey;
    View v;

    //Recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    //ArrayList of messages to store the thread messages
    private ArrayList<Message> messages;

    //Button to send new message on the thread
    private Button buttonSend;

    //EditText to send new message on the thread
    private EditText editTextMessage;


    public FragemntChatbot() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_fragmentchatbot, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        //initializing button and edittext
       // buttonSend = (Button) v.findViewById(R.id.buttonSend);
      //  editTextMessage = (EditText) v.findViewById(R.id.editTextMessage);
        MessageAdapter messageAdapter = new MessageAdapter(getContext(), messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(messageAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Session class instance
        session = new SessionManager(getContext()); //getApplicationContext()
        session.checkLogin();


        //Initializing message arraylist
        messages = new ArrayList<>();

/*
        //initializing button and edittext
        buttonSend = (Button) v.findViewById(R.id.buttonSend);
        editTextMessage = (EditText) v.findViewById(R.id.editTextMessage);

*/


        HashMap<String, String> user = session.getUserDetails();
        accessKey = user.get(SessionManager.ACCESS_KEY);
        username = user.get(SessionManager.NAME);
    }

    public void doSomething(View view)
    {
        usermessage = editTextMessage.getText().toString().trim();
        if (username.equals("")) {

        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CHATBOTURL,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    Message item =
                                            new Message
                                                    (
                                                            jsonObject1.getString("message"),
                                                            jsonObject1.getString("userName")
                                                    );
                                    //System.out.println("hi"+item);
                                    messages.add(item);
                                    //System.out.println("alok"+lstContact);

                                }

                                MessageAdapter messageAdapter = new MessageAdapter(getContext(), messages, username);
                                recyclerView.setAdapter(messageAdapter);
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Erroorr..");
                            System.out.println("finderror" + error);
                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("accessKey", accessKey);
                    //System.out.println("FragementAll1234sonu"+deptId);
                    param.put("userName", username);
                    param.put("message", usermessage);
                    //System.out.print("accessKey123"+accessKey);
                    return param;
                }
            };

            MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);

        }
    }
}