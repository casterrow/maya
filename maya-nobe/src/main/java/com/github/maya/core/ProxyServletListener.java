/**
 * COMMERCIAL USE OF THIS SOFTWARE WITHOUT WARRANTY IS NOT ALLOWED.
 * Use is subject to license terms! You can distribute a copy of this software
 * to others for free. This software is to be a non-profit project in the future.
 * All rights reserved! Owned by Stephen Liu.
 */
package com.github.maya.core;

import java.util.Collection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author ste7en.liu@gmail.com
 * @since 2016/09/03
 */
public class ProxyServletListener
	implements ServletContextListener, HttpSessionListener {

	private ApplicationContext ctx;
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		Collection<HttpSessionListener> httpSessionListeners = 
				ctx.getBeansOfType(HttpSessionListener.class).values();
		
		httpSessionListeners.forEach(e -> e.sessionCreated(se));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		Collection<HttpSessionListener> httpSessionListeners = 
				ctx.getBeansOfType(HttpSessionListener.class).values();
		
		//销毁创建的session
		httpSessionListeners.forEach(e -> e.sessionDestroyed(se));
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		
		Collection<ServletContextListener> servletContextListeners = 
				ctx.getBeansOfType(ServletContextListener.class).values();
		
		servletContextListeners.forEach(e -> e.contextInitialized(sce));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		Collection<ServletContextListener> servletContextListeners = 
				ctx.getBeansOfType(ServletContextListener.class).values();
		
		//销毁创建的ServletContextListener
		servletContextListeners.forEach(e -> contextDestroyed(sce));
		
	}

}
