
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Default;

import com.netxs.Zewar.DataSources.DBConnection;


public class SalesOrderInvoiceDeleteAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		
		Connection connection;
		connection = (Connection) new DBConnection().getMyPooledConnection();
		connection.setAutoCommit(false);
		
		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
		Statement stmt2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
		
		try {
				
				stmt2.executeUpdate("DELETE FROM ledger_entries WHERE LEDGER_ENTRY_ID IN (SELECT LEDGER_ENTRY_ID FROM sales_invoices WHERE SALES_INVOICE_ID='"+request.getParameter("salesInvoiceId")+"')");
				stmt2.executeUpdate("DELETE FROM sales_invoices WHERE LEDGER_ENTRY_ID='"+request.getParameter("salesInvoiceId")+"'");
				stmt2.executeUpdate("UPDATE sales_orders SET SALES_INVOICE_ID=0, SALES_INVOICE_STATUS=0, SALES_ORDER_STATUS_ID="+Default.SALES_ORDER_PROCESS_STATUS_PROCESS+" WHERE SALES_ORDER_ID='"+request.getParameter("salesOrderId")+"'");
				stmt2.executeUpdate("DELETE FROM sales_invoice_items WHERE SALES_INVOICE_ID='"+request.getParameter("salesInvoiceId")+"'");
				stmt2.executeUpdate("DELETE FROM sales_invoice_item_metal_used WHERE SALES_INVOICE_ID='"+request.getParameter("salesInvoiceId")+"'");
				stmt2.executeUpdate("DELETE FROM inventory_gem_entries WHERE INVENTORY_GEM_ENTRY_ID IN (SELECT INVENTORY_GEM_ENTRY_ID FROM sales_invoice_item_gem_used WHERE SALES_INVOICE_ID='"+request.getParameter("salesInvoiceId")+"')");
				stmt2.executeUpdate("DELETE FROM sales_invoice_item_gem_used WHERE SALES_INVOICE_ID='"+request.getParameter("salesInvoiceId")+"'");
				
				
				connection.commit();
				stmt.close();
				connection.close();
			} catch(Exception e) {
				try {connection.rollback();}catch (SQLException sqle){}
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	 
			}
			
		return (mapping.findForward("SUCCESS"));
	}

}
