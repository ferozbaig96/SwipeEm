package com.example.fbulou.swipeem.getMyApplicationContext;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
/*

    must add the following in AndroidManifest.xml under application tag
    android:name="getMyApplicationContext.MyApplication"
*/

    private static MyApplication Instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance=this;
    }

    public static MyApplication getInstance()
    {
        return Instance;
    }

    public static Context getAppContext()
    {
        return Instance.getApplicationContext();
    }
}
