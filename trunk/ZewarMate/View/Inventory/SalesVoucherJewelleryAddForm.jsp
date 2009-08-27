<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

 
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

<html:form action="/Inventory/CreateSalesJewelleryVoucher.do" method="post" styleId="myForm" onsubmit="updateFormObjects();">
 <html:hidden property="hasFormInitialized" />
 <html:hidden property="inventorySalesVoucherId" />
 <html:hidden property="inventoryAccountIdCompany" />
 <html:hidden property="itemGroupId" />
 <html:hidden property="ledgerEntryId" />
 <html:hidden property="cashVoucherId" />
 <html:hidden property="cashVoucherEntryId" />
 <html:hidden property="removeInventorySalesVoucherItemIds" />
  <html:hidden property="removeInventorySalesVoucherJewelleryGemIds" />
 <html:hidden property="removeInventoryItemEntryIdsOut" />
 <input type="hidden" name="submitAction" value="">

 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;">New Sales Jewellery Voucher</span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
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
				<td class="FORM_CAPTION" width="200">Voucher</td>
				<td class="FORM_CONTROL" width="800">
					<html:text property="voucherPrefix" size="8" readonly="true"/>
					<html:text property="voucherPostfix" size="12" onchange="javascript:GetFormDataFromServer();"/>
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
			<tr>
				<td class="FORM_CAPTION">Jewellery</td>
				<td class="FORM_CONTROL">
					<input type="hidden" name="voucherItem[0].insertable" value="">
					<input type="hidden" name="voucherItem[0].inventorySalesVoucherItemId" value="">
					<input type="hidden" name="voucherItem[0].inventoryItemEntryIdOut" value="">
					<input type="hidden" name="voucherItem[0].ledgerEntryId" value="">
					<input type="hidden" name="voucherItem[0].quantity" value="">
					<input type="hidden" name="voucherItem[0].weightUnitId" value="">
					<input type="hidden" name="voucherItem[0].rate" value="">
					<input type="hidden" name="voucherItem[0].itemValueCalculateBy" value="">
					<select name="voucherItem[0].itemId" >
						<c:forEach var="lookup" items="${applicationScope.lookupItemJewellery}" varStatus="status" >
							<option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option>
						  </c:forEach>
					</select>
				</td>
			</tr>

			<tr>
				<td class="FORM_CAPTION">Jewellery Weight</td>
				<td class="FORM_CONTROL">
					<input type="text" name="voucherItem[0].weight" maxlength="10" size="10" > 
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Jewellery Value</td>
				<td class="FORM_CONTROL">
					<input type="text" name="voucherItem[0].itemValue" maxlength="10" size="10" > 
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Making/Other Charges</td>
				<td class="FORM_CONTROL">
					<input type="text" name="voucherItem[0].otherCharges" maxlength="10" size="10" > 
				</td>
			</tr>
		</table>

		<br>
		<div class="FORM_AREA">
			Metal
			<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="953">
				<tr>
					<td width="30" class="LIST_HEADER">&nbsp;</td>
					<td width="200" class="LIST_HEADER">Item</td>
					<td width="125" class="LIST_HEADER">Weight</td>
					<td width="203" class="LIST_HEADER">Wastage</td>
					<td width="150" class="LIST_HEADER">Net.Wgt</td>
					<td width="95" class="LIST_HEADER">Rate</td>
					<td width="150" class="LIST_HEADER">Value</td>
				</tr>
				<tr>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA">
						<html:hidden property="inventorySalesVoucherJewelleryMetalId" /> 
						<nested:select  property="metalItemId"> 
							<html:options collection="lookupItemMetal" property="id" labelProperty="label" />
						</nested:select>
					</td>
					<td class="LIST_DATA"><html:text property="metalWeight" maxlength="6" size="6" onchange="JavaScript:calculateMetalUsedNetWeight(this);calculateMetalUsedValue(this);"/> <html:hidden property="metalWeightUnitId"/><bean:write name="inventorySalesVoucherForm" property="metalWeightUnit"/></td>
					<td class="LIST_DATA">
						<html:text property="metalWastageRate" maxlength="6" size="6" onchange="JavaScript:calculateMetalUsedNetWeight(this);calculateMetalUsedValue(this);"/> 
						<nested:select property="metalWastageUnitId" onchange="JavaScript:calculateMetalUsedNetWeight(this);calculateMetalUsedValue(this);">
							<html:options collection="lookupWastageUnit"  property="id" labelProperty="label" />
						</nested:select>
					</td>
					<td class="LIST_DATA"><html:text property="metalNetWeight" maxlength="6" size="6" onchange="JavaScript:calculateMetalUsedNetWeight(this);calculateMetalUsedValue(this);"/></td>
					<td class="LIST_DATA"><html:text property="metalRate" maxlength="6" size="6" onchange="JavaScript:calculateMetalUsedNetWeight(this);calculateMetalUsedValue(this);"/></td>
					<td class="LIST_DATA"><html:text property="metalValue" maxlength="10" size="10" /></td>
				</tr>				
			</table>
		</div>
		

		<!-- Voucher Items-->
		<br>
		<div class="FORM_AREA">
			<div class="FORM_AREA"><span style="width:475px">Gems</span><span style="width:475;text-align:right;"><a href="#" onclick="addItemOnVoucherjewelleryGemFromSource(new ClassVoucherItem());">Add More</a></span></div>
			<div style="width:953px;" class="LIST_AREA">
				<span style="width:30px;" class="LIST_HEADER">&nbsp;</span><span style="width:200px;" class="LIST_HEADER">Items</span><span style="width:125px;" class="LIST_HEADER">Weight</span><span style="width:203px;" class="LIST_HEADER"> Qty</span><span style="width:150px;" class="LIST_HEADER"> Calculate By</span><span style="width:95px;" class="LIST_HEADER">Rate</span><span style="width:150px;" class="LIST_HEADER">Value</span>
			</div>
			<div style="width:953px; height:200px;padding:0px; margin:0px; overflow:auto;" >
				<table class="LIST_AREA" cellpadding="0" cellspacing="1" id="TBL_VOUCHER_JEWELLERY_GEM" width="935" >
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
		this.itemGroupId = "3";
		this.voucherPrefix = "";
		this.voucherPostfix = "";
		this.inventoryAccountIdCompany = "";
		this.entryDate = "";
		this.comments = "";
		this.removeInventorySalesVoucherItemIds = 0;
		this.removeInventorySalesVoucherJewelleryGemIds = 0;
		this.removeInventoryItemEntryIdsOut = 0;
		this.oGListClassVoucherItem = new Array();
		this.oGListClassVoucherJewelleryGem = new Array();
		this.oGListClassVoucherJewelleryMetal = new Array();
	}

	function ClassVoucherItem() {
		this.insertable = true;
		this.inventorySalesVoucherItemId = 0;
		this.inventoryItemEntryIdOut = 0;
		this.itemId = 0;
		this.quantity = 0;
		this.weight = 0.0;
		this.weightUnitId = 0; 
		this.rate = 0.0;	
		this.itemValueCalculateBy = 0;
		this.itemValue = 0.0;
		this.ledgerEntryId = 0.0;
		this.otherCharges= 0.0;
	}

	function ClassVoucherJewelleryMetal() {
		this.insertable = true;
		this.inventorySalesVoucherJewelleryMetalId = 0;
		this.metalItemId = 0;
		this.metalWeight = 0.0;
		this.metalWeightUnitId = 0; 
		this.metalWeightUnit = '';	
		this.metalWastageRate = 0.0;
		this.metalWastageUnitId = 0;
		this.metalNetWeight = 0.0;
		this.metalRate = 0.0;
		this.metalValue = 0.0;
		
	}
	
	function ClassVoucherJewelleryGem() {
		this.insertable = true;
		this.inventorySalesVoucherJewelleryGemId = 0;
		this.itemId = 0;
		this.quantity = 0;
		this.weight = 0.0;
		this.weightUnitId = 0; 
		this.rate = 0.0;	
		this.itemValueCalculateBy = 0;
		this.itemValue = 0.0;
	}

	var oGXmlHttp;
	var oGXmlDoc;
	var oGMessages = document.getElementById("messages");
	
	var oGForm = document.getElementById("myForm");

	var oGTableVoucherJewelleryGem = document.getElementById("TBL_VOUCHER_JEWELLERY_GEM");	

	var oGClassVoucher = new ClassVoucher();

	oGClassVoucher.inventorySalesVoucherId = '<c:out value="${inventorySalesVoucherForm.inventorySalesVoucherId}"/>';
	oGClassVoucher.itemGroupId = '<c:out value="${inventorySalesVoucherForm.itemGroupId}"/>';
	oGClassVoucher.voucherPrefix = '<c:out value="${inventorySalesVoucherForm.voucherPrefix}"/>';
	oGClassVoucher.voucherPostfix = '<c:out value="${inventorySalesVoucherForm.voucherPostfix}"/>';
	oGClassVoucher.inventoryAccountIdCompany = '<c:out value="${inventorySalesVoucherForm.inventoryAccountIdCompany}"/>';
	oGClassVoucher.entryDate = '<c:out value="${inventorySalesVoucherForm.entryDate}"/>';
	oGClassVoucher.comments = '<c:out value="${inventorySalesVoucherForm.comments}"/>';

	//Jewellery Metal
	oClassVoucherJewelleryMetal = new ClassVoucherJewelleryMetal();
	oClassVoucherJewelleryMetal.insertable = false;
	oClassVoucherJewelleryMetal.inventorySalesVoucherJewelleryMetalId = '<c:out value="${inventorySalesVoucherForm.inventorySalesVoucherJewelleryMetalId}"/>';
	oClassVoucherJewelleryMetal.metalItemId = '<c:out value="${inventorySalesVoucherForm.metalItemId}"/>';
	oClassVoucherJewelleryMetal.metalWeight = '<c:out value="${inventorySalesVoucherForm.metalWeight}"/>';
	oClassVoucherJewelleryMetal.metalWeightUnitId = '<c:out value="${inventorySalesVoucherForm.metalWeightUnitId}"/>'; 
	oClassVoucherJewelleryMetal.metalWeightUnit = '<c:out value="${inventorySalesVoucherForm.metalWeightUnit}"/>';	
	oClassVoucherJewelleryMetal.metalWastageRate = '<c:out value="${inventorySalesVoucherForm.metalWastageRate}"/>';
	oClassVoucherJewelleryMetal.metalWastageUnitId = '<c:out value="${inventorySalesVoucherForm.metalWastageUnitId}"/>';
	oClassVoucherJewelleryMetal.metalNetWeight = '<c:out value="${inventorySalesVoucherForm.metalNetWeight}"/>';
	oClassVoucherJewelleryMetal.metalRate = '<c:out value="${inventorySalesVoucherForm.metalRate}"/>';
	oClassVoucherJewelleryMetal.metalValue = '<c:out value="${inventorySalesVoucherForm.metalValue}"/>';	
	oGClassVoucher.oGListClassVoucherJewelleryMetal[0] = oClassVoucherJewelleryMetal;
	// Voucher Item					
	<c:forEach var="item" items="${inventorySalesVoucherForm.voucherItemList}" varStatus="itemStatus" >
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>] = new ClassVoucherItem();
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].insertable = '<c:out value="${item.insertable}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].inventorySalesVoucherItemId  = '<c:out value="${item.inventorySalesVoucherItemId }"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].inventoryItemEntryIdOut = '<c:out value="${item.inventoryItemEntryIdOut}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].ledgerEntryId = '<c:out value="${item.ledgerEntryId}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].itemId = '<c:out value="${item.itemId}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].quantity = '<c:out value="${item.quantity}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].weight = '<c:out value="${item.weight}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].weightUnitId = '<c:out value="${item.weightUnitId}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].rate = '<c:out value="${item.rate}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].itemValueCalculateBy = '<c:out value="${item.itemValueCalculateBy}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].itemValue= '<c:out value="${item.itemValue}"/>';
		oGClassVoucher.oGListClassVoucherItem[<c:out value="${itemStatus.index}"/>].otherCharges= '<c:out value="${item.otherCharges}"/>';
	</c:forEach>


	// Voucher Jewellery Gem					
	<c:forEach var="item" items="${inventorySalesVoucherForm.voucherJewelleryGemList}" varStatus="itemStatus" >
		oGClassVoucher.oGListClassVoucherJewelleryGem[<c:out value="${itemStatus.index}"/>] = new ClassVoucherJewelleryGem();
		oGClassVoucher.oGListClassVoucherJewelleryGem[<c:out value="${itemStatus.index}"/>].insertable = '<c:out value="${item.insertable}"/>';
		oGClassVoucher.oGListClassVoucherJewelleryGem[<c:out value="${itemStatus.index}"/>].inventorySalesVoucherJewelleryGemId  = '<c:out value="${item.inventorySalesVoucherJewelleryGemId }"/>';
		oGClassVoucher.oGListClassVoucherJewelleryGem[<c:out value="${itemStatus.index}"/>].itemId = '<c:out value="${item.itemId}"/>';
		oGClassVoucher.oGListClassVoucherJewelleryGem[<c:out value="${itemStatus.index}"/>].quantity = '<c:out value="${item.quantity}"/>';
		oGClassVoucher.oGListClassVoucherJewelleryGem[<c:out value="${itemStatus.index}"/>].weight = '<c:out value="${item.weight}"/>';
		oGClassVoucher.oGListClassVoucherJewelleryGem[<c:out value="${itemStatus.index}"/>].weightUnitId = '<c:out value="${item.weightUnitId}"/>';
		oGClassVoucher.oGListClassVoucherJewelleryGem[<c:out value="${itemStatus.index}"/>].rate = '<c:out value="${item.rate}"/>';
		oGClassVoucher.oGListClassVoucherJewelleryGem[<c:out value="${itemStatus.index}"/>].itemValueCalculateBy = '<c:out value="${item.itemValueCalculateBy}"/>';
		oGClassVoucher.oGListClassVoucherJewelleryGem[<c:out value="${itemStatus.index}"/>].itemValue= '<c:out value="${item.itemValue}"/>';
	</c:forEach>


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

		var url="XmlSalesJewelleryVoucher.do?&cache=0&itemGroupId="+oGForm.itemGroupId.value+"&voucherPrefix="+oGForm.voucherPrefix.value+"&voucherPostfix="+oGForm.voucherPostfix.value;
		//alert(url);
		oGXmlHttp=GetXmlHttpObject(stateChanged);
		oGXmlHttp.open("POST", url , true);
		oGXmlHttp.send(null); 
	}

	function stateChanged() { 
		if (oGXmlHttp.readyState==4 || oGXmlHttp.readyState=="complete") { 
			oGMessages.innerText = " ";
			oGXmlDoc = loadXML(oGXmlHttp.responseBody);
			//alert(oGXmlHttp.responseText);
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
					oGClassVoucher.inventorySalesVoucherId = oLXmlVoucher[0].getElementsByTagName("InventorySalesVoucherId")[0].text;
					oGClassVoucher.itemGroupId = oLXmlVoucher[0].getElementsByTagName("ItemGroupId")[0].text;
					oGClassVoucher.voucherPrefix = oLXmlVoucher[0].getElementsByTagName("VoucherPrefix")[0].text;
					oGClassVoucher.voucherPostfix = oLXmlVoucher[0].getElementsByTagName("VoucherPostfix")[0].text;
					oGClassVoucher.inventoryAccountIdCompany = oLXmlVoucher[0].getElementsByTagName("InventoryAccountIdOut")[0].text;
					oGClassVoucher.entryDate = oLXmlVoucher[0].getElementsByTagName("EntryDate")[0].text;
					oGClassVoucher.comments = oLXmlVoucher[0].getElementsByTagName("Comments")[0].text;
					
					

					//load Voucher Entry from XML data
					oGClassVoucher.oGListClassVoucherItem.length = 0;
					oLXmlVoucherItem = oLXmlVoucher[0].getElementsByTagName("VoucherItem");
					for(var i=0; i < oLXmlVoucherItem.length; i++) {
						oClassVoucherItem = new ClassVoucherItem();
						oClassVoucherItem.insertable = oLXmlVoucherItem[i].getElementsByTagName("Insertable")[0].text;
						oClassVoucherItem.inventorySalesVoucherItemId  = oLXmlVoucherItem[i].getElementsByTagName("InventorySalesVoucherItemId")[0].text;
						oClassVoucherItem.inventoryItemEntryIdOut = oLXmlVoucherItem[i].getElementsByTagName("InventoryItemEntryIdOut")[0].text;
						oClassVoucherItem.ledgerEntryId = oLXmlVoucherItem[i].getElementsByTagName("LedgerEntryId")[0].text;
						oClassVoucherItem.itemId = oLXmlVoucherItem[i].getElementsByTagName("ItemId")[0].text;
						oClassVoucherItem.quantity = oLXmlVoucherItem[i].getElementsByTagName("Quantity")[0].text;
						oClassVoucherItem.weight = oLXmlVoucherItem[i].getElementsByTagName("Weight")[0].text;
						oClassVoucherItem.weightUnitId = oLXmlVoucherItem[i].getElementsByTagName("WeightUnitId")[0].text;
						oClassVoucherItem.rate = oLXmlVoucherItem[i].getElementsByTagName("Rate")[0].text;
						oClassVoucherItem.itemValueCalculateBy = oLXmlVoucherItem[i].getElementsByTagName("ItemValueCalculateBy")[0].text;
						oClassVoucherItem.itemValue = oLXmlVoucherItem[i].getElementsByTagName("ItemValue")[0].text;
						oClassVoucherItem.otherCharges = oLXmlVoucherItem[i].getElementsByTagName("OtherCharges")[0].text;
						oGClassVoucher.oGListClassVoucherItem[i] = oClassVoucherItem;
					}
					
					//load Voucher Jewellery Gem Entry from XML data
					oGClassVoucher.oGListClassVoucherJewelleryGem.length = 0;
					oLXmlVoucherJewelleryGem = oLXmlVoucher[0].getElementsByTagName("VoucherJewelleryGem");
					for(var i=0; i < oLXmlVoucherJewelleryGem.length; i++) {
						oClassVoucherJewelleryGem = new ClassVoucherJewelleryGem();
						oClassVoucherJewelleryGem.insertable = oLXmlVoucherJewelleryGem[i].getElementsByTagName("Insertable")[0].text;
						oClassVoucherJewelleryGem.inventorySalesVoucherJewelleryGemId  = oLXmlVoucherJewelleryGem[i].getElementsByTagName("InventorySalesVoucherJewelleryGemId")[0].text;
						oClassVoucherJewelleryGem.itemId = oLXmlVoucherJewelleryGem[i].getElementsByTagName("ItemId")[0].text;
						oClassVoucherJewelleryGem.quantity = oLXmlVoucherJewelleryGem[i].getElementsByTagName("Quantity")[0].text;
						oClassVoucherJewelleryGem.weight = oLXmlVoucherJewelleryGem[i].getElementsByTagName("Weight")[0].text;
						oClassVoucherJewelleryGem.weightUnitId = oLXmlVoucherJewelleryGem[i].getElementsByTagName("WeightUnitId")[0].text;
						oClassVoucherJewelleryGem.rate = oLXmlVoucherJewelleryGem[i].getElementsByTagName("Rate")[0].text;
						oClassVoucherJewelleryGem.itemValueCalculateBy = oLXmlVoucherJewelleryGem[i].getElementsByTagName("ItemValueCalculateBy")[0].text;
						oClassVoucherJewelleryGem.itemValue = oLXmlVoucherJewelleryGem[i].getElementsByTagName("ItemValue")[0].text;
						oGClassVoucher.oGListClassVoucherJewelleryGem[i] = oClassVoucherJewelleryGem;
					}
					
					oGClassVoucher.oGListClassVoucherJewelleryMetal.length = 0;
					oLXmlVoucherJewelleryMetal = oLXmlVoucher[0].getElementsByTagName("VoucherJewelleryMetal");
					for(var i=0; i < oLXmlVoucherJewelleryMetal.length; i++) {
						oClassVoucherJewelleryMetal = new ClassVoucherJewelleryMetal();
						oClassVoucherJewelleryMetal.insertable = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("Insertable")[0].text;
						oClassVoucherJewelleryMetal.inventorySalesVoucherJewelleryMetalId  = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("InventorySalesVoucherJewelleryMetalId")[0].text;
						oClassVoucherJewelleryMetal.metalItemId = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("MetalItemId")[0].text;
						oClassVoucherJewelleryMetal.metalWeight = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("MetalWeight")[0].text;
						oClassVoucherJewelleryMetal.metalWeightUnitId = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("MetalWeightUnitId")[0].text;
						oClassVoucherJewelleryMetal.metalWeightUnit = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("MetalWeightUnit")[0].text;
						oClassVoucherJewelleryMetal.metalWastageRate = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("MetalWastageRate")[0].text;
						oClassVoucherJewelleryMetal.metalWastageUnitId = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("MetalWastageUnitId")[0].text;
						oClassVoucherJewelleryMetal.metalNetWeight = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("MetalNetWeight")[0].text;
						oClassVoucherJewelleryMetal.metalRate = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("MetalRate")[0].text;
						oClassVoucherJewelleryMetal.metalValue = oLXmlVoucherJewelleryMetal[i].getElementsByTagName("MetalValue")[0].text;
						oGClassVoucher.oGListClassVoucherJewelleryMetal[i] = oClassVoucherJewelleryMetal;
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
		for(var i=0; i < oGClassVoucher.oGListClassVoucherJewelleryGem.length; i++) {
			addItemOnVoucherJewelleryGem(oGClassVoucher.oGListClassVoucherJewelleryGem[i]);
		}
		for(var i=0; i < oGClassVoucher.oGListClassVoucherItem.length; i++) {
			document.getElementsByName("voucherItem[0].insertable")[0].value = oGClassVoucher.oGListClassVoucherItem[i].insertable;
			document.getElementsByName("voucherItem[0].inventorySalesVoucherItemId")[0].value  = oGClassVoucher.oGListClassVoucherItem[i].inventorySalesVoucherItemId;
			document.getElementsByName("voucherItem[0].inventoryItemEntryIdOut")[0].value = oGClassVoucher.oGListClassVoucherItem[i].inventoryItemEntryIdOut;
			document.getElementsByName("voucherItem[0].ledgerEntryId")[0].value = oGClassVoucher.oGListClassVoucherItem[i].ledgerEntryId;
			setControlSelect(document.getElementsByName("voucherItem[0].itemId")[0], oGClassVoucher.oGListClassVoucherItem[i].itemId);
			document.getElementsByName("voucherItem[0].quantity")[0].value = oGClassVoucher.oGListClassVoucherItem[i].quantity;
			document.getElementsByName("voucherItem[0].weight")[0].value = oGClassVoucher.oGListClassVoucherItem[i].weight;
			document.getElementsByName("voucherItem[0].weightUnitId")[0].value = oGClassVoucher.oGListClassVoucherItem[i].weightUnitId;
			document.getElementsByName("voucherItem[0].rate")[0].value = oGClassVoucher.oGListClassVoucherItem[i].rate;
			document.getElementsByName("voucherItem[0].itemValueCalculateBy")[0].value = oGClassVoucher.oGListClassVoucherItem[i].itemValueCalculateBy;
			document.getElementsByName("voucherItem[0].itemValue")[0].value = oGClassVoucher.oGListClassVoucherItem[i].itemValue;
			document.getElementsByName("voucherItem[0].otherCharges")[0].value = oGClassVoucher.oGListClassVoucherItem[i].otherCharges;
		}

		for(var i=0; i < oGClassVoucher.oGListClassVoucherJewelleryMetal.length; i++) {
			document.getElementsByName("inventorySalesVoucherJewelleryMetalId")[0].value = oGClassVoucher.oGListClassVoucherJewelleryMetal[i].inventorySalesVoucherJewelleryMetalId;
			setControlSelect(document.getElementsByName("metalItemId")[0] , oGClassVoucher.oGListClassVoucherJewelleryMetal[i].metalItemId);
			document.getElementsByName("metalWeight")[0].value = oGClassVoucher.oGListClassVoucherJewelleryMetal[i].metalWeight;
			document.getElementsByName("metalWeightUnitId")[0].value = oGClassVoucher.oGListClassVoucherJewelleryMetal[i].metalWeightUnitId; 
//			document.getElementsByName("metalWeightUnit")[0].value = oGClassVoucher.oGListClassVoucherJewelleryMetal[i].metalWeightUnit;	
			document.getElementsByName("metalWastageRate")[0].value = oGClassVoucher.oGListClassVoucherJewelleryMetal[i].metalWastageRate;
			setControlSelect(document.getElementsByName("metalWastageUnitId")[0] , oGClassVoucher.oGListClassVoucherJewelleryMetal[i].metalWastageUnitId);
			document.getElementsByName("metalNetWeight")[0].value = oGClassVoucher.oGListClassVoucherJewelleryMetal[i].metalNetWeight;
			document.getElementsByName("metalRate")[0].value = oGClassVoucher.oGListClassVoucherJewelleryMetal[i].metalRate;
			document.getElementsByName("metalValue")[0].value = oGClassVoucher.oGListClassVoucherJewelleryMetal[i].metalValue;
		}

		oGForm.hasFormInitialized.value = "Y";		
		oGForm.voucherPostfix.focus();				
	}
	
	function unloadFormData() {
		//unload Form Control data
		oGForm.submitAction[1].disabled = true;
		oGForm.inventorySalesVoucherId.value = "";

		oGForm.entryDate.readOnly = false; 
		oGForm.entryDate.value = "";
		oGForm.entryDate.readOnly = true;
		
		//unload voucher entries
		var oLRows = oGTableVoucherJewelleryGem.getElementsByTagName("tbody").item(0).rows;

		var vLRows = oLRows.length;

		for(; vLRows > 0; vLRows--) {
			removeItemOnVoucherJewelleryGem(oLRows.item(vLRows-1));

		}

		//deactivate Form Control

		oGForm.entryDate.disabled = true;
		oGForm.buttonEntryDate.disabled = true;
		
		oGForm.hasFormInitialized.value = "N";
		oGClassVoucher = new ClassVoucher();
		oGMessages.innerText = " ";
		oGForm.voucherPostfix.focus();
	}

	function updateVoucherJewelleryGemRefrence(){
		var oListVoucherItem = GetElementsById(oGForm, "VOUCHER_JEWELLERY_GEM_INVENTORY_SALES_VOUCHER_JEWELLERY_GEM_ID");

		if (eval(oListVoucherItem)) {

			if (eval(oListVoucherItem.length) && eval(oListVoucherItem.length) > 1) {

				for (i=0; i < (oListVoucherItem.length); i++) {

					oGForm.VOUCHER_ITEM_INSERTABLE[i].name = "voucherJewelleryGem["+i+"].insertable";
					oGForm.VOUCHER_JEWELLERY_GEM_INVENTORY_SALES_VOUCHER_JEWELLERY_GEM_ID[i].name = "voucherJewelleryGem["+i+"].inventorySalesVoucherJewelleryGemId";
					oGForm.VOUCHER_ITEM_ITEM_ID[i].name = "voucherJewelleryGem["+i+"].itemId";
					oGForm.VOUCHER_ITEM_QUANTITY[i].name = "voucherJewelleryGem["+i+"].quantity";
					oGForm.VOUCHER_ITEM_WEIGHT[i].name = "voucherJewelleryGem["+i+"].weight";
					oGForm.VOUCHER_ITEM_WEIGHT_UNIT_ID[i].name = "voucherJewelleryGem["+i+"].weightUnitId";
					oGForm.VOUCHER_ITEM_RATE[i].name = "voucherJewelleryGem["+i+"].rate";
					oGForm.VOUCHER_ITEM_ITEM_VALUE_CALCULATE_BY[i].name = "voucherJewelleryGem["+i+"].itemValueCalculateBy";
					oGForm.VOUCHER_ITEM_ITEM_VALUE[i].name = "voucherJewelleryGem["+i+"].itemValue";
				}
						
			} else if (eval(oListVoucherItem.length) && eval(oListVoucherItem.length) == 1) {
					oGForm.VOUCHER_ITEM_INSERTABLE.name = "voucherJewelleryGem[0].insertable";
					oGForm.VOUCHER_JEWELLERY_GEM_INVENTORY_SALES_VOUCHER_JEWELLERY_GEM_ID.name = "voucherJewelleryGem[0].inventorySalesVoucherJewelleryGemId";
					oGForm.VOUCHER_ITEM_ITEM_ID.name = "voucherJewelleryGem[0].itemId";
					oGForm.VOUCHER_ITEM_QUANTITY.name = "voucherJewelleryGem[0].quantity";
					oGForm.VOUCHER_ITEM_WEIGHT.name = "voucherJewelleryGem[0].weight";
					oGForm.VOUCHER_ITEM_WEIGHT_UNIT_ID.name = "voucherJewelleryGem[0].weightUnitId";
					oGForm.VOUCHER_ITEM_RATE.name = "voucherJewelleryGem[0].rate";
					oGForm.VOUCHER_ITEM_ITEM_VALUE_CALCULATE_BY.name = "voucherJewelleryGem[0].itemValueCalculateBy";
					oGForm.VOUCHER_ITEM_ITEM_VALUE.name = "voucherJewelleryGem[0].itemValue";
			}
		}
	}
	
	function removeItemOnVoucherJewelleryGem(oRow) {
	//	var index = oRow.rowIndex;
		var collection ;												  

		collection = GetElementsById(oRow.getElementsByTagName("input"), "VOUCHER_JEWELLERY_GEM_INVENTORY_SALES_VOUCHER_JEWELLERY_GEM_ID");
		oGClassVoucher.removeInventorySalesVoucherJewelleryGemIds+= (collection[0].value==0 ? "" : ","+collection[0].value) ;
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
		updateVoucherJewelleryGemRefrence();

		oGForm.removeInventorySalesVoucherItemIds.value = oGClassVoucher.removeInventorySalesVoucherItemIds;
		oGForm.removeInventoryItemEntryIdsOut.value = oGClassVoucher.removeInventoryItemEntryIdsOut;
		oGForm.removeInventorySalesVoucherJewelleryGemIds.value = oGClassVoucher.removeInventorySalesVoucherJewelleryGemIds;
		
		oGForm.hasFormInitialized.value = "Y";
		oGForm.submitAction[0].value = userAction;

//		alert(oGForm.removeInventoryItemEntryIdsOut.value);
//		alert(oGForm.removeInventoryItemEntryIdsOut.value);

		oGForm.submit();
	}
	
	function addItemOnVoucherjewelleryGemFromSource() {
		
		addItemOnVoucherJewelleryGem (new ClassVoucherJewelleryGem());
	}
	
	function addItemOnVoucherJewelleryGem(oClassVoucherJewelleryGem) {
		if (!eval(oClassVoucherJewelleryGem)) {
			alert ('illegal Object : Item Gem.');
			return;
		}

		oPlaceHolder = oGTableVoucherJewelleryGem.getElementsByTagName("tbody").item(0);

		oTr = document.createElement('tr');
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=30;
//				oTd.appendChild(document.createElement('<input type="hidden" name="voucherJewelleryGem[].inventoryItemEntryIdOut" id="VOUCHER_ITEM_INVENTORY_ITEM_ENTRY_ID_IN" value="'+oClassVoucherJewelleryGem.inventoryItemEntryIdOut+'" >'));
//				oTd.appendChild(document.createElement('<input type="hidden" name="voucherJewelleryGem[].ledgerEntryId" id="VOUCHER_ITEM_LEDGER_ENTRY_ID" value="'+oClassVoucherJewelleryGem.ledgerEntryId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherJewelleryGem[].inventorySalesVoucherJewelleryGemId" id="VOUCHER_JEWELLERY_GEM_INVENTORY_SALES_VOUCHER_JEWELLERY_GEM_ID" value="'+oClassVoucherJewelleryGem.inventorySalesVoucherJewelleryGemId+'" >'));
				oTd.appendChild(document.createElement('<input type="hidden" name="voucherJewelleryGem[].insertable" id="VOUCHER_ITEM_INSERTABLE" value="'+oClassVoucherJewelleryGem.insertable+'" >'));
				oA = document.createElement("a");
					oA.appendChild(document.createTextNode('D'));
					oA.href = '#';
					oA.attachEvent("onclick", function () { removeItemOnVoucherJewelleryGem(GetParentByTagName(window.event.srcElement, "TR"))});
					oTd.appendChild(oA)
				oTr.appendChild(oTd);
			
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=200;
				oTd.appendChild(createSelect('VOUCHER_ITEM_ITEM_ID', 'voucherJewelleryGem[].itemId', lookupItemGem, oClassVoucherJewelleryGem.itemId));
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=125;
				oInput = document.createElement('<input type="text" name="voucherJewelleryGem[].weight" id="VOUCHER_ITEM_WEIGHT"  size="4" value="'+oClassVoucherJewelleryGem.weight+'">');
				oInput.attachEvent("onchange", function () {	calculateValueVoucherItem(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTd.appendChild(createSelect('VOUCHER_ITEM_WEIGHT_UNIT_ID', 'voucherJewelleryGem[].weightUnitId', lookupWeightUnit, oClassVoucherJewelleryGem.weightUnitId));
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=203;
				oInput = document.createElement('<input type="text" name="voucherJewelleryGem[].quantity" id="VOUCHER_ITEM_QUANTITY"  size="4" value="'+oClassVoucherJewelleryGem.quantity+'">')
				oInput.attachEvent("onchange", function () {	calculateValueVoucherItem(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=150;
				oSelect = createSelect('VOUCHER_ITEM_ITEM_VALUE_CALCULATE_BY', 'voucherJewelleryGem[].itemValueCalculateBy', lookupItemValueCalculateBy, oClassVoucherJewelleryGem.itemValueCalculateBy);
				oSelect.attachEvent("onchange", function () {	calculateValueVoucherItem(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oSelect);
				oTr.appendChild(oTd);

			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=95;
				oInput = document.createElement('<input type="text" name="voucherJewelleryGem[].rate" id="VOUCHER_ITEM_RATE" size="4" value="'+oClassVoucherJewelleryGem.rate+'">');
				oInput.attachEvent("onchange", function () {	calculateValueVoucherItem(window.event.srcElement.parentElement.parentElement)});
				oTd.appendChild(oInput);
				oTr.appendChild(oTd);
				
			oTd = document.createElement('td');
				oTd.className='LIST_DATA';
				oTd.width=132;
				oTd.appendChild(document.createElement('<input type="text" name="voucherJewelleryGem[].itemValue" id="VOUCHER_ITEM_ITEM_VALUE" size="7" value="'+oClassVoucherJewelleryGem.itemValue+'" readonly=true>'));
				oTr.appendChild(oTd);
				
		oPlaceHolder.appendChild(oTr);
		calculateValueVoucherItem(oTr);

	}

	function calculateValueVoucherItem(oRow) {
		/*	For Gem Item */
		oRate = oRow.all("VOUCHER_ITEM_RATE",0);
		oValueCalculateBy = oRow.all("VOUCHER_ITEM_ITEM_VALUE_CALCULATE_BY",0).options[oRow.all("VOUCHER_ITEM_ITEM_VALUE_CALCULATE_BY",0).selectedIndex];
		oWeight = oRow.all("VOUCHER_ITEM_WEIGHT",0);
		oQuantity = oRow.all("VOUCHER_ITEM_QUANTITY",0);
		oValue = oRow.all("VOUCHER_ITEM_ITEM_VALUE",0);
		oValue.value = (oValueCalculateBy.value==1) ? (oQuantity.value * oRate.value) : (oWeight.value * oRate.value) ;
	}
	document.onload=initializeForm();
-->
</script>

<script type="text/javascript">
<!--
	function calculateMetalUsedNetWeight(obj) {

		oWeight = document.getElementsByName("metalWeight").item(0);
		oWeightUnitId = document.getElementsByName("metalWeightUnitId").item(0);
		oWastageRate = document.getElementsByName("metalWastageRate").item(0);
		oWastageUnitId = document.getElementsByName("metalWastageUnitId").item(0);
		oNetWeight = document.getElementsByName("metalNetWeight").item(0);
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
		oRate = document.getElementsByName("metalRate").item(0);
		oNetWeight = document.getElementsByName("metalNetWeight").item(0);
		oItemValue= document.getElementsByName("metalValue").item(0);
		var vNetWeight = parseFloat(oNetWeight.value);
		var vRate = parseFloat(oRate.value);
		oItemValue.value = Math.abs(vNetWeight * vRate).toFixed(2);
	}

//-->
</script>
