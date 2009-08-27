
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Struts.Form.SalesOrderForm;

public class SalesOrderDeleteAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		SalesOrderForm actionForm = (SalesOrderForm)form;
		int salesOrderId;
		
		try {
			salesOrderId = Integer.parseInt(request.getParameter("salesOrderId"));
		} catch(Exception e){
			salesOrderId=0;
		}
		
		try {
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);

				String query = 	"DELETE FROM sales_order_item_info_estimated_metals WHERE SALES_ORDER_ID="+salesOrderId; 
				stmt.executeUpdate(query);

				query =	"DELETE FROM sales_order_item_info_estimated_gems WHERE SALES_ORDER_ID="+salesOrderId; 
				stmt.executeUpdate(query);

				query = 	"DELETE FROM sales_order_advance_metals WHERE SALES_ORDER_ID="+salesOrderId; 
				stmt.executeUpdate(query);

				query = 	"DELETE FROM sales_order_advance_gems WHERE SALES_ORDER_ID="+salesOrderId; 
				stmt.executeUpdate(query);

				query = 	"DELETE FROM sales_order_items WHERE SALES_ORDER_ITEM_ID IN (SELECT SALES_ORDER_ITEM_ID FROm SALES_ORDER_ITEM_INFO WHERE SALES_ORDER_ID="+salesOrderId+")"; 
				stmt.executeUpdate(query);
				
				query = 	"DELETE FROM sales_order_item_info WHERE SALES_ORDER_ID="+salesOrderId; 
				stmt.executeUpdate(query);
				
				query = 	"DELETE FROM sales_orders WHERE SALES_ORDER_ID="+salesOrderId; 
				stmt.executeUpdate(query);
				actionForm.setSalesOrderId("");
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
