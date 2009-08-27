<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<sql:query var="rsSalesOrder" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
		SELECT 	sc.sales_order_id
					, sc.sales_order_tracking_id
					, sc.order_date 
					, sc.description
					, CONCAT( LA.ACCOUNT_CODE_PREFIX, ' ', LA.ACCOUNT_CODE_POSTFIX, ' ', LA.TITLE  ) AS  customer_name
					, os.sales_order_status					
		FROM 		  sales_orders sc
		INNER JOIN 	ledger_accounts LA ON sc.CUSTOMER_LEDGER_ACCOUNT_ID=LA.LEDGER_ACCOUNT_ID 
		INNER JOIN  sales_order_status os ON os.sales_order_status_id=sc.sales_order_status_id
		AND sc.sales_order_id='<%= request.getParameter("salesOrderId") %>'
</sql:query>
<div>

	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;">Sales Order Process</span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:690;">&nbsp; <a href="<html:rewrite page='/SalesOrder/CreateInvoice.do?'/>salesOrderId=<%= request.getParameter("salesOrderId") %>&hasFormInitialized=N">Sales Invoice</a> | <a href="<html:rewrite page='/SalesOrder/MoveInInventory.do?'/>salesOrderId=<%= request.getParameter("salesOrderId") %>" onclick="return confirm('Are you sure you want to move in inventory.');">Move In Inventory</a></span>
