package au.com.realestate.mvp.presenter;

import javax.inject.Inject;
import au.com.realestate.api.ApiService;
import au.com.realestate.base.BasePresenter;
import au.com.realestate.mvp.models.ApiResponse;
import au.com.realestate.mvp.models.Token;
import au.com.realestate.mvp.models.Tram;
import au.com.realestate.mvp.view.MainView;
import rx.Observable;
import rx.Observer;
import rx.functions.Func2;
import rx.schedulers.Schedulers;


public class HomePresenter extends BasePresenter<MainView> implements Observer<ApiResponse<Tram>> {


    @Inject
    protected ApiService apiService;
    private String Token;

    @Inject
    public HomePresenter() {
    }

    public void getToken() {


        getView().onShowDialog("Loading ....");
        if (Token != null)
            getList();
        else {
            Observable<ApiResponse<Token>> tokenResponseObservable = apiService.getToken();
            Observer<ApiResponse<Token>> observer = new Observer<ApiResponse<Token>>() {
                @Override
                public void onCompleted() {

                    if (Token != null)
                        getList();
                    else {
                        getView().onShowTost("Token is empty, Please refersh the page");
                    }
                }

                @Override
                public void onError(Throwable e) {

                    getView().onShowTost("Failed to get Token, Please try again later");
//                    Log.d("Log", e.getMessage());
                }

                @Override
                public void onNext(ApiResponse<Token> response) {
                    if (!response.hasError && response.hasResponse)
                        Token = response.responseObject.get(0).DeviceToken;
                }
            };
            subscribe(tokenResponseObservable, observer);
        }

    }

    public void getList() {

        getView().onShowDialog("Loading ....");

        Observable<ApiResponse<Tram>> tramApisObservable = Observable.zip(apiService.getTrams("4055", Token).subscribeOn(Schedulers.newThread()), apiService.getTrams("4155", Token).subscribeOn(Schedulers.newThread()), new Func2<ApiResponse<Tram>, ApiResponse<Tram>, ApiResponse<Tram>>() {
            @Override
            public ApiResponse<Tram> call(ApiResponse<Tram> northResponse, ApiResponse<Tram> southResponse) {

                if (!northResponse.hasError && northResponse.hasResponse && !southResponse.hasError && southResponse.hasResponse) {
                    northResponse.southResponseObject.addAll(southResponse.responseObject);
                    return northResponse;
                } else return null;
            }
        });
        subscribe(tramApisObservable, this);

    }

    @Override
    public void onCompleted() {
        getView().onHideDialog();
    }

    @Override
    public void onError(Throwable e) {
        getView().onShowTost("Failed to fetch data, Please try again later");
    }

    @Override
    public void onNext(ApiResponse<Tram> tramApiResponse) {
        if (tramApiResponse != null) {
            getView().onSouthListLoaded(tramApiResponse.southResponseObject);
            getView().onNorthListLoaded(tramApiResponse.responseObject);
        } else getView().onShowTost("Error in fetching data. Please try again later");


    }
}
