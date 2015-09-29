package myhealth.com.myhealth.api;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import myhealth.com.myhealth.R;

public class ErrorListener implements Response.ErrorListener {
    private final APIInterface presenter;
    private final AppCompatActivity activity;

    public ErrorListener(APIInterface presenter, AppCompatActivity activity) {
        this.presenter = presenter;
        this.activity = activity;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error.networkResponse != null) {
            presenter.onErrorResponse(error);

            handleErrorWithToast(error, activity);
        }
    }

    /**
     * Default error handling that get the error message and display it in a Toast
     *
     * @param error
     * @param activity
     */
    private void handleErrorWithToast(VolleyError error, AppCompatActivity activity) {
        // Default error handling implementation
        try {
            // Get response body from the VolleyError object
            String responseBody = new String(error.networkResponse.data, "utf-8");
            // Get the JSON object
            JSONObject jsonObject = new JSONObject(responseBody);
            // Get the string from the "error" object
            String errorString = jsonObject.getString("error");
            // Create a Toast message
            Toast.makeText(activity, errorString, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            // Handle a malformed json response
            Log.e("JSON Exception", e.toString());
        } catch (UnsupportedEncodingException e) {
            // Handle an unsupported encoding
            Log.e("UnsupportedEncodingE", e.toString());
        } catch (NullPointerException e) {
            Toast.makeText(activity, activity.getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
