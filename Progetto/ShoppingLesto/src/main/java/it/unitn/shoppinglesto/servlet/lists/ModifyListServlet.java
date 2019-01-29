package it.unitn.shoppinglesto.servlet.lists;

import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
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
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "ModifyListServlet")
public class ModifyListServlet extends HttpServlet {
    private final String TEMPLISTCOOKIENAME = "templist_shoppingLesto_token";
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
        boolean anon = false;
        if (request.getParameterMap().containsKey("anonymous")) {
            anon = true;
        }
        if (user == null && !anon) {
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

        Integer listId = null;
        try {
            listId = Integer.valueOf(request.getParameter("listId"));
        } catch (RuntimeException ex) {
            response.sendError(500, ex.getMessage());
        }
        try {
            ShoppingList list = shoppingListDAO.getByPrimaryKey(listId);
            if(!anon)
                list.setUser(userDAO.getById(list.getUserId()));
            list.setCategory(listCategoryDAO.getByPrimaryKey(list.getCategoryId()));
            String rootPath = System.getProperty("catalina.home");
            String name = request.getParameter("nameList");
            String description = request.getParameter("descriptionList");
            int categoryId = Integer.parseInt(request.getParameter("category"));
            if (name == null || description == null || name.equals("") || description.equals("")) {
                hasError = true;
                message = "All fields are mandatory and must be filled!";
            } else {
                if (description.length() > 255) {
                    hasError = true;
                    message = "List description can contain a maximum of  255 characters.";
                } else {
                    // int id, String name, String description, String image, int categoryId, int userId, User user
                    list.setName(name);
                    list.setDescription(description);
                    if(categoryId != -1)
                        list.setCategoryId(categoryId);

                    if(!anon)
                        list = shoppingListDAO.update(list);
                    else
                        list = shoppingListDAO.updateAnon(list);

                    Part filePart = request.getPart("avatar");
                    if ((filePart != null) && (filePart.getSize() > 0)) {
                        String fileName = UtilityHelper.getFilename(filePart);
                        fileName = UtilityHelper.renameImage(fileName, "List_" + list.getId());
                        String listIconUploadDir = rootPath + File.separator + avatarsFolder + "Lists";
                        try {
                            list.setImage(UtilityHelper.uploadFileToDirectory(listIconUploadDir, fileName, filePart));
                        } catch (IOException ex) {
                            response.sendError(500, ex.getMessage());
                        }
                        modified = true;
                    }
                    if(!anon)
                        list = shoppingListDAO.update(list);
                    else
                        list = shoppingListDAO.updateAnon(list);
                    if (user != null) {
                        list.setUserId(user.getId());
                    }
                }

            }
        } catch (DAOException e) {
            response.sendError(500, e.getMessage());
        }

        if (hasError) {
            session.setAttribute("errorMessage", message);
            session.setAttribute("action", "editList");
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        } else {
            message = "List was successfully updated";
            session.setAttribute("successMessage", message);
            if(anon)
                response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/list?id=" + listId + "&anonymous=true"));
            else
                response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/list?id=" + listId));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("You can't access this page via get");
    }
}
