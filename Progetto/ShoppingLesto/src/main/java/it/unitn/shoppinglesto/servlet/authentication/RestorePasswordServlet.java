package it.unitn.shoppinglesto.servlet.authentication;

import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.utils.MailHelper;
import it.unitn.shoppinglesto.utils.VelocityHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

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

@WebServlet(name = "RestorePasswordServlet")
public class RestorePasswordServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userMail = request.getParameter("userMail");
        if (userMail == null) {
            response.sendError(500, "There was an error processing the request");
            return;
        }
        // crea stringa nuova password
        // converti in MD5
        // cambia la pw nel db
        // manda la mail all'utente

        try{
            User user = userDAO.getByMail(userMail);
            final String host = getServletContext().getInitParameter("smtp-hostname");
            final String port = getServletContext().getInitParameter("smtp-port");
            final String mail = getServletContext().getInitParameter("smtp-username");
            final String password = getServletContext().getInitParameter("smtp-password");

            try{
                MailHelper mailer = new MailHelper(host, port, mail, password, new InternetAddress(mail, "ShoppingLesto"), new InternetAddress[]{new InternetAddress(user.getMail(), user.getFullName())}, "Restore password ShoppingLesto");
                Map<String, String> model = new HashMap<>();
                model.put("mail", user.getMail());
                String newPw = RandomStringUtils.random(8, true, true);
                model.put("pw", newPw);
                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(VelocityHelper.createVelocityContent("restorepassword.vm", model),"text/html; charset=utf-8");
                mailer.addSinglePartToMultipart(bodyPart);
                mailer.sendMessage();
                user.setPassword(DigestUtils.md5Hex(newPw));
                user = userDAO.update(user);
                String infoMessage = "We have sent an email to the address you gave us.";
                request.getSession().setAttribute("infoMessage", infoMessage);
                response.sendRedirect(getServletContext().getContextPath() + "/");

            }catch(MessagingException | UnsupportedEncodingException me) {
                response.sendError(500, me.getMessage());
            }
        } catch (DAOException e) {
            response.sendError(500, "Cannot restore password");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
