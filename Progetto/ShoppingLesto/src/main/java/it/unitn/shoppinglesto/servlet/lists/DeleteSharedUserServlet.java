package it.unitn.shoppinglesto.servlet.lists;

import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
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

@WebServlet(name = "DeleteSharedUserServlet")
public class DeleteSharedUserServlet extends HttpServlet {
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

        Integer userIdToShare = null;
        boolean isMe = false;
        try {
            userIdToShare = Integer.parseInt(request.getParameter("user"));
            isMe = userIdToShare.equals(user.getId());
        } catch (RuntimeException e) {
            response.sendError(500, e.getMessage());
        }

        try{
            if(listId != null && userIdToShare != null){
                shoppingListDAO.removePermit(listId, userIdToShare);
            }else{
                hasError = true;
                message = "List or user id are nulls";
            }

        }catch (DAOException e){
            response.sendError(500, e.getMessage());
        }

        if (hasError) {
            session.setAttribute("errorMessage", message);
            session.setAttribute("action", "newList");
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        } else {
            session.setAttribute("successMessage", "User deleted");
            String path = isMe ? "/home" : ("/list?id=" + listId);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + path));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
