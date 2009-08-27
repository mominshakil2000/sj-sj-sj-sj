
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
import com.netxs.Zewar.Lookup.LookupItem;
import com.netxs.Zewar.Struts.Form.ItemForm;

public class ItemCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		ItemForm actionForm = (ItemForm) form;
		// Create Cart
		if (actionForm.getHasFormInitialized()=='Y'){
			try { 
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				String query = 	"INSERT INTO items  (item_group_id, item_name, item_description, zakaat_apply) " 
											+	"VALUES( " 
											+	      	actionForm.getItemGroupId() 
											+	" ,'" + actionForm.getItemName()
											+	"','" + actionForm.getItemDescription()
											+	"','" + actionForm.getZakaatApply()
											+	"')";
				stmt.executeUpdate(query);
				ResultSet rs;
				rs = stmt.getGeneratedKeys();
				if (rs.next()){
					actionForm.setItemId(rs.getString(1));
					actionForm.setHasFormInitialized('Y');
				}
				LookupItem.refreshCache();
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
			actionForm.setZakaatApply("Y");
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
