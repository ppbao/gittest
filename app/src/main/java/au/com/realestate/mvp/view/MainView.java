package au.com.realestate.mvp.view;


import java.util.List;

import au.com.realestate.mvp.models.Tram;


public interface MainView extends BaseView{


    void onNorthListLoaded(List<Tram> northList);

    void onSouthListLoaded(List<Tram> southList);

    void onShowDialog(String message);

    void onShowTost(String message);

    void onHideDialog();

}
