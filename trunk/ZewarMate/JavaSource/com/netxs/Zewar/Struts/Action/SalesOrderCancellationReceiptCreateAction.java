
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.*;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Default;
import com.netxs.Zewar.Struts.Form.InventoryPurchaseVoucherForm;
import com.netxs.Zewar.Struts.Form.SalesOrderCancellationReceiptForm;
import com.netxs.Zewar.Struts.Form.SalesOrderCancellationReceiptGemForm;
import com.netxs.Zewar.Struts.Form.SalesOrderCancellationReceiptMetalForm;
import com.netxs.Zewar.DataSources.DBConnection;

public class SalesOrderCancellationReceiptCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		SalesOrderCancellationReceiptForm actionForm = (SalesOrderCancellationReceiptForm) form;
		SalesOrderCancellationReceiptMetalForm advanceMetal;
		SalesOrderCancellationReceiptGemForm unusedAdvanceGem;
		SalesOrderCancellationReceiptGemForm usedAdvanceGem;
		
		

		Connection connection;
		ResultSet rs;
		ResultSet rs2;
		String query ;
		Statement stmt;
		Statement stmt2;
		
		
		if(actionForm.getHasFormInitialized()!= 'Y'){ //Initialize Form 
			try {
				connection = (Connection) new DBConnection().getMyPooledConnection();
				stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				stmt2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);

				// Sales Order
				rs = stmt.executeQuery("SELECT * FROM SALES_ORDERS WHERE SALES_ORDER_ID='"+actionForm.getSalesOrderId()+"' AND SALES_ORDER_STATUS_ID NOT IN ("+Default.SALES_ORDER_STATUS_CANCEL+","+Default.SALES_ORDER_STATUS_COMPLETE+")");
				
				if (rs.next()){
					actionForm.setCustomerLedgerAccountId(rs.getString("CUSTOMER_LEDGER_ACCOUNT_ID"));
					actionForm.setCancellationReceiptDate(DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date()));
					actionForm.setSalesOrderTrackingId(rs.getString("SALES_ORDER_TRACKING_ID"));
					actionForm.setHasFormInitialized('Y');
					actionForm.setCancellationCharges("0.00");
				}else{
					ActionMessages serviceErrors = new ActionMessages();
					serviceErrors.add("error",new ActionMessage("errors","Invalid sales order / order already cancel / Order has completed"));
					saveErrors(request,serviceErrors);
					actionForm.setCustomerLedgerAccountId("0");
					((SalesOrderCancellationReceiptForm) form ).setCustomerLedgerAccountId("");
					actionForm.setSalesOrderId(""); 
					return (mapping.findForward("SUCCESS"));	 
				}
				// Sales Order Advance Metal 
				query =  " SELECT"
					+" SAM.ITEM_ID"
					+",IT.ITEM_NAME"
					+",SAM.ITEM_WEIGHT"
					+",WEU.WEIGHT_UNIT_ID"
					+",WEU.UNIT_CODE AS WEIGHT_UNIT"
					+",SAM.ITEM_RATE"
					+",SAM.ITEM_WASTAGE_RATE"
					+",WAU.WASTAGE_UNIT_ID"
					+",WAU.UNIT_CODE AS WASTAGE_UNIT"
					+",SAM.ITEM_NET_WEIGHT"
					+",SAM.ITEM_VALUE"
				+" FROM	sales_order_advance_metals AS SAM"
				+" INNER JOIN	items AS IT ON SAM.ITEM_ID = IT.ITEM_ID" 
				+" INNER JOIN	weight_units AS WEU ON SAM.ITEM_WEIGHT_UNIT_ID = WEU.WEIGHT_UNIT_ID"
				+" INNER JOIN	wastage_units AS WAU ON SAM.ITEM_WASTAGE_RATE_UNIT_ID = WAU.WASTAGE_UNIT_ID"
				+" WHERE SAM.SALES_ORDER_ID='"+actionForm.getSalesOrderId()+"'";
				rs2 = stmt2.executeQuery(query);
				while (rs2.next()) {
					advanceMetal = new SalesOrderCancellationReceiptMetalForm ();
					advanceMetal.setItemId(rs2.getString("ITEM_ID"));
					advanceMetal.setItemName(rs2.getString("ITEM_NAME"));
					advanceMetal.setWeightUnit(rs2.getString("WEIGHT_UNIT"));
					advanceMetal.setWeightUnitId(rs2.getString("WEIGHT_UNIT_ID"));
					advanceMetal.setWastageRate(rs2.getString("ITEM_WASTAGE_RATE"));
					advanceMetal.setWastageUnit(rs2.getString("WASTAGE_UNIT"));
					advanceMetal.setWastageUnitId(rs2.getString("WASTAGE_UNIT_ID"));
					advanceMetal.setRate(rs2.getString("ITEM_RATE")); 
					advanceMetal.setWeight(rs2.getString("ITEM_WEIGHT"));
					advanceMetal.setNetWeight(rs2.getString("ITEM_NET_WEIGHT"));
					advanceMetal.setValue(rs2.getString("ITEM_VALUE"));
					advanceMetal.setKeepInInvenory(Byte.toString(Default.CUSTOMER_INVENTORY_RETURN));
					actionForm.setAdvanceMetal(advanceMetal);
				}

				
				//Sales Order Advance Gems 
				query = " SELECT " 
						+" ZZ.*" 
						+" ,IT.ITEM_NAME "
						+" ,WU.UNIT_CODE AS WEIGHT_UNIT"
						+" FROM" 
						+" ( SELECT "
						+"  XX.ITEM_ID"
						+" ,XX.ITEM_WEIGHT_UNIT_ID"
						+" ,SUM(XX.ADVANCE_WEIGHT) - (SUM(XX.ISSUE_WEIGHT) - SUM(XX.RETURN_WEIGHT)) AS UNUSED_WEIGHT"
						+" ,SUM(XX.ADVANCE_QUANTITY) - (SUM(XX.ISSUE_QUANTITY)  - SUM(XX.RETURN_QUANTITY))AS UNUSED_QUANTITY"
						+" ,SUM(XX.ISSUE_WEIGHT)  - SUM(XX.RETURN_WEIGHT) AS USED_WEIGHT"
						+" ,SUM(XX.ISSUE_QUANTITY) - SUM(XX.RETURN_QUANTITY) AS USED_QUANTITY"
						+" FROM"
						+"  ((SELECT"
						+"     OAG.ITEM_ID"
						+"    ,OAG.ITEM_WEIGHT_UNIT_ID"
						+"    ,SUM(OAG.ITEM_WEIGHT) AS ADVANCE_WEIGHT"
						+"    ,SUM(OAG.ITEM_QUANTITY) AS ADVANCE_QUANTITY"
						+"    ,0 AS ISSUE_WEIGHT"
						+"    ,0 AS ISSUE_QUANTITY"
						+"    ,0 AS RETURN_WEIGHT"
						+"    ,0 AS RETURN_QUANTITY"
						+"    FROM sales_order_advance_gems OAG"
						+"    WHERE SALES_ORDER_ID="+actionForm.getSalesOrderId()
						+"    GROUP BY OAG.ITEM_ID, OAG.ITEM_WEIGHT_UNIT_ID)"
						+"   UNION"
						+"    ( SELECT	"
						+"      GI.ITEM_ID  AS ISSUE_ITEM_ID"
						+"     ,GI.WEIGHT_UNIT_ID AS ISSUE_WEIGHT_UNIT_ID"
						+"     ,0"
						+"     ,0"
						+"     ,SUM(GI.WEIGHT) AS ISSUE_WEIGHT"
						+"     ,SUM(GI.QUANTITY) AS ISSUE_QUANTITY"		
						+"     ,0"
						+"     ,0 "
						+"     FROM sales_order_process_gem_issue GI"
						+"     INNER JOIN sales_order_processes PI ON GI.SALES_ORDER_PROCESS_ID=PI.SALES_ORDER_PROCESS_ID"  
						+"     WHERE PI.SALES_ORDER_ID="+actionForm.getSalesOrderId()+" AND GI.ITEM_STOCK_TYPE="+Default.STOCK_TYPE_CUSTOMER 
						+"     GROUP BY GI.ITEM_ID , GI.WEIGHT_UNIT_ID)"
						+"   UNION"
						+"    ( SELECT	"
						+"      GI.ITEM_ID  AS ISSUE_ITEM_ID"
						+"     ,GI.WEIGHT_UNIT_ID AS ISSUE_WEIGHT_UNIT_ID"
						+"     ,0"
						+"     ,0"
						+"     ,0"
						+"     ,0 "
						+"     ,SUM(GI.WEIGHT) AS RETURN_WEIGHT"
						+"     ,SUM(GI.QUANTITY) AS RETURN_QUANTITY"		
						+"     FROM sales_order_process_gem_return GI"
						+"     INNER JOIN sales_order_processes PI ON GI.SALES_ORDER_PROCESS_ID=PI.SALES_ORDER_PROCESS_ID"
						+"     WHERE PI.SALES_ORDER_ID="+actionForm.getSalesOrderId()+" AND GI.ITEM_STOCK_TYPE="+Default.STOCK_TYPE_CUSTOMER 
						+"     GROUP BY GI.ITEM_ID , GI.WEIGHT_UNIT_ID)"
						+"  ) XX GROUP BY XX.ITEM_ID, XX.ITEM_WEIGHT_UNIT_ID ) ZZ" 
						+"  INNER JOIN items IT ON IT.ITEM_ID=ZZ.ITEM_ID"
						+"  INNER JOIN weight_units WU ON WU.WEIGHT_UNIT_ID=ZZ.ITEM_WEIGHT_UNIT_ID";
				rs2 = stmt2.executeQuery(query);
				while (rs2.next()) {
					if(rs2.getFloat("UNUSED_WEIGHT") > 0 && rs2.getFloat("UNUSED_QUANTITY") > 0) {
						unusedAdvanceGem = new SalesOrderCancellationReceiptGemForm();
						unusedAdvanceGem.setItemId(rs2.getString("ITEM_ID"));
						unusedAdvanceGem.setItemName("Unused - "+rs2.getString("ITEM_NAME"));
						unusedAdvanceGem.setWeightUnit(rs2.getString("WEIGHT_UNIT"));
						unusedAdvanceGem.setWeightUnitId(rs2.getString("ITEM_WEIGHT_UNIT_ID"));
						unusedAdvanceGem.setWeight(rs2.getString("UNUSED_WEIGHT"));
						unusedAdvanceGem.setQuantity(rs2.getString("UNUSED_QUANTITY"));
						unusedAdvanceGem.setRate("0.00");
						unusedAdvanceGem.setValue("0.00");
						unusedAdvanceGem.setKeepInInvenory(Byte.toString(Default.CUSTOMER_INVENTORY_RETURN));
						actionForm.setUnusedAdvanceGem(unusedAdvanceGem);
					}
					if(rs2.getFloat("USED_WEIGHT") > 0 && rs2.getFloat("USED_QUANTITY") > 0) {
						usedAdvanceGem = new SalesOrderCancellationReceiptGemForm();
						usedAdvanceGem.setItemId(rs2.getString("ITEM_ID"));
						usedAdvanceGem.setItemName("Used   - "+rs2.getString("ITEM_NAME"));
						usedAdvanceGem.setWeightUnit(rs2.getString("WEIGHT_UNIT"));
						usedAdvanceGem.setWeightUnitId(rs2.getString("ITEM_WEIGHT_UNIT_ID"));
						usedAdvanceGem.setWeight(rs2.getString("USED_WEIGHT"));
						usedAdvanceGem.setQuantity(rs2.getString("USED_QUANTITY"));
						usedAdvanceGem.setRate("0.00");
						usedAdvanceGem.setValue("0.00");
						usedAdvanceGem.setKeepInInvenory(Byte.toString(Default.CUSTOMER_INVENTORY_RETURN));
						actionForm.setUsedAdvanceGem(usedAdvanceGem);
					}
				}
				stmt.close();
				stmt2.close();
				connection.close();
				actionForm.setHasFormInitialized('Y');
				return (mapping.findForward("FAIL"));
				
			} catch(Exception e) {
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	 
			}

		} else {
			connection = (Connection) new DBConnection().getMyPooledConnection();
			try {
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
				connection.setAutoCommit(false);
				stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);

				// add Cancellation Receipt
				rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM sales_order_cancellation_receipts ");
				actionForm.setVoucherPostfix(rs.next()?rs.getString(1):"null");
				actionForm.setVoucherPrefix(Default.SALES_ORDER_CANCELLATION_VOUCHER_PREFIX);
				actionForm.setAdvanceGemInventoryPurchaseVoucherId("0");
				stmt.executeUpdate(
						  " INSERT INTO sales_order_cancellation_receipts ( VOUCHER_PREFIX, VOUCHER_POSTFIX, ENTRY_DATE, CANCELLATION_CHARGES, COMMENTS, SALES_ORDER_ID, ADVANCE_GEM_INVENTORY_PURCHASE_VOUCHER_ID) VALUES ( "
						+ "  '"+actionForm.getVoucherPrefix()
						+ "', "+actionForm.getVoucherPostfix()
						+ " ,'"+actionForm.getCancellationReceiptDate()
						+ "','"+actionForm.getCancellationCharges()
						+ "','"+actionForm.getRemarks()
						+ "', "+actionForm.getSalesOrderId()
						+ " ,'"+actionForm.getAdvanceGemInventoryPurchaseVoucherId()+"'"
						+ " ) " 
				);
				rs = stmt.getGeneratedKeys();
				actionForm.setSalesOrderCancellationReceiptId(rs.next()?rs.getString(1):"0");

				// Cancellation Chagres Ledger Entry. Customer Debit, Sales Credit
				stmt.execute(
					 " INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, voucher_prefix, voucher_postfix, entry_date, amount, narration )"
					+" VALUES ("
					+     actionForm.getCustomerLedgerAccountId()
					+", "+Default.SALES_OUT_ACCOUNT_ID
					+",'"+Default.SALES_ORDER_CANCELLATION_VOUCHER_PREFIX+"'"
					+", "+actionForm.getVoucherPostfix() 
					+",'"+actionForm.getCancellationReceiptDate()+"'"
					+", "+actionForm.getCancellationCharges()
					+",'"+actionForm.getRemarks()+"'"
					+" )"
				);
				rs = stmt.getGeneratedKeys();
				actionForm.setCancellationChargesLedgerEntryId(rs.next()?rs.getString(1):"0");
				
				// Check, Are we Purchasing any Advance Gem Item From Customer.
				boolean createPurchaseVoucher = false;
				for(int index=0; index < actionForm.getUnusedAdvanceGemList().size(); index++ ) {
					if (Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getKeepInInvenory()) == Default.CUSTOMER_INVENTORY_PURCHASE){
						createPurchaseVoucher = true;
						break;
					}
				}
				if (!createPurchaseVoucher) {
					for(int index=0; index < actionForm.getUsedAdvanceGemList().size(); index++ ) {
						if (Integer.parseInt(actionForm.getUsedAdvanceGem(index).getKeepInInvenory()) == Default.CUSTOMER_INVENTORY_PURCHASE ){
							createPurchaseVoucher = true;
							break;
						}
					}
				}
				

				// Add Purchase Voucher, If we are purchasing any Advance Gem Item
				InventoryPurchaseVoucherForm purchaseVoucherForm = new InventoryPurchaseVoucherForm ();
				if (createPurchaseVoucher) {
					purchaseVoucherForm.setEntryDate(actionForm.getCancellationReceiptDate());
					purchaseVoucherForm.setVoucherPrefix(Default.INVENTORY_PURCHASE_GEM_VOUCHER_PREFIX);
					purchaseVoucherForm.setInventoryAccountIdCompany(Integer.toString(Default.PURCHASE_IN_ACCOUNT_ID));
					purchaseVoucherForm.setComments("Customer Advance Gems. Reference: Sales Order Tracking # "+actionForm.getSalesOrderTrackingId());
					purchaseVoucherForm.setItemGroupId("2");
					
					rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM inventory_purchase_vouchers WHERE voucher_prefix='"+purchaseVoucherForm.getVoucherPrefix()+"'");
					purchaseVoucherForm.setVoucherPostfix(rs.next()?rs.getString(1):"0");
					stmt.execute(
						 " INSERT INTO inventory_purchase_vouchers (entry_date, voucher_prefix, voucher_postfix, inventory_account_id_in, comments, item_group_id)"
						+" VALUES ("
						+" '"+purchaseVoucherForm.getEntryDate()+"'"
						+",'"+purchaseVoucherForm.getVoucherPrefix()+"'"
						+", "+purchaseVoucherForm.getVoucherPostfix()
						+",'"+purchaseVoucherForm.getInventoryAccountIdCompany()+"'"
						+",'"+purchaseVoucherForm.getComments()+"'"
						+",'"+purchaseVoucherForm.getItemGroupId()+"'"
						+" )"
					);
					rs = stmt.getGeneratedKeys();
					actionForm.setAdvanceGemInventoryPurchaseVoucherId(rs.next()? rs.getString(1) : "0");
					purchaseVoucherForm.setInventoryPurchaseVoucherId(actionForm.getAdvanceGemInventoryPurchaseVoucherId());
				} else {
					purchaseVoucherForm.setInventoryPurchaseVoucherId("0");
				}
				
				PreparedStatement stmtInsertAdvanceMetal = connection.prepareStatement("INSERT INTO sales_order_cancellation_receipt_advance_metal (SALES_ORDER_CANCELLATION_RECEIPT_ID, CUSTOMER_INVENTORY_ACTION_ID, WEIGHT, WEIGHT_UNIT_ID, WASTAGE_RATE, WASTAGE_UNIT_ID, NET_WEIGHT, RATE, ITEM_VALUE, INVENTORY_METAL_ENTRY_ID, LEDGER_ENTRY_ID ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement stmtInsertAdvanceUsedGems = connection.prepareStatement("INSERT INTO sales_order_cancellation_receipt_advance_gem_used (SALES_ORDER_CANCELLATION_RECEIPT_ID, CUSTOMER_INVENTORY_ACTION_ID, QUANTITY, WEIGHT, WEIGHT_UNIT_ID, RATE, ITEM_VALUE, INVENTORY_PURCHASE_VOUCHER_ITEM_ID, INVENTORY_PURCHASE_VOUCHER_ID, INVENTORY_GEMS_ENTRY_ID_IN, INVENTORY_GEMS_ENTRY_ID_OUT, LEDGER_ENTRY_ID ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement stmtInsertAdvanceUnusedGems = connection.prepareStatement("INSERT INTO sales_order_cancellation_receipt_advance_gem_unused (SALES_ORDER_CANCELLATION_RECEIPT_ID, CUSTOMER_INVENTORY_ACTION_ID, QUANTITY, WEIGHT, WEIGHT_UNIT_ID, RATE, ITEM_VALUE, INVENTORY_PURCHASE_VOUCHER_ITEM_ID, INVENTORY_PURCHASE_VOUCHER_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

				// Add/Update Inventory Entries & Voucher Items
																																
				PreparedStatement stmtInsertInventoryMetalEntry = connection.prepareStatement("INSERT INTO inventory_metal_entries (ENTRY_DATE, INVENTORY_ACCOUNT_ID, ITEM_ID, WEIGHT, IN_OUT_STATUS, VOUCHER_PREFIX, VOUCHER_POSTFIX )	VALUES(?,?,?,?,?,?,?)");
				PreparedStatement stmtInsertInventoryGemEntry = connection.prepareStatement("INSERT INTO inventory_gem_entries (ENTRY_DATE, INVENTORY_ACCOUNT_ID, ITEM_ID, QUANTITY, WEIGHT, IN_OUT_STATUS, VOUCHER_PREFIX, VOUCHER_POSTFIX )	VALUES(?,?,?,?,?,?,?,?)");
//				PreparedStatement stmtUpdateInventoryGemEntry = connection.prepareStatement("UPDATE inventory_gem_entries SET ENTRY_DATE=?, INVENTORY_ACCOUNT_ID=?, ITEM_ID=?, QUANTITY=?, WEIGHT=?, IN_OUT_STATUS=?, VOUCHER_PREFIX=?, VOUCHER_POSTFIX=?  WHERE INVENTORY_GEM_ENTRY_ID=?");

				PreparedStatement stmtInsertVoucherItem = connection.prepareStatement("INSERT INTO inventory_purchase_voucher_items ( INVENTORY_PURCHASE_VOUCHER_ID, ITEM_ID, WEIGHT, WEIGHT_UNIT_ID, QUANTITY, RATE, ITEM_VALUE_CALCULATE_BY, ITEM_VALUE, INVENTORY_ITEM_ENTRY_ID_IN, LEDGER_ENTRY_ID)	VALUES(?,?,?,?,?,?,?,?,?,?)");
//				PreparedStatement stmtUpdateVoucherItem = connection.prepareStatement("UPDATE inventory_purchase_voucher_items SET  ITEM_ID=?,	WEIGHT=?, WEIGHT_UNIT_ID=?, QUANTITY=?, RATE=?, ITEM_VALUE_CALCULATE_BY=?, ITEM_VALUE=? WHERE INVENTORY_PURCHASE_VOUCHER_ITEM_ID=?");
				PreparedStatement stmtInsertLedgerEntry = connection.prepareStatement("INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, entry_date, amount, voucher_prefix, voucher_postfix, narration)	VALUES(?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement stmtInsertVoucherEntry = connection.prepareStatement("INSERT INTO cash_voucher_entries (cash_voucher_id, ledger_account_id, narration, amount, ledger_entry_id)	VALUES(?, ?, ?, ?, ?)");
				
				float totalItemsValue = 0.0f;	
				

				// Advance Metal
				for(int index=0; index < actionForm.getAdvanceMetalList().size(); index++ ) {
					if (Integer.parseInt(actionForm.getAdvanceMetal(index).getKeepInInvenory()) == Default.CUSTOMER_INVENTORY_RETURN){ // do inventory adjustment entry
						//Add Ledger Entry
						stmt.execute(
							 " INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, voucher_prefix, voucher_postfix, entry_date, amount, narration )"
							+" VALUES ("
							+     actionForm.getCustomerLedgerAccountId()
							+", "+Default.PURCHASE_OUT_ACCOUNT_ID
							+",'"+actionForm.getVoucherPrefix()+"'"
							+", "+actionForm.getVoucherPostfix()
							+",'"+actionForm.getCancellationReceiptDate()+"'"
							+", "+actionForm.getAdvanceMetal(index).getValue()
							+",'Voucher # "+actionForm.getVoucherPrefix()+"-"+actionForm.getVoucherPostfix()+" "+actionForm.getRemarks()
							+" )"
						);
						rs = stmt.getGeneratedKeys();
						actionForm.getAdvanceMetal(index).setLedgerEntryId(rs.next()?rs.getString(1):"0");

						//Add Inventroy Item. Stock Out (by Out Status = Out)
						stmtInsertInventoryMetalEntry.setString(1, actionForm.getCancellationReceiptDate());
						stmtInsertInventoryMetalEntry.setInt(2, Default.PURCHASE_OUT_ACCOUNT_ID);
						stmtInsertInventoryMetalEntry.setInt(3, Integer.parseInt(actionForm.getAdvanceMetal(index).getItemId()));
						stmtInsertInventoryMetalEntry.setFloat(4, Float.parseFloat(actionForm.getAdvanceMetal(index).getNetWeight()));
						stmtInsertInventoryMetalEntry.setInt(5, 0); // In / Out Status 1=In / 0=Out
						stmtInsertInventoryMetalEntry.setString(6, actionForm.getVoucherPrefix());
						stmtInsertInventoryMetalEntry.setInt(7, Integer.parseInt(actionForm.getVoucherPostfix()));
						stmtInsertInventoryMetalEntry.execute();
						rs = stmtInsertInventoryMetalEntry.getGeneratedKeys();
						actionForm.getAdvanceMetal(index).setInventoryEntryId(rs.next()?rs.getString(1):"0");
						
					} else {
						actionForm.getAdvanceMetal(index).setLedgerEntryId("0");
						actionForm.getAdvanceMetal(index).setInventoryEntryId("0");
					}
					// add Cancellation Meatl Item
					stmtInsertAdvanceMetal.setInt(1, Integer.parseInt(actionForm.getSalesOrderCancellationReceiptId()));
					stmtInsertAdvanceMetal.setInt(2, Integer.parseInt(actionForm.getAdvanceMetal(index).getKeepInInvenory()));
					stmtInsertAdvanceMetal.setFloat(3, Float.parseFloat(actionForm.getAdvanceMetal(index).getWeight()));
					stmtInsertAdvanceMetal.setInt(4, Integer.parseInt(actionForm.getAdvanceMetal(index).getWeightUnitId()));
					stmtInsertAdvanceMetal.setFloat(5, Float.parseFloat(actionForm.getAdvanceMetal(index).getWastageRate()));
					stmtInsertAdvanceMetal.setInt(6, Integer.parseInt(actionForm.getAdvanceMetal(index).getWastageUnitId()));
					stmtInsertAdvanceMetal.setFloat(7, Float.parseFloat(actionForm.getAdvanceMetal(index).getNetWeight()));
					stmtInsertAdvanceMetal.setFloat(8, Float.parseFloat(actionForm.getAdvanceMetal(index).getRate()));
					stmtInsertAdvanceMetal.setFloat(9, Float.parseFloat(actionForm.getAdvanceMetal(index).getValue()));
					stmtInsertAdvanceMetal.setInt(10, Integer.parseInt(actionForm.getAdvanceMetal(index).getInventoryEntryId()));
					stmtInsertAdvanceMetal.setInt(11, Integer.parseInt(actionForm.getAdvanceMetal(index).getLedgerEntryId()));
					stmtInsertAdvanceMetal.execute();
					rs = stmtInsertAdvanceMetal.getGeneratedKeys();
					actionForm.getAdvanceMetal(index).setSalesOrderCancellationReceiptAdvanceMetalId(rs.next()?rs.getString(1): "0");
				} 

				// Unused Advance Gems
				for(int index=0; index < actionForm.getUnusedAdvanceGemList().size(); index++ ) {
					if (Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getKeepInInvenory()) == Default.CUSTOMER_INVENTORY_PURCHASE){ // do inventory adjustment entry
						totalItemsValue += Float.parseFloat(actionForm.getUnusedAdvanceGem(index).getValue());
						actionForm.getUnusedAdvanceGem(index).setLedgerEntryId("0");
/*						//Add Ledger Entry 
						stmtInsertLedgerEntry.setInt(1, Default.PURCHASE_IN_ACCOUNT_ID);
						stmtInsertLedgerEntry.setInt(2, Integer.parseInt(actionForm.getCustomerLedgerAccountId()));
						stmtInsertLedgerEntry.setString(3, purchaseVoucherForm.getEntryDate());
						stmtInsertLedgerEntry.setFloat(4, Float.parseFloat(actionForm.getUnusedAdvanceGem(index).getValue()));
						stmtInsertLedgerEntry.setString(5, purchaseVoucherForm.getVoucherPrefix() );
						stmtInsertLedgerEntry.setInt(6, Integer.parseInt(purchaseVoucherForm.getVoucherPostfix()));
						stmtInsertLedgerEntry.execute();
						rs = stmtInsertLedgerEntry.getGeneratedKeys();
						actionForm.getUnusedAdvanceGem(index).setLedgerEntryId(rs.next()?rs.getString(1):"0");
*/						
						//Add Inventroy Item Entry In
						stmtInsertInventoryGemEntry.setString(1, purchaseVoucherForm.getEntryDate());
						stmtInsertInventoryGemEntry.setInt(2, Integer.parseInt(purchaseVoucherForm.getInventoryAccountIdCompany()));
						stmtInsertInventoryGemEntry.setInt(3, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getItemId()));
						stmtInsertInventoryGemEntry.setInt(4, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getQuantity()));
						stmtInsertInventoryGemEntry.setFloat(5, Float.parseFloat(actionForm.getUnusedAdvanceGem(index).getWeight()));
						stmtInsertInventoryGemEntry.setInt(6, 1); // In / Out Status 1=In / 0=Out
						stmtInsertInventoryGemEntry.setString(7, purchaseVoucherForm.getVoucherPrefix());
						stmtInsertInventoryGemEntry.setInt(8, Integer.parseInt(purchaseVoucherForm.getVoucherPostfix()));
						stmtInsertInventoryGemEntry.execute();
						rs = stmtInsertInventoryGemEntry.getGeneratedKeys();
						actionForm.getUnusedAdvanceGem(index).setInventoryGemsEntryIdIn(rs.next()?rs.getString(1):"0");
						
						//Add Inventory Purchase Voucher Item  
						stmtInsertVoucherItem.setInt(1, Integer.parseInt(purchaseVoucherForm.getInventoryPurchaseVoucherId()));
						stmtInsertVoucherItem.setInt(2, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getItemId()));
						stmtInsertVoucherItem.setFloat(3, Float.parseFloat(actionForm.getUnusedAdvanceGem(index).getWeight()));
						stmtInsertVoucherItem.setInt(4, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getWeightUnitId()));
						stmtInsertVoucherItem.setInt(5, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getQuantity())); 
						stmtInsertVoucherItem.setFloat(6, Float.parseFloat(actionForm.getUnusedAdvanceGem(index).getRate()));
						stmtInsertVoucherItem.setInt(7, Default.VALUE_CALCULATE_BY_QUANTITY);
						stmtInsertVoucherItem.setFloat(8, Float.parseFloat(actionForm.getUnusedAdvanceGem(index).getValue()));
						stmtInsertVoucherItem.setInt(9, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getInventoryGemsEntryIdIn()));
						stmtInsertVoucherItem.setInt(10, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getLedgerEntryId()));
						stmtInsertVoucherItem.execute();
						rs = stmtInsertVoucherItem.getGeneratedKeys();
						actionForm.getUnusedAdvanceGem(index).setInventoryPurchaseVoucherItemId(rs.next()?rs.getString(1):"0");

