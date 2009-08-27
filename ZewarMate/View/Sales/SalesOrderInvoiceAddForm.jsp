<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ page import=" java.util.*
				, com.netxs.Zewar.Lookup.*;"%>
 
	<div><span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE">Sales Order Invoice</span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:720;">&nbsp; <a href="<html:rewrite page='/SalesOrder/CancelInvoice.do?'/>salesOrderId=<bean:write name="salesOrderInvoiceForm" property="salesOrderId"/>&salesInvoiceId=<bean:write name="salesOrderInvoiceForm" property="salesInvoiceId"/>" onclick="return confirm('Are you sure you want to delete Sales Invoice.');">Cancel Invoice</a> | <a href="<html:rewrite page='/SalesOrder/ListOrderProcess.do?'/>salesOrderId=<bean:write name="salesOrderInvoiceForm" property="salesOrderId"/>">Sales Order</a></span></div>
	<div class="BOX" style="width:994;">
	  <html:form action="/SalesOrder/CreateInvoice" method="post" styleId="myForm">
	  	<html:hidden property="hasFormInitialized"/>
	  	<html:hidden property="salesOrderId"/>
	  	<html:hidden property="salesInvoiceId"/>
	  	<html:hidden property="ledgerEntryId"/>
	  	<html:hidden property="insertable"/>
	  	<logic:messagesPresent >
			<ul>
	    	<html:messages id="error">
    	  	<li><c:out value="${error}" /></li>
	    	</html:messages>
    	</ul>
		</logic:messagesPresent>
		<table class="FORM_AREA" width="600"  cellpadding="0" cellspacing="1">
			<tr>
				<td class="FORM_CAPTION" width="150">Sales Order Id</td>
				<td class="FORM_CONTROL" width="450"><html:text property="salesOrderTrackingId" size="12" readonly="true"/></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Customer</td>
				<td class="FORM_CONTROL">
					<html:select property="customerLedgerAccountId">
						<c:forEach var="lookup" items="${applicationScope.lookupCustomer}" varStatus="status" >
							<c:if test='${salesOrderInvoiceForm.customerLedgerAccountId == lookup.id}'>
								<option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option>
							</c:if>
						</c:forEach>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Remarks</td>
				<td class="FORM_CONTROL"><html:textarea property="remarks" rows="4" cols="40"></html:textarea></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Date</td>
				<td class="FORM_CONTROL"><html:text property="invoiceDate" maxlength="10" size="12" readonly="true"/> <input type="button" name="buttonInvoiceDate" value=" ... ">
				<script type="text/javascript">
					var cal = new Zapatec.Calendar.setup({
							inputField:document.getElementsByName("invoiceDate")[0],
							ifFormat:"%Y-%m-%d",
							button:document.getElementsByName("buttonInvoiceDate")[0],
							showsTime:false
					});
				</script>
				</td>
			</tr>
		</table>
		<br>
		<table class="LIST_AREA" width="982"  cellpadding="0" cellspacing="0">
			<tr>
				<td class="LIST_HEADER" width="200">Particulars</td>
				<td class="LIST_HEADER" width="100">&nbsp;</td>				
				<td class="LIST_HEADER" width="67">Qty</td>
				<td class="LIST_HEADER" width="120">Weight</td>
				<td class="LIST_HEADER" width="150">Wastage</td>
				<td class="LIST_HEADER" width="90">Net Wgt</td>
				<td class="LIST_HEADER" width="75">Rate</td>
				<td class="LIST_HEADER" width="90">Calculate By</td>
				<td class="LIST_HEADER" width="90">Amount</td>
			</tr>
			
			<tr>
				<td class="LIST_DATA" colspan="9">&nbsp;</td>
			</tr>	
			<nested:iterate property="invoiceItemList" id="invoiceItem" indexId="invoiceItemIndex">
				<tr>
					<td class="LINE_BOTTOM_SINGLE" colspan="9">
					<nested:hidden name="invoiceItem" indexed="true" property="insertable" />
					<nested:hidden name="invoiceItem" indexed="true" property="salesInvoiceItemId" />
					<div class="LIST_DATA"><B><nested:write property="itemName"/><nested:hidden name="invoiceItem" indexed="true" property="itemId"/><nested:hidden name="invoiceItem" indexed="true" property="itemName"/><nested:hidden name="invoiceItem" indexed="true" property="salesOrderItemId"/></B></div></td>
				</tr>
				<!-- Comapny Metals -->
				<nested:iterate property="companyMetalList" id="companyMetal" indexId="companyMetalIndex">
					<c:set var="companyMetalPrefix" value="invoiceItem[${invoiceItemIndex}].companyMetal[${companyMetalIndex}]"/>
					<tr>
						<td class="LIST_DATA" colspan="2">
							<input name="<c:out value="${companyMetalPrefix}.insertable"/>" value="<nested:write property="insertable"/>" type="hidden" >
							<input name="<c:out value="${companyMetalPrefix}.salesInvoiceItemMetalUsedId"/>" value="<nested:write property="salesInvoiceItemMetalUsedId"/>" type="hidden" >
							<nested:write property="itemName"/>
							<input name="<c:out value="${companyMetalPrefix}.itemId"/>" value="<nested:write property="itemId"/>" type="hidden" >
							<input name="<c:out value="${companyMetalPrefix}.itemName"/>" value="<nested:write property="itemName"/>" type="hidden" >
						</td>
						<td class="LIST_DATA">&nbsp;</td>
						<td class="LIST_DATA">
							<input name="<c:out value="${companyMetalPrefix}.weight"/>" value="<nested:write property="weight"/>" type="text" size="10" onchange="JavaScript:calculateMetalUsedNetWeight(this);calculateMetalUsedValue(this);calculateInvoiceTotalMetal();calculateInvoiceTotalAdvance();calculateInvoiceTotal();" readonly="true" class="FORM_CONTROL_DISABLE">
							<nested:write property="weightUnit"/>
							<input name="<c:out value="${companyMetalPrefix}.weightUnit"/>" value="<nested:write property="weightUnit"/>" type="hidden" >
							<input name="<c:out value="${companyMetalPrefix}.weightUnitId"/>" value="<nested:write property="weightUnitId"/>" type="hidden" >
						</td>
						<td class="LIST_DATA">
							<input name="<c:out value="${companyMetalPrefix}.wastageRate"/>" value="<nested:write property="wastageRate"/>" type="text" size="6"  onchange="JavaScript:calculateMetalUsedNetWeight(this);calculateMetalUsedValue(this);calculateInvoiceTotalMetal();calculateInvoiceTotalAdvance();calculateInvoiceTotal();" > 
							<select name="<c:out value="${companyMetalPrefix}"/>.wastageUnitId" onchange="JavaScript:calculateMetalUsedNetWeight(this);calculateMetalUsedValue(this);calculateInvoiceTotalMetal();calculateInvoiceTotalAdvance();calculateInvoiceTotal();">
							  <c:forEach var="lookup" items="${applicationScope.lookupWastageUnit}" varStatus="status" >
								 <c:choose>
				        			<c:when test='${pageScope.companyMetal.wastageUnitId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
								    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
								 </c:choose>
							  </c:forEach>
							</select>
						</td>
						<td class="LIST_DATA">
							<input name="<c:out value="${companyMetalPrefix}.netWeight"/>" value="<nested:write property="netWeight"/>" type="text" size="10"  onchange="JavaScript:calculateMetalUsedValue(this);calculateInvoiceTotalMetal();calculateInvoiceTotalAdvance();calculateInvoiceTotal();"></td>
						<td class="LIST_DATA">
							<input name="<c:out value="${companyMetalPrefix}.rate"/>" value="<nested:write property="rate"/>" type="text" size="9" onchange="JavaScript:calculateMetalUsedValue(this);calculateInvoiceTotalMetal();calculateInvoiceTotalAdvance();calculateInvoiceTotal();"></td>
						<td class="LIST_DATA">&nbsp;</td>
						<td class="LIST_DATA"><input name="<c:out value="${companyMetalPrefix}.value"/>" value="<nested:write property="value"/>" type="text" size="10" id="companyMetalValue" ></td>
					</tr>
				</nested:iterate>
				<!-- Comapny Gem -->
				<nested:iterate property="companyGemList" id="companyGem" indexId="companyGemIndex">
					<c:set var="companyGemPrefix" value="invoiceItem[${invoiceItemIndex}].companyGem[${companyGemIndex}]"/>
					<tr>
						<td class="LIST_DATA" colspan="2">
							<input name="<c:out value="${companyGemPrefix}.insertable"/>" value="<nested:write property="insertable"/>" type="hidden" >
							<input name="<c:out value="${companyGemPrefix}.salesInvoiceItemGemUsedId"/>" value="<nested:write property="salesInvoiceItemGemUsedId"/>" type="hidden" >
							<input name="<c:out value="${companyGemPrefix}.inventoryGemEntryIdOut"/>" value="<nested:write property="inventoryGemEntryIdOut"/>" type="hidden" >
							<nested:write property="itemName"/>
							<input name="<c:out value="${companyGemPrefix}.itemId"/>" value="<nested:write property="itemId"/>" type="hidden" >
							<input name="<c:out value="${companyGemPrefix}.itemName"/>" value="<nested:write property="itemName"/>" type="hidden" >
						</td>
						<td class="LIST_DATA"><input name="<c:out value="${companyGemPrefix}.quantity"/>" value="<nested:write property="quantity"/>" type="text" size="8" onchange="JavaScript:calculateGemUsedValue(this);calculateInvoiceTotalGem();calculateInvoiceTotalAdvance();calculateInvoiceTotal();" readonly="true" class="FORM_CONTROL_DISABLE"></td>
						<td class="LIST_DATA">
							<input name="<c:out value="${companyGemPrefix}.weight"/>" value="<nested:write property="weight"/>" type="text" size="10" onchange="JavaScript:calculateGemUsedValue(this);calculateInvoiceTotalGem();calculateInvoiceTotalAdvance();calculateInvoiceTotal();" readonly="true" class="FORM_CONTROL_DISABLE"> 
							<nested:write property="weightUnit"/>
							<input name="<c:out value="${companyGemPrefix}.weightUnit"/>" value="<nested:write property="weightUnit"/>" type="hidden" >
							<input name="<c:out value="${companyGemPrefix}.weightUnitId"/>" value="<nested:write property="weightUnitId"/>" type="hidden" >
						</td>
						<td class="LIST_DATA">&nbsp;</td>
						<td class="LIST_DATA">&nbsp;</td>
						<td class="LIST_DATA">
							<input name="<c:out value="${companyGemPrefix}.rate"/>" value="<nested:write property="rate"/>" type="text" size="9" onchange="JavaScript:calculateGemUsedValue(this);calculateInvoiceTotalGem();calculateInvoiceTotalAdvance();calculateInvoiceTotal();"> </td>
						<td class="LIST_DATA">
							<select name="<c:out value="${companyGemPrefix}"/>.valueCalculateBy" onchange="JavaScript:calculateGemUsedValue(this);calculateInvoiceTotalGem();calculateInvoiceTotalAdvance();calculateInvoiceTotal();">
							  <c:forEach var="lookup" items="${applicationScope.lookupItemValueCalculateBy}" varStatus="status" >
								 <c:choose>
				        			<c:when test='${pageScope.companyGem.valueCalculateBy == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></c:when>
								    <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
								 </c:choose>
							  </c:forEach>
							</select>
						</td>
						<td class="LIST_DATA">
							<input name="<c:out value="${companyGemPrefix}.value"/>" value="<nested:write property="value"/>" type="text" size="10" id="companyGemValue" ></td>
					</tr>
				</nested:iterate>
				
				<!-- Customer Gems -->
				<nested:iterate property="customerGemList" id="customerGem" indexId="customerGemIndex">
					<c:set var="customerGemPrefix" value="invoiceItem[${invoiceItemIndex}].customerGem[${customerGemIndex}]"/>
					<tr>
						<td class="LIST_DATA" colspan="2">
							<input name="<c:out value="${customerGemPrefix}.insertable"/>" value="<nested:write property="insertable"/>" type="hidden" >
							<input name="<c:out value="${customerGemPrefix}.salesInvoiceItemGemUsedId"/>" value="<nested:write property="salesInvoiceItemGemUsedId"/>" type="hidden" >
							<nested:write property="itemName"/>
							<input name="<c:out value="${customerGemPrefix}.itemId"/>" value="<nested:write property="itemId"/>" type="hidden" >
							<input name="<c:out value="${customerGemPrefix}.itemName"/>" value="<nested:write property="itemName"/>" type="hidden" >
						</td>
						<td class="LIST_DATA">
							<input name="<c:out value="${customerGemPrefix}.quantity"/>" value="<nested:write property="quantity"/>" type="text" size="8" maxlength="9" readonly="true" class="FORM_CONTROL_DISABLE"></td>
						<td class="LIST_DATA">
							<input name="<c:out value="${customerGemPrefix}.weight"/>" value="<nested:write property="weight"/>" type="text" size="10"  readonly="true" styleClass="FORM_CONTROL_DISABLE"> 
							<nested:write property="weightUnit"/>
							<input name="<c:out value="${customerGemPrefix}.weightUnit"/>" value="<nested:write property="weightUnit"/>" type="hidden" >
							<input name="<c:out value="${customerGemPrefix}.weightUnitId"/>" value="<nested:write property="weightUnitId"/>" type="hidden" >
						</td>
						<td class="LIST_DATA">&nbsp;</td>
						<td class="LIST_DATA">&nbsp;</td>
						<td class="LIST_DATA">&nbsp;</td>
						<td class="LIST_DATA">&nbsp;</td>
						<td class="LIST_DATA">&nbsp;</td>
					</tr>
				</nested:iterate>
				<tr>
					<td class="LIST_DATA" colspan="9">&nbsp;</td>
				</tr>
			</nested:iterate>
