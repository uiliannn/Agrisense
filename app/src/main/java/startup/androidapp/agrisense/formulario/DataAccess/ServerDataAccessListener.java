package startup.androidapp.agrisense.formulario.DataAccess;

import java.io.IOException;

import okhttp3.Request;


public interface ServerDataAccessListener {

    public void onFailure(Request request, IOException e);

    public void onResponseSuccess(String responseBody) throws IOException;

    public void onResponseError(String response);

}