</div>
<div class="BOX" style="width:950;">
	<c:forEach var="rsSalesOrder" items="${rsSalesOrder.rows}">
		<table width="900" class="FORM_AREA">
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
				<td width="150" class="FORM_CAPTION">Order Tracking ID </td>
				<td width="750" class="FORM_CONTROL"><c:out value="${rsSalesOrder.sales_order_tracking_id}"/></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Customer Name</td>
				<td class="FORM_CONTROL"><c:out value="${rsSalesOrder.customer_name}"/></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Order Description</td>
				<td class="FORM_CONTROL"><c:out value="${rsSalesOrder.description}"/></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Order Status</td>
				<td class="FORM_CONTROL"><c:out value="${rsSalesOrder.sales_order_status}"/></td>
			</tr>
		</table>
		<br>
		<sql:query var="rsSalesOrderProcesses" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
			SELECT 	  OP.SALES_ORDER_PROCESS_ID
					, OP.PROCESS_START_DATE  
					, OP.PROCESS_END_DATE  
					, OP.PROCESS_CREATED  
					, VT.vendor_type_name AS sales_order_process_type 
					, CONCAT( LAV.ACCOUNT_CODE_PREFIX, ' ',LAV.ACCOUNT_CODE_POSTFIX, ' ', LAV.title ) AS vendor_name
					, PS.SALES_ORDER_PROCESS_STATUS_ID
					, PS.SALES_ORDER_PROCESS_STATUS
					, IT.ITEM_NAME AS JEWELLERY_ITEM_NAME
			FROM 			sales_order_processes OP
			LEFT OUTER JOIN	vendor_types VT 				ON OP.SALES_ORDER_PROCESS_TYPE_ID=VT.VENDOR_TYPE_ID
			LEFT OUTER JOIN sales_order_items OI 			ON OI.SALES_ORDER_ITEM_ID=OP.SALES_ORDER_ITEM_ID
			LEFT OUTER JOIN items IT 						ON  OI.JEWELLERY_ITEM_ID=IT.ITEM_ID 
			LEFT OUTER JOIN	ledger_accounts LAV 			ON LAV.LEDGER_ACCOUNT_ID=OP.VENDOR_LEDGER_ACCOUNT_ID
			LEFT OUTER JOIN	vendors VN 						ON LAV.LEDGER_ACCOUNT_ID=VN.LEDGER_ACCOUNT_ID
			LEFT OUTER JOIN	sales_order_process_status PS 	ON PS.SALES_ORDER_PROCESS_STATUS_ID=OP.SALES_ORDER_PROCESS_STATUS_ID
			WHERE 		op.sales_order_id=<%= request.getParameter("salesOrderId") %>
			ORDER BY IT.ITEM_NAME, PS.SALES_ORDER_PROCESS_STATUS_ID, OP.SALES_ORDER_PROCESS_ID
		</sql:query>
		<sql:query var="rsSalesOrderItem" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
			SELECT	 	 IT.ITEM_ID
						,IT.ITEM_NAME
						,OI.SALES_ORDER_ITEM_ID
			FROM     	sales_order_item_info OII 
			INNER JOIN 	sales_order_items OI ON OI.SALES_ORDER_ITEM_ID=OII.SALES_ORDER_ITEM_ID
			INNER JOIN 	items IT ON  OI.JEWELLERY_ITEM_ID=IT.ITEM_ID 
			WHERE 		OII.SALES_ORDER_ID=<%= request.getParameter("salesOrderId") %>

		</sql:query>
		<table class="LIST_AREA" width="920"  cellpadding="0" cellspacing="1">
			<tr>
				<td colspan="3" align="left" valign="baseline">Order Processes</td>
				<td colspan="4" align="right">
					<c:if test="${rsSalesOrderItem.rowCount > 0}">
						<form action="<html:rewrite page='/SalesOrder/CreateProcess.do'/>" method="post">
						  <input type="hidden" name="salesOrderId" value="<%= request.getParameter("salesOrderId") %>">  
						  <input type="hidden" name="hasFormInitialized" value="Y">
						  	<select name="salesOrderItemId">
				  				<c:forEach var="rsSalesOrderItem" items="${rsSalesOrderItem.rows}">
									<option value="<c:out value='${rsSalesOrderItem.SALES_ORDER_ITEM_ID}' />"><c:out value='${rsSalesOrderItem.ITEM_NAME}' /></option>
								</c:forEach>
							</select>
						  	<select name="salesOrderProcessTypeId">
							  <c:forEach var="lookup" items="${applicationScope.lookupVendorType}" varStatus="status" >
									<option value="<c:out value='${lookup.id}' />"><c:out value='${lookup.label}' /></option>
								</c:forEach>
							</select>
						  <input type="submit" value="New Process">
						 </form>
					 </c:if>
				</td>
			</tr>
			<tr>
				<td class="LIST_HEADER" width="150" rowspan="2" align="center">Items</td>
				<td class="LIST_HEADER" colspan="4" align="center">Process </td>
				<td class="LIST_HEADER" width="220" rowspan="2" align="center">Vendor</td>
				<td class="LIST_HEADER" width="180" rowspan="2" align="center">Action</td>
			</tr>
			<tr>
				<td class="LIST_HEADER" width="100" align="center">Type</td>
				<td class="LIST_HEADER" width="80" align="center">Start </td>
				<td class="LIST_HEADER" width="80" align="center">Complete</td>
				<td class="LIST_HEADER" width="80" align="center">Status</td>
			</tr>
		</table>
	
		<div style="overflow:auto; width:920px; height:265px; padding:0px; margin:0px">
			<table class="LIST_AREA" width="900"  cellpadding="0" cellspacing="1">
		    	<COL WIDTH=150><COL WIDTH=100><COL WIDTH=80><COL WIDTH=80><COL WIDTH=80><COL WIDTH=220><COL WIDTH=160>
				<c:forEach var="rsSalesOrderProcesses" items="${rsSalesOrderProcesses.rows}">
					
					<tr class="LIST_DATA" onmouseover="this.className='HIGHLIGHT_DATA';" onmouseout="this.className='LIST_DATA'">
						<td><c:out value="${rsSalesOrderProcesses.JEWELLERY_ITEM_NAME}"/></td>
						<td><c:out value="${rsSalesOrderProcesses.sales_order_process_type}"/></td>
						<td><c:out value="${rsSalesOrderProcesses.process_start_date}"/></td>
						<td><c:out value="${rsSalesOrderProcesses.process_end_date}"/></td>
						<td><c:out value="${rsSalesOrderProcesses.sales_order_process_status}"/></td>
						<td><c:out value="${rsSalesOrderProcesses.vendor_name}"/></td>
						<td>
							  <a href="<html:rewrite page='/SalesOrder/UpdateProcessInit.do?'/>salesOrderProcessId=<c:out value='${rsSalesOrderProcesses.sales_order_process_id}'/>&salesOrderId=<%= request.getParameter("salesOrderId") %>&hasFormInitialized=N">Update</a> 
							| <a href="<html:rewrite page='/SalesOrder/DeleteProcessCart.do?'/>salesOrderProcessId=<c:out value='${rsSalesOrderProcesses.sales_order_process_id}'/>&salesOrderId=<%= request.getParameter("salesOrderId") %>" onclick="return confirm('Are you sure you want to delete.');" >Delete</a> 
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:forEach>
</div>
