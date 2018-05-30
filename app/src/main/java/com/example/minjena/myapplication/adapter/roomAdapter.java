package com.example.minjena.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minjena.myapplication.R;
import com.example.minjena.myapplication.model.RecyclerRoom;

import java.util.ArrayList;

public class roomAdapter extends RecyclerView.Adapter<roomAdapter.ViewHolder> {
    private ArrayList<RecyclerRoom> recyclerRoomList;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView roomName;

        public ViewHolder(View itemView) {
            super(itemView);
            roomName = (TextView) itemView.findViewById(R.id.roomName);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public roomAdapter(ArrayList<RecyclerRoom> myDataset) {
        recyclerRoomList = myDataset;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public roomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_room, parent, false);
        // set the view's size, margins, paddings and layout parameters
        roomAdapter.ViewHolder vh = new roomAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(roomAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        final RecyclerRoom item = recyclerRoomList.get(position);
        holder.roomName.setText(recyclerRoomList.get(position).getName().toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recyclerRoomList.size();
    }

}
