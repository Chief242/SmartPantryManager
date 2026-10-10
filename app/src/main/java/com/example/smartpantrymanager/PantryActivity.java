package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity {

    private ListView listPantry;
    private TextView txtEmptyPantry;
    private Button btnAddIngredient;

    private ArrayList<Ingredient> ingredients;
    private DatabaseHelper databaseHelper;
    private PantryAdapter adapter;

    private ActivityResultLauncher<Intent> addIngredientLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        // Connect layout components
        listPantry = findViewById(R.id.listPantry);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);

        // Create database helper
        databaseHelper = new DatabaseHelper(this);

        // Load ingredients from SQLite database
        loadIngredients();

        // Register activity result launcher
        addIngredientLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {

                            if (result.getResultCode() == RESULT_OK) {

                                // Reload ingredients from database
                                loadIngredients();

                                Toast.makeText(
                                        PantryActivity.this,
                                        "Ingredient added successfully!",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                );

        // Add Ingredient Button
        btnAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PantryActivity.this,
                    AddIngredientActivity.class
            );

            addIngredientLauncher.launch(intent);
        });
    }

    // Load ingredients from SQLite database
    private void loadIngredients() {

        ingredients = databaseHelper.getAllIngredients();

        // Create custom adapter
        adapter = new PantryAdapter(
                this,
                ingredients
        );

        listPantry.setAdapter(adapter);

        updatePantryView();
    }

    // Update pantry display
    private void updatePantryView() {

        if (ingredients == null || ingredients.isEmpty()) {

            txtEmptyPantry.setVisibility(TextView.VISIBLE);
            listPantry.setVisibility(ListView.GONE);

        } else {

            txtEmptyPantry.setVisibility(TextView.GONE);
            listPantry.setVisibility(ListView.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reload database data whenever the screen becomes visible
        loadIngredients();
    }

    public void refreshPantry() {
        loadIngredients();
    }
}

