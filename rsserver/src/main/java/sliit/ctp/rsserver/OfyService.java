package sliit.ctp.rsserver;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Created by Prashan on 10-Oct-15.
 */
public class OfyService {
    static{
        ObjectifyService.register(User.class);
    }

    public static Objectify ofy(){ return ObjectifyService.ofy(); }

    public static ObjectifyFactory factory(){
        return ObjectifyService.factory();
    }
}
