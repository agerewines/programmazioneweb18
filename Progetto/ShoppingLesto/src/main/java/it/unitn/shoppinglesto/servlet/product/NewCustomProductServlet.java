package it.unitn.shoppinglesto.servlet.product;

import it.unitn.shoppinglesto.db.daos.ProdCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ProductDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.entities.Photo;
import it.unitn.shoppinglesto.db.entities.Product;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.servlet.admin.ShowProductCategoryServlet;
import it.unitn.shoppinglesto.utils.UtilityHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "NewCustomProductServlet")
@MultipartConfig
public class NewCustomProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    private ProdCategoryDAO prodCategoryDAO;
    private ShoppingListDAO shoppingListDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory!");
        }
        try {
            productDAO = daoFactory.getDAO(ProductDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get product dao from dao factory!", ex);
        }
        try {
            prodCategoryDAO = daoFactory.getDAO(ProdCategoryDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get product dao from dao factory!", ex);
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
        String name = request.getParameter("nameProd");
        String description = request.getParameter("descriptionProd");
        int categoryId = Integer.parseInt(request.getParameter("category"));
        int listId = Integer.parseInt(request.getParameter("listId"));
        Double price = Double.parseDouble(request.getParameter("price"));
        if (name == null || description == null || name.equals("") || description.equals("")) {
            hasError = true;
            message = "All fields are mandatory and must be filled!";
        } else {
            if (description.length() > 255) {
                hasError = true;
                message = "Product description can contain a maximum of  255 characters.";
            } else {
                // int id, String name, String description, String image, int categoryId, int userId, User user
                Product product = new Product();
                product.setCustom(true);
                product.setName(name);
                product.setDescription(description);
                product.setCategoryId(categoryId);
                product.setPrice(price);
                try {
                    product.setCategory(productDAO.getCategory(categoryId));
                    product.setId(productDAO.save(product));
                } catch (DAOException e) {
                    response.sendError(500, e.getMessage());
                }
                Part filePart = request.getPart("photo");
                if ((filePart != null) && (filePart.getSize() > 0)) {
                    Photo prodPhoto = new Photo();
                    prodPhoto.setItemId(product.getId());
                    String fileName = UtilityHelper.getFilename(filePart);
                    fileName = UtilityHelper.renameImage(fileName, "Product_" + product.getId() + "_" + (new Date().toString()).replace(":", "_").replace(" ", "_"));
                    String listCategoryUploadDir = rootPath + File.separator + avatarsFolder + "Product";
                    try {
                        prodPhoto.setPath(UtilityHelper.uploadFileToDirectory(listCategoryUploadDir, fileName, filePart));
                        product.addPhoto(prodPhoto);
                        productDAO.addPhoto(prodPhoto);
                    } catch (DAOException | IOException ex) {
                        response.sendError(500, ex.getMessage());
                    }
                }
                try {
                    shoppingListDAO.addProductToList(listId, product.getId());
                } catch (DAOException e) {
                    response.sendError(500, e.getMessage());
                }
            }

            if (hasError) {
                session.setAttribute("errorMessage", message);
                response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/product/new"));
            } else {
                message = "Product custom was successfully added";
                session.setAttribute("successMessage", message);
                response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/list?id=" + listId));
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("Cannot enter this page");
    }
}
