<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import=" java.util.*
								, com.netxs.Zewar.Lookup.*;"%>

<script type="text/javascript" language="Javascript1.1">
<!-- Begin 
	// accountDescriptionLevel[0] is Control
	// accountDescriptionLevel[1] is Detail
	
	function showLedgerTypeElements() {
		var objForm = document.forms[0];
		if (objForm.accountDescriptionLevel[0].value==1) {
			document.getElementById('tbl1').rows(6).style.display='none';
			document.getElementById('tbl1').rows(7).style.display='none';
			document.getElementById('tbl1').rows(4).style.display='';
			document.getElementById('tbl1').rows(5).style.display='';
		} else {
			document.getElementById('tbl1').rows(6).style.display='';
			document.getElementById('tbl1').rows(7).style.display='';
			document.getElementById('tbl1').rows(4).style.display='none';
			document.getElementById('tbl1').rows(5).style.display='none';
		}
	}
//End-->
</script>

<html:form action="/Ledger/UpdateAccount" method="post">
	<html:hidden property="ledgerAccountId" />
	<html:hidden property="hasFormInitialized" />
	<div><span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE">Update Account </span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
	<div class="BOX" style="width:920;">
		<logic:messagesPresent >
			<div class="FORM_AREA">
					<ul>
			    	<html:messages id="error">
		    	  	<li><c:out value="${error}" /></li>
			    	</html:messages>
		    	</ul>
			</div>
		</logic:messagesPresent>
		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="900" style="height: auto;" id="tbl1">
			<tr>
				<td width="200">&nbsp;</td> 
				<td width="700">&nbsp;</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Ledger Type</td>
				<td class="FORM_CONTROL">
					<html:hidden property="accountDescriptionLevel" />
					<html:radio property="accountDescriptionLevel" value="1" onclick="showLedgerTypeElements()" disabled="true" />Control
					<html:radio property="accountDescriptionLevel" value="2" onclick="showLedgerTypeElements()" disabled="true" />Detail 
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Title</td>
				<td class="FORM_CONTROL"><html:text property="title" size="60" maxlength="50" /></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Description</td>
				<td class="FORM_CONTROL"><html:textarea property="description" rows="4" cols="61"></html:textarea></td>
			</tr>
			
			<tr id="accountDescriptionLevel1">
				<td class="FORM_CAPTION">Account Nature</td>
				<td class="FORM_CONTROL">
					<html:select property="ledgerAccountTypeId">
				  	<html:options collection="lookupLedgerAccountType" property="id" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr id="accountDescriptionLevel1">
				<td class="FORM_CAPTION">Control Ledger Prefix</td>
				<td class="FORM_CONTROL"><html:text property="accountCodePrefix"  size="6" maxlength="2"/></td>
			</tr>
			<tr id="accountDescriptionLevel2" >
				<td class="FORM_CAPTION">Detail Ledger Parent</td>
				<td class="FORM_CONTROL">
					<html:select property="parentLedgerAccountId">
								 	<html:options collection="lookupLedgerAccountControl" property="id" labelProperty="label"   />
 				  </html:select>
				</td>
			</tr>
			<tr id="accountDescriptionLevel2" >
				<td class="FORM_CAPTION">Detail Ledger Postfix</td>
				<td class="FORM_CONTROL">
					<html:text property="accountCodePostfix"  size="6" maxlength="6"/>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Opening Balance</td>
				<td class="FORM_CONTROL">
					<html:text property="openingBalance" size="8" maxlength="8" />
					<html:select property="entryDebitCredit">
						<html:option value="1">Debit</html:option>
						<html:option value="-1">Credit</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Inventory Account</td>
				<td class="FORM_CONTROL"><html:radio property="isInventoryAccount" value="1" /> Yes &nbsp;&nbsp;<html:radio property="isInventoryAccount" value="0" /> No</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Account Active</td>
				<td class="FORM_CONTROL"><html:radio property="accountActive" value="Y" /> Yes &nbsp;&nbsp;<html:radio property="accountActive" value="N" /> No</td>
			</tr>
		</table>
		<br>
		<div align="right"><html:submit property="submitAction" value="  Save  " /></div>
	</div>
</html:form>
<script type="text/javascript" language="Javascript1.1">
	<!-- Begin 
	window.onLoad=document.forms[0].reset();
	window.onLoad=showLedgerTypeElements();
	//End-->
</script>
	