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
import com.netxs.Zewar.Struts.Form.InventorySalesVoucherForm;
import com.netxs.Zewar.Struts.Form.InventorySalesVoucherItemForm;

public class InventorySalesJewelleryVoucherCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		InventorySalesVoucherForm actionForm = (InventorySalesVoucherForm) form;
		Connection connection = null;
		if (actionForm.getHasFormInitialized()!='Y'){
			
			InventorySalesVoucherItemForm voucherItem = new InventorySalesVoucherItemForm();
			voucherItem.setInsertable(true);
			voucherItem.setInventorySalesVoucherItemId("0");
			voucherItem.setInventoryItemEntryIdOut("0");
			voucherItem.setLedgerEntryId("0");
			voucherItem.setItemId("0");
			voucherItem.setQuantity("0");
			voucherItem.setWeight("0");
			voucherItem.setOtherCharges("0.0");
			voucherItem.setWeightUnitId("0"); 
			voucherItem.setRate("0");	
			voucherItem.setItemValueCalculateBy("0");
			voucherItem.setItemValue("0.0");
			
			actionForm.setVoucherPrefix(Default.INVENTORY_PURCHASE_JEWELLERY_VOUCHER_PREFIX);
			actionForm.setItemGroupId("3");
			actionForm.setEntryDate(DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date()));
			actionForm.setInventoryAccountIdCompany(Integer.toString(Default.SALES_OUT_ACCOUNT_ID));
			actionForm.setHasFormInitialized('Y');
			actionForm.setVoucherItem(voucherItem);
			
			actionForm.setMetalWeight("0.0");
			actionForm.setMetalWastageRate("0.0");
			actionForm.setMetalNetWeight("0.0");
			actionForm.setMetalRate("0.0");
			actionForm.setMetalValue("0.0");
			
			actionForm.setMetalWeightUnitId(Integer.toString(Default.WEIGHT_IN_GRAM));
			actionForm.setMetalWeightUnit("gm");
			return (mapping.findForward("FAIL"));
		}
		try {
			actionForm.setItemGroupId("3");
			connection = (Connection) new DBConnection().getMyPooledConnection();
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			ResultSet rs; 
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			
			//start of transication 
			connection.setAutoCommit(false);
	
			// Add Voucher
			rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM inventory_sales_vouchers WHERE voucher_prefix='"+actionForm.getVoucherPrefix()+"'");
			actionForm.setVoucherPostfix(rs.next()?rs.getString(1):"0");
	
			stmt.execute(
				 " INSERT INTO inventory_sales_vouchers (entry_date, voucher_prefix, voucher_postfix, INVENTORY_ACCOUNT_ID_OUT, comments, item_group_id)"
				+" VALUES ("
				+" '"+actionForm.getEntryDate()+"'"
				+",'"+Default.INVENTORY_SALES_VOUCHER_PREFIX+"'"
				+", "+actionForm.getVoucherPostfix()
				+",'"+actionForm.getInventoryAccountIdCompany()+"'"
				+",'"+actionForm.getComments()+"'"
				+",'"+actionForm.getItemGroupId()+"'"
				+" )"
			);
			rs = stmt.getGeneratedKeys();
			actionForm.setInventorySalesVoucherId(rs.next()? rs.getString(1) : "0");
			
			// Add/Update Inventory Entries & Voucher Items
			PreparedStatement stmtInsertInventoryItemEntry = connection.prepareStatement("INSERT INTO inventory_jewellery_entries (entry_date,	inventory_account_id, item_id, weight, in_out_status, voucher_prefix, voucher_postfix )	VALUES(?,?,?,?,?,?,?)");
			PreparedStatement stmtUpdateInventoryItemEntry = connection.prepareStatement("UPDATE inventory_jewellery_entries SET entry_date=?, inventory_account_id=?, item_id=?, weight=?  WHERE inventory_jewellery_entry_id=?");
			//PreparedStatement stmtInsertLedgerEntry = connection.prepareStatement("INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, entry_date, amount, voucher_prefix, voucher_postfix)	VALUES(?, ?, ?, ?, ?, ?)");
			//PreparedStatement stmtUpdateLedgerEntry = connection.prepareStatement("UPDATE ledger_entries SET ledger_account_id_debit=?, ledger_account_id_credit=?, entry_date=?, amount=?, narration=? WHERE ledger_entry_id=?");
			PreparedStatement stmtInsertVoucherItem = connection.prepareStatement("INSERT INTO inventory_sales_voucher_items ( inventory_sales_voucher_id, item_id, weight, weight_unit_id, quantity, rate, item_value_calculate_by, item_value, INVENTORY_ITEM_ENTRY_ID_OUT, LEDGER_ENTRY_ID, OTHER_CHARGES )	VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			PreparedStatement stmtUpdateVoucherItem = connection.prepareStatement("UPDATE inventory_sales_voucher_items SET  item_id=?,	weight=?, weight_unit_id=?, quantity=?, rate=?, item_value_calculate_by=?, item_value=?, OTHER_CHARGES=? WHERE inventory_sales_voucher_item_id=?");

			PreparedStatement stmtInsertVoucherJewelleryGem = connection.prepareStatement("INSERT INTO inventory_sales_voucher_jewellery_gem ( inventory_sales_voucher_id, item_id, weight, weight_unit_id, quantity, rate, item_value_calculate_by, item_value )	VALUES(?,?,?,?,?,?,?,?)");
			PreparedStatement stmtUpdateVoucherJewelleryGem = connection.prepareStatement("UPDATE inventory_sales_voucher_jewellery_gem SET  item_id=?,	weight=?, weight_unit_id=?, quantity=?, rate=?, item_value_calculate_by=?, item_value=? WHERE INVENTORY_SALES_VOUCHER_JEWELLERY_GEM_ID=?");
			
			PreparedStatement stmtInsertVoucherJewelleryMetal = connection.prepareStatement("INSERT INTO inventory_sales_voucher_jewellery_metal (INVENTORY_SALES_VOUCHER_ID, METAL_ITEM_ID, METAL_WEIGHT, METAL_WEIGHT_UNIT_ID, METAL_WASTAGE_RATE, METAL_WASTAGE_UNIT_ID, METAL_NET_WEIGHT, METAL_RATE, METAL_VALUE)	VALUES (?,?,?,?,?,?,?,?,?)");
			PreparedStatement stmtUpdateVoucherJewelleryMetal = connection.prepareStatement("UPDATE inventory_sales_voucher_jewellery_metal SET  INVENTORY_SALES_VOUCHER_ID=?,METAL_ITEM_ID=?, METAL_WEIGHT=?, METAL_WEIGHT_UNIT_ID=?, METAL_WASTAGE_RATE=?, METAL_WASTAGE_UNIT_ID=?, METAL_NET_WEIGHT=?, METAL_RATE=?, METAL_VALUE=? WHERE INVENTORY_SALES_VOUCHER_JEWELLERY_METAL_ID=?");
			
			float totalItemsValue = 0.0f;
			for (int index=0; index < actionForm.getVoucherItemList().size(); index++){
				totalItemsValue += Float.parseFloat(actionForm.getVoucherItem(index).getItemValue());
				if(actionForm.getVoucherItem(index).isInsertable()) { 
					//Add Ledger Entry 
					/*stmtInsertLedgerEntry.setInt(1,Default.LEDGER_CASH_ACCOUNT_ID);
					stmtInsertLedgerEntry.setInt(2,Default.SALES_OUT_ACCOUNT_ID);
					stmtInsertLedgerEntry.setString(3,actionForm.getEntryDate());
					stmtInsertLedgerEntry.setFloat(4,Float.parseFloat(actionForm.getVoucherItem(index).getItemValue()));
					stmtInsertLedgerEntry.setString(5, Default.INVENTORY_SALES_VOUCHER_PREFIX );
					stmtInsertLedgerEntry.setInt(6, Integer.parseInt(actionForm.getVoucherPostfix()));
					stmtInsertLedgerEntry.execute();
					rs = stmtInsertLedgerEntry.getGeneratedKeys();
					actionForm.getVoucherItem(index).setLedgerEntryId(rs.next()?rs.getString(1):"0");*/
					
					//Add Inventroy Item Entry In
					//entry_date, inventory_account_id, item_id, weight, in_out_status, voucher_prefix, voucher_postfix
					stmtInsertInventoryItemEntry.setString(1, actionForm.getEntryDate());
					stmtInsertInventoryItemEntry.setInt(2, Integer.parseInt(actionForm.getInventoryAccountIdCompany()));
					stmtInsertInventoryItemEntry.setInt(3, Integer.parseInt(actionForm.getVoucherItem(index).getItemId()));
					stmtInsertInventoryItemEntry.setFloat(4, Float.parseFloat(actionForm.getVoucherItem(index).getWeight()));
					stmtInsertInventoryItemEntry.setInt(5, 0);
					stmtInsertInventoryItemEntry.setString(6, Default.INVENTORY_SALES_VOUCHER_PREFIX);
					stmtInsertInventoryItemEntry.setInt(7, Integer.parseInt(actionForm.getVoucherPostfix()));
					stmtInsertInventoryItemEntry.execute();
					rs = stmtInsertInventoryItemEntry.getGeneratedKeys();
					actionForm.getVoucherItem(index).setInventoryItemEntryIdOut(rs.next()?rs.getString(1):"0");
					
					//Add Inventory Sales Voucher Item  
					//inventory_sales_voucher_id, item_id, weight, weight_unit_id, quantity, rate, item_value_calculate_by, item_value, INVENTORY_ITEM_ENTRY_ID_OUT
					stmtInsertVoucherItem.setInt(1, Integer.parseInt(actionForm.getInventorySalesVoucherId()));
					stmtInsertVoucherItem.setInt(2, Integer.parseInt(actionForm.getVoucherItem(index).getItemId()));
					stmtInsertVoucherItem.setFloat(3, Float.parseFloat(actionForm.getVoucherItem(index).getWeight()));
					stmtInsertVoucherItem.setInt(4, Integer.parseInt(actionForm.getVoucherItem(index).getWeightUnitId()));
					stmtInsertVoucherItem.setInt(5, Integer.parseInt(actionForm.getVoucherItem(index).getQuantity()));
					stmtInsertVoucherItem.setFloat(6, Float.parseFloat(actionForm.getVoucherItem(index).getRate()));
					stmtInsertVoucherItem.setInt(7, Integer.parseInt(actionForm.getVoucherItem(index).getItemValueCalculateBy()));
					stmtInsertVoucherItem.setFloat(8, Float.parseFloat(actionForm.getVoucherItem(index).getItemValue()));
					stmtInsertVoucherItem.setInt(9, Integer.parseInt(actionForm.getVoucherItem(index).getInventoryItemEntryIdOut()));
					stmtInsertVoucherItem.setInt(10, Integer.parseInt(actionForm.getVoucherItem(index).getLedgerEntryId()));
					stmtInsertVoucherItem.setFloat(11, Float.parseFloat(actionForm.getVoucherItem(index).getOtherCharges()));
					stmtInsertVoucherItem.execute();
					rs = stmtInsertVoucherItem.getGeneratedKeys();
					actionForm.getVoucherItem(index).setInventorySalesVoucherItemId(rs.next()?rs.getString(1):"0");

					//stmtInsertLedgerEntry.clearParameters();
					stmtInsertInventoryItemEntry.clearParameters();
					stmtInsertVoucherItem.clearParameters();

					
				} else {
					
					/*//ledger_account_id_debit=?, ledger_account_id_credit=?, entry_date=?, amount=?
					stmtUpdateLedgerEntry.setInt(1, Default.LEDGER_CASH_ACCOUNT_ID);
					stmtUpdateLedgerEntry.setInt(2, Default.SALES_OUT_ACCOUNT_ID);
					stmtUpdateLedgerEntry.setString(3,actionForm.getEntryDate());
					stmtUpdateLedgerEntry.setFloat(4,Float.parseFloat(actionForm.getVoucherItem(index).getItemValue()));
					stmtUpdateLedgerEntry.setString(5, actionForm.getComments());
					stmtUpdateLedgerEntry.setInt(6, Integer.parseInt(actionForm.getVoucherItem(index).getLedgerEntryId()));
					stmtUpdateLedgerEntry.execute();*/
					
					//Update Inventroy Item Entry In
					//entry_date=?,	inventory_account_id=?, item_id=?, weight=?
					stmtUpdateInventoryItemEntry.setString(1, actionForm.getEntryDate());
					stmtUpdateInventoryItemEntry.setInt(2, Integer.parseInt(actionForm.getInventoryAccountIdCompany()));
					stmtUpdateInventoryItemEntry.setInt(3, Integer.parseInt(actionForm.getVoucherItem(index).getItemId()));
					stmtUpdateInventoryItemEntry.setFloat(4, Float.parseFloat(actionForm.getVoucherItem(index).getWeight()));
					stmtUpdateInventoryItemEntry.setInt(5, Integer.parseInt(actionForm.getVoucherItem(index).getInventoryItemEntryIdOut()));
					stmtUpdateInventoryItemEntry.execute();
					
					//Update Inventory Sales Voucher Item 
					//item_id, weight, weight_unit_id, quantity, rate, item_value_calculate_by, item_value
					stmtUpdateVoucherItem.setInt(1, Integer.parseInt(actionForm.getVoucherItem(index).getItemId()));
					stmtUpdateVoucherItem.setFloat(2, Float.parseFloat(actionForm.getVoucherItem(index).getWeight()));
					stmtUpdateVoucherItem.setInt(3, Integer.parseInt(actionForm.getVoucherItem(index).getWeightUnitId()));
					stmtUpdateVoucherItem.setInt(4, Integer.parseInt(actionForm.getVoucherItem(index).getQuantity()));
					stmtUpdateVoucherItem.setFloat(5, Float.parseFloat(actionForm.getVoucherItem(index).getRate()));
					stmtUpdateVoucherItem.setInt(6, Integer.parseInt(actionForm.getVoucherItem(index).getItemValueCalculateBy()));
					stmtUpdateVoucherItem.setFloat(7, Float.parseFloat(actionForm.getVoucherItem(index).getItemValue()));
					stmtUpdateVoucherItem.setFloat(8, Float.parseFloat(actionForm.getVoucherItem(index).getOtherCharges()));					
					stmtUpdateVoucherItem.setInt(9, Integer.parseInt(actionForm.getVoucherItem(index).getInventorySalesVoucherItemId()));
					stmtUpdateVoucherItem.execute();
					
					//stmtUpdateLedgerEntry.clearParameters();
					stmtUpdateInventoryItemEntry.clearParameters();
					stmtUpdateVoucherItem.clearParameters();
				}
			}

			for (int index=0; index < actionForm.getVoucherJewelleryGemList().size(); index++){
				if(actionForm.getVoucherJewelleryGem(index).isInsertable()) { 

					//Add Inventory Sales Voucher Item  
					stmtInsertVoucherJewelleryGem.setInt(1, Integer.parseInt(actionForm.getInventorySalesVoucherId()));
					stmtInsertVoucherJewelleryGem.setInt(2, Integer.parseInt(actionForm.getVoucherJewelleryGem(index).getItemId()));
					stmtInsertVoucherJewelleryGem.setFloat(3, Float.parseFloat(actionForm.getVoucherJewelleryGem(index).getWeight()));
					stmtInsertVoucherJewelleryGem.setInt(4, Integer.parseInt(actionForm.getVoucherJewelleryGem(index).getWeightUnitId()));
					stmtInsertVoucherJewelleryGem.setInt(5, Integer.parseInt(actionForm.getVoucherJewelleryGem(index).getQuantity()));
					stmtInsertVoucherJewelleryGem.setFloat(6, Float.parseFloat(actionForm.getVoucherJewelleryGem(index).getRate()));
					stmtInsertVoucherJewelleryGem.setInt(7, Integer.parseInt(actionForm.getVoucherJewelleryGem(index).getItemValueCalculateBy()));
					stmtInsertVoucherJewelleryGem.setFloat(8, Float.parseFloat(actionForm.getVoucherJewelleryGem(index).getItemValue()));
					stmtInsertVoucherJewelleryGem.execute();
					rs = stmtInsertVoucherJewelleryGem.getGeneratedKeys();
					actionForm.getVoucherJewelleryGem(index).setInventorySalesVoucherJewelleryGemId(rs.next()?rs.getString(1):"0");
					stmtInsertVoucherJewelleryGem.clearParameters();
				} else {
					//Update Inventory Sales Voucher Item 
					stmtUpdateVoucherJewelleryGem.setInt(1, Integer.parseInt(actionForm.getVoucherJewelleryGem(index).getItemId()));
					stmtUpdateVoucherJewelleryGem.setFloat(2, Float.parseFloat(actionForm.getVoucherJewelleryGem(index).getWeight()));
					stmtUpdateVoucherJewelleryGem.setInt(3, Integer.parseInt(actionForm.getVoucherJewelleryGem(index).getWeightUnitId()));
					stmtUpdateVoucherJewelleryGem.setInt(4, Integer.parseInt(actionForm.getVoucherJewelleryGem(index).getQuantity()));
					stmtUpdateVoucherJewelleryGem.setFloat(5, Float.parseFloat(actionForm.getVoucherJewelleryGem(index).getRate()));
					stmtUpdateVoucherJewelleryGem.setInt(6, Integer.parseInt(actionForm.getVoucherJewelleryGem(index).getItemValueCalculateBy()));
					stmtUpdateVoucherJewelleryGem.setFloat(7, Float.parseFloat(actionForm.getVoucherJewelleryGem(index).getItemValue()));
					stmtUpdateVoucherJewelleryGem.setInt(8, Integer.parseInt(actionForm.getVoucherJewelleryGem(index).getInventorySalesVoucherJewelleryGemId()));
					stmtUpdateVoucherJewelleryGem.execute();

					stmtUpdateVoucherJewelleryGem.clearParameters();
				}
			}
			// Voucher Jewellery Metal
			stmtInsertVoucherJewelleryMetal.setInt(1,Integer.parseInt(actionForm.getInventorySalesVoucherId()));
			stmtInsertVoucherJewelleryMetal.setInt(2,Integer.parseInt(actionForm.getMetalItemId()));
			stmtInsertVoucherJewelleryMetal.setFloat(3, Float.parseFloat(actionForm.getMetalWeight()));
			stmtInsertVoucherJewelleryMetal.setInt(4,Integer.parseInt(actionForm.getMetalWeightUnitId()));
			stmtInsertVoucherJewelleryMetal.setFloat(5, Float.parseFloat(actionForm.getMetalWastageRate()));
			stmtInsertVoucherJewelleryMetal.setInt(6,Integer.parseInt(actionForm.getMetalWastageUnitId()));
			stmtInsertVoucherJewelleryMetal.setFloat(7, Float.parseFloat(actionForm.getMetalNetWeight()));
			stmtInsertVoucherJewelleryMetal.setFloat(8, Float.parseFloat(actionForm.getMetalRate()));
			stmtInsertVoucherJewelleryMetal.setFloat(9, Float.parseFloat(actionForm.getMetalValue()));
			stmtInsertVoucherJewelleryMetal.execute();
			rs = stmtInsertVoucherJewelleryMetal.getGeneratedKeys();
			actionForm.setInventorySalesVoucherJewelleryMetalId(rs.next()?rs.getString(1):"0");
			stmtInsertVoucherJewelleryGem.clearParameters();
			
			
			//Add Cash Voucher
			String cashVoucherPostfix ;
			
			rs = stmt.executeQuery("SELECT voucher_postfix, cash_voucher_id FROM cash_vouchers WHERE VOUCHER_PREFIX='"+Default.CASH_BOOK_RECEIVE_VOUCHER_PREFIX+"' AND LEDGER_ACCOUNT_ID='"+Default.LEDGER_CASH_ACCOUNT_ID+"' AND ENTRY_DATE='"+actionForm.getEntryDate()+"'"  );
			if (rs.next()) {
				cashVoucherPostfix = rs.getString(1);
				actionForm.setCashVoucherId(rs.getString(2));
			} else {
				rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM cash_vouchers WHERE voucher_prefix='"+Default.CASH_BOOK_RECEIVE_VOUCHER_PREFIX+"'");
				cashVoucherPostfix = (rs.next()?rs.getString(1):"0");	
				stmt.execute(" INSERT INTO cash_vouchers (entry_date, voucher_prefix, voucher_postfix, ledger_account_id)"
							+" VALUES ("
							+" '"+actionForm.getEntryDate()+"'"
							+",'"+Default.CASH_BOOK_RECEIVE_VOUCHER_PREFIX+"'"
							+", "+cashVoucherPostfix
							+", "+Default.LEDGER_CASH_ACCOUNT_ID
							+" )"
				);
				rs = stmt.getGeneratedKeys();
				actionForm.setCashVoucherId(rs.next()?rs.getString(1):"0");
			}
			//Add Ledger Entries & Cash Voucher Enteries
			PreparedStatement stmtInsertLedgerEntry = connection.prepareStatement("INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, entry_date, amount, voucher_prefix, voucher_postfix, narration)	VALUES(?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement stmtInsertVoucherEntry = connection.prepareStatement("INSERT INTO cash_voucher_entries (cash_voucher_id, ledger_account_id, narration, amount, ledger_entry_id)	VALUES(?, ?, ?, ?, ?)");
			String narration = "Voucher # "+Default.INVENTORY_SALES_VOUCHER_PREFIX+"-"+actionForm.getVoucherPostfix()+" "+actionForm.getComments();
			
			//Add Ledger Entry
			stmtInsertLedgerEntry.setInt(1,Default.LEDGER_CASH_ACCOUNT_ID);
			stmtInsertLedgerEntry.setInt(2,Default.SALES_OUT_ACCOUNT_ID);
			stmtInsertLedgerEntry.setString(3,actionForm.getEntryDate());
			stmtInsertLedgerEntry.setFloat(4,totalItemsValue);
			stmtInsertLedgerEntry.setString(5, Default.CASH_BOOK_RECEIVE_VOUCHER_PREFIX);
			stmtInsertLedgerEntry.setInt(6, Integer.parseInt(cashVoucherPostfix));
			stmtInsertLedgerEntry.setString(7, narration);
			stmtInsertLedgerEntry.execute();
			rs = stmtInsertLedgerEntry.getGeneratedKeys();
			actionForm.setLedgerEntryId(rs.next()?rs.getString(1):"0");
			
			//Add Cash Voucher Entry 
			stmtInsertVoucherEntry.setInt(1, Integer.parseInt(actionForm.getCashVoucherId()));
			stmtInsertVoucherEntry.setInt(2, Default.SALES_OUT_ACCOUNT_ID);
			stmtInsertVoucherEntry.setString(3, narration);
			stmtInsertVoucherEntry.setFloat(4, totalItemsValue);
			stmtInsertVoucherEntry.setInt(5,Integer.parseInt(actionForm.getLedgerEntryId()));
			stmtInsertVoucherEntry.execute();
			rs = stmtInsertVoucherEntry.getGeneratedKeys();
			actionForm.setCashVoucherEntryId(rs.next()?rs.getString(1):"0");

			stmtInsertLedgerEntry.clearParameters();
			stmtInsertVoucherEntry.clearParameters();
			
			//Update Cash Voucher, Entry and Ledger Entry Ids in Inventory Purchase Voucher
			stmt.execute(
					" UPDATE inventory_sales_vouchers  SET "
					+" LEDGER_ENTRY_ID='"+actionForm.getLedgerEntryId()+"'"
					+",CASH_VOUCHER_ID='"+actionForm.getCashVoucherId()+"'"
					+",CASH_VOUCHER_ENTRY_ID=' "+actionForm.getCashVoucherEntryId()+"'"
					+" WHERE inventory_sales_voucher_id='"+actionForm.getInventorySalesVoucherId()+"'"
			);
			
			//Cleanup database resources
			connection.commit();
			stmtInsertVoucherItem.close();
			stmtUpdateVoucherItem.close();
			stmtInsertInventoryItemEntry.close();
			stmtUpdateInventoryItemEntry.close();
			stmtInsertVoucherJewelleryGem.close();
			stmtUpdateVoucherJewelleryGem.close();
			stmtInsertVoucherJewelleryMetal.close();
			stmtUpdateVoucherJewelleryMetal.close();
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
		actionForm.setInventorySalesVoucherId("0");
		actionForm.setVoucherPostfix("");
		actionForm.setVoucherPrefix("");
		actionForm.getVoucherItemList().clear();
		actionForm.getVoucherJewelleryGemList().clear();
		
		//check user request for add another
		if(!request.getParameter("submitAction").trim().equalsIgnoreCase("save")){
			return (mapping.findForward("SUCCESS_ADD_ANOTHER"));	
		}
		return (mapping.findForward("SUCCESS"));
		 
  }
}
 

