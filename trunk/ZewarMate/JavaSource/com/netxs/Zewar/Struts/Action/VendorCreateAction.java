
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
import com.netxs.Zewar.Lookup.LookupInventoryAccount;
import com.netxs.Zewar.Lookup.LookupLedgerAccount;
import com.netxs.Zewar.Struts.Form.VendorForm;
import com.netxs.Zewar.Struts.Form.VendorItemForm;

public class VendorCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		VendorForm actionForm = (VendorForm) form;
		// Create Cart

		Connection connection;
		connection = (Connection) new DBConnection().getMyPooledConnection();
		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
		ResultSet rs;
		
		VendorItemForm itemAgreedWastage ;
		
		if (actionForm.getHasFormInitialized()=='Y'){
			try { 
				int vendorControlLedgerAccountId = 0;  
				int accountCodePostfix=0;
				int ledgerAccountId;
				//Create Ledger Account for Vendors

				//Step 1 get Customer Control Account Id
				rs = stmt.executeQuery("SELECT LEDGER_ACCOUNT_ID FROM ledger_accounts WHERE account_code_prefix='VN' AND account_code_postfix=0");
				vendorControlLedgerAccountId = (rs.next())? rs.getInt(1) : 0;

				//Step 2 get Max postfix for Customer Control Account
				rs = stmt.executeQuery("SELECT IFNULL(MAX(ACCOUNT_CODE_POSTFIX)+1, 1) FROM ledger_accounts WHERE account_code_prefix='VN'");
				accountCodePostfix = (rs.next())? rs.getInt(1) : 0;

				//Step 3 add ledger account
				stmt.executeUpdate(
						 " INSERT INTO ledger_accounts (ledger_account_type_id, parent_ledger_account_id, title, description, account_code_prefix, account_code_postfix, account_active, opening_balance, entry_debit_credit, account_description_level, account_create_date, is_inventory_account) " 
						+" VALUES( 3 "  
						+", "+ vendorControlLedgerAccountId 
						+",'"+ actionForm.getNameTitle()+" "+actionForm.getFirstName()+" "+actionForm.getMiddleName()+" "+actionForm.getLastName()+"'"
						+",'***Vendor Account "+actionForm.getNameTitle()+" "+actionForm.getFirstName()+" "+actionForm.getMiddleName()+" "+actionForm.getLastName()+"*** ***Company Name "+actionForm.getCompanyName()+"***'"
						+",'VN'"
						+", "+ accountCodePostfix
						+",'Y'"
						+",0.0"
						+",'D'"
						+",2"
						+",curDate()"
						+",1"
						+")"
				);
				LookupLedgerAccount.refreshCache();
				LookupInventoryAccount.refreshCache();
				rs = stmt.executeQuery("SELECT MAX(LEDGER_ACCOUNT_ID) FROM ledger_accounts");
				ledgerAccountId = (rs.next())? rs.getInt(1) : 0;

				String query = 	"INSERT INTO vendors  (NAME_TITLE, FIRST_NAME, LAST_NAME, MIDDLE_NAME, COMPANY_NAME, ADDRESS_LINE1, ADDRESS_LINE2, PHONE_1, PHONE_2, MOBILE, EMAIL_1, EMAIL_2, LEDGER_ACCOUNT_ID, BODY_MAKING_RATE_SIMPLE, BODY_MAKING_RATE_MIX, STONE_SETTING_RATE_SIMPLE, STONE_SETTING_RATE_DIFFICULT ) " 
											+	"VALUES( " 
											+	"  '" +	actionForm.getNameTitle() 
											+	"','" + actionForm.getFirstName()
											+	"','" + actionForm.getLastName()
											+	"','" + actionForm.getMiddleName()
											+	"','" + actionForm.getCompanyName()
											+	"','" + actionForm.getAddressLine1()
											+	"','" + actionForm.getAddressLine2()
											+	"','" + actionForm.getPhone1()
											+	"','" + actionForm.getPhone2()
											+	"','" + actionForm.getMobile()
											+	"','" + actionForm.getEmail1()
											+	"','" + actionForm.getEmail2()
											+ 	"','" + ledgerAccountId
											+ 	"','" + actionForm.getBodyMakingRateSimple()
											+ 	"','" + actionForm.getBodyMakingRateMix()
											+ 	"','" + actionForm.getStoneSettingRateSimple()
											+ 	"','" + actionForm.getStoneSettingRateDifficult()
											+	"')";
				stmt.executeUpdate(query);
				rs = stmt.getGeneratedKeys();
				
				if (rs.next()){
					actionForm.setVendorId(rs.getString(1));
					actionForm.setHasFormInitialized('Y');
				} 
				
				// Add Vendor Types
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

				//Vendor Items Agreed Wastage
				for(int index=0; index < actionForm.getMetalWastageList().size(); index++){
					itemAgreedWastage = (VendorItemForm)actionForm.getMetalWastage(index);
					stmt.executeUpdate("INSERT INTO vendor_items_relation 	(VENDOR_ID, ITEM_ID, AGREED_WASTAGE, AGREED_WASTAGE_UNIT_ID )	VALUES("+actionForm.getVendorId()+","+itemAgreedWastage.getItemId()+",'"+itemAgreedWastage.getAgreedWastage()+"', '"+itemAgreedWastage.getAgreedWastageUnitId()+"')");
					itemAgreedWastage.setInsertable(false);
				}

				stmt.close();
				connection.close();
			} catch(Exception e) {
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	 
			}
		} else {
			
			rs = stmt.executeQuery("SELECT	IT.ITEM_ID, IT.ITEM_NAME FROM items IT WHERE IT.ITEM_GROUP_ID = 1");
			if (rs.next()) {
				do{
					itemAgreedWastage = new VendorItemForm();
					itemAgreedWastage.setItemId(rs.getString(1));
					itemAgreedWastage.setItemName(rs.getString(2));
					itemAgreedWastage.setAgreedWastage("0.00");
					itemAgreedWastage.setAgreedWastageUnitId("2");
					itemAgreedWastage.setInsertable(true);
					actionForm.setMetalWastage(itemAgreedWastage);
				}while (rs.next());
			}
			
			actionForm.setHasFormInitialized('Y');
			return (mapping.findForward("FAIL"));
		}
		//check user request for add another
		if(!request.getParameter("submitAction").trim().equalsIgnoreCase("save")){
			actionForm.setHasFormInitialized('N');
			return (mapping.findForward("SUCCESS_ADD_ANOTHER"));	
		}
		return (mapping.findForward("SUCCESS"));
	}

}
