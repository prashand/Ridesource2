package sliit.ctp.ridesource.endpoint;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import sliit.ctp.ridesource.utility.RsApiBuilder;
import sliit.ctp.rsserver.rsApi.RsApi;
import sliit.ctp.rsserver.rsApi.model.User;

/**
 * Created by Prashan on 10-Oct-15.
 */
public class GetUserAsyncTask extends AsyncTask<String,Void,User> {
    private static RsApi rsApiService = null;

    @Override
    protected User doInBackground(String... username) {
        User user=null;
        try {
            user= RsApiBuilder.build(false).getUser(username[0]).execute();
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return user;
    }
}

