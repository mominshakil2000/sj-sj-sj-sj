<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import=" java.util.*
				, com.netxs.Zewar.Lookup.*;"%> 

<script>
	// lookup Objects 
	var lookupItemMetal = new Array ();
	var lookupItemGem = new Array ();
	var lookupItemJewellery = new Array ();
	var lookupWeightUnit = new Array ();
	var lookupWastageUnit = new Array ();
	var lookupItemValueCalculateBy = new Array ();
	var lookupBodyMakingRateType = new Array();
	var lookupStoneSettingRateType = new Array();
	
	
	// Initialize the lookup Objects 			
	<c:if test="${lookupItemMetal != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupItemMetal}" varStatus="status" >
			lookupItemMetal[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if> 

	<c:if test="${lookupItemGem != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupItemGem}" varStatus="status" >
			lookupItemGem[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if> 
					
	<c:if test="${lookupItemJewellery != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupItemJewellery}" varStatus="status" >
			lookupItemJewellery[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
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
	
	<c:if test="${lookupItemValueCalculateBy != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupItemValueCalculateBy}" varStatus="status" >
			lookupItemValueCalculateBy[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if>

	lookupBodyMakingRateType[0] = new Array(1, "Simple");
	lookupBodyMakingRateType[1] = new Array(2, "Mix");
	
	lookupStoneSettingRateType[0] = new Array(1, "Simple");
	lookupStoneSettingRateType[1] = new Array(2, "Difficult");

	var lookupItemValueCalculateBy = new Array ();
	<c:if test="${lookupItemValueCalculateBy != null}">
		<c:forEach var="lookup" items="${applicationScope.lookupItemValueCalculateBy}" varStatus="status" >
			lookupItemValueCalculateBy[<c:out value='${status.index}' />] = new Array(<c:out value='${lookup.id}' />, "<c:out value='${lookup.label}' />");
		</c:forEach>
	</c:if>
</script>	

<html:form action="/SalesOrder/UpdateOrder" method="post"  onsubmit="updateFormObjects();">
<div><span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE">Sales Order Card</span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
	<div class="BOX" style="width:995;">
		<html:hidden property="insertable" />
		<html:hidden property="hasFormInitialized" />
		<html:hidden property="salesOrderId" />
		<html:hidden property="salesOrderTrackingId" />
		<html:hidden property="orderPostByUserId" />
		<html:hidden property="orderCreated" />
		<html:hidden property="cloneFromSalesOrderId" />
		<html:hidden property="advanceCashCashBookVoucherId" />
		<html:hidden property="advanceCashCashBookVoucherEntryId" />
		<html:hidden property="advanceCashLedgerEntryId" />
		<html:hidden property="salesOrderStatusId" />
		<input type="hidden" name="deleteAdvanceGem" value="0" id="deleteAdvanceGem">
		<input type="hidden" name="deleteAdvanceMetal" value="0" id="deleteAdvanceMetal">
		<input type="hidden" name="deleteOrderItem" value="0" id="deleteOrderItem">
		<input type="hidden" name="deleteEstimatedMetal" value="0" id="deleteEstimatedMetal">
		<input type="hidden" name="deleteEstimatedGem" value="0" id="deleteEstimatedGem">
		
		<logic:messagesPresent >
			<ul>
	    	<html:messages id="error">
    	  	<li><c:out value="${error}" /></li>
	    	</html:messages>
    	</ul>
		</logic:messagesPresent>

		<table class="FORM_AREA" width="600"  cellpadding="0" cellspacing="1">
			<tr>
				<td class="FORM_CAPTION" width="150">Customer</td>
				<td class="FORM_CONTROL" width="450">
					<html:select property="customerLedgerAccountId">
						<html:options collection="lookupCustomer" property="id" labelProperty="label"/>
					</html:select></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Order Date</td>
				<td class="FORM_CONTROL">
					<html:text property="orderDate" size="12" readonly="true" />
					<input type="button" name="buttonOrderDate" value=" ... " >
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("orderDate")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonOrderDate")[0],
								showsTime:false
						});
					</script>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Delivery Date</td>
				<td class="FORM_CONTROL">
					<html:text property="estimatedDeliveryDate" size="12" readonly="true" />
					<input type="button" name="buttonEstimatedDeliveryDate" value=" ... ">
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("estimatedDeliveryDate")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonEstimatedDeliveryDate")[0],
								showsTime:false
						});
					</script>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Description</td>
				<td class="FORM_CONTROL"><html:textarea property="description" rows="3" cols="40"></html:textarea></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Comments</td>
				<td class="FORM_CONTROL"><html:textarea property="comments" rows="3" cols="40"></html:textarea></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION" width="150" >Advance Cash</td>
				<td class="FORM_CONTROL" width="450"><html:text property="advanceCash" size="12" maxlength="10" /></td>
			</tr>
		</table>
		
		<!-- Advance Metal -->
		<br>
		<div class="FORM_AREA">
			<div class="FORM_AREA"><span style="width:475px">Advance Metals</span><span style="width:475;text-align:right;"><a href="javascript:nothing();" onclick="createAdvanceMetal(new classAdvanceMetal());">Add More</a></span></div>
			<div style="width:995px;" class="LIST_AREA">
				<span style="width:30px;" class="LIST_HEADER">&nbsp;</span><span style="width:140px;" class="LIST_HEADER">Items</span><span style="width:125px;" class="LIST_HEADER">Recv. Date</span><span style="width:122px;" class="LIST_HEADER">Weight</span><span style="width:133px;" class="LIST_HEADER">Alloy</span><span style="width:57px;" class="LIST_HEADER">Net.Wgt</span><span style="width:52px;" class="LIST_HEADER">Rate</span><span style="width:73px;" class="LIST_HEADER">Value</span><span style="width:263px;" class="LIST_HEADER">Comments</span>
			</div>
			<div style="width:995px; height:70px;padding:0px; margin:0px; overflow:auto;" >
				<table class="LIST_AREA" cellpadding="0" cellspacing="1" id="collectionAdvanceMetal" width="975" >
				</table>
			</div>
		</div>

		<!-- Advance Gem -->
		<br>
		<div id="collectionAdvanceGem">
		<div class="FORM_AREA"><span style="width:475px">Advance Gems</span><span style="width:475;text-align:right;"><a href="javascript:nothing();" onclick="createAdvanceGem(new classAdvanceGem());">Add More </a></span></div>
			<div style="width:953px;" class="LIST_AREA">
				<span style="width:30px;" class="LIST_HEADER">&nbsp;</span><span style="width:210px;" class="LIST_HEADER">Items</span><span style="width:122px;" class="LIST_HEADER">Qty</span><span style="width:153px;" class="LIST_HEADER">Weight</span><span style="width:182px;" class="LIST_HEADER">&nbsp;</span><span style="width:256px;" class="LIST_HEADER">Comments</span>
			</div>
			<div style="width:953px; height:70px;padding:0px; margin:0px; overflow:auto;" >
				<table class="LIST_AREA" cellpadding="0" cellspacing="1" id="collectionAdvanceGem" width="935" >
				</table>
			</div>
		</div>

		<!-- Sales Order Item -->
		<br><div class="FORM_AREA"><span style="width:475px">Order Items</span><span style="width:475;text-align:right;"><a href="javascript:nothing();" onclick="createSalesOrderItem(new classOrderItem());">Add More </a></span></div>
		<div id="collectionOrderItemArea" class="FORM_AREA"></div>

		<br>
		<div align="right"><html:submit property="submitAction" value="Save"/></div>
	</div>