<!-- 
			<tr>
				<td class="LINE_BOTTOM_SINGLE" colspan="9"><div class="LIST_DATA"><B>Advance</B></div></td>
			</tr>
			<tr>
				<td class="LIST_DATA">Cash</td>
				<td class="LIST_DATA" colspan="7">&nbsp;</td>
				<td class="LIST_DATA"><html:text property="advanceCash" size="11" styleId="customerAdvcanceCash"  readonly="true" styleClass="FORM_CONTROL_DISABLE"/></td>
			</tr>
			<nested:iterate property="customerAdvanceMetalList" id="customerAdvanceMetal" indexId="customerAdvanceMetalIndex">
				<tr>
					<td class="LIST_DATA" colspan="2"><nested:write property="itemName"/><nested:hidden indexed="true" name="customerAdvanceMetal" property="itemId"/><nested:hidden indexed="true" name="customerAdvanceMetal" property="itemName"/></td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA"><nested:text indexed="true" name="customerAdvanceMetal" property="weight" size="10"  readonly="true" styleClass="FORM_CONTROL_DISABLE"/> <nested:write property="weightUnit"/><nested:hidden indexed="true" name="customerAdvanceMetal" property="weightUnitId"/></td>
					<td class="LIST_DATA"><nested:text indexed="true" name="customerAdvanceMetal" property="wastageRate" size="6"  readonly="true" styleClass="FORM_CONTROL_DISABLE"/> <nested:write property="wastageUnit"/><nested:hidden indexed="true" name="customerAdvanceMetal" property="wastageUnitId"/></td>
					<td class="LIST_DATA"><nested:text indexed="true" name="customerAdvanceMetal" property="netWeight" size="10" readonly="true" styleClass="FORM_CONTROL_DISABLE"/></td>
					<td class="LIST_DATA"><nested:text indexed="true" name="customerAdvanceMetal" property="rate" size="9"  readonly="true" styleClass="FORM_CONTROL_DISABLE"/></td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA"><nested:text indexed="true" name="customerAdvanceMetal" property="value" size="11"  readonly="true" styleClass="FORM_CONTROL_DISABLE" styleId="customerAdvanceMetalValue" /></td>
				</tr>
			</nested:iterate>

			<tr>
				<td class="LIST_DATA" colspan="9">&nbsp;</td>
			</tr>
 -->
			<!-- Customer Advance Unused Gems 
			<tr>
				<td class="LINE_BOTTOM_SINGLE" colspan="9"><div class="LIST_DATA"><B>Unused Customer Gems</B> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Note : Return to customer)</div></td>
			</tr>

			<nested:iterate property="customerUnusedGemList" id="customerUnusedGem" indexId="customerUnusedGemIndex">
				<tr>
					<td class="LIST_DATA">
						<nested:write property="itemName"/>
						<nested:hidden property="itemId"/> 
					</td>
					<td class="LIST_DATA">&nbsp;</td>
					
					<td class="LIST_DATA"><nested:text property="quantity" size="8" maxlength="9" readonly="true" styleClass="FORM_CONTROL_DISABLE"/></td>
					<td class="LIST_DATA">
						<nested:text property="weight" size="10" />
						<nested:write property="weightUnit"/>
						<nested:hidden property="weightUnit"/>
						<nested:hidden property="weightUnitId"/>
					</td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA">&nbsp;</td>
				</tr>
			</nested:iterate>
