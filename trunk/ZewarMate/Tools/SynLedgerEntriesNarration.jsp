<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ page import="java.util.*,javax.naming.*,java.sql.*,javax.sql.*;"%>
<%
	Connection connection;
	Statement st1, st2;
	ResultSet rs1;

	// Get JNDI Data Source from Context
	Context initContext = new InitialContext();
	Context ctx = (Context) initContext.lookup("java:/comp/env");

	// Pool Connection 
	connection = ((DataSource) ctx.lookup("jdbc/ZewarShaikhaJeweller"))
			.getConnection();

	//Create Statements 
	st1 = connection.createStatement();
	st2 = connection.createStatement();

	int ledgerAccountId = 0;
	int parentLedgerAccountId = 0;
	int accountCodePostfix = 0;

	//Get cash voucher entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM cash_voucher_entries");
	while(rs1.next()) {
		
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("narration")==null? "" : rs1.getString("narration").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("ledger_entry_id"));
	}

	//Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM general_vouchers");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("narration")==null? "" : rs1.getString("narration").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("ledger_entry_id"));
	}
	
	/* //Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM inventory_purchase_voucher_items");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("narration")==null? "" : rs1.getString("narration").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("ledger_entry_id"));
	}*/
	
	/* //Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM inventory_purchase_vouchers");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("narration")==null? "" : rs1.getString("narration").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("ledger_entry_id"));
	}*/
	
	/* //Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM inventory_sales_voucher_items");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("narration")==null? "" : rs1.getString("narration").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("ledger_entry_id"));
	}*/
	
	/*//Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM inventory_sales_vouchers");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("narration")==null? "" : rs1.getString("narration").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("ledger_entry_id"));
	}*/

	//Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM sales_invoices");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("REMARKS")==null? "" : rs1.getString("REMARKS").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("ledger_entry_id"));
	}

	/*//Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM sales_order_cancellation_receipt_advance_gem_used");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("narration")==null? "" : rs1.getString("narration").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("ledger_entry_id"));
	}*/

	/*//Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM sales_order_cancellation_receipt_advance_metal");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("narration")==null? "" : rs1.getString("narration").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("ledger_entry_id"));
	}*/

	/*//Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM sales_order_cancellation_receipts");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("narration")==null? "" : rs1.getString("narration").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("cancellation_charges_ledger_entry_id"));
	}*/

	//Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM sales_order_processes");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("comments")==null? "" : rs1.getString("comments").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("labour_ledger_entry_id"));
	}

	/*//Get entries for updating narration in ledger entries table
	rs1 = st1.executeQuery(" SELECT * FROM sales_orders");
	while(rs1.next()) {
		st2.executeUpdate("UPDATE ledger_entries set narration = '"+(rs1.getString("comments")==null? "" : rs1.getString("narration").replaceAll("'", "''") )+"' where ledger_entry_id = "+ rs1.getInt("advance_cash_ledger_entry_id"));
	}*/

	
	//Release database access objects
	rs1.close();
	rs1 = null;
	st1.close();
	st1 = null;
	st2.close();
	st2 = null;
	connection.close();
	connection = null;
%>