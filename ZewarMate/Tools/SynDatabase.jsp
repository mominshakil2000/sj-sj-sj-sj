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
			//ResultSet rs1, rs2;

			// Get JNDI Data Source from Context
			Context initContext = new InitialContext();
			Context ctx = (Context)initContext.lookup("java:/comp/env");

			// Pool Connection 
			connection = ((DataSource)ctx.lookup("jdbc/ZewarShaikhaJeweller")).getConnection();

			//Create Statements 
			st1 = connection.createStatement();
			boolean status=true;
			String statusDescription = new String();
			String[] commands  
			 = 	{	 "delete from cash_voucher_entries;",
			 "delete from cash_vouchers;",
			 "delete from general_vouchers;",
			 "delete from inventory_gem_entries;",
			 "delete from inventory_jewellery_entries;",
			 "delete from inventory_metal_entries;",
			 "delete from inventory_metal_item_entries;",
			 "delete from inventory_purchase_voucher_items;",
			 "delete from inventory_purchase_voucher_jewellery_gem;",
			 "delete from inventory_purchase_voucher_jewellery_metal;",
			 "delete from inventory_purchase_vouchers;",
			 "delete from inventory_receipt_items;",
			 "delete from inventory_receipts;",
			 "delete from inventory_sales_voucher_items;",
			 "delete from inventory_sales_voucher_jewellery_gem;",
			 "delete from inventory_sales_voucher_jewellery_metal;",
			 "delete from inventory_sales_vouchers;",
			 "delete from inventory_vendor_stock_voucher_items;",
			 "delete from inventory_vendor_stock_vouchers;",
			 "delete from ledger_entries;",
			 "delete from sales_invoice_item_gem_used;",
			 "delete from sales_invoice_item_metal_used;",
			 "delete from sales_invoice_items;",
			 "delete from sales_invoices;",
			 "delete from sales_order_advance_gems;",
			 "delete from sales_order_advance_metals;",
			 "delete from sales_order_cancellation_receipt_advance_gem_unused;",
			 "delete from sales_order_cancellation_receipt_advance_gem_used;",
			 "delete from sales_order_cancellation_receipt_advance_metal;",
			 "delete from sales_order_cancellation_receipts;",
			 "delete from sales_order_inventory_metal_items;",
			 "delete from sales_order_item_info;",
			 "delete from sales_order_item_info_estimated_gems;",
			 "delete from sales_order_item_info_estimated_metals;",
			 "delete from sales_order_items;",
			 "delete from sales_order_process_gem_issue;",
			 "delete from sales_order_process_gem_labours;",
			 "delete from sales_order_process_gem_return;",
			 "delete from sales_order_process_metal_item_used;",
			 "delete from sales_order_process_metal_used;",
			 "delete from sales_order_processes;",
			 "delete from sales_orders"

//					"ALTER TABLE sales_orders ADD COLUMN CANCEL_ORDER_REOPEN_STATUS tinyint(1) NOT NULL DEFAULT 0;"
//					 ,"UPDATE wastage_units  SET DIVISOR_FACTOR=100 WHERE WASTAGE_UNIT_ID=1"
//					 ,"UPDATE wastage_units  SET DIVISOR_FACTOR=96 WHERE WASTAGE_UNIT_ID=2"
				};
%>
		<div class="BOX">Note: No need to sent me the result</div><br>
		<div class="BOX">
		<table border="0" class="LIST_AREA" cellpadding="0" celspacing="1" width="950">
					<tr>
						<td width="400" class="LIST_HEADER">Command</td>
						<td width="500" class="LIST_HEADER">Command</td>
						<td width="50" class="LIST_HEADER">Status</td>
					</tr>
				    <% for (int i=0; i< commands.length; i++) {
					  			statusDescription =" ";
					  			status = true;
				    %>
					  <tr>
						  <% 	try {
										st1.execute(commands[i]);
							 		} catch (Exception e){
											status=false;
											statusDescription = e.getMessage()+e.toString();
							 		}
						  %>
							<td class="LIST_DATA"><%=commands[i]%></td>
							<td class="LIST_DATA"><%=statusDescription%></td>
							<td class="LIST_DATA"><%=status%></td>
						</tr>
				 	<%}%>
		</table>
		<br>
	</div>
<%	//Release database access objects
		st1.close();
		connection.close();			%>