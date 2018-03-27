package au.com.realestate.mvp.models;
import java.util.ArrayList;
import java.util.List;

public class ApiResponse<T> {
    public String errorMessage;
    public Boolean hasError;
    public Boolean hasResponse;
    public List<T> responseObject;
    public List<Tram> southResponseObject=new ArrayList<Tram>();
}


