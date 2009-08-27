
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

public class InventorySalesGemVoucherUpdateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		InventorySalesVoucherForm actionForm = (InventorySalesVoucherForm) form;
		Connection connection = null;
		if (actionForm.getHasFormInitialized()!='Y'){
			actionForm.setVoucherPrefix(Default.INVENTORY_SALES_VOUCHER_PREFIX);
			actionForm.setItemGroupId("2");
			actionForm.setEntryDate(DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date()));
			actionForm.setInventoryAccountIdCompany(Integer.toString(Default.SALES_OUT_ACCOUNT_ID));
			actionForm.setHasFormInitialized('Y');
			return (mapping.findForward("FAIL"));
		}
		try {
			actionForm.setVoucherPrefix(Default.INVENTORY_SALES_VOUCHER_PREFIX);
			connection = (Connection) new DBConnection().getMyPooledConnection();
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			ResultSet rs; 
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			
			//start of transication 
			connection.setAutoCommit(false);
	
			stmt.execute(
				 " UPDATE inventory_sales_vouchers SET" 
					+"  entry_date='"+actionForm.getEntryDate()
//					+"',INVENTORY_ACCOUNT_ID_OUT='"+actionForm.getInventoryAccountIdCompany()
//					+"',item_group_id='"+actionForm.getItemGroupId()
					+"',comments='"+actionForm.getComments()
//						+"',voucher_prefix='"+actionForm.getVoucherPrefix()
//						+"',voucher_postfix='"+actionForm.getVoucherPostfix()
				+"' WHERE inventory_sales_voucher_id="+actionForm.getInventorySalesVoucherId()
			);
			
			// delete cash voucher entries, ledger entries and relations
			stmt.execute("DELETE FROM ledger_entries WHERE ledger_entry_id IN (select ledger_entry_id FROM inventory_sales_voucher_items WHERE inventory_sales_voucher_item_id IN ("+actionForm.getRemoveInventorySalesVoucherItemIds()+"))");
			stmt.execute("DELETE FROM inventory_sales_voucher_items WHERE inventory_sales_voucher_item_id IN ("+actionForm.getRemoveInventorySalesVoucherItemIds()+")");
			stmt.execute("DELETE FROM inventory_gem_entries WHERE inventory_gem_entry_id IN ("+actionForm.getRemoveInventoryItemEntryIdsOut()+")");

			// Add/Update Inventory Entries & Voucher Items
			PreparedStatement stmtUpdateInventoryItemEntry = connection.prepareStatement("UPDATE inventory_gem_entries SET entry_date=?, inventory_account_id=?, item_id=?, quantity=?, weight=?, item_weight_unit_id=?  WHERE inventory_gem_entry_id=?");
			PreparedStatement stmtUpdateVoucherItem = connection.prepareStatement("UPDATE inventory_sales_voucher_items SET  item_id=?,	weight=?, weight_unit_id=?, quantity=?, rate=?, item_value_calculate_by=?, item_value=? WHERE inventory_sales_voucher_item_id=?");
			//PreparedStatement stmtUpdateLedgerEntry = connection.prepareStatement("UPDATE ledger_entries SET ledger_account_id_debit=?, ledger_account_id_credit=?, entry_date=?, amount=? WHERE ledger_entry_id=?");
			//PreparedStatement stmtInsertLedgerEntry = connection.prepareStatement("INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, entry_date, amount, voucher_prefix, voucher_postfix)	VALUES(?, ?, ?, ?, ?, ?)");
			PreparedStatement stmtInsertInventoryItemEntry = connection.prepareStatement("INSERT INTO inventory_gem_entries (entry_date, inventory_account_id, item_id, quantity, weight, in_out_status, voucher_prefix, voucher_postfix, item_weight_unit_id )	VALUES(?,?,?,?,?,?,?,?,?)");
			PreparedStatement stmtInsertVoucherItem = connection.prepareStatement("INSERT INTO inventory_sales_voucher_items ( inventory_sales_voucher_id, item_id, weight, weight_unit_id, quantity, rate, item_value_calculate_by, item_value, INVENTORY_ITEM_ENTRY_ID_OUT, LEDGER_ENTRY_ID)	VALUES(?,?,?,?,?,?,?,?,?,?)");
			float totalItemsValue = 0.0f;
			for (int index=0; index < actionForm.getVoucherItemList().size(); index++){
				totalItemsValue += Float.parseFloat(actionForm.getVoucherItem(index).getItemValue());
				if(actionForm.getVoucherItem(index).isInsertable()) { 
				
					/*//Add Ledger Entry 
					stmtInsertLedgerEntry.setInt(1,Default.LEDGER_CASH_ACCOUNT_ID);
					stmtInsertLedgerEntry.setInt(2,Default.SALES_OUT_ACCOUNT_ID);
					stmtInsertLedgerEntry.setString(3,actionForm.getEntryDate());
					stmtInsertLedgerEntry.setFloat(4,Float.parseFloat(actionForm.getVoucherItem(index).getItemValue()));
					stmtInsertLedgerEntry.setString(5, Default.INVENTORY_SALES_VOUCHER_PREFIX );
					stmtInsertLedgerEntry.setInt(6, Integer.parseInt(actionForm.getVoucherPostfix()));
					stmtInsertLedgerEntry.execute();
					rs = stmtInsertLedgerEntry.getGeneratedKeys();
					actionForm.getVoucherItem(index).setLedgerEntryId(rs.next()?rs.getString(1):"0");*/
					
					//Add Inventroy Item Entry In
					//entry_date, inventory_account_id, item_id, quantity, weight, in_out_status, voucher_prefix, voucher_postfix
					stmtInsertInventoryItemEntry.setString(1, actionForm.getEntryDate());
					stmtInsertInventoryItemEntry.setInt(2, Integer.parseInt(actionForm.getInventoryAccountIdCompany()));
					stmtInsertInventoryItemEntry.setInt(3, Integer.parseInt(actionForm.getVoucherItem(index).getItemId()));
					stmtInsertInventoryItemEntry.setInt(4, Integer.parseInt(actionForm.getVoucherItem(index).getQuantity()));
					stmtInsertInventoryItemEntry.setFloat(5, Float.parseFloat(actionForm.getVoucherItem(index).getWeight()));
					stmtInsertInventoryItemEntry.setInt(6, 0);
					stmtInsertInventoryItemEntry.setString(7, actionForm.getVoucherPrefix());
					stmtInsertInventoryItemEntry.setInt(8, Integer.parseInt(actionForm.getVoucherPostfix()));
					stmtInsertInventoryItemEntry.setInt(9, Integer.parseInt(actionForm.getVoucherItem(index).getWeightUnitId()));
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
					stmtUpdateLedgerEntry.setInt(5, Integer.parseInt(actionForm.getVoucherItem(index).getLedgerEntryId()));
					stmtUpdateLedgerEntry.execute();*/
					
					//Update Inventroy Item Entry Out
					//entry_date=?,	inventory_account_id=?, item_id=?, quantity=?, weight=?
					stmtUpdateInventoryItemEntry.setString(1, actionForm.getEntryDate());
					stmtUpdateInventoryItemEntry.setInt(2, Integer.parseInt(actionForm.getInventoryAccountIdCompany()));
					stmtUpdateInventoryItemEntry.setInt(3, Integer.parseInt(actionForm.getVoucherItem(index).getItemId()));
					stmtUpdateInventoryItemEntry.setInt(4, Integer.parseInt(actionForm.getVoucherItem(index).getQuantity()));
					stmtUpdateInventoryItemEntry.setFloat(5, Float.parseFloat(actionForm.getVoucherItem(index).getWeight()));
					stmtUpdateInventoryItemEntry.setInt(6, Integer.parseInt(actionForm.getVoucherItem(index).getWeightUnitId()));
					stmtUpdateInventoryItemEntry.setInt(7, Integer.parseInt(actionForm.getVoucherItem(index).getInventoryItemEntryIdOut()));
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
					stmtUpdateVoucherItem.setInt(8, Integer.parseInt(actionForm.getVoucherItem(index).getInventorySalesVoucherItemId()));
					stmtUpdateVoucherItem.execute();
					
					//stmtUpdateLedgerEntry.clearParameters();
					stmtUpdateInventoryItemEntry.clearParameters();
					stmtUpdateVoucherItem.clearParameters();
				}
			}
			//Update Ledger Entries & Cash Voucher Enteries
			PreparedStatement stmtUpdateLedgerEntry = connection.prepareStatement("UPDATE ledger_entries SET entry_date=?, amount=?, narration=? WHERE ledger_entry_id=?");
			PreparedStatement stmtUpdateVoucherEntry = connection.prepareStatement("UPDATE cash_voucher_entries SET cash_voucher_id=?, narration=?, amount=? WHERE CASH_VOUCHER_ENTRY_ID=?");
			String narration = "Voucher # "+actionForm.getVoucherPrefix()+"-"+actionForm.getVoucherPostfix()+" "+actionForm.getComments();
			
			//Update Ledger Entry
			//entry_date=?, amount=?, voucher_postfix,ledger_entry_id=?
			stmtUpdateLedgerEntry.setString(1,actionForm.getEntryDate());
			stmtUpdateLedgerEntry.setFloat(2, totalItemsValue);
			stmtUpdateLedgerEntry.setString(3, narration);
			stmtUpdateLedgerEntry.setInt(4, Integer.parseInt(actionForm.getLedgerEntryId()));
			stmtUpdateLedgerEntry.execute();
			//Update Cash Voucher Entry 
			//cash_voucher_id=?, narration=?, amount=?, CASH_VOUCHER_ENTRY_ID=?
			stmtUpdateVoucherEntry.setInt(1, Integer.parseInt(actionForm.getCashVoucherId()));
			stmtUpdateVoucherEntry.setString(2, narration);
			stmtUpdateVoucherEntry.setFloat(3, totalItemsValue);
			stmtUpdateVoucherEntry.setInt(4, Integer.parseInt(actionForm.getCashVoucherEntryId()));
			stmtUpdateVoucherEntry.execute();

			stmtUpdateLedgerEntry.clearParameters();
			stmtUpdateVoucherEntry.clearParameters();
			//Cleanup database resources
			connection.commit();
			stmtInsertInventoryItemEntry.close();
			stmtInsertVoucherItem.close();
			stmtUpdateInventoryItemEntry.close();
			stmtUpdateVoucherItem.close();
			stmtUpdateLedgerEntry.close();
			stmtUpdateVoucherEntry.close();
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
		
		//check user request for add another
		if(!request.getParameter("submitAction").trim().equalsIgnoreCase("save")){
			return (mapping.findForward("SUCCESS_ADD_ANOTHER"));	
		}
		return (mapping.findForward("SUCCESS"));
		 
  }
}
 

