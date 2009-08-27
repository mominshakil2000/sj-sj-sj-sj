<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import=" java.util.*
				, com.netxs.Zewar.Lookup.*;"
%>
 
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

<c:forEach var="rsSalesOrder" items="${rsSalesOrder.rows}">
	 <c:set var="salesOrderTarckingId" value="${rsSalesOrder.sales_order_tracking_id}" scope="page" />
	 <c:set var="orderDescription" value="${rsSalesOrder.description}" scope="page" />
	 <c:set var="customerName" value="${rsSalesOrder.customer_name}" scope="page" />
</c:forEach>
 
<sql:query var="rsSalesOrderProcess" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
 	  SELECT 	    vt.vendor_type_id 
				  , vt.vendor_type_name AS process_type
		FROM 		sales_order_processes sp
		INNER JOIN 	vendor_types vt ON vt.vendor_type_id=sp.sales_order_process_type_id
		WHERE 		sp.sales_order_process_id=<bean:write name="salesOrderProcessForm" property="salesOrderProcessId" />
</sql:query> 

<c:set var="vendorTypeId" value="0" scope="page" />
<c:set var="processType" value="0" scope="page" />

<c:forEach var="rsSalesOrderProcess" items="${rsSalesOrderProcess.rows}" >
	 <c:set var="vendorTypeId" value="${rsSalesOrderProcess.vendor_type_id}" scope="page" />
	 <c:set var="processType" value="${rsSalesOrderProcess.process_type}" scope="page" />
</c:forEach>

<% pageContext.setAttribute("lookupVendor", LookupVendor.getListByType(pageContext.getAttribute("vendorTypeId").toString())); %>

<div>
	<span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE" style="width:250;">Sales Order Process</span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:660;">&nbsp; <a href="<html:rewrite page='/SalesOrder/ListOrderProcess.do?'/>salesOrderId=<bean:write name='salesOrderProcessForm' property='salesOrderId' />">Sales Order</a></span>
</div>
 
