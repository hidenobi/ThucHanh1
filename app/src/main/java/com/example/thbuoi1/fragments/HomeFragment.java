package com.example.thbuoi1.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thbuoi1.MyApplication;
import com.example.thbuoi1.databinding.FragmentHomeBinding;
import com.example.thbuoi1.models.ItemData;
import com.example.thbuoi1.models.enums.Scope;
import com.example.thbuoi1.models.enums.Subject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private static final String TAG = "TAG-Home";
    private ItemData oldItemData;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        initViews();
        initActions();
    }

    private void initViews() {

    }

    private void initActions() {
        binding.btnAdd.setOnClickListener(view -> {
            Scope scope;
            if (binding.rbStudy.isChecked()) {
                scope = Scope.STUDY;
            } else {
                scope = Scope.SEARCH;
            }
            ArrayList<Subject> subjects = new ArrayList<>();
            if (binding.cbCNTT1.isChecked()) {
                subjects.add(Subject.CNTT);
            }
            if (binding.cbVT1.isChecked()) {
                subjects.add(Subject.VT);
            }
            if (binding.cbDT1.isChecked()) {
                subjects.add(Subject.DT);
            }
            String name = binding.edtName.getText().toString();
            if (name.isEmpty()) {
                showToast();
                return;
            }
            String author = binding.edtAuthor.getText().toString();
            if (author.isEmpty()) {
                showToast();
                return;
            }
            ItemData itemData = new ItemData(
                    name,
                    author,
                    scope,
                    subjects,
                    binding.rating1.getRating()
            );
            Log.i(TAG, "initActions: " + itemData.subjects);
            if (itemData.uid == 0) {
                MyApplication.appDatabase.itemDataDao().saveItemData(itemData);
            } else {
                MyApplication.appDatabase.itemDataDao().updateItemData(itemData);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void showToast() {
        Toast.makeText(requireContext(), "Khong duoc de trong truong", Toast.LENGTH_SHORT).show();
    }
}