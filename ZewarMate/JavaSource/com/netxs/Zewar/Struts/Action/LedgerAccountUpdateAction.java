package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Lookup.LookupLedgerAccount;
import com.netxs.Zewar.Lookup.LookupInventoryAccount;
import com.netxs.Zewar.Struts.Form.LedgerAccountForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public final class LedgerAccountUpdateAction extends Action {

	public ActionForward execute( ActionMapping mapping
  														, ActionForm form
															, HttpServletRequest request
															, HttpServletResponse response) throws Exception {

		LedgerAccountForm actionForm = (LedgerAccountForm)form ;
		if (actionForm.getHasFormInitialized()!='Y'){
			try {
				Connection connection = (Connection) new DBConnection().getMyPooledConnection();
				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
				String query = "SELECT * FROM ledger_accounts WHERE ledger_account_id="+actionForm.getLedgerAccountId();
				ResultSet rs = stmt.executeQuery(query);
				if (rs.next()){
					actionForm.setLedgerAccountTypeId(rs.getString("ledger_account_type_id"));
					actionForm.setParentLedgerAccountId(rs.getString("parent_ledger_account_id"));					
					actionForm.setAccountCodePrefix(rs.getString("account_code_prefix"));
					actionForm.setAccountCodePostfix(rs.getString("account_code_postfix"));
					actionForm.setTitle(rs.getString("title"));
					actionForm.setDescription(rs.getString("description"));
					actionForm.setAccountDescriptionLevel(rs.getString("account_description_level"));
					actionForm.setAccountActive(rs.getString("account_active"));
					actionForm.setOpeningBalance(rs.getString("opening_balance"));
					actionForm.setEntryDebitCredit(rs.getString("entry_debit_credit"));
					actionForm.setIsInventoryAccount(rs.getString("is_inventory_account"));
					actionForm.setHasFormInitialized('Y');
				} else {
					Exception e = new Exception("Invalid ledger account.");
					throw e;
				}
				stmt.close();
				connection.close();
			} catch(Exception e){
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
				return (mapping.findForward("FAIL"));
			}
			return (mapping.findForward("FAIL"));
		} else {
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
				String query = "";
			
				if (actionForm.getAccountDescriptionLevel().equals("1")){
					actionForm.setAccountCodePostfix("0");
					actionForm.setParentLedgerAccountId("0");
				
					// Check for duplication of account code prefix and postfix 
					query = "SELECT title FROM ledger_accounts WHERE account_code_prefix='"+actionForm.getAccountCodePrefix()+"' AND account_description_level=1 AND LEDGER_ACCOUNT_ID!="+actionForm.getLedgerAccountId();
					rs = stmt.executeQuery(query);
					if (rs.next()){
						ActionMessages serviceErrors = new ActionMessages();
						serviceErrors.add("error",new ActionMessage("errors","Account Prefix already exist with control account title "+rs.getString("title")+ ". Choice another Prefix."));
						saveErrors(request,serviceErrors);
						stmt.close();
						connection.close();
						return (mapping.findForward("FAIL"));
					}
				}
				
				// get Account Code Prefix for Detail account
				if (actionForm.getAccountDescriptionLevel().equals("2")){
					query = "SELECT account_code_prefix FROM ledger_accounts WHERE ledger_account_id="+actionForm.getParentLedgerAccountId();
					rs = stmt.executeQuery(query);
					if (rs.next()){
						actionForm.setAccountCodePrefix(rs.getString("account_code_prefix"));
					}
				}
				// Update Account 
				if (actionForm.getAccountDescriptionLevel().equals("1")){
					query = "UPDATE ledger_accounts SET" 
							+ "  ledger_account_type_id=" + actionForm.getLedgerAccountTypeId()
	//						+ ", parent_ledger_account_id=" + actionForm.getParentLedgerAccountId()
							+ ", title='" + actionForm.getTitle() + "'" 
							+ ", description='" + actionForm.getDescription()+ "'"  
							+ ", account_code_prefix='" +actionForm.getAccountCodePrefix().toUpperCase()+ "'"  
	//						+ ", account_code_postfix=" +actionForm.getAccountCodePostfix()  
							+ ", account_active='" +actionForm.getAccountActive()+"'" 
							+ ", opening_balance=" +actionForm.getOpeningBalance()  
							+ ", entry_debit_credit='" +actionForm.getEntryDebitCredit()+"'"  
	//						+ ", account_description_level=" + actionForm.getAccountDescriptionLevel()
							+ ", is_inventory_account=" +actionForm.getIsInventoryAccount()
							+" WHERE ledger_account_id="+actionForm.getLedgerAccountId(); 
					stmt.executeUpdate(query);
					
					stmt.executeUpdate( " UPDATE ledger_accounts SET" 
														 +"  ledger_account_type_id=" + actionForm.getLedgerAccountTypeId()
														 + ",account_code_prefix='" +actionForm.getAccountCodePrefix().toUpperCase()+ "'"
														 + ",is_inventory_account=" +actionForm.getIsInventoryAccount()
														 +" WHERE parent_ledger_account_id="+actionForm.getLedgerAccountId());
				} else {
					query = "UPDATE ledger_accounts SET" 
//						+ "  ledger_account_type_id=" + actionForm.getLedgerAccountTypeId()
//						+ ", parent_ledger_account_id=" + actionForm.getParentLedgerAccountId()
						+ " title='" + actionForm.getTitle() + "'" 
						+ ", description='" + actionForm.getDescription()+ "'"  
//						+ ", account_code_prefix='" +actionForm.getAccountCodePrefix().toUpperCase()+ "'"  
//						+ ", account_code_postfix=" +actionForm.getAccountCodePostfix()  
						+ ", account_active='" +actionForm.getAccountActive()+"'" 
						+ ", opening_balance=" +actionForm.getOpeningBalance()  
						+ ", entry_debit_credit='" +actionForm.getEntryDebitCredit()+"'"
//						+ ", account_description_level=" + actionForm.getAccountDescriptionLevel()
						+ ", is_inventory_account=" +actionForm.getIsInventoryAccount()
						+" WHERE ledger_account_id="+actionForm.getLedgerAccountId();
					stmt.executeUpdate(query);
				}
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
		}
		return (mapping.findForward("SUCCESS"));
  }

}
