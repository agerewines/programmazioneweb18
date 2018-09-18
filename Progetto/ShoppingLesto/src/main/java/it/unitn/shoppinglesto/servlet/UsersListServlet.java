/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.servlet;

import it.unitn.shoppinglesto.db.*;
import it.unitn.shoppinglesto.db.beans.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alessandrogerevini
 */
public class UsersListServlet extends HttpServlet {

    private DBManager dbManager;

    @Override
    public void init() throws ServletException {
        dbManager = (DBManager) super.getServletContext().getAttribute("dbmanager");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            List<User> users = dbManager.getUsers();

            out.println(
                    "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>Lab 06: Users List</title>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                    + "        <!-- Latest compiled and minified CSS -->\n"
                    + "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" crossorigin=\"anonymous\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <div class=\"jumbotron\">\n"
                    + "            <div class=\"container\">\n"
                    + "                <div class=\"card border-primary\">\n"
                    + "                    <div class=\"card-header bg-primary text-white\">\n"
                    + "                        <h5 class=\"card-title\">Users</h5>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"card-body\">\n"
                    + "                        The following table lists all the users of the application.<br>\n"
                    + "                        For each user, you can see the count of shopping-lists shared with him.<br>\n"
                    + "                        Clicking on the number of shopping-lists, you can show the collection of shopping-lists shared with &quot;selected&quot; user.\n"
                    + "                    </div>\n"
                    + "\n"
                    + "                    <!-- Table -->\n"
                    + "                    <table class=\"table\">\n"
                    + "                        <tr>\n"
                    + "                            <th>Email</th>\n"
                    + "                            <th>First name</th>\n"
                    + "                            <th>Last name</th>\n"
                    + "                            <th>Shopping Lists</th>\n"
                    + "                        </tr>\n"
            );
            for (User user : users) {
                out.println(
                        "                        <tr>\n"
                        + "                            <td>" + user.getMail() + "</td>\n"
                        + "                            <td>" + user.getName() + "</td>\n"
                        + "                            <td>" + user.getSurname() + "</td>\n"
                       // + "                            <td><a href=\"shopping.lists.handler?id=" + user.getId() + "\"><span class=\"badge badge-primary badge-pill\">" + user.getShoppingListsCount()+ "</span></a></td>\n"
                        + "                        </tr>\n"
                );
            }
            out.println(
                    "                    </table>\n"
                    + "                    \n"
                    + "                    <div class=\"card-footer\">Copyright &copy; 2018 - Stefano Chirico</div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <!-- Latest compiled and minified JavaScript -->\n"
                    + "        <script src=\"https://code.jquery.com/jquery-3.2.1.min.js\" crossorigin=\"anonymous\"></script>\n"
                    + "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" crossorigin=\"anonymous\"></script>\n"
                    + "    </body>\n"
                    + "</html>"
            );

        } catch (SQLException ex) {
            out.println(
                    "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>Lab 06: Users List</title>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                    + "        <!-- Latest compiled and minified CSS -->\n"
                    + "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" crossorigin=\"anonymous\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <div class=\"jumbotron\">\n"
                    + "            <div class=\"container\">\n"
                    + "                <div class=\"card border-danger\">\n"
                    + "                    <div class=\"card-header bg-danger text-white\">\n"
                    + "                        <h3 class=\"card-title\">Users</h3>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"card-body\">\n"
                    + "                        Error in retriving users list: " + ex.getMessage() + "<br>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"card-footer\">Copyright &copy; 2018 - Stefano Chirico</div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <!-- Latest compiled and minified JavaScript -->\n"
                    + "        <script src=\"https://code.jquery.com/jquery-3.2.1.min.js\" crossorigin=\"anonymous\"></script>\n"
                    + "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" crossorigin=\"anonymous\"></script>\n"
                    + "    </body>\n"
                    + "</html>"
            );
        }
    }

}
