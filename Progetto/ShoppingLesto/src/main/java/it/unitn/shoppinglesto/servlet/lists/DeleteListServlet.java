package it.unitn.shoppinglesto.servlet.lists;

import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.utils.CookieHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "DeleteListServlet")
public class DeleteListServlet extends HttpServlet {
    private final String TEMPLISTCOOKIENAME = "templist_shoppingLesto_token";
    private ShoppingListDAO shoppingListDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory!");
        }
        try {
            shoppingListDAO = daoFactory.getDAO(ShoppingListDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get shopping list dao from dao factory!", ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        boolean anon = false;
        if (request.getParameterMap().containsKey("anonymous")) {
            anon = true;
        }
        User user = (User) session.getAttribute("user");
        if (user == null && !anon) {
            response.sendError(500, "There was an error processing the request, user is null");
            return;
        }

        String message = null;
        boolean hasError = false;

        Integer listId = null;
        try {
            listId = Integer.valueOf(request.getParameter("listId"));
        } catch (RuntimeException ex) {
            response.sendError(500, "Error getting list id");
        }
        try{
            if(!shoppingListDAO.delete(listId).equals(1)){
                hasError = true;
            }
        }catch (DAOException e){
            response.sendError(500, e.getMessage());
        }
        if(anon){
            CookieHelper.removeGenericCookie(response, TEMPLISTCOOKIENAME);
        }

        if (hasError) {
            session.setAttribute("errorMessage", "Error deleting list " + listId);
            session.setAttribute("action", "deleteList");
            if(anon)
                response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/list?anonymous=true&?id=" + listId));

            else
                response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/list?id=" + listId));

        } else {
            if(!anon){
                message = "List was successfully deleted";
                session.setAttribute("successMessage", message);
            }
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
