package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // ----------------------------------
    // Database information
    // ----------------------------------

    private static final String DATABASE_NAME = "smartPantry.db";

    // Version 2 because we are adding recipe tables
    private static final int DATABASE_VERSION = 2;

    // ----------------------------------
    // Pantry table
    // ----------------------------------
    public static final String TABLE_PANTRY = "pantry";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UNIT = "unit";

    // Create pantry table
    private static final String CREATE_PANTRY_TABLE =
            "CREATE TABLE " + TABLE_PANTRY + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_QUANTITY + " REAL NOT NULL, " +
                    COLUMN_UNIT + " TEXT NOT NULL" +
                    ")";

    // ----------------------------------
    // Recipes Table
    // ----------------------------------

    public static final String TABLE_RECIPES = "recipes";

    public static final String RECIPE_ID = "recipe_id";
    public static final String RECIPE_NAME = "recipe_name";
    public static final String RECIPE_INSTRUCTIONS = "recipe_instructions";

    // Create recipes table
    private static final String CREATE_RECIPES_TABLE =
            "CREATE TABLE " + TABLE_RECIPES + " (" +
                    RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RECIPE_NAME + " TEXT NOT NULL, " +
                    RECIPE_INSTRUCTIONS + " TEXT NOT NULL" +
                    ")";

    // ----------------------------------
    // Recipe Ingredients Table
    // ----------------------------------

    public static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    public static final String RECIPE_INGREDIENT_ID =
            "recipe_ingredient_id";

    public static final String RECIPE_INGREDIENT_RECIPE_ID =
            "recipe_id";

    public static final String RECIPE_INGREDIENT_NAME =
            "ingredient_name";

    public static final String RECIPE_INGREDIENT_QUANTITY =
            "required_quantity";

    public static final String RECIPE_INGREDIENT_UNIT =
            "required_unit";

    // Create recipe ingredients table
    private static final String CREATE_RECIPE_INGREDIENTS_TABLE =
            "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                    RECIPE_INGREDIENT_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    RECIPE_INGREDIENT_RECIPE_ID +
                    " INTEGER NOT NULL, " +

                    RECIPE_INGREDIENT_NAME +
                    " TEXT NOT NULL, " +

                    RECIPE_INGREDIENT_QUANTITY +
                    " REAL NOT NULL, " +

                    RECIPE_INGREDIENT_UNIT +
                    " TEXT NOT NULL" +
                    ")";

    // ----------------------------------
    // Constructor
    // ----------------------------------

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ----------------------------------
    // Database Creation
    // ----------------------------------

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create pantry table
        db.execSQL(CREATE_PANTRY_TABLE);

        // Create recipes table
        db.execSQL(CREATE_RECIPES_TABLE);

        // Create recipe ingredients table
        db.execSQL(CREATE_RECIPE_INGREDIENTS_TABLE);

        // Add the initial recipes
        seedRecipes(db);
    }

    // ----------------------------------
    // Database Upgrade
    // ----------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Version 1 -> Version 2

        if (oldVersion < 2) {

            // Do not delete the pantry table.
            // Existing pantry data must remain

            db.execSQL(CREATE_RECIPES_TABLE);

            db.execSQL(CREATE_RECIPE_INGREDIENTS_TABLE);

            seedRecipes(db);
        }
    }

    // ----------------------------------
    // Repair Old Seeded Data
    // ----------------------------------

    @Override
    public void onOpen(SQLiteDatabase db) {

        super.onOpen(db);

        // Repair old seeded data if it was created
        // before the typo fixes

        db.execSQL(
                "UPDATE " + TABLE_RECIPE_INGREDIENTS +
                        " SET " + RECIPE_INGREDIENT_UNIT +
                        "='Pieces' WHERE " +
                        RECIPE_INGREDIENT_UNIT +
                        "='Peices'"
        );

        db.execSQL(
                "UPDATE " + TABLE_RECIPES +
                        " SET " + RECIPE_NAME +
                        "='Egg Sandwich' WHERE " +
                        RECIPE_NAME +
                        "='Eggs Sandwich'"
        );
    }


    // ----------------------------------
    // Pantry - CREATE
    // ----------------------------------

    public long addIngredient(
            String name,
            double quantity,
            String unit
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);

        long result = db.insert(
                TABLE_PANTRY,
                null,
                values
        );

        db.close();

        return result;
    }

    // ----------------------------------
    // Pantry - Read
    // ----------------------------------

    public ArrayList<Ingredient> getAllIngredients() {

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                COLUMN_ID + " ASC"
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(COLUMN_ID)
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_NAME)
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_UNIT)
                );

                Ingredient ingredient = new Ingredient(
                        id,
                        name,
                        quantity,
                        unit
                );

                ingredients.add(ingredient);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ingredients;
    }

    // ----------------------------------
    // Pantry - Update
    // ----------------------------------

    public int updateIngredient(
            int id,
            String name,
            double quantity,
            String unit
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);

        int result = db.update(
                TABLE_PANTRY,
                values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return result;
    }

    // ----------------------------------
    // Pantry - Delete
    // ----------------------------------
    public int deleteIngredient(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_PANTRY,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return result;
    }

    // ----------------------------------
    // Recipe - Add
    // ----------------------------------

    private long addRecipe(
            SQLiteDatabase db,
            String recipeName,
            String instructions
    ) {

        ContentValues values =
                new ContentValues();

        values.put(
                RECIPE_NAME,
                recipeName
        );

        values.put(
                RECIPE_INSTRUCTIONS,
                instructions
        );

        return db.insert(
                TABLE_RECIPES,
                null,
                values
        );
    }

    // ----------------------------------
    // Recipe Ingredient - Add
    // ----------------------------------

    private long addRecipeIngredient(
            SQLiteDatabase db,
            long recipeId,
            String ingredientName,
            double quantity,
            String unit
    ) {

        ContentValues values =
                new ContentValues();

        values.put(
                RECIPE_INGREDIENT_RECIPE_ID,
                recipeId
        );

        values.put(
                RECIPE_INGREDIENT_NAME,
                ingredientName
        );

        values.put(
                RECIPE_INGREDIENT_QUANTITY,
                quantity
        );

        values.put(
                RECIPE_INGREDIENT_UNIT,
                unit
        );

        return db.insert(
                TABLE_RECIPE_INGREDIENTS,
                null,
                values
        );
    }

    // ----------------------------------
    // Seed Recipes
    // ----------------------------------

    private void seedRecipes(
            SQLiteDatabase db
    ) {

        // ----------------------------------
        // Recipe 1 - Scrambled Eggs
        // ----------------------------------

        long recipeId =
                addRecipe(
                        db,
                        "Scrambled Eggs",
                        "Crack the eggs into a bowl and whisk them. "
                                    + "Heat the butter in a pan. Add the eggs "
                                    + " and stir gently until cooked."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Eggs",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Butter",
                10,
                "Grams"
        );

        // ----------------------------------
        // Recipe 2 - Cheese Omelette
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Cheese Omelette",
                        "Whisk the eggs. Cook them in a heated pan "
                                          + "and add cheese before folding the omelette."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Eggs",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Cheese",
                30,
                "Grams"
        );

        // ----------------------------------
        // Recipe 3 - French Toast
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "French Toast",
                        "Beat the eggs with milk. Dip the bread "
                                           + "into the mixture and fry until golden."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Eggs",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Bread",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Milk",
                50,
                "Millilitres"
        );

        // ----------------------------------
        // Recipe 4 - Egg Sandwich
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Egg Sandwich",
                        "Boil the eggs and slice them. Place the eggs "
                                           + "between slices of bread and serve."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Eggs",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Bread",
                2,
                "Pieces"
        );

        // ----------------------------------
        // Recipe 5 - Grilled Cheese Sandwich
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Grilled Cheese Sandwich",
                        "Place cheese between two slices of bread. "
                                           + "Toast both sides in a pan until golden."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Bread",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Cheese",
                40,
                "Grams"
        );

        // ----------------------------------
        // Recipe 6 - Cheese and Egg Sandwich
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Cheese and Egg Sandwich",
                        "Cook the eggs and place them on bread. "
                                           + "Add cheese and serve. "
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Eggs",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Bread",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Cheese",
                30,
                "Grams"
        );

        // ----------------------------------
        // Recipe 7 - Tomato Pasta
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Tomato Pasta",
                        "Cook the pasta. Prepare the tomato sauce "
                                           + "in a pan and mix it with the cooked pasta."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Pasta",
                200,
                "Grams"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Tomatoes",
                2,
                "Pieces"
        );

        // ----------------------------------
        // Recipe 8 - Creamy Pasta
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Creamy Pasta",
                        "Cook the pasta. Heat the milk and cheese "
                                           + "together and mix with the pasta."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Pasta",
                200,
                "Grams"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Milk",
                100,
                "Millilitres"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Cheese",
                40,
                "Grams"
        );

        // ----------------------------------
        // Recipe 9 - Chicken Pasta
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Chicken Pasta",
                        "Cook the pasta. Cook the chicken thoroughly "
                                           + "and combine it with the pasta."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Pasta",
                200,
                "Grams"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Chicken",
                200,
                "Grams"
        );

        // ----------------------------------
        // Recipe 10 - Chicken Sandwich
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Chicken Sandwich",
                        "Cook the chicken thoroughly. Place the chicken "
                                           + "between slices of bread and serve."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Chicken",
                150,
                "Grams"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Bread",
                2,
                "Pieces"
        );

        // ----------------------------------
        // Recipe 11 - Fried Rice
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Fried Rice",
                        "Cook the rice. Fry the eggs and combine them "
                                           + "with the rice in a pan."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Rice",
                200,
                "Grams"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Eggs",
                2,
                "Pieces"
        );

        // ----------------------------------
        // Recipe 12 - Pancakes
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Pancakes",
                        "Mix the flour, eggs and milk into a smooth batter. "
                                           + " Cook the pancakes in a heated pan."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Flour",
                200,
                "Grams"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Eggs",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Milk",
                250,
                "Millilitres"
        );

        // ----------------------------------
        // Recipe 13 - Banana Pancakes
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Banana Pancakes",
                        "Mash the banana and mix it with eggs and flour. "
                                           + "Cook the pancakes in a heated pan."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Bananas",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Eggs",
                2,
                "Pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Flour",
                150,
                "Grams"
        );

        // ----------------------------------
        // Recipe 14 - Beef Stir Fry
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Beef Stir Fry",
                        "Slice the beef and vegetables. Cook everything "
                                           + "together in a hot pan until fully cooked."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Beef",
                200,
                "Grams"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Onions",
                1,
                "Pieces"
        );

        // ----------------------------------
        // Recipe 15 - Chicken and Rice
        // ----------------------------------

        recipeId =
                addRecipe(
                        db,
                        "Chicken and Rice",
                        "Cook the rice. Cook the chicken thoroughly "
                                           + "and serve together."
                );

        addRecipeIngredient(
                db,
                recipeId,
                "Chicken",
                200,
                "Grams"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "Rice",
                200,
                "Grams"
        );
    }

    // -------------------------------
    // Recipe - Read all
    // -------------------------------

    public ArrayList<Recipe> getAllRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                RECIPE_ID + " ASC"
        );

        if (cursor.moveToFirst()) {

            do {

                int recipeId =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        RECIPE_ID
                                )
                        );

                String recipeName =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        RECIPE_NAME
                                )
                        );

                String instructions =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        RECIPE_INSTRUCTIONS
                                )
                        );

                Recipe recipe =
                        new Recipe(
                                recipeId,
                                recipeName,
                                instructions
                        );

                loadRecipeIngredients(
                        db,
                        recipe
                );

                recipes.add(recipe);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recipes;
    }

    // -------------------------------
    // Recipe - Read one
    // -------------------------------

    public Recipe getRecipeById(
            int recipeId
    ) {
        SQLiteDatabase db = this.getReadableDatabase();

        Recipe recipe = null;

        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                RECIPE_ID + "=?",
                new String[]{
                        String.valueOf(recipeId)
                },
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {

            String recipeName =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    RECIPE_NAME
                            )
                    );

            String instructions =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    RECIPE_INSTRUCTIONS
                            )
                    );

            recipe =
                    new Recipe(
                            recipeId,
                            recipeName,
                            instructions
                    );

            loadRecipeIngredients(
                    db,
                    recipe
            );
        }

        cursor.close();
        db.close();

        return recipe;
    }

    // -------------------------------
    // Load Recipe Ingredients
    // -------------------------------

    private void loadRecipeIngredients(
            SQLiteDatabase db,
            Recipe recipe
    ) {

        Cursor cursor =
                db.query(
                        TABLE_RECIPE_INGREDIENTS,
                        null,
                        RECIPE_INGREDIENT_RECIPE_ID + "=?",
                        new String[]{
                                String.valueOf(
                                        recipe.getId()
                                )
                        },
                        null,
                        null,
                        RECIPE_INGREDIENT_ID + " ASC"
                );

        if (cursor.moveToFirst()) {

            do {

                int id =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        RECIPE_INGREDIENT_ID
                                )
                        );

                int recipeId =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        RECIPE_INGREDIENT_RECIPE_ID
                                )
                        );

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        RECIPE_INGREDIENT_NAME
                                )
                        );

                double quantity =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        RECIPE_INGREDIENT_QUANTITY
                                )
                        );

                String unit =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        RECIPE_INGREDIENT_UNIT
                                )
                        );

                RecipeIngredient ingredient =
                        new RecipeIngredient(
                                id,
                                recipeId,
                                name,
                                quantity,
                                unit
                        );

                recipe.addIngredient(
                        ingredient
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
