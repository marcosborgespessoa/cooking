package br.cooking.repo;

import java.util.ArrayList;

import br.cooking.model.Recipe;
import io.reactivex.Observable;
import retrofit2.http.GET;


public interface Repo {

    String ENDPOINT = "https://d17h27t6h515a5.cloudfront.net/";

    @GET("topher/2017/May/59121517_baking/baking.json")
    Observable<ArrayList<Recipe>> getReceitas();
}
