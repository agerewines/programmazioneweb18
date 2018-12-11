package it.unitn.shoppinglesto.servlet.notification;

import it.unitn.shoppinglesto.db.daos.SuggestionDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.Suggestion;
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

@WebServlet(name = "NotificationExpireDismissServlet")
public class NotificationExpireDismissServlet extends HttpServlet {
    private SuggestionDAO suggestionDAO;

    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory!");
        }
        try {
            suggestionDAO = daoFactory.getDAO(SuggestionDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get user dao from dao factory!", ex);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendError(500, "There was an error processing the request");
            return;
        }

        Integer suggetionId = null;
        try{
            suggetionId = Integer.parseInt(request.getParameter("suggestionId"));
        } catch (RuntimeException ex) {
            response.sendError(500, ex.getMessage());
        }

        try {
            Suggestion s = suggestionDAO.getByPrimaryKey(suggetionId);
            s.setSeen(true);
            s = suggestionDAO.update(s);
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("Cannot enter this page");

    }
}
