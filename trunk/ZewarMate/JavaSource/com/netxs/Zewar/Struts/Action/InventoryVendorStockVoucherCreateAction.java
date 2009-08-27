package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netxs.Zewar.Default;
import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Struts.Form.InventoryVendorStockVoucherForm;

public class InventoryVendorStockVoucherCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		InventoryVendorStockVoucherForm actionForm = (InventoryVendorStockVoucherForm) form;
		Connection connection = null;
		
		if (actionForm.getHasFormInitialized()!='Y'){
			actionForm.setEntryDate(DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date()));
			actionForm.setInventoryAccountIdCompany(Integer.toString(Default.SALES_OUT_ACCOUNT_ID));
			actionForm.setHasFormInitialized('Y');
			return (mapping.findForward("FAIL"));
		}
		try {
			connection = (Connection) new DBConnection().getMyPooledConnection();
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			ResultSet rs; 
			int invetoryAccountIdIn = 0, invetoryAccountIdOut = 0;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			
			//start of transication 
			connection.setAutoCommit(false);
			
			if (actionForm.getVoucherPrefix().equals("VR")) {
				invetoryAccountIdIn = Integer.parseInt(Integer.toString(Default.SALES_IN_ACCOUNT_ID));
				invetoryAccountIdOut = Integer.parseInt(actionForm.getInventoryAccountIdVendor());
			} else if(actionForm.getVoucherPrefix().equals("VI")) {
				invetoryAccountIdIn = Integer.parseInt(actionForm.getInventoryAccountIdVendor());
				invetoryAccountIdOut = Integer.parseInt(Integer.toString(Default.SALES_OUT_ACCOUNT_ID));
			} else {
				throw new Exception("Unknown Voucher Type.");
			}
			
			rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM inventory_vendor_stock_vouchers WHERE voucher_prefix='"+actionForm.getVoucherPrefix()+"'");
			actionForm.setVoucherPostfix(rs.next()?rs.getString(1):"0");

			stmt.execute(
				 " INSERT INTO inventory_vendor_stock_vouchers (entry_date, inventory_account_id_in, inventory_account_id_out, voucher_prefix, voucher_postfix)" 
				 + " VALUES ( " 
					+"  '"+actionForm.getEntryDate()
					+"','"+invetoryAccountIdIn
					+"','"+invetoryAccountIdOut
					+"','"+actionForm.getVoucherPrefix()
					+"','"+actionForm.getVoucherPostfix()
				+"')"
			);
			rs = stmt.getGeneratedKeys();
			actionForm.setInventoryVendorStockVoucherId(rs.next()? rs.getString(1) : "0");
			
			// delete cash voucher entries, ledger entries and relations
//			stmt.execute("DELETE FROM inventory_vendor_stock_voucher_items WHERE inventory_vendor_stock_voucher_item_id IN ("+actionForm.getRemoveInventoryVendorStockVoucherItemIds()+")");
//			stmt.execute("DELETE FROM inventory_metal_entries WHERE inventory_metal_entry_id IN ("+actionForm.getRemoveInventoryMetalEntryIdsIn()+","+actionForm.getRemoveInventoryMetalEntryIdsIn()+")");

			// Add/Update Ledger Entries & Cash Voucher Enteries
			PreparedStatement stmtUpdateInventoryMetalEntry = connection.prepareStatement("UPDATE inventory_metal_entries SET entry_date=?,	inventory_account_id=?, item_id=?, weight=?  WHERE inventory_metal_entry_id=?");
			PreparedStatement stmtUpdateVoucherItemMetal = connection.prepareStatement("UPDATE inventory_vendor_stock_voucher_items SET  issue_item_id=?, actual_item_id=?, issue_weight=?, alloy_rate=?, alloy_wastage_unit_id=?, actual_weight=?, comments=? WHERE inventory_vendor_stock_voucher_item_id=?");

			PreparedStatement stmtInsertInventoryMetalEntry = connection.prepareStatement("INSERT INTO inventory_metal_entries (entry_date,	inventory_account_id, item_id, weight, in_out_status, voucher_prefix, voucher_postfix )	VALUES(?,?,?,?,?,?,?)");
			PreparedStatement stmtInsertVoucherItemMetal = connection.prepareStatement("INSERT INTO inventory_vendor_stock_voucher_items ( inventory_vendor_stock_voucher_id, issue_item_id, actual_item_id, issue_weight, alloy_rate, alloy_wastage_unit_id, actual_weight, comments, inventory_metal_entry_id_in, inventory_metal_entry_id_out)	VALUES(?,?,?,?,?,?,?,?,?,?)");
			
			for (int index=0; index < actionForm.getVoucherItemMetalList().size(); index++){
				if(actionForm.getVoucherItemMetal(index).isInsertable()) { 
				
					//Add Inventroy Metal Entry In
					//entry_date, inventory_account_id, item_id, weight, in_out_status, voucher_prefix, voucher_postfix
					stmtInsertInventoryMetalEntry.setString(1, actionForm.getEntryDate());
					stmtInsertInventoryMetalEntry.setInt(2, invetoryAccountIdIn);
					stmtInsertInventoryMetalEntry.setInt(3, Integer.parseInt(actionForm.getVoucherItemMetal(index).getActualItemId()));
					stmtInsertInventoryMetalEntry.setFloat(4, Float.parseFloat(actionForm.getVoucherItemMetal(index).getActualWeight()));
					stmtInsertInventoryMetalEntry.setInt(5, 1);
					stmtInsertInventoryMetalEntry.setString(6, actionForm.getVoucherPrefix());
					stmtInsertInventoryMetalEntry.setInt(7, Integer.parseInt(actionForm.getVoucherPostfix()));
					stmtInsertInventoryMetalEntry.execute();
					rs = stmtInsertInventoryMetalEntry.getGeneratedKeys();
					actionForm.getVoucherItemMetal(index).setInventoryMetalEntryIdIn(rs.next()?rs.getString(1):"0");
					
					//Add Inventroy Metal Entry Out
					//entry_date, inventory_account_id, item_id, weight, in_out_status, voucher_prefix, voucher_postfix
					stmtInsertInventoryMetalEntry.setString(1, actionForm.getEntryDate());
					stmtInsertInventoryMetalEntry.setInt(2, invetoryAccountIdOut);
					stmtInsertInventoryMetalEntry.setInt(3, Integer.parseInt(actionForm.getVoucherItemMetal(index).getIssueItemId()));
					stmtInsertInventoryMetalEntry.setFloat(4, Float.parseFloat(actionForm.getVoucherItemMetal(index).getIssueWeight()));
					stmtInsertInventoryMetalEntry.setInt(5, 0);
					stmtInsertInventoryMetalEntry.setString(6, actionForm.getVoucherPrefix());
					stmtInsertInventoryMetalEntry.setInt(7, Integer.parseInt(actionForm.getVoucherPostfix()));
					stmtInsertInventoryMetalEntry.execute();
					rs = stmtInsertInventoryMetalEntry.getGeneratedKeys();
					actionForm.getVoucherItemMetal(index).setInventoryMetalEntryIdOut(rs.next()?rs.getString(1):"0");
					
					//Add Inventory Vendor Stock Voucher Metal Item 
					//inventory_vendor_stock_voucher_id, issue_item_id, actual_item_id, issue_weight, alloy_rate, alloy_wastage_unit_id, actual_weight, comments, inventory_metal_entry_id_in, inventory_metal_entry_id_out
					stmtInsertVoucherItemMetal.setInt(1, Integer.parseInt(actionForm.getInventoryVendorStockVoucherId()));
					stmtInsertVoucherItemMetal.setInt(2, Integer.parseInt(actionForm.getVoucherItemMetal(index).getIssueItemId()));
					stmtInsertVoucherItemMetal.setInt(3, Integer.parseInt(actionForm.getVoucherItemMetal(index).getActualItemId()));
					stmtInsertVoucherItemMetal.setFloat(4, Float.parseFloat(actionForm.getVoucherItemMetal(index).getIssueWeight()));
					stmtInsertVoucherItemMetal.setFloat(5, Float.parseFloat(actionForm.getVoucherItemMetal(index).getAlloyRate()));
					stmtInsertVoucherItemMetal.setInt(6, Integer.parseInt(actionForm.getVoucherItemMetal(index).getAlloyWastageUnitId()));
					stmtInsertVoucherItemMetal.setFloat(7, Float.parseFloat(actionForm.getVoucherItemMetal(index).getActualWeight()));
					stmtInsertVoucherItemMetal.setString(8, actionForm.getVoucherItemMetal(index).getComments());
					stmtInsertVoucherItemMetal.setInt(9, Integer.parseInt(actionForm.getVoucherItemMetal(index).getInventoryMetalEntryIdIn()));
					stmtInsertVoucherItemMetal.setInt(10, Integer.parseInt(actionForm.getVoucherItemMetal(index).getInventoryMetalEntryIdOut()));
					stmtInsertVoucherItemMetal.execute();
					rs = stmtInsertVoucherItemMetal.getGeneratedKeys();
					actionForm.getVoucherItemMetal(index).setInventoryVendorStockVoucherItemId(rs.next()?rs.getString(1):"0");

					stmtInsertInventoryMetalEntry.clearParameters();
					stmtInsertVoucherItemMetal.clearParameters();

					
				} else {
				//Update Inventroy Metal Entry In
					//entry_date=?,	inventory_account_id=?, item_id=?, weight=?
					stmtUpdateInventoryMetalEntry.setString(1, actionForm.getEntryDate());
					stmtUpdateInventoryMetalEntry.setInt(2, invetoryAccountIdIn);
					stmtUpdateInventoryMetalEntry.setInt(3, Integer.parseInt(actionForm.getVoucherItemMetal(index).getActualItemId()));
					stmtUpdateInventoryMetalEntry.setFloat(4, Float.parseFloat(actionForm.getVoucherItemMetal(index).getActualWeight()));
					stmtUpdateInventoryMetalEntry.setInt(5, Integer.parseInt(actionForm.getVoucherItemMetal(index).getInventoryMetalEntryIdIn()));
					stmtUpdateInventoryMetalEntry.execute();
											
					//Update Inventroy Metal Entry Out
					//entry_date=?,	inventory_account_id=?, item_id=?, weight=?
					stmtUpdateInventoryMetalEntry.setString(1, actionForm.getEntryDate());
					stmtUpdateInventoryMetalEntry.setInt(2, invetoryAccountIdOut);
					stmtUpdateInventoryMetalEntry.setInt(3, Integer.parseInt(actionForm.getVoucherItemMetal(index).getIssueItemId()));
					stmtUpdateInventoryMetalEntry.setFloat(4, Float.parseFloat(actionForm.getVoucherItemMetal(index).getIssueWeight()));
					stmtUpdateInventoryMetalEntry.setInt(5, Integer.parseInt(actionForm.getVoucherItemMetal(index).getInventoryMetalEntryIdOut()));
					stmtUpdateInventoryMetalEntry.execute();
					
					//Update Inventory Vendor Stock Voucher Metal Item 
					//issue_item_id=?, actual_item_id=?, issue_weight=?, alloy_rate=?, alloy_wastage_unit_id=?, actual_weight=?, comments=?
					stmtUpdateVoucherItemMetal.setInt(1, Integer.parseInt(actionForm.getVoucherItemMetal(index).getIssueItemId()));
					stmtUpdateVoucherItemMetal.setInt(2, Integer.parseInt(actionForm.getVoucherItemMetal(index).getActualItemId()));
					stmtUpdateVoucherItemMetal.setFloat(3, Float.parseFloat(actionForm.getVoucherItemMetal(index).getIssueWeight()));
					stmtUpdateVoucherItemMetal.setFloat(4,Float.parseFloat(actionForm.getVoucherItemMetal(index).getAlloyRate()));
					stmtUpdateVoucherItemMetal.setInt(5,Integer.parseInt(actionForm.getVoucherItemMetal(index).getAlloyWastageUnitId()));
					stmtUpdateVoucherItemMetal.setFloat(6, Float.parseFloat(actionForm.getVoucherItemMetal(index).getActualWeight()));
					stmtUpdateVoucherItemMetal.setString(7,actionForm.getVoucherItemMetal(index).getComments());
					stmtUpdateVoucherItemMetal.setInt(8, Integer.parseInt(actionForm.getVoucherItemMetal(index).getInventoryVendorStockVoucherItemId()));
					stmtUpdateVoucherItemMetal.execute();

					stmtUpdateInventoryMetalEntry.clearParameters();
					stmtUpdateVoucherItemMetal.clearParameters();
					/**/				
				}
			}

			//Cleanup database resources
			connection.commit();
			stmtInsertInventoryMetalEntry.close();
			stmtInsertVoucherItemMetal.close();
			stmtUpdateInventoryMetalEntry.close();
			stmtUpdateVoucherItemMetal.close();
			connection.close();
		} catch(Exception e) {
			if (connection != null) {
				try {connection.rollback();}catch (SQLException sqle){}
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
			}
			return (mapping.findForward("FAIL"));	 
		}
		
		//clear form data
		actionForm.setEntryDate("");
		actionForm.setHasFormInitialized('N');
		actionForm.setInventoryAccountIdVendor("0");
		actionForm.setInventoryAccountIdCompany("0");
		actionForm.setInventoryVendorStockVoucherId("0");
		actionForm.setVoucherPostfix("0");
		actionForm.setVoucherPrefix("");
		actionForm.getVoucherItemMetalList().clear();
		
		//check user request for add another
		if(!request.getParameter("submitAction").trim().equalsIgnoreCase("save")){
			return (mapping.findForward("SUCCESS_ADD_ANOTHER"));	
		}
		return (mapping.findForward("SUCCESS"));
		 
  }

}
 

