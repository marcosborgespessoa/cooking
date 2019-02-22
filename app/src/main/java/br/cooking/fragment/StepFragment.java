package br.cooking.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.baking.fragment.StepItemFragment;

import java.util.ArrayList;

import br.cooking.R;
import br.cooking.adapter.StepAdapter;
import br.cooking.model.Step;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepFragment extends Fragment {

    @BindView(R.id.recipe_step_viewpager)
    ViewPager recipeStepViewPager;

    @BindString(R.string.intent_detail_put_extra)
    String intentDetail;
    @BindString(R.string.step_position)
    String intentStepPosition;

    private StepAdapter stepAdapter;
    private ArrayList<Step> steps;
    private int stepPosition;
    private int mPreviousPos;

    Unbinder unbinder;

    public StepFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        unbinder = ButterKnife.bind(this, view);
        steps = getActivity().getIntent().getExtras().getParcelableArrayList(intentDetail);
        stepPosition = getActivity().getIntent().getExtras().getInt(intentStepPosition);
        stepAdapter = new StepAdapter(getFragmentManager(), steps, getContext());
        recipeStepViewPager.setAdapter(stepAdapter);
        recipeStepViewPager.setCurrentItem(stepPosition);
        stepAdapter.notifyDataSetChanged();
        setUpViewPagerListener();

        return view;
    }

    private void setUpViewPagerListener() {
        recipeStepViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                try {
                    ((StepItemFragment) stepAdapter.getItem(mPreviousPos)).onStop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPreviousPos = position;

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
