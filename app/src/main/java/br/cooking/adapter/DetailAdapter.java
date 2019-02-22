package br.cooking.adapter;

import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.cooking.R;

import java.util.ArrayList;

import br.cooking.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;



public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.RecipeDetailsViewHolder>{

    private ArrayList<Step> steps;
    private final RecipeDetailsClickOnClickHandler recipeDetailsClickOnClickHandler;


    public DetailAdapter(RecipeDetailsClickOnClickHandler recipeDetailsClickOnClickHandler){
        this.recipeDetailsClickOnClickHandler = recipeDetailsClickOnClickHandler;
    }

    @Override
    public RecipeDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_detail_item, parent, false);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        return new DetailAdapter.RecipeDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeDetailsViewHolder holder, int position) {
        holder.bind(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void setStepData(ArrayList<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    class RecipeDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_description)
        TextView stepDescription;
        @BindView(R.id.icon_step)
        ImageView iconStep;

        public RecipeDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step step = steps.get(adapterPosition);
            recipeDetailsClickOnClickHandler.onClick(step, adapterPosition);
        }

        protected void bind(Step step){
            stepDescription.setText(step.shortDescription());
            if(step.videoURL().isEmpty()){
                iconStep.setVisibility(View.INVISIBLE);
            }
        }
    }

    public interface RecipeDetailsClickOnClickHandler {
        void onClick(Step step, int position);
    }
}