//						stmtInsertLedgerEntry.clearParameters();
						stmtInsertInventoryGemEntry.clearParameters();
						stmtInsertVoucherItem.clearParameters();
					} else {
						actionForm.getUnusedAdvanceGem(index).setLedgerEntryId("0");
						actionForm.getUnusedAdvanceGem(index).setInventoryPurchaseVoucherItemId("0");
						actionForm.getUnusedAdvanceGem(index).setInventoryGemsEntryIdIn("0");
					}
					//add Advance unused gems to cancellation receipt 
					stmtInsertAdvanceUnusedGems.setInt(1, Integer.parseInt(actionForm.getSalesOrderCancellationReceiptId()));
					stmtInsertAdvanceUnusedGems.setInt(2, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getKeepInInvenory()));
					stmtInsertAdvanceUnusedGems.setInt(3, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getQuantity()));
					stmtInsertAdvanceUnusedGems.setFloat(4, Float.parseFloat(actionForm.getUnusedAdvanceGem(index).getWeight()));
					stmtInsertAdvanceUnusedGems.setInt(5, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getWeightUnitId()));
					stmtInsertAdvanceUnusedGems.setFloat(6, Float.parseFloat(actionForm.getUnusedAdvanceGem(index).getRate()));
					stmtInsertAdvanceUnusedGems.setFloat(7, Float.parseFloat(actionForm.getUnusedAdvanceGem(index).getValue()));
					stmtInsertAdvanceUnusedGems.setInt(8, Integer.parseInt(actionForm.getUnusedAdvanceGem(index).getInventoryPurchaseVoucherItemId()));
					stmtInsertAdvanceUnusedGems.setInt(9, Integer.parseInt(actionForm.getAdvanceGemInventoryPurchaseVoucherId()));
					stmtInsertAdvanceUnusedGems.execute();
					rs = stmtInsertAdvanceUnusedGems.getGeneratedKeys();
					actionForm.getUnusedAdvanceGem(index).setSalesOrderCancellationReceiptAdvanceGemId(rs.next()?rs.getString(1): "0");
				}

				// Used Advance Gems
				for(int index=0; index < actionForm.getUsedAdvanceGemList().size(); index++ ) {
					
					actionForm.getUsedAdvanceGem(index).setLedgerEntryId("0");
					actionForm.getUsedAdvanceGem(index).setInventoryPurchaseVoucherItemId("0");
					actionForm.getUsedAdvanceGem(index).setInventoryGemsEntryIdIn("0");
					actionForm.getUsedAdvanceGem(index).setInventoryGemsEntryIdOut("0");
					
					if (Integer.parseInt(actionForm.getUsedAdvanceGem(index).getKeepInInvenory()) == Default.CUSTOMER_INVENTORY_PURCHASE){
						totalItemsValue += Float.parseFloat(actionForm.getUsedAdvanceGem(index).getValue()); 
/*						//Add Ledger Entry 
						stmtInsertLedgerEntry.setInt(1, Default.PURCHASE_IN_ACCOUNT_ID);
						stmtInsertLedgerEntry.setInt(2, Integer.parseInt(actionForm.getCustomerLedgerAccountId()));
						stmtInsertLedgerEntry.setString(3, purchaseVoucherForm.getEntryDate());
						stmtInsertLedgerEntry.setFloat(4, Float.parseFloat(actionForm.getUsedAdvanceGem(index).getValue()));
						stmtInsertLedgerEntry.setString(5, purchaseVoucherForm.getVoucherPrefix() );
						stmtInsertLedgerEntry.setInt(6, Integer.parseInt(purchaseVoucherForm.getVoucherPostfix()));
						stmtInsertLedgerEntry.execute();
						rs = stmtInsertLedgerEntry.getGeneratedKeys();
						actionForm.getUsedAdvanceGem(index).setLedgerEntryId(rs.next()?rs.getString(1):"0");
*/						
						//Add Inventroy Item Entry In
						stmtInsertInventoryGemEntry.setString(1, purchaseVoucherForm.getEntryDate());
						stmtInsertInventoryGemEntry.setInt(2, Integer.parseInt(purchaseVoucherForm.getInventoryAccountIdCompany()));
						stmtInsertInventoryGemEntry.setInt(3, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getItemId()));
						stmtInsertInventoryGemEntry.setInt(4, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getQuantity()));
						stmtInsertInventoryGemEntry.setFloat(5, Float.parseFloat(actionForm.getUsedAdvanceGem(index).getWeight()));
						stmtInsertInventoryGemEntry.setInt(6, 1); // In / Out Status 1=In / 0=Out
						stmtInsertInventoryGemEntry.setString(7, purchaseVoucherForm.getVoucherPrefix());
						stmtInsertInventoryGemEntry.setInt(8, Integer.parseInt(purchaseVoucherForm.getVoucherPostfix()));
						stmtInsertInventoryGemEntry.execute();
						rs = stmtInsertInventoryGemEntry.getGeneratedKeys();
						actionForm.getUsedAdvanceGem(index).setInventoryGemsEntryIdIn(rs.next()?rs.getString(1):"0");
						
						//Add Inventory Purchase Voucher Item  
						stmtInsertVoucherItem.setInt(1, Integer.parseInt(purchaseVoucherForm.getInventoryPurchaseVoucherId()));
						stmtInsertVoucherItem.setInt(2, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getItemId()));
						stmtInsertVoucherItem.setFloat(3, Float.parseFloat(actionForm.getUsedAdvanceGem(index).getWeight()));
						stmtInsertVoucherItem.setInt(4, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getWeightUnitId()));
						stmtInsertVoucherItem.setInt(5, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getQuantity())); 
						stmtInsertVoucherItem.setFloat(6, Float.parseFloat(actionForm.getUsedAdvanceGem(index).getRate()));
						stmtInsertVoucherItem.setInt(7, Default.VALUE_CALCULATE_BY_QUANTITY);
						stmtInsertVoucherItem.setFloat(8, Float.parseFloat(actionForm.getUsedAdvanceGem(index).getValue()));
						stmtInsertVoucherItem.setInt(9, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getInventoryGemsEntryIdIn()));
						stmtInsertVoucherItem.setInt(10, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getLedgerEntryId()));
						stmtInsertVoucherItem.execute();
						rs = stmtInsertVoucherItem.getGeneratedKeys();
						actionForm.getUsedAdvanceGem(index).setInventoryPurchaseVoucherItemId(rs.next()?rs.getString(1):"0");

