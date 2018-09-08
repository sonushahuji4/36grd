package com.example.sonushahuji.a36ghrd.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonushahuji.a36ghrd.Model.DepartmentStructure;
import com.example.sonushahuji.a36ghrd.Model.HomeModel;
import com.example.sonushahuji.a36ghrd.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
{
    Context mContext;
    List<HomeModel> mData;

    public HomeAdapter(Context mContext, List<HomeModel> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v;                                    //name of xml file
        v= LayoutInflater.from(mContext).inflate(R.layout.list_home,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        myViewHolder.announcement.setText(mData.get(i).getType());
        myViewHolder.department_name.setText(mData.get(i).getDepartment_name());
        myViewHolder.title.setText(mData.get(i).getTitle());
        myViewHolder.message.setText(mData.get(i).getMessage());
        myViewHolder.date.setText(mData.get(i).getDate());
        myViewHolder.link.setText(mData.get(i).getLink());
        myViewHolder.link.setMovementMethod(LinkMovementMethod.getInstance());
        //myViewHolder.imageView.setImageResource(mData.get(i).getGetDepartment_photo());
        //myViewHolder.imageView.setText(mData.get(i).getGetDepartment_photo());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView announcement;
        private TextView department_name;
        private TextView title;
        private TextView message;
        private TextView date;
        private TextView link;
        //private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            announcement=(TextView) itemView.findViewById(R.id.announcement);
            department_name=(TextView) itemView.findViewById(R.id.department_name);
            title=(TextView) itemView.findViewById(R.id.title);
            message=(TextView) itemView.findViewById(R.id.message);
            date=(TextView) itemView.findViewById(R.id.date);
            link=(TextView) itemView.findViewById(R.id.link);
            //imageView=(ImageView) itemView.findViewById(R.id.imageView);

        }
    }
}
