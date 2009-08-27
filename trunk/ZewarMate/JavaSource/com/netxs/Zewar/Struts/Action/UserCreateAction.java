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

public class UserCreateAction extends Action {
	
	public ActionForward execute( ActionMapping mapping
			, ActionForm form
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		UserForm actionForm = (UserForm) form;
		// Create Cart
		if (actionForm.getHasFormInitialized()=='Y'){
			try { 
				Connection connection;
				connection = (Connection) new DBConnection().getMyPooledConnection();
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				ResultSet rs;
				//check for login name duplication
				rs = stmt.executeQuery("SELECT 1 FROM USERS WHERE LOGIN_NAME='"+actionForm.getLoginName()+"'");
				if (rs.next()){
					ActionMessages serviceErrors = new ActionMessages();
					serviceErrors.add("error",new ActionMessage("errors","Login Name already exist. Choice another."));
					saveErrors(request,serviceErrors);
					return (mapping.findForward("FAIL"));
				}
				
				stmt.executeUpdate(
						"INSERT INTO users  (NAME_TITLE, FIRST_NAME, LAST_NAME, MIDDLE_NAME, ROLE_ID, LOGIN_NAME, LOGIN_PASSWORD,  DESCRIPTION ) " 
					+	" VALUES( " 
					+	"  '" +	actionForm.getNameTitle() 
					+	"','" + actionForm.getFirstName()
					+	"','" + actionForm.getLastName()
					+	"','" + actionForm.getMiddleName()
					+	"', " + actionForm.getRoleId()
					+	" ,'" + actionForm.getLoginName()
					+	"','" + actionForm.getLoginPassword()
					+	"','" + actionForm.getDescription()
					+	"')"
				);
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
		//check user request for add another
		if(!request.getParameter("submitAction").trim().equalsIgnoreCase("save")){
			actionForm.setHasFormInitialized('N');
			return (mapping.findForward("SUCCESS_ADD_ANOTHER"));	
		}
		return (mapping.findForward("SUCCESS"));
	}

}
