
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
import com.netxs.Zewar.Struts.Form.VendorGroupForm;

public class VendorGroupUpdateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		VendorGroupForm actionForm = (VendorGroupForm) form;
		//check for cart is valid if not then creat new one
		try {
			Integer.parseInt(actionForm.getVendorTypeId());
		}catch(Exception e){
			actionForm.setVendorTypeId("0");
		}
		if(actionForm.getHasFormInitialized()!= 'Y'){//Initialize Cart
			try {
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				ResultSet rs;
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				String query = 	"SELECT * FROM vendor_types WHERE VENDOR_TYPE_ID="+actionForm.getVendorTypeId();
				rs = stmt.executeQuery(query);
				if (rs.next()){
					actionForm.setAccountPrefix(rs.getString("account_prefix"));
					actionForm.setGemAccount(rs.getString("gem_account"));
					actionForm.setGoldAccount(rs.getString("gold_account"));
					actionForm.setMoneyAccount(rs.getString("money_account"));
					actionForm.setVendorTypeDescription(rs.getString("vendor_type_description"));
					actionForm.setVendorTypeName(rs.getString("vendor_type_name"));
					actionForm.setHasFormInitialized('Y');
				}else{
					Exception e = new Exception("Invalid Customer");
					throw e;
				}
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
				String query = 	" UPDATE vendor_types " 
											+	" SET "
											+ "   VENDOR_TYPE_NAME='" + actionForm.getVendorTypeName()  
											+	"', VENDOR_TYPE_DESCRIPTION='" + actionForm.getVendorTypeDescription()
											+	"', ACCOUNT_PREFIX='" + actionForm.getAccountPrefix()
											+	"', GEM_ACCOUNT='" + actionForm.getGemAccount()
											+	"', GOLD_ACCOUNT='" + actionForm.getGoldAccount()
											+	"', MONEY_ACCOUNT='" + actionForm.getMoneyAccount()
											+ "' WHERE VENDOR_TYPE_ID="+actionForm.getVendorTypeId(); 
				stmt.executeUpdate(query);
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
