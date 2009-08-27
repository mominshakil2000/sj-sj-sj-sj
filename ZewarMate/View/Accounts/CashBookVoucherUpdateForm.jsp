<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ page import=" java.util.*
								, com.netxs.Zewar.Lookup.*;"%>

<div><span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE">Update Cash Voucher </span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
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
	
	<html:form action="/CashBook/UpdateVoucher" method="post" styleId="myForm" >
		<html:hidden property="hasFormInitialized" />
		<html:hidden property="voucherId" />
		<html:hidden property="removeVoucherEntryIds" />
		<html:hidden property="removeLedgerEntryIds" />
		<input type="hidden" name="submitAction" value="">
		
		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="600">
			<tr>
				<td width="150" class="FORM_CAPTION">Voucher Number</td> 
				<td width="450" class="FORM_CONTROL">
					<html:select property="voucherPrefix" onchange="javascript:GetFormDataFromServer();">
						<html:option value="CR">Cash Receive</html:option>
						<html:option value="CP">Cash Payment</html:option>
					</html:select>
					<html:text property="voucherPostfix" size="8"  onchange="javascript:GetFormDataFromServer();"/>
				</td>
			</tr>
			<tr>
				<td width="150" class="FORM_CAPTION">Cash Account</td> 
				<td width="450" class="FORM_CONTROL">
					<html:hidden property="ledgerAccountId"/>
					<html:select property="ledgerAccountId" disabled="true">
						<html:options collection="lookupLedgerAccountCashDetail" property="id" labelProperty="label"/>
					</html:select></td>
			</tr>
			<tr>
				<td width="150" class="FORM_CAPTION">Date</td> 
				<td width="450" class="FORM_CONTROL">
					<html:text property="entryDate" size="12" readonly="true" style="FORM_CONTROL_DISABLE" />
					<!--
					<html:button property="buttonEntryDate" value=" ... " />
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("entryDate")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonEntryDate")[0],
								showsTime:false
						});
					</script> 
					-->
				</td>
			</tr>
		</table>
 		<div style="width:900px;border-width:1px; border-color:#736356; border-style:solid; border-collapse:collapse;">
		<table border="0" class="LIST_AREA" cellpadding="1" cellspacing="0" width="900">
			<COL WIDTH=25><COL WIDTH=300><COL WIDTH=400><COL WIDTH=170>
			<tr>
				<td  class="LIST_HEADER">&nbsp;</td>
				<td class="LIST_HEADER">Ledger Account</td>
				<td class="LIST_HEADER">Narration</td>
				<td class="LIST_HEADER">Amount</td>
			</tr>
		</table>
 		<div style="overflow:auto; width:900px; height:250px; padding:0px; margin:0px;">
			<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="880" id="TBL1_DISTINATION">
				<COL WIDTH=25><COL WIDTH=300><COL WIDTH=400><COL WIDTH=150>
				<c:set var="totalAmount" scope="page" value="0.00"/>
				<logic:iterate id="voucherEntry" name="cashVoucherForm" property="listVoucherEntry" >
					<c:set var="totalAmount" scope="page" value="${totalAmount + voucherEntry.amount}"/> 
					<tr>
						<td class="LIST_DATA" ><a href="#" onclick="JavaScript:removeItemOnVoucherEntry(GetParentByTagName(window.event.srcElement, 'TR'));">D</a></td>
						<td class="LIST_DATA" >
							<bean:write name="voucherEntry" property="ledgerAccountTitle"/>
							<html:hidden name="voucherEntry" property="insertable" indexed="true" styleId="VOUCHER_ENTRY_INSERTABLE" />
							<html:hidden name="voucherEntry" property="voucherEntryId" indexed="true" styleId="VOUCHER_ENTRY_VOUCHER_ENTRY_ID" />
							<html:hidden name="voucherEntry" property="ledgerEntryId" indexed="true" styleId="VOUCHER_ENTRY_LEDGER_ENTRY_ID" />
							<html:hidden name="voucherEntry" property="ledgerAccountId" indexed="true" styleId="VOUCHER_ENTRY_LEDGER_ACCOUNT_ID" />
							<html:hidden name="voucherEntry" property="ledgerAccountTitle" indexed="true"  styleId="VOUCHER_ENTRY_LEDGER_ACCOUNT_TITLE"/>
						</td>
						<td class="LIST_DATA" ><html:textarea name="voucherEntry" property="narration" indexed="true" cols="62" rows="2" styleId="VOUCHER_ENTRY_NARRATION"></html:textarea></td>
						<td class="LIST_DATA" >
							<html:text name="voucherEntry" property="amount" indexed="true" size="20" maxlength="11"  styleId="VOUCHER_ENTRY_AMOUNT"/>
						</td>
					</tr>
				</logic:iterate>
			</table>
		</div>
		<table border="0" class="LIST_AREA" cellpadding="1" cellspacing="0" width="900">
			<COL WIDTH=725><COL WIDTH=170>
			<tr>
				<td class="LIST_HEADER">Grand Total&nbsp;&nbsp;</td>
				<td class="LIST_HEADER"><fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol=" "/></td>
			</tr>
		</table>
		</div>
		<br>
		<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="880" id="TBL1_SOURCE">
			<tr>
				<td class="FORM_DATA" width="320" valign="top">
					<select name="SOURCE_LEDGER_ACCOUNT_ID" id="SOURCE_LEDGER_ACCOUNT_ID">
						<logic:iterate  id="lookup" name="lookupLedgerAccountDetail"  scope="application" >
							<option value="<bean:write name="lookup" property="id" />"><bean:write name="lookup" property="label" /></option>						
						</logic:iterate>
					</select>
				</td>
				<td class="FORM_DATA" width="400" valign="top"><textarea name="SOURCE_NARRATION" id="SOURCE_NARRATION" cols="62" rows="1" ></textarea></td>
				<td class="FORM_DATA" width="100" valign="top"><input type="text" name="SOURCE_AMOUNT" id="SOURCE_AMOUNT" value="0.00" size="20" maxlength="11"></td>
				<td class="FORM_DATA" width="70" valign="top"><input type="button" name="SOURCE_BUTTON_ADD" id="SOURCE_BUTTON_ADD" value="ADD" onclick="addItemOnVoucherEntryFromSource()"></td>
			</tr>
		</table>
		<br>
		<div align="right"><html:button property="submitAction" value="  Save  " onclick="javascript:saveForm();" /></div>
	</html:form> 
