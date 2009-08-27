<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ page import="java.util.*,javax.naming.*,java.sql.*,javax.sql.*;"%>
<%		
			Connection connection;
			Statement stLedgerAccounts;
			ResultSet rsLedgerAccounts;

			// Get JNDI Data Source from Context
			Context initContext = new InitialContext();
			Context ctx = (Context)initContext.lookup("java:/comp/env");

			// Pool Connection 
			connection = ((DataSource)ctx.lookup("jdbc/ZewarShaikhaJeweller")).getConnection();

			//Create Statements 
			stLedgerAccounts = connection.createStatement();

			//Get user selected cash book accounts and there brought forward balance.
			rsLedgerAccounts= stLedgerAccounts.executeQuery (
				" SELECT 	la.ledger_account_id"
					+", la.ledger_account_type_id"
					+", la.parent_ledger_account_id"
					+", la.title"
					+", la.description"
					+", la.account_code_prefix"
					+", LPAD(la.account_code_postfix,6,'0') AS account_code_postfix"
					+", la.account_active"
					+", la.opening_balance"
					+", la.entry_debit_credit"

					+", ELT(la.account_description_level, 'Control', 'Detail ') AS account_description_level"
					+", la.account_create_date"
					+", lt.type_description AS ledger_account_type_description"
	  		+" FROM					ledger_accounts la "
	  		+" INNER JOIN 	ledger_account_types lt ON la.ledger_account_type_id=lt.ledger_account_type_id"
	  		+ ( ((request.getParameter("searchString")==null) || (request.getParameter("searchString").trim().length() == 0)) ? (" ") : (" WHERE la.title like '%"+request.getParameter("searchString")+"%'") )
				+" ORDER BY la.ledger_account_type_id, la.account_code_prefix, la.title"
			);
//			((request.getParameter("searchString")==null) || (request.("searchString").trim().length == 0))
%>


	<div><span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE">Chart of Accounts </span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
	<div class="BOX" style="width: 900;">
	  <!-- Search Form -->
		<form action="../Ledger/ListAccount.do" method="post">
			<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="580">
				<tr>
					<td class="FORM_CAPTION" width="150">Search String</td>
					<td class="FORM_CONTROL">
						<input type="text" name="searchString"  size="40" value='<%=((request.getParameter("searchString")==null) ? ("") : (request.getParameter("searchString")))%>' /> 
						<input type="submit"  name="Search" value="Search" />
					</td>
				</tr>
			</table>
		</form>
		<!-- Ledger Account List -->
		<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="900">
			<tr>
				<td width="100" class="LIST_HEADER">Code</td> 
				<td width="450" class="LIST_HEADER">Title</td>
				<td width="110" class="LIST_HEADER">Type</td>
				<td width="110" class="LIST_HEADER">Level</td>				
				<td width="130" class="LIST_HEADER">Action</td>
			</tr>
		</table>		
 		<div style="overflow:auto; width:900px; height:370px; padding:0px; margin:0px;border-width:1px; border-color:#736356; border-style:solid; border-collapse:collapse;">
		<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="880">
			<COL width="100"><COL width="450"><COL width="110"><COL width="110"><COL width="110">
 		 	<%	while(rsLedgerAccounts.next()) { %>
				<tr class="LIST_DATA" onmouseover="this.className='HIGHLIGHT_DATA';" onmouseout="this.className='LIST_DATA'">
					<td><%=rsLedgerAccounts.getString("account_code_prefix")%> <%=rsLedgerAccounts.getString("account_code_postfix")%></td> 
					<td><%=rsLedgerAccounts.getString("title")%></td>
					<td><%=rsLedgerAccounts.getString("ledger_Account_type_description")%></td>
					<td><%=rsLedgerAccounts.getString("account_description_level")%></td>
					<td><a href="<html:rewrite page='/Ledger/UpdateAccountInit.do?'/>ledgerAccountId=<%=rsLedgerAccounts.getString("ledger_account_id")%>">Update</a></td>
				</tr>
			<% } %>
		</table>
		
	</div>
	</div>