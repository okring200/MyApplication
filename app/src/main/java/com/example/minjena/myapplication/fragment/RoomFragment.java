package com.example.minjena.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.minjena.myapplication.R;
import com.example.minjena.myapplication.activities.ChatActivity;
import com.example.minjena.myapplication.activities.MainActivity;
import com.example.minjena.myapplication.adapter.roomAdapter;
import com.example.minjena.myapplication.model.RecyclerRoom;
import com.example.minjena.myapplication.presenter.RoomPresenter;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

@SuppressLint("ValidFragment")
public class RoomFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<RecyclerRoom> recyclerRoomList;
    RoomPresenter mRoomPresenter;
    Bundle arg;

    public RoomFragment(Bundle arg)
    {
        this.arg = arg;
        mRoomPresenter = new RoomPresenter(arg);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.roomfragment_recyclerview);
        mRecyclerView.setLayoutManager((new LinearLayoutManager(inflater.getContext())));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerRoomList = mRoomPresenter.getRoomList();
        mAdapter = new roomAdapter(recyclerRoomList);
        mRecyclerView.setAdapter(mAdapter);

        final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                int pos = rv.getChildAdapterPosition(child);
                RecyclerRoom temp = recyclerRoomList.get(pos);
                arg.putString( "rkey", temp.getRidx());
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtras(arg);
                startActivity(intent);

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return view;
    }
}

