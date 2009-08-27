package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Struts.Form.GeneralVoucherForm;
 
public final class GeneralVoucherCreateAction extends Action {

	public ActionForward execute( ActionMapping mapping
  														, ActionForm form
															, HttpServletRequest request
															, HttpServletResponse response) throws Exception {

		GeneralVoucherForm actionForm = (GeneralVoucherForm) form;
		Connection connection = null;
	
		if (actionForm.getHasFormInitialized()!='Y'){
			actionForm.setEntryDate(DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date()));
			actionForm.setHasFormInitialized('Y');
			return (mapping.findForward("FAIL"));
		}
		try {
			connection = (Connection) new DBConnection().getMyPooledConnection();
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.TYPE_FORWARD_ONLY);
			ResultSet rs;

			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			connection.setAutoCommit(false);
			
			// Voucher Postfix
			rs = stmt.executeQuery("SELECT IFNULL(MAX(voucher_postfix),0)+1 AS voucher_postfix FROM general_vouchers WHERE voucher_prefix='"+actionForm.getVoucherPrefix()+"'");
			actionForm.setVoucherPostfix(rs.next()?rs.getString(1):"0");
			
			//Add Ledger Entry
			stmt.execute(
				 " INSERT INTO ledger_entries (ledger_account_id_debit, ledger_account_id_credit, voucher_prefix, voucher_postfix, entry_date, amount, narration )"
				+" VALUES ("
				+     actionForm.getLedgerAccountIdDebit()
				+", "+actionForm.getLedgerAccountIdCredit()
				+",'"+actionForm.getVoucherPrefix()+"'"
				+", "+actionForm.getVoucherPostfix()
				+",'"+actionForm.getEntryDate()+"'"
				+", "+actionForm.getAmount()
				+",'"+actionForm.getNarration()+"'"
				+" )"
			);
			rs = stmt.getGeneratedKeys();
			actionForm.setLedgerEntryId(rs.next()?rs.getString(1):"0");
			
			stmt.execute(
					 " INSERT INTO general_vouchers (ledger_account_id_debit, ledger_account_id_credit, voucher_prefix, voucher_postfix, entry_date, narration, amount, ledger_entry_id)"
					+" VALUES ("
					+     actionForm.getLedgerAccountIdDebit()
					+", "+actionForm.getLedgerAccountIdCredit()
					+",'"+actionForm.getVoucherPrefix()+"'"
					+", "+actionForm.getVoucherPostfix()
					+",'"+actionForm.getEntryDate()+"'"
					+",'"+actionForm.getNarration()+"'"
					+", "+actionForm.getAmount()
					+", "+actionForm.getLedgerEntryId()
					+" )"
				);

			rs = stmt.getGeneratedKeys();
			actionForm.setVoucherId(rs.next()?rs.getString(1):"0");
			connection.commit();
			//Cleanup database resources
			rs.close();
			stmt.close();
			connection.close();

		} catch(Exception e) {
			if (connection != null) {
				try {connection.rollback();}catch (SQLException sqle){}
			}
			ActionMessages serviceErrors = new ActionMessages();
			serviceErrors.add("error",new ActionMessage("errors",e.getMessage())); 
			saveErrors(request,serviceErrors);
			return (mapping.findForward("FAIL"));	 
		}
		
		//clear form data
		actionForm.setAmount("0.00");
		actionForm.setEntryDate("");
		actionForm.setHasFormInitialized('N');
		actionForm.setLedgerAccountIdCredit("0");
		actionForm.setLedgerAccountIdDebit("0");
		actionForm.setLedgerEntryId("0");
		actionForm.setNarration("");
		actionForm.setVoucherId("0");
		actionForm.setVoucherPostfix("");
		actionForm.setVoucherPrefix("");
		
		//check user request for add another
		if(!request.getParameter("submitAction").trim().equalsIgnoreCase("save")){
			return (mapping.findForward("SUCCESS_ADD_ANOTHER"));	
		}
		return (mapping.findForward("SUCCESS"));
		 
  }	
} 