</html:form> 

<script language="javascript">
	// Sales Order Advanvce Item Class 
	function classAdvanceMetal() {
		this.insertable = true;
		this.salesOrderAdvanceMetalId = 0;
		this.itemId = 0;
		this.entryDate = ((new Date()).getFullYear())+'-'+((new Date()).getMonth()+1)+'-'+((new Date()).getDate());
		this.itemWeight = 0.0;
		this.itemWeightUnitId = 0;
		this.itemRate = 0.0;
		this.itemWastageRate = 0.0;
		this.itemWastageRateUnitId = 2;
		this.itemNetWeight = 0.0;
		this.itemValue = 0.0;
		this.comments = '';
		this.ledgerEntryId = 0;
	 	this.inventoryMetalEntryIdIn = 0;
		this.inventoryPurchaseVoucherId = 0;
		this.inventoryPurchaseVoucherItemId = 0;
		this.insertablePurchaseItem  = true;
	}
	function classAdvanceGem() {
		this.insertable = true;
		this.salesOrderAdvanceGemId = 0;
		this.itemId = 0;
		this.itemQuantity = 0;
		this.itemWeight = 0.0;
		this.itemWeightUnitId = 2;
		this.comments = '';
	}

	//Sales Order Item Class & sub Class 	
	function classEstimatedMetal() {
		this.insertable = true;
		this.salesOrderItemInfoEstimatedMetalId = 0;
		this.itemId = 0;
		this.itemWeight = 0.0;
		this.itemWeightUnitId = 0;
		this.itemRate = 0.0;
		this.itemWastageRate = 0.0;
		this.itemWastageRateUnitId = 1;
		this.itemNetWeight = 0.0;
		this.itemValue = 0.0;
		this.comments = '';
	}
	function classEstimatedGem() {
		this.insertable = true;
		this.salesOrderItemInfoEstimatedGemId = 0;
		this.itemId = 0;
		this.itemQuantity = 0;
		this.itemRate = 0.00;
		this.itemWeight = 0.0;
		this.itemWeightUnitId = 2;
		this.itemValue = 0.0;
		this.itemValueCalculateBy = 2;
		this.comments = '';
	}
	function classOrderItemInfo() {
	  this.insertable = true;
	  this.salesOrderItemInfoId = 0;
	  this.orderItemCancel = 0;
	  this.lumpSumLabourCharges = 0.0;
	  this.comments = '';
	  this.bodyMakingRateTypeId = 0;
	  this.stoneSettingRateTypeId = 0;
	  this.estimatedMetalList = new Array();
	  this.estimatedGemList = new Array();
	}	
	function classOrderItem() {
	  this.insertable = true;
	  this.salesOrderItemId = 0;
  	  this.jewelleryItemId = 0;
	  this.orderItemInfoList = new Array(new classOrderItemInfo());
	}
	
	
	//Advance Metals
	var advanceMetalList = new Array();
	<nested:iterate name="salesOrderForm" property="advanceMetalList">
		<nested:writeNesting/> = new classAdvanceMetal();
		<nested:writeNesting property="insertable"/> = '<nested:write property="insertable"/>';
		<nested:writeNesting property="salesOrderAdvanceMetalId"/> = '<nested:write property="salesOrderAdvanceMetalId"/>';
		<nested:writeNesting property="entryDate"/> = '<nested:write property="entryDate"/>';
		<nested:writeNesting property="ledgerEntryId"/> = '<nested:write property="ledgerEntryId"/>';
		<nested:writeNesting property="inventoryMetalEntryIdIn"/> = '<nested:write property="inventoryMetalEntryIdIn"/>';
		<nested:writeNesting property="inventoryPurchaseVoucherId"/> = '<nested:write property="inventoryPurchaseVoucherId"/>';
		<nested:writeNesting property="inventoryPurchaseVoucherItemId"/> = '<nested:write property="inventoryPurchaseVoucherItemId"/>';
		<nested:writeNesting property="insertablePurchaseItem"/> = '<nested:write property="insertablePurchaseItem"/>';
		<nested:writeNesting property="itemId"/> = '<nested:write property="itemId"/>';
		<nested:writeNesting property="itemWeight"/> = '<nested:write property="itemWeight"/>';
		<nested:writeNesting property="itemWeightUnitId"/> = '<nested:write property="itemWeightUnitId"/>';
		<nested:writeNesting property="itemRate"/> = '<nested:write property="itemRate"/>';
		<nested:writeNesting property="itemWastageRate"/> = '<nested:write property="itemWastageRate"/>';
		<nested:writeNesting property="itemWastageRateUnitId"/> = '<nested:write property="itemWastageRateUnitId"/>';
		<nested:writeNesting property="itemNetWeight"/> = '<nested:write property="itemNetWeight"/>';		
		<nested:writeNesting property="itemValue"/> = '<nested:write property="itemValue"/>';
		<nested:writeNesting property="comments"/> = '<nested:write property="comments"/>';
	</nested:iterate>	

	//Advance Gems
	var advanceGemList = new Array();
	<nested:iterate name="salesOrderForm" property="advanceGemList">
		<nested:writeNesting/> = new classAdvanceGem();
		<nested:writeNesting property="insertable"/> = '<nested:write property="insertable"/>';
		<nested:writeNesting property="salesOrderAdvanceGemId"/> = '<nested:write property="salesOrderAdvanceGemId"/>';
		<nested:writeNesting property="itemId"/> = '<nested:write property="itemId"/>';
		<nested:writeNesting property="itemQuantity"/> = '<nested:write property="itemQuantity"/>';
		<nested:writeNesting property="itemWeight"/> = '<nested:write property="itemWeight"/>';
		<nested:writeNesting property="itemWeightUnitId"/> = '<nested:write property="itemWeightUnitId"/>';
		<nested:writeNesting property="comments"/> = '<nested:write property="comments"/>';
	</nested:iterate>	

	// Order Items 
	var orderItemList = new Array();
	<nested:iterate name="salesOrderForm" property="orderItemList">
		<nested:writeNesting/> = new classOrderItem();
		<nested:writeNesting property="insertable"/> = '<nested:write property="insertable"/>';
		<nested:writeNesting property="salesOrderItemId"/> = '<nested:write property="salesOrderItemId"/>';
		<nested:writeNesting property="jewelleryItemId"/> = '<nested:write property="jewelleryItemId"/>';
		
		//Order Item Info
