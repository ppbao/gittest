package au.com.realestate.application;

import android.app.Application;

import au.com.realestate.di.component.ApplicationComponent;
import au.com.realestate.di.component.DaggerApplicationComponent;
import au.com.realestate.di.module.ApplicationModule;
import au.com.realestate.modules.hometime.R;


/**
 * Main application class which will initialize the Dagger App component.
 */

public class TestApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {

       applicationComponent= DaggerApplicationComponent.builder()
               .applicationModule(new ApplicationModule(this,getString(R.string.base_url)))
               .build();
    }


    public ApplicationComponent getApplicationComponent()
    {
        return applicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
