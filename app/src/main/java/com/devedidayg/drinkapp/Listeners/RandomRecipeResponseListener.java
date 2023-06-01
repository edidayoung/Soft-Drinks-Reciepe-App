package com.devedidayg.drinkapp.Listeners;

import com.devedidayg.drinkapp.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
