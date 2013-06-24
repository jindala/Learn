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
import org.json.simple.JSONObject;

import Controller.Feedback;

public class CFeedbackHandler extends Action{

	/**
     * 
     */
    public CFeedbackHandler() {
    }
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException
    {
    	System.out.println("Start feedback handler");
    	
    	if(request.getMethod().equalsIgnoreCase("POST")){
    		return doPost(mapping, form, request, response);
    	} else if (request.getMethod().equalsIgnoreCase("GET")){
    		return doGet(mapping, form, request, response);
    	}
		throw new ServletException("Unsupported method");
    }
    
    private ActionForward doPost(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException
    {
    	System.out.println("Save new feedback");
    	
    	Feedback newfeedback = new Feedback();
		String feedbackID = newfeedback.saveFeedback(request);
		
		if(feedbackID == null){
			return mapping.findForward("failure");
		}
		
		System.out.println("End posting feedback");
		
		PrintWriter out = response.getWriter();
        out.print("Success");
        out.flush();
        return null;
    }
    
    private ActionForward doGet(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException
    {
    	JSONObject feedbackMap;
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
		
		System.out.println("feedback JSON info = " + feedbackMap);
		
		PrintWriter out = response.getWriter();
		out.print(feedbackMap);
		return null;
    }
}
