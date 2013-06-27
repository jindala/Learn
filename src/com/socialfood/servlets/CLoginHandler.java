package com.socialfood.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONObject;

import Controller.User;

public class CLoginHandler extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException
    {
        
        System.out.println("Start login");
        User user = new User();
        
        JSONObject userInfoMap = user.verifyUserInfo(request.getParameter("email"), request.getParameter("password"));
        System.out.println("userInfoMap = " + userInfoMap);
        if(userInfoMap != null) {      
	        try {
	            org.json.JSONObject myJson = new org.json.JSONObject(userInfoMap.toString());
	            org.json.JSONObject result = myJson.getJSONArray("result").getJSONObject(0) ;
	            Cookie loginCookie = new Cookie("name", result.getString("name") );
	            Cookie uidCookie = new Cookie("socialfooduid", result.getString("unique_id") );
	            loginCookie.setMaxAge(-1);
	            uidCookie.setMaxAge(-1);
	            
	            response.addCookie(loginCookie);
	            response.addCookie(uidCookie);
	        }catch(Exception e){
	            e.printStackTrace();
	            // do something here!
	        }
    	}
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");        
        
        PrintWriter out = response.getWriter();
        out.print(userInfoMap);
        return mapping.findForward("success");
        
    }
}
