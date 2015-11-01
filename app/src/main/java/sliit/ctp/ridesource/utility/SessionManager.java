package sliit.ctp.ridesource.utility;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;

import sliit.ctp.rsserver.rsApi.model.User;

/**
 * Created by Prashan on 28-Oct-15.
 */
public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    private static User user;

    private static SessionManager singleInstance;

    int PRIVATE_MODE = 0;                                       // Shared pref mode
    private static final String PREF_NAME = "RidesourcePref";   // Sharedpref file name
    private static final String IS_LOGIN = "IsLoggedIn";        // All Shared Preferences Keys
    public static final String KEY_USERNAME = "name";               // User name (make variable public to access from outside)

    public static SessionManager createManager(Context context){
        if(singleInstance==null){
            singleInstance = new SessionManager(context);
        }
        return singleInstance;
    }

    private SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(User user) {
        editor.putBoolean(IS_LOGIN, true);  // Storing login value as TRUE
        editor.putString(KEY_USERNAME, user.getUsername());   // Storing name in pref
        editor.commit();                    // commit changes
        this.user=user;
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));         // user name
        return user;                                                // return user
    }

    public User getUserObject(){
        return user;
    }

    public void logoutUser(){
        editor.clear();    // Clearing all data from Shared Preferences
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}