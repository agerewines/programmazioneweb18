package it.unitn.shoppinglesto.servlet.geoloc;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ProductDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.entities.Category;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.geolocation.RawDBDemoGeoIPLocationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GeolocationServlet")
public class GeolocationServlet extends HttpServlet {
    private ShoppingListDAO shoppingListDAO;
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
        try {
            shoppingListDAO = daoFactory.getDAO(ShoppingListDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get shopping list dao from dao factory!", ex);
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ip = request.getRemoteAddr();

        ip = "193.205.210.82";

        if (ip != null) {
            ServletContext context = this.getServletContext();
            RawDBDemoGeoIPLocationService rdb = new RawDBDemoGeoIPLocationService(context);
            try {
                JsonNode myCity = rdb.getLocation(ip);

                JsonNode location = myCity.get("location");
                Float latitude = Float.valueOf(location.get("latitude").toString());
                Float longitude = Float.valueOf(location.get("longitude").toString());

                // estrapolare nome categoria

                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                Integer catId = null;
                try {
                    catId = Integer.valueOf(request.getParameter("catId"));
                } catch (RuntimeException ex) {
                    response.sendError(500, ex.getMessage());
                }
                Category category = listCategoryDAO.getByPrimaryKey(catId);
                String mapUrl = "https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d22154.981200143142!2d" + longitude + "!3d" + latitude + "!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1s" + category.getName() + "!5e0!3m2!1sit!2sit!4v1542911270560";
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(mapUrl);
            } catch (GeoIp2Exception | DAOException e) {
                request.setAttribute("error", "Your IP isn't in our database");
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String dispatchPath = null;
        try {
            List<ShoppingList> lists = shoppingListDAO.getUserLists(user);
            for (ShoppingList list :lists) {
                list.setCategory(listCategoryDAO.getByPrimaryKey(list.getCategoryId()));
            }
            session.setAttribute("userLists", lists);
        } catch (DAOException ex) {
            response.sendError(500, ex.getMessage());
        }

        dispatchPath = "/WEB-INF/views/geoIP.jsp";

        if (!response.isCommitted()) {
            RequestDispatcher rd = request.getRequestDispatcher(dispatchPath);
            rd.forward(request, response);
        }

    }
}
