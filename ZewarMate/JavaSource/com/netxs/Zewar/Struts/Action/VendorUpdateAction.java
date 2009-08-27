
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Struts.Form.VendorForm;
import com.netxs.Zewar.Struts.Form.VendorItemForm;

public class VendorUpdateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

//		String xquery = " ";
		VendorForm actionForm = (VendorForm) form;
		//check for cart is valid if not then creat new one
		try {
			Integer.parseInt(actionForm.getVendorId());
		}catch(Exception e){
			actionForm.setVendorId("0");
		}
		if(actionForm.getHasFormInitialized()!= 'Y'){//Initialize Cart
			try {
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection(); 
				ResultSet rs;
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				String query = 	"SELECT * FROM vendors WHERE VENDOR_ID="+actionForm.getVendorId();
				rs = stmt.executeQuery(query);
				if (rs.next()){
					actionForm.setNameTitle(rs.getString("name_title"));
					actionForm.setFirstName(rs.getString("first_name"));
					actionForm.setMiddleName(rs.getString("middle_name"));
					actionForm.setLastName(rs.getString("last_name"));
					actionForm.setCompanyName(rs.getString("company_name"));
					actionForm.setAddressLine1(rs.getString("address_line1"));
					actionForm.setAddressLine2(rs.getString("address_line2"));
					actionForm.setPhone1(rs.getString("phone_1"));
					actionForm.setPhone2(rs.getString("phone_2"));
					actionForm.setMobile(rs.getString("mobile"));
					actionForm.setEmail1(rs.getString("email_1"));
					actionForm.setEmail2(rs.getString("email_2"));
					actionForm.setLedgerAccountId(rs.getString("ledger_account_id"));
					actionForm.setBodyMakingRateSimple(rs.getString("body_making_rate_simple"));
					actionForm.setBodyMakingRateMix(rs.getString("body_making_rate_mix"));
					actionForm.setStoneSettingRateSimple(rs.getString("stone_setting_rate_simple"));
					actionForm.setStoneSettingRateDifficult(rs.getString("stone_setting_rate_difficult"));
					
				}else{
					Exception e = new Exception("Invalid Customer");
					throw e;
				}
				rs.close();
				//Vendor Agreed Wastage
				rs = stmt.executeQuery("SELECT	IT.ITEM_ID, IT.ITEM_NAME, VI.AGREED_WASTAGE, VI.ITEM_ID FROM VENDOR_ITEMS_RELATION VI	RIGHT OUTER JOIN  items IT ON VI.ITEM_ID = IT.ITEM_ID  AND  VI.VENDOR_ID="+actionForm.getVendorId()+" WHERE IT.ITEM_GROUP_ID = 1");
				if (rs.next()) {
					VendorItemForm itemAgreedWastage; 
					do{
						itemAgreedWastage = new VendorItemForm();
						itemAgreedWastage.setItemId(rs.getString(1));
						itemAgreedWastage.setItemName(rs.getString(2));
						itemAgreedWastage.setAgreedWastage(rs.getString(3));
						itemAgreedWastage.setAgreedWastageUnitId("2");
						itemAgreedWastage.setInsertable(rs.getInt(1)!=rs.getInt(4));
						actionForm.setMetalWastage(itemAgreedWastage);
					}while (rs.next());
				}
				rs.close();
				
				query = 	"SELECT * FROM vendor_types_relation WHERE VENDOR_ID="+actionForm.getVendorId();
				rs = stmt.executeQuery(query);
				if (rs.next()){
					rs.last();
					String [] vendorTypeId = new String [rs.getRow()]; 
					rs.first();
					do{
						vendorTypeId [rs.getRow()-1]= rs.getString("vendor_type_id");
					}while(rs.next());
					actionForm.setVendorTypeId(vendorTypeId);
				}
				rs.close();
				stmt.close();
				connection.close();
				actionForm.setHasFormInitialized('Y');
			} catch(Exception e) {
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	 
			}
			actionForm.setHasFormInitialized('Y');	 
			return (mapping.findForward("SUCCESS"));	 
		} else { // Update Cart
			try {
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				String query = 	" UPDATE vendors " 
											+	" SET "
											+ "   NAME_TITLE='" + actionForm.getNameTitle()  
											+	"', FIRST_NAME='" + actionForm.getFirstName()
											+	"', LAST_NAME='" + actionForm.getLastName()
											+	"', MIDDLE_NAME='" + actionForm.getMiddleName()
											+	"', COMPANY_NAME='" + actionForm.getCompanyName()
											+	"', ADDRESS_LINE1='" + actionForm.getAddressLine1()
											+	"', ADDRESS_LINE2='" + actionForm.getAddressLine2()
											+	"', PHONE_1='" + actionForm.getPhone1()
											+	"', PHONE_2='" + actionForm.getPhone2()
											+	"', MOBILE='" + actionForm.getMobile()
											+	"', EMAIL_1='" + actionForm.getEmail1()
											+	"', EMAIL_2='" + actionForm.getEmail2()
											+	"', BODY_MAKING_RATE_SIMPLE='" + actionForm.getBodyMakingRateSimple()
											+	"', BODY_MAKING_RATE_MIX='" + actionForm.getBodyMakingRateMix()
											+	"', STONE_SETTING_RATE_SIMPLE='" + actionForm.getStoneSettingRateSimple()
											+	"', STONE_SETTING_RATE_DIFFICULT='" + actionForm.getStoneSettingRateDifficult()
											+ "' WHERE VENDOR_ID="+actionForm.getVendorId(); 
				stmt.executeUpdate(query);

				//Update Vendor Agreed Wastage Rates
				VendorItemForm itemAgreedWastage ;
				for(int index=0; index < actionForm.getMetalWastageList().size(); index++){
					itemAgreedWastage = (VendorItemForm)actionForm.getMetalWastage(index);
					if (itemAgreedWastage.isInsertable()){
						stmt.executeUpdate("INSERT INTO VENDOR_ITEMS_RELATION	(VENDOR_ID, ITEM_ID, AGREED_WASTAGE, AGREED_WASTAGE_UNIT_ID )	VALUES("+actionForm.getVendorId()+","+itemAgreedWastage.getItemId()+",'"+itemAgreedWastage.getAgreedWastage()+"', '"+itemAgreedWastage.getAgreedWastageUnitId()+"')");
						itemAgreedWastage.setInsertable(false);
					} else {
						stmt.executeUpdate("UPDATE VENDOR_ITEMS_RELATION	SET AGREED_WASTAGE = '"+itemAgreedWastage.getAgreedWastage()+"' ,AGREED_WASTAGE_UNIT_ID='"+itemAgreedWastage.getAgreedWastageUnitId()+"'  WHERE VENDOR_ID="+actionForm.getVendorId()+" AND ITEM_ID="+itemAgreedWastage.getItemId());
					}
				}
				
				//Add Vendor Types
				query = "DELETE FROM vendor_types_relation WHERE VENDOR_ID="+actionForm.getVendorId();
				stmt.executeUpdate(query);
				
				if (actionForm.getVendorTypeId() != null){
					for(int x=0; x<actionForm.getVendorTypeId().length; x++ ){
						query = "INSERT INTO vendor_types_relation (VENDOR_ID, VENDOR_TYPE_ID)" 
									+ " VALUES (" 
									+ 			actionForm.getVendorId()
									+ ", " + 	actionForm.getVendorTypeId()[x] 
									+	" ) ";
						stmt.executeUpdate(query);
					}
				}
				stmt.executeUpdate(
						"UPDATE ledger_accounts SET " 
							+ " title='"+actionForm.getNameTitle()+" "+actionForm.getFirstName()+" "+actionForm.getMiddleName()+" "+actionForm.getLastName()+"'" 
							+ ", description='***Vendor Account "+actionForm.getNameTitle()+" "+actionForm.getFirstName()+" "+actionForm.getMiddleName()+" "+actionForm.getLastName()+"*** ***Company Name "+actionForm.getCompanyName()+"***'"  
							+" WHERE ledger_account_id="+actionForm.getLedgerAccountId()
				);
				stmt.close();
				connection.close();
				actionForm.setHasFormInitialized('Y');
			} catch(Exception e) {
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	 
			}
		}
		return (mapping.findForward("SUCCESS"));
	}
}