/*
 * Copyright (c) 2013 ChargeBee Inc
 * All Rights Reserved.
 */
package com.chargebee.sample1;

import com.chargebee.sample1.util.JsonElem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

public class Hello extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
             throws IOException, ServletException {
        JsonElem cust = JsonElem.loadCustomer(request);
        if(cust == null) { // customer not found..
            response.sendError(404, "Customer not present");
            return;
        }
        System.out.println(">>>>>" + cust);
        request.setAttribute("cust", cust);
        request.getRequestDispatcher("/jsp/hello.jsp").forward(request, response);
//        PrintWriter out = response.getWriter();
//        out.println("<html>");
//        out.println("<p>Hello-2!! </p>");
//        out.println("</html>");
    }
}
