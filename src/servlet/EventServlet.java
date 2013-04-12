package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
		System.out.println("search event: " + request.getParameterMap().toString());
		String searchBy = request.getParameter("searchBy");
		String id = request.getParameter("id");
		JSONObject eventMap;
		if(id != null)
			eventMap = event.getEvent(id);
		else if(searchBy.equals("zip"))
			eventMap = event.getEventsByZip(request.getParameter("zip"));
		else if(searchBy.equals("cuisine"))
			eventMap = event.getEventsByCuisine(request.getParameter("cuisine"));
		else if(searchBy.equals("zip_cuisine"))
			eventMap = event.getEventsByCuisineAndZip(request.getParameter("cuisine"), request.getParameter("zip"));
		else
			eventMap = event.getEvent(request.getParameter("event_id"));
		response.setStatus(HttpServletResponse.SC_OK);
				
		System.out.println("event map json object = " +eventMap);
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json"); 
		
		PrintWriter out = response.getWriter();
		out.print(eventMap);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("save new event: " + request.getParameterMap());
		
		Event newEvent = new Event();
		String eventId = newEvent.saveEvent(request);
		if(eventId == null)
		{
			throw new ServletException("event saving failed");
		}
		String contextPath = request.getContextPath();
		response.setStatus(HttpServletResponse.SC_OK);	
		response.sendRedirect(response.encodeRedirectURL(contextPath) + "/meal.html?id=" + eventId);
	}

}