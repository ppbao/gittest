package au.com.realestate.modules.hometime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import au.com.realestate.base.BaseActivity;
import au.com.realestate.di.component.DaggerHomeComponent;
import au.com.realestate.di.module.HomeModule;
import au.com.realestate.modules.hometime.adapter.HomeAdapter;
import au.com.realestate.mvp.models.Tram;
import au.com.realestate.mvp.presenter.HomePresenter;
import au.com.realestate.mvp.view.MainView;
import butterknife.BindView;

import java.util.List;

import javax.inject.Inject;



public class MainActivity extends BaseActivity implements MainView {


    @Inject
    protected HomePresenter mPresenter;

    @BindView(R.id.northListView)
    protected RecyclerView northListRecycler;

    @BindView(R.id.southListView)
    protected RecyclerView southListRecycler;

    private HomeAdapter homeNorthAdapter, homeSouthAdapter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeRecycler();
        if (isNetworkAvailable())
            mPresenter.getToken();
        else
            Toast.makeText(MainActivity.this, "No internet connection.", Toast.LENGTH_SHORT).show();

    }

    private void initializeRecycler() {

        northListRecycler.setHasFixedSize(true);
        northListRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        homeNorthAdapter = new HomeAdapter(getLayoutInflater());
        northListRecycler.setAdapter(homeNorthAdapter);

        southListRecycler.setHasFixedSize(true);
        southListRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        homeSouthAdapter = new HomeAdapter(getLayoutInflater());
        southListRecycler.setAdapter(homeSouthAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void resolveDaggerDependency() {

        DaggerHomeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homeModule(new HomeModule(this))
                .build().inject(this);
    }

    @Override
    public void onNorthListLoaded(List<Tram> northList) {

        homeNorthAdapter.addList(northList);
    }

    @Override
    public void onSouthListLoaded(List<Tram> southList) {
        homeSouthAdapter.addList(southList);
    }

    public void clearClick(View view) {
        homeNorthAdapter.clearList();
        homeSouthAdapter.clearList();
    }

    public void refreshClick(View view) {
        if (isNetworkAvailable())
            mPresenter.getToken();
        else
            Toast.makeText(MainActivity.this, "No internet connection.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onShowTost(String message) {
        hideDialog();
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

}
