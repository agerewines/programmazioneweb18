package it.unitn.shoppinglesto.servlet.product.category;

import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ProdCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.Category;
import it.unitn.shoppinglesto.db.entities.Photo;
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
import java.util.Date;

@WebServlet(name = "NewProductCategoryServlet")
@MultipartConfig
public class NewProductCategoryServlet extends HttpServlet {
    private ProdCategoryDAO prodCategoryDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory!");
        }
        try {
            prodCategoryDAO = daoFactory.getDAO(ProdCategoryDAO.class);
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
        String avatarsFolder = getServletContext().getInitParameter("avatarsFolder");
        if (avatarsFolder == null) {
            response.sendError(500, "Avatars folder not configured");
            return;
        }

        String rootPath = System.getProperty("catalina.home");
        String nameProdCat = request.getParameter("nameProdCat");
        String descriptionProdCat = request.getParameter("descriptionProdCat");

        Category prodCat = null;
        if (nameProdCat == null || descriptionProdCat == null || nameProdCat.equals("") || descriptionProdCat.equals("")) {
            hasError = true;
            message = "All fields are mandatory and must be filled!";
        } else {
            prodCat = new Category();
            prodCat.setName(nameProdCat);
            prodCat.setDescription(descriptionProdCat);
            try{
                // aggiungi  a ListCategory
                prodCat.setId(prodCategoryDAO.save(prodCat));
            } catch (DAOException ex) {
                response.sendError(500, ex.toString());
            }
            // aggiungi le foto se ci sono
            Part filePart = request.getPart("photo");
            if ((filePart != null) && (filePart.getSize() > 0)) {
                Photo listPhoto = new Photo();
                listPhoto.setId(prodCat.getId());
                listPhoto.setItemId(prodCat.getId());
                String fileName = UtilityHelper.getFilename(filePart);
                fileName = UtilityHelper.renameImage(fileName, "ProdCategory_" + prodCat.getId() + "_" + (new Date().toString()).replace(":", "_").replace(" ", "_"));
                String listCategoryUploadDir = rootPath + File.separator + avatarsFolder + "ProdCategory";
                try {
                    listPhoto.setPath(UtilityHelper.uploadFileToDirectory(listCategoryUploadDir, fileName, filePart));
                    prodCat.addPhoto(listPhoto);
                    prodCategoryDAO.addPhoto(listPhoto);
                } catch (DAOException | IOException ex) {
                    response.sendError(500, ex.getMessage());
                }
            }
        }

        if (hasError) {
            session.setAttribute("errorMessage", message);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/users"));
        } else {
            message = "Product category was successfully added";
            session.setAttribute("successMessage", message);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(500, "You are not allowed to see this page");
    }
}
