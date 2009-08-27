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
import com.netxs.Zewar.Struts.Form.UserForm;

public class UserUpdateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		UserForm actionForm = (UserForm) form;

		try {
			Integer.parseInt(actionForm.getUserId());
		}catch(Exception e){
			actionForm.setUserId("0");
		}
		if(actionForm.getHasFormInitialized()!= 'Y'){//Initialize Cart
			try {
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				ResultSet rs;
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				String query = 	"SELECT * FROM users WHERE USER_ID="+actionForm.getUserId();
				rs = stmt.executeQuery(query);
				if (rs.next()){
					actionForm.setNameTitle(rs.getString("name_title"));
					actionForm.setFirstName(rs.getString("first_name"));
					actionForm.setMiddleName(rs.getString("middle_name"));
					actionForm.setLastName(rs.getString("last_name"));
					actionForm.setRoleId(rs.getString("ROLE_ID"));
					actionForm.setLoginName(rs.getString("LOGIN_NAME"));
					actionForm.setLoginPassword(rs.getString("LOGIN_PASSWORD"));
					actionForm.setConfirmPassword(rs.getString("LOGIN_PASSWORD"));
					actionForm.setDescription(rs.getString("DESCRIPTION"));
					actionForm.setHasFormInitialized('Y');
				}else{
					Exception e = new Exception("Invalid User");
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
				String query = 	" UPDATE USERS " 
											+	" SET "
											+ "   NAME_TITLE='" + actionForm.getNameTitle()  
											+	"', FIRST_NAME='" + actionForm.getFirstName()
											+	"', LAST_NAME='" + actionForm.getLastName()
											+	"', MIDDLE_NAME='" + actionForm.getMiddleName()
											+	"', ROLE_ID='" + actionForm.getRoleId()
											+	"', LOGIN_NAME='" + actionForm.getLoginName()
											+	"', LOGIN_PASSWORD='" + actionForm.getLoginPassword()
											+	"', DESCRIPTION='" +  actionForm.getDescription()+"'"
											+ " WHERE USER_ID="+actionForm.getUserId(); 
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
