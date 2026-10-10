package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PantryAdapter extends ArrayAdapter<Ingredient> {

    private Context context;
    private ArrayList<Ingredient> ingredients;
    private DatabaseHelper databaseHelper;

    public PantryAdapter(
            Context context,
            ArrayList<Ingredient> ingredients
    ) {
        super(context, 0, ingredients);

        this.context = context;
        this.ingredients = ingredients;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent
    ) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context)
                    .inflate(
                            R.layout.item_ingredient,
                            parent,
                            false
                    );
        }

        // Get current ingredient
        Ingredient ingredient = ingredients.get(position);

        // Connect views
        TextView txtIngredientName =
                convertView.findViewById(R.id.txtIngredientName);

        TextView txtIngredientQuantity =
                convertView.findViewById(R.id.txtIngredientQuantity);

        Button btnEdit =
                convertView.findViewById(R.id.btnEditIngredient);

        Button btnDelete =
                convertView.findViewById(R.id.btnDeleteIngredient);

        // Display ingredient information
        txtIngredientName.setText(
                ingredient.getName()
        );

        txtIngredientQuantity.setText(
                ingredient.getQuantity()
                + " "
                + ingredient.getUnit()
        );

        // Edit button
        btnEdit.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    AddIngredientActivity.class
            );

            intent.putExtra(
                    "ingredient_id",
                    ingredient.getId()
            );

            intent.putExtra(
                    "ingredient_name",
                    ingredient.getName()
            );

            intent.putExtra(
                    "ingredient_quantity",
                    ingredient.getQuantity()
            );

            intent.putExtra(
                    "ingredient_unit",
                    ingredient.getUnit()
            );

            context.startActivity(intent);
        });

        // Delete button
        btnDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Delete Ingredient")
                    .setMessage(
                            "Are you sure you want to delete "
                            + ingredient.getName()
                            + "?"
                    )
                    .setPositiveButton(
                            "Delete",
                            (dialog, which) -> {

                                int result =
                                        databaseHelper.deleteIngredient(
                                                ingredient.getId()
                                        );

                                if (result > 0) {

                                    ingredients.remove(position);

                                    notifyDataSetChanged();

                                    Toast.makeText(
                                            context,
                                            "Ingredient deleted",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    if (context instanceof PantryActivity) {
                                        ((PantryActivity) context)
                                                .refreshPantry();
                                    }

                                } else {

                                    Toast.makeText(
                                            context,
                                            "Unable to delete ingredient",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    )
                    .setNegativeButton(
                            "Cancel",
                            null
                    )
                    .show();
        });

        return convertView;
    }
}
