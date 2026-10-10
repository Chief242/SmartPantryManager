package com.example.smartpantrymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddIngredientActivity extends AppCompatActivity {

    private EditText edtIngredientName;
    private EditText edtQuantity;
    private Spinner spinnerUnit;
    private Button btnSaveIngredient;
    private Button btnCancelIngredient;

    private DatabaseHelper databaseHelper;

    // Used to determine whether we are adding or editing
    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        // Connect layout components
        edtIngredientName = findViewById(R.id.edtIngredientName);
        edtQuantity = findViewById(R.id.edtQuantity);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        btnCancelIngredient = findViewById(R.id.btnCancelIngredient);

        // Create database helper
        databaseHelper = new DatabaseHelper(this);

        // Check whether an existing ingredient was passed to this screen
        ingredientId = getIntent().getIntExtra(
                "ingredient_id",
                -1
        );

        // If an ingredient ID exists, we are editing
        if (ingredientId != -1) {

            String ingredientName =
                    getIntent().getStringExtra(
                            "ingredient_name"
                    );

            double ingredientQuantity =
                    getIntent().getDoubleExtra(
                            "ingredient_quantity",
                            0
                    );

            String ingredientUnit =
                    getIntent().getStringExtra(
                            "ingredient_unit"
                    );

            // Display existing values
            edtIngredientName.setText(ingredientName);

            edtQuantity.setText(
                    String.valueOf(ingredientQuantity)
            );

            // Select the existing unit in the Spinner
            if (ingredientUnit != null)  {

                ArrayAdapter<?> adapter =
                        (ArrayAdapter<?>) spinnerUnit.getAdapter();

                for (int i = 0; i < adapter.getCount(); i++) {

                    if (adapter.getItem(i)
                            .toString()
                            .equalsIgnoreCase(ingredientUnit)) {

                        spinnerUnit.setSelection(i);
                        break;
                    }
                }
            }

            // Change button text when editing
            btnSaveIngredient.setText(
                    "Update Ingredient"
            );
        }

        // Save or update ingredient
        btnSaveIngredient.setOnClickListener(v -> {

                    String name = edtIngredientName.getText()
                            .toString().trim();

                    String quantityText = edtQuantity.getText()
                            .toString().trim();

                    String unit = spinnerUnit.getSelectedItem()
                            .toString();

                    // Validate ingredient name
                    if (name.isEmpty()) {

                        edtIngredientName.setError(
                                "Please enter an ingredient name"
                        );

                        edtIngredientName.requestFocus();
                        return;
                    }

                    // Validate quantity
                    if (quantityText.isEmpty()) {

                        edtQuantity.setError(
                                "Please enter a quantity"
                        );

                        edtQuantity.requestFocus();
                        return;
                    }

                    double quantity;

                    try {

                        quantity = Double.parseDouble(quantityText);

                        if (!Double.isFinite(quantity) || quantity <= 0) {

                            edtQuantity.setError(
                                    "Enter a quantity greater than zero"
                            );

                            edtQuantity.requestFocus();
                            return;
                        }

                    } catch (NumberFormatException e) {

                        edtQuantity.setError(
                                "Enter a valid quantity"
                        );

                        edtQuantity.requestFocus();
                        return;
                    }

                    // Update existing ingredient
                    if (ingredientId != -1) {

                        int result =
                                databaseHelper.updateIngredient(
                                        ingredientId,
                                        name,
                                        quantity,
                                        unit
                                );

                        if (result > 0) {

                            setResult(Activity.RESULT_OK);
                            finish();

                        } else {

                            edtIngredientName.setError(
                                    "Unable to update ingredient"
                            );

                            edtIngredientName.requestFocus();
                        }
                    }

                    // Create new ingredient
                    else {

                        long result = databaseHelper.addIngredient(
                                name,
                                quantity,
                                unit
                        );

                        if (result != -1) {

                            setResult(Activity.RESULT_OK);
                            finish();

                        } else {

                            edtIngredientName.setError(
                                    "Unable to save ingredient"
                            );

                            edtIngredientName.requestFocus();
                        }
                    }

                });


        // Cancel Button
        btnCancelIngredient.setOnClickListener(v -> {

            setResult(Activity.RESULT_CANCELED);
            finish();

        });
    }
}
