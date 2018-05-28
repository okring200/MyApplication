package com.example.minjena.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minjena.myapplication.R;
import com.example.minjena.myapplication.model.RecyclerUser;
import com.example.minjena.myapplication.presenter.PeoplePresenter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class PeopleFragment extends Fragment{

    Bundle arg;
    PeoplePresenter mPresenter;

    public PeopleFragment(Bundle arg)
    {
        this.arg = arg;
        mPresenter = new PeoplePresenter(arg);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.peoplefragment_recyclerview);
        recyclerView.setLayoutManager((new LinearLayoutManager(inflater.getContext())));
        recyclerView.setAdapter(new PeopleFragmentRecyclerViewAdapter());
        return view;
    }

    class PeopleFragmentRecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

        ArrayList<String> receivedList;
        List<RecyclerUser> recyclerUserList;

        public PeopleFragmentRecyclerViewAdapter()
        {
            receivedList = mPresenter.getPeopleList();
            recyclerUserList = new ArrayList<>();
            for(String el : receivedList)
            {
                RecyclerUser temp = new RecyclerUser(el);
                recyclerUserList.add(temp);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_user,parent,false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((CustomViewHolder)holder).itemName.setText(recyclerUserList.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return recyclerUserList.size();
        }
    }
    private class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;

        public CustomViewHolder(View view) {
            super(view);
            itemName = (TextView)view.findViewById(R.id.itemName);
        }
    }
}
