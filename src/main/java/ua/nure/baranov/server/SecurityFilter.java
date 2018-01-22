package ua.nure.baranov.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.entity.Role;
import ua.nure.baranov.entity.User;

@WebFilter(
		dispatcherTypes = {DispatcherType.REQUEST }, 
		urlPatterns = { "/*" }, 
		initParams = { 
				@WebInitParam(name = "operator", value = "/dashboard"),
				@WebInitParam(name = "administrator", value = "/management"),
				@WebInitParam(name = "attendant", value = "/*"),
				@WebInitParam(name = "operator", value = "/*"),
				@WebInitParam(name = "navigator", value = "/*"),
		})
public class SecurityFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(SecurityFilter.class);
	private String contextPath;
	public SecurityFilter() {
      
    }

	public void destroy() {
		LOGGER.info("Security filter destroyed");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	/*	HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		LOGGER.trace("User --> " + user);
			
		
		String contextPath = req.getServletContext().getContextPath();
		LOGGER.trace("ContextPath --> " + contextPath);
		
		String resource = req.getRequestURI().substring(contextPath.length());
		LOGGER.trace("Resourse --> " + resource);
		
		if(user == null) {
			if(!"/login".equals(resource)||"/login.jsp".equals(resource)) {
				LOGGER.trace("Unregistered user is trying to get access to pages, redirecting to the login page");
				((HttpServletResponse)response).sendRedirect("login");
				return;
			}
		}
		
	/*	if (!accept(user.getRole(), resourse)) {
			((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login.jsp");
			
		}
		
		*/
		chain.doFilter(request, response);
	}

	
/*	private boolean accept(Role role, String res) {
		
		String sRole = role == null ? null : role.toString();
		
		// resource unrestricted
		if (!contains(res)) {
			LOGGER.debug("Access to unsecure zone accepted" + res);
			return true;
		} 
		// unknown user go to the restricted resource
		if (!resourses.containsKey(sRole) ) {
			LOGGER.debug("Access to secure zone denied" + res);
			return false;
		} 
		// known user go to the restricted resource
		if (contains(res, resourses.get(sRole))) {
			LOGGER.debug("Access to secure zone accepted--> " + res);
			return true;
		}
		return false;
	}*/
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