</div>

<script language="javascript">
<!--
	// variable prefix oG stand for Object in Global scope
	// variable prefix oF stand for Object in Form
   	// function name start with Class define the class
   
	function ClassCashVoucher() {
		this.voucherId="";
		this.voucherPrefix="";
		this.voucherPostfix="";
		this.ledgerAccountId="";
		this.entryDate="";
		this.oGListClassCashVoucherEntry = new Array();
		this.removeVoucherEntryIds=0;
		this.removeLedgerEntryIds=0;
	}
	
	function ClassCashVoucherEntry() {
		this.insertable = true;
		this.voucherEntryId="";
		this.ledgerEntryId="";
		this.ledgerAccountId="";
		this.ledgerAccountTitle="";
		this.amount="";
		this.narration;
	}
	
	var oGXmlHttp;
	var oGXmlDoc;
	var oGMessages = document.getElementById("messages");
	
	var oGForm = document.getElementById("myForm");

	var oGTableVoucherEntry = document.getElementById("TBL1_DISTINATION");	

	var oGClassCashVoucher = new ClassCashVoucher();
	
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
					oGClassCashVoucher = new ClassCashVoucher();
					oLXmlVoucher = oGXmlDoc.getElementsByTagName("Voucher");
					oGClassCashVoucher.voucherId = oLXmlVoucher[0].getElementsByTagName("VoucherId")[0].text;
					oGClassCashVoucher.voucherPrefix = oLXmlVoucher[0].getElementsByTagName("VoucherPrefix")[0].text;
					oGClassCashVoucher.voucherPostfix = oLXmlVoucher[0].getElementsByTagName("VoucherPostfix")[0].text;
					oGClassCashVoucher.ledgerAccountId = oLXmlVoucher[0].getElementsByTagName("LedgerAccountId")[0].text;
					oGClassCashVoucher.entryDate = oLXmlVoucher[0].getElementsByTagName("EntryDate")[0].text;
					//load Voucher Entry from XML data 
					
					
					oGClassCashVoucher.oGListClassCashVoucherEntry.length = 0;
					oLXmlVoucherEntry = oLXmlVoucher[0].getElementsByTagName("VoucherEntry");
					for(var i=0; i < oLXmlVoucherEntry.length; i++) {
						oClassCashVoucherEntry = new ClassCashVoucherEntry();
						oClassCashVoucherEntry.insertable = oLXmlVoucherEntry[i].getElementsByTagName("Insertable")[0].text;
						oClassCashVoucherEntry.voucherEntryId = oLXmlVoucherEntry[i].getElementsByTagName("VoucherEntryId")[0].text;
						oClassCashVoucherEntry.ledgerEntryId = oLXmlVoucherEntry[i].getElementsByTagName("LedgerEntryId")[0].text;
						oClassCashVoucherEntry.ledgerAccountId = oLXmlVoucherEntry[i].getElementsByTagName("LedgerAccountId")[0].text;
						oClassCashVoucherEntry.ledgerAccountTitle = oLXmlVoucherEntry[i].getElementsByTagName("LedgerAccountTitle")[0].text;
						oClassCashVoucherEntry.amount = oLXmlVoucherEntry[i].getElementsByTagName("Amount")[0].text;
						oClassCashVoucherEntry.narration = oLXmlVoucherEntry[i].getElementsByTagName("Narration")[0].text;
						oGClassCashVoucher.oGListClassCashVoucherEntry[i] = oClassCashVoucherEntry;
					}
					loadFormData();
				} else {
					oGMessages.innerText = "Fail to Load";
				}
			} else {
				oGMessages.innerText = "Fail to load";
			}
		}
		oGForm.voucherPostfix.focus(); 
	} 
	
	function loadFormData() {

		//activate form cntrol
//		oGForm.voucherPrefix.disabled = false;
//		oGForm.voucherPostfix.readOnly = false;
		oGForm.ledgerAccountId[1].disabled = false;
		oGForm.entryDate.disabled = false;
//		oGForm.buttonEntryDate.disabled = false;
		oGForm.SOURCE_LEDGER_ACCOUNT_ID.disabled = false;
		oGForm.SOURCE_NARRATION.disabled = false;
		oGForm.SOURCE_AMOUNT.disabled = false;
		oGForm.SOURCE_BUTTON_ADD.disabled = false;
		//load Form Control data
		oGForm.voucherId.value = oGClassCashVoucher.voucherId;
		setControlSelect(oGForm.voucherPrefix, oGClassCashVoucher.voucherPrefix);
//		oGForm.voucherPrefix.value = oGClassCashVoucher.voucherPrefix;
//		oGForm.voucherPostfix.value = oGClassCashVoucher.voucherPostfix;
		oGForm.ledgerAccountId[0].value = oGClassCashVoucher.ledgerAccountId;
		oGForm.ledgerAccountId[1].value = oGClassCashVoucher.ledgerAccountId;
		oGForm.entryDate.readOnly = false; 
		oGForm.entryDate.value = oGClassCashVoucher.entryDate;
		oGForm.entryDate.readOnly = true; 
		oGForm.ledgerAccountId[1].disabled = true;
		oGForm.submitAction.disabled = false;
		//load data into form
		for(var i=0; i < oGClassCashVoucher.oGListClassCashVoucherEntry.length; i++) {
			addItemOnVoucherEntry(oGClassCashVoucher.oGListClassCashVoucherEntry[i]);
		}

		//deactivate
//		oGForm.voucherPrefix.disabled = true;
//		oGForm.voucherPostfix.readOnly = true;
		
		
//		oGForm.buttonLoadVoucher.disabled = true;
//		oGForm.buttonUnloadVoucher.disabled = false;

		oGForm.hasFormInitialized.value = "Y";		
		oGForm.voucherPostfix.focus();				
	}
	
	function unloadFormData() {
		//unload Form Control data
		oGForm.submitAction.disabled = true;
		oGForm.voucherId.value = "";
//		oGForm.voucherPrefix.value = "";
//		oGForm.voucherPostfix.value = "";
		oGForm.ledgerAccountId[1].value = "";
		oGForm.entryDate.readOnly = false; 
		oGForm.entryDate.value = "";
		oGForm.entryDate.readOnly = true;
		
		oGForm.SOURCE_LEDGER_ACCOUNT_ID.disabled = true;
		oGForm.SOURCE_NARRATION.disabled = true;
		oGForm.SOURCE_AMOUNT.disabled = true;
		oGForm.SOURCE_BUTTON_ADD.disabled = true;
		//unload voucher entries
		oGTableVoucherEntrybody = oGTableVoucherEntry.getElementsByTagName("tbody").item(0);

		var oLRows = oGTableVoucherEntrybody.rows;
		var vLRows = oLRows.length;
		
		for(; vLRows > 0; vLRows--) {
			removeItemOnVoucherEntry(oLRows.item(vLRows-1));

		}

		//deactivate Form Control
//		oGForm.voucherPrefix.disabled = false;
//		oGForm.voucherPostfix.readOnly = false;
		oGForm.ledgerAccountId[1].disabled = true;
		oGForm.entryDate.disabled = true;
//		oGForm.buttonEntryDate.disabled = true;
		
//		oGForm.buttonLoadVoucher.disabled = false;
//		oGForm.buttonUnloadVoucher.disabled = true;
		oGForm.hasFormInitialized.value = "N";
		oGClassCashVoucher = new ClassCashVoucher();
		oGMessages.innerText = " ";
		oGForm.voucherPostfix.focus();
	}

	function updateVoucherEntryItemRefrence(){
		var oListVoucherEntry = oGForm.VOUCHER_ENTRY_VOUCHER_ENTRY_ID;
		if (eval(oListVoucherEntry)) {
			if (eval(oListVoucherEntry.length) && eval(oListVoucherEntry.length) > 1) {
				for (i=0; i < (oListVoucherEntry.length); i++) {
					oGForm.VOUCHER_ENTRY_INSERTABLE[i].name = "voucherEntry["+i+"].insertable";
					oGForm.VOUCHER_ENTRY_VOUCHER_ENTRY_ID[i].name = "voucherEntry["+i+"].voucherEntryId";
					oGForm.VOUCHER_ENTRY_LEDGER_ENTRY_ID[i].name = "voucherEntry["+i+"].ledgerEntryId";
					oGForm.VOUCHER_ENTRY_LEDGER_ACCOUNT_ID[i].name = "voucherEntry["+i+"].ledgerAccountId";
					oGForm.VOUCHER_ENTRY_LEDGER_ACCOUNT_TITLE[i].name = "voucherEntry["+i+"].ledgerAccountTitle";
					oGForm.VOUCHER_ENTRY_NARRATION[i].name="voucherEntry["+i+"].narration";
					oGForm.VOUCHER_ENTRY_AMOUNT[i].name="voucherEntry["+i+"].amount";
				}
			} else {
					oGForm.VOUCHER_ENTRY_INSERTABLE.name="voucherEntry[0].insertable";
					oGForm.VOUCHER_ENTRY_VOUCHER_ENTRY_ID.name = "voucherEntry[0].voucherEntryId";
					oGForm.VOUCHER_ENTRY_LEDGER_ENTRY_ID.name = "voucherEntry[0].ledgerEntryId";
					oGForm.VOUCHER_ENTRY_LEDGER_ACCOUNT_ID.name="voucherEntry[0].ledgerAccountId";
					oGForm.VOUCHER_ENTRY_LEDGER_ACCOUNT_TITLE.name="voucherEntry[0].ledgerAccountTitle";
					oGForm.VOUCHER_ENTRY_NARRATION.name="voucherEntry[0].narration";
					oGForm.VOUCHER_ENTRY_AMOUNT.name="voucherEntry[0].amount";
			}
		}
	}
	
	function removeItemOnVoucherEntry(oRow) {
	//	var index = oRow.rowIndex;
		var collection ;
		collection = GetElementsById(oRow.getElementsByTagName("input"), "VOUCHER_ENTRY_VOUCHER_ENTRY_ID");
		oGClassCashVoucher.removeVoucherEntryIds += (collection[0].value==0 ? "" : ","+collection[0].value) ;
		
		collection = GetElementsById(oRow.getElementsByTagName("input"), "VOUCHER_ENTRY_LEDGER_ENTRY_ID");
		oGClassCashVoucher.removeLedgerEntryIds += (collection[0].value==0 ? "" : ","+collection[0].value) ;
		oRow.removeNode(true);
	}

	document.onload=initializeForm();
	function initializeForm() {

		//unload Form Control data
		if (oGForm.hasFormInitialized.value == "Y") {
			oGForm.submitAction.disabled = false;
			
			oGForm.SOURCE_LEDGER_ACCOUNT_ID.disabled = false;
			oGForm.SOURCE_NARRATION.disabled = false;
			oGForm.SOURCE_AMOUNT.disabled = false;
			oGForm.SOURCE_BUTTON_ADD.disabled = false;
	
			//deactivate Form Control
//			oGForm.voucherPrefix.disabled = true;
//			oGForm.voucherPostfix.readOnly = true;
			oGForm.ledgerAccountId[1].disabled = true;
			oGForm.entryDate.disabled = false;
//			oGForm.buttonEntryDate.disabled = false;
			
//			oGForm.buttonLoadVoucher.disabled = true;
//			oGForm.buttonUnloadVoucher.disabled = false;
			oGForm.hasFormInitialized.value = "N";
		
		} else {
			oGForm.submitAction.disabled = true;
			oGForm.entryDate.value = "";
			oGForm.entryDate.readOnly = true;
			
			oGForm.SOURCE_LEDGER_ACCOUNT_ID.disabled = true;
			oGForm.SOURCE_NARRATION.disabled = true;
			oGForm.SOURCE_AMOUNT.disabled = true;
			oGForm.SOURCE_BUTTON_ADD.disabled = true;
		
			//deactivate Form Control
//			oGForm.voucherPrefix.disabled = false;
//			oGForm.voucherPostfix.readOnly = false;
			oGForm.ledgerAccountId[1].disabled = true;
			oGForm.entryDate.disabled = true;
//			oGForm.buttonEntryDate.disabled = true;
			
//			oGForm.buttonLoadVoucher.disabled = false;
//			oGForm.buttonUnloadVoucher.disabled = true;
			oGForm.hasFormInitialized.value = "N";
			oGClassCashVoucher = new ClassCashVoucher();
			oGMessages.innerText = " ";
			
		}

	}
	
	function saveForm() {
		if (!confirm("Are you sure you want to save.")) {
			return;
		}
		updateVoucherEntryItemRefrence();
		oGForm.removeVoucherEntryIds.value = oGClassCashVoucher.removeVoucherEntryIds;
		oGForm.removeLedgerEntryIds.value = oGClassCashVoucher.removeLedgerEntryIds;
		oGForm.hasFormInitialized.value = "Y";
		oGForm.submitAction.value = "save";
//		oGForm.voucherPrefix.disabled = false;
		
		oGForm.submit();
	}
	
	function addItemOnVoucherEntryFromSource() {
		oLClassCashVoucherEntry = new ClassCashVoucherEntry();
		oLClassCashVoucherEntry.insertable = true;
		oLClassCashVoucherEntry.voucherEntryId = 0;
		oLClassCashVoucherEntry.ledgerEntryId = 0;
		oLClassCashVoucherEntry.ledgerAccountId = oGForm.SOURCE_LEDGER_ACCOUNT_ID.options[oGForm.SOURCE_LEDGER_ACCOUNT_ID.selectedIndex].value;
		oLClassCashVoucherEntry.ledgerAccountTitle = oGForm.SOURCE_LEDGER_ACCOUNT_ID.options[oGForm.SOURCE_LEDGER_ACCOUNT_ID.selectedIndex].text;
		oLClassCashVoucherEntry.narration = oGForm.SOURCE_NARRATION.value;
		oLClassCashVoucherEntry.amount = oGForm.SOURCE_AMOUNT.value;
		addItemOnVoucherEntry(oLClassCashVoucherEntry);
	}
	
	function addItemOnVoucherEntry(oClassCashVoucherEntry) {
		if (oClassCashVoucherEntry == null){
			alert("Voucher entry data is empty.");
			return false;
		}
		
		//get avaiable item detail
		var oListVoucherEntry = GetElementsById(oGForm.elements, "VOUCHER_ENTRY_LEDGER_ACCOUNT_ID");

	    oGTableVoucherEntrybody = oGTableVoucherEntry.getElementsByTagName("tbody").item(0);
   
		oRow=document.createElement("TR");

		//Create column "Action" cell1
		oCell1=document.createElement("TD");
		oCell1.className='LIST_DATA'; 
		a=document.createElement("A");
		a.appendChild(document.createTextNode('D'));
		a.href='#';
		a.attachEvent("onclick", function () {removeItemOnVoucherEntry(GetParentByTagName(window.event.srcElement, "TR"))}); 
		oCell1.appendChild(a);

		oRow.appendChild(oCell1);

		//Create column "Item" cell2
		oCell2=document.createElement("TD");
		oCell2.className='LIST_DATA';
		oCell2.appendChild(document.createTextNode(oClassCashVoucherEntry.ledgerAccountTitle));
		oCell2.appendChild(document.createElement("<input type='hidden' name='voucherEntry[0].insertable' value='"+oClassCashVoucherEntry.insertable+"' id='VOUCHER_ENTRY_INSERTABLE' >")); 
		oCell2.appendChild(document.createElement("<input type='hidden' name='voucherEntry[0].voucherEntryId' value='"+oClassCashVoucherEntry.voucherEntryId+"' id='VOUCHER_ENTRY_VOUCHER_ENTRY_ID'>"));
		oCell2.appendChild(document.createElement("<input type='hidden' name='voucherEntry[0].ledgerEntryId' value='"+oClassCashVoucherEntry.ledgerEntryId+"' id='VOUCHER_ENTRY_LEDGER_ENTRY_ID'>"));
		oCell2.appendChild(document.createElement("<input type='hidden' name='voucherEntry[0].ledgerAccountId' value='"+oClassCashVoucherEntry.ledgerAccountId+"' id='VOUCHER_ENTRY_LEDGER_ACCOUNT_ID'>"));
		oCell2.appendChild(document.createElement("<input type='hidden' name='voucherEntry[0].ledgerAccountTitle' value='"+oClassCashVoucherEntry.ledgerAccountTitle+"' id='VOUCHER_ENTRY_LEDGER_ACCOUNT_TITLE'>")); 
		oRow.appendChild(oCell2);
	
		//Create column "Amount" cell3
		oCell3=document.createElement("TD");
		oCell3.className='LIST_DATA'; 
		txtAreaNarration = document.createElement("<textarea name='voucherEntry[0].narration'  cols='62' rows='2' id='VOUCHER_ENTRY_NARRATION'></textarea>")
		txtAreaNarration.value=oClassCashVoucherEntry.narration;
		txtAreaNarration.cols=62;
		txtAreaNarration.rows=2;
		oCell3.appendChild(txtAreaNarration);
		
		oRow.appendChild(oCell3);
	
		//Create column "Narration" cell4
		oCell4=document.createElement("TD");
		oCell4.className='LIST_DATA'; 
		oCell4.appendChild(document.createElement("<input type='text' name='voucherEntry[0].amount' value='"+oClassCashVoucherEntry.amount+"' size='20' maxlength='11' id='VOUCHER_ENTRY_AMOUNT'>"));
		oRow.appendChild(oCell4);
	
		oGTableVoucherEntrybody.appendChild(oRow);

		oGTableVoucherEntry.appendChild(oGTableVoucherEntrybody);

		return true;
	}
-->
</script>
	
