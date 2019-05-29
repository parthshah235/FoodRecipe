package com.example.foodrecipe;

public class retriveRecipe {

    private String recipeName,recipeDescription,cuisine,image,imagePath,recipeIngredients;

    public retriveRecipe()
    {

    }

    public retriveRecipe(String recipeName, String recipeDescription, String cuisine, String image, String imagePath,String recipeIngredients) {
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.cuisine = cuisine;
        this.image =image;
        this.imagePath=imagePath;
        this.recipeIngredients=recipeIngredients;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getrecipeIngredients() {
        return recipeIngredients;
    }

    public void setrecipeIngredients(String ingredients) {
        this.recipeIngredients = ingredients;
    }
}
