
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Formatter;
import java.text.*;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Default;
import com.netxs.Zewar.Struts.Form.SalesOrderInvoiceForm;
import com.netxs.Zewar.Struts.Form.SalesOrderInvoiceItemForm;
import com.netxs.Zewar.Struts.Form.SalesOrderInvoiceGemForm;
import com.netxs.Zewar.Struts.Form.SalesOrderInvoiceMetalForm;
import com.netxs.Zewar.DataSources.DBConnection;

public class SalesOrderInvoiceCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		SalesOrderInvoiceForm actionForm = (SalesOrderInvoiceForm) form;
		SalesOrderInvoiceItemForm salesInvoiceItem;
		SalesOrderInvoiceMetalForm invoiceItemCompanyMetal;
		SalesOrderInvoiceGemForm invoiceItemCompanyGem;
		SalesOrderInvoiceGemForm invoiceItemCustomerGem;
		SalesOrderInvoiceGemForm customerUnusedGem;
		SalesOrderInvoiceMetalForm customerAdvanceMetal;
		
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

				float totalGemsWeight = 0.0f;
				float totalMetalValue = 0.0F,totalGemsValue = 0.0F;
				float weight = 0.0f, netWeight = 0.0f, totalBodyMaking = 0.0f;

				//Sales Order
				query =  "SELECT * FROM sales_orders SO WHERE SO.SALES_ORDER_ID="+actionForm.getSalesOrderId();
				rs = stmt.executeQuery(query);
				if (rs.next()){
					//***check all processes has been closed

					actionForm.setCustomerLedgerAccountId(rs.getString("CUSTOMER_LEDGER_ACCOUNT_ID"));
					actionForm.setSalesOrderTrackingId(rs.getString("SALES_ORDER_TRACKING_ID"));
					actionForm.setAdvanceCash(rs.getString("ADVANCE_CASH"));
					actionForm.setInsertable(true);
					actionForm.setSalesInvoiceId(rs.getString("SALES_INVOICE_ID"));
					actionForm.setSalesInvoiceStatus(rs.getString("SALES_INVOICE_STATUS"));
					actionForm.setMoveInInventoryStatus(rs.getString("MOVE_IN_INVENTORY_STATUS"));
					actionForm.setHasFormInitialized('Y');
				} else {
					Exception e = new Exception("Unknow Sales Order");
					throw e;
				}
				rs.close();
				
				if (!actionForm.getMoveInInventoryStatus().equals("0")){
					ActionMessages serviceErrors = new ActionMessages();
					serviceErrors.add("error",new ActionMessage("errors","You cannot creat sales invoice. Because sales order already moved in inventory."));
					saveErrors(request,serviceErrors);
					return (mapping.findForward("FAIL_A"));
				}
				//Check invoice created? Yes:load from sales Invoice, No:load from sales order and order processes
				rs = stmt.executeQuery("SELECT * FROM sales_invoices WHERE SALES_INVOICE_ID="+actionForm.getSalesInvoiceId());
				if (rs.next()) {
					ActionMessages serviceErrors = new ActionMessages();
					serviceErrors.add("error",new ActionMessage("errors","Loading From Sales Invoice"));
					saveErrors(request,serviceErrors);
					
					StringBuilder formmatedString = new StringBuilder();
					Formatter formatter = new Formatter(formmatedString, Locale.US);

						
					actionForm.setAdvanceCash(rs.getString("TOTAL_ADVANCE_IN_CASH"));
					actionForm.setInsertable(false);
					actionForm.setCustomerLedgerAccountId(rs.getString("LEDGER_ACCOUNT_ID"));
					actionForm.setInvoiceDate(rs.getString("INVOICE_DATE"));
					actionForm.setLedgerEntryId(rs.getString("LEDGER_ENTRY_ID"));
					actionForm.setRemarks(rs.getString("REMARKS"));
//					actionForm.setSalesInvoiceId(rs.getString("SALES_INVOICE_ID"));
					actionForm.setTotalInvoiceAmount(formatter.format("%.2f", rs.getFloat("TOTAL_BILL_AMOUNT")).toString());
					formmatedString.setLength(0);
					actionForm.setTotalMaking(formatter.format("%.2f", rs.getFloat("TOTAL_MAKING")).toString());
					formmatedString.setLength(0);
					actionForm.setVoucherPostfix(rs.getString("VOUCHER_POSTFIX"));
					actionForm.setVoucherPrefix(rs.getString("VOUCHER_PREFIX"));
					actionForm.setTotalGemValue(formatter.format("%.2f", rs.getFloat("TOTAL_GEM_AMOUNT")).toString());
					formmatedString.setLength(0);
					actionForm.setTotalMetalValue(formatter.format("%.2f", rs.getFloat("TOTAL_METAL_AMOUNT")).toString());
					formmatedString.setLength(0);
					actionForm.setTotalAdvance(formatter.format("%.2f", rs.getFloat("TOTAL_ADVANCE_IN_CASH")).toString());
					formmatedString.setLength(0);
					actionForm.setTotalDiscount(formatter.format("%.2f", rs.getFloat("TOTAL_DISCOUNT")).toString());
					formmatedString.setLength(0);
					actionForm.setReceiveable(formatter.format("%.2f", rs.getFloat("TOTAL_ADVANCE_IN_CASH")).toString());
					formmatedString.setLength(0);
					actionForm.setHasFormInitialized('Y');
					
					//Sales Invoice Items
					query =  " SELECT *  FROM sales_invoice_items II "
							+" INNER JOIN items  IT ON II.JEWELLERY_ITEM_ID = IT.ITEM_ID" 
							+" WHERE II.SALES_INVOICE_ID="+actionForm.getSalesInvoiceId();
					rs = stmt.executeQuery(query);
					totalGemsValue = 0.0f;
					totalMetalValue = 0.0f;
					while (rs.next()){
						salesInvoiceItem =  new SalesOrderInvoiceItemForm();	
						salesInvoiceItem.setInsertable(false);
						salesInvoiceItem.setSalesInvoiceItemId(rs.getString("SALES_INVOICE_ITEM_ID"));
						salesInvoiceItem.setItemId(rs.getString("ITEM_ID"));
						salesInvoiceItem.setItemName(rs.getString("ITEM_NAME"));

						//Sales Order Item Gems Used
						query =  " SELECT  "
								+"  GU.SALES_INVOICE_ITEM_GEM_USED_ID"
								+" ,GU.INVENTORY_GEM_ENTRY_ID"
								+" ,IT.ITEM_ID"
								+" ,IT.ITEM_NAME"
								+" ,IFNULL(GU.ITEM_RATE,0.0) AS ITEM_RATE"
								+" ,IFNULL(GU.ITEM_VALUE,0.0) AS ITEM_VALUE"
								+" ,IFNULL(GU.ITEM_VALUE_CALCULATE_BY,"+Default.VALUE_CALCULATE_BY_WEIGHT+") AS ITEM_VALUE_CALCULATE_BY"
								+" ,WU.UNIT_CODE AS ITEM_WEIGHT_UNIT"  
								+" ,GU.ITEM_WEIGHT_UNIT_ID "
								+" ,GU.ITEM_INVENTORY_TYPE_ID"
								+" ,GU.ITEM_WEIGHT AS ITEM_WEIGHT" 
								+" ,GU.ITEM_WEIGHT * WU.GRAM_CONVERSION_FACTOR AS WEIGHT_IN_GRAM"
								+" ,GU.ITEM_QUANTITY "
								+" FROM SALES_INVOICE_ITEM_GEM_USED GU"
								+" INNER JOIN ITEMS IT ON GU.ITEM_ID=IT.ITEM_ID"
								+" INNER JOIN WEIGHT_UNITS WU ON WU.WEIGHT_UNIT_ID = GU.ITEM_WEIGHT_UNIT_ID"
								+" WHERE GU.SALES_INVOICE_ITEM_ID="+salesInvoiceItem.getSalesInvoiceItemId()
								+" ORDER BY GU.ITEM_INVENTORY_TYPE_ID, IT.ITEM_NAME";
						rs2 = stmt2.executeQuery(query);
						while (rs2.next()){
							//totalGemsWeight +=rs2.getFloat("WEIGHT_IN_GRAM");
							totalGemsValue += rs2.getFloat("ITEM_VALUE");
							if(rs2.getByte("ITEM_INVENTORY_TYPE_ID")==Default.STOCK_TYPE_COMPANY) {
								invoiceItemCompanyGem = new SalesOrderInvoiceGemForm();
								invoiceItemCompanyGem.setInsertable(false);
								invoiceItemCompanyGem.setSalesInvoiceItemGemUsedId(rs2.getString("SALES_INVOICE_ITEM_GEM_USED_ID"));
								invoiceItemCompanyGem.setInventoryGemEntryIdOut(rs2.getString("INVENTORY_GEM_ENTRY_ID"));
								invoiceItemCompanyGem.setItemId(rs2.getString("Item_Id"));
								invoiceItemCompanyGem.setItemName("Our - "+rs2.getString("Item_Name"));
								invoiceItemCompanyGem.setQuantity(rs2.getString("ITEM_Quantity"));
								invoiceItemCompanyGem.setWeight(rs2.getString("ITEM_Weight"));
								invoiceItemCompanyGem.setWeightUnit(rs2.getString("ITEM_WEIGHT_UNIT"));
								invoiceItemCompanyGem.setWeightUnitId(rs2.getString("ITEM_WEIGHT_UNIT_ID"));
								invoiceItemCompanyGem.setRate(rs2.getString("ITEM_RATE"));
								invoiceItemCompanyGem.setValueCalculateBy(rs2.getString("ITEM_VALUE_CALCULATE_BY"));
								invoiceItemCompanyGem.setValue(rs2.getString("ITEM_VALUE"));
								salesInvoiceItem.setCompanyGem(invoiceItemCompanyGem);
							} else if(rs2.getByte("ITEM_INVENTORY_TYPE_ID")==Default.STOCK_TYPE_CUSTOMER) {
								invoiceItemCustomerGem = new SalesOrderInvoiceGemForm();
								invoiceItemCustomerGem.setInsertable(false);
								invoiceItemCustomerGem.setSalesInvoiceItemGemUsedId(rs2.getString("SALES_INVOICE_ITEM_GEM_USED_ID"));
								invoiceItemCustomerGem.setItemId(rs2.getString("Item_Id"));
								invoiceItemCustomerGem.setItemName("Customer - "+rs2.getString("Item_Name"));
								invoiceItemCustomerGem.setQuantity(rs2.getString("ITEM_Quantity"));
								invoiceItemCustomerGem.setWeight(rs2.getString("ITEM_Weight"));
								invoiceItemCustomerGem.setWeightUnit(rs2.getString("ITEM_WEIGHT_UNIT"));
								invoiceItemCustomerGem.setWeightUnitId(rs2.getString("ITEM_WEIGHT_UNIT_ID"));
								salesInvoiceItem.setCustomerGem(invoiceItemCustomerGem);
							}
						}
					
						// Company Metal Used
						query =  " SELECT  "
							+"  GU.SALES_INVOICE_ITEM_METAL_USED_ID"
							+" ,IT.ITEM_ID"
							+" ,IT.ITEM_NAME"
							+" ,IFNULL(GU.ITEM_RATE,0.0) AS ITEM_RATE"
							+" ,IFNULL(GU.ITEM_VALUE,0.0) AS ITEM_VALUE"
							+" ,WU.UNIT_CODE AS ITEM_WEIGHT_UNIT"  
							+" ,GU.ITEM_WEIGHT_UNIT_ID "
							+" ,GU.ITEM_WEIGHT "
							+" ,GU.ITEM_NET_WEIGHT AS ITEM_NET_WEIGHT" 
							+" ,GU.ITEM_WASTAGE_RATE"
							+" ,GU.ITEM_WASTAGE_RATE_UNIT_ID"
							+" ,GU.ITEM_WEIGHT * WU.GRAM_CONVERSION_FACTOR AS WEIGHT_IN_GRAM"
							+" FROM SALES_INVOICE_ITEM_METAL_USED GU"
							+" INNER JOIN ITEMS IT ON GU.ITEM_ID=IT.ITEM_ID"
							+" INNER JOIN WEIGHT_UNITS WU ON WU.WEIGHT_UNIT_ID = GU.ITEM_WEIGHT_UNIT_ID"
							+" WHERE GU.SALES_INVOICE_ITEM_ID="+salesInvoiceItem.getSalesInvoiceItemId()
							+" ORDER BY IT.ITEM_NAME";
						rs2 = stmt2.executeQuery(query);
						while (rs2.next()){
							totalMetalValue += rs2.getFloat("ITEM_VALUE");
							invoiceItemCompanyMetal = new SalesOrderInvoiceMetalForm();
							invoiceItemCompanyMetal.setInsertable(false);
							invoiceItemCompanyMetal.setSalesInvoiceItemMetalUsedId(rs2.getString("SALES_INVOICE_ITEM_METAL_USED_ID"));
							invoiceItemCompanyMetal.setItemName("Our - "+rs2.getString("ITEM_NAME"));
							invoiceItemCompanyMetal.setItemId(rs2.getString("ITEM_ID"));
							invoiceItemCompanyMetal.setWeightUnit(rs2.getString("ITEM_WEIGHT_UNIT"));
							invoiceItemCompanyMetal.setWeightUnitId(rs2.getString("ITEM_WEIGHT_UNIT_ID"));
							invoiceItemCompanyMetal.setWeightUnitId(Byte.toString(Default.WEIGHT_IN_GRAM));  
							invoiceItemCompanyMetal.setWastageRate(rs2.getString("ITEM_WASTAGE_RATE"));
							invoiceItemCompanyMetal.setWastageUnitId(rs2.getString("ITEM_WASTAGE_RATE_UNIT_ID"));
							invoiceItemCompanyMetal.setRate(rs2.getString("ITEM_RATE")); 
							invoiceItemCompanyMetal.setWeight(rs2.getString("ITEM_WEIGHT"));
							invoiceItemCompanyMetal.setNetWeight(rs2.getString("ITEM_NET_WEIGHT"));
							invoiceItemCompanyMetal.setValue(rs2.getString("ITEM_VALUE"));
							salesInvoiceItem.setCompanyMetal(invoiceItemCompanyMetal);
						}
						actionForm.setInvoiceItem(salesInvoiceItem);
						
					}
//					actionForm.setTotalGemValue(Float.toString(totalGemsValue));
//					actionForm.setTotalMetalValue(Float.toString(totalMetalValue));
					
				} else { // load invoice from Sales Order
					ActionMessages serviceErrors = new ActionMessages();
					serviceErrors.add("error",new ActionMessage("errors","Loading From Sales Order"));
					saveErrors(request,serviceErrors);
					actionForm.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
					// Sales Order Items
					query =  " SELECT *  FROM sales_order_items OI "
							+" INNER JOIN sales_order_item_info II ON II.SALES_ORDER_ITEM_ID = OI.SALES_ORDER_ITEM_ID"
							+" INNER JOIN items  IT ON OI.JEWELLERY_ITEM_ID = IT.ITEM_ID" 
							+" WHERE II.SALES_ORDER_ID="+actionForm.getSalesOrderId();
					rs = stmt.executeQuery(query);
					
					
					totalGemsValue = 0.0f;
					totalMetalValue = 0.0f;

					while (rs.next()){
						totalBodyMaking += rs.getFloat("LUMP_SUM_LABOUR_CHARGES");
						salesInvoiceItem =  new SalesOrderInvoiceItemForm();	
						salesInvoiceItem.setInsertable(true);
						salesInvoiceItem.setSalesOrderItemId(rs.getString("Sales_Order_Item_Id"));
						salesInvoiceItem.setItemId(rs.getString("Item_Id"));
						salesInvoiceItem.setItemName(rs.getString("Item_Name"));


						//Sales Order Item Company and Customer Gems Used
						query =  " SELECT " 
								+"  IT.ITEM_ID"
								+" ,IT.ITEM_NAME"
								+" ,IFNULL(EG.ITEM_RATE,0.0) AS ITEM_RATE"
								+" ,IFNULL(EG.ITEM_RATE,0.0) * XU.WEIGHT AS VALUE"
								+" ,IFNULL(EG.ITEM_VALUE_CALCULATE_BY,"+Default.VALUE_CALCULATE_BY_WEIGHT+") AS ITEM_VALUE_CALCULATE_BY"
								+" ,WU.UNIT_CODE AS WEIGHT_UNIT"  
								+" ,WU.WEIGHT_UNIT_ID "
								+" ,XU.WEIGHT_UNIT_ID"
								+" ,XU.ITEM_STOCK_TYPE"
								+" ,XU.WEIGHT AS WEIGHT" 
								+" ,XU.WEIGHT * WU.GRAM_CONVERSION_FACTOR AS WEIGHT_IN_GRAM"
								+" ,XU.QUANTITY "
								+" FROM ITEMS IT" 
								+" INNER JOIN "
								+"	( ( "
								+"     SELECT "
								+"       XI.ISSUE_ITEM_ID AS ITEM_ID"
								+"      ,XI.ISSUE_WEIGHT_UNIT_ID AS WEIGHT_UNIT_ID"
								+"      ,XI.ISSUE_ITEM_STOCK_TYPE	  AS ITEM_STOCK_TYPE"
								+"      ,SUM(XI.ISSUE_WEIGHT - ifnull(XR.RETURN_WEIGHT,0) ) AS WEIGHT"
								+"      ,SUM(XI.ISSUE_QUANTITY	- ifnull(XR.RETURN_QUANTITY,0)) AS QUANTITY"
								+"     FROM "
								+"       ( SELECT"	
								+"          SUM(GI.WEIGHT) AS ISSUE_WEIGHT"
								+"         ,GI.WEIGHT_UNIT_ID AS ISSUE_WEIGHT_UNIT_ID"
								+"         ,SUM(GI.QUANTITY) AS ISSUE_QUANTITY"		
								+"         ,GI.ITEM_ID  AS ISSUE_ITEM_ID"
								+"         ,GI.ITEM_STOCK_TYPE  AS ISSUE_ITEM_STOCK_TYPE"
								+"         FROM sales_order_process_gem_issue GI"
								+"         INNER JOIN sales_order_processes PI ON GI.SALES_ORDER_PROCESS_ID=PI.SALES_ORDER_PROCESS_ID"
								+"         WHERE PI.SALES_ORDER_ITEM_ID="+salesInvoiceItem.getSalesOrderItemId()	  
								+"         GROUP BY GI.ITEM_ID , GI.WEIGHT_UNIT_ID, GI.ITEM_STOCK_TYPE ) XI" 
								+"     LEFT OUTER  JOIN" 
								+"       ( SELECT"	
								+"          SUM(GR.WEIGHT) AS RETURN_WEIGHT"
								+"         ,GR.WEIGHT_UNIT_ID AS RETURN_WEIGHT_UNIT_ID"
								+"         ,SUM(GR.QUANTITY) AS RETURN_QUANTITY"		
								+"         ,GR.ITEM_ID	AS RETURN_ITEM_ID"	
								+"         ,GR.ITEM_STOCK_TYPE AS RETURN_ITEM_STOCK_TYPE"
								+"         FROM sales_order_process_gem_return GR"
								+"         INNER JOIN sales_order_processes PI ON GR.SALES_ORDER_PROCESS_ID=PI.SALES_ORDER_PROCESS_ID"
								+"         WHERE PI.SALES_ORDER_ITEM_ID="+salesInvoiceItem.getSalesOrderItemId()	  
								+"         GROUP BY GR.ITEM_ID , GR.WEIGHT_UNIT_ID, GR.ITEM_STOCK_TYPE ) XR ON XI.ISSUE_ITEM_ID = XR.RETURN_ITEM_ID AND  XI.ISSUE_WEIGHT_UNIT_ID = XR.RETURN_WEIGHT_UNIT_ID AND XI.ISSUE_ITEM_STOCK_TYPE = XR.RETURN_ITEM_STOCK_TYPE"    
								+"     GROUP BY XI.ISSUE_ITEM_ID, XI.ISSUE_WEIGHT_UNIT_ID, XI.ISSUE_ITEM_STOCK_TYPE)  ) XU   ON IT.ITEM_ID = XU.ITEM_ID"
								+" INNER JOIN WEIGHT_UNITS WU ON WU.WEIGHT_UNIT_ID = XU.WEIGHT_UNIT_ID"
								+" LEFT OUTER JOIN sales_order_item_info_estimated_gems EG ON EG.SALES_ORDER_ITEM_ID="+salesInvoiceItem.getSalesOrderItemId()+" AND EG.ITEM_ID=IT.ITEM_ID" 
								+" ORDER BY XU.ITEM_STOCK_TYPE, IT.ITEM_NAME";
						rs2 = stmt2.executeQuery(query);
						totalGemsWeight = 0.0f;
						while (rs2.next()){
							totalGemsWeight +=rs2.getFloat("WEIGHT_IN_GRAM");
							totalGemsValue += rs2.getFloat("VALUE");
							if(rs2.getByte("Item_Stock_Type")==Default.STOCK_TYPE_COMPANY) {
								invoiceItemCompanyGem = new SalesOrderInvoiceGemForm();
								invoiceItemCompanyGem.setInsertable(true);
								invoiceItemCompanyGem.setItemId(rs2.getString("Item_Id"));
								invoiceItemCompanyGem.setItemName("Our - "+rs2.getString("Item_Name"));
								invoiceItemCompanyGem.setQuantity(rs2.getString("Quantity"));
								invoiceItemCompanyGem.setWeight(rs2.getString("Weight"));
								invoiceItemCompanyGem.setWeightUnit(rs2.getString("WEIGHT_UNIT"));
								invoiceItemCompanyGem.setWeightUnitId(rs2.getString("WEIGHT_UNIT_ID"));
								invoiceItemCompanyGem.setRate(rs2.getString("ITEM_RATE"));
								invoiceItemCompanyGem.setValueCalculateBy(rs2.getString("ITEM_VALUE_CALCULATE_BY"));
								invoiceItemCompanyGem.setValue(rs2.getString("Value"));
								salesInvoiceItem.setCompanyGem(invoiceItemCompanyGem);
							} else if(rs2.getByte("Item_Stock_Type")==Default.STOCK_TYPE_CUSTOMER) {
								invoiceItemCustomerGem = new SalesOrderInvoiceGemForm();
								invoiceItemCustomerGem.setInsertable(true);
								invoiceItemCustomerGem.setItemId(rs2.getString("Item_Id"));
								invoiceItemCustomerGem.setItemName("Customer - "+rs2.getString("Item_Name"));
								invoiceItemCustomerGem.setQuantity(rs2.getString("Quantity"));
								invoiceItemCustomerGem.setWeight(rs2.getString("Weight"));
								invoiceItemCustomerGem.setWeightUnit(rs2.getString("WEIGHT_UNIT"));
								invoiceItemCustomerGem.setWeightUnitId(rs2.getString("WEIGHT_UNIT_ID"));
								salesInvoiceItem.setCustomerGem(invoiceItemCustomerGem);
							}
						}
					
						// Company Metal Used
						query = " SELECT"
								+"  IT.ITEM_ID"
								+" ,IT.ITEM_NAME"
								+" ,XX.BODY_WEIGHT AS ITEM_WEIGHT"
								+" ,'gm' AS WEIGHT_UNIT"
								+" ,'1' AS WEIGHT_UNIT_ID"
								+" ,IEM.ITEM_RATE" 
								+" ,IEM.ITEM_WASTAGE_RATE"
								+" ,IEM.ITEM_WASTAGE_RATE_UNIT_ID"
								+" FROM items IT" 
								+" INNER JOIN " 
								
								+"  (SELECT" 
								+"     BODY_METAL_ITEM_ID"
								+"    ,SUM( RETURN_BODY_WEIGHT - ISSUE_BODY_WEIGHT ) AS BODY_WEIGHT"
								+"   FROM	sales_order_processes"
								+"   WHERE	SALES_ORDER_ITEM_ID="+salesInvoiceItem.getSalesOrderItemId()
								+"   GROUP BY BODY_METAL_ITEM_ID" 
								+"  )  XX ON XX.BODY_METAL_ITEM_ID=IT.ITEM_ID " 
								+" LEFT OUTER JOIN sales_order_item_info_estimated_metals IEM ON IEM.SALES_ORDER_ITEM_ID="+salesInvoiceItem.getSalesOrderItemId()+" AND IEM.ITEM_ID=IT.ITEM_ID";
						rs2 = stmt2.executeQuery(query);
						while (rs2.next()){
							invoiceItemCompanyMetal = new SalesOrderInvoiceMetalForm();
							invoiceItemCompanyMetal.setInsertable(true);
							invoiceItemCompanyMetal.setItemName("Our - "+rs2.getString("ITEM_NAME"));
							invoiceItemCompanyMetal.setItemId(rs2.getString("ITEM_ID"));
							invoiceItemCompanyMetal.setWeightUnit(rs2.getString("WEIGHT_UNIT"));
							invoiceItemCompanyMetal.setWeightUnitId(rs2.getString("WEIGHT_UNIT_ID"));
							invoiceItemCompanyMetal.setWeightUnitId(Byte.toString(Default.WEIGHT_IN_GRAM));  
							invoiceItemCompanyMetal.setWastageRate(rs2.getString("ITEM_WASTAGE_RATE"));
							invoiceItemCompanyMetal.setWastageUnitId(rs2.getString("ITEM_WASTAGE_RATE_UNIT_ID"));
							invoiceItemCompanyMetal.setRate(rs2.getString("ITEM_RATE")); 
							weight = rs2.getFloat("ITEM_WEIGHT") - totalGemsWeight;
							if (rs2.getByte("ITEM_WASTAGE_RATE_UNIT_ID") == Default.WASTAGE_IN_PERCENTAGE)
								netWeight = weight + (weight * rs2.getFloat("ITEM_WASTAGE_RATE")/100);
							else if(rs2.getByte("ITEM_WASTAGE_RATE_UNIT_ID") == Default.WASTAGE_IN_RAATI){
								netWeight = weight + (weight * rs2.getFloat("ITEM_WASTAGE_RATE")/96);
							}
							invoiceItemCompanyMetal.setWeight(Float.toString(weight));
							invoiceItemCompanyMetal.setNetWeight(Float.toString(netWeight));
							invoiceItemCompanyMetal.setValue(Float.toString(netWeight * rs2.getFloat("ITEM_RATE")));
							salesInvoiceItem.setCompanyMetal(invoiceItemCompanyMetal);
							
							//totalMetalValue += (netWeight * rs2.getFloat("ITEM_RATE"));
						}
						
						actionForm.setInvoiceItem(salesInvoiceItem);	
					}
					actionForm.setTotalMaking(Float.toString(totalBodyMaking));

					//actionForm.setTotalGemValue(String.valueOf(totalGemsValue));
					//actionForm.setTotalMetalValue(String.valueOf(totalMetalValue));

				}

				// Sales Order Invoice Customer Advance Metal 
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
					customerAdvanceMetal = new SalesOrderInvoiceMetalForm();
					customerAdvanceMetal.setItemId(rs2.getString("ITEM_ID"));
					customerAdvanceMetal.setItemName(rs2.getString("ITEM_NAME"));
					customerAdvanceMetal.setWeightUnit(rs2.getString("WEIGHT_UNIT"));
					customerAdvanceMetal.setWeightUnitId(rs2.getString("WEIGHT_UNIT_ID"));
					customerAdvanceMetal.setWastageRate(rs2.getString("ITEM_WASTAGE_RATE"));
					customerAdvanceMetal.setWastageUnit(rs2.getString("WASTAGE_UNIT"));
					customerAdvanceMetal.setWastageUnitId(rs2.getString("WASTAGE_UNIT_ID"));
					customerAdvanceMetal.setRate(rs2.getString("ITEM_RATE")); 
					customerAdvanceMetal.setWeight(rs2.getString("ITEM_WEIGHT"));
					customerAdvanceMetal.setNetWeight(rs2.getString("ITEM_NET_WEIGHT"));
					customerAdvanceMetal.setValue(rs2.getString("ITEM_VALUE"));
					actionForm.setCustomerAdvanceMetal(customerAdvanceMetal);
				}
				// Sales Order Invoice Customer Advance Unused Gem
				customerUnusedGem = new SalesOrderInvoiceGemForm();
				customerUnusedGem.setItemName("Diamonds mellee");
				actionForm.setCustomerUnusedGem(customerUnusedGem);					

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
		} else {  
			
			connection = (Connection) new DBConnection().getMyPooledConnection();
			try {
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
				connection.setAutoCommit(false);
				stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
				
				if (actionForm.isInsertable()){
					// add Invoice Receipt
					rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM sales_invoices ");
					actionForm.setVoucherPostfix(rs.next()?rs.getString(1):"0");
					actionForm.setVoucherPrefix(Default.SALES_INVOICE_VOUCHER_PREFIX);
					stmt.executeUpdate(
							  " INSERT INTO sales_invoices ( VOUCHER_PREFIX, VOUCHER_POSTFIX, LEDGER_ACCOUNT_ID, REMARKS, INVOICE_DATE, TOTAL_BILL_AMOUNT, TOTAL_MAKING, TOTAL_ADVANCE_IN_CASH, TOTAL_METAL_AMOUNT, TOTAL_GEM_AMOUNT ) VALUES ( "
							+ "  '"+actionForm.getVoucherPrefix()
							+ "', "+actionForm.getVoucherPostfix()
							+ " , "+actionForm.getCustomerLedgerAccountId()
							+ " ,'"+actionForm.getRemarks()
							+ "','"+actionForm.getInvoiceDate()
							+ "', "+actionForm.getTotalInvoiceAmount()
							+ " , "+actionForm.getTotalMaking()
							+ ", "+actionForm.getTotalAdvance()
							+ ", "+actionForm.getTotalMetalValue()
							+ ", "+actionForm.getTotalGemValue()
							+ " ) " 
					);
					rs = stmt.getGeneratedKeys();
					actionForm.setSalesInvoiceId(rs.next()?rs.getString(1):"0");
					
					//Sales Invoice Value Ledger Entry. Customer Debit, Sales Credit
					stmt.execute(
						 " INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, voucher_prefix, voucher_postfix, entry_date, amount, narration)"
						+" VALUES ("
						+     actionForm.getCustomerLedgerAccountId()
						+", "+Default.SALES_OUT_ACCOUNT_ID
						+",'"+Default.SALES_INVOICE_VOUCHER_PREFIX+"'"
						+", "+actionForm.getVoucherPostfix() 
						+",'"+actionForm.getInvoiceDate()+"'"
						+", "+actionForm.getTotalInvoiceAmount()
						+",'"+actionForm.getRemarks()+"'"
						+" )"
					);
					rs = stmt.getGeneratedKeys();
					actionForm.setLedgerEntryId(rs.next()?rs.getString(1):"0");
					//Update Sales Invoice
					stmt.executeUpdate("UPDATE sales_invoices SET LEDGER_ENTRY_ID="+actionForm.getLedgerEntryId()+" WHERE SALES_INVOICE_ID="+actionForm.getSalesInvoiceId());
					stmt.executeUpdate("UPDATE sales_orders SET SALES_INVOICE_ID="+actionForm.getSalesInvoiceId()+", SALES_INVOICE_STATUS=1, SALES_ORDER_STATUS_ID="+Default.SALES_ORDER_STATUS_COMPLETE+"  WHERE SALES_ORDER_ID="+actionForm.getSalesOrderId());
				}else{
					stmt.executeUpdate(
							  " UPDATE sales_invoices SET "
							+ "  LEDGER_ACCOUNT_ID="+actionForm.getCustomerLedgerAccountId()
							+ " ,REMARKS='"+actionForm.getRemarks()
							+ "',INVOICE_DATE='"+actionForm.getInvoiceDate()
							+ "',TOTAL_BILL_AMOUNT="+actionForm.getTotalInvoiceAmount()
							+ " ,TOTAL_MAKING="+actionForm.getTotalMaking()
							+ " ,TOTAL_METAL_AMOUNT="+actionForm.getTotalMetalValue()
							+ " ,TOTAL_GEM_AMOUNT="+actionForm.getTotalGemValue()
							+ " ,TOTAL_ADVANCE_IN_CASH="+actionForm.getTotalAdvance()
							+ " WHERE SALES_INVOICE_ID="+actionForm.getSalesInvoiceId() 
					);
				
					//Sales Invoice Value Ledger Entry. Customer Debit, Sales Credit
					stmt.execute(
						 " UPDATE ledger_entries SET "
						+" ledger_account_id_debit="+actionForm.getCustomerLedgerAccountId()
						+",ledger_account_id_credit="+Default.SALES_OUT_ACCOUNT_ID
						+",entry_date='"+actionForm.getInvoiceDate()+"'"
						+",amount="+actionForm.getTotalInvoiceAmount()
						+",narration='"+actionForm.getRemarks()+"'"
						+" WHERE ledger_entry_id="+actionForm.getLedgerEntryId()
					);
				}
				
				PreparedStatement stmtInsertSalesInvoiceItem = connection.prepareStatement("INSERT INTO sales_invoice_items (SALES_INVOICE_ID, JEWELLERY_ITEM_ID ) VALUES (?, ?)");
				PreparedStatement stmtUpdateSalesInvoiceItem = connection.prepareStatement("UPDATE  sales_invoice_items SET SALES_INVOICE_ID=?, JEWELLERY_ITEM_ID=?  WHERE SALES_INVOICE_ITEM_ID =?");
				 
				PreparedStatement stmtInsertInvoiceItemMetal = connection.prepareStatement("INSERT INTO sales_invoice_item_metal_used (SALES_INVOICE_ITEM_ID, SALES_INVOICE_ID, ITEM_ID, ITEM_WEIGHT, ITEM_WEIGHT_UNIT_ID, ITEM_WASTAGE_RATE, ITEM_WASTAGE_RATE_UNIT_ID, ITEM_NET_WEIGHT, ITEM_RATE, ITEM_VALUE ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement stmtUpdateInvoiceItemMetal = connection.prepareStatement("UPDATE  sales_invoice_item_metal_used SET SALES_INVOICE_ITEM_ID=?, SALES_INVOICE_ID=?, ITEM_ID=?, ITEM_WEIGHT=?, ITEM_WEIGHT_UNIT_ID=?, ITEM_WASTAGE_RATE=?, ITEM_WASTAGE_RATE_UNIT_ID=?, ITEM_NET_WEIGHT=?, ITEM_RATE=?, ITEM_VALUE=?  WHERE SALES_INVOICE_ITEM_METAL_USED_ID=?");
				
				PreparedStatement stmtInsertInvoiceItemGem = connection.prepareStatement("INSERT INTO sales_invoice_item_gem_used (SALES_INVOICE_ITEM_ID, SALES_INVOICE_ID, ITEM_ID, ITEM_INVENTORY_TYPE_ID, ITEM_QUANTITY, ITEM_WEIGHT, ITEM_WEIGHT_UNIT_ID, ITEM_RATE, ITEM_VALUE, ITEM_VALUE_CALCULATE_BY, INVENTORY_GEM_ENTRY_ID ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement stmtUpdateInvoiceItemGem = connection.prepareStatement("UPDATE  sales_invoice_item_gem_used SET SALES_INVOICE_ITEM_ID=?, SALES_INVOICE_ID=?, ITEM_ID=?, ITEM_INVENTORY_TYPE_ID=?, ITEM_QUANTITY=?, ITEM_WEIGHT=?, ITEM_WEIGHT_UNIT_ID=?, ITEM_RATE=?, ITEM_VALUE=?, ITEM_VALUE_CALCULATE_BY=?  WHERE SALES_INVOICE_ITEM_GEM_USED_ID=?");
				
				PreparedStatement stmtInsertInventoryGemEntry = connection.prepareStatement("INSERT INTO inventory_gem_entries (ENTRY_DATE, INVENTORY_ACCOUNT_ID, ITEM_ID, QUANTITY, WEIGHT, IN_OUT_STATUS, VOUCHER_PREFIX, VOUCHER_POSTFIX, ITEM_WEIGHT_UNIT_ID )	VALUES(?,?,?,?,?,?,?,?,?)");
				PreparedStatement stmtUpdateInventoryGemEntry = connection.prepareStatement("UPDATE inventory_gem_entries SET ENTRY_DATE=?, INVENTORY_ACCOUNT_ID=?, ITEM_ID=?, QUANTITY=?, WEIGHT=?, IN_OUT_STATUS=?, VOUCHER_PREFIX=?, VOUCHER_POSTFIX=?, ITEM_WEIGHT_UNIT_ID=? WHERE INVENTORY_GEM_ENTRY_ID=? ");
				
				Iterator invoiceItemIterator, invoiceItemMetalIterator, invoiceItemGemIterator;
				
				// Add/Update Invoice Items
				invoiceItemIterator = actionForm.getInvoiceItemList().iterator();

				while (invoiceItemIterator.hasNext()) {
					salesInvoiceItem = (SalesOrderInvoiceItemForm) invoiceItemIterator.next();
					if(salesInvoiceItem.isInsertable()) {
						stmtInsertSalesInvoiceItem.setInt(1, Integer.parseInt(actionForm.getSalesInvoiceId()));
						stmtInsertSalesInvoiceItem.setInt(2, Integer.parseInt(salesInvoiceItem.getItemId()));
						stmtInsertSalesInvoiceItem.execute();
						rs = stmtInsertSalesInvoiceItem.getGeneratedKeys();
						salesInvoiceItem.setSalesInvoiceItemId(rs.next()?rs.getString(1):"0");
						rs.close();
						stmtInsertSalesInvoiceItem.clearParameters();
						salesInvoiceItem.setInsertable(false);
					} else {
						stmtUpdateSalesInvoiceItem.setInt(1, Integer.parseInt(actionForm.getSalesInvoiceId()));
						stmtUpdateSalesInvoiceItem.setInt(2, Integer.parseInt(salesInvoiceItem.getItemId()));
						stmtUpdateSalesInvoiceItem.setInt(3, Integer.parseInt(salesInvoiceItem.getSalesInvoiceItemId()));
						stmtUpdateSalesInvoiceItem.execute();
					}					
										


					// Add Invoice Item Company Metal
					invoiceItemMetalIterator  = salesInvoiceItem.getCompanyMetalList().iterator();
					while (invoiceItemMetalIterator.hasNext()) {
						invoiceItemCompanyMetal = (SalesOrderInvoiceMetalForm) invoiceItemMetalIterator.next();
						if(invoiceItemCompanyMetal.isInsertable()) {
							stmtInsertInvoiceItemMetal.setInt(1, Integer.parseInt(salesInvoiceItem.getSalesInvoiceItemId())); //SALES_INVOICE_ITEM_ID 
							stmtInsertInvoiceItemMetal.setInt(2, Integer.parseInt(actionForm.getSalesInvoiceId())); //SALES_INVOICE_ID 
							stmtInsertInvoiceItemMetal.setInt(3, Integer.parseInt(invoiceItemCompanyMetal.getItemId())); //ITEM_ID
							stmtInsertInvoiceItemMetal.setFloat(4, Float.parseFloat(invoiceItemCompanyMetal.getWeight())); //WEIGHT
							stmtInsertInvoiceItemMetal.setInt(5, Integer.parseInt(invoiceItemCompanyMetal.getWastageUnitId())); //WEIGHT_UNIT_ID
							stmtInsertInvoiceItemMetal.setFloat(6, Float.parseFloat(invoiceItemCompanyMetal.getWastageRate())); //WASTAGE_RATE
							stmtInsertInvoiceItemMetal.setInt(7, Integer.parseInt(invoiceItemCompanyMetal.getWastageUnitId())); //WASTAGE_RATE_UNIT_ID
							stmtInsertInvoiceItemMetal.setFloat(8, Float.parseFloat(invoiceItemCompanyMetal.getNetWeight())); //NET_WEIGHT
							stmtInsertInvoiceItemMetal.setFloat(9, Float.parseFloat(invoiceItemCompanyMetal.getRate())); //RATE
							stmtInsertInvoiceItemMetal.setFloat(10, Float.parseFloat(invoiceItemCompanyMetal.getValue())); //ITEM_VALUE
							stmtInsertInvoiceItemMetal.execute();
							rs = stmtInsertInvoiceItemMetal.getGeneratedKeys();
							invoiceItemCompanyMetal.setSalesInvoiceItemMetalUsedId(rs.next()?rs.getString(1):"0");
							rs.close();
							stmtInsertInvoiceItemMetal.clearParameters();
							invoiceItemCompanyMetal.setInsertable(false);							
						} else {
							stmtUpdateInvoiceItemMetal.setInt(1, Integer.parseInt(salesInvoiceItem.getSalesInvoiceItemId())); //SALES_INVOICE_ITEM_ID 
							stmtUpdateInvoiceItemMetal.setInt(2, Integer.parseInt(actionForm.getSalesInvoiceId())); //SALES_INVOICE_ID 
							stmtUpdateInvoiceItemMetal.setInt(3, Integer.parseInt(invoiceItemCompanyMetal.getItemId())); //ITEM_ID
							stmtUpdateInvoiceItemMetal.setFloat(4, Float.parseFloat(invoiceItemCompanyMetal.getWeight())); //WEIGHT
							stmtUpdateInvoiceItemMetal.setInt(5, Integer.parseInt(invoiceItemCompanyMetal.getWastageUnitId())); //WEIGHT_UNIT_ID
							stmtUpdateInvoiceItemMetal.setFloat(6, Float.parseFloat(invoiceItemCompanyMetal.getWastageRate())); //WASTAGE_RATE
							stmtUpdateInvoiceItemMetal.setInt(7, Integer.parseInt(invoiceItemCompanyMetal.getWastageUnitId())); //WASTAGE_RATE_UNIT_ID
							stmtUpdateInvoiceItemMetal.setFloat(8, Float.parseFloat(invoiceItemCompanyMetal.getNetWeight())); //NET_WEIGHT
							stmtUpdateInvoiceItemMetal.setFloat(9, Float.parseFloat(invoiceItemCompanyMetal.getRate())); //RATE
							stmtUpdateInvoiceItemMetal.setFloat(10, Float.parseFloat(invoiceItemCompanyMetal.getValue())); //ITEM_VALUE
							stmtUpdateInvoiceItemMetal.setInt(11, Integer.parseInt(invoiceItemCompanyMetal.getSalesInvoiceItemMetalUsedId())); //SALES_INVOICE_ITEM_METAL_USED_ID
							stmtUpdateInvoiceItemMetal.execute();
						}
					}
					
					// Add Invoice Item Company Gem
					invoiceItemGemIterator  = salesInvoiceItem.getCompanyGemList().iterator();
					while (invoiceItemGemIterator.hasNext()) {
						invoiceItemCompanyGem = (SalesOrderInvoiceGemForm) invoiceItemGemIterator.next();
						if (invoiceItemCompanyGem.isInsertable()) {
							//Insert Inventroy Gem Entry Out
							stmtInsertInventoryGemEntry.setString(1, actionForm.getInvoiceDate());
							stmtInsertInventoryGemEntry.setInt(2,Default.SALES_OUT_ACCOUNT_ID);
							stmtInsertInventoryGemEntry.setInt(3,Integer.parseInt(invoiceItemCompanyGem.getItemId()));
							stmtInsertInventoryGemEntry.setInt(4,Integer.parseInt(invoiceItemCompanyGem.getQuantity()));
							stmtInsertInventoryGemEntry.setFloat(5,Float.parseFloat(invoiceItemCompanyGem.getWeight()));
							stmtInsertInventoryGemEntry.setInt(6,0);  //0=Out, 1=In
							stmtInsertInventoryGemEntry.setString(7,Default.SALES_ORDER_PREFIX);
							stmtInsertInventoryGemEntry.setString(8,actionForm.getSalesOrderTrackingId());
							stmtInsertInventoryGemEntry.setInt(9,Integer.parseInt(invoiceItemCompanyGem.getWeightUnitId()));
							stmtInsertInventoryGemEntry.execute();
							rs = stmtInsertInventoryGemEntry.getGeneratedKeys();
							invoiceItemCompanyGem.setInventoryGemEntryIdOut(rs.next()?rs.getString(1):"0");
							rs.close();
							stmtInsertInventoryGemEntry.clearParameters();

							
							stmtInsertInvoiceItemGem.setInt(1, Integer.parseInt(salesInvoiceItem.getSalesInvoiceItemId())); //SALES_INVOICE_ITEM_ID 
							stmtInsertInvoiceItemGem.setInt(2, Integer.parseInt(actionForm.getSalesInvoiceId())); //SALES_INVOICE_ID 
							stmtInsertInvoiceItemGem.setInt(3, Integer.parseInt(invoiceItemCompanyGem.getItemId())); //ITEM_ID
							stmtInsertInvoiceItemGem.setInt(4, Default.STOCK_TYPE_COMPANY); //INVENTORY_TYPE_ID
							stmtInsertInvoiceItemGem.setInt(5, Integer.parseInt(invoiceItemCompanyGem.getQuantity())); //QUANTITY 
							stmtInsertInvoiceItemGem.setFloat(6, Float.parseFloat(invoiceItemCompanyGem.getWeight()));  //WEIGHT
							stmtInsertInvoiceItemGem.setInt(7, Integer.parseInt(invoiceItemCompanyGem.getWeightUnitId())); //WEIGHT_UNIT_ID
							stmtInsertInvoiceItemGem.setFloat(8, Float.parseFloat(invoiceItemCompanyGem.getRate())); //RATE
							stmtInsertInvoiceItemGem.setFloat(9, Float.parseFloat(invoiceItemCompanyGem.getValue())); //ITEM_VALUE
							stmtInsertInvoiceItemGem.setInt(10, Integer.parseInt(invoiceItemCompanyGem.getValueCalculateBy())); //ITEM_VALUE_CALCULATE_BY
							stmtInsertInvoiceItemGem.setInt(11, Integer.parseInt(invoiceItemCompanyGem.getInventoryGemEntryIdOut())); 
							stmtInsertInvoiceItemGem.execute();
							rs = stmtInsertInvoiceItemGem.getGeneratedKeys();
							invoiceItemCompanyGem.setSalesInvoiceItemGemUsedId(rs.next()?rs.getString(1):"0");
							rs.close();
							invoiceItemCompanyGem.setInsertable(false);
							stmtInsertInvoiceItemGem.clearParameters();
						} else {
							//Insert Inventroy Gem Entry Out
							stmtUpdateInventoryGemEntry.setString(1, actionForm.getInvoiceDate());
							stmtUpdateInventoryGemEntry.setInt(2,Default.SALES_OUT_ACCOUNT_ID);
							stmtUpdateInventoryGemEntry.setInt(3,Integer.parseInt(invoiceItemCompanyGem.getItemId()));
							stmtUpdateInventoryGemEntry.setInt(4,Integer.parseInt(invoiceItemCompanyGem.getQuantity()));
							stmtUpdateInventoryGemEntry.setFloat(5,Float.parseFloat(invoiceItemCompanyGem.getWeight()));
							stmtUpdateInventoryGemEntry.setInt(6,0);  //0=Out, 1=In
							stmtUpdateInventoryGemEntry.setString(7,Default.SALES_ORDER_PREFIX);
							stmtUpdateInventoryGemEntry.setString(8,actionForm.getSalesOrderTrackingId());
							stmtUpdateInventoryGemEntry.setInt(9,Integer.parseInt(invoiceItemCompanyGem.getWeightUnitId()));
							stmtUpdateInventoryGemEntry.setInt(10,Integer.parseInt(invoiceItemCompanyGem.getInventoryGemEntryIdOut()));
							stmtUpdateInventoryGemEntry.execute();
//							rs = stmtUpdateInventoryGemEntry.getGeneratedKeys();
//							invoiceItemCompanyGem.setInventoryGemEntryIdOut(rs.next()?rs.getString(1):"0");
//							rs.close();
							stmtUpdateInventoryGemEntry.clearParameters();
							
							
							//Invoice Gem Items
							stmtUpdateInvoiceItemGem.setInt(1, Integer.parseInt(salesInvoiceItem.getSalesInvoiceItemId())); //SALES_INVOICE_ITEM_ID 
							stmtUpdateInvoiceItemGem.setInt(2, Integer.parseInt(actionForm.getSalesInvoiceId())); //SALES_INVOICE_ID 
							stmtUpdateInvoiceItemGem.setInt(3, Integer.parseInt(invoiceItemCompanyGem.getItemId())); //ITEM_ID
							stmtUpdateInvoiceItemGem.setInt(4, Default.STOCK_TYPE_COMPANY); //INVENTORY_TYPE_ID
							stmtUpdateInvoiceItemGem.setInt(5, Integer.parseInt(invoiceItemCompanyGem.getQuantity())); //QUANTITY 
							stmtUpdateInvoiceItemGem.setFloat(6, Float.parseFloat(invoiceItemCompanyGem.getWeight()));  //WEIGHT
							stmtUpdateInvoiceItemGem.setInt(7, Integer.parseInt(invoiceItemCompanyGem.getWeightUnitId())); //WEIGHT_UNIT_ID
							stmtUpdateInvoiceItemGem.setFloat(8, Float.parseFloat(invoiceItemCompanyGem.getRate())); //RATE
							stmtUpdateInvoiceItemGem.setFloat(9, Float.parseFloat(invoiceItemCompanyGem.getValue())); //ITEM_VALUE
							stmtUpdateInvoiceItemGem.setInt(10, Integer.parseInt(invoiceItemCompanyGem.getValueCalculateBy())); //ITEM_VALUE_CALCULATE_BY
							stmtUpdateInvoiceItemGem.setInt(11, Integer.parseInt(invoiceItemCompanyGem.getSalesInvoiceItemGemUsedId())); //SALES_INVOICE_ITEM_GEM_USED_ID
							stmtUpdateInvoiceItemGem.execute();


						}
					}
					
					//Add Invoice Item Customer Gem
					invoiceItemGemIterator  = salesInvoiceItem.getCustomerGemList().iterator();
					while (invoiceItemGemIterator.hasNext()) {
						invoiceItemCustomerGem = (SalesOrderInvoiceGemForm) invoiceItemGemIterator.next();
						if (invoiceItemCustomerGem.isInsertable()) {
							stmtInsertInvoiceItemGem.setInt(1, Integer.parseInt(salesInvoiceItem.getSalesInvoiceItemId())); //SALES_INVOICE_ITEM_ID 
							stmtInsertInvoiceItemGem.setInt(2, Integer.parseInt(actionForm.getSalesInvoiceId())); //SALES_INVOICE_ID 
							stmtInsertInvoiceItemGem.setInt(3, Integer.parseInt(invoiceItemCustomerGem.getItemId())); //ITEM_ID
							stmtInsertInvoiceItemGem.setInt(4, Default.STOCK_TYPE_CUSTOMER); //INVENTORY_TYPE_ID
							stmtInsertInvoiceItemGem.setInt(5, Integer.parseInt(invoiceItemCustomerGem.getQuantity())); //QUANTITY 
							stmtInsertInvoiceItemGem.setFloat(6, Float.parseFloat(invoiceItemCustomerGem.getWeight()));  //WEIGHT
							stmtInsertInvoiceItemGem.setInt(7, Integer.parseInt(invoiceItemCustomerGem.getWeightUnitId())); //WEIGHT_UNIT_ID
							stmtInsertInvoiceItemGem.setFloat(8, 0.0f); //RATE
							stmtInsertInvoiceItemGem.setFloat(9, 0.0f); //ITEM_VALUE
							stmtInsertInvoiceItemGem.setInt(10, Default.WEIGHT_IN_CARAT); //ITEM_VALUE_CALCULATE_BY
							stmtInsertInvoiceItemGem.setInt(11, 0);
							stmtInsertInvoiceItemGem.execute();
							rs = stmtInsertInvoiceItemGem.getGeneratedKeys();
							invoiceItemCustomerGem.setSalesInvoiceItemGemUsedId(rs.next()?rs.getString(1):"0");
							rs.close();
							stmtInsertInvoiceItemGem.clearParameters();
							invoiceItemCustomerGem.setInsertable(false);
						} else {
							stmtUpdateInvoiceItemGem.setInt(1, Integer.parseInt(salesInvoiceItem.getSalesInvoiceItemId())); //SALES_INVOICE_ITEM_ID 
							stmtUpdateInvoiceItemGem.setInt(2, Integer.parseInt(actionForm.getSalesInvoiceId())); //SALES_INVOICE_ID 
							stmtUpdateInvoiceItemGem.setInt(3, Integer.parseInt(invoiceItemCustomerGem.getItemId())); //ITEM_ID
							stmtUpdateInvoiceItemGem.setInt(4, Default.STOCK_TYPE_CUSTOMER); //INVENTORY_TYPE_ID
							stmtUpdateInvoiceItemGem.setInt(5, Integer.parseInt(invoiceItemCustomerGem.getQuantity())); //QUANTITY 
							stmtUpdateInvoiceItemGem.setFloat(6, Float.parseFloat(invoiceItemCustomerGem.getWeight()));  //WEIGHT
							stmtUpdateInvoiceItemGem.setInt(7, Integer.parseInt(invoiceItemCustomerGem.getWeightUnitId())); //WEIGHT_UNIT_ID
							stmtUpdateInvoiceItemGem.setFloat(8, 0.0f); //RATE
							stmtUpdateInvoiceItemGem.setFloat(9, 0.0f); //ITEM_VALUE
							stmtUpdateInvoiceItemGem.setInt(10, Default.WEIGHT_IN_CARAT); //ITEM_VALUE_CALCULATE_BY
							stmtUpdateInvoiceItemGem.setInt(11, Integer.parseInt(invoiceItemCustomerGem.getSalesInvoiceItemGemUsedId())); //SALES_INVOICE_ITEM_GEM_USED_ID
							stmtUpdateInvoiceItemGem.execute();
						}
					}

				}
				
				connection.commit();
				connection.close();

			} catch (Exception e){
				if (connection != null) {
					try {
						connection.rollback();
					} catch (SQLException sqle){
						ActionMessages serviceErrors = new ActionMessages();
						serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
						saveErrors(request,serviceErrors); 
					}
					ActionMessages serviceErrors = new ActionMessages();
					serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
					saveErrors(request,serviceErrors);
				}
				return (mapping.findForward("FAIL"));	
			}	
			actionForm.setHasFormInitialized('N');
		 }
		
		return (mapping.findForward("SUCCESS"));
	}

}
