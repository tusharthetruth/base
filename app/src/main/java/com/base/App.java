package com.base;

import android.app.Application;
import android.content.res.Resources;

/**
 * Created by appstreet on 2/23/17.
 */

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        if (instance == null) {
            throw new IllegalStateException();
        }
        return instance;
    }

    public static Resources R() {
        return instance.getResources();
    }
}
