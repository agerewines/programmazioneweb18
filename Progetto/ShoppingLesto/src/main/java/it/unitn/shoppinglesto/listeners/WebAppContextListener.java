/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.listeners;

import it.unitn.shoppinglesto.db.exceptions.DAOFactoryException;
import it.unitn.shoppinglesto.db.factories.DAOFactory;
import it.unitn.shoppinglesto.db.factories.jdbc.JDBCDAOFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author alessandrogerevini
 */
public class WebAppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            InputStream input = sce.getServletContext().getResourceAsStream("/WEB-INF/db.properties");
            Properties props = new Properties();
            props.load(input);
            String dbUrl = props.getProperty("dbUrl");
            String dbUser = props.getProperty("dbUser");
            String dbPassword = props.getProperty("dbPassword");
            JDBCDAOFactory.configure(dbUrl, dbUser, dbPassword);
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            sce.getServletContext().setAttribute("daoFactory", daoFactory);
            
        }catch(IOException | DAOFactoryException ex) {
            Logger.getLogger(WebAppContextListener.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
