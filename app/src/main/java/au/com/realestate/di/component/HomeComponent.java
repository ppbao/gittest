package au.com.realestate.di.component;

import au.com.realestate.di.module.HomeModule;
import au.com.realestate.di.scope.PerActivity;
import au.com.realestate.modules.hometime.MainActivity;
import dagger.Component;


@PerActivity
@Component(modules = HomeModule.class, dependencies = ApplicationComponent.class)
public interface HomeComponent {

    void inject(MainActivity activity);

}
