package sliit.ctp.ridesource.utility;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import sliit.ctp.rsserver.rsApi.RsApi;

/**
 * Created by Prashan on 30-Oct-15.
 */
public class RsApiBuilder {
    private static RsApi singleInstance = null;
    private static String URL = "https://vocal-ceiling-107606.appspot.com/_ah/api/";

    private RsApiBuilder(){}

    /**
     * Gets singleton instance
     * @param disableGZip true when testing against the local server
     * @return
     */
    public static RsApi build(boolean disableGZip) {
        if (singleInstance == null) {
            RsApi.Builder builder = new RsApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(URL);
            if (disableGZip) {
                builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
            }
            singleInstance = builder.build();
        }
        return singleInstance;
    }
}
