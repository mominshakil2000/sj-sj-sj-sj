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
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netxs.Zewar.Default;
import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Struts.Form.SalesOrderForm;
import com.netxs.Zewar.Struts.Form.SalesOrderItemEstimatedMetalForm;
import com.netxs.Zewar.Struts.Form.SalesOrderItemEstimatedGemForm;
import com.netxs.Zewar.Struts.Form.SalesOrderItemForm;
import com.netxs.Zewar.Struts.Form.SalesOrderItemInfoForm;
import com.netxs.Zewar.Struts.Form.SalesOrderAdvanceMetalForm;
import com.netxs.Zewar.Struts.Form.SalesOrderAdvanceGemForm;
import com.netxs.Zewar.Struts.Form.InventoryPurchaseVoucherForm;
import com.netxs.Zewar.Struts.Form.InventoryPurchaseVoucherItemForm;

public class SalesOrderReOpenCreateAction extends Action {
 
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SalesOrderForm actionForm = (SalesOrderForm) form;
		
		Connection connection;
		connection = (Connection) new DBConnection().getMyPooledConnection();
		connection.setAutoCommit(false);
		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
		Statement stmt2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
		Statement stmt3 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
		Statement stmt4 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);

		ResultSet rs, rs1, rs2, rs3, rs4;
	
		if(actionForm.getHasFormInitialized()!= 'Y') {
			try {
				//Inititalize form from database or initialize with default value
				rs = stmt.executeQuery("SELECT * FROM SALES_ORDERS WHERE SALES_ORDER_ID='"+actionForm.getCloneFromSalesOrderId()+"' AND SALES_ORDER_STATUS_ID = "+Default.SALES_ORDER_STATUS_CANCEL+" AND SALES_ORDER_STATUS_ID != "+Default.SALES_ORDER_STATUS_COMPLETE+" AND CANCEL_ORDER_REOPEN_STATUS=0");
				if (rs.next()) {
					actionForm.setInsertable(true);
					actionForm.setSalesOrderTrackingId("");
					actionForm.setCustomerLedgerAccountId(rs.getString("CUSTOMER_LEDGER_ACCOUNT_ID"));
					actionForm.setOrderPostByUserId("0");
					actionForm.setOrderDate(rs.getString("ORDER_DATE"));
					actionForm.setEstimatedDeliveryDate(rs.getString("ESTIMATED_DELIVERY_DATE"));
					actionForm.setAdvanceCash("0.00");
					actionForm.setDescription(rs.getString("DESCRIPTION"));
					actionForm.setComments(rs.getString("COMMENTS"));
					actionForm.setSalesOrderStatusId("0");
					actionForm.setOrderCreated((byte)0);
					actionForm.setAdvanceCashCashBookVoucherId("0");
					actionForm.setAdvanceCashCashBookVoucherEntryId("0");
					actionForm.setAdvanceCashLedgerEntryId("0");
								
					
					//load Sales Order Items
					actionForm.getOrderItemList().clear();
					rs1 = stmt.executeQuery("SELECT OI.JEWELLERY_ITEM_ID, II.* FROM sales_order_items OI INNER JOIN sales_order_item_info II ON OI.SALES_ORDER_ITEM_ID = II.SALES_ORDER_ITEM_ID WHERE II.ORDER_ITEM_CANCEL=1 AND II.SALES_ORDER_ID="+actionForm.getCloneFromSalesOrderId()+" ORDER BY SALES_ORDER_ITEM_ID" );
					if (rs1.next()) {

						SalesOrderItemForm orderItem;
						SalesOrderItemInfoForm orderItemInfo;
						SalesOrderItemEstimatedMetalForm estimatedMetal;
						SalesOrderItemEstimatedGemForm estimatedGem;
						rs3 = stmt3.executeQuery("SELECT * FROM sales_order_item_info_estimated_metals WHERE sales_order_id="+actionForm.getCloneFromSalesOrderId()+" ORDER BY SALES_ORDER_ITEM_ID, SALES_ORDER_ITEM_INFO_ID");
						rs4 = stmt4.executeQuery("SELECT * FROM sales_order_item_info_estimated_gems WHERE sales_order_id="+actionForm.getCloneFromSalesOrderId()+" ORDER BY SALES_ORDER_ID, SALES_ORDER_ITEM_ID, SALES_ORDER_ITEM_INFO_ID");
	
						do {
							orderItem = new SalesOrderItemForm();
							orderItem.setInsertable(false);
							orderItem.setSalesOrderItemId(rs1.getString("SALES_ORDER_ITEM_ID"));
							orderItem.setJewelleryItemId(rs1.getString("JEWELLERY_ITEM_ID"));
							
							orderItemInfo = new SalesOrderItemInfoForm();
							orderItemInfo.setInsertable(true);
							orderItemInfo.setSalesOrderItemInfoId("0");
							orderItemInfo.setOrderItemCancel((byte)0);
							orderItemInfo.setLumpSumLabourCharges(rs1.getString("LUMP_SUM_LABOUR_CHARGES"));
							orderItemInfo.setComments(rs1.getString("COMMENTS").replaceAll("\r", "" ).replaceAll("\n", "\\\\n" ));
							orderItemInfo.setBodyMakingRateTypeId(rs1.getString("STONE_SETTING_RATE_TYPE_ID"));
							orderItemInfo.setStoneSettingRateTypeId(rs1.getString("STONE_SETTING_RATE_TYPE_ID"));
							while (rs3.next() && rs3.getString("SALES_ORDER_ITEM_INFO_ID").equals(rs1.getString("SALES_ORDER_ITEM_INFO_ID")) ){
								estimatedMetal = new SalesOrderItemEstimatedMetalForm();
								estimatedMetal.setInsertable(true);
								estimatedMetal.setSalesOrderItemInfoEstimatedMetalId("0");							
								estimatedMetal.setItemId(rs3.getString("ITEM_ID"));
								estimatedMetal.setItemWeight(rs3.getString("ITEM_WEIGHT"));
								estimatedMetal.setItemWeightUnitId(rs3.getString("ITEM_WEIGHT_UNIT_ID"));
								estimatedMetal.setItemRate(rs3.getString("ITEM_RATE"));
								estimatedMetal.setItemWastageRate(rs3.getString("ITEM_WASTAGE_RATE"));
								estimatedMetal.setItemWastageRateUnitId(rs3.getString("ITEM_WASTAGE_RATE_UNIT_ID"));
								estimatedMetal.setItemNetWeight(rs3.getString("ITEM_NET_WEIGHT"));
								estimatedMetal.setItemValue(rs3.getString("ITEM_VALUE"));
								estimatedMetal.setComments(rs3.getString("COMMENTS").replaceAll("\r", "" ).replaceAll("\n", "\\\\n" ));
								orderItemInfo.setEstimatedMetal(estimatedMetal);
							}
							rs3.previous();
							
							while (rs4.next() && rs4.getString("SALES_ORDER_ITEM_INFO_ID").equals(rs1.getString("SALES_ORDER_ITEM_INFO_ID")) ){
								estimatedGem = new SalesOrderItemEstimatedGemForm();
								estimatedGem.setInsertable(true);
								estimatedGem.setSalesOrderItemInfoEstimatedGemId("0");
								estimatedGem.setItemId(rs4.getString("ITEM_ID"));
								estimatedGem.setItemQuantity(rs4.getString("ITEM_QUANTITY"));
								estimatedGem.setItemWeight(rs4.getString("ITEM_WEIGHT"));
								estimatedGem.setItemWeightUnitId(rs4.getString("ITEM_WEIGHT_UNIT_ID"));
								estimatedGem.setItemRate(rs4.getString("ITEM_RATE"));
								estimatedGem.setComments(rs4.getString("COMMENTS").replaceAll("\r", "" ).replaceAll("\n", "\\\\n" ));
								estimatedGem.setItemValue(rs4.getString("ITEM_VALUE"));
								estimatedGem.setItemValueCalculateBy(rs4.getString("ITEM_VALUE_CALCULATE_BY"));
								orderItemInfo.setEstimatedGem(estimatedGem);
							}
							rs4.previous();
							orderItem.setOrderItemInfo(orderItemInfo);
							
							actionForm.setOrderItem(orderItem);
						} while (rs1.next());
						rs.close();
						rs1.close();
						rs3.close();
						rs4.close();
					}
				} else { // not a valid Order
					ActionMessages serviceErrors = new ActionMessages();
					serviceErrors.add("error",new ActionMessage("errors","Invalid order status. Can not re-open."));
					saveErrors(request,serviceErrors);
					return mapping.findForward("SUCCESS");
				}
				actionForm.setHasFormInitialized('Y');
				return (mapping.findForward("FAIL"));
			}catch (Exception e) {
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return mapping.findForward("FAIL");
			}
			
		} else {
			try {
				if(actionForm.getOrderCreated()==1 && actionForm.getSalesOrderStatusId().equals("0")){
					rs = stmt.executeQuery("SELECT IFNULL(MAX(SALES_ORDER_TRACKING_ID),0)+1 FROM SALES_ORDERS ");
					actionForm.setSalesOrderTrackingId(rs.next()?rs.getString(1):"");
					actionForm.setSalesOrderStatusId("1");
					try {
						DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).parse(actionForm.getOrderDate());
					} catch (Exception e) {
						throw new Exception("Invalid Sales Order Date.");
					}

					/* Start - disable auto advance legder account entry 
					// add advance cash payment receive voucher entry
					int cashVoucherPostfix = 0;
					rs = stmt.executeQuery("SELECT CASH_VOUCHER_ID, VOUCHER_POSTFIX   FROM cash_vouchers WHERE entry_date='"+actionForm.getOrderDate()+"' AND VOUCHER_PREFIX='"+Default.CASH_BOOK_RECEIVE_VOUCHER_PREFIX+"' AND LEDGER_ACCOUNT_ID="+Default.LEDGER_CASH_ACCOUNT_ID);
					if(rs.next()) { // cash book receive voucher of today is present
						actionForm.setAdvanceCashCashBookVoucherId(rs.getString("CASH_VOUCHER_ID"));
						cashVoucherPostfix = rs.getInt("VOUCHER_POSTFIX");
					} else { // add new cash receive voucher of today
	 					rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM cash_vouchers WHERE voucher_prefix='"+Default.CASH_BOOK_RECEIVE_VOUCHER_PREFIX+"'");
						cashVoucherPostfix = (rs.next()?rs.getInt(1):0);
						stmt.execute(
							 " INSERT INTO cash_vouchers (ENTRY_DATE, voucher_prefix, voucher_postfix, ledger_account_id)"
							+" VALUES ("
							+"  '"+actionForm.getOrderDate()
							+"','"+Default.CASH_BOOK_RECEIVE_VOUCHER_PREFIX
							+"', "+cashVoucherPostfix
							+" , "+Default.LEDGER_CASH_ACCOUNT_ID
							+" )"
						);
						rs = stmt.getGeneratedKeys();
						actionForm.setAdvanceCashCashBookVoucherId(rs.next()?rs.getString(1):"0");
					}
					
					stmt.execute("INSERT INTO ledger_entries "
							+" (ledger_account_id_debit, ledger_account_id_credit, ENTRY_DATE, amount, voucher_prefix, voucher_postfix)"
							+" VALUES( " 
							      +Default.LEDGER_CASH_ACCOUNT_ID
							+" , "+actionForm.getCustomerLedgerAccountId() 
							+" ,'"+actionForm.getOrderDate() 
							+"','"+actionForm.getAdvanceCash() 
							+"','"+Default.CASH_BOOK_RECEIVE_VOUCHER_PREFIX 
							+"', "+cashVoucherPostfix+")");
					rs = stmt.getGeneratedKeys();
					actionForm.setAdvanceCashLedgerEntryId(rs.next()?rs.getString(1):"0");
					
					 stmt.execute("INSERT INTO cash_voucher_entries " 
							+"(cash_voucher_id, ledger_account_id, narration, amount, ledger_entry_id)	" 
							+" VALUES("
								  +actionForm.getAdvanceCashCashBookVoucherId()
							+" , "+actionForm.getCustomerLedgerAccountId() 
							+" ,'"+"Customer Advance Cash. Reference: Sales Order Tracking # "+actionForm.getSalesOrderTrackingId()
							+"','"+actionForm.getAdvanceCash() 
							+"','"+actionForm.getAdvanceCashLedgerEntryId()+"')");
					rs = stmt.getGeneratedKeys(); 
					actionForm.setAdvanceCashCashBookVoucherEntryId(rs.next()?rs.getString(1):"0");
					* END - disable auto advance metal legder account entry */
					
					actionForm.setDeleteAdvanceMetal(actionForm.getDeleteAdvanceMetal().replaceFirst("0,","")); //for fixing bug server error 124
					
					// delete cash voucher entries, ledger entries and relations
					/* Start - disable auto advance metal legder account entry
					stmt.execute("DELETE FROM ledger_entries WHERE ledger_entry_id IN (select ledger_entry_id FROM sales_order_advance_metals WHERE sales_order_advance_metal_id IN ("+actionForm.getDeleteAdvanceMetal()+"))");
					* Start - disable auto advance metal legder account entry */
					stmt.execute("DELETE FROM inventory_purchase_vouchers WHERE inventory_purchase_voucher_id IN ( select INVENTORY_PURCHASE_VOUCHER_ID FROM sales_order_advance_metals WHERE sales_order_advance_metal_id IN ("+actionForm.getDeleteAdvanceMetal()+"))");
					stmt.execute("DELETE FROM inventory_purchase_voucher_items WHERE inventory_purchase_voucher_item_id IN ( select INVENTORY_PURCHASE_VOUCHER_ITEM_ID FROM sales_order_advance_metals WHERE sales_order_advance_metal_id IN ("+actionForm.getDeleteAdvanceMetal()+"))");
					stmt.execute("DELETE FROM inventory_metal_entries WHERE inventory_metal_entry_id IN (select INVENTORY_METAL_ENTRY_ID FROM sales_order_advance_metals WHERE sales_order_advance_metal_id IN ("+actionForm.getDeleteAdvanceMetal()+"))");

					//Add/Update Inventory Entries & Voucher Items
					PreparedStatement stmtUpdateVoucherItem = connection.prepareStatement("UPDATE inventory_purchase_voucher_items SET  item_id=?,	weight=?, weight_unit_id=?, quantity=?, rate=?, item_value_calculate_by=?, item_value=? WHERE inventory_purchase_voucher_item_id=?");
					PreparedStatement stmtInsertVoucherItem = connection.prepareStatement("INSERT INTO inventory_purchase_voucher_items ( inventory_purchase_voucher_id, item_id, weight, weight_unit_id, quantity, rate, item_value_calculate_by, item_value, inventory_item_entry_id_in, LEDGER_ENTRY_ID)	VALUES(?,?,?,?,?,?,?,?,?,?)");
					PreparedStatement stmtInsertInventoryItemEntry = connection.prepareStatement("INSERT INTO inventory_metal_entries (ENTRY_DATE,	inventory_account_id, item_id, weight, in_out_status, voucher_prefix, voucher_postfix )	VALUES(?,?,?,?,?,?,?)");
					PreparedStatement stmtUpdateInventoryItemEntry = connection.prepareStatement("UPDATE inventory_metal_entries SET ENTRY_DATE=?, inventory_account_id=?, item_id=?, weight=?  WHERE inventory_metal_entry_id=?");
					
					/* Start - disable auto advance metal legder account entry
					PreparedStatement stmtInsertLedgerEntry = connection.prepareStatement("INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, ENTRY_DATE, amount, voucher_prefix, voucher_postfix)	VALUES(?, ?, ?, ?, ?, ?)");
					PreparedStatement stmtUpdateLedgerEntry = connection.prepareStatement("UPDATE ledger_entries SET ledger_account_id_debit=?, ledger_account_id_credit=?, ENTRY_DATE=?, amount=? WHERE ledger_entry_id=?");
					* END - disable auto advance metal legder account entry*/
					
					//Advance Purchase Metal Voucher
					InventoryPurchaseVoucherForm purchaseVoucherForm = new InventoryPurchaseVoucherForm ();
					purchaseVoucherForm.setVoucherPrefix(Default.INVENTORY_PURCHASE_METAL_VOUCHER_PREFIX);
					purchaseVoucherForm.setInventoryAccountIdCompany(Integer.toString(Default.PURCHASE_IN_ACCOUNT_ID));
					purchaseVoucherForm.setComments("Customer Advance Metal. Reference: Sales Order Tracking # "+actionForm.getSalesOrderTrackingId());
					purchaseVoucherForm.setItemGroupId("1");
					
					for (int index=0; index < actionForm.getAdvanceMetalList().size(); index++){
						purchaseVoucherForm.setEntryDate(actionForm.getAdvanceMetal(index).getEntryDate());
						purchaseVoucherForm.setInventoryPurchaseVoucherId(actionForm.getAdvanceMetal(index).getInventoryPurchaseVoucherId());
						rs = stmt.executeQuery("SELECT * FROM inventory_purchase_vouchers WHERE inventory_purchase_voucher_id="+purchaseVoucherForm.getInventoryPurchaseVoucherId());
						if (rs.next()) {
							purchaseVoucherForm.setVoucherPostfix(rs.getString("VOUCHER_POSTFIX"));
							stmt.execute(
									 " UPDATE inventory_purchase_vouchers SET" 
									+"  ENTRY_DATE='"+purchaseVoucherForm.getEntryDate()
									+"' WHERE inventory_purchase_voucher_id="+purchaseVoucherForm.getInventoryPurchaseVoucherId()
							);
							
						} else {
							rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM inventory_purchase_vouchers WHERE voucher_prefix='"+purchaseVoucherForm.getVoucherPrefix()+"'");
							purchaseVoucherForm.setVoucherPostfix(rs.next()?rs.getString(1):"0");
							
							stmt.execute(
								 " INSERT INTO inventory_purchase_vouchers (ENTRY_DATE, voucher_prefix, voucher_postfix, inventory_account_id_in, COMMENTS, item_group_id)"
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
							purchaseVoucherForm.setInventoryPurchaseVoucherId(rs.next()? rs.getString(1) : "0");
							actionForm.getAdvanceMetal(index).setInventoryPurchaseVoucherId(purchaseVoucherForm.getInventoryPurchaseVoucherId());
							
						}
						
						
						if(actionForm.getAdvanceMetal(index).isInsertablePurchaseItem()) {
							/* Start - disable auto advance metal legder account entry
							//	Add Ledger Entry
							stmtInsertLedgerEntry.setInt(1, Default.PURCHASE_IN_ACCOUNT_ID);
							stmtInsertLedgerEntry.setInt(2,  Integer.parseInt(actionForm.getCustomerLedgerAccountId()));
							stmtInsertLedgerEntry.setString(3, purchaseVoucherForm.getEntryDate());
							stmtInsertLedgerEntry.setFloat(4, Float.parseFloat(actionForm.getAdvanceMetal(index).getItemValue()));
							stmtInsertLedgerEntry.setString(5, purchaseVoucherForm.getVoucherPrefix() );
							stmtInsertLedgerEntry.setInt(6, Integer.parseInt(purchaseVoucherForm.getVoucherPostfix()));
							stmtInsertLedgerEntry.execute();
							rs = stmtInsertLedgerEntry.getGeneratedKeys();
							actionForm.getAdvanceMetal(index).setLedgerEntryId(rs.next()?rs.getString(1):"0");
							* Start - disable auto advance metal legder account entry*/
							
							//Add Inventroy Item Entry In
							stmtInsertInventoryItemEntry.setString(1, purchaseVoucherForm.getEntryDate());
							stmtInsertInventoryItemEntry.setInt(2, Integer.parseInt(purchaseVoucherForm.getInventoryAccountIdCompany()));
							stmtInsertInventoryItemEntry.setInt(3, Integer.parseInt(actionForm.getAdvanceMetal(index).getItemId()));
							stmtInsertInventoryItemEntry.setFloat(4, Float.parseFloat(actionForm.getAdvanceMetal(index).getItemWeight()));
							stmtInsertInventoryItemEntry.setInt(5, 1); // In / Out Status 1=In / 0=Out
							stmtInsertInventoryItemEntry.setString(6, purchaseVoucherForm.getVoucherPrefix());
							stmtInsertInventoryItemEntry.setInt(7, Integer.parseInt(purchaseVoucherForm.getVoucherPostfix()));
							stmtInsertInventoryItemEntry.execute();
							rs = stmtInsertInventoryItemEntry.getGeneratedKeys();
							actionForm.getAdvanceMetal(index).setInventoryMetalEntryIdIn(rs.next()?rs.getString(1):"0");
							
							//Add Inventory Purchase Voucher Item  
							stmtInsertVoucherItem.setInt(1, Integer.parseInt(purchaseVoucherForm.getInventoryPurchaseVoucherId()));
							stmtInsertVoucherItem.setInt(2, Integer.parseInt(actionForm.getAdvanceMetal(index).getItemId()));
							stmtInsertVoucherItem.setFloat(3, Float.parseFloat(actionForm.getAdvanceMetal(index).getItemWeight()));
							stmtInsertVoucherItem.setInt(4, Integer.parseInt(actionForm.getAdvanceMetal(index).getItemWeightUnitId()));
							stmtInsertVoucherItem.setInt(5, 0); //Quanitity
							stmtInsertVoucherItem.setFloat(6, Float.parseFloat(actionForm.getAdvanceMetal(index).getItemRate()));
							stmtInsertVoucherItem.setInt(7, Default.VALUE_CALCULATE_BY_QUANTITY);
							stmtInsertVoucherItem.setFloat(8, Float.parseFloat(actionForm.getAdvanceMetal(index).getItemValue()));
							stmtInsertVoucherItem.setInt(9, Integer.parseInt(actionForm.getAdvanceMetal(index).getInventoryMetalEntryIdIn()));
							stmtInsertVoucherItem.setInt(10, Integer.parseInt(actionForm.getAdvanceMetal(index).getLedgerEntryId()));
							stmtInsertVoucherItem.execute();
							rs = stmtInsertVoucherItem.getGeneratedKeys();
							actionForm.getAdvanceMetal(index).setInventoryPurchaseVoucherItemId(rs.next()?rs.getString(1):"0");
							/* Start - disable auto advance metal legder account entry
							stmtInsertLedgerEntry.clearParameters();
							 * END - disable auto advance metal legder account entry */
							stmtInsertInventoryItemEntry.clearParameters();
							stmtInsertVoucherItem.clearParameters();
							
						} else {
							/* Start - disable auto advance metal legder account entry
							//	Update Ledger Entry
							stmtUpdateLedgerEntry.setInt(1, Default.PURCHASE_IN_ACCOUNT_ID);
							stmtUpdateLedgerEntry.setInt(2, Integer.parseInt(actionForm.getCustomerLedgerAccountId()));
							stmtUpdateLedgerEntry.setString(3,purchaseVoucherForm.getEntryDate());
							stmtUpdateLedgerEntry.setFloat(4,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemValue()));
							stmtUpdateLedgerEntry.setInt(5, Integer.parseInt(actionForm.getAdvanceMetal(index).getLedgerEntryId()));
							stmtUpdateLedgerEntry.execute();
							 END - disable auto advance metal legder account entry */

							//Update Inventroy Item Entry In
							//ENTRY_DATE=?,	inventory_account_id=?, item_id=?, weight=?
							stmtUpdateInventoryItemEntry.setString(1, purchaseVoucherForm.getEntryDate());
							stmtUpdateInventoryItemEntry.setInt(2, Default.PURCHASE_IN_ACCOUNT_ID);
							stmtUpdateInventoryItemEntry.setInt(3, Integer.parseInt(actionForm.getAdvanceMetal(index).getItemId()));
							stmtUpdateInventoryItemEntry.setFloat(4, Float.parseFloat(actionForm.getAdvanceMetal(index).getItemWeight()));
							stmtUpdateInventoryItemEntry.setInt(5, Integer.parseInt(actionForm.getAdvanceMetal(index).getInventoryMetalEntryIdIn()));
							stmtUpdateInventoryItemEntry.execute();
							
							//Update Inventory Purchase Voucher Item 
							//item_id, weight, weight_unit_id, quantity, rate, item_value_calculate_by, item_value
							stmtUpdateVoucherItem.setInt(1, Integer.parseInt(actionForm.getAdvanceMetal(index).getItemId()));
							stmtUpdateVoucherItem.setFloat(2, Float.parseFloat(actionForm.getAdvanceMetal(index).getItemWeight()));
							stmtUpdateVoucherItem.setInt(3, Integer.parseInt(actionForm.getAdvanceMetal(index).getItemWeightUnitId()));
							stmtUpdateVoucherItem.setInt(4, 0); // quantity
							stmtUpdateVoucherItem.setFloat(5, Float.parseFloat(actionForm.getAdvanceMetal(index).getItemRate()));
							stmtUpdateVoucherItem.setInt(6, Default.VALUE_CALCULATE_BY_QUANTITY);
							stmtUpdateVoucherItem.setFloat(7, Float.parseFloat(actionForm.getAdvanceMetal(index).getItemValue()));
							stmtUpdateVoucherItem.setInt(8, Integer.parseInt(actionForm.getAdvanceMetal(index).getInventoryPurchaseVoucherItemId()));
							stmtUpdateVoucherItem.execute();
							/* Start - disable auto advance metal legder account entry
							stmtUpdateLedgerEntry.clearParameters();
							* END - disable auto advance metal legder account entry */
							stmtUpdateInventoryItemEntry.clearParameters();
							stmtUpdateVoucherItem.clearParameters();
						}
					}
					//Cleanup resources
					stmtInsertInventoryItemEntry.close();
					stmtInsertVoucherItem.close();
					stmtUpdateInventoryItemEntry.close();
					stmtUpdateVoucherItem.close();
				}
				if (actionForm.isInsertable()) {
					stmt.executeUpdate(
					     " INSERT INTO SALES_ORDERS (CUSTOMER_LEDGER_ACCOUNT_ID, SALES_ORDER_TRACKING_ID, ORDER_POST_BY_USER_ID, ORDER_DATE, ESTIMATED_DELIVERY_DATE, ADVANCE_CASH, DESCRIPTION, COMMENTS, ORDER_CREATED, SALES_ORDER_STATUS_ID, ADVANCE_CASH_CASH_BOOK_VOUCHER_ID, ADVANCE_CASH_CASH_BOOK_VOUCHER_ENTRY_ID, ADVANCE_CASH_LEDGER_ENTRY_ID ) VALUES ( "
						      +actionForm.getCustomerLedgerAccountId()
						+" ,'"+actionForm.getSalesOrderTrackingId()
						+"','"+actionForm.getOrderPostByUserId()
						+"','"+actionForm.getOrderDate()
						+"','"+actionForm.getEstimatedDeliveryDate()
						+"','"+actionForm.getAdvanceCash()
						+"','"+actionForm.getDescription()
						+"','"+actionForm.getComments()
						+"','"+actionForm.getOrderCreated()
						+"','"+actionForm.getSalesOrderStatusId()
						+"','"+actionForm.getAdvanceCashCashBookVoucherId()
						+"','"+actionForm.getAdvanceCashCashBookVoucherEntryId()
						+"','"+actionForm.getAdvanceCashLedgerEntryId()
						+"') " 
					);
					rs = stmt.getGeneratedKeys();
					actionForm.setSalesOrderId(rs.next()?rs.getString(1):"0");
					actionForm.setInsertable(false);
					
				} else {
					
					stmt.executeUpdate(
							 " UPDATE sales_orders  SET "
							+"  CUSTOMER_LEDGER_ACCOUNT_ID='"+actionForm.getCustomerLedgerAccountId()
							+"',SALES_ORDER_TRACKING_ID='"+actionForm.getSalesOrderTrackingId()
							+"',ORDER_POST_BY_USER_ID='"+actionForm.getOrderPostByUserId()
							+"',ORDER_DATE='"+actionForm.getOrderDate()
							+"',ESTIMATED_DELIVERY_DATE='"+actionForm.getEstimatedDeliveryDate()
							+"',ADVANCE_CASH='"+actionForm.getAdvanceCash()
							+"',DESCRIPTION='"+actionForm.getDescription()
							+"',COMMENTS='"+actionForm.getComments()
							+"',ORDER_CREATED='"+actionForm.getOrderCreated()
							+"',SALES_ORDER_STATUS_ID='"+actionForm.getSalesOrderStatusId()
							+"',ADVANCE_CASH_CASH_BOOK_VOUCHER_ID='"+actionForm.getAdvanceCashCashBookVoucherId()
							+"',ADVANCE_CASH_CASH_BOOK_VOUCHER_ENTRY_ID='"+actionForm.getAdvanceCashCashBookVoucherEntryId()
							+"',ADVANCE_CASH_LEDGER_ENTRY_ID='"+actionForm.getAdvanceCashLedgerEntryId()
							+"',CANCEL_ORDER_REOPEN_STATUS='1"
							+"' WHERE SALES_ORDER_ID="+actionForm.getSalesOrderId());
				}
				
				//Add or Update Advance Metal, Gem & Sales Order Item 
				PreparedStatement stmtInsertAdvanceMetal = connection.prepareStatement("INSERT INTO SALES_ORDER_ADVANCE_METALS (SALES_ORDER_ID, ITEM_ID, ITEM_WEIGHT, ITEM_WEIGHT_UNIT_ID, ITEM_RATE, ITEM_WASTAGE_RATE, ITEM_WASTAGE_RATE_UNIT_ID, ITEM_NET_WEIGHT, ITEM_VALUE, COMMENTS, LEDGER_ENTRY_ID, INVENTORY_METAL_ENTRY_ID, INVENTORY_PURCHASE_VOUCHER_ID, INVENTORY_PURCHASE_VOUCHER_ITEM_ID, ENTRY_DATE ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement stmtUpdateAdvanceMetal = connection.prepareStatement("UPDATE SALES_ORDER_ADVANCE_METALS SET SALES_ORDER_ID=?,ITEM_ID=?, ITEM_WEIGHT=?, ITEM_WEIGHT_UNIT_ID=?, ITEM_RATE=?, ITEM_WASTAGE_RATE=?, ITEM_WASTAGE_RATE_UNIT_ID=?, ITEM_NET_WEIGHT=?, ITEM_VALUE=?, COMMENTS=?, LEDGER_ENTRY_ID=?, INVENTORY_METAL_ENTRY_ID=?, INVENTORY_PURCHASE_VOUCHER_ID=?, INVENTORY_PURCHASE_VOUCHER_ITEM_ID=?,  ENTRY_DATE=? WHERE (SALES_ORDER_ADVANCE_METAL_ID=?)");
				PreparedStatement stmtInsertAdvanceGem = connection.prepareStatement("INSERT INTO SALES_ORDER_ADVANCE_GEMS  (SALES_ORDER_ID, ITEM_ID, ITEM_QUANTITY, ITEM_WEIGHT, ITEM_WEIGHT_UNIT_ID, COMMENTS) VALUES (?, ?, ?, ?, ?, ? )");
				PreparedStatement stmtUpdateAdvanceGem = connection.prepareStatement("UPDATE SALES_ORDER_ADVANCE_GEMS SET SALES_ORDER_ID=?,ITEM_ID=?,ITEM_QUANTITY=?,ITEM_WEIGHT=?,ITEM_WEIGHT_UNIT_ID=?,COMMENTS=? WHERE (SALES_ORDER_ADVANCE_GEM_ID=?)");
				PreparedStatement stmtInsertOrderItem = connection.prepareStatement("INSERT INTO SALES_ORDER_ITEMS (JEWELLERY_ITEM_ID)	VALUES(?)");
				PreparedStatement stmtUpdateOrderItem = connection.prepareStatement("UPDATE SALES_ORDER_ITEMS SET JEWELLERY_ITEM_ID=?  WHERE SALES_ORDER_ITEM_ID=? ");
				PreparedStatement stmtInsertOrderItemInfo = connection.prepareStatement("INSERT INTO SALES_ORDER_ITEM_INFO (SALES_ORDER_ID, SALES_ORDER_ITEM_ID, LUMP_SUM_LABOUR_CHARGES, COMMENTS, BODY_MAKING_RATE_TYPE_ID, STONE_SETTING_RATE_TYPE_ID, ORDER_ITEM_CANCEL)	VALUES(?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement stmtUpdateOrderItemInfo = connection.prepareStatement("UPDATE SALES_ORDER_ITEM_INFO SET SALES_ORDER_ID=?, SALES_ORDER_ITEM_ID=?, LUMP_SUM_LABOUR_CHARGES=?, COMMENTS=?, BODY_MAKING_RATE_TYPE_ID=?, STONE_SETTING_RATE_TYPE_ID=?, ORDER_ITEM_CANCEL=?  WHERE SALES_ORDER_ITEM_INFO_ID=? ");
				PreparedStatement stmtInsertItemEstimatedMetal = connection.prepareStatement("INSERT INTO SALES_ORDER_ITEM_INFO_ESTIMATED_METALS (SALES_ORDER_ITEM_INFO_ID, SALES_ORDER_ITEM_ID, SALES_ORDER_ID, ITEM_ID, ITEM_WEIGHT, ITEM_WEIGHT_UNIT_ID, ITEM_RATE, ITEM_WASTAGE_RATE, ITEM_WASTAGE_RATE_UNIT_ID, ITEM_NET_WEIGHT, ITEM_VALUE, COMMENTS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement stmtUpdateItemEstimatedMetal = connection.prepareStatement("UPDATE SALES_ORDER_ITEM_INFO_ESTIMATED_METALS SET SALES_ORDER_ITEM_INFO_ID=?, SALES_ORDER_ITEM_ID=?, SALES_ORDER_ID=?, ITEM_ID=?, ITEM_WEIGHT=?, ITEM_WEIGHT_UNIT_ID=?, ITEM_RATE=?, ITEM_WASTAGE_RATE=?, ITEM_WASTAGE_RATE_UNIT_ID=?, ITEM_NET_WEIGHT=?, ITEM_VALUE=?, COMMENTS=? WHERE (SALES_ORDER_ITEM_INFO_ESTIMATED_METAL_ID=?)");
				PreparedStatement stmtInsertItemEstimatedGem = connection.prepareStatement("INSERT INTO SALES_ORDER_ITEM_INFO_ESTIMATED_GEMS  (SALES_ORDER_ITEM_INFO_ID, SALES_ORDER_ITEM_ID, SALES_ORDER_ID, ITEM_ID, ITEM_QUANTITY, ITEM_WEIGHT, ITEM_WEIGHT_UNIT_ID, ITEM_RATE, COMMENTS, ITEM_VALUE, ITEM_VALUE_CALCULATE_BY   ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement stmtUpdateItemEstimatedGem = connection.prepareStatement("UPDATE SALES_ORDER_ITEM_INFO_ESTIMATED_GEMS SET SALES_ORDER_ITEM_INFO_ID=?, SALES_ORDER_ITEM_ID=?, SALES_ORDER_ID=?, ITEM_ID=?, ITEM_QUANTITY=?, ITEM_WEIGHT=?, ITEM_WEIGHT_UNIT_ID=?, ITEM_RATE=?, COMMENTS=?,  ITEM_VALUE=?, ITEM_VALUE_CALCULATE_BY=? WHERE (SALES_ORDER_ITEM_INFO_ESTIMATED_GEM_ID=?)");
				
				
				//Advance Metals		
				for (int index = 0; index < actionForm.getAdvanceMetalList().size(); index++) {
				if (actionForm.getAdvanceMetal(index).isInsertable()) {
						stmtInsertAdvanceMetal.setInt(1,Integer.parseInt(actionForm.getSalesOrderId()));
						stmtInsertAdvanceMetal.setInt(2,Integer.parseInt(actionForm.getAdvanceMetal(index).getItemId()));
						stmtInsertAdvanceMetal.setFloat(3,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemWeight()));
						stmtInsertAdvanceMetal.setInt(4,Integer.parseInt(actionForm.getAdvanceMetal(index).getItemWeightUnitId()));
						stmtInsertAdvanceMetal.setFloat(5,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemRate()));
						stmtInsertAdvanceMetal.setFloat(6,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemWastageRate()));
						stmtInsertAdvanceMetal.setInt(7,Integer.parseInt(actionForm.getAdvanceMetal(index).getItemWastageRateUnitId()));
						stmtInsertAdvanceMetal.setFloat(8,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemNetWeight()));
						stmtInsertAdvanceMetal.setFloat(9,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemValue()));
						stmtInsertAdvanceMetal.setString(10,actionForm.getAdvanceMetal(index).getComments());
						stmtInsertAdvanceMetal.setInt(11,Integer.parseInt(actionForm.getAdvanceMetal(index).getLedgerEntryId()));
						stmtInsertAdvanceMetal.setInt(12,Integer.parseInt(actionForm.getAdvanceMetal(index).getInventoryMetalEntryIdIn()));
						stmtInsertAdvanceMetal.setInt(13,Integer.parseInt(actionForm.getAdvanceMetal(index).getInventoryPurchaseVoucherId()));
						stmtInsertAdvanceMetal.setInt(14,Integer.parseInt(actionForm.getAdvanceMetal(index).getInventoryPurchaseVoucherItemId()));
						stmtInsertAdvanceMetal.setString(15,actionForm.getAdvanceMetal(index).getEntryDate());
						stmtInsertAdvanceMetal.executeUpdate();
						rs = stmtInsertAdvanceMetal.getGeneratedKeys();
						actionForm.getAdvanceMetal(index).setSalesOrderAdvanceMetalId(rs.next()?rs.getString(1):"0");
						rs.close();
						actionForm.getAdvanceMetal(index).setInsertable(false);
						stmtInsertAdvanceMetal.clearParameters();
					} else {
						stmtUpdateAdvanceMetal.setInt(1,Integer.parseInt(actionForm.getSalesOrderId()));
						stmtUpdateAdvanceMetal.setInt(2,Integer.parseInt(actionForm.getAdvanceMetal(index).getItemId()));
						stmtUpdateAdvanceMetal.setFloat(3,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemWeight()));
						stmtUpdateAdvanceMetal.setInt(4,Integer.parseInt(actionForm.getAdvanceMetal(index).getItemWeightUnitId()));
						stmtUpdateAdvanceMetal.setFloat(5,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemRate()));
						stmtUpdateAdvanceMetal.setFloat(6,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemWastageRate()));
						stmtUpdateAdvanceMetal.setInt(7,Integer.parseInt(actionForm.getAdvanceMetal(index).getItemWastageRateUnitId()));
						stmtUpdateAdvanceMetal.setFloat(8,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemNetWeight()));
						stmtUpdateAdvanceMetal.setFloat(9,Float.parseFloat(actionForm.getAdvanceMetal(index).getItemValue()));
						stmtUpdateAdvanceMetal.setString(10,actionForm.getAdvanceMetal(index).getComments());
						stmtUpdateAdvanceMetal.setInt(11,Integer.parseInt(actionForm.getAdvanceMetal(index).getLedgerEntryId()));
						stmtUpdateAdvanceMetal.setInt(12,Integer.parseInt(actionForm.getAdvanceMetal(index).getInventoryMetalEntryIdIn()));
						stmtUpdateAdvanceMetal.setInt(13,Integer.parseInt(actionForm.getAdvanceMetal(index).getInventoryPurchaseVoucherId()));
						stmtUpdateAdvanceMetal.setInt(14,Integer.parseInt(actionForm.getAdvanceMetal(index).getInventoryPurchaseVoucherItemId()));
						stmtUpdateAdvanceMetal.setString(15,actionForm.getAdvanceMetal(index).getEntryDate());
						stmtUpdateAdvanceMetal.setInt(16,Integer.parseInt(actionForm.getAdvanceMetal(index).getSalesOrderAdvanceMetalId()));
						stmtUpdateAdvanceMetal.execute();
						stmtUpdateAdvanceMetal.clearParameters();
					}
				}
				
				//Advance Gems
				for (int index = 0; index < actionForm.getAdvanceGemList().size(); index++) {
					if (actionForm.getAdvanceGem(index).isInsertable()) {
						stmtInsertAdvanceGem.setInt(1,Integer.parseInt(actionForm.getSalesOrderId()));
						stmtInsertAdvanceGem.setInt(2,Integer.parseInt(actionForm.getAdvanceGem(index).getItemId()));
						stmtInsertAdvanceGem.setInt(3,Integer.parseInt(actionForm.getAdvanceGem(index).getItemQuantity()));
						stmtInsertAdvanceGem.setFloat(4,Float.parseFloat(actionForm.getAdvanceGem(index).getItemWeight()));
						stmtInsertAdvanceGem.setInt(5,Integer.parseInt(actionForm.getAdvanceGem(index).getItemWeightUnitId()));
						stmtInsertAdvanceGem.setString(6,actionForm.getAdvanceGem(index).getComments());
						stmtInsertAdvanceGem.executeUpdate();
						rs = stmtInsertAdvanceGem.getGeneratedKeys();
						actionForm.getAdvanceGem(index).setSalesOrderAdvanceGemId(rs.next()?rs.getString(1):"0");
						rs.close();
						actionForm.getAdvanceGem(index).setInsertable(false);
						stmtInsertAdvanceGem.clearParameters();
					} else {
						stmtUpdateAdvanceGem.setInt(1,Integer.parseInt(actionForm.getSalesOrderId()));
						stmtUpdateAdvanceGem.setInt(2,Integer.parseInt(actionForm.getAdvanceGem(index).getItemId()));
						stmtUpdateAdvanceGem.setInt(3,Integer.parseInt(actionForm.getAdvanceGem(index).getItemQuantity()));
						stmtUpdateAdvanceGem.setFloat(4,Float.parseFloat(actionForm.getAdvanceGem(index).getItemWeight()));
						stmtUpdateAdvanceGem.setInt(5,Integer.parseInt(actionForm.getAdvanceGem(index).getItemWeightUnitId()));
						stmtUpdateAdvanceGem.setString(6,actionForm.getAdvanceGem(index).getComments());
						stmtUpdateAdvanceGem.setInt(7,Integer.parseInt(actionForm.getAdvanceGem(index).getSalesOrderAdvanceGemId()));
						stmtUpdateAdvanceGem.execute();
						stmtUpdateAdvanceGem.clearParameters();
					}
				}

				//Sales Order Items
				for (int x = 0; x < actionForm.getOrderItemList().size(); x++) {
					if (actionForm.getOrderItem(x).isInsertable()) {
						stmtInsertOrderItem.setInt(1,Integer.parseInt(actionForm.getOrderItem(x).getJewelleryItemId()));
						stmtInsertOrderItem.executeUpdate();
						rs = stmtInsertOrderItem.getGeneratedKeys();
						actionForm.getOrderItem(x).setSalesOrderItemId(rs.next()?rs.getString(1):"0");
						rs.close();
						actionForm.getOrderItem(x).setInsertable(false);
						stmtInsertOrderItem.clearParameters();
					} else {
						stmtUpdateOrderItem.setInt(1,Integer.parseInt(actionForm.getOrderItem(x).getJewelleryItemId()));
						stmtUpdateOrderItem.setString(2,actionForm.getOrderItem(x).getSalesOrderItemId());
						stmtUpdateOrderItem.executeUpdate();
						stmtUpdateOrderItem.clearParameters();
					}
					for(int y = 0; y < actionForm.getOrderItem(x).getOrderItemInfoList().size(); y++) {
						if (actionForm.getOrderItem(x).getOrderItemInfo(y).isInsertable()) {
							stmtInsertOrderItemInfo.setInt(1,Integer.parseInt(actionForm.getSalesOrderId()));
							stmtInsertOrderItemInfo.setInt(2,Integer.parseInt(actionForm.getOrderItem(x).getSalesOrderItemId()));
							stmtInsertOrderItemInfo.setFloat(3,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getLumpSumLabourCharges()));
							stmtInsertOrderItemInfo.setString(4,actionForm.getOrderItem(x).getOrderItemInfo(y).getComments());
							stmtInsertOrderItemInfo.setInt(5,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getBodyMakingRateTypeId()));
							stmtInsertOrderItemInfo.setInt(6,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getStoneSettingRateTypeId()));
							stmtInsertOrderItemInfo.setByte(7,actionForm.getOrderItem(x).getOrderItemInfo(y).getOrderItemCancel());

							stmtInsertOrderItemInfo.executeUpdate();
							rs = stmtInsertOrderItemInfo.getGeneratedKeys();
							actionForm.getOrderItem(x).getOrderItemInfo(y).setSalesOrderItemInfoId(rs.next()?rs.getString(1):"0");
							rs.close();
							actionForm.getOrderItem(x).getOrderItemInfo(y).setInsertable(false);
							stmtInsertOrderItemInfo.clearParameters();
						} else {
							stmtUpdateOrderItemInfo.setInt(1,Integer.parseInt(actionForm.getSalesOrderId()));
							stmtUpdateOrderItemInfo.setInt(2,Integer.parseInt(actionForm.getOrderItem(x).getSalesOrderItemId()));
							stmtUpdateOrderItemInfo.setFloat(3,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getLumpSumLabourCharges()));
							stmtUpdateOrderItemInfo.setString(4,actionForm.getOrderItem(x).getOrderItemInfo(y).getComments());
							stmtUpdateOrderItemInfo.setInt(5,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getBodyMakingRateTypeId()));
							stmtUpdateOrderItemInfo.setInt(6,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getStoneSettingRateTypeId()));
							stmtUpdateOrderItemInfo.setByte(7,actionForm.getOrderItem(x).getOrderItemInfo(y).getOrderItemCancel());
							stmtUpdateOrderItemInfo.setString(8,actionForm.getOrderItem(x).getOrderItemInfo(y).getSalesOrderItemInfoId());
							stmtUpdateOrderItemInfo.executeUpdate();
							stmtUpdateOrderItemInfo.clearParameters();
						}
						//Order Item Info
						for(int z=0; z < actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetalList().size(); z++ ){
							if (actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).isInsertable()) {
								stmtInsertItemEstimatedMetal.setInt(1,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getSalesOrderItemInfoId()));
								stmtInsertItemEstimatedMetal.setInt(2,Integer.parseInt(actionForm.getOrderItem(x).getSalesOrderItemId()));
								stmtInsertItemEstimatedMetal.setInt(3,Integer.parseInt(actionForm.getSalesOrderId()));
								stmtInsertItemEstimatedMetal.setInt(4,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemId()));
								stmtInsertItemEstimatedMetal.setFloat(5,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemWeight()));
								stmtInsertItemEstimatedMetal.setInt(6,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemWeightUnitId()));
								stmtInsertItemEstimatedMetal.setFloat(7,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemRate()));
								stmtInsertItemEstimatedMetal.setFloat(8,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemWastageRate()));
								stmtInsertItemEstimatedMetal.setInt(9,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemWastageRateUnitId()));
								stmtInsertItemEstimatedMetal.setFloat(10,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemNetWeight()));
								stmtInsertItemEstimatedMetal.setFloat(11,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemValue()));
								stmtInsertItemEstimatedMetal.setString(12,actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getComments());
								stmtInsertItemEstimatedMetal.executeUpdate();
								rs = stmtInsertItemEstimatedMetal.getGeneratedKeys();
								actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).setSalesOrderItemInfoEstimatedMetalId(rs.next()?rs.getString(1):"0");
								rs.close();
								actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).setInsertable(false);
								stmtInsertItemEstimatedMetal.clearParameters();
								
							} else {
								stmtUpdateItemEstimatedMetal.setInt(1,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getSalesOrderItemInfoId()));
								stmtUpdateItemEstimatedMetal.setInt(2,Integer.parseInt(actionForm.getOrderItem(x).getSalesOrderItemId()));
								stmtUpdateItemEstimatedMetal.setInt(3,Integer.parseInt(actionForm.getSalesOrderId()));
								stmtUpdateItemEstimatedMetal.setInt(4,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemId()));
								stmtUpdateItemEstimatedMetal.setFloat(5,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemWeight()));
								stmtUpdateItemEstimatedMetal.setInt(6,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemWeightUnitId()));
								stmtUpdateItemEstimatedMetal.setFloat(7,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemRate()));
								stmtUpdateItemEstimatedMetal.setFloat(8,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemWastageRate()));
								stmtUpdateItemEstimatedMetal.setInt(9,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemWastageRateUnitId()));
								stmtUpdateItemEstimatedMetal.setFloat(10,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemNetWeight()));
								stmtUpdateItemEstimatedMetal.setFloat(11,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getItemValue()));
								stmtUpdateItemEstimatedMetal.setString(12,actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getComments());
								stmtUpdateItemEstimatedMetal.setString(13,actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedMetal(z).getSalesOrderItemInfoEstimatedMetalId());
								stmtUpdateItemEstimatedMetal.executeUpdate();
								stmtUpdateItemEstimatedMetal.clearParameters();
							}
						}
						for(int z=0; z < actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGemList().size(); z++ ){
							if (actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).isInsertable()) {
								stmtInsertItemEstimatedGem.setInt(1,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getSalesOrderItemInfoId()));
								stmtInsertItemEstimatedGem.setInt(2,Integer.parseInt(actionForm.getOrderItem(x).getSalesOrderItemId()));
								stmtInsertItemEstimatedGem.setInt(3,Integer.parseInt(actionForm.getSalesOrderId()));
								stmtInsertItemEstimatedGem.setInt(4,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemId()));
								stmtInsertItemEstimatedGem.setInt(5,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemQuantity()));
								stmtInsertItemEstimatedGem.setFloat(6,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemWeight()));
								stmtInsertItemEstimatedGem.setInt(7,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemWeightUnitId()));
								stmtInsertItemEstimatedGem.setFloat(8,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemRate()));
								stmtInsertItemEstimatedGem.setString(9,actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getComments());
								stmtInsertItemEstimatedGem.setFloat(10,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemValue()));
								stmtInsertItemEstimatedGem.setInt(11,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemValueCalculateBy()));
								stmtInsertItemEstimatedGem.executeUpdate();
								rs = stmtInsertItemEstimatedGem.getGeneratedKeys();
								actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).setSalesOrderItemInfoEstimatedGemId(rs.next()?rs.getString(1):"0");
								rs.close();
								actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).setInsertable(false);
								stmtInsertItemEstimatedGem.clearParameters();
							} else { 
								stmtUpdateItemEstimatedGem.setInt(1,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getSalesOrderItemInfoId()));
								stmtUpdateItemEstimatedGem.setInt(2,Integer.parseInt(actionForm.getOrderItem(x).getSalesOrderItemId()));
								stmtUpdateItemEstimatedGem.setInt(3,Integer.parseInt(actionForm.getSalesOrderId()));
								stmtUpdateItemEstimatedGem.setInt(4,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemId()));
								stmtUpdateItemEstimatedGem.setInt(5,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemQuantity()));
								stmtUpdateItemEstimatedGem.setFloat(6,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemWeight()));
								stmtUpdateItemEstimatedGem.setInt(7,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemWeightUnitId()));
								stmtUpdateItemEstimatedGem.setFloat(8,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemRate()));
								stmtUpdateItemEstimatedGem.setString(9,actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getComments());
								stmtUpdateItemEstimatedGem.setFloat(10,Float.parseFloat(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemValue()));
								stmtUpdateItemEstimatedGem.setInt(11,Integer.parseInt(actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getItemValueCalculateBy()));
								stmtUpdateItemEstimatedGem.setString(12,actionForm.getOrderItem(x).getOrderItemInfo(y).getEstimatedGem(z).getSalesOrderItemInfoEstimatedGemId());
								stmtUpdateItemEstimatedGem.executeUpdate();
								stmtUpdateItemEstimatedGem.clearParameters();
							}
						}
					}
				}
				//Delete Advance Metal, Gem & Sales Order Item
				stmt.execute("DELETE FROM sales_order_advance_metals WHERE sales_order_advance_metal_id IN ("+actionForm.getDeleteAdvanceMetal()+")");
				stmt.execute("DELETE FROM sales_order_advance_gems WHERE sales_order_advance_gem_id IN ("+actionForm.getDeleteAdvanceGem()+")");
				stmt.execute("DELETE FROM sales_order_item_info_estimated_metals WHERE sales_order_item_info_estimated_metal_id IN ("+actionForm.getDeleteEstimatedMetal()+") OR sales_order_item_id IN ("+actionForm.getDeleteOrderItem()+")");
				stmt.execute("DELETE FROM sales_order_item_info_estimated_gems WHERE sales_order_item_info_estimated_gem_id IN ("+actionForm.getDeleteEstimatedGem()+") OR sales_order_item_id IN ("+actionForm.getDeleteOrderItem()+")");
				stmt.execute("DELETE FROM sales_order_item_info WHERE sales_order_item_id IN ("+actionForm.getDeleteOrderItem()+")");
				stmt.execute("DELETE FROM sales_order_items WHERE sales_order_item_id IN ("+actionForm.getDeleteOrderItem()+")");
				
				//Cleanup database resources
				stmt.close();
				stmtInsertAdvanceMetal.close();
				stmtUpdateAdvanceMetal.close();
				stmtInsertAdvanceGem.close();
				stmtUpdateAdvanceGem.close();
				stmtInsertOrderItemInfo.close();
				stmtUpdateOrderItemInfo.close();
				stmtInsertItemEstimatedMetal.close();
				stmtUpdateItemEstimatedMetal.close();
				stmtInsertItemEstimatedGem.close();
				stmtUpdateItemEstimatedGem.close();
				connection.commit();
				connection.close();

			} catch (Exception e) {
				try {connection.rollback();} catch (SQLException sqle){}
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return mapping.findForward("FAIL");
			}
		}
		return mapping.findForward("SUCCESS");
	}

}