<div class="BOX" style="width:920;">

 <html:form action="/SalesOrder/UpdateProcess">
	 <logic:messagesPresent >
	 	 <div class="FORM_AREA">
		 <ul>
	   	<html:messages id="error">
	 	 		<li><c:out value="${error}" /></li>
		 	</html:messages>
	 	 </ul>
	 	  </div>
	 </logic:messagesPresent>		


	<html:hidden property="salesOrderProcessId" />
	<html:hidden property="salesOrderId" />
	<html:hidden property="hasFormInitialized" />
	<nested:hidden property="salesOrderItemId" />
	<nested:hidden property="labourLedgerEntryId" />
	<nested:hidden property="insertableSubSectionRow" />
	<nested:hidden property="subSectionRowId" />
	<nested:hidden property="deleteVendorGemIssueIds" />
	<nested:hidden property="deleteVendorGemReturnIds" />
	<nested:hidden property="deleteGemLabourIds" />
	<nested:hidden property="deleteMetalUsedIds" />
	<nested:hidden property="deleteMetalItemUsedIds" />
  
	<div class="FORM_AREA" align="right">Close <b>Process</b>? <html:checkbox property="salesOrderProcessStatusId" value="2" /> <html:hidden property="salesOrderProcessStatusId" /> On date <html:text property="processEndDate" readonly="true" size="12" /> <input type="button" name="buttonProcessEndDate" value=" ... ">
		<script type="text/javascript">
			var cal = new Zapatec.Calendar.setup({
					inputField:document.getElementsByName("processEndDate")[0],
					ifFormat:"%Y-%m-%d",
					button:document.getElementsByName("buttonProcessEndDate")[0],
					showsTime:false
			});
		</script>
	</div> 
		<table class="FORM_AREA" width="700"  cellpadding="0" cellspacing="1">
			<tr>
				<td width="200" class="FORM_CAPTION">Order ID </td>
				<td width="500" class="FORM_CONTROL"><c:out  value="${salesOrderTarckingId}" /></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Customer Name</td>
				<td class="FORM_CONTROL"><c:out value="${customerName}"/></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Order Description</td>
				<td class="FORM_CONTROL"><c:out value="${orderDescription}"/></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Item</td>
				<td class="FORM_CONTROL">
					<nested:hidden property="jewelleryItemId"/>
					<nested:select property="jewelleryItemId" disabled="true">
						<html:options collection="lookupItemJewellery"  property="id" labelProperty="label" />
					</nested:select>
				</td>
			</tr>	

			<tr>
				<td class="FORM_CAPTION">Process Type</td>
				<td class="FORM_CONTROL"><c:out value="${processType}"/></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Vendor</td>
				<td class="FORM_CONTROL">
					<html:select property="vendorLedgerAccountId" onchange="updateVendorItemAgreedWastageList(this.value);">
						<html:options collection="lookupVendor"  property="id" labelProperty="label" />
					</html:select></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Process Start Date</td>
				<td class="FORM_CONTROL">
					<html:text property="processStartDate" readonly="true" size="12" />
					<input type="button" name="buttonProcessStartDate" value=" ... ">
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("processStartDate")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonProcessStartDate")[0],
								showsTime:false
						});
					</script>
				</td>
			</tr>		
			<tr>
				<td class="FORM_CAPTION">Expected Delivery Date</td>
				<td class="FORM_CONTROL">
					<nested:text property="vendorDeliveryDate" readonly="true" size="12"/> 
					<input type="button" name="buttonVendorDeliveryDate" value=" ... "></td>
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("vendorDeliveryDate")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonVendorDeliveryDate")[0],
								showsTime:false
						});
					</script>
			</tr>	
			<tr>
				<td class="FORM_CAPTION">Actual Delivery Date</td>
				<td class="FORM_CONTROL">
					<nested:text property="actualDeliveryDate" readonly="true" size="12"/> 
					<input type="button" name="buttonActualDeliveryDate" value=" ... "></td>
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("actualDeliveryDate")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonActualDeliveryDate")[0],
								showsTime:false
						});
					</script>
			</tr>	
			<tr>
				<td class="FORM_CAPTION">Comments</td>
				<td class="FORM_CONTROL">
					<nested:textarea  property="comments" rows="3" cols="45"></nested:textarea>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Lump Sum Labour</td>
				<td class="FORM_CONTROL"><nested:text property="lumsumLabour" size="18" /> </td>
			</tr>	
			<tr>
				<td class="FORM_CAPTION">Issue Body </td>
				<td class="FORM_CONTROL">
					<div style="width:458"> 
						<span style="width:120px;">Weight</span>
					</div>
					<div style="width:458"> 
						<span style="width:120px;">
							<nested:text property="issueBodyWeight" size="10"/> gm
						</span>
					</div>
				</td>
			</tr>
	
			<tr>
				<td class="FORM_CAPTION">Return Body </td>
				<td class="FORM_CONTROL">
					<div style="width:458"> 
						<span style="width:200px;">Metal</span>
						<span style="width:120px;">Weight</span>
						<span style="width:130px;">No of pieces</span>
					</div>
					<div style="width:458"> 
						<span style="width:200px;">
							<nested:select  property="bodyMetalItemId">
								<html:option value="0">&nbsp;</html:option>
								<html:options collection="lookupItemMetal" property="id" labelProperty="label" />
							</nested:select>
						</span>
						<span style="width:120px;">
							<nested:text property="returnBodyWeight" size="10"/> gm
							<nested:hidden property="bodyMetalWeightUnitId"/>
						</span>
						<span style="width:130px;">&nbsp; <nested:text property="returnBodyPieces" size="10"/></span>
						
					</div>
				</td>
					
			</tr>
		</table>
		
		<!-- Vendor Job -- Issued Gems to Vendor -->
		<table class="LIST_AREA" width="940"  cellpadding="0" cellspacing="1">
			<tr>
				<td colspan="3">Gems Issue To Vendor</td>
				<td colspan="3" align="right"><a onclick="javascript:document.forms[0].insertableSubSectionRow.value='A'; document.forms[0].submit();" href="#">Add</a></td>
			</tr>
			<tr>
				<td class="LIST_HEADER" width="30" align="center">&nbsp;</td>
				<td class="LIST_HEADER" width="350" align="center">Gems</td>
				<td class="LIST_HEADER" width="185" align="center">Date</td>
				<td class="LIST_HEADER" width="190" align="center">Weight</td>
				<td class="LIST_HEADER" width="185" align="center">Quantity</td>
			</tr>
			<nested:iterate property="vendorGemIssueList" id="vendorGemIssue" indexId="vendorGemIssueIndex">
				<c:set var="vendorGemIssuePrefix" value="vendorGemIssue[${vendorGemIssueIndex}]"/>
				<input type="hidden" name="<c:out value="${vendorGemIssuePrefix}.salesOrderProcessGemIssueReturnId"/>" value="<nested:write property="salesOrderProcessGemIssueReturnId"/>">
				<input type="hidden" name="<c:out value="${vendorGemIssuePrefix}.insertable"/>" value="<nested:write property="insertable"/>">
				<tr>
					<td class="LIST_DATA" align="center" valign="middle"><a onclick="javascript:document.forms[0].insertableSubSectionRow.value='F';document.forms[0].subSectionRowId.value=<c:out value="${vendorGemIssueIndex}"/>; document.forms[0].submit();" href="#" > D </a> </td>
					<td class="LIST_DATA">
						<select name="<c:out value="${vendorGemIssuePrefix}"/>.itemId" > 
						  <c:forEach var="lookup" items="${applicationScope.lookupItemGem}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.vendorGemIssue.itemId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>
						<select name="<c:out value="${vendorGemIssuePrefix}"/>.itemStockType" > 
						  <c:forEach var="lookup" items="${applicationScope.lookupItemStockType}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.vendorGemIssue.itemStockType == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>
					</td>
					<td class="LIST_DATA" ><input type="text" name="<c:out value='${vendorGemIssuePrefix}.transactionDate'/>" value="<nested:write property="transactionDate"/>" size="12" readonly="readonly">  <input type="button" name="<c:out value='${vendorGemIssuePrefix}.buttonTransactionDate'/>" value=" ... ">
						<script type="text/javascript">
							var cal = new Zapatec.Calendar.setup({
									inputField:document.getElementsByName("<c:out value='${vendorGemIssuePrefix}.transactionDate'/>")[0],
									ifFormat:"%Y-%m-%d",
									button:document.getElementsByName("<c:out value='${vendorGemIssuePrefix}.buttonTransactionDate'/>")[0],
									showsTime:false
							});
						</script>
					</td>
					<td class="LIST_DATA" >
						<input type="text" name="<c:out value="${vendorGemIssuePrefix}.weight"/>" value="<nested:write property="weight"/>" size="9">
						<select name="<c:out value="${vendorGemIssuePrefix}"/>.weightUnitId" > 
						  <c:forEach var="lookup" items="${applicationScope.lookupWeightUnit}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.vendorGemIssue.weightUnitId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>

					</td>
					<td class="LIST_DATA" ><input type="text" name="<c:out value="${vendorGemIssuePrefix}.quantity"/>" value="<nested:write property="quantity"/>" size="9"></td>
				</tr>
			</nested:iterate>
		</table> 

		<!-- Vendor Job -- Returned Gems from Vendor -->
		<table class="LIST_AREA" width="940"  cellpadding="0" cellspacing="1">
			<tr>
				<td colspan="3">Gems Return From Vendor</td>
				<td colspan="3" align="right"><a onclick="javascript:document.forms[0].insertableSubSectionRow.value='B'; document.forms[0].submit();" href="#">Add</a></td>
			</tr>
			<tr>
				<td class="LIST_HEADER" width="30" align="center">&nbsp;</td>
				<td class="LIST_HEADER" width="350" align="center">Gems</td>
				<td class="LIST_HEADER" width="185" align="center">Date</td>
				<td class="LIST_HEADER" width="190" align="center">Weight</td>
				<td class="LIST_HEADER" width="185" align="center">Quantity</td>
			</tr>
			<nested:iterate property="vendorGemReturnList" id="vendorGemReturn" indexId="vendorGemReturnIndex">
				<c:set var="vendorGemReturnPrefix" value="vendorGemReturn[${vendorGemReturnIndex}]"/>
				<input type="hidden" name="<c:out value="${vendorGemReturnPrefix}.salesOrderProcessGemIssueReturnId"/>" value="<nested:write property="salesOrderProcessGemIssueReturnId"/>">
				<input type="hidden" name="<c:out value="${vendorGemReturnPrefix}.insertable"/>" value="<nested:write property="insertable"/>">
				<tr>
					<td class="LIST_DATA" align="center" valign="middle"><a onclick="javascript:document.forms[0].insertableSubSectionRow.value='G';document.forms[0].subSectionRowId.value=<c:out value="${vendorGemReturnIndex}"/>;  document.forms[0].submit();" href="#" > D </a> </td>
					<td class="LIST_DATA">
						<select name="<c:out value="${vendorGemReturnPrefix}"/>.itemId" > 
						  <c:forEach var="lookup" items="${applicationScope.lookupItemGem}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.vendorGemReturn.itemId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>
						<select name="<c:out value="${vendorGemReturnPrefix}"/>.itemStockType" > 
						  <c:forEach var="lookup" items="${applicationScope.lookupItemStockType}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.vendorGemReturn.itemStockType == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>
					</td>
					<td class="LIST_DATA" ><input type="text" name="<c:out value="${vendorGemReturnPrefix}.transactionDate"/>" value="<nested:write property="transactionDate"/>" size="12" readonly="readonly">  <input type="button" name="<c:out value="${vendorGemReturnPrefix}.buttonTransactionDate"/>" value=" ... " >
						<script type="text/javascript">
							var cal = new Zapatec.Calendar.setup({
									inputField:document.getElementsByName("<c:out value='${vendorGemReturnPrefix}.transactionDate'/>")[0],
									ifFormat:"%Y-%m-%d",
									button:document.getElementsByName("<c:out value='${vendorGemReturnPrefix}.buttonTransactionDate'/>")[0],
									showsTime:false
							});
						</script>

					</td>
					<td class="LIST_DATA" >
						<input type="text" name="<c:out value="${vendorGemReturnPrefix}.weight"/>" value="<nested:write property="weight"/>" size="9">
						<select name="<c:out value="${vendorGemReturnPrefix}"/>.weightUnitId" > 
						  <c:forEach var="lookup" items="${applicationScope.lookupWeightUnit}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.vendorGemReturn.weightUnitId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>

					</td>
					<td class="LIST_DATA" ><input type="text" name="<c:out value="${vendorGemReturnPrefix}.quantity"/>" value="<nested:write property="quantity"/>" size="9"></td>
				</tr>
			</nested:iterate>
		</table> 



		<!-- Vendor Job ->  Gems Labour Charges  -->
		<table class="LIST_AREA" width="940"  cellpadding="0" cellspacing="1">
			<tr>
				<td colspan="3">Labour Charges By Gems</td>
				<td colspan="3" align="right" valign="middle"><a onclick="javascript:document.forms[0].insertableSubSectionRow.value='C'; document.forms[0].submit();" href="#">Add</a></td>
			</tr>
			<tr>
				<td class="LIST_HEADER" width="30" align="center" rowspan="2">&nbsp;</td>
				<td class="LIST_HEADER" width="350" align="center" rowspan="2">Rate / Stone</td>
				<td class="LIST_HEADER" align="center" colspan="2">Estimated</td>
				<td class="LIST_HEADER" align="center"  colspan="2">Actual</td>
			</tr>
			<tr>
				<td class="LIST_HEADER" width="185" align="center">Quantity</td>
				<td class="LIST_HEADER" width="190" align="center">Labour</td>
				<td class="LIST_HEADER" width="95" align="center">Quantity </td>
				<td class="LIST_HEADER" width="90" align="center">Labour</td>
			</tr>
			<nested:iterate property="gemLabourList" id="gemLabour" indexId="gemLabourIndex">
				<c:set var="gemLabourPrefix" value="gemLabour[${gemLabourIndex}]"/>
				<input type="hidden" name="<c:out value="${gemLabourPrefix}.salesOrderProcessGemLabourId"/>" value="<nested:write property="salesOrderProcessGemLabourId"/>">
				<input type="hidden" name="<c:out value="${gemLabourPrefix}.insertable"/>" value="<nested:write property="insertable"/>">
					<tr>
						<td class="LIST_DATA" align="center"><a onclick="javascript:document.forms[0].insertableSubSectionRow.value='H';document.forms[0].subSectionRowId.value=<c:out value="${gemLabourIndex}"/>; document.forms[0].submit();" href="#" > D </a> </td>
						<td class="LIST_DATA" ><input type="text" name="<c:out value="${gemLabourPrefix}.settingRate"/>"  size="9" onchange="JavaScript:calculateEstimatedGemsLabourCharges(this); calculateActualGemsLabourCharges(this);" value="<nested:write property="settingRate"/>"></td>
						<td class="LIST_DATA" ><input type="text" name="<c:out value="${gemLabourPrefix}.estimatedQuantity"/>"  size="9" onchange="JavaScript:calculateEstimatedGemsLabourCharges(this)" value="<nested:write property="estimatedQuantity"/>"></td>
						<td class="LIST_DATA" ><input type="text" name="<c:out value="${gemLabourPrefix}.estimatedTotalLabour"/>" value="<nested:write property="estimatedTotalLabour"/>" size="9"></td>
						<td class="LIST_DATA" ><input type="text" name="<c:out value="${gemLabourPrefix}.actualQuantity"/>" value="<nested:write property="actualQuantity"/>" size="9" onchange="JavaScript:calculateActualGemsLabourCharges(this);"></td>
						<td class="LIST_DATA" ><input type="text" name="<c:out value="${gemLabourPrefix}.actualTotalLabour"/>" value="<nested:write property="actualTotalLabour"/>" size="9"></td>
					</tr>
			</nested:iterate>
		</table>
		
		<!-- Vendor Job ->  Metal Used from Vendor Stock  -->
		<table class="LIST_AREA" width="940"  cellpadding="0" cellspacing="1">
			<tr>
				<td colspan="3">Metal Used from Stock (Vendor)</td>
				<td colspan="3" align="right" valign="middle"><a onclick="javascript:document.forms[0].insertableSubSectionRow.value='D'; document.forms[0].submit();" href="#">Add</a></td>
			</tr>
			<tr>
				<td class="LIST_HEADER" rowspan="2" width="30" align="center" >&nbsp;</td>
				<td class="LIST_HEADER" rowspan="2" width="350" align="center">Item</td>
				<td class="LIST_HEADER" rowspan="2" width="185" align="center">Weight</td>
				<td class="LIST_HEADER" colspan="2" align="center">Wastage (+)</td>
				<td class="LIST_HEADER" rowspan="2" width="90" align="center">Net Weight</td>
			</tr>
			<tr>
				<td class="LIST_HEADER" width="190" align="center" >Rate</td>
				<td class="LIST_HEADER" width="95" align="center" >Quantity</td>
			</tr>
			<nested:iterate property="metalUsedList" id="metalUsed" indexId="metalUsedIndex">
				<c:set var="metalUsedPrefix" value="metalUsed[${metalUsedIndex}]"/>
				<input type="hidden" name="<c:out value="${metalUsedPrefix}.salesOrderProcessMetalUsedId"/>" value="<nested:write property="salesOrderProcessMetalUsedId"/>">
				<input type="hidden" name="<c:out value="${metalUsedPrefix}.inventoryMetalEntryIdOut"/>" value="<nested:write property="inventoryMetalEntryIdOut"/>">
				<input type="hidden" name="<c:out value="${metalUsedPrefix}.insertable"/>" value="<nested:write property="insertable"/>">
				<tr>
					<td class="LIST_DATA" align="center"><a onclick="javascript:document.forms[0].insertableSubSectionRow.value='I';document.forms[0].subSectionRowId.value=<c:out value="${metalUsedIndex}"/>; document.forms[0].submit();" href="#" > D </a> </td>
					<td class="LIST_DATA" >
						<select name="<c:out value="${metalUsedPrefix}"/>.itemId"  onchange="Javascript:updateVendorItemAgreedWastage(this);" styleId="METAL_USED_ITEM_ID" > 
						  <c:forEach var="lookup" items="${applicationScope.lookupItemMetal}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.metalUsed.itemId==lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>
					</td>
					<td class="LIST_DATA" >
						<input type="text" name="<c:out value="${metalUsedPrefix}.weight"/>" value="<nested:write property="weight"/>" size="9" onchange="JavaScript:calculateMetalUsedWastageQuantity(this);"> gm
						<input type="hidden" name="<c:out value="${metalUsedPrefix}.weightUnitId"/>" value="<nested:write property="weightUnitId"/>">
					</td>
					<td class="LIST_DATA" >
						<input type="text" name="<c:out value="${metalUsedPrefix}.wastageRate"/>" value="<nested:write property="wastageRate"/>" size="9"  onchange="JavaScript:calculateMetalUsedWastageQuantity(this);">
						<select name="<c:out value="${metalUsedPrefix}"/>.wastageUnitId" onchange="JavaScript:calculateMetalUsedWastageQuantity(this);"> 
						  <c:forEach var="lookup" items="${applicationScope.lookupWastageUnit}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.metalUsed.wastageUnitId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>
					</td>
					<td class="LIST_DATA" ><input type="text" name="<c:out value="${metalUsedPrefix}.wastageQuantity"/>" value="<nested:write property="wastageQuantity"/>" size="9" onchange="JavaScript:calculateMetalUsedNetWeight(this);"></td>
					<td class="LIST_DATA" ><input type="text" name="<c:out value="${metalUsedPrefix}.netWeight"/>" value="<nested:write property="netWeight"/>" size="9"></td>
				</tr>
			</nested:iterate>
			
		</table>				

		<!-- Vendor Job ->  Metal Item Used  from our Stock -->
		<table class="LIST_AREA" width="940"  cellpadding="0" cellspacing="1">
			<tr>
				<td colspan="3">Metal Item Used from Stock (Our)</td>
				<td colspan="4" align="right" valign="middle"><a onclick="javascript:document.forms[0].insertableSubSectionRow.value='E'; document.forms[0].submit();" href="#">Add</a></td>
			</tr>
			<tr>
				<td class="LIST_HEADER" width="30"  align="center" >&nbsp;</td>
				<td class="LIST_HEADER" width="350" align="center">Item</td>
				<td class="LIST_HEADER" width="185" align="center">Issue Weight</td>
				<td class="LIST_HEADER" width="95" align="center">Return Waste</td>
				<td class="LIST_HEADER" width="190" align="center">Wastage (-)</td>
				<td class="LIST_HEADER" width="90" align="center">Net Weight</td>
			</tr>
			<nested:iterate property="metalItemUsedList" id="metalItemUsed" indexId="metalItemUsedIndex">
				<c:set var="metalItemUsedPrefix" value="metalItemUsed[${metalItemUsedIndex}]"/>
				<input type="hidden" name="<c:out value="${metalItemUsedPrefix}.salesOrderProcessMetalItemUsedId"/>" value="<nested:write property="salesOrderProcessMetalItemUsedId"/>">
				<input type="hidden" name="<c:out value="${metalItemUsedPrefix}.inventoryMetalItemEntryIdOut"/>" value="<nested:write property="inventoryMetalItemEntryIdOut"/>">
				<input type="hidden" name="<c:out value="${metalItemUsedPrefix}.insertable"/>" value="<nested:write property="insertable"/>">
				<tr>
					<td class="LIST_DATA" align="center"><a onclick="javascript:document.forms[0].insertableSubSectionRow.value='J';document.forms[0].subSectionRowId.value=<c:out value="${metalItemUsedIndex}"/>; document.forms[0].submit();" href="#" > D </a> </td>
					<td class="LIST_DATA">
						<select name="<c:out value="${metalItemUsedPrefix}"/>.itemIdMetal"> 
						  <c:forEach var="lookup" items="${applicationScope.lookupItemMetal}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.metalItemUsed.itemIdMetal== lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>
						<select name="<c:out value="${metalItemUsedPrefix}"/>.itemIdJewellery"> 
						  <c:forEach var="lookup" items="${applicationScope.lookupItemJewellery}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.metalItemUsed.itemIdJewellery== lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>
					</td>
					<td class="LIST_DATA" >
						<input type="text" name="<c:out value="${metalItemUsedPrefix}.issueItemWeight"/>" value="<nested:write property="issueItemWeight"/>" size="9" onchange="JavaScript:calculateMetalItemUsedNetWeight(this);">
						<input type="hidden" name="<c:out value="${metalItemUsedPrefix}.weightUnitId"/>" value="<nested:write property="weightUnitId"/>"> gm
					</td>
					<td class="LIST_DATA" ><nested:text name="metalItemUsed"  indexed="true" property="returnWasteQuantity" size="9" onchange="JavaScript:calculateMetalItemUsedNetWeight(this);"/></td>							
					<td class="LIST_DATA" >
						<input type="text" name="<c:out value="${metalItemUsedPrefix}.issueItemWastageRate"/>" value="<nested:write property="issueItemWastageRate"/>"  size="4" onchange="JavaScript:calculateMetalItemUsedNetWeight(this);">
						<select name="<c:out value="${metalItemUsedPrefix}"/>.issueItemWastageUnitId" onchange="JavaScript:calculateMetalItemUsedNetWeight(this);"> 
						  <c:forEach var="lookup" items="${applicationScope.lookupWastageUnit}" varStatus="status" >
							 <c:choose>
			        			<c:when test='${pageScope.metalItemUsed.issueItemWastageUnitId== lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
							    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
							 </c:choose>
						  </c:forEach>
						</select>
					</td>
					<td class="LIST_DATA" ><nested:text name="metalItemUsed"  indexed="true" property="issueItemNetWeight" size="8" onchange="JavaScript:calculateMetalItemUsedNetWeight(this);"/> gm</td>
					
				</tr>
			</nested:iterate>
		</table>
		<div align="right"><html:submit value=" Save " /></div>
 </html:form>
