package sliit.ctp.rsserver;


import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
// Note that any JSON parser can be used; this one is used for illustrative purposes.
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Lumesh on 10/31/2015.
 */


public class DistanceTimeManager {

    private DistanceTimeManager() {  }

    public static DistanceTime getDistanceTime(double orilong,double orilat,double deslong,double deslat){
        URL url = null;
        HttpURLConnection con;
        String result=null;

        DistanceTime dt = new DistanceTime();

        try {
            url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin="+orilong+","+orilat+"&destination="+deslong+","+deslat+"&avoid=highways&mode=bus&key=AIzaSyAFCtu34hQ_lPiy1p5VpoAKi3XE8ZgcSck");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            con = (HttpURLConnection)url.openConnection();
            InputStream ins = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(ins);
            BufferedReader in = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line=null;
            while ((line = in.readLine())!=null)
            {
                sb.append(line + "\n");
            }
            result=sb.toString();
            isr.close();
            in.close();
            JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);

            JSONArray newTempARr = routes.getJSONArray("legs");
            JSONObject newDisTimeOb = newTempARr.getJSONObject(0);

            JSONObject distOb = newDisTimeOb.getJSONObject("distance");
            JSONObject timeOb = newDisTimeOb.getJSONObject("duration");
            dt.setDistance(distOb.getInt("value"));
            dt.setTime(timeOb.getString("text"));
            return dt;
        } catch (IOException e) {

        }
        return dt;
    }
}
