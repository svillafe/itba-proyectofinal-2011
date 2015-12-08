package argendata.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import argendata.model.relational.ArgendataUser;
import argendata.util.DataSession;
import argendata.util.Properties;
import argendata.web.command.ForgetPasswordForm;
import argendata.web.command.SearchForm;

@Component
public class HeaderFilter extends DelegatingFilterProxy {

	private Properties properties;
	private DataSession dataSession;
	
	@Autowired
	public HeaderFilter(Properties properties, DataSession dataSession){
		this.properties = properties;
		this.dataSession = dataSession;
	}
	
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		HttpSession session = req.getSession();
		ArgendataUser user = (ArgendataUser) session.getAttribute("user");
		
		req.setAttribute("logged", user != null);
		
		if(user!=null){
			req.setAttribute("username", user.getUsername());
		}
		
		String login_fail = req.getParameter("login_fail");
		String login_error = "";
		if (login_fail != null && login_fail.equals("true")) {
			login_error = "Username and password do not match";
		}
		req.setAttribute("login_error", login_error);

		
		req.setAttribute("forgetPasswordForm", new ForgetPasswordForm());
		req.setAttribute("searchForm", new SearchForm());
		req.setAttribute("mainUrl", properties.getMainURL() );

		req.setAttribute("datasetsQty", dataSession.getDatasetsQty());
		req.setAttribute("appsQty", dataSession.getAppsQty());
		req.setAttribute("usersQty", dataSession.getUsersQty());
		req.setAttribute("projectName", this.properties.getProjectName());
		
		chain.doFilter(req, response);
		
		
	}

	public void destroy() {
	}

}