-->
			<tr>
				<td colspan="9">
					<br>
					<table align="right" class="LIST_AREA" width="250">
						<tr>
							<td width="150" class="LIST_HEADER">Metal Value</td>
							<td width="100" class="LIST_DATA" align="right"><html:text property="totalMetalValue"  size="15" styleId="totalMetalValue" onchange="calculateInvoiceTotal()"/></td>
						</tr>
						<tr>
							<td class="LIST_HEADER">Stone Value</td>
							<td class="LIST_DATA" align="right"><html:text property="totalGemValue"  size="15" styleId="totalGemValue"  onchange="calculateInvoiceTotal()" /> </td>
						</tr>
						<tr>
							<td class="LIST_HEADER">Making</td>
							<td class="LIST_DATA" align="right"><html:text property="totalMaking"  size="15" styleId="totalMaking"  onchange="calculateInvoiceTotal()"/></td>
						</tr>
						<tr>
							<td class="LIST_HEADER">Total Bill Amount</td>
							<td class="LIST_DATA" align="right"><html:text property="totalInvoiceAmount"  size="15" styleClass="FORM_CONTROL_DISABLE" readonly="true" styleId="totalInvoiceAmount"/></td>
						</tr>
						<tr>
							<td class="LIST_HEADER">Total Advance Cash Value</td>
							<td class="LIST_DATA" align="right"><html:text property="totalAdvance" size="15" styleClass="FORM_CONTROL_DISABLE" readonly="true" styleId="totalAdvance"/></td>
						</tr>
						<tr>
							<td class="LIST_HEADER">Receiveable</td>
							<td class="LIST_DATA" align="right"><html:text property="receiveable" size="15" styleClass="FORM_CONTROL_DISABLE" readonly="true" styleId="receiveable"/></td> 
						</tr>
					</table>

				</td>
			</tr>
		</table>
		<br>
		<div align="right"><html:submit property="submitAction" value="Save" /></div>
	  </html:form>
	</div>
