
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

public class ItemUpdateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		ItemForm actionForm = (ItemForm) form;
		//check for cart is valid if not then creat new one
		try {
			Integer.parseInt(actionForm.getItemId());
		}catch(Exception e){
			actionForm.setItemId("0");
		}
		if(actionForm.getHasFormInitialized()!= 'Y'){//Initialize Cart
			try {
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				ResultSet rs;
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				String query = 	"SELECT * FROM items WHERE ITEM_ID="+actionForm.getItemId();
				rs = stmt.executeQuery(query);
				if (rs.next()){
					actionForm.setItemId(rs.getString("item_id"));
					actionForm.setItemGroupId(rs.getString("item_group_id"));
					actionForm.setItemName(rs.getString("item_name"));
					actionForm.setItemDescription(rs.getString("item_description"));
					actionForm.setZakaatApply(rs.getString("zakaat_apply"));
					actionForm.setHasFormInitialized('Y');
				}else{
					Exception e = new Exception("Invalid Item");
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
				String query = 	" UPDATE items" 
											+	" SET "
											+ "   ITEM_GROUP_ID=" + actionForm.getItemGroupId()  
											+	" , ITEM_NAME='" + actionForm.getItemName()
											+	"', ITEM_DESCRIPTION='" + actionForm.getItemDescription()
											+	"', ZAKAAT_APPLY='" + actionForm.getZakaatApply()
											+ "' WHERE ITEM_ID="+actionForm.getItemId(); 
				stmt.executeUpdate(query);
				stmt.close();
				connection.close();
				LookupItem.refreshCache();
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
