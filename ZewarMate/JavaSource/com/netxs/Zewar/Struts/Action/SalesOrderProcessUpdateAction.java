
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Default;
import com.netxs.Zewar.Struts.Form.SalesOrderProcessForm;
import com.netxs.Zewar.Struts.Form.SalesOrderProcessGemLabourForm;
import com.netxs.Zewar.Struts.Form.SalesOrderProcessGemIssueReturnForm;
import com.netxs.Zewar.Struts.Form.SalesOrderProcessMetalItemUsedForm;
import com.netxs.Zewar.Struts.Form.SalesOrderProcessMetalUsedForm;
import com.netxs.Zewar.DataSources.DBConnection;

public class SalesOrderProcessUpdateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		SalesOrderProcessForm actionForm = (SalesOrderProcessForm) form;
		SalesOrderProcessGemLabourForm salesOrderProcessGemLabour;
		SalesOrderProcessGemIssueReturnForm salesOrderProcessGemIssueReturn;
		SalesOrderProcessMetalUsedForm salesOrderProcessMetalUsed;
		SalesOrderProcessMetalItemUsedForm salesOrderProcessMetalItemUsed;
		ResultSet rs;
		ResultSet rs2;
		Statement stmt;
		Statement stmt2;
		Connection connection;
		connection = (Connection) new DBConnection().getMyPooledConnection();
		connection.setAutoCommit(false);
		//check for cart is valid if not then creat new one
		if(actionForm.getHasFormInitialized()!= 'Y'){ //Initialize Form 
			try {
				stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				stmt2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				//Process Detail
				String query = 	"SELECT *  FROM sales_order_processes WHERE sales_order_process_id="+actionForm.getSalesOrderProcessId();
				rs = stmt.executeQuery(query);

				if (rs.next()){
						actionForm.setInsertableSubSectionRow('0');
						actionForm.setSalesOrderId(rs.getString("SALES_ORDER_ID"));
						actionForm.setVendorLedgerAccountId(rs.getString("VENDOR_LEDGER_ACCOUNT_ID"));
						actionForm.setSalesOrderProcessTypeId(rs.getString("SALES_ORDER_PROCESS_TYPE_ID"));
						actionForm.setSalesOrderProcessStatusId(rs.getString("SALES_ORDER_PROCESS_STATUS_ID"));
						actionForm.setProcessStartDate(rs.getString("PROCESS_START_DATE"));
						actionForm.setProcessEndDate(rs.getString("PROCESS_END_DATE"));
						actionForm.setHasFormInitialized('Y');
						actionForm.setSalesOrderItemId(rs.getString("SALES_ORDER_ITEM_ID"));
						actionForm.setLumsumLabour(rs.getString("LUMSUM_LABOUR"));
						actionForm.setWastageRate(rs.getString("WASTAGE_RATE"));
						actionForm.setVendorDeliveryDate(rs.getString("VENDOR_DELIVERY_DATE"));
						actionForm.setActualDeliveryDate(rs.getString("ACTUAL_DELIVERY_DATE"));
						actionForm.setBodyMetalItemId(rs.getString("BODY_METAL_ITEM_ID"));
						actionForm.setBodyMetalWeightUnitId(rs.getString("BODY_METAL_WEIGHT_UNIT_ID"));
						actionForm.setIssueBodyWeight(rs.getString("ISSUE_BODY_WEIGHT"));
						actionForm.setReturnBodyWeight(rs.getString("RETURN_BODY_WEIGHT"));
						actionForm.setReturnBodyPieces(rs.getString("RETURN_BODY_PIECES"));
						actionForm.setComments(rs.getString("COMMENTS"));
						actionForm.setLabourLedgerEntryId(rs.getString("LABOUR_LEDGER_ENTRY_ID"));

						query = "SELECT *  FROM sales_order_items WHERE sales_order_item_id="+actionForm.getSalesOrderItemId();
						rs = stmt.executeQuery(query);
						if (rs.next()){
							actionForm.setJewelleryItemId(rs.getString("JEWELLERY_ITEM_ID"));
						}
						
				}else{
					Exception e = new Exception("Unknow Sales Order Process");
					throw e;
				}
					
				// Sales Order Process Item Gems Labour
				query = "SELECT * FROM sales_order_process_gem_labours WHERE SALES_ORDER_PROCESS_ID="+actionForm.getSalesOrderProcessId();
				rs2 = stmt2.executeQuery(query);
				actionForm.getGemLabourList().clear();
				while(rs2.next()){
					salesOrderProcessGemLabour = new SalesOrderProcessGemLabourForm();
					salesOrderProcessGemLabour.setSalesOrderProcessGemLabourId(rs2.getString("SALES_ORDER_PROCESS_GEM_LABOUR_ID"));
					salesOrderProcessGemLabour.setSalesOrderProcessId(rs2.getString("SALES_ORDER_PROCESS_ID"));
					salesOrderProcessGemLabour.setSettingRate(rs2.getString("SETTING_RATE"));
					salesOrderProcessGemLabour.setEstimatedQuantity(rs2.getString("ESTIMATED_QUANTITY"));
					salesOrderProcessGemLabour.setEstimatedTotalLabour(rs2.getString("ESTIMATED_TOTAL_LABOUR"));
					salesOrderProcessGemLabour.setActualQuantity(rs2.getString("ACTUAL_QUANTITY"));
					salesOrderProcessGemLabour.setActualTotalLabour(rs2.getString("ACTUAL_TOTAL_LABOUR"));
					actionForm.setGemLabour(salesOrderProcessGemLabour);
				} 

				// Sales Order Process Item Gems Issue 
				query = "SELECT * FROM sales_order_process_gem_issue  WHERE SALES_ORDER_PROCESS_ID="+actionForm.getSalesOrderProcessId();
				rs2 = stmt2.executeQuery(query);
				actionForm.getVendorGemIssueList().clear();
				while(rs2.next()){
					salesOrderProcessGemIssueReturn = new SalesOrderProcessGemIssueReturnForm();
					salesOrderProcessGemIssueReturn.setSalesOrderProcessGemIssueReturnId(rs2.getString("SALES_ORDER_PROCESS_GEM_ISSUE_ID"));
					salesOrderProcessGemIssueReturn.setSalesOrderProcessId(rs2.getString("SALES_ORDER_PROCESS_ID"));
					salesOrderProcessGemIssueReturn.setTransactionDate(rs2.getString("TRANSACTION_DATE"));
					salesOrderProcessGemIssueReturn.setItemId(rs2.getString("ITEM_ID"));
					salesOrderProcessGemIssueReturn.setQuantity(rs2.getString("QUANTITY"));
					salesOrderProcessGemIssueReturn.setWeight(rs2.getString("WEIGHT"));
					salesOrderProcessGemIssueReturn.setWeightUnitId(rs2.getString("WEIGHT_UNIT_ID"));
					salesOrderProcessGemIssueReturn.setItemStockType(rs2.getString("ITEM_STOCK_TYPE"));
					actionForm.setVendorGemIssue(salesOrderProcessGemIssueReturn);
				}
				
				//Sales Order Process Item Gems Return
				query = "SELECT * FROM sales_order_process_gem_return  WHERE SALES_ORDER_PROCESS_ID="+actionForm.getSalesOrderProcessId();
				rs2 = stmt2.executeQuery(query);
				actionForm.getVendorGemReturnList().clear();
				while(rs2.next()){
					salesOrderProcessGemIssueReturn = new SalesOrderProcessGemIssueReturnForm();
					salesOrderProcessGemIssueReturn.setSalesOrderProcessGemIssueReturnId(rs2.getString("SALES_ORDER_PROCESS_GEM_RETURN_ID"));
					salesOrderProcessGemIssueReturn.setSalesOrderProcessId(rs2.getString("SALES_ORDER_PROCESS_ID"));
					salesOrderProcessGemIssueReturn.setTransactionDate(rs2.getString("TRANSACTION_DATE"));
					salesOrderProcessGemIssueReturn.setItemId(rs2.getString("ITEM_ID"));
					salesOrderProcessGemIssueReturn.setQuantity(rs2.getString("QUANTITY"));
					salesOrderProcessGemIssueReturn.setWeight(rs2.getString("WEIGHT"));
					salesOrderProcessGemIssueReturn.setWeightUnitId(rs2.getString("WEIGHT_UNIT_ID"));
					salesOrderProcessGemIssueReturn.setItemStockType(rs2.getString("ITEM_STOCK_TYPE"));
					actionForm.setVendorGemReturn(salesOrderProcessGemIssueReturn);
				}


				// Sales Order Process Item Metal Used
				query = "SELECT * FROM sales_order_process_metal_used  WHERE SALES_ORDER_PROCESS_ID="+actionForm.getSalesOrderProcessId();
				rs2 = stmt2.executeQuery(query);
				actionForm.getMetalUsedList().clear();
				while(rs2.next()){
					salesOrderProcessMetalUsed = new SalesOrderProcessMetalUsedForm();
					salesOrderProcessMetalUsed.setSalesOrderProcessMetalUsedId(rs2.getString("SALES_ORDER_PROCESS_METAL_USED_ID"));
					salesOrderProcessMetalUsed.setItemId(rs2.getString("ITEM_ID"));
					salesOrderProcessMetalUsed.setWeight(rs2.getString("WEIGHT"));
					salesOrderProcessMetalUsed.setWeightUnitId(rs2.getString("WEIGHT_UNIT_ID"));
					salesOrderProcessMetalUsed.setWastageRate(rs2.getString("WASTAGE_RATE"));
					salesOrderProcessMetalUsed.setWastageQuantity(rs2.getString("WASTAGE_QUANTITY"));
					salesOrderProcessMetalUsed.setWastageQuantity(rs2.getString("WASTAGE_QUANTITY"));
					salesOrderProcessMetalUsed.setWastageUnitId(rs2.getString("WASTAGE_UNIT_ID"));
					salesOrderProcessMetalUsed.setNetWeight(rs2.getString("NET_WEIGHT"));
					salesOrderProcessMetalUsed.setInventoryMetalEntryIdOut(rs2.getString("INVENTORY_METAL_ENTRY_ID"));
					actionForm.setMetalUsed(salesOrderProcessMetalUsed);
				}


				// Sales Order Process Item Metal Part Charge Customer
				query = "SELECT * FROM sales_order_process_metal_item_used  WHERE SALES_ORDER_PROCESS_ID="+actionForm.getSalesOrderProcessId();
				rs2 = stmt2.executeQuery(query);
				actionForm.getMetalItemUsedList().clear();
				while(rs2.next()){
					salesOrderProcessMetalItemUsed = new SalesOrderProcessMetalItemUsedForm();
					salesOrderProcessMetalItemUsed.setSalesOrderProcessMetalItemUsedId(rs2.getString("SALES_ORDER_PROCESS_METAL_ITEM_USED_ID"));
					salesOrderProcessMetalItemUsed.setItemIdMetal(rs2.getString("ITEM_ID_METAL"));
					salesOrderProcessMetalItemUsed.setItemIdJewellery(rs2.getString("ITEM_ID_JEWELLERY"));
					salesOrderProcessMetalItemUsed.setIssueItemWeight(rs2.getString("ISSUE_ITEM_WEIGHT"));
					salesOrderProcessMetalItemUsed.setIssueItemNetWeight(rs2.getString("ISSUE_ITEM_NET_WEIGHT"));
					salesOrderProcessMetalItemUsed.setWeightUnitId(rs2.getString("WEIGHT_UNIT_ID"));
					salesOrderProcessMetalItemUsed.setReturnWasteQuantity(rs2.getString("RETURN_WASTE_QUANTITY"));
					salesOrderProcessMetalItemUsed.setIssueItemWastageRate(rs2.getString("ISSUE_ITEM_WASTAGE_RATE"));
					salesOrderProcessMetalItemUsed.setIssueItemWastageUnitId(rs2.getString("ISSUE_ITEM_WASTAGE_UNIT_ID"));
					salesOrderProcessMetalItemUsed.setInventoryMetalItemEntryIdOut(rs2.getString("INVENTORY_METAL_ITEM_ENTRY_ID"));
					actionForm.setMetalItemUsed(salesOrderProcessMetalItemUsed);
				}
				stmt.close();
				stmt2.close();
				connection.close();
			} catch(Exception e) {
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	 
			}
			actionForm.setHasFormInitialized('Y');
			return (mapping.findForward("FAIL"));

		} else if(actionForm.getInsertableSubSectionRow()!= '0') {

			connection = (Connection) new DBConnection().getMyPooledConnection();
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);

			int index ;
			try {
				index = Integer.parseInt(actionForm.getSubSectionRowId());
			} catch(Exception e){
				index = -1;
			}

			switch (actionForm.getInsertableSubSectionRow()){
				case 'A': 	salesOrderProcessGemIssueReturn = new SalesOrderProcessGemIssueReturnForm();
							salesOrderProcessGemIssueReturn.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
							salesOrderProcessGemIssueReturn.setWeightUnitId(Integer.toString(Default.WEIGHT_IN_CARAT));
							salesOrderProcessGemIssueReturn.setWeight("0");
							salesOrderProcessGemIssueReturn.setInsertable(true);
							actionForm.setVendorGemIssue(salesOrderProcessGemIssueReturn);
							break;
				case 'B': 	salesOrderProcessGemIssueReturn = new SalesOrderProcessGemIssueReturnForm();
							salesOrderProcessGemIssueReturn.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
							salesOrderProcessGemIssueReturn.setWeightUnitId(Integer.toString(Default.WEIGHT_IN_CARAT));
							salesOrderProcessGemIssueReturn.setWeight("0");
							salesOrderProcessGemIssueReturn.setInsertable(true);
							actionForm.setVendorGemReturn(salesOrderProcessGemIssueReturn);
							break;
				case 'C':	// Initialized Labour Charges By Gems -> Estimated Quantity 
							// Get Sales Order Body Making and Stone Setting Rate Types
							rs = stmt.executeQuery("SELECT OII.BODY_MAKING_RATE_TYPE_ID,OII.STONE_SETTING_RATE_TYPE_ID FROM  sales_order_item_info OII INNER JOIN sales_order_processes OP ON 	OP.SALES_ORDER_ID=OII.SALES_ORDER_ID AND OP.SALES_ORDER_ITEM_ID=OII.SALES_ORDER_ITEM_ID WHERE OP.SALES_ORDER_PROCESS_ID='"+request.getParameter("salesOrderProcessItemId")+"'");
							salesOrderProcessGemLabour = new SalesOrderProcessGemLabourForm();
							byte bodyMakingRateTypeId = 1;
							byte stoneSettingRateTypeId = 1;
							if (rs.next()) {
								bodyMakingRateTypeId = rs.getByte("BODY_MAKING_RATE_TYPE_ID");
								stoneSettingRateTypeId = rs.getByte("STONE_SETTING_RATE_TYPE_ID");
							}
							
							// get Vendor Body Making and Stone Setting Rate
							rs = stmt.executeQuery("SELECT VN.BODY_MAKING_RATE_SIMPLE, VN.BODY_MAKING_RATE_MIX, VN.STONE_SETTING_RATE_SIMPLE, VN.STONE_SETTING_RATE_DIFFICULT, OP.SALES_ORDER_PROCESS_TYPE_ID FROM vendors VN INNER JOIN  sales_order_processes OP ON OP.VENDOR_LEDGER_ACCOUNT_ID=VN.LEDGER_ACCOUNT_ID WHERE OP.SALES_ORDER_PROCESS_ID='"+request.getParameter("salesOrderProcessId")+"'");
							if (rs.next()) {
								if (rs.getByte("SALES_ORDER_PROCESS_TYPE_ID")==Default.VENDOR_PROCESS_BODY_MAKING)   
									salesOrderProcessGemLabour.setSettingRate(bodyMakingRateTypeId == 1 ? rs.getString("BODY_MAKING_RATE_SIMPLE") : rs.getString("BODY_MAKING_RATE_MIX"));
								 else if(rs.getByte("SALES_ORDER_PROCESS_TYPE_ID")==Default.VENDOR_PROCESS_STONE_SETTING) 
									salesOrderProcessGemLabour.setSettingRate(stoneSettingRateTypeId == 1 ? rs.getString("STONE_SETTING_RATE_SIMPLE") : rs.getString("STONE_SETTING_RATE_DIFFICULT"));
							} 
							salesOrderProcessGemLabour.setInsertable(true);
							actionForm.setGemLabour(salesOrderProcessGemLabour);
							break;
				case 'D': 	salesOrderProcessMetalUsed = new SalesOrderProcessMetalUsedForm();
							rs = stmt.executeQuery("SELECT  VI.AGREED_WASTAGE, VI.ITEM_ID FROM sales_order_processes OP INNER JOIN vendors VN ON VN.LEDGER_ACCOUNT_ID=OP.VENDOR_LEDGER_ACCOUNT_ID INNER JOIN vendor_items_relation VI ON VI.VENDOR_ID=VN.VENDOR_ID WHERE OP.SALES_ORDER_PROCESS_ID='"+request.getParameter("salesOrderProcessId")+"' LIMIT 1");
							if (rs.next()) {
								salesOrderProcessMetalUsed.setWastageRate(rs.getString("AGREED_WASTAGE"));
								salesOrderProcessMetalUsed.setItemId(rs.getString("ITEM_ID"));
								salesOrderProcessMetalUsed.setWastageUnitId("2");
							}
							salesOrderProcessMetalUsed.setInsertable(true);
							actionForm.setMetalUsed(salesOrderProcessMetalUsed);
							break;	
				case 'E': 	salesOrderProcessMetalItemUsed = new SalesOrderProcessMetalItemUsedForm();
							salesOrderProcessMetalItemUsed.setIssueItemWastageUnitId("1");
							salesOrderProcessMetalItemUsed.setIssueItemWastageRate("0.0");
							salesOrderProcessMetalItemUsed.setInsertable(true);
							actionForm.setMetalItemUsed(salesOrderProcessMetalItemUsed);
							break;
				
				case 'F':	if (index != -1) {
								salesOrderProcessGemIssueReturn = (SalesOrderProcessGemIssueReturnForm) actionForm.getVendorGemIssueList().get(index);	
								actionForm.setDeleteVendorGemIssueIds(actionForm.getDeleteVendorGemIssueIds()+","+salesOrderProcessGemIssueReturn.getSalesOrderProcessGemIssueReturnId());
								actionForm.getVendorGemIssueList().remove(index);
							}
							break;
				

				case 'G': 	if (index != -1) {
								salesOrderProcessGemIssueReturn = (SalesOrderProcessGemIssueReturnForm) actionForm.getVendorGemReturnList().get(index);	
								actionForm.setDeleteVendorGemReturnIds(actionForm.getDeleteVendorGemIssueIds()+","+salesOrderProcessGemIssueReturn.getSalesOrderProcessGemIssueReturnId());
								actionForm.getVendorGemReturnList().remove(index);
							}
							break;

				case 'H': 	if (index != -1) {
								salesOrderProcessGemLabour = (SalesOrderProcessGemLabourForm) actionForm.getGemLabourList().get(index);
								actionForm.setDeleteGemLabourIds(actionForm.getDeleteGemLabourIds()+","+salesOrderProcessGemLabour.getSalesOrderProcessGemLabourId());
								actionForm.getGemLabourList().remove(index);
							}
							break;

				case 'I': 	if (index != -1) {
								salesOrderProcessMetalUsed = (SalesOrderProcessMetalUsedForm) actionForm.getMetalUsedList().get(index);
								actionForm.setDeleteMetalUsedIds(actionForm.getDeleteMetalUsedIds()+","+salesOrderProcessMetalUsed.getSalesOrderProcessMetalUsedId());
								actionForm.getMetalUsedList().remove(index);
							}
							break;

				case 'J': 	if (index != -1) {
								salesOrderProcessMetalItemUsed = (SalesOrderProcessMetalItemUsedForm) actionForm.getMetalItemUsedList().get(index);
								actionForm.setDeleteMetalItemUsedIds(actionForm.getDeleteMetalItemUsedIds()+","+salesOrderProcessMetalItemUsed.getSalesOrderProcessMetalItemUsedId());
								actionForm.getMetalItemUsedList().remove(index);
							}
							break;
					


			}
			actionForm.setInsertableSubSectionRow('0');
			actionForm.setSubSectionRowId("-1");
			return (mapping.findForward("FAIL"));	
		} else { // Update Cart
			//String query = new String();
			float vendorTotalLabour = 0.0f;
			String orderTrackingId;
			try {

				stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);

				PreparedStatement stmtUpdateInventoryItemEntry = connection.prepareStatement("UPDATE inventory_metal_entries SET entry_date=?, inventory_account_id=?, item_id=?, weight=?, in_out_status=?, voucher_prefix=?, voucher_postfix=?   WHERE inventory_metal_entry_id=?");
				
				//get Order Tracking Id
				rs = stmt.executeQuery("SELECT * FROM sales_orders WHERE sales_order_id="+actionForm.getSalesOrderId());
				orderTrackingId = (rs.next()?rs.getString("SALES_ORDER_TRACKING_ID"):"0");
				
				vendorTotalLabour += Float.parseFloat(actionForm.getLumsumLabour());
				
				if(actionForm.isInsertable()){
					stmt.executeUpdate(
						 " INSERT INTO  sales_order_processes  " 
						+" ( VENDOR_LEDGER_ACCOUNT_ID, PROCESS_START_DATE, PROCESS_END_DATE, SALES_OPRDER_PROCESS_STATUS_ID, LUMSUM_LABOUR, WASTAGE_RATE, WASTAGE_RATE_UNIT_ID, VENDOR_DELIVERY_DATE, ACTUAL_DELIVERY_DATE, ISSUE_BODY_WEIGHT, BODY_METAL_ITEM_ID, BODY_METAL_WEIGHT_UNIT_ID, RETURN_BODY_WEIGHT, RETURN_BODY_PIECES, COMMENTS)" 
						+" VALUES ( "
						+	  actionForm.getVendorLedgerAccountId() 
						+" ,'"+actionForm.getProcessStartDate()
						+"','"+actionForm.getProcessEndDate()
						+"', "+(actionForm.getSalesOrderProcessStatusId().equals("2")? "2" : "sales_order_process_status_id")
						+" , "+actionForm.getLumsumLabour()
						+" , "+actionForm.getWastageRate()
						+" , "+actionForm.getWastageRateUnitId()
						+" ,'"+actionForm.getVendorDeliveryDate()
						+"','"+actionForm.getActualDeliveryDate()
						+"','"+actionForm.getIssueBodyWeight()
						+"','"+actionForm.getBodyMetalItemId()
						+"','"+actionForm.getBodyMetalWeightUnitId()
						+"','"+actionForm.getReturnBodyWeight()
						+"','"+actionForm.getReturnBodyPieces()
						+"','"+actionForm.getComments() 
						+"' )"
					);
					rs = stmt.getGeneratedKeys();
					actionForm.setSalesOrderProcessId((rs.next() ? rs.getString(1):"0"));
				}else{
					stmt.executeUpdate(
							 " UPDATE sales_order_processes  " 
							+" SET "
							+"  VENDOR_LEDGER_ACCOUNT_ID="+actionForm.getVendorLedgerAccountId() 
							+", process_start_date='"+actionForm.getProcessStartDate()+"'"
							+", process_end_date='"+actionForm.getProcessEndDate()+"'"
							+", sales_order_process_status_id="+(actionForm.getSalesOrderProcessStatusId().equals("2")? "2" : "sales_order_process_status_id")
							+", LUMSUM_LABOUR="+actionForm.getLumsumLabour()
							+", WASTAGE_RATE="+actionForm.getWastageRate()
							+", WASTAGE_RATE_UNIT_ID="+actionForm.getWastageRateUnitId()
							+", VENDOR_DELIVERY_DATE='"+actionForm.getVendorDeliveryDate()+"'"
							+", ACTUAL_DELIVERY_DATE='"+actionForm.getActualDeliveryDate()+"'"
							+", ISSUE_BODY_WEIGHT='"+actionForm.getIssueBodyWeight()+"'"
							+", BODY_METAL_ITEM_ID='"+actionForm.getBodyMetalItemId()+"'"
							+", BODY_METAL_WEIGHT_UNIT_ID='"+actionForm.getBodyMetalWeightUnitId()+"'"
							+", RETURN_BODY_WEIGHT='"+actionForm.getReturnBodyWeight()+"'"
							+", RETURN_BODY_PIECES='"+actionForm.getReturnBodyPieces()+"'"
							+", COMMENTS='"+actionForm.getComments()+"'"
							+" WHERE sales_order_process_id="+actionForm.getSalesOrderProcessId()
					);					
				}
				

				PreparedStatement stmtInsertGemIssue = connection.prepareStatement("INSERT INTO sales_order_process_gem_issue (SALES_ORDER_PROCESS_ID, TRANSACTION_DATE, ITEM_ID, WEIGHT, WEIGHT_UNIT_ID, QUANTITY, ITEM_STOCK_TYPE) VALUES (?,?,?,?,?,?,?)");
				PreparedStatement stmtUpdateGemIssue = connection.prepareStatement("UPDATE sales_order_process_gem_issue SET TRANSACTION_DATE=?, ITEM_ID=?, WEIGHT=?, WEIGHT_UNIT_ID=?, QUANTITY=?, ITEM_STOCK_TYPE=? WHERE SALES_ORDER_PROCESS_GEM_ISSUE_ID=?");
				
				PreparedStatement stmtInsertGemReturn = connection.prepareStatement("INSERT INTO sales_order_process_gem_return (SALES_ORDER_PROCESS_ID, TRANSACTION_DATE, ITEM_ID, WEIGHT, WEIGHT_UNIT_ID, QUANTITY, ITEM_STOCK_TYPE) VALUES (?,?,?,?,?,?,?)");
				PreparedStatement stmtUpdateGemReturn = connection.prepareStatement("UPDATE sales_order_process_gem_return SET TRANSACTION_DATE=?, ITEM_ID=?, WEIGHT=?, WEIGHT_UNIT_ID=?, QUANTITY=?, ITEM_STOCK_TYPE=? WHERE SALES_ORDER_PROCESS_GEM_RETURN_ID=?");
				
				PreparedStatement stmtInsertGemLabour = connection.prepareStatement("INSERT INTO sales_order_process_gem_labours (SALES_ORDER_PROCESS_ID, SETTING_RATE , ESTIMATED_TOTAL_LABOUR, ESTIMATED_QUANTITY, ACTUAL_QUANTITY, ACTUAL_TOTAL_LABOUR) VALUES (?,?,?,?,?,?) ");
				PreparedStatement stmtUpdateGemLabour = connection.prepareStatement("UPDATE sales_order_process_gem_labours SET SALES_ORDER_PROCESS_ID=?, SETTING_RATE=?, ESTIMATED_TOTAL_LABOUR=?, ESTIMATED_QUANTITY=?, ACTUAL_QUANTITY=? , ACTUAL_TOTAL_LABOUR=? WHERE SALES_ORDER_PROCESS_GEM_LABOUR_ID=?");
				
				PreparedStatement stmtInsertMetalUsed = connection.prepareStatement("INSERT INTO sales_order_process_metal_used (SALES_ORDER_PROCESS_ID, ITEM_ID, WEIGHT, WEIGHT_UNIT_ID, WASTAGE_RATE, WASTAGE_UNIT_ID, NET_WEIGHT, WASTAGE_QUANTITY, INVENTORY_METAL_ENTRY_ID) VALUES (?,?,?,?,?,?,?,?,?)");
				PreparedStatement stmtUpdateMetalUsed = connection.prepareStatement("UPDATE sales_order_process_metal_used SET SALES_ORDER_PROCESS_ID=?, ITEM_ID=?, WEIGHT=?, WEIGHT_UNIT_ID=?, WASTAGE_RATE=?, WASTAGE_UNIT_ID=?, NET_WEIGHT=?, WASTAGE_QUANTITY=?, INVENTORY_METAL_ENTRY_ID=? WHERE SALES_ORDER_PROCESS_METAL_USED_ID=?");
				
				PreparedStatement stmtInsertInventoryMetalEntry = connection.prepareStatement("INSERT INTO inventory_metal_entries (ENTRY_DATE, INVENTORY_ACCOUNT_ID, ITEM_ID, WEIGHT, IN_OUT_STATUS, VOUCHER_PREFIX, VOUCHER_POSTFIX )	VALUES(?,?,?,?,?,?,?)");
				PreparedStatement stmtUpdateInventoryMetalEntry = connection.prepareStatement("UPDATE inventory_metal_entries SET ENTRY_DATE=?, INVENTORY_ACCOUNT_ID=?, ITEM_ID=?, WEIGHT=?, IN_OUT_STATUS=?, VOUCHER_PREFIX=?, VOUCHER_POSTFIX=? WHERE INVENTORY_METAL_ENTRY_ID=?");
				
				PreparedStatement stmtInsertMetalItemUsed = connection.prepareStatement("INSERT INTO sales_order_process_metal_item_used (SALES_ORDER_PROCESS_ID, ITEM_ID_METAL, ITEM_ID_JEWELLERY, ISSUE_ITEM_WEIGHT, ISSUE_ITEM_NET_WEIGHT, WEIGHT_UNIT_ID, RETURN_WASTE_QUANTITY, ISSUE_ITEM_WASTAGE_RATE, ISSUE_ITEM_WASTAGE_UNIT_ID, INVENTORY_METAL_ITEM_ENTRY_ID) VALUES (?,?,?,?,?,?,?,?,?,?)");
				PreparedStatement stmtUpdateMetalItemUsed = connection.prepareStatement("UPDATE sales_order_process_metal_item_used SET SALES_ORDER_PROCESS_ID=?, ITEM_ID_METAL=?, ITEM_ID_JEWELLERY=?, ISSUE_ITEM_WEIGHT=?, ISSUE_ITEM_NET_WEIGHT=?, WEIGHT_UNIT_ID=?, RETURN_WASTE_QUANTITY=?, ISSUE_ITEM_WASTAGE_RATE=?, ISSUE_ITEM_WASTAGE_UNIT_ID=? WHERE SALES_ORDER_PROCESS_METAL_ITEM_USED_ID=?");
				
				PreparedStatement stmtInsertInventoryMetalItemEntry = connection.prepareStatement("INSERT INTO inventory_metal_item_entries (ENTRY_DATE, INVENTORY_ACCOUNT_ID, METAL_ITEM_ID, JEWELLERY_ITEM_ID, WEIGHT, IN_OUT_STATUS, VOUCHER_PREFIX, VOUCHER_POSTFIX )	VALUES(?,?,?,?,?,?,?,?)");
				PreparedStatement stmtUpdateInventoryMetalItemEntry = connection.prepareStatement("UPDATE inventory_metal_item_entries SET ENTRY_DATE=?, INVENTORY_ACCOUNT_ID=?, METAL_ITEM_ID=?, JEWELLERY_ITEM_ID=?, WEIGHT=?, IN_OUT_STATUS=?, VOUCHER_PREFIX=?, VOUCHER_POSTFIX=? WHERE INVENTORY_METAL_ITEM_ENTRY_ID=?");
				
				Iterator oIterator ;


				// Vendor Gems Issue
				oIterator = actionForm.getVendorGemIssueList().iterator();
				while (oIterator.hasNext()){
					salesOrderProcessGemIssueReturn = (SalesOrderProcessGemIssueReturnForm) oIterator.next();
					if(salesOrderProcessGemIssueReturn.isInsertable()) {
						stmtInsertGemIssue.setInt(1, Integer.parseInt(actionForm.getSalesOrderProcessId()));
						stmtInsertGemIssue.setString(2, salesOrderProcessGemIssueReturn.getTransactionDate());
						stmtInsertGemIssue.setInt(3, Integer.parseInt(salesOrderProcessGemIssueReturn.getItemId()));
						stmtInsertGemIssue.setFloat(4, Float.parseFloat(salesOrderProcessGemIssueReturn.getWeight()));
						stmtInsertGemIssue.setInt(5, Integer.parseInt(salesOrderProcessGemIssueReturn.getWeightUnitId()));
						stmtInsertGemIssue.setInt(6, Integer.parseInt(salesOrderProcessGemIssueReturn.getQuantity()));
						stmtInsertGemIssue.setInt(7, Integer.parseInt(salesOrderProcessGemIssueReturn.getItemStockType()));
						stmtInsertGemIssue.execute();
						rs = stmtInsertGemIssue.getGeneratedKeys();
						salesOrderProcessGemIssueReturn.setSalesOrderProcessGemIssueReturnId(rs.next()?rs.getString(1):"0");
						rs.close();
						salesOrderProcessGemIssueReturn.setInsertable(false);
						stmtInsertGemIssue.clearParameters();
						
					} else {
						stmtUpdateGemIssue.setString(1, salesOrderProcessGemIssueReturn.getTransactionDate());
						stmtUpdateGemIssue.setInt(2, Integer.parseInt(salesOrderProcessGemIssueReturn.getItemId()));
						stmtUpdateGemIssue.setFloat(3, Float.parseFloat(salesOrderProcessGemIssueReturn.getWeight()));
						stmtUpdateGemIssue.setInt(4, Integer.parseInt(salesOrderProcessGemIssueReturn.getWeightUnitId()));
						stmtUpdateGemIssue.setInt(5, Integer.parseInt(salesOrderProcessGemIssueReturn.getQuantity()));
						stmtUpdateGemIssue.setInt(6, Integer.parseInt(salesOrderProcessGemIssueReturn.getItemStockType()));
						stmtUpdateGemIssue.setInt(7, Integer.parseInt(salesOrderProcessGemIssueReturn.getSalesOrderProcessGemIssueReturnId()));
						stmtUpdateGemIssue.execute();
						stmtUpdateGemIssue.clearParameters();
					}
				}

				// Vendor Gems Return	
				oIterator = (actionForm.getVendorGemReturnList()).iterator();
				while (oIterator.hasNext()){
					salesOrderProcessGemIssueReturn = (SalesOrderProcessGemIssueReturnForm) oIterator.next();
					if(salesOrderProcessGemIssueReturn.isInsertable()) {
						stmtInsertGemReturn.setInt(1, Integer.parseInt(actionForm.getSalesOrderProcessId()));
						stmtInsertGemReturn.setString(2, salesOrderProcessGemIssueReturn.getTransactionDate());
						stmtInsertGemReturn.setInt(3, Integer.parseInt(salesOrderProcessGemIssueReturn.getItemId()));
						stmtInsertGemReturn.setFloat(4, Float.parseFloat(salesOrderProcessGemIssueReturn.getWeight()));
						stmtInsertGemReturn.setInt(5, Integer.parseInt(salesOrderProcessGemIssueReturn.getWeightUnitId()));
						stmtInsertGemReturn.setInt(6, Integer.parseInt(salesOrderProcessGemIssueReturn.getQuantity()));
						stmtInsertGemReturn.setInt(7, Integer.parseInt(salesOrderProcessGemIssueReturn.getItemStockType()));
						stmtInsertGemReturn.execute();
						rs = stmtInsertGemReturn.getGeneratedKeys();
						salesOrderProcessGemIssueReturn.setSalesOrderProcessGemIssueReturnId(rs.next()?rs.getString(1):"0");
						rs.close();
						salesOrderProcessGemIssueReturn.setInsertable(false);
						stmtInsertGemReturn.clearParameters();
					} else {
						stmtUpdateGemReturn.setString(1, salesOrderProcessGemIssueReturn.getTransactionDate());
						stmtUpdateGemReturn.setInt(2, Integer.parseInt(salesOrderProcessGemIssueReturn.getItemId()));
						stmtUpdateGemReturn.setFloat(3, Float.parseFloat(salesOrderProcessGemIssueReturn.getWeight()));
						stmtUpdateGemReturn.setInt(4, Integer.parseInt(salesOrderProcessGemIssueReturn.getWeightUnitId()));
						stmtUpdateGemReturn.setInt(5, Integer.parseInt(salesOrderProcessGemIssueReturn.getQuantity()));
						stmtUpdateGemReturn.setInt(6, Integer.parseInt(salesOrderProcessGemIssueReturn.getItemStockType()));
						stmtUpdateGemReturn.setInt(7, Integer.parseInt(salesOrderProcessGemIssueReturn.getSalesOrderProcessGemIssueReturnId()));
						stmtUpdateGemReturn.execute();
					}
				}

				//Vendor Gems Labour
				oIterator = (actionForm.getGemLabourList()).iterator(); 
				while (oIterator.hasNext()){
					salesOrderProcessGemLabour = (SalesOrderProcessGemLabourForm) oIterator.next();
					vendorTotalLabour += Math.max(Float.parseFloat(salesOrderProcessGemLabour.getEstimatedTotalLabour()), Float.parseFloat(salesOrderProcessGemLabour.getActualTotalLabour()));	
					if(salesOrderProcessGemLabour.isInsertable()){
						stmtInsertGemLabour.setInt(1,Integer.parseInt(actionForm.getSalesOrderProcessId()));
						stmtInsertGemLabour.setFloat(2,Float.parseFloat(salesOrderProcessGemLabour.getSettingRate()));
						stmtInsertGemLabour.setFloat(3,Float.parseFloat(salesOrderProcessGemLabour.getEstimatedTotalLabour()));
						stmtInsertGemLabour.setInt(4,Integer.parseInt(salesOrderProcessGemLabour.getEstimatedQuantity()));
						stmtInsertGemLabour.setInt(5,Integer.parseInt(salesOrderProcessGemLabour.getActualQuantity()));
						stmtInsertGemLabour.setFloat(6,Float.parseFloat(salesOrderProcessGemLabour.getActualTotalLabour()));
						stmtInsertGemLabour.execute();
						rs = stmtInsertGemLabour.getGeneratedKeys();
						salesOrderProcessGemLabour.setSalesOrderProcessGemLabourId(rs.next()?rs.getString(1):"0");
						rs.close();
						salesOrderProcessGemLabour.setInsertable(false);
						stmtInsertGemLabour.clearParameters();
					} else {
						stmtUpdateGemLabour.setInt(1,Integer.parseInt(actionForm.getSalesOrderProcessId()));
						stmtUpdateGemLabour.setFloat(2,Float.parseFloat(salesOrderProcessGemLabour.getSettingRate()));
						stmtUpdateGemLabour.setFloat(3,Float.parseFloat(salesOrderProcessGemLabour.getEstimatedTotalLabour()));
						stmtUpdateGemLabour.setInt(4,Integer.parseInt(salesOrderProcessGemLabour.getEstimatedQuantity()));
						stmtUpdateGemLabour.setInt(5,Integer.parseInt(salesOrderProcessGemLabour.getActualQuantity()));
						stmtUpdateGemLabour.setFloat(6,Float.parseFloat(salesOrderProcessGemLabour.getActualTotalLabour()));
						stmtUpdateGemLabour.setInt(7,Integer.parseInt(salesOrderProcessGemLabour.getSalesOrderProcessGemLabourId()));
						stmtUpdateGemLabour.execute();
						stmtUpdateGemLabour.clearParameters();
					}
				}
				// Sales Order Process Item Metal Used 
				oIterator = (actionForm.getMetalUsedList()).iterator();
				while (oIterator.hasNext()){
					salesOrderProcessMetalUsed = (SalesOrderProcessMetalUsedForm) oIterator.next();

					if(salesOrderProcessMetalUsed.isInsertable()){
						//Insert Inventroy Metal Entry Out
						stmtInsertInventoryMetalEntry.setString(1, actionForm.getActualDeliveryDate());
						stmtInsertInventoryMetalEntry.setInt(2,Integer.parseInt(actionForm.getVendorLedgerAccountId()));
						stmtInsertInventoryMetalEntry.setInt(3,Integer.parseInt(salesOrderProcessMetalUsed.getItemId()));
						stmtInsertInventoryMetalEntry.setFloat(4,Float.parseFloat(salesOrderProcessMetalUsed.getWeight()));
						stmtInsertInventoryMetalEntry.setInt(5,0);  //0=Out, 1=In
						stmtInsertInventoryMetalEntry.setString(6,Default.SALES_ORDER_PREFIX);
						stmtInsertInventoryMetalEntry.setString(7,orderTrackingId);
						stmtInsertInventoryMetalEntry.execute();
						rs = stmtInsertInventoryMetalEntry.getGeneratedKeys();
						salesOrderProcessMetalUsed.setInventoryMetalEntryIdOut(rs.next()?rs.getString(1):"0");
						rs.close();
						stmtInsertInventoryMetalEntry.clearParameters();
						
						stmtInsertMetalUsed.setInt(1, Integer.parseInt(actionForm.getSalesOrderProcessId()));
						stmtInsertMetalUsed.setInt(2, Integer.parseInt(salesOrderProcessMetalUsed.getItemId()));
						stmtInsertMetalUsed.setFloat(3, Float.parseFloat(salesOrderProcessMetalUsed.getWeight()));
						stmtInsertMetalUsed.setInt(4, Integer.parseInt(salesOrderProcessMetalUsed.getWeightUnitId()));
						stmtInsertMetalUsed.setFloat(5, Float.parseFloat(salesOrderProcessMetalUsed.getWastageRate()));
						stmtInsertMetalUsed.setInt(6, Integer.parseInt(salesOrderProcessMetalUsed.getWastageUnitId()));
						stmtInsertMetalUsed.setFloat(7, Float.parseFloat(salesOrderProcessMetalUsed.getNetWeight()));
						stmtInsertMetalUsed.setFloat(8, Float.parseFloat(salesOrderProcessMetalUsed.getWastageQuantity()));
						stmtInsertMetalUsed.setInt(9, Integer.parseInt(salesOrderProcessMetalUsed.getInventoryMetalEntryIdOut()));
						//stmtInsertMetalUsed.setInt(9, Integer.parseInt("0"));
						stmtInsertMetalUsed.execute();
						rs = stmtInsertMetalUsed.getGeneratedKeys();
						salesOrderProcessMetalUsed.setSalesOrderProcessMetalUsedId(rs.next()?rs.getString(1):"0");
						rs.close();
						salesOrderProcessMetalUsed.setInsertable(false);
						stmtInsertMetalUsed.clearParameters();
						
					} else {
						//Insert Inventroy Metal Entry Out
						stmtUpdateInventoryMetalEntry.setString(1, actionForm.getActualDeliveryDate());
						stmtUpdateInventoryMetalEntry.setInt(2,Integer.parseInt(actionForm.getVendorLedgerAccountId()));
						stmtUpdateInventoryMetalEntry.setInt(3,Integer.parseInt(salesOrderProcessMetalUsed.getItemId()));
						stmtUpdateInventoryMetalEntry.setFloat(4,Float.parseFloat(salesOrderProcessMetalUsed.getWeight()));
						stmtUpdateInventoryMetalEntry.setInt(5,0);  //0=Out, 1=In
						stmtUpdateInventoryMetalEntry.setString(6,Default.SALES_ORDER_PREFIX);
						stmtUpdateInventoryMetalEntry.setString(7,orderTrackingId);
						stmtUpdateInventoryMetalEntry.setInt(8,Integer.parseInt(salesOrderProcessMetalUsed.getSalesOrderProcessMetalUsedId()));
						stmtUpdateInventoryMetalEntry.execute();
						stmtUpdateInventoryMetalEntry.clearParameters();
						
						stmtUpdateMetalUsed.setInt(1, Integer.parseInt(actionForm.getSalesOrderProcessId()));
						stmtUpdateMetalUsed.setInt(2, Integer.parseInt(salesOrderProcessMetalUsed.getItemId()));
						stmtUpdateMetalUsed.setFloat(3, Float.parseFloat(salesOrderProcessMetalUsed.getWeight()));
						stmtUpdateMetalUsed.setInt(4, Integer.parseInt(salesOrderProcessMetalUsed.getWeightUnitId()));
						stmtUpdateMetalUsed.setFloat(5, Float.parseFloat(salesOrderProcessMetalUsed.getWastageRate()));
						stmtUpdateMetalUsed.setInt(6, Integer.parseInt(salesOrderProcessMetalUsed.getWastageUnitId()));
						stmtUpdateMetalUsed.setFloat(7, Float.parseFloat(salesOrderProcessMetalUsed.getNetWeight()));
						stmtUpdateMetalUsed.setFloat(8, Float.parseFloat(salesOrderProcessMetalUsed.getWastageQuantity()));
						stmtUpdateMetalUsed.setInt(9, Integer.parseInt(salesOrderProcessMetalUsed.getInventoryMetalEntryIdOut()));
						//stmtUpdateMetalUsed.setInt(9, Integer.parseInt("0"));
						stmtUpdateMetalUsed.setInt(10, Integer.parseInt(salesOrderProcessMetalUsed.getSalesOrderProcessMetalUsedId()));
						stmtUpdateMetalUsed.execute();

						//Update Inventroy Item Entry Out
						stmtUpdateInventoryItemEntry.setString(1, actionForm.getActualDeliveryDate());
						stmtUpdateInventoryItemEntry.setInt(2, Integer.parseInt(actionForm.getVendorLedgerAccountId()));
						stmtUpdateInventoryItemEntry.setInt(3, Integer.parseInt(salesOrderProcessMetalUsed.getItemId()));
						stmtUpdateInventoryItemEntry.setFloat(4, Float.parseFloat(salesOrderProcessMetalUsed.getWeight()));
						stmtUpdateInventoryItemEntry.setInt(5, 0);  // 1 IN , 0 Out
						stmtUpdateInventoryItemEntry.setString(6, Default.SALES_ORDER_PREFIX);
						stmtUpdateInventoryItemEntry.setString(7, orderTrackingId);
						stmtUpdateInventoryItemEntry.setString(8, salesOrderProcessMetalUsed.getInventoryMetalEntryIdOut());
						stmtUpdateInventoryItemEntry.execute();
					}
				}

				// Sales Order Process Item Metal Part Used
				oIterator = (actionForm.getMetalItemUsedList()).iterator();
				while (oIterator.hasNext()){
					salesOrderProcessMetalItemUsed = (SalesOrderProcessMetalItemUsedForm) oIterator.next();
					if(salesOrderProcessMetalItemUsed.isInsertable()){
						
						//Insert Inventroy Metal Item Entry Out
						stmtInsertInventoryMetalItemEntry.setString(1, actionForm.getActualDeliveryDate());
						stmtInsertInventoryMetalItemEntry.setInt(2,Default.SALES_OUT_ACCOUNT_ID);
						stmtInsertInventoryMetalItemEntry.setInt(3,Integer.parseInt(salesOrderProcessMetalItemUsed.getItemIdMetal()));
						stmtInsertInventoryMetalItemEntry.setInt(4,Integer.parseInt(salesOrderProcessMetalItemUsed.getItemIdJewellery()));
						stmtInsertInventoryMetalItemEntry.setFloat(5,Float.parseFloat(salesOrderProcessMetalItemUsed.getIssueItemWeight()));
						stmtInsertInventoryMetalItemEntry.setInt(6,0);  //0=Out, 1=In
						stmtInsertInventoryMetalItemEntry.setString(7,Default.SALES_ORDER_PREFIX);
						stmtInsertInventoryMetalItemEntry.setString(8,orderTrackingId);
						stmtInsertInventoryMetalItemEntry.execute();
						rs = stmtInsertInventoryMetalItemEntry.getGeneratedKeys();
						salesOrderProcessMetalItemUsed.setInventoryMetalItemEntryIdOut(rs.next()?rs.getString(1):"0");
						rs.close();
						stmtInsertInventoryMetalItemEntry.clearParameters();
						
						stmtInsertMetalItemUsed.setInt(1, Integer.parseInt(actionForm.getSalesOrderProcessId()));
						stmtInsertMetalItemUsed.setInt(2, Integer.parseInt(salesOrderProcessMetalItemUsed.getItemIdMetal()));
						stmtInsertMetalItemUsed.setInt(3, Integer.parseInt(salesOrderProcessMetalItemUsed.getItemIdJewellery()));
						stmtInsertMetalItemUsed.setFloat(4, Float.parseFloat(salesOrderProcessMetalItemUsed.getIssueItemWeight()));
						stmtInsertMetalItemUsed.setFloat(5, Float.parseFloat(salesOrderProcessMetalItemUsed.getIssueItemNetWeight()));
						stmtInsertMetalItemUsed.setInt(6, Integer.parseInt(salesOrderProcessMetalItemUsed.getWeightUnitId()));
						stmtInsertMetalItemUsed.setFloat(7, Float.parseFloat(salesOrderProcessMetalItemUsed.getReturnWasteQuantity()));
						stmtInsertMetalItemUsed.setFloat(8, Float.parseFloat(salesOrderProcessMetalItemUsed.getIssueItemWastageRate()));
						stmtInsertMetalItemUsed.setInt(9, Integer.parseInt(salesOrderProcessMetalItemUsed.getIssueItemWastageUnitId()));
						stmtInsertMetalItemUsed.setInt(10, Integer.parseInt(salesOrderProcessMetalItemUsed.getInventoryMetalItemEntryIdOut()));
						stmtInsertMetalItemUsed.execute();
						rs=stmtInsertMetalItemUsed.getGeneratedKeys();
						salesOrderProcessMetalItemUsed.setSalesOrderProcessMetalItemUsedId(rs.next()?rs.getString(1):"0");
						rs.close();
						salesOrderProcessMetalItemUsed.setInsertable(false);
						stmtInsertMetalItemUsed.clearParameters();
					}else{
						//Insert Inventroy Metal Item Entry Out
						stmtUpdateInventoryMetalItemEntry.setString(1, actionForm.getActualDeliveryDate());
						stmtUpdateInventoryMetalItemEntry.setInt(2,Default.SALES_OUT_ACCOUNT_ID);
						stmtUpdateInventoryMetalItemEntry.setInt(3,Integer.parseInt(salesOrderProcessMetalItemUsed.getItemIdMetal()));
						stmtUpdateInventoryMetalItemEntry.setInt(4,Integer.parseInt(salesOrderProcessMetalItemUsed.getItemIdJewellery()));
						stmtUpdateInventoryMetalItemEntry.setFloat(5,Float.parseFloat(salesOrderProcessMetalItemUsed.getIssueItemWeight()));
						stmtUpdateInventoryMetalItemEntry.setInt(6,0);  //0=Out, 1=In
						stmtUpdateInventoryMetalItemEntry.setString(7,Default.SALES_ORDER_PREFIX);
						stmtUpdateInventoryMetalItemEntry.setString(8,orderTrackingId);
						stmtUpdateInventoryMetalItemEntry.setInt(9,Integer.parseInt(salesOrderProcessMetalItemUsed.getInventoryMetalItemEntryIdOut()));
						stmtUpdateInventoryMetalItemEntry.execute();
						stmtUpdateInventoryMetalItemEntry.clearParameters();
						
						stmtUpdateMetalItemUsed.setInt(1, Integer.parseInt(actionForm.getSalesOrderProcessId()));
						stmtUpdateMetalItemUsed.setInt(2, Integer.parseInt(salesOrderProcessMetalItemUsed.getItemIdMetal()));
						stmtUpdateMetalItemUsed.setInt(3, Integer.parseInt(salesOrderProcessMetalItemUsed.getItemIdJewellery()));
						stmtUpdateMetalItemUsed.setFloat(4, Float.parseFloat(salesOrderProcessMetalItemUsed.getIssueItemWeight()));
						stmtUpdateMetalItemUsed.setFloat(5, Float.parseFloat(salesOrderProcessMetalItemUsed.getIssueItemNetWeight()));
						stmtUpdateMetalItemUsed.setInt(6, Integer.parseInt(salesOrderProcessMetalItemUsed.getWeightUnitId()));
						stmtUpdateMetalItemUsed.setFloat(7, Float.parseFloat(salesOrderProcessMetalItemUsed.getReturnWasteQuantity()));
						stmtUpdateMetalItemUsed.setFloat(8, Float.parseFloat(salesOrderProcessMetalItemUsed.getIssueItemWastageRate()));
						stmtUpdateMetalItemUsed.setInt(9, Integer.parseInt(salesOrderProcessMetalItemUsed.getIssueItemWastageUnitId()));
						stmtUpdateMetalItemUsed.setInt(10, Integer.parseInt(salesOrderProcessMetalItemUsed.getSalesOrderProcessMetalItemUsedId()));
						stmtUpdateMetalItemUsed.execute();
						stmtUpdateMetalItemUsed.clearParameters();
					}
				}

				//Vendor labour ledger entry Update  
				stmt.executeUpdate("UPDATE ledger_entries SET " 
						+"   ledger_account_id_credit="+actionForm.getVendorLedgerAccountId()
						+" , ledger_account_id_debit="+Default.LABOUR_EXPENSE_ACCOUNT_ID
						+" , entry_date='"+actionForm.getActualDeliveryDate()
						+"', voucher_prefix='"+Default.SALES_ORDER_PREFIX
						+"', voucher_postfix="+orderTrackingId
						+" ,amount="+vendorTotalLabour
						+" ,narration='"+actionForm.getComments()+"'"
						+" WHERE ledger_entry_id="+actionForm.getLabourLedgerEntryId());
				stmt.executeUpdate("DELETE FROM sales_order_process_gem_issue WHERE  SALES_ORDER_PROCESS_GEM_ISSUE_ID IN ("+actionForm.getDeleteVendorGemIssueIds()+")");
				stmt.executeUpdate("DELETE FROM sales_order_process_gem_return WHERE  SALES_ORDER_PROCESS_GEM_RETURN_ID IN ("+actionForm.getDeleteVendorGemReturnIds()+")");
				stmt.executeUpdate("DELETE FROM sales_order_process_gem_labours WHERE  SALES_ORDER_PROCESS_GEM_LABOUR_ID IN ("+actionForm.getDeleteGemLabourIds()+")");
				
				stmt.executeUpdate("DELETE FROM inventory_metal_entries WHERE  INVENTORY_METAL_ENTRY_ID IN (SELECT INVENTORY_METAL_ENTRY_ID FROM sales_order_process_metal_used WHERE  SALES_ORDER_PROCESS_METAL_USED_ID IN ("+actionForm.getDeleteMetalUsedIds()+"))");
				stmt.executeUpdate("DELETE FROM sales_order_process_metal_used WHERE  SALES_ORDER_PROCESS_METAL_USED_ID IN ("+actionForm.getDeleteMetalUsedIds()+")");
				
				stmt.executeUpdate("DELETE FROM inventory_metal_item_entries WHERE  INVENTORY_METAL_ITEM_ENTRY_ID IN (SELECT INVENTORY_METAL_ITEM_ENTRY_ID FROM sales_order_process_metal_item_used WHERE  SALES_ORDER_PROCESS_METAL_ITEM_USED_ID IN ("+actionForm.getDeleteMetalItemUsedIds()+"))");
				stmt.executeUpdate("DELETE FROM sales_order_process_metal_item_used WHERE  SALES_ORDER_PROCESS_METAL_ITEM_USED_ID IN ("+actionForm.getDeleteMetalItemUsedIds()+")");
				stmt.close();
				connection.commit();
				connection.close();
				
			} catch(Exception e) {
				try {connection.rollback();} catch (SQLException sqle){}
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	 
			}
//			actionForm.setHasFormInitialized('N');
		}
		
		return (mapping.findForward("SUCCESS"));
	}

}
