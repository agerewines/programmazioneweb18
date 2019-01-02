package it.unitn.shoppinglesto.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ProductDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.jdbc.JDBCListCategoryDAO;
import it.unitn.shoppinglesto.db.entities.Category;
import it.unitn.shoppinglesto.db.entities.Product;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.geolocation.RawDBDemoGeoIPLocationService;
import it.unitn.shoppinglesto.utils.UtilityHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samuelecastellan
 */


@WebServlet(name = "GeoIPServlet")
public class GeoIPServlet extends HttpServlet {

    private ListCategoryDAO listCategoryDAO;

    private final String TEMPLISTCOOKIENAME = "templist_shoppingLesto_token";
    private ShoppingListDAO shoppingListDAO;
    private ProductDAO productDAO;

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
        try {
            shoppingListDAO = daoFactory.getDAO(ShoppingListDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get shopping list dao from dao factory!", ex);
        }
        try {
            productDAO = daoFactory.getDAO(ProductDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get product dao from dao factory!", ex);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int listId = -23;
        String ip = request.getRemoteAddr();

        //ip = "193.205.210.82";

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

                try {
                    listId = Integer.valueOf(request.getParameter("id"));
                } catch (Exception e) {}

                if (listId != -23) {
                    Category category = listCategoryDAO.getByPrimaryKey(listId);
                    categoryName = category.getName();
                } else {
                    try {
                        categoryName = listCategoryDAO.getCategoryNameByUser(user);
                    } catch (DAOException e) {
                        throw new DAOException("User is null");
                    }
                }

                if (categoryName == null)
                    categoryName = "alimentari";

                String mapUrl = "https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d22154.981200143142!2d" + longitude + "!3d" + latitude + "!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1s" + categoryName + "!5e0!3m2!1sit!2sit!4v1542911270560";

                request.getSession().setAttribute("map", mapUrl);

                boolean anon = false;
                if (request.getParameterMap().containsKey("anonymous")) {
                    anon = true;
                }
                String dispatchPath = null;
                if (user == null && anon) {
                    response.sendError(500, "Anonimous user can't access this page");
                } else if(user != null){
                    try {
                        List<ShoppingList> lists = shoppingListDAO.getUserLists(user);
                        session.setAttribute("userLists", lists);
                    } catch (DAOException ex) {
                        response.sendError(500, ex.getMessage());
                    }
                }

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
