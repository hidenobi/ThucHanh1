package com.example.thbuoi1.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import com.example.thbuoi1.MyApplication;
import com.example.thbuoi1.R;
import com.example.thbuoi1.adapter.ItemDataAdapter;
import com.example.thbuoi1.adapter.OnClickItem;
import com.example.thbuoi1.databinding.FragmentRightBinding;
import com.example.thbuoi1.models.ItemData;

import java.util.ArrayList;
import java.util.List;

public class RightFragment extends Fragment {
    ItemDataAdapter itemDataAdapter;
    FragmentRightBinding binding;
    LiveData<List<ItemData>> arrayListLiveData;

    public RightFragment() {
        // Required empty public constructor
    }


    public static RightFragment newInstance() {
        RightFragment fragment = new RightFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initAdapter();
        binding.btnAdd.setOnClickListener(v -> {
            Navigation.findNavController(RightFragment.this.requireView()).navigate(R.id.action_rightFragment_to_createFragment);
        });
    }

    private void initData() {
        Log.e("TAG", "initData: " + MyApplication.appDatabase.itemDataDao().getList());
    }

    private void initAdapter() {
        itemDataAdapter = new ItemDataAdapter((ArrayList<ItemData>) MyApplication.appDatabase.itemDataDao().getList(), new OnClickItem() {
            @Override
            public void update(ItemData itemData) {

            }

            @Override
            public void delete(ItemData itemData) {
                MyApplication.appDatabase.itemDataDao().deleteItemData(itemData);
                itemDataAdapter.setList((ArrayList<ItemData>) MyApplication.appDatabase.itemDataDao().getList());
            }
        });
        binding.rvItemData.setAdapter(itemDataAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        itemDataAdapter.setList((ArrayList<ItemData>) MyApplication.appDatabase.itemDataDao().getList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentRightBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}