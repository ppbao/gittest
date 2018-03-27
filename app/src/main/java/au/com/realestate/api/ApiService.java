package au.com.realestate.api;

import au.com.realestate.mvp.models.ApiResponse;
import au.com.realestate.mvp.models.Token;
import au.com.realestate.mvp.models.Tram;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Api class defines the get request for Token and Trams using RxJava.
 */

public interface ApiService {

    //With RxJava
    @GET("/TramTracker/RestService/GetDeviceToken/?aid=TTIOSJSON&devInfo=HomeTimeAndroid")
    Observable<ApiResponse<Token>> getToken();

    @GET("/TramTracker/RestService//GetNextPredictedRoutesCollection/{stopId}/78/false/?aid=TTIOSJSON&cid=2")
    Observable<ApiResponse<Tram>> getTrams(
            @Path("stopId") String stopId,
            @Query("tkn") String token
    );
}
