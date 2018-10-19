package it.unitn.shoppinglesto.servlet;

import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.utils.UtilityHelper;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet(name = "UserProfileServlet")
public class UserProfileServlet extends HttpServlet {

    private UserDAO userDAO;

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
        if(!response.isCommitted())
            request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if(user == null){
            response.sendError(500, "There was an error processing the request");
            return;
        }
        String message = null;
        boolean hasError = false;
        boolean modified = false;
        String avatarsFolder = getServletContext().getInitParameter("avatarsFolder");
        if(avatarsFolder == null){
            response.sendError(500, "Avatars folder not configured");
            return;
        }

        String rootPath =System.getProperty("catalina.home");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");


        if(firstName == null || lastName == null || firstName.equals("") || lastName.equals("")){
            hasError = true;
            message = "All fields are mandatory and must be filled!";
        }else{
            Part filePart = request.getPart("avatar");
            if((filePart != null) && (filePart.getSize() > 0)){
                String fileName = UtilityHelper.getFilename(filePart);
                fileName = UtilityHelper.renameImageToOwner(fileName, "Profile_" + user.getId() + "_" + user.getFullName() + "_" + (new Date().toString()).replace(":", "_"));
                String userAvatarUploadDir = rootPath + File.separator + avatarsFolder +"/Users";
                try{
                    user.setAvatar(UtilityHelper.uploadFileToDirectory(userAvatarUploadDir, fileName, filePart));
                }catch(IOException ex){
                    response.sendError(500, ex.getMessage());
                }
                modified = true;
            }

            if(!firstName.equals(user.getFirstName())){
                modified = true;
                user.setFirstName(firstName);
            }
            if(!lastName.equals(user.getLastName())){
                modified = true;
                user.setLastName(lastName);
            }



        }

        if(hasError){
            request.setAttribute("errorMessage", message);
            getServletContext().getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
        }else{
            try {
                if(modified){
                    user = userDAO.update(user);
                    message = "Profile was successfully updated";
                    session.setAttribute("successMessage", message);
                }
                response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/users/profile"));
            } catch (DAOException ex) {
                response.sendError(500, ex.toString());
            }
        }
    }

}
