package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private ArrayList<Recipe> recipes;

    public RecipeAdapter(
            Context context,
            ArrayList<Recipe> recipes
    ) {

        super(
                context,
                0,
                recipes
        );

        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent
    ) {

        if (convertView == null) {

            convertView =
                    LayoutInflater
                            .from(context)
                            .inflate(
                                    R.layout.item_recipe,
                                    parent,
                                    false
                            );
        }

        Recipe recipe =
                recipes.get(position);

        TextView txtRecipeName =
                convertView.findViewById(
                        R.id.txtRecipeName
                );

        TextView txtRecipeIngredientCount =
                convertView.findViewById(
                        R.id.txtRecipeIngredientCount
                );

        Button btnViewRecipe =
                convertView.findViewById(
                        R.id.btnViewRecipe
                );

        txtRecipeName.setText(
                recipe.getName()
        );

        txtRecipeIngredientCount.setText(
                recipe.getIngredients().size()
                        + " required ingredients"
        );

        btnViewRecipe.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            RecipeDetailActivity.class
                    );

            intent.putExtra(
                    "recipe_id",
                    recipe.getId()
            );

            context.startActivity(intent);
        });

        return convertView;
    }
}
