<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="java.util.*,javax.naming.*,java.sql.*,javax.sql.*;"%>

<%		
	Connection connection;
	Statement stVoucher, stVoucherItems, stVoucherJewelleryMetal, stVoucherJewelleryGem;
	ResultSet rsVoucher=null, rsVoucherItems=null,rsVoucherJewelleryMetal=null,rsVoucherJewelleryGem = null;

	// Get JNDI Data Source from Context
	Context initContext = new InitialContext();
	Context ctx = (Context)initContext.lookup("java:/comp/env");

	// Pool Connection 
	connection = ((DataSource)ctx.lookup("jdbc/ZewarShaikhaJeweller")).getConnection();

	//Create Statements 
	stVoucher = connection.createStatement();
	stVoucherItems = connection.createStatement();
	stVoucherJewelleryMetal = connection.createStatement();
	stVoucherJewelleryGem = connection.createStatement();
	//Parse Request Parameters
	String voucherPrefix = (String)request.getParameter("voucherPrefix");
	int voucherPostfix = Integer.parseInt((String)request.getParameter("voucherPostfix")); 
	int itemGroupId = Integer.parseInt((String)request.getParameter("itemGroupId")); 
	int voucherId = 0;			
	byte responseFlag = 0;
	String responseFlagMessage = "";
	
	// get Voucher
	rsVoucher = stVoucher.executeQuery (  
					 " SELECT *  FROM inventory_purchase_vouchers "
					+" WHERE voucher_prefix = '"+voucherPrefix+"'"
					+" AND   voucher_postfix = '"+voucherPostfix+"'"
					+" AND   item_group_id = '"+itemGroupId+"'"
				);
	
	if (rsVoucher.next()) {
		if(rsVoucher.getInt("SALES_OUT_STATUS")==0){

			responseFlag = 1;
			voucherId = rsVoucher.getInt("inventory_purchase_voucher_id");
			
			// get Voucher Entries
			rsVoucherItems = stVoucherItems.executeQuery (
					 " SELECT * FROM inventory_purchase_voucher_items "
					+" WHERE inventory_purchase_voucher_id = '"+voucherId+"'" );
			if(itemGroupId==3){
				rsVoucherJewelleryMetal = stVoucherJewelleryMetal.executeQuery (
						 " SELECT * FROM inventory_purchase_voucher_jewellery_metal "
						+" WHERE INVENTORY_PURCHASE_VOUCHER_ID = '"+voucherId+"'" );
				rsVoucherJewelleryGem= stVoucherJewelleryGem.executeQuery (
						 " SELECT * FROM inventory_purchase_voucher_jewellery_gem "
						+" WHERE INVENTORY_PURCHASE_VOUCHER_ID = '"+voucherId+"'" );
			}
		} else {
			responseFlag = 0;
			responseFlagMessage = "Item has been sales out.";
		} 
	} else {
		responseFlag = 0;
		responseFlagMessage = "Voucher not found.";
	};
