package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeName;
    private TextView txtRecipeIngredients;
    private TextView txtRecipeInstructions;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recipe_detail
        );

        txtRecipeName =
                findViewById(
                        R.id.txtRecipeName
                );

        txtRecipeIngredients =
                findViewById(
                        R.id.txtRecipeIngredients
                );

        txtRecipeInstructions =
                findViewById(
                        R.id.txtRecipeInstructions
                );

        databaseHelper =
                new DatabaseHelper(this);

        int recipeId =
                getIntent().getIntExtra(
                        "recipe_id",
                        -1
                );

        if (recipeId == -1) {

            Toast.makeText(
                    this,
                    "Recipe could not be found.",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        loadRecipe(recipeId);
    }

    private void loadRecipe(
            int recipeId
    ) {

        Recipe recipe =
                databaseHelper.getRecipeById(
                        recipeId
                );

        if (recipe == null) {

            Toast.makeText(
                    this,
                    "Recipe could not be found.",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        txtRecipeName.setText(
                recipe.getName()
        );

        StringBuilder ingredientText =
                new StringBuilder();

        for (
                RecipeIngredient ingredient
                : recipe.getIngredients()
        ) {

            ingredientText
                    .append("• ")
                    .append(
                            ingredient.getName()
                    )
                    .append(" — ")
                    .append(
                            ingredient.getQuantity()
                    )
                    .append(" ")
                    .append(
                            ingredient.getUnit()
                    )
                    .append("\n");
        }

        txtRecipeIngredients.setText(
                ingredientText.toString()
        );

        txtRecipeInstructions.setText(
                recipe.getInstructions()
        );
    }
}
