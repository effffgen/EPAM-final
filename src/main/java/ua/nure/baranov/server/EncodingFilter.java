package ua.nure.baranov.server;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter("/*")
public class EncodingFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(EncodingFilter.class);

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String requestEncoding = request.getCharacterEncoding();
		if (requestEncoding == null) {
			LOGGER.trace("Setting encoding UTF-8");
			request.setCharacterEncoding("UTF-8");
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		LOGGER.info("Encoding filter destroyed");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		LOGGER.info("Encoding filter initialization");
	}


}
