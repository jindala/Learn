package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
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
		
		JSONObject userInfoMap = user.getUserInfo(request.getParameter("email"));
		System.out.println("userInfoMap = " + userInfoMap);
		
		String contextPath = request.getContextPath();
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(response.encodeRedirectURL(contextPath) + "/home");
		
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");   
		System.out.println("userInfo json = " + userInfoMap);
		
		PrintWriter out = response.getWriter();
		out.print(userInfoMap);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		Map<String, String[]> reqParamMap = request.getParameterMap();
		
		String email = reqParamMap.get("email")[0];
		String password = reqParamMap.get("password")[0];
		
		User incomingUser = new User();
		JSONObject userInfo = incomingUser.verifyUserInfo(email, password);
				
		//Form response object
		JSONArray userArray = (JSONArray)userInfo.get("result");
		LinkedHashMap firstUser = (LinkedHashMap) userArray.get(0);
		
		JSONObject responseJson = new JSONObject();
		responseJson.put("name", firstUser.get("name"));
		
		String contextPath = request.getContextPath();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json"); 
		
		if(userInfo != null)
			response.setHeader("Set-Cookie", "socialfooduid=" + firstUser.get("unique_id") + "; path=/Learn");
		
		//PrintWriter out = response.getWriter();
		//out.print(userInfo);
		
		response.sendRedirect(response.encodeRedirectURL(contextPath) + "/index.html");
	}

}
