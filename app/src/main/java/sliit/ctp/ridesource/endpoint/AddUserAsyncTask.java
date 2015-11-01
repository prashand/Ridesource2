package sliit.ctp.ridesource.endpoint;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import sliit.ctp.rsserver.rsApi.model.User;
import sliit.ctp.rsserver.rsApi.RsApi;

/**
 * Created by Prashan on 10-Oct-15.
 */
public class AddUserAsyncTask extends AsyncTask<User,Void,Void>{
    private static RsApi rsApiService = null;
    private Context context;

//    public AddUserAsyncTask(Context context) {
//        this.context = context;
//    }

    @Override
    protected Void doInBackground(User... users) {
        if(rsApiService==null){
            RsApi.Builder builder = new RsApi.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(),null)
            .setRootUrl("https://vocal-ceiling-107606.appspot.com/_ah/api/");
//            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                @Override
//                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                    abstractGoogleClientRequest.setDisableGZipContent(true);
//                }
//            });
            rsApiService = builder.build();
        }
        try {
            rsApiService.insertUser(users[0]).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
