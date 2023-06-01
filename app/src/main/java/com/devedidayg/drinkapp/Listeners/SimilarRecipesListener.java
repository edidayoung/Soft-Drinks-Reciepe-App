package com.devedidayg.drinkapp.Listeners;

import com.devedidayg.drinkapp.Models.SimilarRecipeResponse;

import java.util.List;

public interface SimilarRecipesListener {
    void didFetch(List<SimilarRecipeResponse> responses, String message);
    void didError(String message);
}
