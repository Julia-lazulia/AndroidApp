package myhealth.com.myhealth.api;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * API holds all of the API endpoints and can fire off API requests
 */
public class API {

    // HTTP request methods
    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int DELETE = 3;
    // API endpoints
    public static final String USER_LOGIN_POST = "user/login";
    public static final String USER_PASSWORD_PUT = "user/password";
    public static final String ECG_GET = "measurement/ecg";
    public static final String ECG_POST = "measurement/ecg";
    public static final String BLOODPRESSURE_GET = "measurement/bloodpressure";
    public static final String BLOODPRESSURE_POST = "measurement/bloodpressure";
    public static final String PULSE_GET = "measurement/pulse";
    public static final String PULSE_POST = "measurement/pulse";
    // The base url of the API
    private static final String BASE_URL = "https://myhealth-hanze.herokuapp.com/api/v1/";

    /**
     * Can perform a HTTP request to the server
     *
     * @param endpoint
     * @param method
     * @param presenter
     * @param activity
     * @param parameters
     * @param authenticated
     */
    public void request(final String endpoint, final int method, final APIInterface presenter,
                        final AppCompatActivity activity, final Map<String, String> parameters,
                        final boolean authenticated) {
        StringRequest postRequest = new PostRequest(BASE_URL + endpoint, method, presenter,
                activity, parameters, authenticated);

        // Finally, add the request to the queue
        Volley.newRequestQueue(activity).add(postRequest);
    }
}