</div>


<script language="JavaScript">
<!-- 
	function calculateEstimatedGemsLabourCharges(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));
		
		oSettingRate = document.getElementsByName(oName+".settingRate").item(0);
		oSettingRate.value = Math.abs(oSettingRate.value).toFixed(2);

		oEstimatedQuantity = document.getElementsByName(oName+".estimatedQuantity").item(0);
		oEstimatedQuantity.value = Math.abs(parseInt(oEstimatedQuantity.value));
		oEstimatedTotalLabour = document.getElementsByName(oName+".estimatedTotalLabour").item(0);
		oEstimatedTotalLabour.value = Math.abs(oEstimatedQuantity.value * oSettingRate.value).toFixed(2) ;
	}
	function calculateActualGemsLabourCharges(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));
		
		oSettingRate = document.getElementsByName(oName+".settingRate").item(0);
		oSettingRate.value = Math.abs(oSettingRate.value).toFixed(2);

		oActualQuantity = document.getElementsByName(oName+".actualQuantity").item(0);
		oActualQuantity.value = Math.abs(parseInt(oActualQuantity.value));
		oActualTotalLabour = document.getElementsByName(oName+".actualTotalLabour").item(0);
		oActualTotalLabour.value = Math.abs(oActualQuantity.value * oSettingRate.value).toFixed(2) ;
	}
	function calculateMetalUsedNetWeight(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));
		oWeight = document.getElementsByName(oName+".weight").item(0);
		oWeightUnitId = document.getElementsByName(oName+".weightUnitId").item(0);
		oWastageRate = document.getElementsByName(oName+".wastageRate").item(0);
		oWastageQuantity = document.getElementsByName(oName+".wastageQuantity").item(0);
		oWastageUnitId = document.getElementsByName(oName+".wastageUnitId").item(0);
		oNetWeight = document.getElementsByName(oName+".netWeight").item(0);
		var vWeight = parseFloat(oWeight.value);
		var vWastageRate = parseFloat(oWastageRate.value);
		var vWastageQuantity = parseFloat(oWastageQuantity.value);
		var vNetWeight = parseFloat(oNetWeight.value);
		oNetWeight.value = Math.abs(vWeight + vWastageQuantity).toFixed(3);
	}
	function calculateMetalUsedWastageQuantity(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));
		oWeight = document.getElementsByName(oName+".weight").item(0);
		oWeightUnitId = document.getElementsByName(oName+".weightUnitId").item(0);
		oWastageRate = document.getElementsByName(oName+".wastageRate").item(0);
		oWastageQuantity = document.getElementsByName(oName+".wastageQuantity").item(0);
		oWastageUnitId = document.getElementsByName(oName+".wastageUnitId").item(0);
		oNetWeight = document.getElementsByName(oName+".netWeight").item(0);
		if (oWastageUnitId.value == 1)
			oWastageQuantity.value = Math.abs((oWeight.value * oWastageRate.value)/100).toFixed(3) ;
		else
			oWastageQuantity.value = Math.abs((oWeight.value * oWastageRate.value)/96).toFixed(3) ;

		calculateMetalUsedNetWeight(obj);
	}

	function updateVendorItemAgreedWastage(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));

		oItemId = document.getElementsByName(oName+".itemId").item(0);
		oWastageRate = document.getElementsByName(oName+".wastageRate").item(0);
		oWastageRate.value = getVendorItemAgreedWastage(document.forms[0].vendorLedgerAccountId.value, oItemId.value);
		oWastageUnitId = document.getElementsByName(oName+".wastageUnitId").item(0);
		oWastageUnitId.value = getVendorItemAgreedWastageUnitId(document.forms[0].vendorLedgerAccountId.value, oItemId.value);
		calculateMetalUsedWastageQuantity(obj);
	}
	function updateVendorItemAgreedWastageList (vVendorId) {
		var el_collection=document.all('METAL_USED_ITEM_ID');
		if (el_collection != null){
			el_collection_length  =	eval(el_collection.length)  ? el_collection.length : 1;
			for (i=0;i<el_collection_length;i++) {
				updateVendorItemAgreedWastage(el_collection[i]);
			}
		}
		//return getLookupVendorItemAgreedWastage ( document.forms[0].vendorLedgerAccountId.value, vItemId); 
	}
	


	function calculateMetalChargeCustomerNetWeight(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));
		oWeight = document.getElementsByName(oName+".weight").item(0);
		oWeightUnitId = document.getElementsByName(oName+".weightUnitId").item(0);
		oWastageRate = document.getElementsByName(oName+".wastageRate").item(0);
		oWastageQuantity = document.getElementsByName(oName+".wastageQuantity").item(0);
		oWastageUnitId = document.getElementsByName(oName+".wastageUnitId").item(0);
		oNetWeight = document.getElementsByName(oName+".netWeight").item(0);
		var vWeight = parseFloat(oWeight.value);
		var vWastageRate = parseFloat(oWastageRate.value);
		var vWastageQuantity = parseFloat(oWastageQuantity.value);
		var vNetWeight = parseFloat(oNetWeight.value);
		oNetWeight.value = Math.abs(vWeight + vWastageQuantity).toFixed(3);
	}
	function calculateMetalChargeCustomerWastageQuantity(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));
		oWeight = document.getElementsByName(oName+".weight").item(0);
		oWeightUnitId = document.getElementsByName(oName+".weightUnitId").item(0);
		oWastageRate = document.getElementsByName(oName+".wastageRate").item(0);
		oWastageQuantity = document.getElementsByName(oName+".wastageQuantity").item(0);
		oWastageUnitId = document.getElementsByName(oName+".wastageUnitId").item(0);
		oNetWeight = document.getElementsByName(oName+".netWeight").item(0);
		if (oWastageUnitId.value == 1)
			oWastageQuantity.value = Math.abs((oWeight.value * oWastageRate.value)/100).toFixed(3) ;
		else
			oWastageQuantity.value = Math.abs((oWeight.value * oWastageRate.value)/96).toFixed(3) ;

		calculateMetalChargeCustomerNetWeight(obj);
	}
	

	function calculateMetalItemUsedNetWeight(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));
		oIssueItemWeight = document.getElementsByName(oName+".issueItemWeight").item(0);
		oIssueItemNetWeight = document.getElementsByName(oName+".issueItemNetWeight").item(0);
		oReturnWasteQuantity = document.getElementsByName(oName+".returnWasteQuantity").item(0);
		oWeightUnitId = document.getElementsByName(oName+".weightUnitId").item(0);
		oIssueItemWastageRate = document.getElementsByName(oName+".issueItemWastageRate").item(0);		
		oIssueItemWastageUnitId = document.getElementsByName(oName+".issueItemWastageUnitId").item(0);

		vIssueItemWastageQuantity = 0.0;
		if (oIssueItemWastageUnitId.value == 1) 
			vIssueItemWastageQuantity = ((oIssueItemWeight.value - oReturnWasteQuantity.value )* oIssueItemWastageRate.value)/100 ;
		else 
			vIssueItemWastageQuantity = ((oIssueItemWeight.value - oReturnWasteQuantity.value) * oIssueItemWastageRate.value)/96 ;

		oIssueItemNetWeight.value = parseFloat((oIssueItemWeight.value - oReturnWasteQuantity.value ) - vIssueItemWastageQuantity).toFixed(3);
	}
	
 //-->
