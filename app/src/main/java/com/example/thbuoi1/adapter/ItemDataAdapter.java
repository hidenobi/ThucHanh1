package com.example.thbuoi1.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thbuoi1.databinding.ItemInfoHomeBinding;
import com.example.thbuoi1.models.ItemData;
import com.example.thbuoi1.models.enums.Scope;
import com.example.thbuoi1.models.enums.Subject;

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
            binding.ivImg.setVisibility(View.GONE);
            binding.tvFavourite.setVisibility(View.GONE);
            binding.ivDelete.setVisibility(View.VISIBLE);
            binding.tvName.setText(itemData.name);
            binding.author.setText(itemData.author);
            if (itemData.scope == Scope.STUDY) {
                binding.search.setChecked(false);
                binding.study.setChecked(true);
            } else {
                binding.search.setChecked(true);
                binding.study.setChecked(false);
            }
            binding.cbCNTT.setChecked(false);
            binding.cbVT.setChecked(false);
            binding.cbDT.setChecked(false);
            itemData.subjects.forEach(subject -> {
                if (subject == Subject.CNTT) {
                    binding.cbCNTT.setChecked(true);
                }
                if (subject == Subject.VT) {
                    binding.cbVT.setChecked(true);
                }
                if (subject == Subject.DT) {
                    binding.cbDT.setChecked(true);
                }
            });
            binding.rating.setRating(itemData.rating);
            binding.ivDelete.setOnClickListener(view -> {
                onClickItem.delete(itemData);
            });
            binding.getRoot().setOnClickListener(view -> {
                onClickItem.update(itemData);
            });
        }
    }
}


