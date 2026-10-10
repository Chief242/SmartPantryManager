package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity{

    private ListView listRecipes;
    private TextView txtNoRecipes;

    private DatabaseHelper databaseHelper;
    private ArrayList<Recipe> matchingRecipes;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        listRecipes = findViewById(R.id.listRecipes);
        txtNoRecipes = findViewById(R.id.txtNoRecipes);

        databaseHelper = new DatabaseHelper(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        ArrayList<Ingredient> pantry =
                databaseHelper.getAllIngredients();

        ArrayList<Recipe> allRecipes =
                databaseHelper.getAllRecipes();

        matchingRecipes = RecipeMatcher.findMatchingRecipes(
                allRecipes,
                pantry
        );

        adapter = new RecipeAdapter(this, matchingRecipes);
        listRecipes.setAdapter(adapter);

        updateRecipeView();
    }

    private void updateRecipeView() {

        if (matchingRecipes == null || matchingRecipes.isEmpty()) {

            txtNoRecipes.setVisibility(TextView.VISIBLE);
            listRecipes.setVisibility(ListView.GONE);

        } else {

            txtNoRecipes.setVisibility(TextView.GONE);
            listRecipes.setVisibility(ListView.VISIBLE);
        }
    }
}
