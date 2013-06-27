package com.socialfood.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.json.simple.JSONObject;

import Controller.Event;

/**
 * Event handler
 * @author anupamjindal
 *
 */
public class CEventHandler extends Action{

	/**
     * 
     */
    public CEventHandler() {
    }
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException
    {
    	System.out.println("Start event handler");
        if(request.getMethod().equalsIgnoreCase("POST")) {
            return doPost(mapping, form, request, response);
        }else if(request.getMethod().equalsIgnoreCase("GET")) {
            return doGet(mapping, form, request, response);
        }
        throw new ServletException("Unsupported method");
        
        
    }
    private ActionForward doPost(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException
    {
        System.out.println("Start event registration");
        
        Event newEvent = new Event();
        String eventId = newEvent.saveEvent(request);
        if(eventId == null)
        {
            return (mapping.findForward("failure"));
        }
        
        System.out.println("End event registration " + eventId);
        
        ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
        redirect.addParameter("id", eventId);
        
        return redirect;
    }
    private ActionForward doGet(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException
    {
        
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
        out.flush();
        return null;
    }
    
    
}
