package com.example.sonushahuji.a36ghrd.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sonushahuji.a36ghrd.Model.Message;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;
import com.example.sonushahuji.a36ghrd.Singleton.MySingleton;
import com.example.sonushahuji.a36ghrd.adapter.MessageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.CHATBOT;
import static com.example.sonushahuji.a36ghrd.Constants.Constants.CHATBOTURL;

public class ChatRoomActivity extends AppCompatActivity implements View.OnClickListener
{
    String usermessage;
    String username;
    String accessKey;
    // Session Manager Class
    SessionManager session;

    //Progress dialog
    private ProgressDialog dialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //initializing button and edittext
        buttonSend = (Button) findViewById(R.id.buttonSend);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        //Displaying dialog while the chat room is being ready
        dialog = new ProgressDialog(this);
        dialog.setMessage("Opening chat room");
        dialog.show();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing message arraylist

        session = new SessionManager(getApplicationContext()); //getApplicationContext()
        session.checkLogin();


        //Initializing message arraylist
        messages = new ArrayList<>();


        HashMap<String, String> user = session.getUserDetails();
        accessKey = user.get(SessionManager.ACCESS_KEY);
        username = user.get(SessionManager.NAME);

        buttonSend.setOnClickListener(ChatRoomActivity.this);

    }

    @Override
    public void onClick(View view)
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
                                    System.out.println("alok"+item);
                                    System.out.println("alok12345"+jsonObject1.getString("message"));

                                }
                                CHATBOT=786;
                                MessageAdapter messageAdapter = new MessageAdapter(getApplicationContext(), messages, username);
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

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        }
        editTextMessage.setText("");

    }
}
