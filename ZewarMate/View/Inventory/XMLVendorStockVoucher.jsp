<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="java.util.*,javax.naming.*,java.sql.*,javax.sql.*;"%>

<%		
	Connection connection;
	Statement stVoucher, stVoucherEntries;
	ResultSet rsVoucher, rsVoucherEntries = null;

	// Get JNDI Data Source from Context
	Context initContext = new InitialContext();
	Context ctx = (Context)initContext.lookup("java:/comp/env");

	// Pool Connection 
	connection = ((DataSource)ctx.lookup("jdbc/ZewarShaikhaJeweller")).getConnection();

	//Create Statements 
	stVoucher = connection.createStatement();
	stVoucherEntries = connection.createStatement();

	//Parse Request Parameters
	String voucherPrefix = (String)request.getParameter("voucherPrefix");
	int voucherPostfix = Integer.parseInt((String)request.getParameter("voucherPostfix")); 
	int voucherId = 0;			
	byte responseFlag = 0;
	String responseFlagMessage = "";
	
	// get Voucher
	rsVoucher = stVoucher.executeQuery (
					 " SELECT"
					+" inventory_vendor_stock_voucher_id"
					+",entry_date"
					+",inventory_account_id_in"
					+",inventory_account_id_out"
					+",voucher_prefix"
					+",voucher_postfix"
					+" FROM inventory_vendor_stock_vouchers"
					+" WHERE voucher_prefix = '"+voucherPrefix+"'"
					+" AND   voucher_postfix = '"+voucherPostfix+"'"
				);
	
	if (rsVoucher.next()) {
		responseFlag = 1;
		voucherId = rsVoucher.getInt("inventory_vendor_stock_voucher_id");
		
		// get Voucher Entries
		rsVoucherEntries = stVoucherEntries.executeQuery (
				 " SELECT"
				+" inventory_vendor_stock_voucher_item_id" 
				+",inventory_vendor_stock_voucher_id"
				+",issue_item_id"
				+",actual_item_id"
				+",issue_weight"
				+",alloy_rate"
				+",alloy_wastage_unit_id"
				+",actual_weight"
				+",comments"
				+",inventory_metal_entry_id_in"
				+",inventory_metal_entry_id_out"
				+" FROM"
				+" inventory_vendor_stock_voucher_items"
				+" WHERE inventory_vendor_stock_voucher_id = '"+voucherId+"'" );
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
			<InventoryVendorStockVoucherId><%=rsVoucher.getInt("inventory_vendor_stock_voucher_id")%></InventoryVendorStockVoucherId>
			<VoucherPrefix><%=rsVoucher.getString("voucher_prefix")%></VoucherPrefix>
			<VoucherPostfix><%=rsVoucher.getInt("voucher_postfix")%></VoucherPostfix>
			<InventoryAccountIdIn><%=rsVoucher.getString("inventory_account_id_in")%></InventoryAccountIdIn>
			<InventoryAccountIdOut><%=rsVoucher.getString("inventory_account_id_out")%></InventoryAccountIdOut>
			<EntryDate><%=rsVoucher.getString("entry_date")%></EntryDate>
			<% while(rsVoucherEntries.next()){ %>			
				<VoucherItemMetal>
					<Insertable>false</Insertable>
					<InventoryVendorStockVoucherItemId><%=rsVoucherEntries.getString("inventory_vendor_stock_voucher_item_id")%></InventoryVendorStockVoucherItemId>
					<IssueItemId><%=rsVoucherEntries.getString("issue_item_id")%></IssueItemId>
					<ActualItemId><%=rsVoucherEntries.getString("actual_item_id")%></ActualItemId>
					<IssueWeight><%=rsVoucherEntries.getString("issue_weight")%></IssueWeight>
					<AlloyRate><%=rsVoucherEntries.getString("alloy_rate")%></AlloyRate>
					<AlloyWastageUnitId><%=rsVoucherEntries.getString("alloy_wastage_unit_id")%></AlloyWastageUnitId>
					<ActualWeight><%=rsVoucherEntries.getString("actual_weight")%></ActualWeight>					
					<Comments><%=rsVoucherEntries.getString("comments")%></Comments>
					<InventoryMetalEntryIdIn><%=rsVoucherEntries.getString("inventory_metal_entry_id_in")%></InventoryMetalEntryIdIn>
					<InventoryMetalEntryIdOut><%=rsVoucherEntries.getString("inventory_metal_entry_id_out")%></InventoryMetalEntryIdOut>
				</VoucherItemMetal>
			<% } %>
		</Voucher> 
	<% } %>
</RootNote> 

