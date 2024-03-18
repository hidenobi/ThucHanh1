package com.example.thbuoi1;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SearchView;
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

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ItemAdapter itemAdapter;
    ArrayList<Item> list;

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
        class QueryText implements SearchView.OnQueryTextListener {

            @Override
            public boolean onQueryTextSubmit(String s) {
                searchByName(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s == null || s.isEmpty()) {
                    searchByName(null);
                }
                return true;
            }
        }
        QueryText queryText = new QueryText();
        searchView.setOnQueryTextListener(queryText);
        floatingActionButton.setOnClickListener(view -> {
            openBottomSheet(null);
        });
    }

    private void searchByName(String s) {
        if (s == null || s.isEmpty()) {
            itemAdapter.setList(list);
        } else {
            ArrayList<Item> searchList = new ArrayList<>();
            for (Item item : list) {
                if (item.name.toLowerCase().contains(s.toLowerCase())) {
                    searchList.add(item);
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
        RadioButton rbMale = view.findViewById(R.id.rb_male);
        RadioButton rbFemale = view.findViewById(R.id.rb_female);
        if (item != null) {
            edtName.setText(item.name);
            tvDateTime.setText(item.birthday);
            cbDefender.setChecked(item.isDefender);
            cbMidfielder.setChecked(item.isMidfielder);
            cbStriker.setChecked(item.isStriker);
            if (item.gender == Gender.MALE) {
                rbMale.setChecked(true);
                rbFemale.setChecked(false);
            } else {
                rbMale.setChecked(false);
                rbFemale.setChecked(true);
            }
        }
        tvDateTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        (datePicker, year, monthOfYear, dayOfMonth) -> {
                            LocalDate chooseTime = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                            long chooseMillisecond = chooseTime.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
                            if (chooseMillisecond > System.currentTimeMillis()) {
                                showToast("Set new birthday fail");
                            } else {
                                tvDateTime.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay
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
            Gender gender;
            if (rbMale.isChecked()) {
                gender = Gender.MALE;
            } else {
                gender = Gender.FEMALE;
            }
            if (name.isEmpty() || date.isEmpty()) {
                showToast("The fields is not null");
            } else if (!isDefender && !isMidfielder && !isStriker) {
                showToast("Must choose one position");
            } else {
                Item newItem = new Item(name, date, gender, isDefender, isMidfielder, isStriker, color, id);
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
                        "12/07/2002",
                        Gender.MALE,
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
                        "12/07/2002",
                        Gender.FEMALE,
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
