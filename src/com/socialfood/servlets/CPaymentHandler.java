package com.socialfood.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import Controller.Event;

public class CPaymentHandler extends Action {

	/**
     * 
     */
    public CPaymentHandler() {
    }
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException
    {
        System.out.println("Start event payment");
        
		Event event = new Event();
		String eventid = event.bookEvent(request);
                
        System.out.println("End event payment");
        
        ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
        redirect.addParameter("id", eventid);
        
        return redirect;
    }
}
