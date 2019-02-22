package br.cooking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;


import br.cooking.R;
import br.cooking.model.Ingredient;
import br.cooking.model.Recipe;

public class WProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe receita) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list);
        views.setTextViewText(R.id.w_recipe_name, receita.name());
        views.removeAllViews(R.id.w_ingredients);

        for (Ingredient ingrediente : receita.ingredients()) {
            StringBuilder sb = new StringBuilder();
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                    R.layout.widget_item);

            String name = ingrediente.ingredient();
            float quantidade = ingrediente.quantity();
            String medida = ingrediente.measure();
            sb.append(name +" - "+quantidade+" - "+medida);

            ingredientView.setTextViewText(R.id.widget_ingredient_name, sb);
            views.addView(R.id.w_ingredients, ingredientView);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
