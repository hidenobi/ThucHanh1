package com.example.thbuoi1;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Item> list;
    final private OnClickItem onClickItem;
    final private Context context;
    final private Resources resources;


    public ItemAdapter(ArrayList<Item> list, OnClickItem onClickItem, Context context) {
        this.list = list;
        this.onClickItem = onClickItem;
        this.context = context;
        this.resources = context.getResources();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = list.get(position);
        holder.cardView.setCardBackgroundColor(resources.getColor(item.color, context.getTheme()));
        holder.name.setText(item.name);
        holder.birthday.setText(item.birthday);
        holder.cbDefender.setChecked(item.isDefender);
        holder.cbMidfielder.setChecked(item.isMidfielder);
        holder.cbStriker.setChecked(item.isStriker);
        holder.cardView.setOnClickListener(view ->
        {
            onClickItem.update(item);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<Item> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name, birthday;
        CheckBox cbDefender, cbMidfielder, cbStriker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_item);
            name = itemView.findViewById(R.id.tv_name);
            birthday = itemView.findViewById(R.id.tv_year);
            cbDefender = itemView.findViewById(R.id.cb_defender);
            cbMidfielder = itemView.findViewById(R.id.cb_midfielder);
            cbStriker = itemView.findViewById(R.id.cb_striker);
        }
    }
}

interface OnClickItem {
    void update(Item item);
}
