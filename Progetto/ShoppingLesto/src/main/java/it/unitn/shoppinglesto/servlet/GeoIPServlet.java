package it.unitn.shoppinglesto.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ProductDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.daos.jdbc.JDBCListCategoryDAO;
import it.unitn.shoppinglesto.db.entities.Product;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.geolocation.GeoIP;
import it.unitn.shoppinglesto.geolocation.RawDBDemoGeoIPLocationService;
import it.unitn.shoppinglesto.servlet.lists.ShowListServlet;

import javax.print.DocFlavor;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samuelecastellan
 */


@WebServlet(name = "GeoIPServlet")
public class GeoIPServlet extends HttpServlet {

    private ListCategoryDAO listCategoryDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory!");
        }
        try {
            listCategoryDAO = daoFactory.getDAO(ListCategoryDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get list category dao from dao factory!", ex);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String ip = request.getParameter("ipAddress"); //da form nella pagina /geoip

        String ip = request.getRemoteAddr();

        //ip = "193.205.210.82";

        System.out.println(ip);

        if (ip != null) {
            ServletContext context = this.getServletContext();
            RawDBDemoGeoIPLocationService rdb = new RawDBDemoGeoIPLocationService(context);
            try {
                JsonNode myCity = rdb.getLocation(ip);

                JsonNode location = myCity.get("location");
                Float latitude = Float.valueOf(location.get("latitude").toString());
                Float longitude = Float.valueOf(location.get("longitude").toString());

                // estrapolare nome categoria

                String categoryName = null;

                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");

                // JDBC get list category
                try {
                    categoryName = listCategoryDAO.getCategoryNameByUser(user);
                } catch (DAOException e) {
                    throw new DAOException("User is null");
                }

                if (categoryName == null)
                    categoryName = "general";

                String mapUrl = "https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d22154.981200143142!2d" + longitude + "!3d" + latitude + "!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1s" + categoryName + "!5e0!3m2!1sit!2sit!4v1542911270560";

                request.getSession().setAttribute("map", mapUrl);


            } catch (GeoIp2Exception | DAOException e) {
                request.setAttribute("error", "Your IP isn't in our database");
            }

            request.getRequestDispatcher("/WEB-INF/views/geoIP.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Ip Address not valid");
            request.getRequestDispatcher("/WEB-INF/views/geoIP.jsp").forward(request, response);
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/geoIP.jsp");
        rd.forward(request, response);
    }
}
