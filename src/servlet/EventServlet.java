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
import Controller.User;

/**
 * Servlet implementation class FeebackServlet
 */
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Event event = new Event();
		
		String searchBy = request.getParameter("searchBy");
		Map eventMap;
		if(searchBy.equals("zip"))
			eventMap = event.getEventsByZip(request.getParameter("zip"));
		else if(searchBy.equals("cuisine"))
			eventMap = event.getEventsByCuisine(request.getParameter("cuisine"));
		else if(searchBy.equals("zip_cuisine"))
			eventMap = event.getEventsByCuisineAndZip(request.getParameter("cuisine"), request.getParameter("zip"));
		else
			eventMap = event.getEvent(request.getParameter("event_id"));
		response.setStatus(HttpServletResponse.SC_OK);
		
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");   
		JSONObject events = (JSONObject)eventMap;
		System.out.println("eventInfo json = " + events);
		
		PrintWriter out = response.getWriter();
		out.print(events);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("save new event: " + request.getParameterMap());
		
		Event newEvent = new Event();
		boolean eventSaved = newEvent.saveEvent(request);
		if(!eventSaved)
		{
			throw new ServletException("event saving failed");
		}
		String contextPath = request.getContextPath();
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(response.encodeRedirectURL(contextPath) + "/eventConfirm");
		
	}

}