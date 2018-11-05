package it.unitn.shoppinglesto.servlet.lists;

import it.unitn.shoppinglesto.db.daos.*;
import it.unitn.shoppinglesto.db.entities.Product;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddProductToListServlet")
public class AddProductToListServlet extends HttpServlet {
    private UserDAO userDAO;
    private ShoppingListDAO shoppingListDAO;
    private ListCategoryDAO listCategoryDAO;
    private ProductDAO productDAO;

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
        } try {
            productDAO = daoFactory.getDAO(ProductDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get product dao from dao factory!", ex);
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
        Integer listId = null, prodId = null;
        try{
            listId = Integer.parseInt(request.getParameter("listId"));
            prodId = Integer.parseInt(request.getParameter("prodId"));
        }catch (RuntimeException ex) {
            response.sendError(500, "Error retrieving prod and list id");
        }
        // lo aggiungo alla lista
        if(listId != null && prodId != null){
            try{
                shoppingListDAO.addProductToList(listId, prodId);
            }catch (DAOException e){
                response.sendError(500, e.getMessage());
            }
            message = "Product successfully added into list";
            session.setAttribute("successMessage", message);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/list?id=" + listId));
        }
        else{
            message = "Error getting product and list id";
            session.setAttribute("errorMessage", message);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/list?id=" + listId));
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        Integer listId = Integer.parseInt(request.getParameter("listId"));
        if (listId != 0) {
            ShoppingList list = null;
            List<Product> availableProducts = null;
            try {
                list = shoppingListDAO.getByPrimaryKey(listId);
                availableProducts = productDAO.getAvailableProduct(list);
                session.setAttribute("products", availableProducts);
            }catch (DAOException e) {
                e.printStackTrace();
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/addProduct.jsp");
            rd.forward(request, response);
        }
    }
}
