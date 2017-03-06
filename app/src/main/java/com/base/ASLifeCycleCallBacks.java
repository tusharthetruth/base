package com.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;

/**
 * Created by appstreet on 12/14/16.
 */

public class ASLifeCycleCallBacks implements Application.ActivityLifecycleCallbacks {

    private static final String LOG_TAG = "APP";

    private enum AppState {
        NONE,
        FOREGROUND,
        BACKGROUND
    }

    private WeakReference<AppLifeCycleListener> mListener = new WeakReference<>(null);

    private AppState mState = AppState.NONE;
    private int mStartedActivityCount = 0;

    public static String getLifeCycleLogTag(Object object) {
        return "LIFECYCLE::" + object.getClass().getSimpleName() + object.hashCode();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
//        App.log().v(getLifeCycleLogTag(activity), "onCreate" + (bundle == null ? " [first launch]" : " [relaunch]"));
    }

    @Override
    public void onActivityStarted(Activity activity) {
//        App.log().v(getLifeCycleLogTag(activity), "onStart");
        mStartedActivityCount++;
        if (mStartedActivityCount > 0)
            changeState(AppState.FOREGROUND);
    }

    @Override
    public void onActivityResumed(Activity activity) {
//        App.log().v(getLifeCycleLogTag(activity), "onResume");
    }

    @Override
    public void onActivityPaused(Activity activity) {
//        App.log().v(getLifeCycleLogTag(activity), "onPause");
    }

    @Override
    public void onActivityStopped(Activity activity) {
//        App.log().v(getLifeCycleLogTag(activity), "onStop");
        mStartedActivityCount--;
        if (mStartedActivityCount <= 0)
            changeState(AppState.BACKGROUND);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
//        App.log().v(getLifeCycleLogTag(activity), "onSaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
//        App.log().v(getLifeCycleLogTag(activity), "onDestroy");
    }

    public void register(AppLifeCycleListener listener) {
        mListener = new WeakReference<>(listener);
    }

    private void changeState(AppState newState) {
        if (mState == newState)
            return;

        mState = newState;

        if (mListener.get() == null)
            return;

        switch (mState) {
            case NONE:
                break;
            case FOREGROUND:
//                App.log().v(LOG_TAG, "onAppForegrounded");
                mListener.get().onAppForegrounded();
                break;
            case BACKGROUND:
//                App.log().v(LOG_TAG, "onAppBackgrounded");
                mListener.get().onAppBackgrounded();
                break;
        }
    }

    public interface AppLifeCycleListener {
        void onAppForegrounded();

        void onAppBackgrounded();
    }
}
