
package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netxs.Zewar.Lookup.LookupLedgerAccount;
import com.netxs.Zewar.Lookup.LookupInventoryAccount;
import com.netxs.Zewar.Lookup.LookupCustomer;
import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Struts.Form.CustomerForm;

public class CustomerCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		CustomerForm actionForm = (CustomerForm) form;
		// Create Cart
		if (actionForm.getHasFormInitialized()=='Y'){
			try { 
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				ResultSet rs;
				int customerControlLedgerAccountId = 0; 
				int accountCodePostfix=0;
				int ledgerAccountId;
				//Check customer not exist 
				rs = stmt.executeQuery("SELECT * FROM customers WHERE first_name='"+actionForm.getFirstName()+"' AND last_name='"+actionForm.getLastName()+"'");
				if (rs.next()){
					ActionMessages serviceErrors = new ActionErrors();
					serviceErrors.add("error",new ActionMessage("errors","Customer already exist : "+rs.getString("name_title")+" "+rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name")));
					saveErrors(request,serviceErrors);
					return (mapping.findForward("FAIL"));
				}
					
				//Create Ledger Account for Customer
				//Step 1 get Customer Control Account Id
				rs = stmt.executeQuery("SELECT LEDGER_ACCOUNT_ID FROM ledger_accounts WHERE account_code_prefix='CS' AND account_code_postfix=0");
				customerControlLedgerAccountId = (rs.next())? rs.getInt(1) : 0;

				//Step 2 get Max postfix for Customer Control Account
				rs = stmt.executeQuery("SELECT IFNULL(MAX(ACCOUNT_CODE_POSTFIX)+1, 1) FROM ledger_accounts WHERE account_code_prefix='CS'");
				accountCodePostfix = (rs.next())? rs.getInt(1) : 0;

				//Step 3 add ledger account
				stmt.execute(
					" INSERT INTO ledger_accounts (ledger_account_type_id, parent_ledger_account_id, title, description, account_code_prefix, account_code_postfix, account_active, opening_balance, entry_debit_credit, account_description_level, account_create_date, is_inventory_account) " 
				 +" VALUES( 2 "  
						+", "+ customerControlLedgerAccountId 
						+",'"+ actionForm.getNameTitle()+" "+actionForm.getFirstName()+" "+actionForm.getMiddleName()+" "+actionForm.getLastName()+"'"
						+",'***Customer Account "+actionForm.getNameTitle()+" "+actionForm.getFirstName()+" "+actionForm.getMiddleName()+" "+actionForm.getLastName()+"*** ***Company Name "+actionForm.getCompanyName()+"***'"
						+",'CS'"
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
//				rs = stmt.executeQuery("SELECT MAX(LEDGER_ACCOUNT_ID) FROM ledger_accounts");
				rs = stmt.getGeneratedKeys();
				ledgerAccountId = (rs.next())? rs.getInt(1) : 0;

				stmt.execute(
						"INSERT INTO customers  (NAME_TITLE, FIRST_NAME, LAST_NAME, MIDDLE_NAME, COMPANY_NAME, ADDRESS_LINE1, ADDRESS_LINE2, CITY_NAME, COUNTRY_ID, PHONE_1, PHONE_2, MOBILE, EMAIL_1, EMAIL_2, COMMENTS , LEDGER_ACCOUNT_ID) " 
					+	" VALUES( " 
					+	"  '" +	actionForm.getNameTitle() 
					+	"','" + actionForm.getFirstName()
					+	"','" + actionForm.getLastName()
					+	"','" + actionForm.getMiddleName()
					+	"','" + actionForm.getCompanyName()  
					+	"','" + actionForm.getAddressLine1()
					+	"','" + actionForm.getAddressLine2()
					+	"','" + actionForm.getCityName()
					+	"', " + actionForm.getCountryId()
					+	" ,'" + actionForm.getPhone1()
					+	"','" + actionForm.getPhone2()
					+	"','" + actionForm.getMobile()
					+	"','" + actionForm.getEmail1()
					+	"','" + actionForm.getEmail2()
					+	"','" + actionForm.getComments()
					+ "', " + ledgerAccountId
					+	")"
				);
//				rs = stmt.executeQuery("SELECT Max(customer_id ) FROM customers");
				rs = stmt.getGeneratedKeys();
				if (rs.next()){
					actionForm.setCustomerId(rs.getString(1));
					actionForm.setHasFormInitialized('Y');
				}
				stmt.close();
				connection.close();
				LookupCustomer.refreshCache();
			} catch(Exception e) {
				ActionMessages serviceErrors = new ActionErrors();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	 
			}
		} else {
			actionForm.setHasFormInitialized('Y');
			actionForm.setCountryId("1");
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
