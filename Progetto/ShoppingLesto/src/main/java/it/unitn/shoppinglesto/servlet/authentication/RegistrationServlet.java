/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.servlet.authentication;

import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alessandrogerevini
 */
public class RegistrationServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory!");
        }
        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get user dao from factory!", ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            session.setAttribute("action", "register");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/register.jsp");
            rd.forward(request, response);
        } else {
            String message = "You are already logged in";
            session.setAttribute("infoMessage", message);
            // rimando alla servlet della home
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        }
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

        User user = null;
        boolean hasError = false;
        String message = null;
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("mail");
        String password = (String) request.getAttribute("password");
        String confirmation = (String) request.getAttribute("confirmation");
        // Dubbi
        String termsStr = request.getParameter("checkTerms");
        boolean terms = "A".equals(termsStr);

        if (firstName == null || lastName == null || email == null || password == null || confirmation == null
            || firstName.length()==0 || lastName.length() ==0
            || email.length()==0 || password.length()==0 || confirmation.length() == 0){
            hasError = true;
            message = "All fields are mandatory! You are missing something!";
        } else {
            try {
                if (userDAO.emailExists(email)) {
                    hasError = true;
                    message = "Email is already taken by someone else";
                } else {
                    if (!password.equals(confirmation)) {
                        hasError = true;
                        message = "Password confirmation does not match Password!";
                    } else {
                        if(!terms){
                            hasError = true;
                            message = "You need to accept our terms, before register!";
                        }else{
                            user = new User(null, email, firstName, lastName, password);
                            userDAO.save(user);
                        }

                    }
                }
            } catch (DAOException ex) {
                response.sendError(500, ex.getMessage());
            }

        }
        String dest;
        if (hasError) {
            request.setAttribute("errorMessage", message);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("mail", email);
            request.setAttribute("action", "registerError");
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
        } else {
            request.setAttribute("user", user);
            request.setAttribute("path", "/verification?id=");
            request.setAttribute("subject", "Confirmation Instructions");
            request.setAttribute("template", "confirmRegistrationTemplate.vm");
            request.setAttribute("linkName", "Confirm Registration");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/sendRegistrationEmail.handler");
            rd.forward(request, response);
        }

    }

}
