package com.example.cookpadtest1;

public class RecipeCard {
    private String username;
    private int recipe_image;
    private String recipe_name;

    public RecipeCard(String username, int recipe_image, String recipe_name) {
        this.username = username;
        this.recipe_image = recipe_image;
        this.recipe_name = recipe_name;
    }

    public String getUsername() {
        return username;
    }

    public int getRecipe_image() {
        return recipe_image;
    }

    public String getRecipe_name() {
        return recipe_name;
    }
}
