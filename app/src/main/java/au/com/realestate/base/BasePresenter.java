package au.com.realestate.base;


import javax.inject.Inject;

import au.com.realestate.mvp.view.BaseView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * All presenter extended by base presenter and we define all common functions here for presenters.
 */

public class BasePresenter<V extends BaseView> {


    @Inject protected V mView;

    protected V getView()
    {
        return mView;
    }

    protected <T> void subscribe(Observable<T> observable, Observer<T> observer)
    {
        observable.subscribeOn(Schedulers.newThread())
                .toSingle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
