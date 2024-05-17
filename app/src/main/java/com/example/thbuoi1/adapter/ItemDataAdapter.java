package com.example.thbuoi1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thbuoi1.databinding.ItemInfoHomeBinding;
import com.example.thbuoi1.models.ItemData;

import java.util.ArrayList;

public class ItemDataAdapter extends RecyclerView.Adapter<ItemDataAdapter.ViewHolder> {
    private ArrayList<ItemData> list;
    final private OnClickItem onClickItem;


    public ItemDataAdapter(ArrayList<ItemData> list, OnClickItem onClickItem) {
        this.list = list;
        this.onClickItem = onClickItem;

    }

    public void setList(ArrayList<ItemData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemInfoHomeBinding binding = ItemInfoHomeBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, this.onClickItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemData itemData = list.get(position);
        holder.bind(itemData);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private OnClickItem onClickItem;
        private ItemInfoHomeBinding binding;

        public ViewHolder(ItemInfoHomeBinding binding, OnClickItem onClickItem) {
            super(binding.getRoot());
            this.onClickItem = onClickItem;
            this.binding = binding;
        }

        void bind(ItemData itemData) {
            binding.tvName.setText(itemData.name);
            binding.date.setText(itemData.date);
            switch (itemData.scope) {
                case CNTT:
                    binding.cntt.setChecked(true);
                case KT:
                    binding.kt.setChecked(true);
                case QTKD:
                    binding.qtkd.setChecked(true);
                case TKDH:
                    binding.tkdh.setChecked(true);
            }
            if (itemData.active) {
                binding.active.setChecked(true);
            }
            binding.hocPhi.setText("Hoc phi: " + itemData.hocPhi);
            binding.ivDelete.setVisibility(View.VISIBLE);
            binding.ivDelete.setOnClickListener(view -> {
                onClickItem.delete(itemData);
            });
            binding.getRoot().setOnClickListener(view -> {
                onClickItem.update(itemData);
            });
        }
    }
}


