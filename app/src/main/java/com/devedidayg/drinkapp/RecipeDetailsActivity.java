package com.devedidayg.drinkapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devedidayg.drinkapp.Adapters.IngredientsAdapter;
import com.devedidayg.drinkapp.Adapters.InstructionsAdapter;
import com.devedidayg.drinkapp.Adapters.SimilarRecipeAdapter;
import com.devedidayg.drinkapp.Listeners.InstructionsListener;
import com.devedidayg.drinkapp.Listeners.RecipeClickListener;
import com.devedidayg.drinkapp.Listeners.RecipeDetailsListener;
import com.devedidayg.drinkapp.Listeners.SimilarRecipesListener;
import com.devedidayg.drinkapp.Models.InstructionsResponse;
import com.devedidayg.drinkapp.Models.RecipeDetailsResponse;
import com.devedidayg.drinkapp.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    int id;
    TextView textView_drinks_name, textView_drinks_source, textView_drinks_summary ;
    ImageView imageView_drinks_image;
    RecyclerView  recyclerView_drinks_ingredients, recycler_drinks_similar, recycler_drinks_instructions;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;

    SimilarRecipeAdapter similarRecipeAdapter;

    InstructionsAdapter instructionsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        id = Integer.parseInt(getIntent().getStringExtra("id"));

        findViews();
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipesListener, id);
        manager.getInstructons(instructionsListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details. . .");
        dialog.show();

    }

    private void findViews() {
        textView_drinks_name = findViewById(R.id.textView_drinks_name);
        textView_drinks_source = findViewById(R.id.textView_drinks_source);
        textView_drinks_summary = findViewById(R.id.textView_drinks_summary);
        imageView_drinks_image = findViewById(R.id.imageView_drinks_image);
        recyclerView_drinks_ingredients = findViewById(R.id.recycler_drinks_ingredients);
        recycler_drinks_similar = findViewById(R.id.recycler_drinks_similar);
        recycler_drinks_instructions = findViewById(R.id.recycler_drinks_instructions);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_drinks_name.setText(response.title);
            textView_drinks_source.setText(response.sourceName);
            textView_drinks_summary.setText(response.summary);
            Picasso.get().load(response.image).into(imageView_drinks_image);

            recyclerView_drinks_ingredients.setHasFixedSize(true);
            recyclerView_drinks_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
            recyclerView_drinks_ingredients.setAdapter(ingredientsAdapter);

        }

        @Override
        public void didError(String message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RecipeDetailsActivity.this);
            builder.setTitle("Error")
                    .setMessage(" Network connection error. Please check your Internet and try again " + message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .show();
        }
    };

    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> responses, String message) {
            recycler_drinks_similar.setHasFixedSize(true);
            recycler_drinks_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this, responses, recipeClickListener);
            recycler_drinks_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();

        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id, String recipeUrl) {

        }

        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));

        }
    };

    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {
            recycler_drinks_instructions.setHasFixedSize(true);
            recycler_drinks_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(RecipeDetailsActivity.this, response);
            recycler_drinks_instructions.setAdapter(instructionsAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
        }
    };
}