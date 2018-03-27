package au.com.realestate.di.component;

import android.content.Context;
import javax.inject.Singleton;
import au.com.realestate.di.module.ApplicationModule;
import dagger.Component;
import retrofit2.Retrofit;



@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Retrofit exposeRetrofit();

}
