<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="java.util.*,javax.naming.*,java.sql.*,javax.sql.*;"%>

<%		
	Connection connection;
	Statement stVoucher, stVoucherEntries;
	ResultSet rsVoucher, rsVoucherEntries = null;

	// Get JNDI Data Source from Context
	Context initContext = new InitialContext();
	Context ctx = (Context)initContext.lookup("java:/comp/env");

	// Pool Connection 
	connection = ((DataSource)ctx.lookup("jdbc/ZewarShaikhaJeweller")).getConnection();

	//Create Statements 
	stVoucher = connection.createStatement();
	stVoucherEntries = connection.createStatement();

	//Parse Request Parameters
	String voucherPrefix = (String)request.getParameter("voucherPrefix");
	int voucherPostfix = Integer.parseInt((String)request.getParameter("voucherPostfix")); 
	int voucherId = 0;			
	byte responseFlag = 0;
	String responseFlagMessage = "";
	
	// get Voucher
	rsVoucher = stVoucher.executeQuery (
			 " SELECT"
			+" general_voucher_id"
			+",ledger_entry_id"
			+",ledger_account_id_credit"
			+",ledger_account_id_debit"
			+",voucher_prefix"
			+",voucher_postfix"
			+",entry_date"
			+",narration"
			+",amount"
			+" FROM"
			+" general_vouchers"
					+" WHERE voucher_prefix = '"+voucherPrefix+"'"
					+" AND   voucher_postfix = '"+voucherPostfix+"'"
				);
	if (rsVoucher.next()) {
		responseFlag=1;
	} else {
		responseFlag = 0;
		responseFlagMessage = "Voucher not found.";
	};
%>

<?xml version="1.0" ?>
<RootNote>
	<ControlFlag>
		<ResponseFlag><%=responseFlag%></ResponseFlag>
		<ResponseFlagMessage><%=responseFlagMessage%></ResponseFlagMessage>
	</ControlFlag>
	<% if(responseFlag==1){ %>
		<Voucher>
			<VoucherId><%=rsVoucher.getInt("general_voucher_id")%></VoucherId>
			<VoucherPrefix><%=rsVoucher.getString("voucher_prefix")%></VoucherPrefix>
			<VoucherPostfix><%=rsVoucher.getInt("voucher_postfix")%></VoucherPostfix>
			<EntryDate><%=rsVoucher.getString("entry_date")%></EntryDate>
			<LedgerEntryId><%=rsVoucher.getString("ledger_entry_id")%></LedgerEntryId>
			<LedgerAccountIdDebit><%=rsVoucher.getString("ledger_account_id_debit")%></LedgerAccountIdDebit>
			<LedgerAccountIdCredit><%=rsVoucher.getString("ledger_account_id_credit")%></LedgerAccountIdCredit>
			<Narration><%=rsVoucher.getString("narration")%></Narration>
			<Amount><%=rsVoucher.getString("amount")%></Amount>
		</Voucher> 
	<% } %>
</RootNote>