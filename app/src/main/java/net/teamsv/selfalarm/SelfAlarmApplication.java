package net.teamsv.selfalarm;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by Nolsigan on 16. 5. 12..
 */
public class SelfAlarmApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }

}
