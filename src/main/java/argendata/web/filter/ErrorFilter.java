package argendata.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ErrorFilter extends OncePerRequestFilter {

	private final Logger logger = Logger.getLogger(ErrorFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
	{

		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			try {
				logger.error(e.getStackTrace(),e);
				logger.error(e);
				response.sendRedirect("../static/error");
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.error(e.getStackTrace(),e);
			}
		}
	}

}