//						stmtInsertLedgerEntry.clearParameters();
						stmtInsertInventoryGemEntry.clearParameters();
						stmtInsertVoucherItem.clearParameters();	
						
					} else if (Integer.parseInt(actionForm.getUsedAdvanceGem(index).getKeepInInvenory()) == Default.CUSTOMER_INVENTORY_REPLACEMENT){
						//Add Inventroy Item Entry In
						stmtInsertInventoryGemEntry.setString(1, actionForm.getCancellationReceiptDate());
						stmtInsertInventoryGemEntry.setInt(2, Default.PURCHASE_IN_ACCOUNT_ID);
						stmtInsertInventoryGemEntry.setInt(3, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getItemId()));
						stmtInsertInventoryGemEntry.setInt(4, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getQuantity()));
						stmtInsertInventoryGemEntry.setFloat(5, Float.parseFloat(actionForm.getUsedAdvanceGem(index).getWeight()));
						stmtInsertInventoryGemEntry.setInt(6, 1); // In / Out Status 1=In / 0=Out
						stmtInsertInventoryGemEntry.setString(7, actionForm.getVoucherPrefix());
						stmtInsertInventoryGemEntry.setInt(8, Integer.parseInt(actionForm.getVoucherPostfix()));
						stmtInsertInventoryGemEntry.execute();
						rs = stmtInsertInventoryGemEntry.getGeneratedKeys();
						actionForm.getUsedAdvanceGem(index).setInventoryGemsEntryIdIn(rs.next()?rs.getString(1):"0");
						
						//Add Inventroy Item Entry Out
						stmtInsertInventoryGemEntry.setString(1, actionForm.getCancellationReceiptDate());
						stmtInsertInventoryGemEntry.setInt(2, Default.PURCHASE_OUT_ACCOUNT_ID);
						stmtInsertInventoryGemEntry.setInt(3, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getItemId()));
						stmtInsertInventoryGemEntry.setInt(4, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getQuantity()));
						stmtInsertInventoryGemEntry.setFloat(5, Float.parseFloat(actionForm.getUsedAdvanceGem(index).getWeight()));
						stmtInsertInventoryGemEntry.setInt(6, 0); // In / Out Status 1=In / 0=Out
						stmtInsertInventoryGemEntry.setString(7, actionForm.getVoucherPrefix());
						stmtInsertInventoryGemEntry.setInt(8, Integer.parseInt(actionForm.getVoucherPostfix()));
						stmtInsertInventoryGemEntry.execute();
						rs = stmtInsertInventoryGemEntry.getGeneratedKeys();
						actionForm.getUsedAdvanceGem(index).setInventoryGemsEntryIdOut(rs.next()?rs.getString(1):"0");
						
					}
					stmtInsertAdvanceUsedGems.setInt(1, Integer.parseInt(actionForm.getSalesOrderCancellationReceiptId()));
					stmtInsertAdvanceUsedGems.setInt(2, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getKeepInInvenory()));
					stmtInsertAdvanceUsedGems.setInt(3, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getQuantity()));
					stmtInsertAdvanceUsedGems.setFloat(4, Float.parseFloat(actionForm.getUsedAdvanceGem(index).getWeight()));
					stmtInsertAdvanceUsedGems.setInt(5, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getWeightUnitId()));
					stmtInsertAdvanceUsedGems.setFloat(6, Float.parseFloat(actionForm.getUsedAdvanceGem(index).getRate()));
					stmtInsertAdvanceUsedGems.setFloat(7, Float.parseFloat(actionForm.getUsedAdvanceGem(index).getValue()));
					stmtInsertAdvanceUsedGems.setInt(8, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getInventoryPurchaseVoucherItemId()));
					stmtInsertAdvanceUsedGems.setInt(9, Integer.parseInt(actionForm.getAdvanceGemInventoryPurchaseVoucherId()));
					stmtInsertAdvanceUsedGems.setInt(10, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getInventoryGemsEntryIdIn()));
					stmtInsertAdvanceUsedGems.setInt(11, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getInventoryGemsEntryIdOut()));
					stmtInsertAdvanceUsedGems.setInt(12, Integer.parseInt(actionForm.getUsedAdvanceGem(index).getLedgerEntryId()));
					stmtInsertAdvanceUsedGems.execute();
					rs = stmtInsertAdvanceUsedGems.getGeneratedKeys();
					actionForm.getUsedAdvanceGem(index).setSalesOrderCancellationReceiptAdvanceGemId(rs.next()?rs.getString(1): "0");
				}
				
				// Add Cash Voucher
				String cashVoucherPostfix ;
				
				rs = stmt.executeQuery("SELECT voucher_postfix, cash_voucher_id FROM cash_vouchers WHERE VOUCHER_PREFIX='"+Default.CASH_BOOK_PAYMENT_VOUCHER_PREFIX+"' AND LEDGER_ACCOUNT_ID='"+Default.LEDGER_CASH_ACCOUNT_ID+"' AND ENTRY_DATE='"+actionForm.getCancellationReceiptDate()+"'"  );
				if (rs.next()) {
					cashVoucherPostfix = rs.getString(1);
					actionForm.setCashVoucherId(rs.getString(2));
				} else {
					rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM cash_vouchers WHERE voucher_prefix='"+Default.CASH_BOOK_PAYMENT_VOUCHER_PREFIX+"'");
					cashVoucherPostfix = (rs.next()?rs.getString(1):"0");	
					stmt.execute(" INSERT INTO cash_vouchers (entry_date, voucher_prefix, voucher_postfix, ledger_account_id)"
								+" VALUES ("
								+" '"+actionForm.getCancellationReceiptDate()+"'"
								+",'"+Default.CASH_BOOK_PAYMENT_VOUCHER_PREFIX+"'"
								+", "+cashVoucherPostfix
								+", "+Default.LEDGER_CASH_ACCOUNT_ID
								+" )"
					);
					rs = stmt.getGeneratedKeys();
					actionForm.setCashVoucherId(rs.next()?rs.getString(1):"0");
				}
				
				// Add Ledger Entries & Cash Voucher Enteries
				String narration = "Voucher # "+actionForm.getVoucherPrefix()+"-"+actionForm.getVoucherPostfix()+" "+actionForm.getRemarks();
				//Add Ledger Entry 
				stmtInsertLedgerEntry.setInt(1,Default.PURCHASE_IN_ACCOUNT_ID);
				stmtInsertLedgerEntry.setInt(2, Default.LEDGER_CASH_ACCOUNT_ID);
				stmtInsertLedgerEntry.setString(3,actionForm.getCancellationReceiptDate());
				stmtInsertLedgerEntry.setFloat(4, totalItemsValue);
				stmtInsertLedgerEntry.setString(5, Default.CASH_BOOK_PAYMENT_VOUCHER_PREFIX);
				stmtInsertLedgerEntry.setInt(6, Integer.parseInt(cashVoucherPostfix));
				stmtInsertLedgerEntry.setString(7, narration);
				stmtInsertLedgerEntry.execute();
				rs = stmtInsertLedgerEntry.getGeneratedKeys();
				actionForm.setPurchaseLedgerEntryId(rs.next()?rs.getString(1):"0");
				//Add Cash Voucher Entry 
				stmtInsertVoucherEntry.setInt(1, Integer.parseInt(actionForm.getCashVoucherId()));
				stmtInsertVoucherEntry.setInt(2, Default.PURCHASE_IN_ACCOUNT_ID);
				stmtInsertVoucherEntry.setString(3, narration);
				stmtInsertVoucherEntry.setFloat(4, totalItemsValue);
				stmtInsertVoucherEntry.setInt(5,Integer.parseInt(actionForm.getPurchaseLedgerEntryId()));
				stmtInsertVoucherEntry.execute();
				rs = stmtInsertVoucherEntry.getGeneratedKeys();
				actionForm.setCashVoucherEntryId(rs.next()?rs.getString(1):"0");

				stmtInsertLedgerEntry.clearParameters();
				stmtInsertVoucherEntry.clearParameters();
				
				//Update Cash Voucher, Entry and Ledger Entry Ids in Inventory Purchase Voucher
				stmt.execute(
					 " UPDATE inventory_purchase_vouchers SET "
					+" LEDGER_ENTRY_ID='"+actionForm.getPurchaseLedgerEntryId()+"'"
					+",CASH_VOUCHER_ID='"+actionForm.getCashVoucherId()+"'"
					+",CASH_VOUCHER_ENTRY_ID=' "+actionForm.getCashVoucherEntryId()+"'"
					+" WHERE INVENTORY_PURCHASE_VOUCHER_ID='"+actionForm.getAdvanceGemInventoryPurchaseVoucherId()+"'"
				);
				
				stmt.execute(
						 " UPDATE sales_order_cancellation_receipts SET "
						+" CACELLATION_CHARGES_LEDGER_ENTRY_ID ='"+actionForm.getCancellationChargesLedgerEntryId()+"'"
						+",ADVANCE_GEM_INVENTORY_PURCHASE_VOUCHER_ID='"+actionForm.getAdvanceGemInventoryPurchaseVoucherId()+"'"
						+" WHERE SALES_ORDER_CANCELLATION_RECEIPT_ID='"+actionForm.getSalesOrderCancellationReceiptId()+"'"
						);
				
				stmt.execute(
						 " UPDATE sales_orders SET "
						+" SALES_ORDER_STATUS_ID ='"+Default.SALES_ORDER_STATUS_CANCEL+"'"
						+" WHERE SALES_ORDER_ID='"+actionForm.getSalesOrderId()+"'"
						);	

				stmt.execute(
						 " UPDATE sales_order_item_info SET "
						+" ORDER_ITEM_CANCEL=1"
						+" WHERE SALES_ORDER_ID='"+actionForm.getSalesOrderId()+"'"
						);	

				connection.commit();
				connection.close();
			} catch (Exception e){
				if (connection != null) {
					try {connection.rollback();}catch (SQLException sqle){}
					ActionMessages serviceErrors = new ActionMessages();
					serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
					saveErrors(request,serviceErrors);
				}
				throw e;
			}
		}
		actionForm.setCustomerLedgerAccountId("0");
		((SalesOrderCancellationReceiptForm) form ).setCustomerLedgerAccountId("");
		actionForm.setSalesOrderId(""); 
		
		return (mapping.findForward("SUCCESS"));
	}

}
