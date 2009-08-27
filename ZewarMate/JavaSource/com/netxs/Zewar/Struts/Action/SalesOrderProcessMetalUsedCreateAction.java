
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Default;
import com.netxs.Zewar.Struts.Form.SalesOrderProcessForm;

import com.netxs.Zewar.DataSources.DBConnection;

public class SalesOrderProcessMetalUsedCreateAction extends Action {
	
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
				
				stmt.execute("INSERT INTO inventory_metal_entries (entry_date, inventory_account_id, item_id, weight, in_out_status, voucher_prefix, voucher_postfix )	VALUES(curDate(),0,0,0.00,0,'','')");
				rs = stmt.getGeneratedKeys();
				String inventoryMetalEntryId = rs.next()?rs.getString(1): "null";
				
				rs = stmt.executeQuery("SELECT  VI.AGREED_WASTAGE, VI.ITEM_ID FROM sales_order_processes OP INNER JOIN vendors VN ON VN.LEDGER_ACCOUNT_ID=OP.VENDOR_LEDGER_ACCOUNT_ID INNER JOIN vendor_items_relation VI ON VI.VENDOR_ID=VN.VENDOR_ID WHERE OP.SALES_ORDER_PROCESS_ID='"+request.getParameter("salesOrderProcessId")+"' LIMIT 1");
				float wastageRate = 0.0f;
				byte wastageUnitId = 1;
				byte itemId = 0;
				if (rs.next()) {
					wastageRate = rs.getFloat("AGREED_WASTAGE");
					itemId = rs.getByte("ITEM_ID");
					wastageUnitId = 2;
				} 
				// Process Items
				String query =  "insert into sales_order_process_metal_used (SALES_ORDER_PROCESS_ID,ITEM_ID,WEIGHT,WEIGHT_UNIT_ID,WASTAGE_RATE,WASTAGE_UNIT_ID,NET_WEIGHT,INVENTORY_METAL_ENTRY_ID) " 
						+ " VALUES ( "
						+      request.getParameter("salesOrderProcessId")
						+ ", "+itemId
						+ ", 0.0"
						+ ", 1"
						+ ", "+wastageRate
						+ ", "+wastageUnitId
						+ ", "+inventoryMetalEntryId
						+ ", 0.0 )";
				 
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
