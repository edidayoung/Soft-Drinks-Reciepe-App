package com.devedidayg.drinkapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.devedidayg.drinkapp.Listeners.RecipeClickListener;
import com.devedidayg.drinkapp.Models.Recipe;
import com.devedidayg.drinkapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder>{
    Context context;
    List<Recipe> list;
    RecipeClickListener listener;

    public RandomRecipeAdapter(Context context, List<Recipe> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        Recipe recipe = list.get(position);
        holder.textView_title.setText(list.get(position).title);
        holder.textView_title.setSelected(true);
        holder.textView_favourite.setText("Add to Likes");
        holder.textView_servings.setText(list.get(position).servings + " Served");
        holder.textView_shares.setText("Share");
        holder.imageView_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareRecipe(recipe);
            }
        });
        Picasso.get().load(list.get(position).image).into(holder.imageView_drinks);

        holder.random_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = holder.getAdapterPosition();
                listener.onRecipeClick(String.valueOf(list.get(itemPosition).id));
            }
        });

        if (list.get(position).isFavorite) {
            holder.imageView_favourite.setImageResource(R.drawable.ic_favourite);
        } else {
            holder.imageView_favourite.setImageResource(R.drawable.ic_favourite_border);
        }
        holder.imageView_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle the isFavorite flag
                int itemPosition = holder.getAdapterPosition();
                Recipe recipe = list.get(itemPosition);
                recipe.isFavorite = !recipe.isFavorite;

                // Set the favorite icon drawable based on the updated isFavorite flag
                if (recipe.isFavorite) {
                    holder.imageView_favourite.setImageResource(R.drawable.ic_favourite);
                } else {
                    holder.imageView_favourite.setImageResource(R.drawable.ic_favourite_border);
                }

                // Perform any other actions you need when the favorite icon is clicked
                // For example, you can update the database or show a toast message
                if (recipe.isFavorite) {
                    // Recipe is now favorite
                    Toast.makeText(context, "Added to Likes", Toast.LENGTH_SHORT).show();
                } else {
                    // Recipe is no longer favorite
                    Toast.makeText(context, "Removed from Liked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private void shareRecipe(Recipe recipe) {
        String shareText = "Hey check out this cool recipe: " + recipe.title + "\n\n\n" + recipe.summary;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, recipe.title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        context.startActivity(Intent.createChooser(shareIntent, "Share Recipe"));
    }

}
class RandomRecipeViewHolder extends RecyclerView.ViewHolder{
    CardView random_list_container;
    TextView textView_title, textView_favourite, textView_servings, textView_shares;
    ImageView imageView_drinks, imageView_share, imageView_favourite;

    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_favourite = itemView.findViewById(R.id.textView_favourite);
        textView_servings = itemView.findViewById(R.id.textView_servings);
        textView_shares = itemView.findViewById(R.id.textView_shares);
        imageView_drinks = itemView.findViewById(R.id.imageView_drinks);
        imageView_share = itemView.findViewById(R.id.imageView_share);
        imageView_favourite = itemView.findViewById(R.id.imageView_favourite);
    }
}
