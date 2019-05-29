package com.example.foodrecipe;

public class recipe {

    String id,recipeName,recipeDescription,cuisine,recipeIngredients,image;

    public recipe()
    {

    }

    public recipe(String id, String RecipeName, String RecipeDescription, String cuisine, String RecipeIngredients,String image) {
        this.id = id;
        this.recipeName = RecipeName;
        this.recipeDescription = RecipeDescription;
        this.cuisine = cuisine;
        this.recipeIngredients = RecipeIngredients;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }


}
