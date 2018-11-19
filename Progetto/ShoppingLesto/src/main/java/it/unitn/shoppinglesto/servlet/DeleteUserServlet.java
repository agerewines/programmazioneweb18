package it.unitn.shoppinglesto.servlet;

import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.utils.MailHelper;
import it.unitn.shoppinglesto.utils.VelocityHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
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
            throw new ServletException("Impossible to get user dao from dao factory!", ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        session.removeAttribute("user");
        if (user == null) {
            response.sendError(500, "There was an error processing the request");
            return;
        }
        // delete user
        try{
            String email = user.getMail();
            String fullName = user.getFullName();
            userDAO.delete(user.getId());
            final String host = getServletContext().getInitParameter("smtp-hostname");
            final String port = getServletContext().getInitParameter("smtp-port");
            final String mail = getServletContext().getInitParameter("smtp-username");
            final String password = getServletContext().getInitParameter("smtp-password");

            try{
                MailHelper mailer = new MailHelper(host, port, mail, password, new InternetAddress(mail, "ShoppingLesto"), new InternetAddress[]{new InternetAddress(email, fullName)}, "Deleted account on ShoppingLesto");
                Map<String, String> model = new HashMap<>();

                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(VelocityHelper.createVelocityContent("deleteuser.vm", model),"text/html; charset=utf-8");
                mailer.addSinglePartToMultipart(bodyPart);
                mailer.sendMessage();

                String infoMessage = "We have sent an email to the address you gave us.";
                request.getSession().setAttribute("infoMessage", infoMessage);
                response.sendRedirect(getServletContext().getContextPath() + "/");

            }catch(MessagingException | UnsupportedEncodingException me) {
                response.sendError(500, me.getMessage());
            }
        } catch (DAOException e) {
            response.sendError(500, "Cannot delete user");
        }
        // remove it from session

        // go to the home page
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
