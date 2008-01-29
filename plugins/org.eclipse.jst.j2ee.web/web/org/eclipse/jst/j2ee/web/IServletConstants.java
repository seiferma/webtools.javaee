/*******************************************************************************
 * Copyright (c) 2008 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Kaloyan Raev, kaloyan.raev@sap.com - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.web;

public interface IServletConstants {
	
	public static final String QUALIFIED_IO_EXCEPTION = "java.io.IOException"; //$NON-NLS-1$
	
	public static final String QUALIFIED_SERVLET = "javax.servlet.Servlet"; //$NON-NLS-1$
	public static final String QUALIFIED_GENERIC_SERVLET = "javax.servlet.GenericServlet"; //$NON-NLS-1$
	public static final String QUALIFIED_HTTP_SERVLET = "javax.servlet.http.HttpServlet"; //$NON-NLS-1$
	
	public static final String QUALIFIED_SERVLET_EXCEPTION = "javax.servlet.ServletException"; //$NON-NLS-1$
	public static final String QUALIFIED_SERVLET_CONFIG = "javax.servlet.ServletConfig"; //$NON-NLS-1$
	public static final String QUALIFIED_SERVLET_REQUEST = "javax.servlet.ServletRequest"; //$NON-NLS-1$
	public static final String QUALIFIED_SERVLET_RESPONSE = "javax.servlet.ServletResponse"; //$NON-NLS-1$
	public static final String QUALIFIED_HTTP_SERVLET_REQUEST = "javax.servlet.http.HttpServletRequest"; //$NON-NLS-1$
	public static final String QUALIFIED_HTTP_SERVLET_RESPONSE = "javax.servlet.http.HttpServletResponse"; //$NON-NLS-1$

	public static final String QUALIFIED_FILTER = "javax.servlet.Filter"; //$NON-NLS-1$
	public static final String QUALIFIED_FILTER_CONFIG = "javax.servlet.FilterConfig"; //$NON-NLS-1$
	public static final String QUALIFIED_FILTER_CHAIN = "javax.servlet.FilterChain"; //$NON-NLS-1$
	
	public static final String QUALIFIED_SERVLET_CONTEXT_LISTENER = "javax.servlet.ServletContextListener"; //$NON-NLS-1$
	public static final String QUALIFIED_SERVLET_CONTEXT_EVENT = "javax.servlet.ServletContextEvent"; //$NON-NLS-1$
	public static final String QUALIFIED_SERVLET_CONTEXT_ATTRIBUTE_LISTENER = "javax.servlet.ServletContextAttributeListener"; //$NON-NLS-1$
	public static final String QUALIFIED_SERVLET_CONTEXT_ATTRIBUTE_EVENT = "javax.servlet.ServletContextAttributeEvent"; //$NON-NLS-1$
	public static final String QUALIFIED_HTTP_SESSION_LISTENER = "javax.servlet.http.HttpSessionListener"; //$NON-NLS-1$
	public static final String QUALIFIED_HTTP_SESSION_EVENT = "javax.servlet.http.HttpSessionEvent"; //$NON-NLS-1$
	public static final String QUALIFIED_HTTP_SESSION_ATTRIBUTE_LISTENER = "javax.servlet.http.HttpSessionAttributeListener"; //$NON-NLS-1$
	public static final String QUALIFIED_HTTP_SESSION_BINDING_EVENT = "javax.servlet.http.HttpSessionBindingEvent"; //$NON-NLS-1$
	public static final String QUALIFIED_HTTP_SESSION_ACTIVATION_LISTENER = "javax.servlet.http.HttpSessionActivationListener"; //$NON-NLS-1$
	public static final String QUALIFIED_HTTP_SESSION_BINDING_LISTENER = "javax.servlet.http.HttpSessionBindingListener"; //$NON-NLS-1$
	public static final String QUALIFIED_SERVLET_REQUEST_LISTENER = "javax.servlet.ServletRequestListener"; //$NON-NLS-1$
	public static final String QUALIFIED_SERVLET_REQUEST_EVENT = "javax.servlet.ServletRequestEvent"; //$NON-NLS-1$
	public static final String QUALIFIED_SERVLET_REQUEST_ATTRIBUTE_LISTENER = "javax.servlet.ServletRequestAttributeListener"; //$NON-NLS-1$
	public static final String QUALIFIED_SERVLET_REQUEST_ATTRIBUTE_EVENT = "javax.servlet.ServletRequestAttributeEvent"; //$NON-NLS-1$
	
	public static final String METHOD_INIT = "init"; //$NON-NLS-1$
	public static final String METHOD_DESTROY = "destroy"; //$NON-NLS-1$
	public static final String METHOD_GET_SERVLET_CONFIG = "getServletConfig"; //$NON-NLS-1$
	public static final String METHOD_GET_SERVLET_INFO = "getServletInfo"; //$NON-NLS-1$
	public static final String METHOD_SERVICE = "service"; //$NON-NLS-1$
	public static final String METHOD_DO_GET = "doGet"; //$NON-NLS-1$
	public static final String METHOD_DO_POST = "doPost"; //$NON-NLS-1$
	public static final String METHOD_DO_PUT = "doPut"; //$NON-NLS-1$
	public static final String METHOD_DO_DELETE = "doDelete"; //$NON-NLS-1$
	public static final String METHOD_DO_HEAD = "doHead"; //$NON-NLS-1$
	public static final String METHOD_DO_OPTIONS = "doOptions"; //$NON-NLS-1$
	public static final String METHOD_DO_TRACE = "doTrace"; //$NON-NLS-1$
	public static final String METHOD_TO_STRING = "toString"; //$NON-NLS-1$
	public static final String METHOD_DO_FILTER = "doFilter"; //$NON-NLS-1$

}
