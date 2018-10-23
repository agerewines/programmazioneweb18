package it.unitn.shoppinglesto.servlet;

import it.unitn.shoppinglesto.db.daos.ProductDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.Product;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "ImageServlet")
public class ImageServlet extends HttpServlet {

    private UserDAO userDAO;
    private ShoppingListDAO shoppingListDAO;
    //private ListCategoryDAO categoryDAO;
    //private ProductCategoryDAO prodCategoryDAO;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException{
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if(daoFactory == null){
            throw new ServletException("Impossible to get dao factory!");
        }
        try {
            userDAO= daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get user dao from dao factory!", ex);
        }
        try{
            shoppingListDAO = daoFactory.getDAO(ShoppingListDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get shopping list dao from dao factory!", ex);
        }
        /*try{
            categoryDAO = daoFactory.getDAO(ListCategoryDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get list category dao from dao factory", ex);
        }
        try{
            prodCategoryDAO = daoFactory.getDAO(ProductCategoryDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get product category dao from dao factory", ex);
        }*/
        try{
            productDAO = daoFactory.getDAO(ProductDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get product category dao from dao factory", ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        {
            response.setContentType("image/jpeg");
            String avatarsFolder = getServletContext().getInitParameter("avatarsFolder");
            if (avatarsFolder == null) {
                response.sendError(500, "Avatars folder not configured");
                return;
            }

            String resource = request.getParameter("resource");
            Integer id = null;
            try {
                id = Integer.valueOf(request.getParameter("id"));
            } catch (NumberFormatException ex) {
                response.sendError(404);
            }

            String contentIndexStr = request.getParameter("contentIndex");
            Integer contentIndex = null;
            if (contentIndexStr != null && !contentIndexStr.equals("")) {
                try {
                    contentIndex = Integer.valueOf(contentIndexStr);
                } catch (NumberFormatException ex) {
                    response.sendError(404);
                }
            }

            String path = null;
            boolean validResource = true;
            Product product;

            if (resource != null && id != null) {
                try {
                    switch (resource) {
                        case "user":
                            User user = userDAO.getByPrimaryKey(id);
                            path = user.getAvatar();
                            break;
                        case "products":
                            product = productDAO.getByPrimaryKey(id);
                            if (product != null) {
                                if (contentIndex == null)
                                    path = product.getLogo();
                                else
                                    path = product.getImagePaths().get(contentIndex);
                            }
                            break;
                        /*case "customproducts":
                            product = productDAO.getCustomByPrimaryKey(id);
                            if(product != null){
                                path = product.getLogoPath();
                            }
                            break;*/
                        case "shoppingLists":
                            ShoppingList list = shoppingListDAO.getByPrimaryKey(id);
                            if (list != null)
                                path = list.getImage();
                            break;
                        /*case "listCategories":
                            ListCategory category = categoryDAO.getByPrimaryKey(id);
                            if(category != null && !category.getImagePaths().isEmpty() && !(contentIndex >= category.getImagePaths().size()))
                                path = category.getImagePaths().get(contentIndex);
                            break;
                        case "productCategories":
                            ProductCategory prodCategory = prodCategoryDAO.getByPrimaryKey(id);
                            if(prodCategory != null){
                                path = prodCategory.getLogoPath();
                            }
                            break;*/
                        default:
                            validResource = false;
                            break;
                    }

                } catch (DAOException ex) {
                    response.sendError(500, ex.getMessage());
                }
            } else {
                response.sendError(404);
            }


            if (!validResource || path == null) {
                response.sendError(404);
            } else {
                InputStream inputStream = new FileInputStream(path);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                try (OutputStream outputStream = response.getOutputStream()) {
                    outputStream.write(bytes);
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
