package com.socialfood.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
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
        String contextPath = request.getContextPath();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Set-Cookie", "socialfooduid=" + userId+ "; path=/Learn");
        
        System.out.println("End registration");
        return (mapping.findForward("success"));
    }
    

}// end of class
