
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Struts.Form.SalesOrderProcessForm;

import com.netxs.Zewar.DataSources.DBConnection;

public class SalesOrderProcessMetalItemUsedCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		
		SalesOrderProcessForm actionForm = (SalesOrderProcessForm) form;
		actionForm.setSalesOrderProcessId(request.getParameter("salesOrderProcessId"));	

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
				String query;

				
				float wastageRate = 0.0f;
				byte wastageUnitId = 1;
				byte itemId = 0;
				// Process Items
				 query =  "insert into sales_order_process_metal_item_used ( SALES_ORDER_PROCESS_ID, ITEM_ID_METAL) " 
						+ " VALUES ( "
						+      request.getParameter("salesOrderProcessId")
						+ ", "+itemId
						+ " )";
				 
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
		actionForm.setHasFormInitialized('N');
		return (mapping.findForward("SUCCESS"));
	}

}
