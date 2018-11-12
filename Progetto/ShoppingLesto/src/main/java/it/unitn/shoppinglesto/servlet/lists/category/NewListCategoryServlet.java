package it.unitn.shoppinglesto.servlet.lists.category;

import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
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

@WebServlet(name = "NewListCategoryServlet")
@MultipartConfig
public class NewListCategoryServlet extends HttpServlet {
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
            listCategoryDAO = daoFactory.getDAO(ListCategoryDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get list category dao from dao factory!", ex);
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
        String avatarsFolder = getServletContext().getInitParameter("avatarsFolder");
        if (avatarsFolder == null) {
            response.sendError(500, "Avatars folder not configured");
            return;
        }

        String rootPath = System.getProperty("catalina.home");
        String nameList = request.getParameter("nameListCat");
        String descriptionList = request.getParameter("descriptionListCat");

        Category listCat = null;
        if (nameList == null || descriptionList == null || nameList.equals("") || descriptionList.equals("")) {
            hasError = true;
            message = "All fields are mandatory and must be filled!";
        } else {
            listCat = new Category();
            listCat.setName(nameList);
            listCat.setDescription(descriptionList);
            try{
                // aggiungi  a ListCategory
                listCat.setId(listCategoryDAO.save(listCat));
            } catch (DAOException ex) {
                response.sendError(500, ex.toString());
            }
            // aggiungi le foto se ci sono
            Part filePart = request.getPart("photo");
            if ((filePart != null) && (filePart.getSize() > 0)) {
                Photo listPhoto = new Photo();
                listPhoto.setId(listCat.getId());
                listPhoto.setItemId(listCat.getId());
                String fileName = UtilityHelper.getFilename(filePart);
                fileName = UtilityHelper.renameImage(fileName, "ListCategory_" + listCat.getId() + "_" + (new Date().toString()).replace(":", "_").replace(" ", "_"));
                String listCategoryUploadDir = rootPath + File.separator + avatarsFolder + "ListCategory";
                try {
                    listPhoto.setPath(UtilityHelper.uploadFileToDirectory(listCategoryUploadDir, fileName, filePart));
                    listCat.addPhoto(listPhoto);
                    listCategoryDAO.addPhoto(listPhoto);
                } catch (DAOException | IOException ex) {
                    response.sendError(500, ex.getMessage());
                }
            }
        }

        if (hasError) {
            session.setAttribute("errorMessage", message);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/users"));
        } else {
            message = "List was successfully updated";
            session.setAttribute("successMessage", message);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(500, "You are not allowed to see this page");
    }
}
