package startup.androidapp.agrisense.formulario.Models;

import android.content.Context;

public class DataConstant {
    public static Context applicationContext = null;
    public static boolean isDebuggable = true;
    public static String PREF_IS_LOGIN = "PREF_IS_LOGIN";
    public static String PREF_AUTH_TOKEN = "PREF_AUTH_TOKEN";
    public static String PREF_USER = "PREF_USER";
    public static String SERVER_URL = "http://ws.agrisense.technology/";
    public static String BASE_URL = SERVER_URL + "mdataaccess/";
    public static String ASSET_URL = SERVER_URL + "assets/uploaded/";
    public static String JWT_KEY = "NCK*";
    public static String FOLDER_SAVE = "Android/data/startup.androidapp.agrisense.formulario/";
    public static String DEFAULT_FILENAME = "agri_";
    public static int REQUEST_CAMERA = 101;
    public static int SELECT_FILE = 100;
    public static int MAX_PAGE = 5;
    public static int COUNT_ROW = 5;
    public static String APPS_TAG = "AGRISENSE";
}
