package it.unitn.shoppinglesto.servlet.notification;

import it.unitn.shoppinglesto.db.daos.ProductDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.SuggestionDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.*;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SuggestionServlet")
public class SuggestionServlet extends HttpServlet {
    private ShoppingListDAO shoppingListDAO;
    private SuggestionDAO suggestionDAO;
    private ProductDAO productDAO;


    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory!");
        }
        try {
            shoppingListDAO = daoFactory.getDAO(ShoppingListDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get user dao from dao factory!", ex);
        }
        try {
            suggestionDAO = daoFactory.getDAO(SuggestionDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get user dao from dao factory!", ex);
        }
        try {
            productDAO = daoFactory.getDAO(ProductDAO.class);
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
        boolean hasNews = false;
        List<NotificationExpire> allNews = new ArrayList<>();
        try {
            List<ShoppingList> userList = shoppingListDAO.getUserLists(user);
            List<Suggestion> suggestions = new ArrayList<>();
            for (ShoppingList list: userList) {
                if(suggestionDAO.hasSuggestions(list.getId()))
                    suggestions.addAll(suggestionDAO.getAllListSuggestion(list.getId()));
            }
            if(!suggestions.isEmpty()){
                for (Suggestion s : suggestions) {
                    if(!s.isSeen()){
                        int counter = s.getCounter();
                        if( counter>= 3 ){
                            LocalTime last = s.getLast().toLocalTime();
                            LocalTime expire = last.plusSeconds(s.getAverage());
                            System.out.println(expire.toString() + ", " + LocalTime.now());
                            if(expire.isBefore(LocalTime.now())){
                                NotificationExpire newNews = new NotificationExpire();
                                newNews.setList(shoppingListDAO.getByPrimaryKey(s.getIdList()));
                                newNews.setProduct(productDAO.getByPrimaryKey(s.getIdProd()));
                                newNews.setUser(user);
                                newNews.setSuggestion(s);
                                allNews.add(newNews);
                                hasNews = true;
                            }
                        }
                    }
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        // check if a product is expired
        if(hasNews) {
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-cache");
            for (NotificationExpire news: allNews) {
                response.getWriter().write("<li class=\"list-group-item text-center notify\" onclick=\"expireNotificationDismiss(this)\" data-suggestion-id=\"" + news.getSuggestion().getId() + " data-list-id=\"" + news.getSuggestion().getIdList() + "\"> " +
                        "   <strong>" + news.getProduct().getName() + "</strong> in <strong>" + news.getList().getName() + "</strong> is about to expire" +
                        "</li>");
            }
        }

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("Cannot enter this page");

    }
}
