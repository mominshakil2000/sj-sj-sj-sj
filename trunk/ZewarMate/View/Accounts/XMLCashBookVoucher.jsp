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
						+" cv.cash_voucher_id"
						+",cv.entry_DATE"
						+",cv.voucher_prefix"
						+",cv.voucher_postfix"
						+",cv.ledger_account_id"
					+" FROM cash_vouchers cv "
					+" WHERE cv.voucher_prefix = '"+voucherPrefix+"'"
					+" AND   cv.voucher_postfix = '"+voucherPostfix+"'"
				);
	if (rsVoucher.next()) {
		responseFlag = 1;
		voucherId = rsVoucher.getInt("cash_voucher_id");
		
		  // get Voucher Entries
		rsVoucherEntries = stVoucherEntries.executeQuery (
							" SELECT"
								+" cve.cash_voucher_entry_id"
								+",cve.ledger_entry_id"
								+",cve.ledger_account_id"
								+",cve.narration"
							 	+",cve.amount"
							 	+",concat(la.account_code_prefix, ' ' , la.account_code_postfix, ' ', la.title) AS ledger_account_title "
							+" FROM cash_voucher_entries cve "
							+" INNER JOIN ledger_accounts la ON cve.ledger_account_id=la.ledger_account_id"
							+" WHERE cve.cash_voucher_id = '"+voucherId+"'" );
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
			<VoucherId><%=rsVoucher.getInt("cash_voucher_id")%></VoucherId>
			<VoucherPrefix><%=rsVoucher.getString("voucher_prefix")%></VoucherPrefix>
			<VoucherPostfix><%=rsVoucher.getInt("voucher_postfix")%></VoucherPostfix>
			<LedgerAccountId><%=rsVoucher.getString("ledger_account_id")%></LedgerAccountId>
			<EntryDate><%=rsVoucher.getString("entry_DATE")%></EntryDate>
			<% while(rsVoucherEntries.next()){ %>			
				<VoucherEntry>
					<Insertable>false</Insertable>
					<VoucherEntryId><%=rsVoucherEntries.getString("cash_voucher_entry_id")%></VoucherEntryId>
					<LedgerEntryId><%=rsVoucherEntries.getString("ledger_entry_id")%></LedgerEntryId>
					<LedgerAccountId><%=rsVoucherEntries.getString("ledger_account_id")%></LedgerAccountId>
					<LedgerAccountTitle><%=rsVoucherEntries.getString("ledger_account_title").replaceAll("&","&amp;").replaceAll(">","&gt;").replaceAll("<","&lt;")%></LedgerAccountTitle>
					<Amount><%=rsVoucherEntries.getString("amount")%></Amount>
					<Narration><%=rsVoucherEntries.getString("narration").replaceAll("&","&amp;").replaceAll(">","&gt;").replaceAll("<","&lt;")%></Narration>
				</VoucherEntry>
			<% } %>			
		</Voucher> 
	<% } %>
</RootNote>