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

public class CUserHandler extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException
    {
        
        System.out.println("get user");
        User user = new User();
        
        JSONObject userInfoMap = user.getUserInfoFromId(request.getParameter("uid"));
        System.out.println("userInfoMap = " + userInfoMap);
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");        
        
        PrintWriter out = response.getWriter();
        out.print(userInfoMap);
        out.flush();
        return null;
        
    }
}
