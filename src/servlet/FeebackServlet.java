package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import Controller.Event;
import Controller.Feedback;

/**
 * Servlet implementation class FeebackServlet
 */
public class FeebackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeebackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map feedbackMap;
		Feedback feedback = new Feedback();
		String searchBy = request.getParameter("searchBy");
		
		//feedback for a customer
		if(searchBy.equals("reviewerEmail"))
			feedbackMap = feedback.getCustomerFeedback(request.getParameter("reviewerEmail"));
		
		//feedback for an organizer by cuisine
		else if(searchBy.equals("organizer_cuisine"))
			feedbackMap = feedback.getOrganizerFeedbackByCuisine(request.getParameter("cuisine"), request.getParameter("revieweeEmail"));

		//feedback for an organizer
		else 
			feedbackMap = feedback.getOrganizerFeedback(request.getParameter("revieweeEmail"));
		
		response.setStatus(HttpServletResponse.SC_OK);		
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json"); 
		
		JSONObject feedbackJSON = (JSONObject)feedbackMap;
		System.out.println("feedback JSON info = " + feedbackJSON);
		
		PrintWriter out = response.getWriter();
		out.print(feedbackJSON);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("save new feedback: " + request.getParameterMap());
		
		Feedback newfeedback = new Feedback();
		newfeedback.saveFeedback(request);
		
		String contextPath = request.getContextPath();  		//get current URL
		response.setStatus(HttpServletResponse.SC_OK);			//success response -- TO-DO: check for exception thrown in case of error on save
		response.sendRedirect(response.encodeRedirectURL(contextPath) + "/");	//To be decided, depending on if you need to redirect or not
	}

}
