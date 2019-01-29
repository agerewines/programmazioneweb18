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

    @Override
    public void init() throws ServletException {
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        boolean anon = false;
        if (request.getParameterMap().containsKey("anonymous")) {
            anon = true;
        }
        if (user == null && anon) {
            try {
                String listId = UtilityHelper.getCookieValue(request, TEMPLISTCOOKIENAME);
                // se listId é giá presente allora togli il pulsante addlist
                if (listId != null) {
                    request.setAttribute("listId", listId);
                    List<ShoppingList> userLists = new ArrayList<>();
                    userLists.add(shoppingListDAO.getByPrimaryKey(Integer.parseInt(listId)));
                    request.setAttribute("userLists", userLists);
                }
                request.setAttribute("anon", true);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        } else if(user != null){
            try {
                List<ShoppingList> lists = shoppingListDAO.getUserLists(user);
                session.setAttribute("userLists", lists);
            } catch (DAOException ex) {
                response.sendError(500, ex.getMessage());
            }

        }
        if (!response.isCommitted()) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/geoIP.jsp");
            rd.forward(request, response);
        }
    }
}
