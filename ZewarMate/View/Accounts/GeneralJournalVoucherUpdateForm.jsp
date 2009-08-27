<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import=" java.util.*
								, com.netxs.Zewar.Lookup.*;"%>


<jsp:useBean id="now" class="java.util.Date" />
<html:form action="/GeneralJournal/UpdateVoucher" method="post" styleId="myForm">
	<html:hidden property="voucherId" />
	<html:hidden property="voucherPrefix" value="JV" />
	<html:hidden property="hasFormInitialized" />
	<input type="hidden" name="submitAction" value="">
	<div><span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE">Update General Voucher</span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
	<div class="BOX" style="width:920;">
		<span id="messages" class="WARNING">
			<logic:messagesPresent >
				<ul>
		   		<html:messages id="error">
		 	  		<li><c:out value="${error}" /></li>
			   	</html:messages>
		 		</ul>
			</logic:messagesPresent>		
		</span>
		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="600">
			<tr>
				<td width="150" class="FORM_CAPTION">Voucher Number</td> 
				<td width="450" class="FORM_CONTROL">&nbsp;JV - <html:text property="voucherPostfix" size="6"  onchange="javascript:GetFormDataFromServer();"/></td>
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
		<div align="right"><html:button property="submitAction" value="  Save  " onclick="javascript:saveForm('save');" /></div>
	</div>
