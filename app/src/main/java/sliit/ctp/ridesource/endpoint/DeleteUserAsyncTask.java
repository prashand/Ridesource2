package sliit.ctp.ridesource.endpoint;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import sliit.ctp.ridesource.utility.RsApiBuilder;
import sliit.ctp.rsserver.rsApi.RsApi;
import sliit.ctp.rsserver.rsApi.model.User;

/**
 * Created by Prashan on 31-Oct-15.
 */
public class DeleteUserAsyncTask extends AsyncTask<String,Void,Void>{
    private static RsApi rsApiService = null;

    @Override
    protected Void doInBackground(String... username) {
        User user = null;
        try {
            RsApiBuilder.build(false).deleteUser(username[0]).execute();
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return null;
    }
}

