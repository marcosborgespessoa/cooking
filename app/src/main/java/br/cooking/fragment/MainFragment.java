package br.cooking.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.cooking.R;
import br.cooking.RecipeIdlingResource;
import br.cooking.activity.DetailActivity;
import br.cooking.activity.MainActivity;
import br.cooking.adapter.MainAdapter;

import br.cooking.model.Recipe;
import br.cooking.repo.Repo;
import br.cooking.repo.RepoFactory;
import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainFragment extends Fragment implements MainAdapter.RecipeClickOnClickHandler {

    @BindView(R.id.recyclerview_recipe)
    RecyclerView recyclerView;
    @BindView(R.id.error_message_display)
    TextView mErrorMessageDisplay;

    @BindString(R.string.intent_detail_put_extra)
    String intentDetail;

    @BindBool(R.bool.two_pane_mode)
    boolean twoPaneMode;

    private Unbinder unbinder;
    private MainAdapter adapter;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isOnline()) {
            load();
        } else {
            showErrorMessage();
        }
    }

    private void load() {
        RecipeIdlingResource idlingResource = ((MainActivity) getActivity()).getReceitaIdlingResource();
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        Repo repo = RepoFactory.create(Repo.class, Repo.ENDPOINT);
        Observable<ArrayList<Recipe>> observable = repo.getReceitas();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes -> {
                    if (twoPaneMode) {
                        adapter = new MainAdapter(this);
                        adapter.setRecipeData(recipes);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    } else {
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new MainAdapter(this);
                        adapter.setRecipeData(recipes);
                        recyclerView.setAdapter(adapter);
                    }
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                });
    }

    @Override
    public void onClick(Recipe receita) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(intentDetail, receita);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showErrorMessage() {
        recyclerView.setVisibility(View.GONE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

}