</script>


<sql:query var="rsVendorItem" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
	SELECT  * FROM vendor_items_relation ORDER BY vendor_id, item_id
</sql:query>
<script type="text/javascript" language="JavaScript">
<!-- 
	var lookupVendorAgreedWastageList = new Array();
	// lookupVendorAgreedWastageList[X][0:VENDOR_ID]
	// lookupVendorAgreedWastageList[X][1:AGREED_WASTAGE_ITEM_ID]
	// lookupVendorAgreedWastageList[X][2:AGREED_WASTAGE]
	// lookupVendorAgreedWastageList[X][3:AGREED_WASTAGE_UNIT_ID]
	<c:forEach var="rowVendorItem" items="${rsVendorItem.rows}" varStatus="statusVendorItem" >
		lookupVendorAgreedWastageList[<c:out value="${statusVendorItem.index}"/>] = new Array('<c:out value="${rowVendorItem.VENDOR_ID}"/>', '<c:out value="${rowVendorItem.ITEM_ID}"/>', '<fmt:formatNumber minIntegerDigits="1" minFractionDigits="3" value="${rowVendorItem.AGREED_WASTAGE}" />','<c:out value="${rowVendorItem.AGREED_WASTAGE_UNIT_ID}"/>' );
	</c:forEach>
	function getVendorItemAgreedWastage (vVendorId, vItemId) {
		for (var x=0; x < lookupVendorAgreedWastageList.length; x++) {
			if (lookupVendorAgreedWastageList[x][0]==vVendorId && lookupVendorAgreedWastageList[x][1]==vItemId) 
				return lookupVendorAgreedWastageList[x][2];
		}
		return 0.000; 
	}
	function getVendorItemAgreedWastageUnitId (vVendorId, vItemId) {
		for (var x=0; x < lookupVendorAgreedWastageList.length; x++) {
			if (lookupVendorAgreedWastageList[x][0]==vVendorId && lookupVendorAgreedWastageList[x][1]==vItemId) 
				return lookupVendorAgreedWastageList[x][3];
		}
		return 1; 
	}
 //-->
</script>