
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Default;
import com.netxs.Zewar.Struts.Form.SalesOrderProcessForm;

import com.netxs.Zewar.DataSources.DBConnection;

public class SalesOrderProcessGemLabourCreateAction extends Action {
	
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
				// Process Items

				String query;

				// Initialized Labour Charges By Gems -> Estimated Quantity 
				// Get Sales Order Body Making and Stone Setting Rate Types
				rs = stmt.executeQuery("SELECT OII.BODY_MAKING_RATE_TYPE_ID,OII.STONE_SETTING_RATE_TYPE_ID FROM  sales_order_item_info OII INNER JOIN sales_order_processes OP ON 	OP.SALES_ORDER_ID=OII.SALES_ORDER_ID AND OP.SALES_ORDER_ITEM_ID=OII.SALES_ORDER_ITEM_ID WHERE OP.SALES_ORDER_PROCESS_ID='"+request.getParameter("salesOrderProcessItemId")+"'");
				byte bodyMakingRateTypeId = 1;
				byte stoneSettingRateTypeId = 1;
				if (rs.next()) {
					bodyMakingRateTypeId = rs.getByte("BODY_MAKING_RATE_TYPE_ID");
					stoneSettingRateTypeId = rs.getByte("STONE_SETTING_RATE_TYPE_ID");
				}
				
				// get Vendor Body Making and Stone Setting Rate
				rs = stmt.executeQuery("SELECT VN.BODY_MAKING_RATE_SIMPLE, VN.BODY_MAKING_RATE_MIX, VN.STONE_SETTING_RATE_SIMPLE, VN.STONE_SETTING_RATE_DIFFICULT, OP.SALES_ORDER_PROCESS_TYPE_ID FROM vendors VN INNER JOIN  sales_order_processes OP ON OP.VENDOR_LEDGER_ACCOUNT_ID=VN.LEDGER_ACCOUNT_ID WHERE OP.SALES_ORDER_PROCESS_ID='"+request.getParameter("salesOrderProcessId")+"'");
				float settingRate = 0.0f;
				if (rs.next()) {
					if (rs.getByte("SALES_ORDER_PROCESS_TYPE_ID")==Default.VENDOR_PROCESS_BODY_MAKING)   
						settingRate = bodyMakingRateTypeId == 1 ? rs.getFloat("BODY_MAKING_RATE_SIMPLE") : rs.getFloat("BODY_MAKING_RATE_MIX");
					 else if(rs.getByte("SALES_ORDER_PROCESS_TYPE_ID")==Default.VENDOR_PROCESS_STONE_SETTING) 
						settingRate = stoneSettingRateTypeId == 1 ? rs.getFloat("STONE_SETTING_RATE_SIMPLE") : rs.getFloat("STONE_SETTING_RATE_DIFFICULT");
				} 
				query =	  "INSERT INTO sales_order_process_gem_labours (  SALES_ORDER_PROCESS_ID, SETTING_RATE , ACTUAL_QUANTITY , ACTUAL_TOTAL_LABOUR )  " 
						+ " VALUES ( " 
						       + request.getParameter("salesOrderProcessId")
						+ ", " + settingRate
						+ ", 0"
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
