
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.netxs.Zewar.DataSources.DBConnection;

public class SalesOrderProcessDeleteAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		int salesOrderProcessId;
		
		try {
			salesOrderProcessId = Integer.parseInt(request.getParameter("salesOrderProcessId"));
		} catch(Exception e){
			salesOrderProcessId=0;
		}
		
		try {
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);

				stmt.executeUpdate("DELETE FROM sales_order_process_gem_issue  WHERE SALES_ORDER_PROCESS_ID="+salesOrderProcessId);
				stmt.executeUpdate("DELETE FROM sales_order_process_gem_return  WHERE SALES_ORDER_PROCESS_ID="+salesOrderProcessId);
				
				stmt.executeUpdate("DELETE FROM sales_order_process_gem_labours WHERE SALES_ORDER_PROCESS_ID="+salesOrderProcessId);

				
				stmt.executeUpdate("DELETE FROM inventory_metal_entries WHERE  INVENTORY_METAL_ENTRY_ID IN (SELECT INVENTORY_METAL_ENTRY_ID FROM sales_order_process_metal_used WHERE  SALES_ORDER_PROCESS_ID="+salesOrderProcessId+")");
				stmt.executeUpdate("DELETE FROM sales_order_process_metal_used WHERE  SALES_ORDER_PROCESS_METAL_USED_ID="+salesOrderProcessId);
				
				stmt.executeUpdate("DELETE FROM inventory_metal_item_entries WHERE  INVENTORY_METAL_ITEM_ENTRY_ID IN (SELECT INVENTORY_METAL_ITEM_ENTRY_ID FROM sales_order_process_metal_item_used WHERE SALES_ORDER_PROCESS_ID="+salesOrderProcessId+")");
				stmt.executeUpdate("DELETE FROM sales_order_process_metal_item_used  WHERE SALES_ORDER_PROCESS_ID="+salesOrderProcessId);
				
				stmt.executeUpdate("DELETE FROM ledger_entries WHERE  LEDGER_ENTRY_ID IN (SELECT LABOUR_LEDGER_ENTRY_ID FROM sales_order_processes WHERE  SALES_ORDER_PROCESS_ID="+salesOrderProcessId+")"); 
				
				stmt.executeUpdate("DELETE FROM sales_order_processes WHERE SALES_ORDER_PROCESS_ID="+salesOrderProcessId);
				stmt.close();
				connection.close();
			} catch(Exception e) {
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	 
			}
		return (mapping.findForward("SUCCESS"));
	}

}
