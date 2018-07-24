package com.huidao.fishing.game.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.huidao.fishing.base.session.SessionManager;

/**
 * Application Lifecycle Listener implementation class AppColseListener
 *
 */
public class AppColseListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public AppColseListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	SessionManager.closeAll();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
