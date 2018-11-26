package it.unitn.shoppinglesto.servlet.chat;

import it.unitn.shoppinglesto.db.daos.MessageDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.Message;
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
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
    private UserDAO userDAO;
    private ShoppingListDAO shoppingListDAO;
    private MessageDAO messageDAO;

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
            throw new ServletException("Impossible to get shoppingList dao from dao factory!", ex);
        }
        try {
            messageDAO = daoFactory.getDAO(MessageDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get message dao from dao factory!", ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer listId = null;
        try {
            listId = Integer.valueOf(request.getParameter("listId"));
        } catch (RuntimeException ex) {
            response.sendError(500, ex.getMessage());
        }
        Integer userId = null;
        try {
            userId = Integer.valueOf(request.getParameter("userId"));
        } catch (RuntimeException ex) {
            response.sendError(500, ex.getMessage());
        }
        Timestamp lastMessageTime = null;
        try {
            lastMessageTime = request.getParameterMap().containsKey("lastMessageTime") ? Timestamp.valueOf(request.getParameter("lastMessageTime")) : null;
        } catch (RuntimeException ex) {
            response.sendError(500, ex.getMessage());
        }
        try {
            List<Message> messages;
            if(lastMessageTime != null){
                messages = messageDAO.getNewMessages(listId, lastMessageTime);
            }else{
                messages = messageDAO.getAllListMessages(listId);
            }
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-cache");
            for (Message m : messages) {
                assert userId != null;
                User user = userDAO.getById(m.getUserId());
                if (userId.equals(m.getUserId())) {
                    response.getWriter().write("<li class=\"media text-right mb-3\">\n" +
                            "                                    <div class=\"media-body text-right\">\n" +
                            "                                       <p class=\"lead\">" + m.getText() + "</p>" +
                            "                                       <h6>" + user.getFullName() +
                            "                                           <small class=\"text-muted\">" + m.getCreatedAt() + "</small>\n" +
                            "                                       </h6>" +
                            "                                    </div>\n" +
                            "<img class=\"img-circle rounded ml-3\" height=\"50\" width=\"50\"\n" +
                            "   src='/ShoppingLesto/images?id=" + m.getUserId() + "&resource=user'\n" +
                            "   onerror=\"this.onerror=null;this.src='/ShoppingLesto/images/avatars/Users/default.jpeg';\"/>" +
                            "                                </li>");
                } else {
                    response.getWriter().write("<li class=\"media mb-3\">\n" +
                            "<img class=\"img-circle rounded mr-3\" height=\"50\" width=\"50\"\n" +
                            "   src='/ShoppingLesto/images?id=" + m.getUserId() + "&resource=user'\n" +
                            "   onerror=\"this.onerror=null;this.src='/ShoppingLesto/images/avatars/Users/default.jpeg';\"/>" +
                            "                                    <div class=\"media-body\">\n" +
                            "                                       <p class=\"lead\">" + m.getText() + "</p>" +
                            "                                       <h6>" + user.getFullName() +
                            "                                           <small class=\"text-muted\">" + m.getCreatedAt() + "</small>\n" +
                            "                                       </h6>" +
                            "                                    </div>\n" +
                            "                                </li>");
                }
                response.getWriter().write("<hr/>");
            }
        } catch (DAOException e) {
            response.sendError(500, e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

