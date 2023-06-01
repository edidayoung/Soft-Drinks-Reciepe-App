package com.devedidayg.drinkapp.Listeners;

import com.devedidayg.drinkapp.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
