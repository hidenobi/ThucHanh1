package com.example.thbuoi1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.thbuoi1.MyApplication;
import com.example.thbuoi1.databinding.FragmentCreateBinding;
import com.example.thbuoi1.models.ItemData;
import com.example.thbuoi1.models.enums.Scope;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {
    private FragmentCreateBinding binding;

    public CreateFragment() {
        // Required empty public constructor
    }


    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
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
        binding.add.setOnClickListener(view1 ->
        {
            String name = binding.tvName.getText().toString();
            String date = binding.date.getText().toString();
            String hocPhi = binding.hocPhi.getText().toString();
            if (name.isEmpty() || date.isEmpty() || hocPhi.isEmpty()) {
                showToast();
                return;
            }
            Scope scope = Scope.CNTT;
            if (binding.cntt.isChecked()) {
                scope = Scope.CNTT;
            } else if (binding.kt.isChecked()) {
                scope = Scope.KT;
            } else if (binding.qtkd.isChecked()) {
                scope = Scope.QTKD;
            } else if (binding.tkdh.isChecked()) {
                scope = Scope.TKDH;
            }
            Boolean active = binding.active.isChecked();
            ItemData itemData = new ItemData(name, date, scope, active, Float.parseFloat(hocPhi));
            MyApplication.appDatabase.itemDataDao().saveItemData(itemData);
            Navigation.findNavController(view).popBackStack();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void showToast() {
        Toast.makeText(requireContext(), "Khong duoc de trong truong", Toast.LENGTH_SHORT).show();
    }
}