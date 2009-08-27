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

public class VendorGroupCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		VendorGroupForm actionForm = (VendorGroupForm) form;
		// Create Cart
		if (actionForm.getHasFormInitialized()=='Y'){
			try { 
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				stmt.executeUpdate(	"INSERT INTO vendor_types  (vendor_type_name, vendor_type_description, account_prefix, gold_account, money_account, gem_account ) " 
											+	"VALUES( " 
											+	"  '" +	actionForm.getVendorTypeName() 
											+	"','" + actionForm.getVendorTypeDescription()
											+	"','" + actionForm.getAccountPrefix()
											+	"','" + actionForm.getGoldAccount()
											+	"','" + actionForm.getMoneyAccount()
											+	"','" + actionForm.getGemAccount()
											+	"')");
				
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()){
					actionForm.setVendorTypeId(rs.getString(1));
					actionForm.setHasFormInitialized('Y');
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
			actionForm.setHasFormInitialized('Y');
			return (mapping.findForward("FAIL"));
		}
		return (mapping.findForward("SUCCESS"));
	}

}