//		<nested:writeNesting property="orderItemInfoList"/> = new Array();
		<nested:iterate property="orderItemInfoList">
			<nested:writeNesting/> = new classOrderItemInfo();
			<nested:writeNesting property="insertable"/> = '<nested:write property="insertable"/>';
			<nested:writeNesting property="salesOrderItemInfoId"/> = '<nested:write property="salesOrderItemInfoId"/>';
			<nested:writeNesting property="orderItemCancel"/> = '<nested:write property="orderItemCancel"/>';
			<nested:writeNesting property="itemId"/> = '<nested:write property="itemId"/>';
			<nested:writeNesting property="lumpSumLabourCharges"/> = '<nested:write property="lumpSumLabourCharges"/>';
			<nested:writeNesting property="comments"/> = '<nested:write property="comments"/>';
			<nested:writeNesting property="bodyMakingRateTypeId"/> = '<nested:write property="bodyMakingRateTypeId"/>';
			<nested:writeNesting property="stoneSettingRateTypeId"/> = '<nested:write property="stoneSettingRateTypeId"/>';	

			//Estimated Metals
//			<nested:writeNesting property="estimatedMetalList"/> = new Array();
			<nested:iterate property="estimatedMetalList">
				<nested:writeNesting/> = new Array();
				<nested:writeNesting property="insertable"/> = '<nested:write property="insertable"/>';
				<nested:writeNesting property="salesOrderItemInfoEstimatedMetalId"/> = '<nested:write property="salesOrderItemInfoEstimatedMetalId"/>';
				<nested:writeNesting property="itemId"/> = '<nested:write property="itemId"/>';
				<nested:writeNesting property="itemWeight"/> = '<nested:write property="itemWeight"/>';
				<nested:writeNesting property="itemWeightUnitId"/> = '<nested:write property="itemWeightUnitId"/>';
				<nested:writeNesting property="itemRate"/> = '<nested:write property="itemRate"/>';
				<nested:writeNesting property="itemWastageRate"/> = '<nested:write property="itemWastageRate"/>';
				<nested:writeNesting property="itemWastageRateUnitId"/> = '<nested:write property="itemWastageRateUnitId"/>';
				<nested:writeNesting property="itemNetWeight"/> = '<nested:write property="itemNetWeight"/>';
				<nested:writeNesting property="itemValue"/> = '<nested:write property="itemValue"/>';
				<nested:writeNesting property="comments"/> = '<nested:write property="comments"/>';
			</nested:iterate>
			
			//Estimated Gems
