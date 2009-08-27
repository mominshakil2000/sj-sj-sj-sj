package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.text.*;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.*;
import com.netxs.Zewar.Struts.Form.CashVoucherForm;
import com.netxs.Zewar.DataSources.DBConnection;

public final class CashVoucherUpdateAction extends Action {

	public ActionForward execute( ActionMapping mapping
  								, ActionForm form
								, HttpServletRequest request
								, HttpServletResponse response) throws Exception {

		CashVoucherForm actionForm = (CashVoucherForm) form;
		Connection connection = null;
		
		if (actionForm.getHasFormInitialized()!='Y'){
			actionForm.setEntryDate(DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date()));
			actionForm.setLedgerAccountId(String.valueOf(Default.LEDGER_CASH_ACCOUNT_ID));
			actionForm.setHasFormInitialized('Y');
			return (mapping.findForward("FAIL"));
		}
		try {
			connection = (Connection) new DBConnection().getMyPooledConnection();
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			ResultSet rs;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
				
			//start of transication 
			connection.setAutoCommit(false);
			
			stmt.execute(
				 " UPDATE cash_vouchers " 
				+" SET" 
					+" entry_date='"+actionForm.getEntryDate()+"'"
					+",ledger_account_id="+actionForm.getLedgerAccountId()
				+" WHERE cash_voucher_id="+actionForm.getVoucherId()
			);
			
			// delete cash voucher entries, ledger entries and relations
			stmt.execute("DELETE FROM cash_voucher_entries WHERE cash_voucher_entry_id IN ("+actionForm.getRemoveVoucherEntryIds()+")");
			stmt.execute("DELETE FROM ledger_entries WHERE ledger_entry_id IN ("+actionForm.getRemoveLedgerEntryIds()+")");

			// Add/Update Ledger Entries & Cash Voucher Enteries
			PreparedStatement stmtUpdateLedgerEntry = connection.prepareStatement("UPDATE ledger_entries SET ledger_account_id_debit=?, ledger_account_id_credit=?, entry_date=?, amount=?, narration=? WHERE ledger_entry_id=?");
			PreparedStatement stmtUpdateVoucherEntry = connection.prepareStatement("UPDATE cash_voucher_entries SET ledger_account_id=?, narration=?, amount=?  WHERE cash_voucher_entry_id=?");

			PreparedStatement stmtInsertLedgerEntry = connection.prepareStatement("INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, entry_date, amount, voucher_prefix, voucher_postfix, narration)	VALUES(?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement stmtInsertVoucherEntry = connection.prepareStatement("INSERT INTO cash_voucher_entries (cash_voucher_id, ledger_account_id, narration, amount, ledger_entry_id)	VALUES(?, ?, ?, ?, ?)");
			
			for (int index=0; index < actionForm.getListVoucherEntry().size(); index++){
				if(actionForm.getVoucherEntry(index).isInsertable()) { 
					//Add Ledger Entry
					if (actionForm.getVoucherPrefix().equalsIgnoreCase("CP")){
						stmtInsertLedgerEntry.setInt(1,Integer.parseInt(actionForm.getVoucherEntry(index).getLedgerAccountId()));
						stmtInsertLedgerEntry.setInt(2, Integer.parseInt(actionForm.getLedgerAccountId()));
						stmtInsertLedgerEntry.setString(3,actionForm.getEntryDate());
						stmtInsertLedgerEntry.setFloat(4,Float.parseFloat(actionForm.getVoucherEntry(index).getAmount()));
						stmtInsertLedgerEntry.setString(5, actionForm.getVoucherPrefix());
						stmtInsertLedgerEntry.setInt(6, Integer.parseInt(actionForm.getVoucherPostfix()));
						stmtInsertLedgerEntry.setString(7,actionForm.getVoucherEntry(index).getNarration());
					} else {
						stmtInsertLedgerEntry.setInt(1, Integer.parseInt(actionForm.getLedgerAccountId()));
						stmtInsertLedgerEntry.setInt(2,Integer.parseInt(actionForm.getVoucherEntry(index).getLedgerAccountId()));
						stmtInsertLedgerEntry.setString(3,actionForm.getEntryDate());
						stmtInsertLedgerEntry.setFloat(4,Float.parseFloat(actionForm.getVoucherEntry(index).getAmount()));
						stmtInsertLedgerEntry.setString(5, actionForm.getVoucherPrefix());
						stmtInsertLedgerEntry.setInt(6, Integer.parseInt(actionForm.getVoucherPostfix()));
						stmtInsertLedgerEntry.setString(7,actionForm.getVoucherEntry(index).getNarration());
					}
					stmtInsertLedgerEntry.execute();
					rs = stmtInsertLedgerEntry.getGeneratedKeys();
					actionForm.getVoucherEntry(index).setLedgerEntryId(rs.next()?rs.getString(1):"0");
					
					//Add Cash Voucher Entry 
					stmtInsertVoucherEntry.setInt(1,Integer.parseInt(actionForm.getVoucherId()));
					stmtInsertVoucherEntry.setInt(2,Integer.parseInt(actionForm.getVoucherEntry(index).getLedgerAccountId()));
					stmtInsertVoucherEntry.setString(3,actionForm.getVoucherEntry(index).getNarration());
					stmtInsertVoucherEntry.setFloat(4,Float.parseFloat(actionForm.getVoucherEntry(index).getAmount()));
					stmtInsertVoucherEntry.setInt(5,Integer.parseInt(actionForm.getVoucherEntry(index).getLedgerEntryId()));
					stmtInsertVoucherEntry.execute();
					rs = stmtInsertVoucherEntry.getGeneratedKeys();
					actionForm.getVoucherEntry(index).setVoucherEntryId(rs.next()?rs.getString(1):"0");

					stmtInsertLedgerEntry.clearParameters();
					stmtInsertVoucherEntry.clearParameters();
					
				} else {
					//Update Ledger Entry
					if (actionForm.getVoucherPrefix().equalsIgnoreCase("CP")){
						stmtUpdateLedgerEntry.setInt(1,Integer.parseInt(actionForm.getVoucherEntry(index).getLedgerAccountId()));
						stmtUpdateLedgerEntry.setInt(2, Integer.parseInt(actionForm.getLedgerAccountId()));
						stmtUpdateLedgerEntry.setString(3,actionForm.getEntryDate());
						stmtUpdateLedgerEntry.setFloat(4,Float.parseFloat(actionForm.getVoucherEntry(index).getAmount()));
						stmtUpdateLedgerEntry.setString(5,actionForm.getVoucherEntry(index).getNarration());
						stmtUpdateLedgerEntry.setInt(6, Integer.parseInt(actionForm.getVoucherEntry(index).getLedgerEntryId()));
					} else {
						stmtUpdateLedgerEntry.setInt(1, Integer.parseInt(actionForm.getLedgerAccountId()));
						stmtUpdateLedgerEntry.setInt(2,Integer.parseInt(actionForm.getVoucherEntry(index).getLedgerAccountId()));
						stmtUpdateLedgerEntry.setString(3,actionForm.getEntryDate());
						stmtUpdateLedgerEntry.setFloat(4,Float.parseFloat(actionForm.getVoucherEntry(index).getAmount()));
						stmtUpdateLedgerEntry.setString(5,actionForm.getVoucherEntry(index).getNarration());
						stmtUpdateLedgerEntry.setInt(6, Integer.parseInt(actionForm.getVoucherEntry(index).getLedgerEntryId()));
					}

					stmtUpdateLedgerEntry.execute();
					
					//Update Cash Voucher Entry 
					stmtUpdateVoucherEntry.setInt(1,Integer.parseInt(actionForm.getVoucherEntry(index).getLedgerAccountId()));
					stmtUpdateVoucherEntry.setString(2,actionForm.getVoucherEntry(index).getNarration());
					stmtUpdateVoucherEntry.setFloat(3,Float.parseFloat(actionForm.getVoucherEntry(index).getAmount()));
					stmtUpdateVoucherEntry.setInt(4, Integer.parseInt(actionForm.getVoucherEntry(index).getVoucherEntryId()));
					stmtUpdateVoucherEntry.execute();

					stmtUpdateLedgerEntry.clearParameters();
					stmtUpdateVoucherEntry.clearParameters();
				}
			}

			//Cleanup database resources
			connection.commit();
			stmtInsertLedgerEntry.close();
			stmtInsertVoucherEntry.close();
			stmtUpdateLedgerEntry.close();
			stmtUpdateVoucherEntry.close();
			connection.close();
		} catch(Exception e) {
			if (connection != null) {
				try {connection.rollback();}catch (SQLException sqle){}
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,serviceErrors);
			}
			return (mapping.findForward("FAIL"));	 
		}
		
		//clear form data
		actionForm.setEntryDate("");
		actionForm.setHasFormInitialized('N');
		actionForm.setLedgerAccountId("");
		actionForm.setVoucherId("");
		actionForm.setVoucherPostfix("");
		actionForm.setVoucherPrefix("");
		actionForm.getListVoucherEntry().clear();
		
		//check user request for add another
		if(!request.getParameter("submitAction").trim().equalsIgnoreCase("save")){
			return (mapping.findForward("SUCCESS_ADD_ANOTHER"));	
		}
		return (mapping.findForward("SUCCESS"));
		 
  }

}
 