</html:form> 
<script language="javascript">
<!--
	// variable prefix oG stand for Object in Global scope
	// variable prefix oF stand for Object in Form
   	// function name start with Class define the class
   
	function ClassVoucher() {
		this.voucherId="";
		this.voucherPrefix="";
		this.voucherPostfix="";
		this.ledgerEntryId="";
		this.entryDate="";
		this.ledgerAccountIdDebit="";
		this.ledgerAccountIdCredit="";
		this.narration="";
		this.amount="";
	}
	
	var oGXmlHttp;
	var oGXmlDoc;
	var oGMessages = document.getElementById("messages");
	
	var oGForm = document.getElementById("myForm");

	var oGTableVoucherEntry = document.getElementById("TBL1_DISTINATION");

	var oGClassVoucher = new ClassVoucher();
	
	function GetFormDataFromServer() {
		unloadFormData();
		//Validating Form
		if((oGForm.voucherPostfix.value).search("^[0-9]+$")==-1) {
			alert("Please Enter valid Voucher Number");
			oGForm.voucherPostfix.focus();
			return;
		}
		
		//Disable Load Control
		oGMessages.innerText = "Loading ...";
		var url="XmlVoucher.do?&cache=0&voucherPrefix="+oGForm.voucherPrefix.value+"&voucherPostfix="+oGForm.voucherPostfix.value;
		oGXmlHttp=GetXmlHttpObject(stateChanged);
		oGXmlHttp.open("POST", url , true);
		oGXmlHttp.send(null); 
		
	}

	function stateChanged() { 
		if (oGXmlHttp.readyState==4 || oGXmlHttp.readyState=="complete") { 
			oGMessages.innerText = " ";
			oGXmlDoc = loadXML(oGXmlHttp.responseBody);
//			alert(oGXmlHttp.responseText);
			var vControlFlag = oGXmlDoc.getElementsByTagName("ControlFlag");
			
			if (vControlFlag[0]) {
				if (vControlFlag[0].getElementsByTagName("ResponseFlag")[0].text=="0")  {
					oGMessages.innerText = vControlFlag[0].getElementsByTagName("ResponseFlagMessage")[0].text;					
					oGForm.voucherPostfix.focus();
				}
				else if (vControlFlag[0].getElementsByTagName("ResponseFlag")[0].text=="1")  {
					//load Voucher from XML data
					oGClassVoucher = new ClassVoucher();
					oLXmlVoucher = oGXmlDoc.getElementsByTagName("Voucher");
					oGClassVoucher.voucherId = oLXmlVoucher[0].getElementsByTagName("VoucherId")[0].text;
					oGClassVoucher.ledgerEntryId = oLXmlVoucher[0].getElementsByTagName("LedgerEntryId")[0].text;
					oGClassVoucher.voucherPrefix = oLXmlVoucher[0].getElementsByTagName("VoucherPrefix")[0].text;
					oGClassVoucher.voucherPostfix = oLXmlVoucher[0].getElementsByTagName("VoucherPostfix")[0].text;
					oGClassVoucher.ledgerAccountIdDebit = oLXmlVoucher[0].getElementsByTagName("LedgerAccountIdDebit")[0].text;
					oGClassVoucher.ledgerAccountIdCredit = oLXmlVoucher[0].getElementsByTagName("LedgerAccountIdCredit")[0].text;
					oGClassVoucher.entryDate = oLXmlVoucher[0].getElementsByTagName("EntryDate")[0].text;
					oGClassVoucher.narration = oLXmlVoucher[0].getElementsByTagName("Narration")[0].text;
					oGClassVoucher.amount = oLXmlVoucher[0].getElementsByTagName("Amount")[0].text;
					//load Voucher Entry from XML data 
					loadFormData();
				} else {
					oGMessages.innerText = "Fail to Load";
				}
			} else {
				oGMessages.innerText = "Fail to load. Session may be expired";
			}
		}
		oGForm.voucherPostfix.focus(); 
	} 
	
	function loadFormData() {

		//activate form cntrol
		oGForm.ledgerAccountIdDebit.disabled = false;
		oGForm.ledgerAccountIdCredit.disabled = false;
		oGForm.entryDate.disabled = false;
		oGForm.amount.disabled = false;
		oGForm.amountCredit.disabled = false;
		oGForm.amountCredit.readonly = false;
		oGForm.narration.disabled = false;
		oGForm.buttonEntryDate.disabled = false;
		//load Form Control data
		oGForm.voucherId.value = oGClassVoucher.voucherId;
		oGForm.voucherPrefix.value = oGClassVoucher.voucherPrefix;
		oGForm.voucherPostfix.value = oGClassVoucher.voucherPostfix;
		setControlSelect(oGForm.ledgerAccountIdDebit, oGClassVoucher.ledgerAccountIdDebit);
		setControlSelect(oGForm.ledgerAccountIdCredit, oGClassVoucher.ledgerAccountIdCredit);
		oGForm.entryDate.readOnly = false; 
		oGForm.entryDate.value = oGClassVoucher.entryDate;
		oGForm.entryDate.readOnly = true; 
		oGForm.amount.value = oGClassVoucher.amount;
		oGForm.amountCredit.value = oGClassVoucher.amount;
		oGForm.narration.value = oGClassVoucher.narration;
		oGForm.amountCredit.readonly = true;
		oGForm.submitAction.disabled = false;


		oGForm.hasFormInitialized.value = "Y";		
		oGForm.voucherPostfix.focus();				
	}
	
	function unloadFormData() {
		//unload Form Control data
		oGForm.voucherId.value = "";
		oGForm.ledgerAccountIdDebit.options[0].selected = true;
		oGForm.ledgerAccountIdCredit.options[0].selected = true;
		oGForm.entryDate.readOnly = false; 
		oGForm.entryDate.value = "";
		oGForm.entryDate.readOnly = true; 
		oGForm.entryDate.disabled = true; 
		oGForm.amount.value = "";
		oGForm.amountCredit.value = "";
		oGForm.narration.value = "";
		oGForm.submitAction.disabled = false;
		// disbaled 
		oGForm.ledgerAccountIdDebit.disabled = true;
		oGForm.ledgerAccountIdCredit.disabled = true;
		oGForm.entryDate.disabled = true;
		oGForm.amount.disabled = true;
		oGForm.narration.disabled = true;
		oGForm.buttonEntryDate.disabled = true;

		oGForm.hasFormInitialized.value = "Y";		
		oGForm.voucherPostfix.focus();				
		oGClassVoucher = new ClassVoucher();
		oGMessages.innerText = " ";
		oGForm.voucherPostfix.focus();
	}


	document.onload=initializeForm();
	function initializeForm() {
		oGForm.amountCredit.value=oGForm.amount.value
		//unload Form Control data
		if (oGForm.hasFormInitialized.value == "Y") {
			oGForm.submitAction.disabled = false;
			//deactivate Form Control
			oGForm.ledgerAccountIdDebit.disabled = false;
			oGForm.ledgerAccountIdCredit.disabled = false;
			oGForm.entryDate.disabled = false;
			oGForm.buttonEntryDate.disabled = false;
			oGForm.narration.disabled = false;
			oGForm.amount.disabled = false;
			
			oGForm.hasFormInitialized.value = "N";
		
		} else {
			oGForm.submitAction.disabled = true;
			oGForm.entryDate.value = "";
			oGForm.entryDate.readOnly = true;
			
			//deactivate Form Control
			oGForm.ledgerAccountIdDebit.disabled = true;
			oGForm.ledgerAccountIdCredit.disabled = true;
			oGForm.entryDate.disabled = true;
			oGForm.buttonEntryDate.disabled = true;
			oGForm.narration.disabled = true;
			oGForm.amount.disabled = true;
			
			oGForm.hasFormInitialized.value = "N";
			oGClassVoucher = new ClassVoucher();
			oGMessages.innerText = " ";
			
		}

	}
	
	function saveForm(actionValue) {
		if (!confirm("Are you sure you want to save.")) {
			return;
		}
		oGForm.hasFormInitialized.value = "Y";
		oGForm.submitAction.value = actionValue;

		
		oGForm.submit();
	}
	
-->
</script>
	

