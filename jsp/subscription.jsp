


<%@page import="org.json.JSONObject"%>
<%
    JSONObject subInfo = (JSONObject) request.getAttribute("subInfo");
%>

<div style="width: 20%;display: inline-block;">
    
</div>
<div style="display: inline-block;">
<h4>Subscription details</h4>

<p>Name : <%=subInfo.getString("plan") %></p>
<p>Current State :<%=subInfo.getString("state")%> </p>
<p>Price : <%=subInfo.getString("price")%></p>
<%if(subInfo.has("trialEnd")){%>
<p>Trials Ends On : <%=subInfo.getString("trialEnd") %></p>
<%}else if (subInfo.has("cancelledAt")){%>
<p>Canceled On :<%=subInfo.getString("cancelledAt") %>
<%}else {%>
<p>Next Billing On :<%=subInfo.getString("nextBilling")%></p>
<%}%>
<div>
    

    
</div>
</div>