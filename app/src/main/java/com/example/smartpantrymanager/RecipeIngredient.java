package com.example.smartpantrymanager;

public class RecipeIngredient {

    private int id;
    private int recipeId;
    private String name;
    private double quantity;
    private String unit;

    public RecipeIngredient(
            int id,
            int recipeId,
            String name,
            double quantity,
            String unit
    ) {

        this.id = id;
        this.recipeId = recipeId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Get ingredient ID
    public int getId() {
        return id;
    }

    // Get recipe ID
    public int getRecipeId() {
        return recipeId;
    }

    // Get ingredient name
    public String getName() {
        return name;
    }

    // Get required quantity
    public double getQuantity() {
        return quantity;
    }

    // Get required unit
    public String getUnit() {
        return unit;
    }

    // Set ingredient ID
    public void setId(int id) {
        this.id = id;
    }

    // Set recipe ID
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    // Set ingredient name
    public void setName(String name) {
        this.name = name;
    }

    // Set required quantity
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    // Set required unit
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
