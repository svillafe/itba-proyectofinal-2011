package argendata.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import argendata.model.relational.ArgendataUser;



public class AdminFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");
		if (user == null) {
			resp.sendRedirect("../main/home");
			return;
		}

		if (user.isAdmin() ) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect("../main/home");
		}

	}

	public void destroy() {}

	public void init(FilterConfig filterConfig) throws ServletException {}

}
