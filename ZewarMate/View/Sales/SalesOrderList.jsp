<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import=" java.util.*
				, com.netxs.Zewar.Lookup.*;"%>

<%  String salesOrderTrackingId = request.getParameter("salesOrderTrackingId")!=null ? request.getParameter("salesOrderTrackingId") : "";
	String customerLedgerAccountId = request.getParameter("customerLedgerAccountId")!=null ? request.getParameter("customerLedgerAccountId") : "";
	String orderItemId = request.getParameter("orderItemId")!=null ? request.getParameter("orderItemId") : "";
	String salesOrderProcessTypeId = request.getParameter("salesOrderProcessTypeId")!=null ? request.getParameter("salesOrderProcessTypeId") : "";
	String salesOrderStatusId = request.getParameter("salesOrderStatusId")!=null ? request.getParameter("salesOrderStatusId") : "";
%> 

<div><span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE">Sales Orders</span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
<div class="BOX" style="width:920;">
<form action="../SalesOrder/ListOrder.do" method="post">
	<table class="FORM_AREA" width="900"  cellpadding="0" cellspacing="1">
		<logic:messagesPresent >
			<tr>
				<td colspan="2">
					<ul>
			    	<html:messages id="error">
		    	  	<li><c:out value="${error}" /></li>
			    	</html:messages>
		    	</ul>
				</td> 
			</tr>
		</logic:messagesPresent>
		<tr>
			<td class="FORM_CAPTION" width="150">Order Id</td>
			<td class="FORM_CONTROL" width="750"><input type="text" name="salesOrderTrackingId" value="<%=salesOrderTrackingId%>"></td>
		</tr>
		<tr>
			<td class="FORM_CAPTION">Customer</td>
			<td class="FORM_CONTROL">
				<select name="customerLedgerAccountId" > 
				  <option value="">All</option>
				  <c:forEach var="lookup" items="${applicationScope.lookupCustomer}" varStatus="status" >
					 <c:choose>
				        <c:when test='${param.customerLedgerAccountId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
				        <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
					 </c:choose>
				  </c:forEach>
				</select></td>
		</tr>
		<tr>
			<td class="FORM_CAPTION">Order Item</td>
			<td class="FORM_CONTROL">
				<select name="orderItemId">
				  <option value="">All</option>
				  <c:forEach var="lookup" items="${applicationScope.lookupItemJewellery}" varStatus="status" >
					  <c:choose>
				        <c:when test='${param.orderItemId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
				        <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
					 </c:choose>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="FORM_CAPTION">Process</td>
			<td class="FORM_CONTROL">
				<select name="salesOrderProcessTypeId">
				  <option value="">All</option>
				  <c:forEach var="lookup" items="${applicationScope.lookupVendorType}" varStatus="status" >
					<c:choose>
				        <c:when test='${param.salesOrderProcessTypeId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
				        <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
					 </c:choose>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="FORM_CAPTION">Order Status</td>
			<td class="FORM_CONTROL">
				<select name="salesOrderStatusId">
				  <option value="">All</option>
				  <c:forEach var="lookup" items="${applicationScope.lookupSalesOrderStatus}" varStatus="status" >
					 <c:choose>
				        <c:when test='${param.salesOrderStatusId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
				        <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
					 </c:choose>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="submit"  value=" Search " ></td>
		</tr>
	</table>
	</form>

	<sql:query var="rsSalesOrders" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
		SELECT 	  SC.SALES_ORDER_ID
				, SC.ORDER_DATE 
				, SC.SALES_ORDER_TRACKING_ID
				, CONCAT( LA.ACCOUNT_CODE_PREFIX, ' ', LA.ACCOUNT_CODE_POSTFIX, ' ', LA.TITLE  ) AS  customer_name
				, OS.SALES_ORDER_STATUS_ID
				, OS.SALES_ORDER_STATUS
		FROM 	  sales_orders SC
		INNER JOIN 	ledger_accounts LA ON SC.CUSTOMER_LEDGER_ACCOUNT_ID=LA.LEDGER_ACCOUNT_ID 
		INNER JOIN  sales_order_status OS ON OS.SALES_ORDER_STATUS_ID=SC.SALES_ORDER_STATUS_ID
		WHERE 	SC.ORDER_CREATED='1'
		<%= (salesOrderTrackingId.length()>0 ? "AND SC.SALES_ORDER_TRACKING_ID='"+salesOrderTrackingId+"'" : "")%>
		<%= (customerLedgerAccountId.length()>0 ? "AND LA.LEDGER_ACCOUNT_ID ='"+customerLedgerAccountId+"'" : "")%>
		<%= (salesOrderStatusId.length()>0 ? "AND SC.SALES_ORDER_STATUS_ID='"+salesOrderStatusId+"'" : "")%>
		ORDER BY LA.TITLE
	</sql:query>
	<!-- 
	<%= (salesOrderProcessTypeId.length()>0 ? "AND SC.sales_order_process_type_id='"+salesOrderProcessTypeId+"'" : "")%>
	<%= (orderItemId.length()>0 ? "AND SC.order_item_id='"+orderItemId+"'" : "")%>
	 -->

	<div class="LIST_AREA">Confirmed Orders</div>
	<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="900">
		<tr>
			<td class="LIST_HEADER" width="100">Order Id</td>
			<td class="LIST_HEADER" width="270">Customer Name</td>
			<td class="LIST_HEADER" width="80">Order Date</td>
			<td class="LIST_HEADER" width="100">Status</td>
			<td class="LIST_HEADER" width="350">Action</td>
		</tr>
	</table>
	<div style="overflow:auto; width:900px; height:150px; padding:0px; margin:0px">
	 <table class="LIST_AREA" width="880"  cellpadding="0" cellspacing="1">
		<COL WIDTH=100><COL WIDTH=270><COL WIDTH=80><COL WIDTH=100><COL WIDTH=330>
		<c:forEach var="rsSalesOrders" items="${rsSalesOrders.rows}">
			<tr class="LIST_DATA" onmouseover="this.className='HIGHLIGHT_DATA';" onmouseout="this.className='LIST_DATA'">
				<td><c:out value="${rsSalesOrders.sales_order_tracking_id}"/></td>
				<td><c:out value="${rsSalesOrders.customer_name}"/></td>
				<td><c:out value="${rsSalesOrders.order_date}"/></td>
				<td><c:out value="${rsSalesOrders.sales_order_status}"/></td>
				<td>
				   <a href="<html:rewrite page='/SalesOrder/UpdateOrder.do?'/>salesOrderId=<c:out value='${rsSalesOrders.sales_order_id}'/>">Update</a> 
				 | <a href="<html:rewrite page='/SalesOrder/ListOrderProcess.do?'/>salesOrderId=<c:out value='${rsSalesOrders.sales_order_id}'/>">Processes</a> 
				 | <a href="<html:rewrite page='/SalesOrder/CreateOrderCart.do?'/>salesOrderId=<c:out value='${rsSalesOrders.sales_order_id}'/>&cloneType=C">Copy</a>
				 | <a href="<html:rewrite page='/SalesOrder/CreateCancellationReceipt.do?'/>salesOrderId=<c:out value='${rsSalesOrders.sales_order_id}'/>">Cancel</a>
				 | <a href="<html:rewrite page='/SalesOrder/CreateOrderReOpen.do?'/>cloneFromSalesOrderId=<c:out value='${rsSalesOrders.sales_order_id}'/>&cloneType=R">Re-Open</a> </td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<br>
	
	<sql:query var="rsSalesOrderCart" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
		SELECT 	SC.SALES_ORDER_ID
			  , SC.ORDER_DATE 
			  , CONCAT( LA.ACCOUNT_CODE_PREFIX, ' ', LA.ACCOUNT_CODE_POSTFIX, ' ', LA.TITLE  ) AS  CUSTOMER_NAME
		FROM    sales_orders SC
		INNER JOIN 	ledger_accounts LA ON SC.CUSTOMER_LEDGER_ACCOUNT_ID=LA.LEDGER_ACCOUNT_ID 
		WHERE 	SC.ORDER_CREATED='0'
	 	ORDER BY LA.TITLE
	</sql:query>
	<div class="LIST_AREA">Saved Orders</div>
	<table class="LIST_AREA" width="900"  cellpadding="0" cellspacing="1">
		<tr>
			<td class="LIST_HEADER" width="550">Customer Name</td>
			<td class="LIST_HEADER" width="350">Action</td>
		</tr>
	</table>
	 <div style="overflow:auto; width:900px; height:150px; padding:0px; margin:0px">
	  <table class="LIST_AREA" width="880"  cellpadding="0" cellspacing="1">
	     <COL WIDTH=550><COL WIDTH=330>
		 <c:forEach var="rsSalesOrderCart" items="${rsSalesOrderCart.rows}">
			<tr class="LIST_DATA" onmouseover="this.className='HIGHLIGHT_DATA';" onmouseout="this.className='LIST_DATA'">
				<td class="LIST_DATA"><c:out value="${rsSalesOrderCart.customer_name}"/></td>
				<td class="LIST_DATA">
				   <a href="<html:rewrite page='/SalesOrder/CreateOrderCart.do?'/>salesOrderId=<c:out value="${rsSalesOrderCart.sales_order_id}"/>">Update</a>
				 | <a onclick="return confirm('Are you sure you want to delete.');" href="<html:rewrite page='/SalesOrder/DeleteOrder.do?'/>salesOrderId=<c:out value="${rsSalesOrderCart.sales_order_id}"/>"> Delete</a>
				</td>
			</tr>
		 </c:forEach>
	  </table>
</div>
