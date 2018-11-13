package it.unitn.shoppinglesto.servlet.lists.category;

import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.Category;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.utils.UtilityHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "EditCategoryServlet")
@MultipartConfig
public class EditCategoryServlet extends HttpServlet {
    private UserDAO userDAO;
    private ShoppingListDAO shoppingListDAO;
    private ListCategoryDAO listCategoryDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory!");
        }
        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get user dao from dao factory!", ex);
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
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendError(500, "There was an error processing the request");
            return;
        }
        String message = null;
        boolean hasError = false;
        boolean modified = false;
        String avatarsFolder = getServletContext().getInitParameter("avatarsFolder");
        if (avatarsFolder == null) {
            response.sendError(500, "Avatars folder not configured");
            return;
        }

        String rootPath = System.getProperty("catalina.home");
        String nameList = request.getParameter("nameListCat");
        String descriptionList = request.getParameter("descriptionListCat");

        Category listCat = null;
        if ( nameList == null || descriptionList == null || nameList.equals("") || descriptionList.equals("")) {
            hasError = true;
            message = "All fields are mandatory and must be filled!";
        } else {
            listCat = new Category();
            listCat.setName(nameList);
            listCat.setDescription(descriptionList);
            // aggiungi  a ListCategory
            // prendi l'id, salvalo in listCat
            // aggiungi le foto se ci sono
        }

        if (hasError) {
            request.setAttribute("errorMessage", "errors");
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        } else {
            if (modified) {
                //listCat = listCategoryDAO.update(listCat);
                message = "List was successfully updated";
                session.setAttribute("successMessage", message);
            }
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("You can't access this page via get");
    }
}
