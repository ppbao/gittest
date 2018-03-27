package au.com.realestate.di.module;


import au.com.realestate.api.ApiService;
import au.com.realestate.di.scope.PerActivity;
import au.com.realestate.mvp.view.MainView;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


    @Module
    public class HomeModule {


        private MainView view;

        public HomeModule(MainView view)
        {
            this.view=view;
        }


        @PerActivity
        @Provides
        ApiService provideApiService(Retrofit retrofit)
        {
            return retrofit.create(ApiService.class);
        }

        @PerActivity
        @Provides
        MainView providesView()
        {
            return view;
        }

}
