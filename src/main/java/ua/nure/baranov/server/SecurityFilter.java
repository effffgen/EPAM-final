/*package ua.nure.baranov.server;

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

import org.apache.log4j.Logger;

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

	private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);
	private String contextPath = null;
	public SecurityFilter() {
      
    }

	public void destroy() {
		LOGGER.info("Security filter destroyed");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		User user = null;
		if (session.isNew()) {
			user = new User();
			session.setAttribute("user", user);
		}
		user = (User) session.getAttribute("user");
		LOGGER.trace("User --> " + user);
		
		String contextPath = req.getServletContext().getContextPath();
		LOGGER.trace("ContextPath --> " + contextPath);
		
		String resourse = req.getRequestURI().substring(contextPath.length());
		LOGGER.trace("Resourse --> " + resourse);
		
		if (!accept(user.getRole(), resourse)) {
			((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}
		
		
		chain.doFilter(request, response);
	}

	
	private boolean accept(Role role, String res) {
		
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
	}
	

	public void init(FilterConfig fConfig) throws ServletException {
		contextPath = fConfig.getServletContext().getContextPath();
		log.debug("contextPath -->" + contextPath);
		Enumeration<String> roles = fConfig.getInitParameterNames();
		while (roles.hasMoreElements()) {
			String role = (String) roles.nextElement();
			LOGGER.debug("Init param -->" + role);
			if (role.equals("userAttribute")) {
				ua = fConfig.getInitParameter(role);
				log.debug("User session attribute name -->" + ua);
				continue;
			}
			String[] path = fConfig.getInitParameter(role).split(",");
			Arrays.sort(path);
			for (int i = 0; i < path.length; i++) {
				path[i] = path[i].replace("*", ".*");
			}
			resourses.put(role, path);
			LOGGER.debug("deny --> " + role + Arrays.toString(path));
		}
	}
		LOGGER.info("Security filter started");
	}

}
*/