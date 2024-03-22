package com.example.thbuoi1;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Spinner searchView;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ItemAdapter itemAdapter;
    ArrayList<Item> list;
    String[] s = {"", "Châm biếm", "Sự thật", "Phê phán"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initFirstItem();
        initAdapter();
        initActions();
    }

    private void initActions() {
        searchByName((String) searchView.getSelectedItem());
        floatingActionButton.setOnClickListener(view -> {
            openBottomSheet(null);
        });
    }

    private void searchByName(String s) {
        if (s == null || s.isEmpty()) {
            itemAdapter.setList(list);
        } else {
            String a = "Phê phán";
            String b = "Sự thật";
            String c = "Châm biếm";
            ArrayList<Item> searchList = new ArrayList<>();
            for (Item item : list) {
                boolean isExits = false;
                if (item.isDefender) {
                    if (a.toLowerCase().contains(s.toLowerCase())) {
                        if (!isExits) {
                            searchList.add(item);
                            isExits = true;
                        }
                    }
                }
                if (item.isMidfielder) {
                    if (b.toLowerCase().contains(s.toLowerCase())) {
                        if (!isExits) {
                            searchList.add(item);
                            isExits = true;
                        }
                    }
                }
                {
                    if (c.toLowerCase().contains(s.toLowerCase())) {
                        if (!isExits) {
                            searchList.add(item);
                            isExits = true;
                        }
                    }
                }
            }
            itemAdapter.setList(searchList);
        }
    }

    private void openBottomSheet(Item item) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.DialogStyle);
        View view = getLayoutInflater().inflate(MainActivity.this.getResources().getLayout(R.layout.add_player_bottom_sheet),
                null,
                false);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bsd = (BottomSheetDialog) dialogInterface;
            View bottomSheet = bsd.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet == null) return;
            bottomSheet.setBackgroundColor(Color.TRANSPARENT);
        });
        EditText edtName = view.findViewById(R.id.edtName);
        TextView tvDateTime = view.findViewById(R.id.tvDateTime);
        CheckBox cbDefender = view.findViewById(R.id.cb_defender);
        CheckBox cbMidfielder = view.findViewById(R.id.cb_midfielder);
        CheckBox cbStriker = view.findViewById(R.id.cb_striker);
        if (item != null) {
            edtName.setText(item.name);
            tvDateTime.setText(item.birthday);
            cbDefender.setChecked(item.isDefender);
            cbMidfielder.setChecked(item.isMidfielder);
            cbStriker.setChecked(item.isStriker);
        }
        tvDateTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog datePickerDialog = new TimePickerDialog(
                        MainActivity.this,
                        (timePicker, i, i1) -> {
                            tvDateTime.setText(i + ":" + i1);
                        }, hour, minute, true
                );
                datePickerDialog.show();
            }
        });
        ImageView ivDone = view.findViewById(R.id.ivDone);
        ImageView ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });
        ivDone.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String date = tvDateTime.getText().toString();
            boolean isDefender = cbDefender.isChecked();
            boolean isMidfielder = cbMidfielder.isChecked();
            boolean isStriker = cbStriker.isChecked();
            Long id;
            if (item != null) {
                id = item.id;
            } else {
                id = System.currentTimeMillis();
            }
            @ColorRes int color;
            if (id % 2 == 0) {
                color = R.color.ColorBlur;
            } else {
                color = R.color.ColorLightYellow;
            }
            if (name.isEmpty() || date.isEmpty()) {
                showToast("The fields is not null");
            } else if (!isDefender && !isMidfielder && !isStriker) {
                showToast("Must choose one position");
            } else {
                Item newItem = new Item(name, date, isDefender, isMidfielder, isStriker, color, id);
                if (item == null) {
                    saveItem(newItem);
                } else {
                    updateItem(newItem);
                }
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private void saveItem(Item newItem) {
        list.add(newItem);
        itemAdapter.setList(list);
    }

    private void updateItem(Item newItem) {
        int index = list.indexOf(newItem);
        if (index < 0) {
            showToast("Update fail");
        } else {
            list.set(index, newItem);
            itemAdapter.setList(list);
        }
    }

    private void initAdapter() {
        class ClickItem implements OnClickItem {

            @Override
            public void update(Item item) {
                openBottomSheet(item);
            }
        }

        ClickItem clickItem = new ClickItem();
        ArrayList<Item> tmpList = new ArrayList<>();
        tmpList.addAll(list);
        itemAdapter = new ItemAdapter(tmpList, clickItem, this.getApplicationContext());
        recyclerView.setAdapter(itemAdapter);
        class TouchHelperCallback extends ItemTouchHelper.Callback {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(
                        ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.END | ItemTouchHelper.START
                );
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                list.remove(viewHolder.getAdapterPosition());
                itemAdapter.setList(list);
            }
        }
        TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        SpinnerAdapter sp = new ArrayAdapter<String>(MainActivity.this, android.R.layout.activity_list_item, s);
        searchView.setAdapter(sp);
    }

    private void initView() {
        searchView = findViewById(R.id.svSearch);
        recyclerView = findViewById(R.id.rvTasks);
        floatingActionButton = findViewById(R.id.btnAdd);
    }

    private void initFirstItem() {
        list = new ArrayList<>();
        list.add(
                new Item(
                        "David",
                        "13:00",
                        true,
                        false,
                        false,
                        R.color.ColorLightYellow,
                        1
                )
        );
        list.add(
                new Item(
                        "JK",
                        "12:00",
                        true,
                        true,
                        false,
                        R.color.ColorBlur,
                        2
                )
        );
    }

    private void showToast(String s) {
        Toast.makeText(this.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}

