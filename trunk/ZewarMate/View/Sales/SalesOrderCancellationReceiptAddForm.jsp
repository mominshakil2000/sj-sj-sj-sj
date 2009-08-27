<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ page import=" java.util.*
				, com.netxs.Zewar.Lookup.*;" %>

	<div><span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE" style="width:270;">Sales Order Cancellation Receipt</span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:600;">&nbsp;</span></div>
	<div class="BOX" style="width:994;">
	  <html:form action="/SalesOrder/CreateCancellationReceipt" method="post">
	  	<html:hidden property="hasFormInitialized"/>
		<html:hidden property="customerLedgerAccountId"/>
		<html:hidden property="salesOrderId"/>
	  	<html:hidden property="salesOrderTrackingId"/>
	  	<logic:messagesPresent >
			<ul>
	    	<html:messages id="error">
    	  	<li><c:out value="${error}" /></li>
	    	</html:messages>
    	</ul>
		</logic:messagesPresent>
		<table class="FORM_AREA" width="600"  cellpadding="0" cellspacing="1">
			<tr>
				<td class="FORM_CAPTION" width="150" >Date</td>
				<td class="FORM_CONTROL" width="450"><html:text property="cancellationReceiptDate" maxlength="10" size="12" readonly="true"/> <input type="button" name="buttonCancellationReceiptDate" value=" ... " >
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("cancellationReceiptDate")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonCancellationReceiptDate")[0],
								showsTime:false
						});
					</script>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Cancellation Charges</td>
				<td class="FORM_CONTROL"><html:text property="cancellationCharges"  size="12"/></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Remarks</td>
				<td class="FORM_CONTROL"><html:textarea property="remarks" rows="4" cols="40"></html:textarea></td>
			</tr>
		</table>
		<br>
		<table class="LIST_AREA" width="982"  cellpadding="0" cellspacing="0">
			<tr>
				<td class="LIST_HEADER" width="232">Particulars</td>
				<td class="LIST_HEADER" width="100">Keep In Inventory?</td>
				<td class="LIST_HEADER" width="90">Qty</td>
				<td class="LIST_HEADER" width="120">Weight</td>
				<td class="LIST_HEADER" width="150">Wastage</td>
				<td class="LIST_HEADER" width="90">Net Wgt</td>
				<td class="LIST_HEADER" width="90">Rate</td>
				<td class="LIST_HEADER" width="110">Amount</td>
			</tr>
			<nested:iterate property="advanceMetalList" id="advanceMetal" indexId="advanceMetalIndex">
				<tr>
					<td class="LIST_DATA"><nested:write  property="itemName"/><nested:hidden indexed="true" name="advanceMetal" property="itemId"/><nested:hidden indexed="true" name="advanceMetal" property="itemName"/></td>
					<td class="LIST_DATA">
						<nested:select indexed="true" name="advanceMetal" property="keepInInvenory">
						   <html:option value="1" >Return</html:option>
						   <html:option value="2" >Keep Advance</html:option>
						</nested:select>
					</td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA">
						<nested:text indexed="true" name="advanceMetal" property="weight" size="10" readonly="true" styleClass="FORM_CONTROL_DISABLE"/> <nested:write property="weightUnit"/>
						<nested:hidden indexed="true" name="advanceMetal" property="weightUnitId"/><nested:hidden indexed="true" name="advanceMetal" property="weightUnit"/>
					</td>
					<td class="LIST_DATA">
						<nested:text indexed="true" name="advanceMetal" property="wastageRate" size="10" readonly="true" styleClass="FORM_CONTROL_DISABLE"/> 
						<nested:write property="wastageUnit"/>
						<nested:hidden indexed="true" name="advanceMetal" property="wastageUnitId"/>
						<nested:hidden indexed="true" name="advanceMetal" property="wastageUnit"/>
					</td>
					<td class="LIST_DATA" width="90"><nested:text indexed="true" name="advanceMetal" property="netWeight" size="10" readonly="true" styleClass="FORM_CONTROL_DISABLE"/></td>
					<td class="LIST_DATA" width="90"><nested:text indexed="true" name="advanceMetal" property="rate" size="10" readonly="true" styleClass="FORM_CONTROL_DISABLE"/></td>
					<td class="LIST_DATA" width="110"><nested:text indexed="true" name="advanceMetal" property="value" size="10" readonly="true" styleClass="FORM_CONTROL_DISABLE"/></td>
				</tr>
			</nested:iterate>
			
			<nested:iterate property="unusedAdvanceGemList" id="unusedAdvanceGem" >
				<tr>
					<td class="LIST_DATA"><nested:write property="itemName"/><nested:hidden indexed="true" name="unusedAdvanceGem" property="itemId"/><nested:hidden indexed="true" name="unusedAdvanceGem" property="itemName"/></td>
					<td class="LIST_DATA">
						<nested:select indexed="true" name="unusedAdvanceGem" property="keepInInvenory">
							<html:option value="1">Return</html:option>
							<html:option value="3">Purchase</html:option>
						</nested:select>
					</td>
					<td class="LIST_DATA"><nested:text indexed="true" name="unusedAdvanceGem" property="quantity" size="10" readonly="true" styleClass="FORM_CONTROL_DISABLE"/></td>
					<td class="LIST_DATA">
						<nested:text indexed="true" name="unusedAdvanceGem" property="weight" size="10" readonly="true" styleClass="FORM_CONTROL_DISABLE"/>
						<nested:write property="weightUnit" />
						<nested:hidden indexed="true" name="unusedAdvanceGem" property="weightUnitId"/>
						<nested:hidden indexed="true" name="unusedAdvanceGem" property="weightUnit"/>
					</td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA"><nested:text indexed="true" name="unusedAdvanceGem" property="rate" size="10" /></td>
					<td class="LIST_DATA"><nested:text indexed="true" name="unusedAdvanceGem" property="value" size="10" /></td>
				</tr>
			</nested:iterate>

			<nested:iterate property="usedAdvanceGemList" id="usedAdvanceGem">
				<tr>
					<td class="LIST_DATA"><nested:write property="itemName"/><nested:hidden indexed="true" name="usedAdvanceGem"  property="itemId"/><nested:hidden indexed="true" name="usedAdvanceGem" property="itemName"/></td>
					<td class="LIST_DATA">
						<nested:select indexed="true" name="usedAdvanceGem" property="keepInInvenory">
							<html:option value="3">Purchase</html:option>
							<html:option value="4">Replacement</html:option>
						</nested:select>
					</td>
					<td class="LIST_DATA"><nested:text indexed="true" name="usedAdvanceGem" property="quantity" size="10" readonly="true" styleClass="FORM_CONTROL_DISABLE"/></td>
					<td class="LIST_DATA">
						<nested:text indexed="true" name="usedAdvanceGem" property="weight" size="10" readonly="true" styleClass="FORM_CONTROL_DISABLE"/>
						<nested:write property="weightUnit"/>
						<nested:hidden indexed="true" name="usedAdvanceGem" property="weightUnitId"/>
						<nested:hidden indexed="true" name="usedAdvanceGem" property="weightUnit"/>
					</td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA"><nested:text indexed="true" name="usedAdvanceGem" property="rate" size="10" /></td>
					<td class="LIST_DATA"><nested:text indexed="true" name="usedAdvanceGem" property="value" size="10" /></td>
				</tr>
			</nested:iterate>

		</table>
		<br>
		<div align="right"><html:submit property="submitAction" value="Create Receipt" /></div>
	  </html:form>
 </div>
