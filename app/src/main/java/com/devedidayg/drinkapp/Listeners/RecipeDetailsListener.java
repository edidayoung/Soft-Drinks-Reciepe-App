package com.devedidayg.drinkapp.Listeners;

import com.devedidayg.drinkapp.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
