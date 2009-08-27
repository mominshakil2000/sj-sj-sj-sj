<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>

 
<%@ page import=" java.util.*
				, com.netxs.Zewar.Lookup.*;"%> 


<script>
	// lookup Objects 
	var lookupItemMetal = new Array ();
	var lookupWeightUnit = new Array ();
	var lookupWastageUnit = new Array ();
	
	// Initialize the lookup Objects 			
	<c:if test="${lookupItemMetal != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupItemMetal}" varStatus="status" >
			lookupItemMetal[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if> 

	<c:if test="${lookupWeightUnit != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupWeightUnit}" varStatus="status" >
			lookupWeightUnit[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if> 

	<c:if test="${lookupWastageUnit != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupWastageUnit}" varStatus="status" >
			lookupWastageUnit[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if>

</script>	

<html:form action="/Inventory/CreateVendorStockVoucher.do" method="post" styleId="myForm" onsubmit="updateFormObjects();">
 <html:hidden property="hasFormInitialized" />
 <html:hidden property="inventoryVendorStockVoucherId" />
 <html:hidden property="inventoryAccountIdCompany" />
 <html:hidden property="removeInventoryVendorStockVoucherItemIds" />
 <html:hidden property="removeInventoryMetalEntryIdsIn" />
 <html:hidden property="removeInventoryMetalEntryIdsOut" />
 <input type="hidden" name="submitAction" value="">
 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:350;">New Stock Issue/Return Voucher</span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:260;">&nbsp;</span>
 </div>
 <div class="BOX" style="width:950px;">
		<span id="messages" class="WARNING">
			<logic:messagesPresent >
				<ul>
		   		<html:messages id="error">
		 	  		<li><c:out value="${error}" /></li>
			   	</html:messages>
		 		</ul>
			</logic:messagesPresent>		
		</span>

		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="900">
			<tr>
				<td width="170"  class="FORM_CAPTION">Issue / Return </td>
				<td width="730" class="FORM_CONTROL">
					<html:select property="voucherPrefix">
						<html:option value="VI">Issue Voucher</html:option>
						<html:option value="VR">Return Voucher</html:option>
					</html:select>
					<html:hidden property="voucherPostfix" value="0" />
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Vendor Account</td>
				<td class="FORM_CONTROL">
					<html:select property="inventoryAccountIdVendor">
						<html:options collection="lookupInventoryAccount" property="id" labelProperty="label"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="FORM_CAPTION"> Date</td>
				<td class="FORM_CONTROL">
					<html:text property="entryDate" size="12" readonly="true" />
					<input type="button" name="buttonEntryDate" value=" ... ">
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
		</table>

		<!-- Metal Items-->
		<br>
		<div id="idItemMetalList" class="FORM_AREA">
			<div class="FORM_AREA"><span style="width:450px">Metals</span><span style="width:470;text-align:right;"><a href="#" onclick="addItemOnVoucherItemMetalFromSource(new ClassVoucherItemMetal());">Add More</a></span></div>
			<div style="width:945px;" class="LIST_AREA">
				<span style="width:30px;" class="LIST_HEADER">&nbsp;</span><span style="width:155px;" class="LIST_HEADER">Item Out</span><span style="width:100px;" class="LIST_HEADER">Weight</span><span style="width:160px;" class="LIST_HEADER">Alloy</span><span style="width:75px;" class="LIST_HEADER">Net Wgt</span><span style="width:155px;" class="LIST_HEADER">Item In</span><span style="width:270px;" class="LIST_HEADER">Comments</span>
			</div>
			<div style="width:945px; height:200px;padding:0px; margin:0px; overflow:auto;" >
				<table class="LIST_AREA" cellpadding="0" cellspacing="1" id="TBL1_DISTINATION" width="925" >
				</table>
			</div>
		</div>
		<br>
		<div align="right"><html:button property="submitAction" value="  Save  " onclick="javascript:saveForm();" /></div>
</html:form>

<script language="javascript">
<!--
	// variable prefix oG stand for Object in Global scope
	// variable prefix oF stand for Object in Form
   	// function name start with Class define the class
   
	function ClassVoucher() {
		this.inventoryVendorStockVoucherId = "";
		this.voucherPrefix = "";
		this.voucherPostfix = "";
		this.inventoryAccountIdVendor = "";
		this.inventoryAccountIdCompany = "";
		this.entryDate = "";
		this.comments = "";
		this.removeInventoryVendorStockVoucherItemIds = 0;
		this.removeInventoryMetalEntryIdsIn = 0;
		this.removeInventoryMetalEntryIdsOut = 0;
		this.oGListClassVoucherItemMetal = new Array();
	}

	function ClassVoucherItemMetal() {
		this.insertable = true;
		this.inventoryVendorStockVoucherItemId = 0;
		this.inventoryMetalEntryIdIn = 0;
		this.inventoryMetalEntryIdOut = 0;
		this.issueItemId = 0;
		this.actualItemId = 0;
		this.issueWeight = 0.0;
		this.alloyRate = 0.0;
		this.alloyWastageUnitId = 2;
		this.actualWeight = 0.0;
		this.comments = "";
	}
	
	var oGXmlHttp;
	var oGXmlDoc;
	var oGMessages = document.getElementById("messages");
	
	var oGForm = document.getElementById("myForm");

	var oGTableVoucherItemMetal = document.getElementById("TBL1_DISTINATION");	

	var oGClassVoucher = new ClassVoucher();

	oGClassVoucher.inventoryVendorStockVoucherId = '<c:out value="${inventoryVendorStockVoucherForm.inventoryVendorStockVoucherId}"/>';
	oGClassVoucher.voucherPrefix = '<c:out value="${inventoryVendorStockVoucherForm.voucherPrefix}"/>'; 
	oGClassVoucher.voucherPostfix = '<c:out value="${inventoryVendorStockVoucherForm.voucherPostfix}"/>';
	oGClassVoucher.inventoryAccountIdVendor = '<c:out value="${inventoryVendorStockVoucherForm.inventoryAccountIdVendor}"/>';
	oGClassVoucher.inventoryAccountIdCompany = '<c:out value="${inventoryVendorStockVoucherForm.inventoryAccountIdCompany}"/>';
	oGClassVoucher.entryDate = '<c:out value="${inventoryVendorStockVoucherForm.entryDate}"/>';

	<c:forEach var="metal" items="${inventoryVendorStockVoucherForm.voucherItemMetalList}" varStatus="metalStatus" >
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>] = new ClassVoucherItemMetal();
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].insertable = '<c:out value="${metal.insertable}"/>';
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].inventoryVendorStockVoucherItemId = '<c:out value="${metal.inventoryVendorStockVoucherItemId}"/>';
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].inventoryMetalEntryIdIn = '<c:out value="${metal.inventoryMetalEntryIdIn}"/>';
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].inventoryMetalEntryIdOut = '<c:out value="${metal.inventoryMetalEntryIdOut}"/>';
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].issueItemId = '<c:out value="${metal.issueItemId}"/>';
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].actualItemId = '<c:out value="${metal.actualItemId}"/>';
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].issueWeight = '<c:out value="${metal.issueWeight}"/>';
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].alloyRate = '<c:out value="${metal.alloyRate}"/>';
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].alloyWastageUnitId = '<c:out value="${metal.alloyWastageUnitId}"/>';
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].actualWeight= '<c:out value="${metal.actualWeight}"/>';
		oGClassVoucher.oGListClassVoucherItemMetal[<c:out value="${metalStatus.index}"/>].comments = '<c:out value="${metal.comments}"/>'; 
		
	</c:forEach>

	function loadFormData() {

		//activate form cntrol
		oGForm.inventoryAccountIdVendor.disabled = false;
		oGForm.entryDate.disabled = false;
		oGForm.buttonEntryDate.disabled = false;


		//load Form Control data
		oGForm.inventoryVendorStockVoucherId.value = oGClassVoucher.inventoryVendorStockVoucherId;
		setControlSelect(oGForm.voucherPrefix, oGClassVoucher.voucherPrefix);


		oGForm.inventoryAccountIdVendor.value = oGClassVoucher.inventoryAccountIdVendor;
		oGForm.entryDate.readOnly = false; 
		oGForm.entryDate.value = oGClassVoucher.entryDate;
		oGForm.entryDate.readOnly = true; 
		oGForm.submitAction.disabled = false;
		//load data into form
		for(var i=0; i < oGClassVoucher.oGListClassVoucherItemMetal.length; i++) {
			addItemOnVoucherItemMetal(oGClassVoucher.oGListClassVoucherItemMetal[i]);
		}

		//deactivate
//		oGForm.voucherPrefix.disabled = true;
//		oGForm.voucherPostfix.readOnly = true;
		
		
//		oGForm.buttonLoadVoucher.disabled = true;
//		oGForm.buttonUnloadVoucher.disabled = false;

		oGForm.hasFormInitialized.value = "Y";		
		
	}
	

	function updateVoucherItemRefrence(){
		var oListVoucherItemMetal = GetElementsById(oGForm, "VOUCHER_ITEM_METAL_INVENTORY_VENDOR_STOCK_VOUCHER_ITEM_ID");
		if (eval(oListVoucherItemMetal)) {
			if (eval(oListVoucherItemMetal.length) && eval(oListVoucherItemMetal.length) > 1) {
				for (i=0; i < (oListVoucherItemMetal.length); i++) {
					oGForm.VOUCHER_ITEM_METAL_INSERTABLE[i].name = "voucherItemMetal["+i+"].insertable";
					oGForm.VOUCHER_ITEM_METAL_INVENTORY_VENDOR_STOCK_VOUCHER_ITEM_ID[i].name = "voucherItemMetal["+i+"].inventoryVendorStockVoucherItemId";
					oGForm.VOUCHER_ITEM_METAL_INVENTORY_METAL_ENTRY_ID_IN[i].name = "voucherItemMetal["+i+"].inventoryMetalEntryIdIn";
					oGForm.VOUCHER_ITEM_METAL_INVENTORY_METAL_ENTRY_ID_OUT[i].name = "voucherItemMetal["+i+"].inventoryMetalEntryIdOut";
					oGForm.VOUCHER_ITEM_METAL_ISSUE_ITEM_ID[i].name = "voucherItemMetal["+i+"].issueItemId";
					oGForm.VOUCHER_ITEM_METAL_ACTUAL_ITEM_ID[i].name = "voucherItemMetal["+i+"].actualItemId";
					oGForm.VOUCHER_ITEM_METAL_ISSUE_WEIGHT[i].name = "voucherItemMetal["+i+"].issueWeight";
					oGForm.VOUCHER_ITEM_METAL_ALLOY_RATE[i].name = "voucherItemMetal["+i+"].alloyRate";
					oGForm.VOUCHER_ITEM_METAL_ALLOY_WASTAGE_UNIT_ID[i].name = "voucherItemMetal["+i+"].alloyWastageUnitId";
					oGForm.VOUCHER_ITEM_METAL_ACTUAL_WEIGHT[i].name = "voucherItemMetal["+i+"].actualWeight";
					oGForm.VOUCHER_ITEM_METAL_COMMENTS[i].name = "voucherItemMetal["+i+"].comments";
				}
			} else if (eval(oListVoucherItemMetal.length) && eval(oListVoucherItemMetal.length) == 1) {
					oGForm.VOUCHER_ITEM_METAL_INSERTABLE.name = "voucherItemMetal[0].insertable";
	 				oGForm.VOUCHER_ITEM_METAL_INVENTORY_VENDOR_STOCK_VOUCHER_ITEM_ID.name = "voucherItemMetal[0].inventoryVendorStockVoucherItemId";
					oGForm.VOUCHER_ITEM_METAL_INVENTORY_METAL_ENTRY_ID_IN.name = "voucherItemMetal[0].inventoryMetalEntryIdIn";
					oGForm.VOUCHER_ITEM_METAL_INVENTORY_METAL_ENTRY_ID_OUT.name = "voucherItemMetal[0].inventoryMetalEntryIdOut";
					oGForm.VOUCHER_ITEM_METAL_ISSUE_ITEM_ID.name = "voucherItemMetal[0].issueItemId";
					oGForm.VOUCHER_ITEM_METAL_ACTUAL_ITEM_ID.name = "voucherItemMetal[0].actualItemId";
					oGForm.VOUCHER_ITEM_METAL_ISSUE_WEIGHT.name = "voucherItemMetal[0].issueWeight";
					oGForm.VOUCHER_ITEM_METAL_ALLOY_RATE.name = "voucherItemMetal[0].alloyRate";
					oGForm.VOUCHER_ITEM_METAL_ALLOY_WASTAGE_UNIT_ID.name = "voucherItemMetal[0].alloyWastageUnitId";
					oGForm.VOUCHER_ITEM_METAL_ACTUAL_WEIGHT.name = "voucherItemMetal[0].actualWeight";
					oGForm.VOUCHER_ITEM_METAL_COMMENTS.name = "voucherItemMetal[0].comments";

			}
		}
	}
	
	function removeItemOnVoucherItemMetal(oRow) {
	//	var index = oRow.rowIndex;
		var collection ;												  
		collection = GetElementsById(oRow.getElementsByTagName("input"), "VOUCHER_ITEM_METAL_INVENTORY_VENDOR_STOCK_VOUCHER_ITEM_ID");
		oGClassVoucher.removeInventoryVendorStockVoucherItemIds += (collection[0].value==0 ? "" : ","+collection[0].value) ;
//		alert(collection[0].value);

		collection = GetElementsById(oRow.getElementsByTagName("input"), "VOUCHER_ITEM_METAL_INVENTORY_METAL_ENTRY_ID_IN");
		oGClassVoucher.removeInventoryMetalEntryIdsIn += (collection[0].value==0 ? "" : ","+collection[0].value) ;
//		alert(collection[0].value);

		collection = GetElementsById(oRow.getElementsByTagName("input"), "VOUCHER_ITEM_METAL_INVENTORY_METAL_ENTRY_ID_OUT");
		oGClassVoucher.removeInventoryMetalEntryIdsOut += (collection[0].value==0 ? "" : ","+collection[0].value) ;
//		alert(collection[0].value);
		
		oRow.removeNode(true);
	}

	document.onload=initializeForm();
	function initializeForm() {
		//unload Form Control data
		if (oGForm.hasFormInitialized.value == "Y") {
			oGForm.submitAction.disabled = false;
			//deactivate Form Control
			oGForm.inventoryAccountIdVendor.disabled = false;
			oGForm.entryDate.disabled = false;
			oGForm.buttonEntryDate.disabled = false;
			oGForm.hasFormInitialized.value = "N";
			loadFormData();
		} else {
			oGForm.submitAction.disabled = true;
			oGForm.entryDate.value = "";
			oGForm.entryDate.readOnly = true;
			
		
			//deactivate Form Control
			oGForm.inventoryAccountIdVendor.disabled = true;
			oGForm.entryDate.disabled = true;
			oGForm.buttonEntryDate.disabled = true;
			
			oGForm.hasFormInitialized.value = "N";
			oGClassVoucher = new ClassVoucher();
			oGMessages.innerText = " ";
			
		}

	}
	function saveForm(userAction) {
		if (!confirm("Are you sure you want to save.")) {
			return;
		}
		updateVoucherItemRefrence();
		oGForm.removeInventoryVendorStockVoucherItemIds.value = oGClassVoucher.removeInventoryVendorStockVoucherItemIds;
		oGForm.removeInventoryMetalEntryIdsIn.value = oGClassVoucher.removeInventoryMetalEntryIdsIn;
		oGForm.removeInventoryMetalEntryIdsOut.value = oGClassVoucher.removeInventoryMetalEntryIdsOut;
		oGForm.hasFormInitialized.value = "Y";
		oGForm.submitAction.value = userAction;
//		alert(oGForm.removeInventoryVendorStockVoucherItemIds.value);
//		alert(oGForm.removeInventoryMetalEntryIdsOut.value);
//		alert(oGForm.removeInventoryMetalEntryIdsIn.value);
		oGForm.submit();
	}
	
	function addItemOnVoucherItemMetalFromSource() {
		
		addItemOnVoucherItemMetal (new ClassVoucherItemMetal());
	}
	
	function addItemOnVoucherItemMetal(oClassVoucherItemMetal) {
		if (!eval(oClassVoucherItemMetal)) {
			alert ('illegal Object : Item Metal.');
			return;
		}

		oPlaceHolder = oGTableVoucherItemMetal.getElementsByTagName("tbody").item(0);

		oTr = document.createElement('tr');
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=30;
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItemMetal[0].insertable" 				id="VOUCHER_ITEM_METAL_INSERTABLE" 				value="'+oClassVoucherItemMetal.insertable+'" >'));
																																			   
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItemMetal[0].inventoryVendorStockVoucherItemId" 	id="VOUCHER_ITEM_METAL_INVENTORY_VENDOR_STOCK_VOUCHER_ITEM_ID" value="'+oClassVoucherItemMetal.inventoryVendorStockVoucherItemId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItemMetal[0].inventoryMetalEntryIdIn" 	id="VOUCHER_ITEM_METAL_INVENTORY_METAL_ENTRY_ID_IN" value="'+oClassVoucherItemMetal.inventoryMetalEntryIdIn+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItemMetal[0].inventoryMetalEntryIdOut" 	id="VOUCHER_ITEM_METAL_INVENTORY_METAL_ENTRY_ID_OUT" value="'+oClassVoucherItemMetal.inventoryMetalEntryIdOut+'" >'));				
				oA = document.createElement("a");
					oA.appendChild(document.createTextNode('D'));
					oA.href = '#';
					oA.attachEvent("onclick", function () {removeItemOnVoucherItemMetal(GetParentByTagName(window.event.srcElement, "TR"))});
					oTd.appendChild(oA)
				oTr.appendChild(oTd);
			
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=155;
				oTd.appendChild(createSelect('VOUCHER_ITEM_METAL_ISSUE_ITEM_ID', 'voucherItemMetal[0].issueItemId', lookupItemMetal, oClassVoucherItemMetal.issueItemId));
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=100;
				oInput = document.createElement('<input type="text" name="voucherItemMetal[0].issueWeight" id="VOUCHER_ITEM_METAL_ISSUE_WEIGHT"  size="4" value="'+oClassVoucherItemMetal.issueWeight+'">');
				oInput.attachEvent("onchange", function () {	calculateMetalNetWeight(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTd.appendChild(document.createTextNode('Grams'));
				oTr.appendChild(oTd);


			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=160;
			
				oInput = document.createElement('<input type="text" name="voucherItemMetal[0].alloyRate" id="VOUCHER_ITEM_METAL_ALLOY_RATE"  size="4" value="'+oClassVoucherItemMetal.alloyRate+'">');
				oInput.attachEvent("onchange", function () {	calculateMetalNetWeight(window.event.srcElement.parentElement.parentElement)});
					oTd.appendChild(oInput);
					oSelect = createSelect('VOUCHER_ITEM_METAL_ALLOY_WASTAGE_UNIT_ID', 'voucherItemMetal[0].alloyWastageUnitId', lookupWastageUnit, oClassVoucherItemMetal.alloyWastageUnitId)
					oTd.appendChild(oSelect);
					oSelect.attachEvent("onchange", function () {	calculateMetalNetWeight(window.event.srcElement.parentElement.parentElement)});
					oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=75;
				oInput = document.createElement('<input type="text" name="voucherItemMetal[0].actualWeight" id="VOUCHER_ITEM_METAL_ACTUAL_WEIGHT"  size="4" value="'+oClassVoucherItemMetal.actualWeight+'">');
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=155;
				oTd.appendChild(createSelect('VOUCHER_ITEM_METAL_ACTUAL_ITEM_ID', 'voucherItemMetal[0].actualItemId', lookupItemMetal, oClassVoucherItemMetal.actualItemId));
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=250;
				oTextArea = document.createElement('<textarea name="voucherItemMetal[0].comments" id="VOUCHER_ITEM_METAL_COMMENTS" ></textarea>');
				oTextArea.value=oClassVoucherItemMetal.comments;								
				oTextArea.cols=35;
				oTextArea.rows=1;
				oTd.appendChild(oTextArea);				
				oTr.appendChild(oTd);	
		oPlaceHolder.appendChild(oTr);
	}

	
	function calculateMetalNetWeight(rowObj) {
		var objWeight = rowObj.all("VOUCHER_ITEM_METAL_ISSUE_WEIGHT",0);
		var objWastageRate = rowObj.all("VOUCHER_ITEM_METAL_ALLOY_RATE",0);
		var	objWastageUnit = rowObj.all("VOUCHER_ITEM_METAL_ALLOY_WASTAGE_UNIT_ID",0).options[rowObj.all("VOUCHER_ITEM_METAL_ALLOY_WASTAGE_UNIT_ID",0).selectedIndex];
		objNetWeight = rowObj.all("VOUCHER_ITEM_METAL_ACTUAL_WEIGHT",0);

		if (objWastageUnit.value == 1) {
			objNetWeight.value = parseFloat(objWeight.value * objWastageRate.value / 100) + parseFloat(objWeight.value) ;
		}	else {
			objNetWeight.value = parseFloat(objWeight.value * objWastageRate.value / 96) + parseFloat(objWeight.value) ;
		}
		
	}
-->
</script>
