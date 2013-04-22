package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Event;
import Controller.User;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("save new user");
		
		User newUser = new User();
		String userId = newUser.saveUserInfo(request);
		if(userId == null)
		{
			throw new ServletException("user registration failed");
		}
		String contextPath = request.getContextPath();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Set-Cookie", "socialfooduid=" + userId+ "; path=/Learn");
		
		response.sendRedirect(response.encodeRedirectURL(contextPath) + "/index.html");
	}

}
