package br.cooking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;


import br.cooking.R;
import br.cooking.model.Recipe;
import br.cooking.repo.Repo;
import br.cooking.repo.RepoFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WConfigActivity extends AppCompatActivity {

    @BindView(R.id.radioGroup)
    RadioGroup namesRadioGroup;

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private ArrayList<Recipe> receitas;
    private static final int START_INDEX = 0;
    private static final int MIN_SIZE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
        }

        if(isOnline()){
            Repo repo = RepoFactory.create(Repo.class,Repo.ENDPOINT);
            Observable<ArrayList<Recipe>> observable = repo.getReceitas();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(receitas -> {
                            this.receitas = receitas;
                        if(receitas.isEmpty()){
                            finish();
                        } else {
                            // Fill the radioGroup
                            int currentIndex = START_INDEX;
                            for (Recipe recipe : receitas) {
                                AppCompatRadioButton button = new AppCompatRadioButton(this);
                                button.setText(recipe.name());
                                button.setId(currentIndex++);
                                namesRadioGroup.addView(button);
                            }

                            if (namesRadioGroup.getChildCount() > MIN_SIZE) {
                                ((AppCompatRadioButton) namesRadioGroup.getChildAt(START_INDEX)).setChecked(true);
                            }
                        }
                    });
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @OnClick(R.id.button)
    public void onOkButtonClick() {
        int checkedItemId = namesRadioGroup.getCheckedRadioButtonId();
        Recipe receita = receitas.get(checkedItemId);

        WProvider.updateAppWidget(getApplicationContext(),AppWidgetManager.getInstance(getApplicationContext()), mAppWidgetId, receita);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

}
