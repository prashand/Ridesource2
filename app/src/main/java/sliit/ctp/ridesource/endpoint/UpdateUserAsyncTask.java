package sliit.ctp.ridesource.endpoint;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import sliit.ctp.ridesource.utility.RsApiBuilder;
import sliit.ctp.rsserver.rsApi.model.User;

/**
 * Created by Prashan on 31-Oct-15.
 */
public class UpdateUserAsyncTask extends AsyncTask<User,Void,User>{

    @Override
    protected User doInBackground(User... users) {
        User user = null;
        try {
            RsApiBuilder.build(false).updateUser(users[0]).execute();
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return null;
    }
}
