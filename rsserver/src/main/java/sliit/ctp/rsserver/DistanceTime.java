package sliit.ctp.rsserver;

/**
 * Created by Lumesh on 10/31/2015.
 */
public class DistanceTime {

    private double distance;
    private String time;

    public DistanceTime() {
        distance=0;
        time="";
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
