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
import com.netxs.Zewar.Struts.Form.InventoryPurchaseVoucherForm;

public class InventoryPurchaseMetalVoucherUpdateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		InventoryPurchaseVoucherForm actionForm = (InventoryPurchaseVoucherForm) form;
		Connection connection = null;
		if (actionForm.getHasFormInitialized()!='Y'){
			actionForm.setVoucherPrefix(Default.INVENTORY_PURCHASE_METAL_VOUCHER_PREFIX);
			actionForm.setItemGroupId("1");
			actionForm.setEntryDate(DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date()));
			actionForm.setInventoryAccountIdCompany(Integer.toString(Default.PURCHASE_IN_ACCOUNT_ID));
			actionForm.setHasFormInitialized('Y');
			return (mapping.findForward("FAIL"));
		}
		try {
			actionForm.setVoucherPrefix(Default.INVENTORY_PURCHASE_METAL_VOUCHER_PREFIX);
			connection = (Connection) new DBConnection().getMyPooledConnection();
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			ResultSet rs; 
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			
			//start of transication 
			connection.setAutoCommit(false);
	
			stmt.execute(
				 " UPDATE inventory_purchase_vouchers SET" 
					+"  entry_date='"+actionForm.getEntryDate()
//					+"',inventory_account_id_in='"+actionForm.getInventoryAccountIdCompany()
//					+"',item_group_id='"+actionForm.getItemGroupId()
					+"',comments='"+actionForm.getComments()
//						+"',voucher_prefix='"+actionForm.getVoucherPrefix()
//						+"',voucher_postfix='"+actionForm.getVoucherPostfix()
				+"' WHERE inventory_purchase_voucher_id="+actionForm.getInventoryPurchaseVoucherId()
			);
			
			// delete cash voucher entries, ledger entries and relations
			stmt.execute("DELETE FROM ledger_entries WHERE ledger_entry_id IN (select ledger_entry_id FROM inventory_purchase_voucher_items WHERE inventory_purchase_voucher_item_id IN ("+actionForm.getRemoveInventoryPurchaseVoucherItemIds()+"))");
			stmt.execute("DELETE FROM inventory_purchase_voucher_items WHERE inventory_purchase_voucher_item_id IN ("+actionForm.getRemoveInventoryPurchaseVoucherItemIds()+")");
			stmt.execute("DELETE FROM inventory_metal_entries WHERE inventory_metal_entry_id IN ("+actionForm.getRemoveInventoryItemEntryIdsIn()+")");
			
			// Add/Update Inventory Entries & Voucher Items
			PreparedStatement stmtUpdateInventoryItemEntry = connection.prepareStatement("UPDATE inventory_metal_entries SET entry_date=?, inventory_account_id=?, item_id=?, weight=?  WHERE inventory_metal_entry_id=?");
			PreparedStatement stmtUpdateVoucherItem = connection.prepareStatement("UPDATE inventory_purchase_voucher_items SET  item_id=?,	weight=?, weight_unit_id=?, quantity=?, rate=?, item_value_calculate_by=?, item_value=? WHERE inventory_purchase_voucher_item_id=?");
//			PreparedStatement stmtUpdateLedgerEntry = connection.prepareStatement("UPDATE ledger_entries SET ledger_account_id_debit=?, ledger_account_id_credit=?, entry_date=?, amount=? WHERE ledger_entry_id=?");
//			PreparedStatement stmtInsertLedgerEntry = connection.prepareStatement("INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, entry_date, amount, voucher_prefix, voucher_postfix)	VALUES(?, ?, ?, ?, ?, ?)");
			PreparedStatement stmtInsertInventoryItemEntry = connection.prepareStatement("INSERT INTO inventory_metal_entries (entry_date,	inventory_account_id, item_id, weight, in_out_status, voucher_prefix, voucher_postfix )	VALUES(?,?,?,?,?,?,?)");
			PreparedStatement stmtInsertVoucherItem = connection.prepareStatement("INSERT INTO inventory_purchase_voucher_items ( inventory_purchase_voucher_id, item_id, weight, weight_unit_id, quantity, rate, item_value_calculate_by, item_value, inventory_item_entry_id_in, LEDGER_ENTRY_ID)	VALUES(?,?,?,?,?,?,?,?,?,?)");
			float totalItemsValue = 0.0f;
			for (int index=0; index < actionForm.getVoucherItemList().size(); index++){
				totalItemsValue += Float.parseFloat(actionForm.getVoucherItem(index).getItemValue());
				if(actionForm.getVoucherItem(index).isInsertable()) { 
/*					//Add Ledger Entry 
					stmtInsertLedgerEntry.setInt(1,Default.PURCHASE_IN_ACCOUNT_ID);
					stmtInsertLedgerEntry.setInt(2, Default.LEDGER_CASH_ACCOUNT_ID);
					stmtInsertLedgerEntry.setString(3,actionForm.getEntryDate());
					stmtInsertLedgerEntry.setFloat(4,Float.parseFloat(actionForm.getVoucherItem(index).getItemValue()));
					stmtInsertLedgerEntry.setString(5, Default.INVENTORY_PURCHASE_METAL_VOUCHER_PREFIX );
					stmtInsertLedgerEntry.setInt(6, Integer.parseInt(actionForm.getVoucherPostfix()));
					stmtInsertLedgerEntry.execute();
					rs = stmtInsertLedgerEntry.getGeneratedKeys();
					actionForm.getVoucherItem(index).setLedgerEntryId(rs.next()?rs.getString(1):"0");
*/					
					//Add Inventroy Item Entry In
					stmtInsertInventoryItemEntry.setString(1, actionForm.getEntryDate());
					stmtInsertInventoryItemEntry.setInt(2, Integer.parseInt(actionForm.getInventoryAccountIdCompany()));
					stmtInsertInventoryItemEntry.setInt(3, Integer.parseInt(actionForm.getVoucherItem(index).getItemId()));
					stmtInsertInventoryItemEntry.setFloat(4, Float.parseFloat(actionForm.getVoucherItem(index).getWeight()));
					stmtInsertInventoryItemEntry.setInt(5, 1);
					stmtInsertInventoryItemEntry.setString(6, actionForm.getVoucherPrefix());
					stmtInsertInventoryItemEntry.setInt(7, Integer.parseInt(actionForm.getVoucherPostfix()));
					stmtInsertInventoryItemEntry.execute();
					rs = stmtInsertInventoryItemEntry.getGeneratedKeys();
					actionForm.getVoucherItem(index).setInventoryItemEntryIdIn(rs.next()?rs.getString(1):"0");
					
					//Add Inventory Purchase Voucher Item  
					stmtInsertVoucherItem.setInt(1, Integer.parseInt(actionForm.getInventoryPurchaseVoucherId()));
					stmtInsertVoucherItem.setInt(2, Integer.parseInt(actionForm.getVoucherItem(index).getItemId()));
					stmtInsertVoucherItem.setFloat(3, Float.parseFloat(actionForm.getVoucherItem(index).getWeight()));
					stmtInsertVoucherItem.setInt(4, Integer.parseInt(actionForm.getVoucherItem(index).getWeightUnitId()));
					stmtInsertVoucherItem.setInt(5, Integer.parseInt(actionForm.getVoucherItem(index).getQuantity()));
					stmtInsertVoucherItem.setFloat(6, Float.parseFloat(actionForm.getVoucherItem(index).getRate()));
					stmtInsertVoucherItem.setInt(7, Integer.parseInt(actionForm.getVoucherItem(index).getItemValueCalculateBy()));
					stmtInsertVoucherItem.setFloat(8, Float.parseFloat(actionForm.getVoucherItem(index).getItemValue()));
					stmtInsertVoucherItem.setInt(9, Integer.parseInt(actionForm.getVoucherItem(index).getInventoryItemEntryIdIn()));
					stmtInsertVoucherItem.setInt(10, 0); //Integer.parseInt(actionForm.getVoucherItem(index).getLedgerEntryId()));
					stmtInsertVoucherItem.execute();
					rs = stmtInsertVoucherItem.getGeneratedKeys();
					actionForm.getVoucherItem(index).setInventoryPurchaseVoucherItemId(rs.next()?rs.getString(1):"0");

//					stmtInsertLedgerEntry.clearParameters();
					stmtInsertInventoryItemEntry.clearParameters();
					stmtInsertVoucherItem.clearParameters();
				} else {
					
/*					//ledger_account_id_debit=?, ledger_account_id_credit=?, entry_date=?, amount=?
					stmtUpdateLedgerEntry.setInt(1, Default.PURCHASE_IN_ACCOUNT_ID);
					stmtUpdateLedgerEntry.setInt(2, Default.LEDGER_CASH_ACCOUNT_ID);
					stmtUpdateLedgerEntry.setString(3,actionForm.getEntryDate());
					stmtUpdateLedgerEntry.setFloat(4,Float.parseFloat(actionForm.getVoucherItem(index).getItemValue()));
					stmtUpdateLedgerEntry.setInt(5, Integer.parseInt(actionForm.getVoucherItem(index).getLedgerEntryId()));
					stmtUpdateLedgerEntry.execute();
*/					
					//Update Inventroy Item Entry In
					//entry_date=?,	inventory_account_id=?, item_id=?, weight=?
					stmtUpdateInventoryItemEntry.setString(1, actionForm.getEntryDate());
					stmtUpdateInventoryItemEntry.setInt(2, Integer.parseInt(actionForm.getInventoryAccountIdCompany()));
					stmtUpdateInventoryItemEntry.setInt(3, Integer.parseInt(actionForm.getVoucherItem(index).getItemId()));
					stmtUpdateInventoryItemEntry.setFloat(4, Float.parseFloat(actionForm.getVoucherItem(index).getWeight()));
					stmtUpdateInventoryItemEntry.setInt(5, Integer.parseInt(actionForm.getVoucherItem(index).getInventoryItemEntryIdIn()));
					stmtUpdateInventoryItemEntry.execute();
					
					//Update Inventory Purchase Voucher Item 
					//item_id, weight, weight_unit_id, quantity, rate, item_value_calculate_by, item_value
					stmtUpdateVoucherItem.setInt(1, Integer.parseInt(actionForm.getVoucherItem(index).getItemId()));
					stmtUpdateVoucherItem.setFloat(2, Float.parseFloat(actionForm.getVoucherItem(index).getWeight()));
					stmtUpdateVoucherItem.setInt(3, Integer.parseInt(actionForm.getVoucherItem(index).getWeightUnitId()));
					stmtUpdateVoucherItem.setInt(4, Integer.parseInt(actionForm.getVoucherItem(index).getQuantity()));
					stmtUpdateVoucherItem.setFloat(5, Float.parseFloat(actionForm.getVoucherItem(index).getRate()));
					stmtUpdateVoucherItem.setInt(6, Integer.parseInt(actionForm.getVoucherItem(index).getItemValueCalculateBy()));
					stmtUpdateVoucherItem.setFloat(7, Float.parseFloat(actionForm.getVoucherItem(index).getItemValue()));
					stmtUpdateVoucherItem.setInt(8, Integer.parseInt(actionForm.getVoucherItem(index).getInventoryPurchaseVoucherItemId()));
//					stmtUpdateLedgerEntry.clearParameters();

					stmtUpdateVoucherItem.execute();
					stmtUpdateInventoryItemEntry.clearParameters();
					stmtUpdateVoucherItem.clearParameters();
				}
			}

			// Add Cash Voucher
			String cashVoucherPostfix = "0";
			rs = stmt.executeQuery("SELECT * FROM cash_vouchers WHERE CASH_VOUCHER_ID='"+actionForm.getCashVoucherId()+"'"  );
			if (rs.next()) {
				if(actionForm.getEntryDate()!=rs.getString("ENTRY_DATE")){
					rs = stmt.executeQuery("SELECT voucher_postfix, cash_voucher_id FROM cash_vouchers WHERE VOUCHER_PREFIX='"+Default.CASH_BOOK_PAYMENT_VOUCHER_PREFIX+"' AND LEDGER_ACCOUNT_ID='"+Default.LEDGER_CASH_ACCOUNT_ID+"' AND ENTRY_DATE='"+actionForm.getEntryDate()+"'"  );
					if (rs.next()) {
						cashVoucherPostfix = rs.getString(1);
						actionForm.setCashVoucherId(rs.getString(2));
					} else {
						rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM cash_vouchers WHERE voucher_prefix='"+Default.CASH_BOOK_PAYMENT_VOUCHER_PREFIX+"'");
						cashVoucherPostfix = (rs.next()?rs.getString(1):"0");	
						stmt.execute(" INSERT INTO cash_vouchers (entry_date, voucher_prefix, voucher_postfix, ledger_account_id)"
									+" VALUES ("
									+" '"+actionForm.getEntryDate()+"'"
									+",'"+Default.CASH_BOOK_PAYMENT_VOUCHER_PREFIX+"'"
									+", "+cashVoucherPostfix
									+", "+Default.LEDGER_CASH_ACCOUNT_ID
									+" )"
						);
						rs = stmt.getGeneratedKeys();
						actionForm.setCashVoucherId(rs.next()?rs.getString(1):"0");
					}
				}
					
				//Update Ledger Entries & Cash Voucher Enteries
				PreparedStatement stmtUpdateLedgerEntry = connection.prepareStatement("UPDATE ledger_entries SET entry_date=?, amount=?, voucher_postfix=? , narration=? WHERE ledger_entry_id=?");
				PreparedStatement stmtUpdateVoucherEntry = connection.prepareStatement("UPDATE cash_voucher_entries SET cash_voucher_id=?, narration=?, amount=? WHERE CASH_VOUCHER_ENTRY_ID=?");
				String narration = "Voucher # "+actionForm.getVoucherPrefix()+"-"+actionForm.getVoucherPostfix()+" "+actionForm.getComments();
				
				//Update Ledger Entry
				//entry_date=?, amount=?, voucher_postfix,ledger_entry_id=?
				stmtUpdateLedgerEntry.setString(1,actionForm.getEntryDate());
				stmtUpdateLedgerEntry.setFloat(2, totalItemsValue);
				stmtUpdateLedgerEntry.setInt(3, Integer.parseInt(cashVoucherPostfix));
				stmtUpdateLedgerEntry.setString(4, narration);
				stmtUpdateLedgerEntry.setInt(5, Integer.parseInt(actionForm.getLedgerEntryId()));
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
				
				//Update Cash Voucher, Entry and Ledger Entry Ids in Inventory Purchase Voucher
				stmt.execute(
						 " UPDATE inventory_purchase_vouchers SET "
						+" LEDGER_ENTRY_ID='"+actionForm.getLedgerEntryId()+"'"
						+",CASH_VOUCHER_ID='"+actionForm.getCashVoucherId()+"'"
						+",CASH_VOUCHER_ENTRY_ID=' "+actionForm.getCashVoucherEntryId()+"'"
						+" WHERE INVENTORY_PURCHASE_VOUCHER_ID='"+actionForm.getInventoryPurchaseVoucherId()+"'"
				);
			}
			//Cleanup database resources
			connection.commit();
			stmtInsertInventoryItemEntry.close();
			stmtInsertVoucherItem.close();
			stmtUpdateInventoryItemEntry.close();
			stmtUpdateVoucherItem.close();
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
		actionForm.setInventoryPurchaseVoucherId("0");
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