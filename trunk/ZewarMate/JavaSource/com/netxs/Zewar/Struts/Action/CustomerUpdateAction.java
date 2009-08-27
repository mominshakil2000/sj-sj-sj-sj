
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

import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Lookup.LookupCustomer;
import com.netxs.Zewar.Struts.Form.CustomerForm;

public class CustomerUpdateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		CustomerForm actionForm = (CustomerForm) form;
		//check for cart is valid if not then creat new one
		try {
			Integer.parseInt(actionForm.getCustomerId());
		}catch(Exception e){
			actionForm.setCustomerId("0");
		}
		if(actionForm.getHasFormInitialized()!= 'Y'){//Initialize Cart
			try {
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				ResultSet rs;
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				String query = 	"SELECT * FROM customers WHERE CUSTOMER_ID="+actionForm.getCustomerId();
				rs = stmt.executeQuery(query);
				if (rs.next()){
					actionForm.setNameTitle(rs.getString("name_title"));
					actionForm.setFirstName(rs.getString("first_name"));
					actionForm.setMiddleName(rs.getString("middle_name"));
					actionForm.setLastName(rs.getString("last_name"));
					actionForm.setCompanyName(rs.getString("company_name"));
					actionForm.setAddressLine1(rs.getString("address_line1"));
					actionForm.setAddressLine2(rs.getString("address_line2"));
					actionForm.setCityName(rs.getString("city_name"));
					actionForm.setCountryId(rs.getString("country_id"));
					actionForm.setPhone1(rs.getString("phone_1"));
					actionForm.setPhone2(rs.getString("phone_2"));
					actionForm.setMobile(rs.getString("mobile"));
					actionForm.setEmail1(rs.getString("email_1"));
					actionForm.setEmail2(rs.getString("email_2"));
					actionForm.setComments(rs.getString("comments"));
					actionForm.setLedgerAccountId(rs.getString("ledger_account_id"));
					actionForm.setHasFormInitialized('Y');
				}else{
					Exception e = new Exception("Invalid Customer");
					throw e;
				}
				stmt.close();
				connection.close();
				actionForm.setHasFormInitialized('Y');
			} catch(Exception e) {
				ActionMessages serviceErrors = new ActionErrors();
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
				String query = 	" UPDATE customers " 
											+	" SET "
											+ "   NAME_TITLE='" + actionForm.getNameTitle()  
											+	"', FIRST_NAME='" + actionForm.getFirstName()
											+	"', LAST_NAME='" + actionForm.getLastName()
											+	"', MIDDLE_NAME='" + actionForm.getMiddleName()
											+	"', COMPANY_NAME='" + actionForm.getCompanyName()
											+	"', ADDRESS_LINE1='" + actionForm.getAddressLine1()
											+	"', ADDRESS_LINE2='" + actionForm.getAddressLine2()
											+	"', CITY_NAME='" + actionForm.getCityName()
											+	"', COUNTRY_ID='" + actionForm.getCountryId()
											+	"', PHONE_1='" + actionForm.getPhone1()
											+	"', PHONE_2='" + actionForm.getPhone2()
											+	"', MOBILE='" + actionForm.getMobile()
											+	"', EMAIL_1='" + actionForm.getEmail1()
											+	"', EMAIL_2='" + actionForm.getEmail2()
											+	"', COMMENTS='" +  actionForm.getComments()+"'"
											+ " WHERE CUSTOMER_ID="+actionForm.getCustomerId(); 
				stmt.executeUpdate(query);
				stmt.executeUpdate(
					 "UPDATE ledger_accounts SET" 
							+ " title='"+actionForm.getNameTitle()+" "+actionForm.getFirstName()+" "+actionForm.getMiddleName()+" "+actionForm.getLastName()+"'" 
							+ ", description='***Customer Account "+actionForm.getNameTitle()+" "+actionForm.getFirstName()+" "+actionForm.getMiddleName()+" "+actionForm.getLastName()+"*** ***Company Name "+actionForm.getCompanyName()+"***'"  
							+" WHERE ledger_account_id="+actionForm.getLedgerAccountId()
				);
				stmt.close();
				connection.close();
				LookupCustomer.refreshCache();
				actionForm.setHasFormInitialized('Y');
			} catch(Exception e) {
				ActionMessages serviceErrors = new ActionErrors();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));	 
			}
		}
		
		return (mapping.findForward("SUCCESS"));
	}

}
