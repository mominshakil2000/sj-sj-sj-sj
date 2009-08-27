
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Struts.Form.SalesOrderProcessForm;


import com.netxs.Zewar.DataSources.DBConnection;


public class SalesOrderProcessGemReturnCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		

		SalesOrderProcessForm actionForm = (SalesOrderProcessForm) form;
		actionForm.setHasFormInitialized('N');

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
				// Process Items

				String query = 	"INSERT INTO sales_order_process_gem_return ( TRANSACTION_DATE, SALES_ORDER_PROCESS_ID , ITEM_ID , WEIGHT , WEIGHT_UNIT_ID , QUANTITY, ITEM_STOCK_TYPE ) " 
											+ " VALUES ( " 
											+ "' "+DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date())
											+ "',"+request.getParameter("salesOrderProcessId")
											+ " ,0"
											+ " ,0.0"
											+ " ,0"
											+ " ,0 " 
											+ " ,1 )";
				
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
