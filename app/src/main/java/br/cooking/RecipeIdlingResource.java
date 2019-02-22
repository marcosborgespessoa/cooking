package br.cooking;

import android.support.annotation.Nullable;

public class RecipeIdlingResource implements android.support.test.espresso.IdlingResource {

    private Boolean isIdleNow = Boolean.TRUE;

    @Nullable
    private volatile ResourceCallback callback;

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean idleState) {
        isIdleNow = idleState;
        if (idleState && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}
