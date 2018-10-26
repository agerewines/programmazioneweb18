package it.unitn.shoppinglesto.servlet.lists;

import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ShareListServlet")
public class ShareListServlet extends HttpServlet {

    private final String TEMPLISTCOOKIENAME = "templist_shoppingLesto_token";
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
            shoppingListDAO = daoFactory.getDAO(ShoppingListDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get shopping list dao from dao factory!", ex);
        }
        try {
            listCategoryDAO = daoFactory.getDAO(ListCategoryDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get list category dao from dao factory!", ex);
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

        Integer listId = null;
        try {
            listId = Integer.valueOf(request.getParameter("listId"));
        } catch (RuntimeException e) {
            response.sendError(500, e.getMessage());
        }

        boolean edit = false, add= false, share = false;
        if(request.getParameterMap().containsKey("edit"))
            edit = request.getParameter("edit").equals("edit");
        if(request.getParameterMap().containsKey("add"))
            add = request.getParameter("add").equals("add");
        if(request.getParameterMap().containsKey("share"))
            share = request.getParameter("share").equals("share");


        Integer userIdToShare = Integer.parseInt(request.getParameter("user"));
        try {
            ShoppingList list = shoppingListDAO.getByPrimaryKey(listId);

            list.setPermissions(true);
            User userToShare = userDAO.getById(userIdToShare);

            if (list.isShare()) {
                shoppingListDAO.shareListTo(list, userToShare, edit, add, share);
                message = "List successfully shared with " + userToShare.getFullName();
            } else {
                hasError = true;
                message = "You need permissions to share this list";
            }
        } catch (DAOException e) {
            response.sendError(500, e.getMessage());
        }

        if (hasError) {
            session.setAttribute("errorMessage", message);
            session.setAttribute("action", "newList");
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        } else {
            session.setAttribute("successMessage", message);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/list?id=" + listId));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
