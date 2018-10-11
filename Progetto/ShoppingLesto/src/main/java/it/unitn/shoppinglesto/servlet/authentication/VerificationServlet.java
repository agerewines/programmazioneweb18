package it.unitn.shoppinglesto.servlet.authentication;

import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet which activate the user that clicks on the link in the email
 *
 * @author alessandrogerevini
 */
@WebServlet(name = "VerificationServlet")
public class VerificationServlet extends HttpServlet {
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
        response.setContentType("text/html;charset=UTF-8");

        String activationKey = request.getParameter("id");
        if(activationKey == null){
            response.sendError(500, "No activation key provided");
            return;
        }

        try {
            Integer userId = userDAO.getUserIdByActKey(activationKey);
            User user = userDAO.getByPrimaryKey(userId);
            if(user == null){
                response.sendError(500, "No user matches this id");
                return;
            }else{
                user.setActive(true);
                user = userDAO.update(user);
                userDAO.deleteActKey(user);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                String message = "Welcome " + user.getFirstName() + " " + user.getLastName();
                session.setAttribute("successMessage", message);
                response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/home"));
            }
        } catch (DAOException ex) {
            response.sendError(500, ex.getMessage());
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
