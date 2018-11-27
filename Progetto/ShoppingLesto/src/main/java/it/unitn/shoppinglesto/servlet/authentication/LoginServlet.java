package it.unitn.shoppinglesto.servlet.authentication;

import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.utils.CookieHelper;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException{
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if(daoFactory == null){
            throw new ServletException("Impossible to get dao factory!");
        }
        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get user dao from factory!", ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberStr = request.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberStr);
        String returnUrl = request.getParameter("returnUrl");
        if (returnUrl == null || returnUrl.length() == 0)
            returnUrl = getServletContext().getContextPath() + "/home";

        User user = null;
        boolean hasError = false;
        String message = null;

        if(email == null || password == null || email.length() == 0 || password.length() == 0){
            hasError = true;
            message = "Required email and password";
        }else{
            try {
                user = userDAO.getByMail(email);
                if(user == null){
                    hasError = true;
                    message = "Invalid email or password. Please try again!";
                }else{
                    if(!user.isActive()){
                        hasError = true;
                        message = "This account hasn't been verified. Please check your email and verify the account.";
                    }else{
                        String pwMd5 = DigestUtils.md5Hex(password);
                        if(!user.getPassword().equals(pwMd5)){
                            hasError = true;
                            message = "Invalid email or password. Please try again!";
                        }
                    }

                }
            } catch (DAOException ex) {
                response.sendError(500, ex.getMessage());
            }
        }

        if(hasError){
            request.setAttribute("errorMessage", message);
            request.setAttribute("action", "login");
            request.setAttribute("userMail", email);

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");
            rd.forward(request, response);
        }else{
            HttpSession session = request.getSession();
            if(remember){
                try {
                    user.setUuid(DigestUtils.sha256Hex(UUID.randomUUID().toString()));
                    userDAO.update(user);
                    CookieHelper.storeUserCookie(response, user);
                } catch (DAOException ex) {
                    response.sendError(500, ex.getMessage());
                }
            }
            Integer status = null;
            try {
                status = userDAO.checkStatus(user);
            } catch (DAOException ex) {
                response.sendError(500, ex.getMessage());
            }
            message = "Welcome back " + user.getFirstName() + " " + user.getLastName();
            if(status != null && !status.equals(0))
                user.setAdmin(true);
            session.setAttribute("user", user);
            session.setAttribute("successMessage", message);

            request.setAttribute("user", user);
            request.setAttribute("path", "/geoip");
            request.setAttribute("subject", "Our suggestions");
            request.setAttribute("template", "geolocTemplate.vm");
            request.setAttribute("linkName", "Point of Interest");

            //RequestDispatcher rd = getServletContext().getRequestDispatcher("/sendRegistrationEmail.handler");
            //rd.forward(request, response);

            response.sendRedirect(response.encodeRedirectURL(returnUrl));

        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            session.setAttribute("action", "login");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");
            rd.forward(request, response);
        } else {
            String message = "You are already logged in";
            session.setAttribute("errorMessage", message);
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        }
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
