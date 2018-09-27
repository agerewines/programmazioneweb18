/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.servlet;

import it.unitn.shoppinglesto.db.entities.*;
import it.unitn.shoppinglesto.db.*;
import it.unitn.shoppinglesto.db.exceptions.*;
import it.unitn.shoppinglesto.db.factories.*;
import it.unitn.shoppinglesto.db.daos.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alessandrogerevini
 */
public class UsersListServlet extends HttpServlet {

    private UserDAO dao;


    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            dao = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system2", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            List<User> users = dao.getAll();
            request.setAttribute("users", users);
            getServletContext().getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(UsersListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
