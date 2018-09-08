package com.example.sonushahuji.a36ghrd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sonushahuji.a36ghrd.Model.AppliedSchemes;
import com.example.sonushahuji.a36ghrd.Model.HomeModel;
import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.activity.XYZ;

import java.util.List;

public class Applied_Adapter extends RecyclerView.Adapter<Applied_Adapter.MyViewHolder>
{
    Context mContext;
    List<AppliedSchemes> mData;


    public Applied_Adapter(Context mContext, List<AppliedSchemes> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v;                                    //name of xml file
        v= LayoutInflater.from(mContext).inflate(R.layout.list_applied,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        myViewHolder.appleid_id.setText(mData.get(i).getAppleid_id());
        myViewHolder.applied_date.setText(mData.get(i).getApplied_date());
        myViewHolder.appleid_scheme_name.setText(mData.get(i).getAppleid_scheme_name());
        myViewHolder.appleid_status.setText(mData.get(i).getAppleid_status());
        myViewHolder.applied_others.setText(mData.get(i).getAppleid_others());
        myViewHolder.deptname.setText(mData.get(i).getDeptname());
        myViewHolder.applicationstatus.setText(mData.get(i).getAppicationstatus());
       // Intent intent = new Intent(mContext, XYZ.class);
        //mContext.startActivity(intent);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView appleid_id;
        private TextView applied_date;
        private TextView appleid_scheme_name;
        private TextView appleid_status;
        private TextView applied_others;
        private TextView applicationstatus;
        private TextView deptname;
        //private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            appleid_id=(TextView) itemView.findViewById(R.id.appleid_id);
            applied_date=(TextView) itemView.findViewById(R.id.applied_date);
            appleid_scheme_name=(TextView) itemView.findViewById(R.id.appleid_scheme_name);
            appleid_status=(TextView) itemView.findViewById(R.id.appleid_status);
            applied_others=(TextView) itemView.findViewById(R.id.applied_others);
            applicationstatus=(TextView) itemView.findViewById(R.id.applicationstatus);
            deptname=(TextView) itemView.findViewById(R.id.deptname);
            //imageView=(ImageView) itemView.findViewById(R.id.imageView);

        }
    }

}
