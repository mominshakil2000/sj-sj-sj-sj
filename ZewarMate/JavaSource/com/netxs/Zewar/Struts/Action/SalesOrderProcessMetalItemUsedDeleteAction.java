
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Struts.Form.SalesOrderProcessForm;


import com.netxs.Zewar.DataSources.DBConnection;


public class SalesOrderProcessMetalItemUsedDeleteAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		SalesOrderProcessForm actionForm = (SalesOrderProcessForm) form;

		actionForm.setSalesOrderProcessId(request.getParameter("salesOrderProcessId"));
		actionForm.setHasFormInitialized('N');

		try {
			Integer.parseInt(actionForm.getSalesOrderProcessId());
		}catch(Exception e){
			actionForm.setSalesOrderId("0");
		}
		try {
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				ResultSet rs;
				
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				// Process Items
				String query = 	"DELETE FROM sales_order_process_metal_item_used WHERE  SALES_ORDER_PROCESS_METAL_ITEM_USED_ID="+request.getParameter("salesOrderProcessMetalItemUsedId") ;
				stmt.executeUpdate(query);
				stmt.close();
				connection.close();
			} catch(Exception e) {
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				actionForm.setHasFormInitialized('N');
				return (mapping.findForward("FAIL"));	 
			}
		return (mapping.findForward("SUCCESS"));
	}

}
