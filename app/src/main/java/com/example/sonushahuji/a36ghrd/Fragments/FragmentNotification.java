package com.example.sonushahuji.a36ghrd.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sonushahuji.a36ghrd.R;

public class FragmentNotification extends Fragment
{
    View v;
    public FragmentNotification()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.activity_fragmentnotification,container,false);
        return v;
    }
}
