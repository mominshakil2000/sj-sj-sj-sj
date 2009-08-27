<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
 
<%@ page import=" java.util.*
								, com.netxs.Zewar.Lookup.*;"%>
<script>
<!--
	//Lookup Items 
	var lookupItemGem = new Array ();
	<c:if test="${lookupItemGem != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupItemGem}" varStatus="status" >
			lookupItemGem[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if> 

	var lookupItemMetal = new Array ();
	<c:if test="${lookupItemMetal != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupItemMetal}" varStatus="status" >
			lookupItemMetal[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if> 

	var lookupItemJewellery = new Array ();
	<c:if test="${lookupItemJewellery != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupItemJewellery}" varStatus="status" >
			lookupItemJewellery[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if>

	var lookupWeightUnit = new Array ();
	<c:if test="${lookupWeightUnit != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupWeightUnit}" varStatus="status" >
			lookupWeightUnit[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if> 
	
	var lookupItemValueCalculateBy = new Array ();
	<c:if test="${lookupItemValueCalculateBy != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupItemValueCalculateBy}" varStatus="status" >
			lookupItemValueCalculateBy[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if>
-->
</script>	

<html:form action="/Inventory/CreateSalesMetalVoucher.do" method="post" styleId="myForm" onsubmit="updateFormObjects();">
 <html:hidden property="hasFormInitialized" />
 <html:hidden property="inventorySalesVoucherId" />
 <html:hidden property="inventoryAccountIdCompany" />
 <html:hidden property="itemGroupId" />
 <html:hidden property="removeInventorySalesVoucherItemIds" />
 <html:hidden property="removeInventoryItemEntryIdsOut" />
 <input type="hidden" name="submitAction" value="">

 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;">New Sales Metal Voucher</span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
 </div>
 <div class="BOX" style="width:970px;">
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
				<td class="FORM_CAPTION">Voucher</td>
				<td class="FORM_CONTROL">
					<html:text property="voucherPrefix" size="8" readonly="true"/>
					<html:hidden property="voucherPostfix" />
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Entry Date</td>
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
			<tr>
				<td class="FORM_CAPTION">Comments</td>
				<td class="FORM_CONTROL"><html:textarea property="comments" cols="40" rows="4"></html:textarea></td>
			</tr>
		</table>

		<!-- Voucher Items-->
		<br>
		<div class="FORM_AREA">
			<div class="FORM_AREA"><span style="width:475px">Metals</span><span style="width:475;text-align:right;"><a href="#" onclick="addItemOnVoucherItemFromSource(new ClassVoucherItem());">Add More</a></span></div>
			<div style="width:953px;" class="LIST_AREA">
				<span style="width:30px;" class="LIST_HEADER">&nbsp;</span><span style="width:210px;" class="LIST_HEADER">Items</span><span style="width:95px;" class="LIST_HEADER">&nbsp;</span><span style="width:70px;" class="LIST_HEADER">&nbsp;</span><span style="width:125px;" class="LIST_HEADER">Weight</span><span style="width:70px;" class="LIST_HEADER">Rate</span><span style="width:85px;" class="LIST_HEADER">Value</span><span style="width:268px;" class="LIST_HEADER">&nbsp;</span>
			</div>
			<div style="width:953px; height:200px;padding:0px; margin:0px; overflow:auto;" >
				<table class="LIST_AREA" cellpadding="0" cellspacing="1" id="TBL_VOUCHER_ITEM" width="935" >
				</table>
			</div>
		</div>
		<br>
		<div align="right"><html:button property="submitAction" value="  Save  " onclick="javascript:saveForm();" /></div>
	</div>
</html:form>



<script language="javascript">
<!--
	// variable prefix oG stand for Object in Global scope
	// variable prefix oF stand for Object in Form
   	// function name start with Class define the class
   
	function ClassVoucher() {
		this.inventorySalesVoucherId = "";
		this.itemGroupId = "1";
		this.voucherPrefix = "";
		this.voucherPostfix = "";
		this.inventoryAccountIdCompany = "";
		this.entryDate = "";
		this.comments = "";
		this.removeInventorySalesVoucherItemIds = 0;
		this.removeInventoryItemEntryIdsOut = 0;
		this.oGListClassVoucherItem = new Array();
	}

	function ClassVoucherItem() {
		this.insertable = true;
		this.inventorySalesVoucherItemId = 0;
		this.inventoryItemEntryIdOut = 0;
		this.itemId = 0;
		this.quantity = 0;
		this.weight = 0.0;
		this.weightUnitId = 1; 
		this.rate = 0.0;	
		this.itemValueCalculateBy = 2;
		this.itemValue = 0.0;
		this.ledgerEntryId = 0;
	}

	var oGXmlHttp;
	var oGXmlDoc;
	var oGMessages = document.getElementById("messages");
	
	var oGForm = document.getElementById("myForm");

	var oGTableVoucherItem = document.getElementById("TBL_VOUCHER_ITEM");	

	var oGClassVoucher = new ClassVoucher();

	oGClassVoucher.inventorySalesVoucherId = '<c:out value="${inventorySalesVoucherForm.inventorySalesVoucherId}"/>';
	oGClassVoucher.itemGroupId = '<c:out value="${inventorySalesVoucherForm.itemGroupId}"/>';
	oGClassVoucher.voucherPrefix = '<c:out value="${inventorySalesVoucherForm.voucherPrefix}"/>';
	oGClassVoucher.voucherPostfix = '<c:out value="${inventorySalesVoucherForm.voucherPostfix}"/>';
	oGClassVoucher.inventoryAccountIdCompany = '<c:out value="${inventorySalesVoucherForm.inventoryAccountIdCompany}"/>';
	oGClassVoucher.entryDate = '<c:out value="${inventorySalesVoucherForm.entryDate}"/>';
	oGClassVoucher.comments = '<c:out value="${inventorySalesVoucherForm.comments}"/>';

	// Voucher Item					
	<c:forEach var="item" items="${inventorySalesVoucherForm.voucherItemList}" varStatus="itemStatus" >
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>] = new ClassVoucherItem();
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].insertable = '<c:out value="${item.insertable}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].inventorySalesVoucherItemId  = '<c:out value="${item.inventorySalesVoucherItemId }"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].inventoryItemEntryIdOut = '<c:out value="${item.inventoryItemEntryIdOut}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].ledgerEntrytId = '<c:out value="${item.ledgerEntryId}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].itemId = '<c:out value="${item.itemId}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].quantity = '<c:out value="${item.quantity}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].weight = '<c:out value="${item.weight}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].weightUnitId = '<c:out value="${item.weightUnitId}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].rate = '<c:out value="${item.rate}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].itemValueCalculateBy = '<c:out value="${item.itemValueCalculateBy}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].itemValue= '<c:out value="${item.itemValue}"/>';
	</c:forEach>

	
	function loadFormData() {

		//activate form cntrol
		oGForm.entryDate.disabled = false;
		oGForm.buttonEntryDate.disabled = false;
		//load Form Control data
		oGForm.inventorySalesVoucherId.value = oGClassVoucher.inventorySalesVoucherId;
		oGForm.itemGroupId.value = oGClassVoucher.itemGroupId;
		oGForm.voucherPrefix.value = oGClassVoucher.voucherPrefix;
		oGForm.voucherPostfix.value = oGClassVoucher.voucherPostfix;
		oGForm.comments.value = oGClassVoucher.comments;
		oGForm.entryDate.readOnly = false; 
		oGForm.entryDate.value = oGClassVoucher.entryDate;
		oGForm.entryDate.readOnly = true; 
		oGForm.submitAction[1].disabled = false;
		//load data into form
		for(var i=0; i < oGClassVoucher.oGListClassVoucherItem.length; i++) {
			addItemOnVoucherItem(oGClassVoucher.oGListClassVoucherItem[i]);
		}

		oGForm.hasFormInitialized.value = "Y";		
//		oGForm.voucherPostfix.focus();				
	}
	
	function unloadFormData() {
		//unload Form Control data
		oGForm.submitAction[1].disabled = true;
		oGForm.inventorySalesVoucherId.value = "";

		oGForm.entryDate.readOnly = false; 
		oGForm.entryDate.value = "";
		oGForm.entryDate.readOnly = true;
		
		//unload voucher entries
		var oLRows = oGTableVoucherItem.getElementsByTagName("tbody").item(0).rows;

		var vLRows = oLRows.length;

		for(; vLRows > 0; vLRows--) {
			removeItemOnVoucherItem(oLRows.item(vLRows-1));

		}

		//deactivate Form Control

		oGForm.entryDate.disabled = true;
		oGForm.buttonEntryDate.disabled = true;
		
		oGForm.hasFormInitialized.value = "N";
		oGClassVoucher = new ClassVoucher();
		oGMessages.innerText = " ";
//		oGForm.voucherPostfix.focus();
	}

	function updateVoucherItemRefrence(){
		var oListVoucherItem = GetElementsById(oGForm, "VOUCHER_ITEM_INVENTORY_SALES_VOUCHER_ITEM_ID");
		if (eval(oListVoucherItem)) {
			if (eval(oListVoucherItem.length) && eval(oListVoucherItem.length) > 1) {
				for (i=0; i < (oListVoucherItem.length); i++) {
					oGForm.VOUCHER_ITEM_INSERTABLE[i].name = "voucherItem["+i+"].insertable";
					oGForm.VOUCHER_ITEM_INVENTORY_SALES_VOUCHER_ITEM_ID[i].name = "voucherItem["+i+"].inventorySalesVoucherItemId";
					oGForm.VOUCHER_ITEM_INVENTORY_ITEM_ENTRY_ID_IN[i].name = "voucherItem["+i+"].inventoryItemEntryIdOut";
					oGForm.VOUCHER_ITEM_LEDGER_ENTRY_ID[i].name = "voucherItem["+i+"].ledgerEntryId";
					oGForm.VOUCHER_ITEM_ITEM_ID[i].name = "voucherItem["+i+"].itemId";
					oGForm.VOUCHER_ITEM_QUANTITY[i].name = "voucherItem["+i+"].quantity";
					oGForm.VOUCHER_ITEM_WEIGHT[i].name = "voucherItem["+i+"].weight";
					oGForm.VOUCHER_ITEM_WEIGHT_UNIT_ID[i].name = "voucherItem["+i+"].weightUnitId";
					oGForm.VOUCHER_ITEM_RATE[i].name = "voucherItem["+i+"].rate";
					oGForm.VOUCHER_ITEM_ITEM_VALUE_CALCULATE_BY[i].name = "voucherItem["+i+"].itemValueCalculateBy";
					oGForm.VOUCHER_ITEM_ITEM_VALUE[i].name = "voucherItem["+i+"].itemValue";
				}
						
			} else if (eval(oListVoucherItem.length) && eval(oListVoucherItem.length) == 1) {
					oGForm.VOUCHER_ITEM_INSERTABLE.name = "voucherItem[0].insertable";
					oGForm.VOUCHER_ITEM_INVENTORY_SALES_VOUCHER_ITEM_ID.name = "voucherItem[0].inventorySalesVoucherItemId";
					oGForm.VOUCHER_ITEM_INVENTORY_ITEM_ENTRY_ID_IN.name = "voucherItem[0].inventoryItemEntryIdOut";
					oGForm.VOUCHER_ITEM_LEDGER_ENTRY_ID.name = "voucherItem[0].ledgerEntryId";
					oGForm.VOUCHER_ITEM_ITEM_ID.name = "voucherItem[0].itemId";
					oGForm.VOUCHER_ITEM_QUANTITY.name = "voucherItem[0].quantity";
					oGForm.VOUCHER_ITEM_WEIGHT.name = "voucherItem[0].weight";
					oGForm.VOUCHER_ITEM_WEIGHT_UNIT_ID.name = "voucherItem[0].weightUnitId";
					oGForm.VOUCHER_ITEM_RATE.name = "voucherItem[0].rate";
					oGForm.VOUCHER_ITEM_ITEM_VALUE_CALCULATE_BY.name = "voucherItem[0].itemValueCalculateBy";
					oGForm.VOUCHER_ITEM_ITEM_VALUE.name = "voucherItem[0].itemValue";
			}
		}
	}
	
	function removeItemOnVoucherItem(oRow) {
	//	var index = oRow.rowIndex;
		var collection ;												  

		collection = GetElementsById(oRow.getElementsByTagName("input"), "VOUCHER_ITEM_INVENTORY_SALES_VOUCHER_ITEM_ID");
		oGClassVoucher.removeInventorySalesVoucherItemIds+= (collection[0].value==0 ? "" : ","+collection[0].value) ;
//		alert(collection[0].value);

		collection = GetElementsById(oRow.getElementsByTagName("input"), "VOUCHER_ITEM_INVENTORY_ITEM_ENTRY_ID_IN");
		oGClassVoucher.removeInventoryItemEntryIdsOut+= (collection[0].value==0 ? "" : ","+collection[0].value) ;
//		alert(collection[0].value);
		
		oRow.removeNode(true);
	}

	function initializeForm() {
		//unload Form Control data
		if (oGForm.hasFormInitialized.value == "Y") {
			oGForm.submitAction[1].disabled = false;
			//deactivate Form Control
			oGForm.entryDate.disabled = false;
			oGForm.buttonEntryDate.disabled = false;
			loadFormData();
		} else {
			oGForm.submitAction[1].disabled = true;
			oGForm.entryDate.value = "";
			oGForm.entryDate.readOnly = true;
			//deactivate Form Control
			oGForm.entryDate.disabled = true;
			oGForm.buttonEntryDate.disabled = true;
			oGClassVoucher = new ClassVoucher();
			oGMessages.innerText = " ";
		}

	}
	
	function saveForm(userAction) {
		if (!confirm("Are you sure you want to save.")) {
			return;
		}
		updateVoucherItemRefrence();

		oGForm.removeInventorySalesVoucherItemIds.value = oGClassVoucher.removeInventorySalesVoucherItemIds;
		oGForm.removeInventoryItemEntryIdsOut.value = oGClassVoucher.removeInventoryItemEntryIdsOut;
		oGForm.hasFormInitialized.value = "Y";
		oGForm.submitAction[0].value = userAction;

//		alert(oGForm.removeInventoryItemEntryIdsOut.value);
//		alert(oGForm.removeInventoryItemEntryIdsOut.value);

		oGForm.submit();
	}
	
	function addItemOnVoucherItemFromSource() {
		
		addItemOnVoucherItem (new ClassVoucherItem());
	}
	
	function addItemOnVoucherItem(oClassVoucherItem) {
		if (!eval(oClassVoucherItem)) {
			alert ('illegal Object : Item Metal.');
			return;
		}

		oPlaceHolder = oGTableVoucherItem.getElementsByTagName("tbody").item(0);

		oTr = document.createElement('tr');
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=30;
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItem[].inventoryItemEntryIdOut" id="VOUCHER_ITEM_INVENTORY_ITEM_ENTRY_ID_IN" value="'+oClassVoucherItem.inventoryItemEntryIdOut+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItem[].ledgerEntryId" id="VOUCHER_ITEM_LEDGER_ENTRY_ID" value="'+oClassVoucherItem.ledgerEntryId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItem[].inventorySalesVoucherItemId" id="VOUCHER_ITEM_INVENTORY_SALES_VOUCHER_ITEM_ID" value="'+oClassVoucherItem.inventorySalesVoucherItemId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItem[].insertable" id="VOUCHER_ITEM_INSERTABLE" value="'+oClassVoucherItem.insertable+'" >'));
				oA = document.createElement("a");
					oA.appendChild(document.createTextNode('D'));
					oA.href = '#';
					oA.attachEvent("onclick", function () { removeItemOnVoucherItem(GetParentByTagName(window.event.srcElement, "TR"))});
					oTd.appendChild(oA)
				oTr.appendChild(oTd);
			
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=215;
				oTd.appendChild(createSelect('VOUCHER_ITEM_ITEM_ID', 'voucherItem[].itemId', lookupItemMetal, oClassVoucherItem.itemId));
				oTr.appendChild(oTd);
				
					
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=95;
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItem[].itemValueCalculateBy" id="VOUCHER_ITEM_ITEM_VALUE_CALCULATE_BY" value="'+oClassVoucherItem.itemValueCalculateBy+'" >'));
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=70;
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItem[].quantity" id="VOUCHER_ITEM_QUANTITY" value="'+oClassVoucherItem.quantity+'" >'));
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=125;
				oInput = document.createElement('<input type="text" name="voucherItem[].weight" id="VOUCHER_ITEM_WEIGHT"  maxlength="6" size="4" value="'+oClassVoucherItem.weight+'">');
				oInput.attachEvent("onchange", function () {	calculateValueVoucherItem(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherItem[].weightUnitId" id="VOUCHER_ITEM_WEIGHT_UNIT_ID" value="'+oClassVoucherItem.weightUnitId+'" >'));
				oTr.appendChild(oTd);

				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=70;
				oInput = document.createElement('<input type="text" name="voucherItem[].rate" id="VOUCHER_ITEM_RATE"  maxlength="6" size="4" value="'+oClassVoucherItem.rate+'">');
				oInput.attachEvent("onchange", function () {	calculateValueVoucherItem(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=85;
				oTd.appendChild(document.createElement('<input type="text" name="voucherItem[].itemValue" id="VOUCHER_ITEM_ITEM_VALUE"  maxlength="10" size="7" value="'+oClassVoucherItem.itemValue+'" readonly=true>'));
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=250;
				oTr.appendChild(oTd);	
		oPlaceHolder.appendChild(oTr);
		calculateValueVoucherItem(oTr);

	}

	function calculateValueVoucherItem(oRow) {
/*		For Meatl Item */
		oRate = oRow.all("VOUCHER_ITEM_RATE",0);
		oWeight = oRow.all("VOUCHER_ITEM_WEIGHT",0);
		oValue = oRow.all("VOUCHER_ITEM_ITEM_VALUE",0);
		oValue.value = oWeight.value * oRate.value ;




	}
	document.onload=initializeForm();
-->
</script>
