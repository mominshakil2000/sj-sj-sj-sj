
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Default;
import com.netxs.Zewar.Struts.Form.SalesOrderInvoiceForm;
import com.netxs.Zewar.Struts.Form.SalesOrderInvoiceGemForm;
import com.netxs.Zewar.Struts.Form.SalesOrderInvoiceItemForm;
import com.netxs.Zewar.Struts.Form.SalesOrderInvoiceMetalForm;

import com.netxs.Zewar.DataSources.DBConnection;
import java.text.*;
import java.util.*;

public class SalesOrderMoveInInventoryAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		
		SalesOrderInvoiceForm actionForm = (SalesOrderInvoiceForm) form;
		SalesOrderInvoiceItemForm salesInvoiceItem;
		SalesOrderInvoiceMetalForm invoiceItemCompanyMetal;
		SalesOrderInvoiceGemForm invoiceItemCompanyGem;
		SalesOrderInvoiceGemForm invoiceItemCustomerGem;
		
		Connection connection;
		connection = (Connection) new DBConnection().getMyPooledConnection();
		connection.setAutoCommit(false);
		// Make a metal Item Inventory Entry
		PreparedStatement stmtInsertInventoryMetalItemEntry = connection.prepareStatement("INSERT INTO inventory_metal_item_entries (ENTRY_DATE, INVENTORY_ACCOUNT_ID, METAL_ITEM_ID, JEWELLERY_ITEM_ID, WEIGHT, IN_OUT_STATUS, VOUCHER_PREFIX, VOUCHER_POSTFIX )	VALUES(?,?,?,?,?,?,?,?)");
		PreparedStatement stmtInsertOrderInventoryMetalItemEntryRelation = connection.prepareStatement("INSERT INTO sales_order_inventory_metal_items (SALES_ORDER_ID, INVENTORY_MTAL_ITEM_ENTRY_ID)	VALUES(?,?)");

		ResultSet rs, rs2 ;
		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
		Statement stmt2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
		String query;
		float totalGemsWeight = 0.0f;
		float totalMetalValue = 0.0F,totalGemsValue = 0.0F;
		float weight = 0.0f, netWeight = 0.0f, totalBodyMaking = 0.0f;
		try {
			//Sales Order
			rs = stmt.executeQuery("SELECT * FROM sales_orders SO WHERE SO.SALES_ORDER_ID="+actionForm.getSalesOrderId());
			if (rs.next()){
				actionForm.setSalesOrderTrackingId(rs.getString("SALES_ORDER_TRACKING_ID"));
				actionForm.setInsertable(true);
				actionForm.setHasFormInitialized('Y');
				actionForm.setSalesInvoiceStatus(rs.getString("SALES_INVOICE_STATUS"));
				actionForm.setMoveInInventoryStatus(rs.getString("MOVE_IN_INVENTORY_STATUS"));
			} else {
				Exception e = new Exception("Unknow Sales Order");
				throw e;
			}
			rs.close();
			if (actionForm.getMoveInInventoryStatus().equals("0") && actionForm.getSalesInvoiceStatus().equals("0")){
				
					   
					rs = stmt.executeQuery(
							" SELECT *  FROM sales_order_items OI "
							+" INNER JOIN sales_order_item_info II ON II.SALES_ORDER_ITEM_ID = OI.SALES_ORDER_ITEM_ID"
							+" INNER JOIN items  IT ON OI.JEWELLERY_ITEM_ID = IT.ITEM_ID" 
							+" WHERE II.SALES_ORDER_ID="+actionForm.getSalesOrderId());
					
					
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

										
					
					Iterator oIterator ;
					
					oIterator = (actionForm.getInvoiceItemList()).iterator();
					while (oIterator.hasNext()){
						
						
						SalesOrderInvoiceItemForm invoiceItem = (SalesOrderInvoiceItemForm) oIterator.next();
						
						if (invoiceItem.getCompanyMetalList().size()!=0 && invoiceItem!= null){
						
							invoiceItemCompanyMetal = (SalesOrderInvoiceMetalForm) invoiceItem.getCompanyMetal(0);
							
							//Insert Inventroy Metal Item Entry Out
							stmtInsertInventoryMetalItemEntry.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
							stmtInsertInventoryMetalItemEntry.setInt(2,Default.SALES_OUT_ACCOUNT_ID);
	//						stmtInsertInventoryMetalItemEntry.setInt(3,Integer.parseInt("0"));
							stmtInsertInventoryMetalItemEntry.setInt(3,Integer.parseInt(invoiceItemCompanyMetal.getItemId()));
	//						stmtInsertInventoryMetalItemEntry.setInt(4,Integer.parseInt("0"));
							stmtInsertInventoryMetalItemEntry.setInt(4,Integer.parseInt(invoiceItem.getItemId()));
							stmtInsertInventoryMetalItemEntry.setFloat(5,Float.parseFloat(invoiceItemCompanyMetal.getWeight()));
							stmtInsertInventoryMetalItemEntry.setInt(6,1);  //0=Out, 1=In
							stmtInsertInventoryMetalItemEntry.setString(7,Default.SALES_ORDER_PREFIX);
							stmtInsertInventoryMetalItemEntry.setString(8, actionForm.getSalesOrderTrackingId());
							stmtInsertInventoryMetalItemEntry.execute();
							rs = stmtInsertInventoryMetalItemEntry.getGeneratedKeys();
							invoiceItem.setInventoryMetalItemEntryIdIn(rs.next()?rs.getString(1):"0");
							rs.close();
							stmtInsertInventoryMetalItemEntry.clearParameters();
							
							
							
							//sales Order and Inventory Metal Item Entry Relation
							stmtInsertOrderInventoryMetalItemEntryRelation.setInt(1,Integer.parseInt(actionForm.getSalesOrderId()));
							stmtInsertOrderInventoryMetalItemEntryRelation.setInt(2,Integer.parseInt(invoiceItem.getInventoryMetalItemEntryIdIn()));
							stmtInsertOrderInventoryMetalItemEntryRelation.execute();
							stmtInsertOrderInventoryMetalItemEntryRelation.clearParameters();
						
					}
					}
					//Update Moved In Inventory Status = In|1
					stmt2.executeUpdate("UPDATE sales_orders SET MOVE_IN_INVENTORY_STATUS=1, SALES_ORDER_STATUS_ID="+Default.SALES_ORDER_STATUS_COMPLETE+" WHERE SALES_ORDER_ID="+actionForm.getSalesOrderId());

					connection.commit();
			} else {
				try {connection.rollback();}catch (SQLException sqle){}
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors","You cannot move in inventory. Either sales invoice created or already moved in inventory."));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	
				
			}
			
			connection.close();
		} catch(Exception e) {
			try {connection.rollback();}catch (SQLException sqle){}
			ActionMessages serviceErrors = new ActionMessages();
			serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,serviceErrors);
			return (mapping.findForward("FAIL"));	 
		}
			
		return (mapping.findForward("SUCCESS"));
	}

}
