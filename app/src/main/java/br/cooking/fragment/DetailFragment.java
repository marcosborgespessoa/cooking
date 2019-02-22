package br.cooking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.fragment.StepItemFragment;

import br.cooking.R;
import br.cooking.activity.StepActivity;
import br.cooking.adapter.DetailAdapter;
import br.cooking.model.Ingredient;
import br.cooking.model.Recipe;
import br.cooking.model.Step;
import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DetailFragment extends Fragment implements DetailAdapter.RecipeDetailsClickOnClickHandler {

    @BindView(R.id.recipe_details_ingredients)
    TextView recipeDetailsIngredients;
    @BindView(R.id.recipe_details_steps)
    RecyclerView recyclerViewSteps;

    @BindString(R.string.intent_detail_put_extra)
    String intentDetail;
    @BindString(R.string.step_position)
    String stepPosition;
    @BindString(R.string.ingredients_header)
    String ingredientsHeader;

    @BindBool(R.bool.two_pane_mode)
    boolean twoPaneMode;

    private DetailAdapter adapter;
    private Recipe recipe;
    private Unbinder unbinder;


    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        recipe = (Recipe) getActivity().getIntent().getExtras().getParcelable(intentDetail);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadIngredients();
        loadSteps();
    }


    public void loadIngredients() {
        StringBuilder sb = new StringBuilder();
        sb.append(ingredientsHeader);
        for (Ingredient ingredient : recipe.ingredients()) {
            String name = ingredient.ingredient();
            float quantity = ingredient.quantity();
            String measure = ingredient.measure();
            sb.append("\n");
            sb.append(name + " - " + quantity + " - " + measure);
        }
        recipeDetailsIngredients.setText(sb);
    }

    private void loadSteps() {
        recyclerViewSteps.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewSteps.setLayoutManager(layoutManager);
        adapter = new DetailAdapter(this);
        adapter.setStepData(recipe.steps());
        recyclerViewSteps.setAdapter(adapter);
        recyclerViewSteps
                .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        if (twoPaneMode) {
            onClick(recipe.steps().get(0), 0);
        }
    }

    @Override
    public void onClick(Step step, int position) {
        if (twoPaneMode) {
            StepItemFragment fragment = StepItemFragment.newInstance(step);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.recipe_step_container, fragment);
            transaction.commit();
        } else {
            Intent intent = new Intent(getActivity(), StepActivity.class);
            intent.putExtra(intentDetail, recipe.steps());
            intent.putExtra(stepPosition, position);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
