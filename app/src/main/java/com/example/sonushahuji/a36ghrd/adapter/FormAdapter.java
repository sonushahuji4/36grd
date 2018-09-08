package com.example.sonushahuji.a36ghrd.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.sonushahuji.a36ghrd.Model.AllModel;
import com.example.sonushahuji.a36ghrd.Model.Form_Model;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.interfaces.OnBrowseClick;

import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.MyViewHolder>
{
    Context mContext;
    List<Form_Model> mData;//List<Class name > define data;
    OnBrowseClick onBrowseClick;

    public FormAdapter(Context mContext, List<Form_Model> mData, OnBrowseClick onBrowseClick)
    {
        this.mContext = mContext;
        this.mData = mData;
        this.onBrowseClick=onBrowseClick;
    }

    public FormAdapter(Context context, List<Form_Model> lstContact, Response.Listener<String> listener) {

        this.mContext = context;
        this.mData = lstContact;
        this.onBrowseClick= (OnBrowseClick) listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v;                                    //name of xml file
        v= LayoutInflater.from(mContext).inflate(R.layout.list_apply_form,viewGroup,false);
        MyViewHolder vHolder= new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i)
    {
        myViewHolder.uploadfile.setText(mData.get(i).getUploadfilename());

        myViewHolder.browserfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBrowseClick.buttonClick(1,mData.get(i).getUploadfilename());
            }
        });

        /*myViewHolder.browserfile.setOnClickListener
                (new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
             System.out.println("Browser Worked");
                //if everything is ok we will open image chooser
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mContext.startActivity(intent);

                onBrowseClick.buttonClick(1,mData.get(i).);

                //getImage(i);
               // startActivityForResult(i, 100);
            }
        });*/


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView uploadfile;
        private Button browserfile;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            uploadfile=(TextView) itemView.findViewById(R.id.uploadfile);
            browserfile=(Button) itemView.findViewById(R.id.browsefile);

        }
    }
}
