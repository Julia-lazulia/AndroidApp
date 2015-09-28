package myhealth.com.myhealth.passwordEdit;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import myhealth.com.myhealth.R;
import myhealth.com.myhealth.api.API;
import myhealth.com.myhealth.api.APIInterface;
import myhealth.com.myhealth.maingui.MainActivity;

public class PasswordEditPresenter implements APIInterface {
    PasswordEditActivity mView;
    PasswordEditService mService;

    public PasswordEditPresenter(PasswordEditActivity view) {
        mView = view;
        mService = new PasswordEditService((PasswordEditActivity) mView, this);
    }

    public void setService(PasswordEditService service) {
        this.mService = service;
    }

    /**
     * Handles the users interaction with the app
     */
    public void onChangeClicked() {
        String passwordOld = mView.getOldPassword();
        String password1 = mView.getNewPassword1();
        String password2 = mView.getNewPassword2();
        if (password1.isEmpty()) {
            mView.showPassword1Error(R.string.password_empty1);
            return;
        }
        if (password2.isEmpty()) {
            mView.showPassword2Error(R.string.password_empty2);
            return;
        }
        if (!password1.equals(password2)) {
            mView.showPassword2Error(R.string.password_not_the_same);
            return;
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("old_password", passwordOld);
        parameters.put("new_password", password1);

        API.request(API.USER_PASSWORD_PUT, API.PUT, this, mView, parameters, true);
    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(mView, mView.getString(R.string.password_edit_succesvol), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(mView, MainActivity.class);
        i.putExtra("logged_in", true);
        mView.startActivity(i);
    }

    @Override
    public void onErrorResponse(VolleyError errorResponse) {
        // Empty if you want the default implementation, see the API class
    }
}
