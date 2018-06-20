package com.example.minjena.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.minjena.myapplication.R;
import com.example.minjena.myapplication.model.RecyclerChat;

import java.util.ArrayList;

public class chatAdapater extends RecyclerView.Adapter<chatAdapater.ViewHolder> {

    private ArrayList<RecyclerChat> chat;
    private String myidx;
    private Button btnSound;

    public interface  OnItemClickListener{
        public void onItemClick(RecyclerChat temp);
    }
    private OnItemClickListener onItemClickListener;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    //public static class ViewHolder extends RecyclerView.ViewHolder {
    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.txtChat);
            btnSound = (Button) itemView.findViewById(R.id.btnSound);
        }

        public Button getBtnSound()
        {
            return btnSound;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public chatAdapater(ArrayList<RecyclerChat> myDataset, String myidx, OnItemClickListener onItemClickListener) {
        chat = myDataset;
        this.myidx = myidx;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public int getItemViewType(int position) {
        if(chat.get(position).getFrom_idx().equals(myidx)){
            return 1;
        }
        else
            return 2;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public chatAdapater.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v;
        if(viewType == 1)
        {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_rchat, parent, false);
        }
        else
        {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_chat, parent, false);
        }
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        final RecyclerChat temp = chat.get(position);
        holder.mTextView.setText(chat.get(position).getMsg().toString());
        holder.setIsRecyclable(false);
        if(holder.getBtnSound() != null){
            holder.getBtnSound().setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(temp);
                }
            });
        }
        /*if(holder.btnSound != null) {
            Button btnSound = holder.btnSound;
            btnSound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("aa", chat.get(position).getMsg().toString());
                }
            });
        }*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return chat.size();
    }
}
