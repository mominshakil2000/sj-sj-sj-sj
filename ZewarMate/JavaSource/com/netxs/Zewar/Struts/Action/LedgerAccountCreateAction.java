package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Lookup.LookupLedgerAccount;
import com.netxs.Zewar.Lookup.LookupInventoryAccount;
import com.netxs.Zewar.Struts.Form.LedgerAccountForm;

public final class LedgerAccountCreateAction extends Action {

	public ActionForward execute( ActionMapping mapping
  														, ActionForm form
															, HttpServletRequest request
															, HttpServletResponse response) throws Exception {

		LedgerAccountForm actionForm = (LedgerAccountForm)form ;
		if (actionForm.getHasFormInitialized()!='Y'){
			actionForm.setHasFormInitialized('Y');
			return mapping.findForward("FAIL");
		}
		try {
			Integer.parseInt(actionForm.getParentLedgerAccountId());
		} catch (Exception e) {
			actionForm.setParentLedgerAccountId("0");
		}
		try{
			Connection connection; 
			ResultSet rs;
			connection = (Connection) new DBConnection().getMyPooledConnection();
			Statement stmt = connection.createStatement();
		
			if (actionForm.getAccountDescriptionLevel().equals("1")){
				actionForm.setAccountCodePostfix("0");
				actionForm.setParentLedgerAccountId("0");
				// Check for duplication of Control account code prefix  
				rs = stmt.executeQuery("SELECT ledger_account_id, title FROM ledger_accounts WHERE account_code_prefix='"+actionForm.getAccountCodePrefix()+"'");
				if (rs.next()){
					ActionMessages serviceErrors = new ActionMessages();
					serviceErrors.add("error",new ActionMessage("errors","Control Account already exist with title "+rs.getString("title")));
					saveErrors(request,serviceErrors);
					stmt.close();
					connection.close();
					return (mapping.findForward("FAIL"));
				}
			} 
			else if (actionForm.getAccountDescriptionLevel().equals("2")) { // if ledger account type is detail get parent prefix
				rs = stmt.executeQuery("SELECT account_code_prefix, ledger_account_type_id FROM ledger_accounts WHERE ledger_account_id="+actionForm.getParentLedgerAccountId());
				if (rs.next()){
					actionForm.setAccountCodePrefix(rs.getString("account_code_prefix"));
					actionForm.setLedgerAccountTypeId(rs.getString("ledger_account_type_id"));
				}
				//get account code postfix if user has not entered
				try {
					Integer.parseInt(actionForm.getAccountCodePostfix());
				}catch (Exception e){
					actionForm.setAccountCodePostfix("0");
				}
				if(actionForm.getAccountCodePostfix().equals("0")) { 
					rs = stmt.executeQuery("SELECT MAX(account_code_postfix)+1 FROM ledger_accounts WHERE account_code_prefix='"+actionForm.getAccountCodePrefix()+"'");
					if (rs.next())
						actionForm.setAccountCodePostfix(rs.getString(1));
				}
			}
			// Check for duplication of account code prefix and postfix 
			rs = stmt.executeQuery("SELECT ledger_account_id, title FROM ledger_accounts WHERE account_code_prefix='"+actionForm.getAccountCodePrefix()+"' AND account_code_postfix="+actionForm.getAccountCodePostfix());
			if (rs.next()){
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors","Account already exist with title "+rs.getString("title")));
				saveErrors(request,serviceErrors);
				stmt.close();
				connection.close();
				return (mapping.findForward("FAIL"));
			}

			// add ledger account
			stmt.executeUpdate(
				" INSERT INTO ledger_accounts (ledger_account_type_id, parent_ledger_account_id, title, description, account_code_prefix, account_code_postfix, account_active, opening_balance, entry_debit_credit, account_description_level, account_create_date, is_inventory_account) " 
			 +" VALUES(  " 
		 			 		 + actionForm.getLedgerAccountTypeId()
					+", "+ actionForm.getParentLedgerAccountId() 
					+",'"+ actionForm.getTitle() + "'"
					+",'"+ actionForm.getDescription()+ "'"
					+",'"+ actionForm.getAccountCodePrefix().toUpperCase()+ "'"
					+", "+ actionForm.getAccountCodePostfix()
					+",'"+(actionForm.getAccountActive())+"'"
					+", "+ actionForm.getOpeningBalance()
					+",'"+ actionForm.getEntryDebitCredit()+"'"
					+", "+ actionForm.getAccountDescriptionLevel()
					+", "+"curDate()"
					+", "+ actionForm.getIsInventoryAccount()
					+")"
			);
			stmt.close();
			connection.close();
			LookupLedgerAccount.refreshCache();
			LookupInventoryAccount.refreshCache();
		}catch(Exception e){
			ActionMessages serviceErrors = new ActionMessages();
			serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,serviceErrors);
			return (mapping.findForward("FAIL"));
		}

		return (mapping.findForward("SUCCESS"));
  }
}
