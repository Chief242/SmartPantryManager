package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnPantry;
    private Button btnRecipes;
    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPantry = findViewById(R.id.btnPantry);
        btnRecipes = findViewById(R.id.btnRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        btnPantry.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            MainActivity.this,
                            PantryActivity.class
                    )
            );
        });

        btnRecipes.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            MainActivity.this,
                            SuggestedRecipesActivity.class
                    )
            );
        });

        btnSettings.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            MainActivity.this,
                            SettingsActivity.class
                    )
            );
        });
    }

    // ------------------------------
    // Toolbar Navigation
    // ------------------------------

    @Override
    public boolean onCreateOptionsMenu(
            Menu menu
    ) {

        getMenuInflater().inflate(
                R.menu.main_menu,
                menu
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(
            MenuItem item
    ) {

        int itemId =
                item.getItemId();

        if (
                itemId == R.id.menuPantry
        ) {

            startActivity(
                    new Intent(
                            this,
                            PantryActivity.class
                    )
            );

            return true;
        }

        if (
                itemId == R.id.menuRecipes
        ) {

            startActivity(
                    new Intent(
                            this,
                            SuggestedRecipesActivity.class
                    )
            );

            return true;
        }

        if (
                itemId == R.id.menuSettings
        ) {

            startActivity(
                    new Intent(
                            this,
                            SettingsActivity.class
                    )
            );

            return true;
        }

        return super.onOptionsItemSelected(
                item
        );
    }
}