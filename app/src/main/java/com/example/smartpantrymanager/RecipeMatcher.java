package com.example.smartpantrymanager;

import java.util.ArrayList;

public class RecipeMatcher {
    //------------------------------------------
    // Find recipes that can actually be made
    //------------------------------------------

    public static ArrayList<Recipe> findMatchingRecipes(
            ArrayList<Recipe> recipes,
            ArrayList<Ingredient> pantry
    ) {

        ArrayList<Recipe> matchingRecipes =
                new ArrayList<>();

        for (Recipe recipe : recipes) {

            if (canMakeRecipe(recipe, pantry)) {

                matchingRecipes.add(recipe);
            }
        }

        return matchingRecipes;
    }

    //------------------------------------------
    // Check One Recipe
    //------------------------------------------

    private static boolean canMakeRecipe(
            Recipe recipe,
            ArrayList<Ingredient> pantry
    ) {

        for (
                RecipeIngredient required
                : recipe.getIngredients()
        ) {

            double availableQuantity = 0;

            boolean compatibleIngredientFound =
                    false;

            for (
                    Ingredient pantryItem
                    : pantry
            ) {

                // Compare ingredient names
                if (
                        normalizeName(
                                pantryItem.getName()
                        ).equals(
                                normalizeName(
                                        required.getName()
                                )
                        )
                ) {

                    double convertedQuantity =
                            convertToBaseUnit(
                                    pantryItem.getQuantity(),
                                    pantryItem.getUnit()
                            );

                    double requiredBaseQuantity =
                            convertToBaseUnit(
                                    required.getQuantity(),
                                    required.getUnit()
                            );

                    // -1 means the units cannot be compared
                    if (
                            convertedQuantity >= 0
                            && requiredBaseQuantity >= 0
                            && getUnitType(
                                    pantryItem.getUnit()
                            ).equals(
                                    getUnitType(
                                            required.getUnit()
                                    )
                            )
                    ) {

                        compatibleIngredientFound = true;

                        availableQuantity += convertedQuantity;
                    }
                }
            }

            // Ingredient doesn't exist or units cannot be compared
            if (!compatibleIngredientFound) {

                return false;
            }

            // Not enough quantity
            double requiredQuantity =
                    convertToBaseUnit(
                            required.getQuantity(),
                            required.getUnit()
                    );

            if (
                    requiredQuantity < 0
                    || availableQuantity + 0.000001
                    < requiredQuantity
            ) {

                return false;
            }
        }

        // Every required ingredient passed
        return true;
    }

    //------------------------------------------
    // Normalize Ingredient Names
    //------------------------------------------

    private static String normalizeName(
            String name
    ) {

        if (name == null) {
            return "";
        }

        String value =
                name
                        .trim()
                        .toLowerCase()
                        .replaceAll(
                                "[^a-z0-9]",
                                ""
                        );

        // Common singular/plural differences
        if (value.equals("eggs")) {
            return "egg";
        }

        if (value.equals("tomatoes")) {
            return "tomato";
        }

        if (value.equals("bananas")) {
            return "banana";
        }

        if (value.equals("onions")) {
            return "onion";
        }

        if (value.equals("potatoes")) {
            return "potato";
        }

        if (value.endsWith("ies")
                && value.length() > 3) {

            return value.substring(
                    0,
                    value.length() - 3
            ) + "y";
        }

        if (value.endsWith("s")
                && value.length() > 3) {

            return value.substring(
                    0,
                    value.length() - 1
            );
        }

        return value;
    }

    //------------------------------------------
    // Unit Type
    //------------------------------------------

    private static String getUnitType(
            String unit
    ) {

        String normalized = normalizeUnit(unit);

        if (
                normalized.equals("grams")
                || normalized.equals("kg")
        ) {
            return "weight";
        }

        if (
                normalized.equals("ml")
                || normalized.equals("litres")
        ) {
            return "volume";
        }

        if (
                normalized.equals("pieces")
                || normalized.equals("packets")
                || normalized.equals("cans")
                || normalized.equals("bottles")
        ) {
            return normalized;
        }

        return "unknown";
    }
    //------------------------------------------
    // Convert To Base Unit
    //------------------------------------------

    private static double convertToBaseUnit(
            double quantity,
            String unit
    ) {

        String normalized =
                normalizeUnit(unit);

        switch (normalized) {

            case "kg":
                return quantity * 1000.0;

            case "grams":
                return quantity;

            case "litres":
                return quantity * 1000.0;

            case "ml":
                return quantity;

            case "pieces":
            case "packets":
            case "cans":
            case "bottles":
                return quantity;

            default:
                return -1;
        }
    }

    //------------------------------------------
    // Normalize Units
    //------------------------------------------

    private static String normalizeUnit(
            String unit
    ) {

        if (unit == null) {
            return "";
        }

        String value =
                unit.trim().toLowerCase();

        switch (value) {

            case "kg":
            case "kgs":
            case "kilogram":
            case "kilograms":
                return "kg";

            case "g":
            case "gram":
            case "grams":
                return "grams";

            case "l":
            case "litre":
            case "litres":
            case "liter":
            case "liters":
                return "litres";

            case "ml":
            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
                return "ml";

            case "piece":
            case "pieces":
                return "pieces";

            case "packet":
            case "packets":
                return "packets";

            case "can":
            case "cans":
                return "cans";

            case "bottle":
            case "bottles":
                return "bottles";

            default:
                return value;
        }
    }
}
