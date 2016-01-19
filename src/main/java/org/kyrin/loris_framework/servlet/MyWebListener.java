package org.kyrin.loris_framework.servlet;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class MyWebListener implements ServletContextListener,ServletContextAttributeListener,ServletRequestListener,ServletRequestAttributeListener{

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("----------servletContext init-----------");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("----------servletContext destroy----------");
	}

	public void attributeAdded(ServletContextAttributeEvent event) {
		System.out.println("----------servletContext attribute added----------");
	}

	public void attributeRemoved(ServletContextAttributeEvent event) {
		System.out.println("----------servletContext attribute removed----------");
	}

	public void attributeReplaced(ServletContextAttributeEvent event) {
		System.out.println("----------servletContext attribute replaced----------");
	}

	public void requestDestroyed(ServletRequestEvent sre) {
		System.out.println("----------servlet request destroyed----------");
	}

	public void requestInitialized(ServletRequestEvent sre) {
		System.out.println("----------servlet request initialized----------");
	}

	public void attributeAdded(ServletRequestAttributeEvent srae) {
		System.out.println("----------servlet request attribute added----------");
	}

	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		System.out.println("----------servlet request attribute removed----------");
	}

	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		System.out.println("----------servlet request attribute replaced----------");
	}

}
