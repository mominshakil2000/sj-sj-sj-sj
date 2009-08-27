<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import=" java.util.*
								, com.netxs.Zewar.Lookup.*;"%>


<jsp:useBean id="now" class="java.util.Date" />
<html:form action="/GeneralJournal/CreateVoucher" method="post" >
	<html:hidden property="voucherId" />
	<html:hidden property="voucherPrefix" value="JV" />
	<html:hidden property="hasFormInitialized" />
	<div><span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE">General Voucher</span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
	<div class="BOX" style="width:920;">
		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="600">
			<tr>
				<td width="150" class="FORM_CAPTION">Voucher Number</td> 
				<td width="450" class="FORM_CONTROL">&nbsp;JV - AUTO</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Date</td> 
				<td class="FORM_CONTROL">
					<html:text property="entryDate"  size="12" readonly="true" />
					<html:button property="buttonEntryDate" value=" ... " />
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("entryDate")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonEntryDate")[0],
								showsTime:false
						});
					</script>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Narration</td> 
				<td class="FORM_CONTROL"><html:textarea property="narration" cols="36" rows="4" ></html:textarea></td>
			</tr>
		</table>
		<br><br><br>
		<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="900">
			<tr>
				<td width="600" class="LIST_HEADER">Account </td> 
				<td width="150" class="LIST_HEADER">Debit</td>
				<td width="150" class="LIST_HEADER">Credit</td>
			</tr>
			<tr>
				<td width="500" class="LIST_DATA">
					<html:select property="ledgerAccountIdDebit">
						<html:options collection="lookupLedgerAccountDetail" property="id" labelProperty="label"/>
					</html:select></td> 
				<td width="100" class="LIST_DATA" align="right"><html:text property="amount"  size="21" maxlength="10" onchange="JavaScript:amountCredit.value=this.value" /></td>
				<td width="100" class="LIST_DATA">&nbsp;</td>
			</tr>
			<tr>
				<td width="500" class="LIST_DATA">
					<html:select property="ledgerAccountIdCredit">
						<html:options collection="lookupLedgerAccountDetail" property="id" labelProperty="label"/>
					</html:select></td> 
				<td width="100" class="LIST_DATA">&nbsp;</td>
				<td align="right" width="100" class="LIST_DATA" ><input type="text" name="amountCredit"  size="21" maxlength="10" value="0.0" readonly="true"/></td>
			</tr>
		</table>
		<br><br>
		<div align="right"><html:submit property="submitAction" value="  Save  " /> <html:submit property="submitAction" value="Save & Add Another"  /></div>
	</div>
</html:form> 

<script type="text/javascript" language="Javascript1.1">
	<!-- Begin 
	window.onLoad=document.forms[0].reset();
	document.forms[0].amountCredit.value=document.forms[0].amount.value
	//End-->
</script>