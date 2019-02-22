package br.cooking.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.cooking.R;
import br.cooking.model.Recipe;
import butterknife.BindString;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindString(R.string.intent_detail_put_extra)
    String intentDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(((Recipe) getIntent().getExtras().
                getParcelable(intentDetail)).name());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
