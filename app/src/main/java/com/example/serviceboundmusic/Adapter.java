package com.example.serviceboundmusic;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.NameViewHolder> {
    private ArrayList<String> nameArrayList;
    private LayoutInflater layoutInflater;

    public Adapter(Context context, ArrayList<String> nameArrayList) {
        layoutInflater = LayoutInflater.from(context);
        this.nameArrayList = nameArrayList;

    }

    @NonNull
    @Override
    public Adapter.NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemview = layoutInflater.inflate(R.layout.item, parent, false);

        return new NameViewHolder(mItemview, this);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.NameViewHolder holder, int position) {
        String mCurrent = nameArrayList.get(position);
        holder.nameitemview.setText(mCurrent);

    }

    @Override
    public int getItemCount() {
        return nameArrayList.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        public TextView nameitemview;
        Adapter namelistAdapter;

        public NameViewHolder(@NonNull View itemView, Adapter adapter) {
            super(itemView);
            nameitemview = itemView.findViewById(R.id.txtname);
            this.namelistAdapter = adapter;


        }
    }
}
