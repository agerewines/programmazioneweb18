package it.unitn.shoppinglesto.geolocation;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;


public class RawDBDemoGeoIPLocationService {
    private Reader dbReader;

    public RawDBDemoGeoIPLocationService(ServletContext context) throws IOException {

        String dbLoc = context.getRealPath("/WEB-INF/GeoLite2-City.mmdb");
        //URL database = getClass().getResource(dbLoc);

        //InputStream database = getClass().getResourceAsStream("/WEB-INF/GeoLite2-City.mmdb");
        File database = new File(dbLoc);
        dbReader = new Reader(database);

        //dbReader = new DatabaseReader.Builder(database).build();

    }

    public JsonNode getLocation(String ip) throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);

        JsonNode r = dbReader.get(ipAddress);

        return r;

        /*CityResponse response = dbReader.city(ipAddress);

        String cityName = response.getCity().getName();
        String latitude = response.getLocation().getLatitude().toString();
        String longitude = response.getLocation().getLongitude().toString();
        return new GeoIP(ipAddress.toString(), cityName, latitude, longitude);*/
    }
}
