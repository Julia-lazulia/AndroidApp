package myhealth.com.myhealth.api;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostRequest extends StringRequest {
    private final AppCompatActivity activity;
    private final Map<String, String> parameters;
    private final boolean authenticated;

    public PostRequest(String url, int method, final APIInterface presenter, AppCompatActivity activity,
                       Map<String, String> parameters, boolean authenticated) {
        super(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    presenter.onResponse(response);
                }
            }
        }, new ErrorListener(presenter, activity));
        this.activity = activity;
        this.parameters = parameters;
        this.authenticated = authenticated;
    }

    /**
     * Get the JWT and puts in in a HashMap, or creates an empty HashMap
     * if user is not authenticated.
     *
     * @param parameters
     * @param activity
     * @param authenticated
     * @return
     */
    private Map<String, String> getTokenAndPutInHashMap(Map<String, String> parameters,
                                                        AppCompatActivity activity,
                                                        boolean authenticated) {
        if (authenticated) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(activity.getBaseContext());
            String token = prefs.getString("jwt", "");
            parameters.put("Authorization", "Bearer " + token);
        } else {
            parameters = new HashMap<>();
        }
        return parameters;
    }

    @Override
    protected Map<String, String> getParams() {
        if (this.parameters == null) {
            return new HashMap<>();
        } else {
            return this.parameters;
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return getTokenAndPutInHashMap(this.parameters, this.activity, this.authenticated);
    }
}