//			<nested:writeNesting property="estimatedGemList"/> = new Array();
			<nested:iterate property="estimatedGemList">
				<nested:writeNesting/> = new Array();
				<nested:writeNesting property="insertable"/> = '<nested:write property="insertable"/>';
				<nested:writeNesting property="salesOrderItemInfoEstimatedGemId"/> = '<nested:write property="salesOrderItemInfoEstimatedGemId"/>';
				<nested:writeNesting property="itemId"/> = '<nested:write property="itemId"/>';
				<nested:writeNesting property="itemQuantity"/> = '<nested:write property="itemQuantity"/>';
				<nested:writeNesting property="itemWeight"/> = '<nested:write property="itemWeight"/>';
				<nested:writeNesting property="itemWeightUnitId"/> = '<nested:write property="itemWeightUnitId"/>';
				<nested:writeNesting property="itemRate"/> = '<nested:write property="itemRate"/>';
				<nested:writeNesting property="itemValue"/> = '<nested:write property="itemValue"/>';
				<nested:writeNesting property="itemValueCalculateBy"/> = '<nested:write property="itemValueCalculateBy"/>';
				<nested:writeNesting property="comments"/> = '<nested:write property="comments"/>';
			</nested:iterate>
		</nested:iterate>
	</nested:iterate>	
 
	//On load Start
	//Iterate thorugh all Advance Metals
	for(index=0; index <  advanceMetalList.length; index++){
		oPlaceHolder = document.all('collectionAdvanceMetal', 0);
		createAdvanceMetal(advanceMetalList[index], oPlaceHolder);
	}
	//Iterate thorugh all Advance Gems
	for(index=0; index <  advanceGemList.length; index++){
			oPlaceHolder = document.all('collectionAdvanceGem', 0);
			createAdvanceGem(advanceGemList[index], oPlaceHolder);
	}
	//Iterate thorugh all Sales Order Items
	for(index=0; index <  orderItemList.length; index++){
		createSalesOrderItem(orderItemList[index]);
		for (y=0; y <  orderItemList[index].orderItemInfoList.length; y++) {
			for(subIndex=0; subIndex <  orderItemList[index].orderItemInfoList[y].estimatedMetalList.length; subIndex++){
				oPlaceHolder = document.all('collectionOrderItemArea', 0).all('collectionOrderItem',index);
				createSalesOrderEstimatedMetal(orderItemList[index].orderItemInfoList[y].estimatedMetalList[subIndex], oPlaceHolder);
			}
			for(subIndex=0; subIndex <  orderItemList[index].orderItemInfoList[y].estimatedGemList.length; subIndex++){
				oPlaceHolder = document.all('collectionOrderItemArea', 0).all('collectionOrderItem',index);
				createSalesOrderEstimatedGem(orderItemList[index].orderItemInfoList[y].estimatedGemList[subIndex], oPlaceHolder);
			}

		}
	}

	//On load End 

	function createAdvanceMetal(oClassAdvanceMetal) {	
		if (!eval(oClassAdvanceMetal)) {
			alert ('illegal Object : Advance Item Metal.');
			return;
		}

		oPlaceHolder = document.getElementById('collectionAdvanceMetal').getElementsByTagName("tbody").item(0);

		oTr = document.createElement('tr');
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=30;
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="salesOrderAdvanceMetalId" value="'+oClassAdvanceMetal.salesOrderAdvanceMetalId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="insertable" value="'+oClassAdvanceMetal.insertable+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="ledgerEntryId" value="'+oClassAdvanceMetal.ledgerEntryId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="inventoryMetalEntryIdIn" value="'+oClassAdvanceMetal.inventoryMetalEntryIdIn+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="inventoryPurchaseVoucherId" value="'+oClassAdvanceMetal.inventoryPurchaseVoucherId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="inventoryPurchaseVoucherItemId" value="'+oClassAdvanceMetal.inventoryPurchaseVoucherItemId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="insertablePurchaseItem" value="'+oClassAdvanceMetal.insertablePurchaseItem+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="itemQuantity" value="1" >'));
				oA = document.createElement("a");
					oA.appendChild(document.createTextNode('D'));
					oA.href = 'javascript:nothing();';
					oA.attachEvent("onclick", function () {	myFnDeleteAdvanceMetal(GetParentByTagName(window.event.srcElement,'TR'))});
					oTd.appendChild(oA)
				oTr.appendChild(oTd);
			
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=140;
				oTd.appendChild(createSelect('itemId', ' ', lookupItemMetal, oClassAdvanceMetal.itemId));
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=125;
				oInput = document.createElement('<input type="text"   name="entryDate[0]" id="entryDate"  maxlength="10" size="12" value="'+oClassAdvanceMetal.entryDate+'">');
				oTd.appendChild(oInput);
				oInput2 = document.createElement('<input type="button" name="" id="buttonEntryDate" value=" ... ">');
				oTd.appendChild(oInput2);
				var cal = new Zapatec.Calendar.setup({inputField:oInput,ifFormat:"%Y-%m-%d",button:oInput2,showsTime:false}); 
				oTr.appendChild(oTd);
						
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=122;
				oInput = document.createElement('<input type="text" name="" id="itemWeight"  maxlength="6" size="4" value="'+oClassAdvanceMetal.itemWeight+'">');
				oInput.attachEvent("onchange", function () {	calculateAdvanceMetalNetWeight(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTd.appendChild(createSelect('itemWeightUnitId', '', lookupWeightUnit, oClassAdvanceMetal.itemWeightUnitId));
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=133;
				oInput = document.createElement('<input type="text" name="" id="itemWastageRate"  maxlength="6" size="4" value="'+oClassAdvanceMetal.itemWastageRate+'">');
				oInput.attachEvent("onchange", function () {	calculateAdvanceMetalNetWeight(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oSelect = createSelect('itemWastageRateUnitId', '', lookupWastageUnit, oClassAdvanceMetal.itemWastageRateUnitId)
				oTd.appendChild(oSelect);
				oSelect.attachEvent("onchange", function () {	calculateAdvanceMetalNetWeight(window.event.srcElement.parentElement.parentElement)});
				oTr.appendChild(oTd);
								
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=57;
				oInput = document.createElement('<input type="text" name="" id="itemNetWeight"  maxlength="6" size="4" value="'+oClassAdvanceMetal.itemNetWeight+'">');
				oInput.attachEvent("onchange", function () {	calculateAdvanceMetalValue(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=52;
				oInput = document.createElement('<input type="text" name="" id="itemRate"  maxlength="6" size="4" value="'+oClassAdvanceMetal.itemRate+'">');
				oInput.attachEvent("onchange", function () {	calculateAdvanceMetalValue(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=73;
				oTd.appendChild(document.createElement('<input type="text" name="" id="itemValue"  maxlength="10" size="7" value="'+oClassAdvanceMetal.itemValue+'" >'));
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=243;
				oTextArea = document.createElement('<textarea name="" id="comments" ></textarea>');
				oTextArea.value=oClassAdvanceMetal.comments;								
				oTextArea.cols=35;
				oTextArea.rows=1;
				oTd.appendChild(oTextArea);				
				oTr.appendChild(oTd);	
		oPlaceHolder.appendChild(oTr);
//		calculateAdvanceMetalValue(oTr);

	}

	function createAdvanceGem(oClassAdvanceGem) {	
		if (!eval(oClassAdvanceGem)) {
			alert ('illegal Object : Advance Item Gem .');
			return;
		}
		oPlaceHolder = document.getElementById('collectionAdvanceGem').getElementsByTagName("tbody").item(0);
		oTr = document.createElement('tr');
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=30;
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="salesOrderAdvanceGemId" value="'+oClassAdvanceGem.salesOrderAdvanceGemId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="insertable" 								value="'+oClassAdvanceGem.insertable+'" >'));
				oA = document.createElement("a");
					oA.appendChild(document.createTextNode('D'));
					oA.href = 'javascript:nothing();';
					oA.attachEvent("onclick", function () {	myFnDeleteAdvanceGem(GetParentByTagName(window.event.srcElement,'TR'))});
					oTd.appendChild(oA)
				oTr.appendChild(oTd);
			
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=210;
				oTd.appendChild(createSelect('itemId', '', lookupItemGem, oClassAdvanceGem.itemId));
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=122;
				oInput = document.createElement('<input type="text" name="" id="itemQuantity"  maxlength="6" size="4" value="'+oClassAdvanceGem.itemQuantity+'">')
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=153;
				oInput = document.createElement('<input type="text" name="" id="itemWeight"  maxlength="6" size="4" value="'+oClassAdvanceGem.itemWeight+'">');
				oTd.appendChild(oInput);
				oTd.appendChild(createSelect('itemWeightUnitId', '', lookupWeightUnit, oClassAdvanceGem.itemWeightUnitId));
				oTr.appendChild(oTd);

				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=182;
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=238;
				oTextArea = document.createElement('<textarea name="" id="comments" ></textarea>');
				oTextArea.value=oClassAdvanceGem.comments;								

				oTextArea.cols=35;
				oTextArea.rows=1;
				oTd.appendChild(oTextArea);				
				oTr.appendChild(oTd);	
		oPlaceHolder.appendChild(oTr);
	}
		
	function createSalesOrderItem(oClassSalesOrderItem ) {
		if (!eval(oClassSalesOrderItem)){
			alert ('illegal Object: Sales Order Item');
			return;
		}
		var oPlaceHolder = document.getElementById("collectionOrderItemArea");

		oDiv0 =  document.createElement("div");
		oDiv0.id = 'collectionOrderItem';
		//oDiv0.align = 'right';

			//Add Delete Order Item Link
			oA = document.createElement("a");
			oA.appendChild(document.createTextNode('Delete Item'));
			oA.href = 'javascript:nothing();';
			oA.attachEvent("onclick", function () {	myFnDeleteSalesOrderItem(GetParentByTagName(window.event.srcElement,'DIV'))});
			oDiv0.appendChild(oA);

			oDiv1 =  document.createElement("div");
			oDiv1.style.borderWidth = 1;
			oDiv1.style.borderColor = '#736356';
			oDiv1.style.borderStyle = 'solid';
			oDiv1.style.borderCollapse = 'collapse';
			oDiv1.style.padding = '0px 0px 0px 0px';
			oDiv1.style.width = 952;

				// Add Table for Order Item, Order Item Info, Picture Place Holder 
				oTable0 = document.createElement("table")
				oTable0.className = "FORM_AREA";
				oTable0.width = 940;
				oTable0.cellPadding = 0;
				oTable0.cellSpacing = 0;
				oTable0Body = document.createElement('tbody');
					oTable0Tr1 = document.createElement('tr');
						oTable0Tr1Td1 = document.createElement('td');
						oTable0Tr1Td1.width=640;

							//Order Item Table
							oTable2 = document.createElement("table")
							oTable2.className = "FORM_AREA";
							oTable2.width = 640;
							oTable2.cellPadding = 0;
							oTable2.cellSpacing = 1;
							oTable2.id = "orderItemList";
								oTable2Body = document.createElement('tbody');
									oTr = document.createElement('tr');
										oTd = document.createElement('td');
										oTd.className = "FORM_CAPTION";
										oTd.width=150;
										oTd.appendChild(document.createTextNode('Item'));
										oTr.appendChild(oTd);

										oTd= document.createElement('td');
										oTd.className = "FORM_CONTROL";
										oTd.width = 400;
										oTd.appendChild(document.createElement('<input type="hidden" name="" id="salesOrderItemId" value="'+oClassSalesOrderItem.salesOrderItemId+'" >'));
										oTd.appendChild(document.createElement('<input type="hidden" name="" id="insertable" value="'+oClassSalesOrderItem.insertable+'" >'));
										oTd.appendChild(createSelect('jewelleryItemId', '', lookupItemJewellery, oClassSalesOrderItem.jewelleryItemId));
										oTr.appendChild(oTd);		

										oTable2Body.appendChild(oTr);
										oTable2.appendChild(oTable2Body);
										
										oTable0Tr1Td1.appendChild(oTable2);
										oTable0Tr1.appendChild(oTable0Tr1Td1);

							// Order Item Info
							oTable2 = document.createElement("table")
							oTable2.className = "FORM_AREA";
							oTable2.width = 640;
							oTable2.cellPadding = 0;
							oTable2.cellSpacing = 1;
							oTable2.id = "orderItemInfoList";
								oTable2Body = document.createElement('tbody');
									oTr = document.createElement('tr');
										oTd = document.createElement('td');
										oTd.className = "FORM_CAPTION";
										oTd.width=150;
										oTd.appendChild(document.createTextNode('Estimated Labour Charges'));
										oTr.appendChild(oTd);
										oTd = document.createElement('td');
										oTd.className = "FORM_CONTROL";
										oTd.width = 400;
										oTd.appendChild(document.createElement('<input type="hidden" name="" id="salesOrderItemInfoId" value="'+oClassSalesOrderItem.orderItemInfoList[0].salesOrderItemInfoId+'" >'));
										oTd.appendChild(document.createElement('<input type="hidden" name="" id="orderItemCancel" value="'+oClassSalesOrderItem.orderItemInfoList[0].orderItemCancel+'" >'));
										oTd.appendChild(document.createElement('<input type="hidden" name="" id="insertable" value="'+oClassSalesOrderItem.orderItemInfoList[0].insertable+'" >'));
										oTd.appendChild(document.createElement('<input type="text" name="" id="lumpSumLabourCharges"  maxlength="6" size="45" value="'+oClassSalesOrderItem.orderItemInfoList[0].lumpSumLabourCharges+'">'));
										oTr.appendChild(oTd);				
										oTable2Body.appendChild(oTr);
			
									oTr = document.createElement('tr');
										oTd = document.createElement('td');
										oTd.className = "FORM_CAPTION";
										oTd.width=150;
										oTd.appendChild(document.createTextNode('Comments'));
										oTr.appendChild(oTd);				
										oTd = document.createElement('td');
										oTd.className = "FORM_CONTROL";
										oTd.width = 400;
											oTextArea = document.createElement('<textarea name="" id="comments" ></textarea>');
											oTextArea.value=oClassSalesOrderItem.orderItemInfoList[0].comments;								
											oTextArea.cols=46;
											oTextArea.rows=2;
											oTd.appendChild(oTextArea);				
											oTr.appendChild(oTd);				
											oTable2Body.appendChild(oTr);

									oTr = document.createElement('tr');
										oTd = document.createElement('td');
										oTd.className = "FORM_CAPTION";
										oTd.width=150;
										oTd.appendChild(document.createTextNode('Use Body Making Rate'));
										oTr.appendChild(oTd);
										oTd = document.createElement('td');
										oTd.className = "FORM_CONTROL";
										oTd.width = 400;
										oTd.appendChild(createSelect('bodyMakingRateTypeId', '', lookupBodyMakingRateType, oClassSalesOrderItem.orderItemInfoList[0].bodyMakingRateTypeId));
										oTr.appendChild(oTd);
										oTable2Body.appendChild(oTr);						
		
									oTr = document.createElement('tr');
										oTd = document.createElement('td');
										oTd.className = "FORM_CAPTION";
										oTd.width=150;
										oTd.appendChild(document.createTextNode('Use Stone Setting Rate'));
										oTr.appendChild(oTd);
			
										oTd = document.createElement('td');
										oTd.className = "FORM_CONTROL";
										oTd.width = 400;
										oTd.appendChild(createSelect('stoneSettingRateTypeId', '', lookupStoneSettingRateType, oClassSalesOrderItem.orderItemInfoList[0].stoneSettingRateTypeId));
										oTr.appendChild(oTd);				
										
										oTable2Body.appendChild(oTr);						
										oTable2.appendChild(oTable2Body);
										oTable0Tr1Td1.appendChild(oTable2);
										oTable0Tr1.appendChild(oTable0Tr1Td1);

						//Image Place Holder										
						oTable0Tr1Td2= document.createElement('td');
						oTable0Tr1Td2.className = "FORM_CONTROL";
						oTable0Tr1Td2.width = 300;
						//oTable0Tr1Td2.appendChild(document.createTextNode('Image Place Holder'));
						oTable0Tr1Td2.appendChild(document.createTextNode('.'));
						oTable0Tr1.appendChild(oTable0Tr1Td2);
						
						oTable0Body.appendChild(oTable0Tr1);
						oTable0.appendChild(oTable0Body);
						oDiv1.appendChild(oTable0);	

				//Estimated Metal 
				oDiv1.appendChild(document.createElement("br"));
					oDiv2 = document.createElement('div');
						oSpan = document.createElement('span');
						oSpan.style.width = 475;
						oSpan.appendChild(document.createTextNode("Estimated Metal"));
						oDiv2.appendChild(oSpan);
					
						oSpan = document.createElement('span');
						oSpan.style.width = 475;
						oSpan.style.textAlign = 'right';
							oA = document.createElement('a');
							oA.appendChild(document.createTextNode('Add More '));
							oA.href = 'javascript:nothing();';
							oA.attachEvent("onclick", function () {createSalesOrderEstimatedMetal(new classEstimatedMetal(), window.event.srcElement.parentElement.parentElement.parentElement);});
							oSpan.appendChild(oA);
							oDiv2.appendChild(oSpan);
							oDiv1.appendChild(oDiv2);
		
					oDiv2 = document.createElement('div');
					oDiv2.width=950;
						oSpan = document.createElement('span');
						oSpan.style.width = 30;
						oSpan.className = 'LIST_HEADER';
						oSpan.appendChild(document.createTextNode('.'));
						oDiv2.appendChild(oSpan);
			
						oSpan = document.createElement('span');
						oSpan.style.width = 210;
						oSpan.className = 'LIST_HEADER';
						oSpan.appendChild(document.createTextNode('Items'));
						oDiv2.appendChild(oSpan);
			
						oSpan = document.createElement('span');
						oSpan.style.width = 122;
						oSpan.className = 'LIST_HEADER';
						oSpan.appendChild(document.createTextNode('Weight'));
						oDiv2.appendChild(oSpan);
			
						oSpan = document.createElement('span');
						oSpan.style.width = 153;
						oSpan.className = 'LIST_HEADER';
						oSpan.appendChild(document.createTextNode('Wastage'));
						oDiv2.appendChild(oSpan);
			
						oSpan = document.createElement('span');
						oSpan.style.width = 57;
						oSpan.className = 'LIST_HEADER';
						oSpan.appendChild(document.createTextNode('Net.Wgt'));
						oDiv2.appendChild(oSpan);
			
						oSpan = document.createElement('span');
						oSpan.style.width = 52;
						oSpan.className = 'LIST_HEADER';
						oSpan.appendChild(document.createTextNode('Rate'));
						oDiv2.appendChild(oSpan);
				
						oSpan = document.createElement('span');
						oSpan.style.width = 73;
						oSpan.className = 'LIST_HEADER';
						oSpan.appendChild(document.createTextNode('Value'));
						oDiv2.appendChild(oSpan);
	
						oSpan = document.createElement('span');
						oSpan.style.width = 238;
						oSpan.className = 'LIST_HEADER';
						oSpan.appendChild(document.createTextNode('Comments'));
						oDiv2.appendChild(oSpan);
	
						oDiv1.appendChild(oDiv2);
						
						oDiv2 = document.createElement('div');
						oDiv2.width = 950;
						oDiv2.style.height = 70;
						oDiv2.style.padding = 0;
						oDiv2.style.margin = 0;
						oDiv2.style.overflow = 'auto';
								
							oTable1 = document.createElement("table")
							oTable1.className = "LIST_AREA";
							oTable1.width = 935;
							oTable1.cellPadding = 0;
							oTable1.cellSpacing = 1;
							oTable1.id = "collectionEstimatedMetal";
								oTable1Body = document.createElement('tbody');
								oTable1.appendChild(oTable1Body);
								oDiv2.appendChild(oTable1);
								oDiv1.appendChild(oDiv2);
				
				oDiv1.appendChild(document.createElement("br"));
				
					// Estimated Gems 
					oDiv2 = document.createElement('div');
					oSpan = document.createElement('span');
					oSpan.style.width = 475;
					oSpan.appendChild(document.createTextNode("Estimated Gems"));
					oDiv2.appendChild(oSpan);
				
					oSpan = document.createElement('span');
					oSpan.style.width = 475;
					oSpan.style.textAlign = 'right';
						oA = document.createElement('a');
						oA.appendChild(document.createTextNode('Add More '));
						oA.href = 'javascript:nothing();';
						oA.attachEvent("onclick", function () {createSalesOrderEstimatedGem(new classEstimatedGem(), window.event.srcElement.parentElement.parentElement.parentElement);});
						oSpan.appendChild(oA);
						oDiv2.appendChild(oSpan);
						oDiv1.appendChild(oDiv2);
		
		
						oDiv2 = document.createElement('div');
						oDiv2.width=950;
							oSpan = document.createElement('span');
							oSpan.style.width = 30;
							oSpan.className = 'LIST_HEADER';
							oSpan.appendChild(document.createTextNode('.'));
							oDiv2.appendChild(oSpan);
		
							oSpan = document.createElement('span');
							oSpan.style.width = 210;
							oSpan.className = 'LIST_HEADER';
							oSpan.appendChild(document.createTextNode('Items'));
							oDiv2.appendChild(oSpan);
		
							oSpan = document.createElement('span');
							oSpan.style.width = 72;
							oSpan.className = 'LIST_HEADER';
							oSpan.appendChild(document.createTextNode('Qty'));
							oDiv2.appendChild(oSpan);
		
							oSpan = document.createElement('span');
							oSpan.style.width = 131;
							oSpan.className = 'LIST_HEADER';
							oSpan.appendChild(document.createTextNode('Weight'));
							oDiv2.appendChild(oSpan);
							
							oSpan = document.createElement('span');
							oSpan.style.width = 71;
							oSpan.className = 'LIST_HEADER';
							oSpan.appendChild(document.createTextNode('Rate'));
							oDiv2.appendChild(oSpan);
		
							oSpan = document.createElement('span');
							oSpan.style.width = 92;
							oSpan.className = 'LIST_HEADER';
							oSpan.appendChild(document.createTextNode('Calc By'));
							oDiv2.appendChild(oSpan);
		
							oSpan = document.createElement('span');
							oSpan.style.width = 91;
							oSpan.className = 'LIST_HEADER';
							oSpan.appendChild(document.createTextNode('Value'));
							oDiv2.appendChild(oSpan);
			
							oSpan = document.createElement('span');
							oSpan.style.width = 238;
							oSpan.className = 'LIST_HEADER';
							oSpan.appendChild(document.createTextNode('Comments'));
							oDiv2.appendChild(oSpan);
							oDiv1.appendChild(oDiv2);
		
						oDiv2 = document.createElement('div');
						oDiv2.width = 950;
						oDiv2.style.height = 70;
						oDiv2.style.padding = 0;
						oDiv2.style.margin = 0;
						oDiv2.style.overflow = 'auto';
							oTable1 = document.createElement("table");
							oTable1.className = "LIST_AREA";
							oTable1.width = 935;
							oTable1.cellPadding = 0;
							oTable1.cellSpacing = 1;
							oTable1.id = "collectionEstimatedGem";
								oTable1Body = document.createElement('tbody');
								oTable1.appendChild(oTable1Body);
								oDiv2.appendChild(oTable1);

					oDiv1.appendChild(oDiv2);
					oDiv0.appendChild(oDiv1);
					oDiv0.appendChild(document.createElement('br'));
	  				oPlaceHolder.appendChild(oDiv0);
	  	
	}
	

	function createSalesOrderEstimatedMetal(oClassEstimatedMetal, oPlaceHolder){
		if (!eval(oClassEstimatedMetal)) {
			alert ('illegal Object : Sales Ordeer Item Metal Estimation.');
			return;
		}

		oPlaceHolder = oPlaceHolder.all('collectionEstimatedMetal').getElementsByTagName("tbody").item(0);

		oTr = document.createElement('tr');

			oTd = document.createElement('td');
			oTd.className='LIST_DATA';
			oTd.width=30;
			oTd.appendChild(document.createElement('<input type="hidden" name="" 	id="salesOrderItemInfoEstimatedMetalId" value="'+oClassEstimatedMetal.salesOrderItemInfoEstimatedMetalId+'" >'));
			oTd.appendChild(document.createElement('<input type="hidden" name="" 	id="insertable" 						value="'+oClassEstimatedMetal.insertable+'" >'));
			  oA = document.createElement("a");
				oA.appendChild(document.createTextNode('D'));
				oA.href = 'javascript:nothing();';
				oA.attachEvent("onclick", function () {	myFnDeleteSalesOrderItemEstimatedMetal(GetParentByTagName(window.event.srcElement,'TR'))});
				oTd.appendChild(oA)
			  oTr.appendChild(oTd);
			  
			  oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=210;
				oTd.appendChild(createSelect('itemId', '', lookupItemMetal, oClassEstimatedMetal.itemId));
				oTr.appendChild(oTd);
					
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=122;
				oInput = document.createElement('<input type="text" name="" id="itemWeight"  maxlength="6" size="4" value="'+oClassEstimatedMetal.itemWeight+'">');
				oInput.attachEvent("onchange", function () {	calculateItemEstimatedMetalNetWeight(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTd.appendChild(createSelect('itemWeightUnitId', '', lookupWeightUnit, oClassEstimatedMetal.itemWeightUnitId));
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=153;
				oInput = document.createElement('<input type="text" name="" id="itemWastageRate"  maxlength="6" size="4" value="'+oClassEstimatedMetal.itemWastageRate+'">');
				oInput.attachEvent("onchange", function () {	calculateItemEstimatedMetalNetWeight(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oSelect = createSelect('itemWastageRateUnitId', '', lookupWastageUnit, oClassEstimatedMetal.itemWastageRateUnitId)
				oTd.appendChild(oSelect);
				oSelect.attachEvent("onchange", function () {	calculateItemEstimatedMetalNetWeight(window.event.srcElement.parentElement.parentElement)});				
				oTr.appendChild(oTd);
								
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=57;
				oInput = document.createElement('<input type="text" name="" id="itemNetWeight"  maxlength="6" size="4" value="'+oClassEstimatedMetal.itemNetWeight+'">');
				oInput.attachEvent("onchange", function () {	calculateItemEstimatedMetalValue(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=52;
				oInput = document.createElement('<input type="text" name="" id="itemRate"  maxlength="6" size="4" value="'+oClassEstimatedMetal.itemRate+'">');
				oInput.attachEvent("onchange", function () {	calculateItemEstimatedMetalValue(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=73;
				oTd.appendChild(document.createElement('<input type="text" name="" id="itemValue"  maxlength="10" size="7" value="'+oClassEstimatedMetal.itemValue+'" >'));
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=238;
				oTextArea = document.createElement('<textarea name="" id="comments" ></textarea>');
				oTextArea.value=oClassEstimatedMetal.comments;								
				oTextArea.cols=35;
				oTextArea.rows=1;
				oTd.appendChild(oTextArea);				
				oTr.appendChild(oTd);	
				
		oPlaceHolder.appendChild(oTr);
//		calculateItemEstimatedMetalValue(oTr);
	}
	
	function createSalesOrderEstimatedGem(oClassEstimatedGem, oPlaceHolder){
		if (!eval(oClassEstimatedGem)) {
			alert ('illegal Object : Sales Ordeer Item Gem Estimation.');
			return;
		}
		oPlaceHolder = oPlaceHolder.all('collectionEstimatedGem').getElementsByTagName("tbody").item(0);
		oTr = document.createElement('tr');
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=30;
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="salesOrderItemInfoEstimatedGemId" value="'+oClassEstimatedGem.salesOrderItemInfoEstimatedGemId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="" id="insertable" value="'+oClassEstimatedGem.insertable+'" >'));

				oA = document.createElement("a");
					oA.appendChild(document.createTextNode('D'));
					oA.href = 'javascript:nothing();';
					oA.attachEvent("onclick", function () {	myFnDeleteSalesOrderItemEstimatedGem(GetParentByTagName(window.event.srcElement,'TR'))});
					oTd.appendChild(oA)
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=210;
				oTd.appendChild(createSelect('itemId', '', lookupItemGem, oClassEstimatedGem.itemId));
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=72;
				oInput = document.createElement('<input type="text" name="" id="itemQuantity"  size="4" value="'+oClassEstimatedGem.itemQuantity+'">')
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=131;
				oInput = document.createElement('<input type="text" name="" id="itemWeight"  maxlength="6" size="4" value="'+oClassEstimatedGem.itemWeight+'">');
				oTd.appendChild(oInput);
				oTd.appendChild(createSelect('itemWeightUnitId', '', lookupWeightUnit, oClassEstimatedGem.itemWeightUnitId));
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=71;
				oInput = document.createElement('<input type="text" name="" id="itemRate"  size="4" value="'+oClassEstimatedGem.itemRate+'">')
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);
			
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oSelect = createSelect('itemValueCalculateBy', '', lookupItemValueCalculateBy, oClassEstimatedGem.itemValueCalculateBy);
				oSelect.attachEvent("onchange", function () {	calculateItemEstimatedGemValue(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oSelect);
				oTd.width=102;
				oTr.appendChild(oTd);
		

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=81;
				oInput = document.createElement('<input type="text" name="" id="itemValue"  size="4" value="'+oClassEstimatedGem.itemValue+'">')
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);
								
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=238;
				oTextArea = document.createElement('<textarea name="" id="comments" ></textarea>');
				oTextArea.value=oClassEstimatedGem.comments;								

				oTextArea.cols=35;
				oTextArea.rows=1;
				oTd.appendChild(oTextArea);				
				oTr.appendChild(oTd);	

		oPlaceHolder.appendChild(oTr);
//		calculateItemEstimatedGemValue(oTr);
	}
	
	


	function myFnDeleteAdvanceMetal (obj) { 
	  if (GetElementsById(obj.getElementsByTagName('INPUT'),'salesOrderAdvanceMetalId')[0].value!='0') 
			document.getElementById("deleteAdvanceMetal").value += ","+GetElementsById(obj.getElementsByTagName('INPUT'),'salesOrderAdvanceMetalId')[0].value ;
		obj.removeNode(true); 
	}
	function myFnDeleteAdvanceGem (obj) { 
		if (GetElementsById(obj.getElementsByTagName('INPUT'),'salesOrderAdvanceGemId')[0].value!='0') 
			document.getElementById("deleteAdvanceGem").value += ","+GetElementsById(obj.getElementsByTagName('INPUT'),'salesOrderAdvanceGemId')[0].value ;
		obj.removeNode(true); 
	}
	function myFnDeleteSalesOrderItem (obj) { 
	
		if (GetElementsById(obj.getElementsByTagName('INPUT'),'salesOrderItemId')[0].value!='0') 
			document.getElementById("deleteOrderItem").value += ","+GetElementsById(obj.getElementsByTagName('INPUT'),'salesOrderItemId')[0].value ;

		obj.removeNode(true); 
	}

	function myFnDeleteSalesOrderItemEstimatedMetal (obj) {  
//		alert(obj.tagName);
		if (GetElementsById(obj.getElementsByTagName('INPUT'),'salesOrderItemInfoEstimatedMetalId')[0].value!='0') 
			document.getElementById("deleteEstimatedMetal").value += ","+GetElementsById(obj.getElementsByTagName('INPUT'),'salesOrderItemInfoEstimatedMetalId')[0].value ;
		obj.removeNode(true); 
	}
	
	function myFnDeleteSalesOrderItemEstimatedGem (obj) { 		
		if (GetElementsById(obj.getElementsByTagName('INPUT'),'salesOrderItemInfoEstimatedGemId')[0].value!='0') 
			document.getElementById("deleteEstimatedGem").value += ","+GetElementsById(obj.getElementsByTagName('INPUT'),'salesOrderItemInfoEstimatedGemId')[0].value ;
		obj.removeNode(true); 
	}
	
	function updateAdvanceMetalName(){
		var oCollection = document.getElementById("collectionAdvanceMetal").getElementsByTagName("tbody")[0].getElementsByTagName("tr");
		var preFix = "";
		var element;
		for(var i=0; i < oCollection.length; i++) {
			preFix = "advanceMetal["+i+"].";
			element = oCollection[i].getElementsByTagName("input");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
			element = oCollection[i].getElementsByTagName("select");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
			element = oCollection[i].getElementsByTagName("textarea");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
		}
	}
	
	function updateAdvanceGemName(){
		var oCollection = document.getElementById("collectionAdvanceGem").getElementsByTagName("tbody")[0].getElementsByTagName("tr");
		var oCollectionEstimatedMetal;
		var oCollectionEstimatedGem;
		var preFix = "";
		var element;
		for(var i=0; i < oCollection.length; i++) {
			preFix = "advanceGem["+i+"].";
			element = oCollection[i].getElementsByTagName("input");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
			element = oCollection[i].getElementsByTagName("select");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
			element = oCollection[i].getElementsByTagName("textarea");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
		}
	}
	
	function updateSalesOrderItemName(){
		var oCollection = document.all("collectionOrderItem");
		var oCollectionOrderItem, oCollectionOrderItemInfo, oCollectionEsimatedMetal, oCollectionEstimatedGem;
		var preFixOrderItem = "";
		var element;

		if (oCollection==null) {
			return true;
		} 

		oCollection = oCollection.length ? oCollection : new Array(oCollection);

		for(var x=0; x < oCollection.length; x++) {
			//Order Item
			oCollectionOrderItem = oCollection[x].all('orderItemList');
			preFix = "orderItem["+x+"].";
			element = oCollectionOrderItem.getElementsByTagName("input");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
			element = oCollectionOrderItem.getElementsByTagName("select");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
			element = oCollectionOrderItem.getElementsByTagName("textarea");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}

			//Order Item Info
			oCollectionOrderItemInfo = oCollection[x].all('orderItemInfoList');
			preFix = "orderItem["+x+"].orderItemInfo[0].";
			element = oCollectionOrderItemInfo.getElementsByTagName("input");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
			element = oCollectionOrderItemInfo.getElementsByTagName("select");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
			element = oCollectionOrderItemInfo.getElementsByTagName("textarea");
			for(var j=0; j < element.length; j++) {
				element[j].name = preFix+element[j].id;
//				alert(element[j].name);
			}
						
			//Estimated Metal
			oCollectionEstimatedMetal = oCollection[x].all('collectionEstimatedMetal',0).getElementsByTagName("tbody")[0].getElementsByTagName("tr");
//			alert(oCollectionEstimatedMetal);
//			alert(oCollectionEstimatedMetal.length);

			for(var y=0; y<oCollectionEstimatedMetal.length; y++) {
				preFix = "orderItem["+x+"].orderItemInfo[0].estimatedMetal["+y+"].";
				
				element = oCollectionEstimatedMetal[y].getElementsByTagName("input");
				for(var j=0; j < element.length; j++) {
					element[j].name = preFix+element[j].id;
//					alert(element[j].name);
				}
				element = oCollectionEstimatedMetal[y].getElementsByTagName("select");
				for(var j=0; j < element.length; j++) {
					element[j].name = preFix+element[j].id;
//					alert(element[j].name);
				}
				element = oCollectionEstimatedMetal[y].getElementsByTagName("textarea");
				for(var j=0; j < element.length; j++) {
					element[j].name = preFix+element[j].id;
//					alert(element[j].name);
				}
			}

			//Estimated Gem
			oCollectionEstimatedGem = oCollection[x].all('collectionEstimatedGem',0).getElementsByTagName("tbody")[0].getElementsByTagName("tr");
			for(var y=0; y<oCollectionEstimatedGem.length; y++) {
				preFix = "orderItem["+x+"].orderItemInfo[0].estimatedGem["+y+"].";
				element = oCollectionEstimatedGem[y].getElementsByTagName("input");
				for(var j=0; j < element.length; j++) {
					element[j].name = preFix+element[j].id;
//					alert(element[j].name);
				}
				element = oCollectionEstimatedGem[y].getElementsByTagName("select");
				for(var j=0; j < element.length; j++) {
					element[j].name = preFix+element[j].id;
//					alert(element[j].name);
				}
				element = oCollectionEstimatedGem[y].getElementsByTagName("textarea");
				for(var j=0; j < element.length; j++) {
					element[j].name = preFix+element[j].id;
//					alert(element[j].name);
				}
			}
		}
		return true;
	}
	
	function updateFormObjects(){
		updateAdvanceMetalName();	
		updateAdvanceGemName();	
		updateSalesOrderItemName();	
	}
	
		
	function calculateAdvanceMetalNetWeight(rowObj) {
		objWeight = rowObj.all("itemWeight",0);
		objWastageRate = rowObj.all("itemWastageRate",0);
		objWastageUnit = rowObj.all("itemWastageRateUnitId",0).options[rowObj.all("itemWastageRateUnitId",0).selectedIndex];
		objNetWeight = rowObj.all("itemNetWeight",0);
		objRate = rowObj.all("itemRate",0);
		objValue = rowObj.all("itemValue",0);

		if (objWastageUnit.value==1)
			objNetWeight.value = parseFloat(objWeight.value) - parseFloat(objWeight.value*objWastageRate.value/100);
		else 
			objNetWeight.value = parseFloat(objWeight.value) - parseFloat(objWeight.value*objWastageRate.value/96);
			
		objValue.value = parseFloat(objNetWeight.value * objRate.value);

	}
	function calculateAdvanceMetalValue(rowObj) {
		objNetWeight = rowObj.all("itemNetWeight",0);
		objRate = rowObj.all("itemRate",0);
		objValue = rowObj.all("itemValue",0);
		objValue.value = parseFloat(objNetWeight.value * objRate.value);

	}

	function calculateItemEstimatedMetalNetWeight(rowObj) {
		objWeight = rowObj.all("itemWeight",0);
		objWastageRate = rowObj.all("itemWastageRate",0);
		objWastageUnit = rowObj.all("itemWastageRateUnitId",0).options[rowObj.all("itemWastageRateUnitId",0).selectedIndex];
		objNetWeight = rowObj.all("itemNetWeight",0);
		objRate = rowObj.all("itemRate",0);
		objValue = rowObj.all("itemValue",0);
		if (objWastageUnit.value==1)
			objNetWeight.value = parseFloat(objWeight.value) + parseFloat(objWeight.value*objWastageRate.value/100);
		else 
			objNetWeight.value = parseFloat(objWeight.value) + parseFloat(objWeight.value*objWastageRate.value/96);
			
		objValue.value = parseFloat(objNetWeight.value * objRate.value);

	}
	function calculateItemEstimatedMetalValue(rowObj) {
		objNetWeight = rowObj.all("itemNetWeight",0);
		objRate = rowObj.all("itemRate",0);
		objValue = rowObj.all("itemValue",0);
		objValue.value = parseFloat(objNetWeight.value * objRate.value);
	}
	
	function calculateItemEstimatedGemValue(oRow) {
		oRate = oRow.all("itemRate",0);
		oValueCalculateBy = oRow.all("itemValueCalculateBy",0).options[oRow.all("itemValueCalculateBy",0).selectedIndex];
		oWeight = oRow.all("itemWeight",0);
		oQuantity = oRow.all("itemQuantity",0);
		oValue = oRow.all("itemValue",0);
		oValue.value = (oValueCalculateBy.value==1) ? (oQuantity.value * oRate.value) : (oWeight.value * oRate.value) ;
	}
</script>