package it.unitn.shoppinglesto.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import it.unitn.shoppinglesto.geolocation.GeoIP;
import it.unitn.shoppinglesto.geolocation.RawDBDemoGeoIPLocationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;

@WebServlet(name = "GeoIPServlet")
public class GeoIPServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String ip = request.getParameter("ipAddress");
        String ip = request.getRemoteAddr();
        System.out.println(ip);

        if (ip != null) {

            RawDBDemoGeoIPLocationService rdb = new RawDBDemoGeoIPLocationService();
            try {
                JsonNode myCity = rdb.getLocation(ip);

                JsonNode location = myCity.get("location");
                Float latitude = Float.valueOf(location.get("latitude").toString());
                Float longitude = Float.valueOf(location.get("longitude").toString());

                Float latMax = latitude + 0.01f;
                Float lonMax = longitude + 0.01f;
                Float latMin = latitude - 0.01f;
                Float lonMin = longitude - 0.01f;

                /*
                request.getSession().setAttribute("latMax", latMax);
                request.getSession().setAttribute("lonMax", lonMax);
                request.getSession().setAttribute("latMin", latMin);
                request.getSession().setAttribute("lonMin", lonMin);
                */

                String mapUrl = "https://www.openstreetmap.org/export/embed.html?bbox=" + lonMin + "%2C" + latMin + "%2C" + lonMax + "%2C" + latMax + "&amp;layer=hot";

                request.getSession().setAttribute("map", mapUrl);



            } catch (GeoIp2Exception e) {
                e.printStackTrace();
            }

            request.getRequestDispatcher("/WEB-INF/views/geoIP.jsp").forward(request, response);
        }
        else {
            request.setAttribute("error", "Ip Address not valid");
            request.getRequestDispatcher("/WEB-INF/views/geoIP.jsp").forward(request, response);
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/geoIP.jsp");
        rd.forward(request, response);
    }
/*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/geoIP.jsp");
        rd.forward(request, response);

    }*/
}