<script language="JavaScript">
	
	function loadDefaultValue () {
		if(myForm.insertable.value == "true"){
			calculateInvoiceTotalMetal();
			calculateInvoiceTotalGem();
			calculateInvoiceTotalAdvance();
			calculateInvoiceTotal();
		}
	}
	function calculateMetalUsedNetWeight(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));
		oWeight = document.getElementsByName(oName+".weight").item(0);
		oWeightUnitId = document.getElementsByName(oName+".weightUnitId").item(0);
		oWastageRate = document.getElementsByName(oName+".wastageRate").item(0);
		oWastageUnitId = document.getElementsByName(oName+".wastageUnitId").item(0);
		oNetWeight = document.getElementsByName(oName+".netWeight").item(0);
		var vWeight = parseFloat(oWeight.value);
		var vWastageRate = parseFloat(oWastageRate.value);
		var vWasteQuantity = 0.0;
		if (oWastageUnitId.value == 1) {
			vWasteQuantity = parseFloat((vWeight * vWastageRate)/100).toFixed(3) ;

		} else {
			vWasteQuantity = parseFloat((vWeight * vWastageRate)/96).toFixed(3) ;
		}
		oNetWeight.value = (parseFloat(vWeight) + parseFloat(vWasteQuantity)).toFixed(3);
	}
	function calculateMetalUsedValue(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));
		oRate = document.getElementsByName(oName+".rate").item(0);
		oNetWeight = document.getElementsByName(oName+".netWeight").item(0);
		oItemValue= document.getElementsByName(oName+".value").item(0);
		var vNetWeight = parseFloat(oNetWeight.value);
		var vRate = parseFloat(oRate.value);
		oItemValue.value = Math.abs(vNetWeight * vRate).toFixed(2);
	}


	function calculateGemUsedValue(obj) {
		var oName = (obj.name).substring(0,(obj.name).lastIndexOf('.'));
		oWeight = document.getElementsByName(oName+".weight").item(0);
		oQuantity = document.getElementsByName(oName+".quantity").item(0);
		oRate = document.getElementsByName(oName+".rate").item(0);
		oValueCalculateBy = document.getElementsByName(oName+".valueCalculateBy").item(0);
		oItemValue= document.getElementsByName(oName+".value").item(0);
		
		if (oValueCalculateBy.value == 1) {
			oItemValue.value = parseFloat(oQuantity.value * oRate.value).toFixed(2) ;
		} else if(oValueCalculateBy.value == 2) {
			oItemValue.value = parseFloat(oWeight.value * oRate.value).toFixed(2) ;
		} else {}
	}

	function calculateInvoiceTotalMetal() {
		var oCollectionCompanyMetalValue = GetElementsById(document.getElementsByTagName('INPUT'), 'companyMetalValue');
		var oTotalMetalValue = document.getElementById('totalMetalValue');
		var totalMetalValue = 0.0;
		for(i=0; i<oCollectionCompanyMetalValue.length; i++ ) 
			totalMetalValue += parseInt(oCollectionCompanyMetalValue[i].value) ? parseFloat(oCollectionCompanyMetalValue[i].value) : 0; 
		oTotalMetalValue.value = parseFloat(totalMetalValue).toFixed(2);
	}
	
	function calculateInvoiceTotalGem() {
		var oCollectionCompanyGemValue = GetElementsById(document.getElementsByTagName('INPUT'), 'companyGemValue');
		var oTotalGemValue = document.getElementById('totalGemValue');
		var totalGemValue = 0.0;
		for(i=0; i<oCollectionCompanyGemValue.length; i++ ) 
			totalGemValue += parseFloat(oCollectionCompanyGemValue[i].value) ? parseFloat(oCollectionCompanyGemValue[i].value) : 0; 
		oTotalGemValue.value = parseFloat(totalGemValue).toFixed(2);
	}
	function calculateInvoiceTotalAdvance() {
		var oCollectionCustomerAdvanceMetalValue = GetElementsById(document.getElementsByTagName('INPUT'), 'customerAdvanceMetalValue');
		var oCustomerAdvanceCash = document.getElementById('customerAdvcanceCash');
		var oTotalAdvance = document.getElementById('totalAdvance');
		var totalAdvance = 0.0;

		for(i=0; i<oCollectionCustomerAdvanceMetalValue.length; i++ ) 
			totalAdvance += parseFloat(oCollectionCustomerAdvanceMetalValue[i].value) ? parseFloat(oCollectionCustomerAdvanceMetalValue[i].value) : 0; 
		totalAdvance += parseFloat(totalAdvance.value) ? parseFloat(totalAdvance.value) : 0.0;
		oTotalAdvance.value =  parseFloat(totalAdvance).toFixed(2);
	}
	function calculateInvoiceTotal() {
		var oCollectionCustomerAdvanceMetalValue = GetElementsById(document.getElementsByTagName('INPUT'), 'customerAdvanceMetalValue');
		var oCustomerAdvanceCash = document.getElementById('customerAdvcanceCash');
		var oTotalMetalValue = document.getElementById('totalMetalValue');
		var oTotalGemValue = document.getElementById('totalGemValue');
		var oTotalMaking = document.getElementById('totalMaking');
		var oTotalInvoiceAmount = document.getElementById('totalInvoiceAmount');
		var oTotalAdvance = document.getElementById('totalAdvance');
		var oReceiveable = document.getElementById('receiveable');

		var totalMetalValue = parseFloat(oTotalMetalValue.value) ? parseFloat(oTotalMetalValue.value) : 0.0;
		var totalGemValue = parseFloat(oTotalGemValue.value) ? parseFloat(oTotalGemValue.value) : 0.0;
		var totalAdvance = 0.0;
		var totalMaking = parseFloat(oTotalMaking.value) ? parseFloat(oTotalMaking.value) : 0.0;
		
		oTotalInvoiceAmount.value = (parseFloat(totalMetalValue) + parseFloat(totalGemValue) + parseFloat(totalMaking)).toFixed(0);

		for(i=0; i<oCollectionCustomerAdvanceMetalValue.length; i++ ) 
			totalAdvance += parseFloat(oCollectionCustomerAdvanceMetalValue[i].value) ? parseFloat(oCollectionCustomerAdvanceMetalValue[i].value) : 0; 
		totalAdvance += parseFloat(totalAdvance.value) ? parseFloat(totalAdvance.value) : 0.0;
		oTotalAdvance.value =  parseFloat(totalAdvance).toFixed(2);
		
		oReceiveable.value =  parseFloat(oTotalInvoiceAmount.value - totalAdvance).toFixed(2) ;
	}
		onLoad = loadDefaultValue();
</script>
