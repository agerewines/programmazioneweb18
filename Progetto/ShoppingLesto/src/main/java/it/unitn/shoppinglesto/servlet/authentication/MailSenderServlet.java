package it.unitn.shoppinglesto.servlet.authentication;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.geolocation.RawDBDemoGeoIPLocationService;
import it.unitn.shoppinglesto.utils.MailHelper;
import it.unitn.shoppinglesto.utils.UtilityHelper;
import it.unitn.shoppinglesto.utils.VelocityHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import javax.mail.internet.InternetAddress;
import java.util.Map;

@WebServlet(name = "MailSenderServlet")
/**
 *  Servlet used to send email after registration to the mail provided by the user
 * @author alessandrogerevini
 */
public class MailSenderServlet extends HttpServlet {
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
        response.setContentType("text/html;charset=UTF-8");
        try{
            User user = (User) request.getAttribute("user");
            String path = (String) request.getAttribute("path");
            String subject = (String) request.getAttribute("subject");
            String templateName = (String) request.getAttribute("template");
            String linkName = (String) request.getAttribute("linkName");

            if(user == null || path == null || subject == null || templateName == null || linkName == null){
                response.sendError(500, "Impossible to send the email. No valid user!");
                return;
            }

            String activationKey = userDAO.getActivationKey(user);
            String url;
            RawDBDemoGeoIPLocationService rdb = new RawDBDemoGeoIPLocationService(getServletContext());
            if (templateName.equals("geolocTemplate.vm")) {
                String ip = request.getRemoteAddr();

                JsonNode myCity = rdb.getLocation(ip);

                JsonNode location = myCity.get("location");
                Float latitude = Float.valueOf(location.get("latitude").toString());
                Float longitude = Float.valueOf(location.get("longitude").toString());

                Float latMax = latitude + 0.01f;
                Float lonMax = longitude + 0.01f;
                Float latMin = latitude - 0.01f;
                Float lonMin = longitude - 0.01f;

                url = "https://www.openstreetmap.org/export/embed.html?bbox=" + lonMin + "%2C" + latMin + "%2C" + lonMax + "%2C" + latMax + "&amp;layer=hot";
            } else
                url = UtilityHelper.getURLWithContextPath(request) + path + activationKey;

            final String host = getServletContext().getInitParameter("smtp-hostname");
            final String port = getServletContext().getInitParameter("smtp-port");
            final String mail = getServletContext().getInitParameter("smtp-username");
            final String password = getServletContext().getInitParameter("smtp-password");

            try{
                MailHelper mailer = new MailHelper(host, port, mail, password, new InternetAddress(mail, "ShoppingLesto"), new InternetAddress[]{new InternetAddress(user.getMail(), (user.getFirstName() + " " + user.getLastName()).trim())}, subject);
                Map<String, String> model = new HashMap<>();
                model.put("name", user.getFullName());
                model.put("site", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());
                model.put("url", url);
                model.put("linkName", linkName);

                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(VelocityHelper.createVelocityContent(templateName, model),"text/html; charset=utf-8");
                mailer.addSinglePartToMultipart(bodyPart);
                mailer.sendMessage();

                String infoMessage = "We have sent an email to the address you gave us. Please check your email and follow the instructions.";
                request.getSession().setAttribute("infoMessage", infoMessage);
                response.sendRedirect(getServletContext().getContextPath() + "/");

            }catch(MessagingException | UnsupportedEncodingException me) {
                response.sendError(500, me.getMessage());
            }

        } catch (DAOException ex) {
            response.sendError(500, ex.getMessage());
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }
        // Connect to google
        // create email
        // send email
        // send message to user notifying to check email

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
