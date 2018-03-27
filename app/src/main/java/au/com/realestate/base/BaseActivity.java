package au.com.realestate.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import au.com.realestate.application.TestApplication;
import au.com.realestate.di.component.ApplicationComponent;
import butterknife.ButterKnife;
import butterknife.Unbinder;

    /**
     * All activities extended by base activity and we define all common functions here.
     */

    public abstract class BaseActivity extends AppCompatActivity {

        private ProgressDialog mProgressDialog;
        Unbinder unbinder;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(getContentView());
            unbinder = ButterKnife.bind(this);
            onViewReady(savedInstanceState, getIntent());

        }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {

        // Tobe used by child activity
        resolveDaggerDependency();

    }

    protected ApplicationComponent getApplicationComponent() {
        return ((TestApplication) getApplication()).getApplicationComponent();
    }

    protected void resolveDaggerDependency() {

    }

    protected void showDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    protected abstract int getContentView();

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    protected Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
