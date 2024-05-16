package com.example.thbuoi1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private NavHostFragment navHostFragment;
    private NavController navController;
    private com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;

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
        setNavigator();
        setupBottomNavigator();
    }

    private void setNavigator() {
        navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();


    }

    private void setupBottomNavigator() {
        bottomNavigationView = findViewById(R.id.bottom_navi);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}

