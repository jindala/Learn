package com.socialfood.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

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
        System.out.println("Start event registration");
        
        Event newEvent = new Event();
		String eventId = newEvent.saveEvent(request);
		if(eventId == null)
		{
			return (mapping.findForward("failure"));
		}
        
        System.out.println("End event registration");
        
        ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
        redirect.addParameter("id", eventId);
        return redirect;
    }
}
