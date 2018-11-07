package it.unitn.shoppinglesto.geolocation;


/**
 *
 * @author samuelecastellan
 */

public class GeoIP {
    private String ipAddress;
    private String city;
    private String latitude;
    private String longitude;

    public GeoIP() {
    }

    public GeoIP(String ip, String cityName, String latitude, String longitude) {
    }

    // setter
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    //getter
    public String getIpAddress() {
        return ipAddress;
    }

    public String getCity() {
        return city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