%>
<?xml version="1.0" ?>
<RootNote>
	<ControlFlag>
		<ResponseFlag><%=responseFlag%></ResponseFlag>
		<ResponseFlagMessage><%=responseFlagMessage%></ResponseFlagMessage>
	</ControlFlag>
	<% if(responseFlag==1){ %>
		<Voucher>
			<InventorySalesVoucherId><%=rsVoucher.getInt("inventory_purchase_voucher_id")%></InventorySalesVoucherId>
			<ItemGroupId><%=rsVoucher.getString("item_group_id")%></ItemGroupId>
			<VoucherPrefix><%=rsVoucher.getString("voucher_prefix")%></VoucherPrefix>
			<VoucherPostfix><%=rsVoucher.getInt("voucher_postfix")%></VoucherPostfix>
			<InventoryAccountIdOut><%=rsVoucher.getString("inventory_account_id_in")%></InventoryAccountIdOut>
			<EntryDate><%=rsVoucher.getString("entry_date")%></EntryDate>
			<Comments><%=rsVoucher.getString("comments")%></Comments>
			<% while(rsVoucherItems.next()){ %>			
				<VoucherItem>
					<Insertable>true</Insertable>
					<InventorySalesVoucherItemId><%=rsVoucherItems.getString("inventory_purchase_voucher_item_id")%></InventorySalesVoucherItemId>
					<InventoryItemEntryIdOut><%=rsVoucherItems.getString("inventory_item_entry_id_in")%></InventoryItemEntryIdOut>
					<LedgerEntryId><%=rsVoucherItems.getString("ledger_entry_id")%></LedgerEntryId>
					<ItemId><%=rsVoucherItems.getString("item_id")%></ItemId>
					<Quantity><%=rsVoucherItems.getString("quantity")%></Quantity>
					<Weight><%=rsVoucherItems.getString("weight")%></Weight>
					<WeightUnitId><%=rsVoucherItems.getString("weight_unit_id")%></WeightUnitId>
					<Rate><%=rsVoucherItems.getString("rate")%></Rate>
					<ItemValueCalculateBy><%=rsVoucherItems.getString("item_value_calculate_by")%></ItemValueCalculateBy>
					<ItemValue><%=rsVoucherItems.getString("item_value")%></ItemValue>
					<OtherCharges><%=rsVoucherItems.getString("other_charges")%></OtherCharges>
				</VoucherItem>
			<% } %>
			<% while(rsVoucherJewelleryGem.next()){ %>			
				<VoucherJewelleryGem>
					<Insertable>true</Insertable>
					<InventorySalesVoucherJewelleryGemId><%=rsVoucherJewelleryGem.getString("inventory_purchase_voucher_jewellery_gem_id")%></InventorySalesVoucherJewelleryGemId>
					<ItemId><%=rsVoucherJewelleryGem.getString("item_id")%></ItemId>
					<Quantity><%=rsVoucherJewelleryGem.getString("quantity")%></Quantity>
					<Weight><%=rsVoucherJewelleryGem.getString("weight")%></Weight>
					<WeightUnitId><%=rsVoucherJewelleryGem.getString("weight_unit_id")%></WeightUnitId>
					<Rate><%=rsVoucherJewelleryGem.getString("rate")%></Rate>
					<ItemValueCalculateBy><%=rsVoucherJewelleryGem.getString("item_value_calculate_by")%></ItemValueCalculateBy>
					<ItemValue><%=rsVoucherJewelleryGem.getString("item_value")%></ItemValue>
				</VoucherJewelleryGem>
			<% } %>
			<% while(rsVoucherJewelleryMetal.next()){ %>			
				<VoucherJewelleryMetal>
					<Insertable>true</Insertable>
					<InventorySalesVoucherJewelleryMetalId><%=rsVoucherJewelleryMetal.getString("inventory_purchase_voucher_jewellery_metal_id")%></InventorySalesVoucherJewelleryMetalId>
					<MetalItemId><%=rsVoucherJewelleryMetal.getString("metal_item_id")%></MetalItemId>
					<MetalWeight><%=rsVoucherJewelleryMetal.getString("metal_weight")%></MetalWeight>
					<MetalWeightUnitId><%=rsVoucherJewelleryMetal.getString("metal_weight_unit_id")%></MetalWeightUnitId>
					<MetalWeightUnit>gm</MetalWeightUnit>
					<MetalWastageRate><%=rsVoucherJewelleryMetal.getString("metal_wastage_rate")%></MetalWastageRate>
					<MetalWastageUnitId><%=rsVoucherJewelleryMetal.getString("metal_wastage_unit_id")%></MetalWastageUnitId>
					<MetalNetWeight><%=rsVoucherJewelleryMetal.getString("metal_net_weight")%></MetalNetWeight>
					<MetalRate><%=rsVoucherJewelleryMetal.getString("metal_rate")%></MetalRate>
					<MetalValue><%=rsVoucherJewelleryMetal.getString("metal_value")%></MetalValue>
				</VoucherJewelleryMetal>
			<% } %>
		</Voucher> 
	<% } %>
</RootNote> 


