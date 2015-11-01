package sliit.ctp.rsserver;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import static sliit.ctp.rsserver.OfyService.ofy;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "rsApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "rsserver.ctp.sliit",
                ownerName = "rsserver.ctp.sliit",
                packagePath = ""
        )
)
public class UserEndpoint {

    private static final Logger logger = Logger.getLogger(UserEndpoint.class.getName());

    /**
     * This method gets the <code>User</code> object associated with the specified <code>id</code>.
     *
     * @param username The username of the object to be returned.
     * @return The <code>User</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getUser")
    public User getUser(@Named("username") String username) {
        return findRecord(username);
    }

    /**
     * This inserts a new <code>User</code> object.
     *
     * @param user The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertUser")
    public void insertUser(User user) throws ConflictException {
        if(user.getUsername() != null && findRecord(user.getUsername())!=null){
            throw new ConflictException("User already exists");
        }
        ofy().save().entity(user).now();
    }

    @ApiMethod(name = "updateUser")
    public void updateUser(User newUser) throws NotFoundException {
        User oldUser = findRecord(newUser.getUsername());
        if(oldUser==null){
            throw new NotFoundException("User Record does not exist");
        }
        ofy().delete().type(User.class).id(oldUser.getUsername()).now();
        ofy().save().entity(newUser).now();
    }


    @ApiMethod(name = "deleteUser")
    public void deleteUser(@Named("username") String username) throws NotFoundException {
        User oldUser = findRecord(username);
        if(oldUser==null){
            throw new NotFoundException("User Record does not exist");
        }
        ofy().delete().type(User.class).id(username).now();
    }

    @ApiMethod(name = "getDistanceTime")
    public DistanceTime getDistanceTime(@Named ("orilong") double orilong,@Named ("orilat") double orilat,@Named ("deslong") double deslong,@Named ("deslat") double deslat) {
        return DistanceTimeManager.getDistanceTime(orilong,orilat,deslong,deslat);
    }

    @ApiMethod(name="t2")
    public User t2(User user){
        Iterator<User> iterator = listUser(null, null).getItems().iterator();
        User partner;
        User closestpartner = iterator.next();
        double distance;

//        distance = DistanceTimeManager.getDistanceTime(
//                user.getLongitude(), user.getLatitude(),
//                closestpartner.getLongitude(), closestpartner.getLatitude())
//                .getDistance();

        return closestpartner;
    }

    @ApiMethod(name="getPartner")
    public User findPartner(User user) {
        Iterator<User> iterator =listUser(null, null).getItems().iterator();
        User partner = null;
        User closestpartner = iterator.next();

        if(closestpartner.getUsername().equals(user.getUsername())){
            closestpartner= iterator.next();
        }

        double distance = DistanceTimeManager.getDistanceTime(
                    user.getLatitude(),user.getLongitude(),
                    closestpartner.getLatitude(),closestpartner.getLongitude())
                    .getDistance();

            while (iterator.hasNext()) {

                partner = iterator.next();

                if (!partner.isDriver()) {
                    continue;
                }

                try {
                  if (DistanceTimeManager.getDistanceTime(
                          user.getLongitude(), user.getLatitude(),
                          partner.getLongitude(), partner.getLatitude())
                          .getDistance() < distance) {
                      closestpartner = partner;
                  }
                } catch(Exception e){
                  return partner;
                }
            }
            return closestpartner;
    }

    @ApiMethod(name = "listUser")
    public CollectionResponse<User> listUser(@Nullable @Named("cursor") String cursorString,
                                               @Nullable @Named("count") Integer count) {

        Query<User> query = ofy().load().type(User.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<User> records = new ArrayList<User>();
        QueryResultIterator<User> iterator = query.iterator();
        int num = 0;
        while (iterator.hasNext()) {
            records.add(iterator.next());
            if (count != null) {
                num++;
                if (num == count) break;
            }
        }

        if (cursorString != null && cursorString != "") {
            Cursor cursor = iterator.getCursor();
            if (cursor != null) {
                cursorString = cursor.toWebSafeString();
            }
        }
        return CollectionResponse.<User>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    //Private method to retrieve a <code>User</code> record
    private User findRecord(String username) {
        return ofy().load().type(User.class).id(username).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }

}