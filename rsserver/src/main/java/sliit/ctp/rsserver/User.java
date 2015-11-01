package sliit.ctp.rsserver;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class User {
    @Id
    private String username;
    private String password;
    private String name;
    private String creditCardNo;

    private double latitude;
    private double longitude;

    private boolean isDriver;

    private String partner;

    public User(){ }

    public String getUsername() { return username; }

    public User setUsername(String username) { this.username = username; return this;}

    public String getPassword() { return password; }

    public User setPassword(String password) { this.password = password; return this;}

    public String getName() { return name; }

    public User setName(String name) { this.name = name; return this;}

    public String getCreditCardNo() { return creditCardNo; }

    public User setCreditCardNo(String creditCardNo) { this.creditCardNo = creditCardNo; return this;}

    public double getLatitude() { return latitude; }

    public User setLatitude(double latitude) { this.latitude = latitude; return this; }

    public double getLongitude() { return longitude; }

    public User setLongitude(double longitude) { this.longitude = longitude; return this; }

    public boolean isDriver() { return isDriver; }

    public void setIsDriver(boolean isDriver) { this.isDriver = isDriver; }

    public String getPartner() { return partner; }

    public void setPartner(String partner) { this.partner = partner; }
}