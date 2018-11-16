package it.unitn.shoppinglesto.servlet.product.category;

import it.unitn.shoppinglesto.db.daos.ProdCategoryDAO;
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

@WebServlet(name = "EditProductCategoryServlet")
@MultipartConfig
public class EditProductCategoryServlet extends HttpServlet {
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
        boolean modified = false;
        String avatarsFolder = getServletContext().getInitParameter("avatarsFolder");
        if (avatarsFolder == null) {
            response.sendError(500, "Avatars folder not configured");
            return;
        }

        String rootPath = System.getProperty("catalina.home");
        String nameProdCat = request.getParameter("nameProdCat");
        String descriptionProdCat = request.getParameter("descriptionProdCat");
        Integer prodCatId = Integer.parseInt(request.getParameter("prodCatId"));

        Category prodCat = null;
        if ( nameProdCat == null || descriptionProdCat == null || nameProdCat.equals("") || descriptionProdCat.equals("") || prodCatId.equals("")) {
            hasError = true;
            message = "All fields are mandatory and must be filled!";
        } else {
            try {
                prodCat = prodCategoryDAO.getByPrimaryKey(prodCatId);
                // prendi la categoria dal db
                prodCat.setDescription(descriptionProdCat);
                prodCat.setName(nameProdCat);
                // aggiungi la foto se presente
                prodCategoryDAO.simpleUpdate(prodCat);
                Part filePart = request.getPart("photo");
                if ((filePart != null) && (filePart.getSize() > 0)) {
                    Photo prodPhoto = new Photo();
                    prodPhoto.setId(prodCat.getId());
                    prodPhoto.setItemId(prodCat.getId());
                    String fileName = UtilityHelper.getFilename(filePart);
                    fileName = UtilityHelper.renameImage(fileName, "ProdCategory_" + prodCat.getId() + "_" + (new Date().toString()).replace(":", "_").replace(" ", "_"));
                    String listCategoryUploadDir = rootPath + File.separator + avatarsFolder + "ProdCategory";
                    try {
                        prodPhoto.setPath(UtilityHelper.uploadFileToDirectory(listCategoryUploadDir, fileName, filePart));
                        prodCat.addPhoto(prodPhoto);
                        prodCategoryDAO.addPhoto(prodPhoto);
                    } catch (DAOException | IOException ex) {
                        response.sendError(500, ex.getMessage());
                    }
                }
                // aggiorna la categoria
                prodCategoryDAO.update(prodCat);

            } catch (DAOException e) {
                response.sendError(500, e.getMessage());
            }
        }

        if (hasError) {
            session.setAttribute("errorMessage", message);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        } else {
            message = "Product category was successfully updated";
            session.setAttribute("successMessage", message);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/admin/prodCat"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("You can't access this page via get");
    }
}
