package it.unitn.shoppinglesto.servlet.chat;

import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.daos.MessageDAO;
import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.Message;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
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
import java.util.List;
import java.util.Map;

@WebServlet(name = "CreateMessageServlet")
public class CreateMessageServlet extends HttpServlet {
    private UserDAO userDAO;
    private ShoppingListDAO shoppingListDAO;
    private MessageDAO messageDAO;

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
            shoppingListDAO = daoFactory.getDAO(ShoppingListDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get shoppingList dao from dao factory!", ex);
        }
        try {
            messageDAO = daoFactory.getDAO(MessageDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get message dao from dao factory!", ex);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ShoppingList list = null;
        boolean anon = false;
        if (request.getParameterMap().containsKey("anonymous")) {
            anon = true;
        }
        Integer listId = null;
        try {
            listId = Integer.valueOf(request.getParameter("listId"));
        } catch (RuntimeException ex) {
            response.sendError(500, ex.getMessage());
        }

        if (user != null && !anon) {
            try {
                Message message = new Message();
                message.setListId(listId);
                message.setUserId(user.getId());
                message.setText(request.getParameter("message"));
                message.setId(messageDAO.save(message));

                // send mail to users
                list = shoppingListDAO.getByPrimaryKey(listId);
                Integer ownerId = shoppingListDAO.getListOwner(list);
                List<User> sendTo = shoppingListDAO.getSharedUser(list);
                sendTo.add(userDAO.getById(ownerId));


                final String host = getServletContext().getInitParameter("smtp-hostname");
                final String port = getServletContext().getInitParameter("smtp-port");
                final String mail = getServletContext().getInitParameter("smtp-username");
                final String password = getServletContext().getInitParameter("smtp-password");

                try{
                    String subject = "New message in chat";
                    for (User userToSend :sendTo) {
                        if(!userToSend.getMail().equals(user.getMail())){
                            MailHelper mailer = new MailHelper(host, port, mail, password, new InternetAddress(mail, "ShoppingLesto"), new InternetAddress[]{new InternetAddress(userToSend.getMail(), user.getFullName().trim())}, subject);
                            Map<String, String> model = new HashMap<>();
                            model.put("name", userToSend.getFullName());
                            model.put("site", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());
                            model.put("message", message.getText());
                            model.put("userName", user.getFullName());
                            model.put("listName", list.getName());

                            MimeBodyPart bodyPart = new MimeBodyPart();
                            bodyPart.setContent(VelocityHelper.createVelocityContent("messageMailTemplate.vm", model),"text/html; charset=utf-8");
                            mailer.addSinglePartToMultipart(bodyPart);
                            mailer.sendMessage();
                        }
                    }

                }catch(MessagingException | UnsupportedEncodingException me) {
                    response.sendError(500, me.getMessage());
                }

            } catch (DAOException e) {
                response.sendError(500, e.getMessage());
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
