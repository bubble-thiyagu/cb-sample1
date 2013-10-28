/*
 * Copyright (c) 2013 ChargeBee Inc
 * All Rights Reserved.
 */
package com.chargebee.sample1.account;

import com.chargebee.sample1.util.*;
import com.chargebee.Environment;
import com.chargebee.Result;
import com.chargebee.models.Customer;
import com.chargebee.models.Plan;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author chargebee-ajit
 */
public class Subscription extends HttpServlet {

      private Result result;
    private com.chargebee.models.Subscription subscription;
    private Customer customer;
    private Plan plan;
    private JSONObject subInfo;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        Environment.configure("cbprod3-test","SWVigRZ3cu0fzTGN6wRbjcuZNfCPKM5aGO");
        JsonElem cust = JsonElem.loadCustomer(request);
        if(cust == null) { // customer not found..
            response.sendError(404, "Customer not present");
            return;
        }
        result = com.chargebee.models.Subscription.retrieve("A11").request();
        subscription = result.subscription();
        customer = result.customer();
        plan = Plan.retrieve(subscription.planId()).request().plan();
        subData();
        request.setAttribute("subInfo", subInfo);
        request.getRequestDispatcher("/jsp/subscription.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          try {
              processRequest(request, response);
          } catch (Exception ex) {
              Logger.getLogger(Subscription.class.getName()).log(Level.SEVERE, null, ex);
          }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          try {
              processRequest(request, response);
          } catch (Exception ex) {
              Logger.getLogger(Subscription.class.getName()).log(Level.SEVERE, null, ex);
          }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    private void subData() throws Exception {
        subInfo = new JSONObject();
        subInfo.put("plan", plan.name());
        subInfo.put("quantity", subscription.planQuantity());
        subInfo.put("state", subscription.status().name());
        subInfo.put("price", Utility.money(plan.price()));
        if (subscription.status().equals(com.chargebee.models.Subscription.Status.IN_TRIAL)) {
            subInfo.put("trialEnd", Utility.prettyDate(subscription.trialEnd()));
        } else if (subscription.status().equals(com.chargebee.models.Subscription.Status.CANCELLED)) {
            subInfo.put("cancelledAt", Utility.prettyDate(subscription.cancelledAt()));
        } else {
            subInfo.put("nextBilling", Utility.prettyDate(subscription.currentTermEnd()));
        }

    }
}
