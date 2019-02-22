package br.cooking.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.baking.fragment.StepItemFragment;

import java.util.List;

import br.cooking.model.Step;

public class StepAdapter extends FragmentPagerAdapter {

    private List<Step> steps;

    public StepAdapter(FragmentManager fm, List<Step> steps, Context context) {
        super(fm);
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        return StepItemFragment.newInstance(steps.get(position));
    }

    @Override
    public int getCount() {
        return steps.size();
    }
}
