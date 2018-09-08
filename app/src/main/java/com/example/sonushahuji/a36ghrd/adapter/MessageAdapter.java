package com.example.sonushahuji.a36ghrd.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sonushahuji.a36ghrd.Model.Message;
import com.example.sonushahuji.a36ghrd.R;

import java.util.ArrayList;

import static com.example.sonushahuji.a36ghrd.Constants.Constants.CHATBOT;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    //Button to send new message on the thread
    private Button buttonSend;

    //EditText to send new message on the thread
    private EditText editTextMessage;

    private ArrayList<Message> messages;
    private Context context;
    private String username;

    //Tag for tracking self message
    private int SELF = 786;

    public MessageAdapter(Context context, ArrayList<Message> messages,String username)
    {
        this.username=username;
        this.messages = messages;
        this.context=context;
    }

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        this.messages = messages;
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Creating view
        View itemView;
        System.out.println("gggghjjdgjhs   "+viewType);
        //if view type is self
        if (viewType == CHATBOT)
        {
            //Inflating the layout self
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_thread, parent, false);
            System.out.println("INSIED_chat_thread  ");
        } else
            {
            //else inflating the layout others
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_thread_other, parent, false);
                System.out.println("INSIED_chat_thread_other  ");
        }
        //returing the view
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Adding messages to the views
        Message message = messages.get(position);
        //holder.textViewMessage.setText(message.getMessage());
        //holder.buttonSend.setOnClickListener(new View.OnClickListener()
        //holder.textViewTime.setText(message.getName()+", "+message.getSentAt());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    //Initializing views
    public class ViewHolder extends RecyclerView.ViewHolder {
        public EditText textViewMessage;
        private Button buttonSend;
        public TextView textViewTime;

        public ViewHolder(View itemView) {
            super(itemView);
           // textViewMessage = (EditText) itemView.findViewById(R.id.textViewMessage);
            //buttonSend=(Button) itemView.findViewById(R.id.buttonSend);
            //textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }
    }
}
