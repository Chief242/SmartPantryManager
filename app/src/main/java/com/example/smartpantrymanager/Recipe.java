package com.example.smartpantrymanager;

import java.util.ArrayList;

public class Recipe {

    private int id;
    private String name;
    private String instructions;
    private ArrayList<RecipeIngredient> ingredients;

    public Recipe(
            int id,
            String name,
            String instructions
    ) {

        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.ingredients = new ArrayList<>();
    }

    // Get recipe ID
    public int getId() {
        return id;
    }

    // Get recipe name
    public String getName() {
        return name;
    }

    // Get preparation instructions
    public String getInstructions() {
        return instructions;
    }

    // Get recipe ingredients
    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    // Add an ingredient to this recipe
    public void addIngredient(
            RecipeIngredient ingredient
    ) {
        ingredients.add(ingredient);
    }

    // Set recipe ID
    public void setId(int id) {
        this.id = id;
    }

    // Set recipe name
    public void setName(String name) {
        this.name = name;
    }

    // Set recipe instructions
    public void setInstructions(
            String instructions
    ) {
        this.instructions = instructions;
    }

    // Set recipe ingredients
    public void setIngredients(
            ArrayList<RecipeIngredient> ingredients
    ) {

        this.ingredients = ingredients;
    }
}
