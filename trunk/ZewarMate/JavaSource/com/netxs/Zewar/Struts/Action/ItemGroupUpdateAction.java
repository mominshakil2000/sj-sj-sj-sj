
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
import com.netxs.Zewar.Struts.Form.ItemGroupForm;

public class ItemGroupUpdateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		ItemGroupForm actionForm = (ItemGroupForm) form;
		//check for cart is valid if not then creat new one
		try {
			Integer.parseInt(actionForm.getItemGroupId());
		}catch(Exception e){
			actionForm.setItemGroupId("0");
		}
		if(actionForm.getHasFormInitialized()!= 'Y'){//Initialize Cart
			try {
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				ResultSet rs;
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				String query = 	"SELECT * FROM item_groups WHERE ITEM_GROUP_ID="+actionForm.getItemGroupId();
				rs = stmt.executeQuery(query);
				if (rs.next()){
					actionForm.setItemGroupName(rs.getString("item_group_name"));
					actionForm.setItemGroupDescription(rs.getString("item_group_description"));
					actionForm.setHasFormInitialized('Y');
				}else{
					Exception e = new Exception("Invalid Item Group.");
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
				String query = 	" UPDATE item_groups " 
											+	" SET "
											+ "   ITEM_GROUP_NAME='" + actionForm.getItemGroupName()  
											+	"', ITEM_GROUP_DESCRIPTION='" + actionForm.getItemGroupDescription()
											+ "' WHERE ITEM_GROUP_ID="+actionForm.getItemGroupId(); 
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
