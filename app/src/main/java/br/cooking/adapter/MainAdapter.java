package br.cooking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.cooking.R;


import br.cooking.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ReceitaViewHolder> {

    private ArrayList<Recipe> recipes;
    private final RecipeClickOnClickHandler recipeClickOnClickHandler;

    public MainAdapter(RecipeClickOnClickHandler clickHandler){
        recipeClickOnClickHandler = clickHandler;
    }

    @Override
    public ReceitaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item, parent, false);

        return new ReceitaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceitaViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipeData(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class ReceitaViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener {

        @BindView(R.id.recipe_name)
        TextView recipeName;

        @BindView(R.id.recipe_serving)
        TextView recipeServing;

        public ReceitaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);

        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = recipes.get(adapterPosition);
            recipeClickOnClickHandler.onClick(recipe);
        }

        protected void bind(Recipe recipe){
            recipeName.setText(recipe.name());
            recipeServing.setText("Servers " +recipe.servings().toString() + " people");
        }
    }

    public interface RecipeClickOnClickHandler {
        void onClick(Recipe recipe);
    }
}
