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

import Controller.User;

/**
 * Registration Action Handler
 * @author Ganesh Guttikonda
 *
 */
public class CRegistrationHandler extends Action {

    /**
     * 
     */
    public CRegistrationHandler() {
    }
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException
    {
        System.out.println("Start registration");
        
        User newUser = new User();
        String userId = newUser.saveUserInfo(request);
        if(userId == null)
        {
            return (mapping.findForward("failure"));
        }

        Cookie loginCookie = new Cookie("name", request.getParameter("name") );
        Cookie uidCookie = new Cookie("socialfooduid", userId );
        loginCookie.setMaxAge(-1);
        uidCookie.setMaxAge(-1);
        
        response.addCookie(loginCookie);
        response.addCookie(uidCookie);
        
        System.out.println("End registration");
        return (mapping.findForward("success"));
    }
    

}// end of class
