package br.cooking.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by vinicius.rocha on 2/3/18.
 */

@AutoValue
public abstract class Ingredient implements Parcelable {

    public abstract Float quantity();
    public abstract String measure();
    public abstract String ingredient();


    public static Ingredient create(Float quantity, String measure, String ingredient) {
        return new AutoValue_Ingredient(quantity, measure, ingredient);
    }

    public static TypeAdapter<Ingredient> typeAdapter(Gson gson) {
        return new AutoValue_Ingredient.GsonTypeAdapter(gson);
    }

}
