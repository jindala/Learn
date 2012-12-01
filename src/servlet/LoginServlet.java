package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import Controller.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		
		Map userInfoMap = user.getUserInfo(request.getParameter("email"));
		System.out.println("userInfoMap = " + userInfoMap);
		
		String contextPath = request.getContextPath();
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(response.encodeRedirectURL(contextPath) + "/home");
		
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");   
		JSONObject userInfo = (JSONObject)userInfoMap;
		System.out.println("userInfo json = " + userInfo);
		
		PrintWriter out = response.getWriter();
		out.print(userInfo);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("login user: " + request.getParameterMap());
		
		User newUser = new User();
		newUser.saveUserInfo(request);
		
		String contextPath = request.getContextPath();
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(response.encodeRedirectURL(contextPath) + "/home");
	}

}
