<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="java.util.*,javax.naming.*,java.sql.*,javax.sql.*,java.text.DateFormat,java.util.Date,java.util.Locale;"%>

<% 	Connection connection;
		Statement stCashBookAccounts;
		ResultSet rsCashBookAccounts;
		
		// Get JNDI Data Source from Context
		Context initContext = new InitialContext();
		Context ctx = (Context)initContext.lookup("java:/comp/env");

		// Pool Connection 
		connection = ((DataSource)ctx.lookup("jdbc/ZewarShaikhaJeweller")).getConnection();

		//Create Statements 
		stCashBookAccounts = connection.createStatement();

		rsCashBookAccounts = stCashBookAccounts.executeQuery (
		  " SELECT la.ledger_account_id, la.title, la.account_code_prefix, LPAD(la.account_code_postfix,6,'0') AS account_code_postfix "
		 +" FROM ledger_accounts la "
		 +" WHERE la.account_description_level=2 "
		 +" ORDER BY la.account_code_prefix, la.account_code_postfix"
		);		
//	  String today = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date());	
%>

<form action="../Ledger/ISViewResult.do" method="post">
<div><span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE">Income Statement View </span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
<div class="BOX" style="width:620;">
	<br>
	<table class="FORM_AREA">
		<tr>
			<td class="FORM_CAPTION">Type of View</td>
			<td class="FORM_CONTROL">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="">
					<option value="1">Codewise</option>
					<option value="2">Alphabatically</option>
					<option value="3">Receivable Balance</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="FORM_CAPTION">Ledger Account</td>
			<td class="FORM_CONTROL">
				<div>
				  From&nbsp;&nbsp;
					<select name="accountCodeFrom" >
						<%	while (rsCashBookAccounts.next()) 
							 		out.print("<option value=\""+rsCashBookAccounts.getString("account_code_prefix")+"|"+rsCashBookAccounts.getString("account_code_postfix")+"\">"+rsCashBookAccounts.getString("account_code_prefix")+" "+rsCashBookAccounts.getString("account_code_postfix")+" "+rsCashBookAccounts.getString("title")+"</option>\n");
						%>
					</select>
				</div>
				<div>
					To &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select name="accountCodeTo" >
						<% rsCashBookAccounts.beforeFirst();
							 while (rsCashBookAccounts.next())
				 			 		out.print("<option value=\""+rsCashBookAccounts.getString("account_code_prefix")+"|"+rsCashBookAccounts.getString("account_code_postfix")+"\" selected >"+rsCashBookAccounts.getString("account_code_prefix")+" "+rsCashBookAccounts.getString("account_code_postfix")+" "+rsCashBookAccounts.getString("title")+"</option>\n");
				 		%>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td class="FORM_CAPTION">Date </td>
			<td class="FORM_CONTROL">
				<div>
				  Month
				  <select name="balanceMonth">
				  <option value="1">Jan</option>
				  <option value="2">Feb</option>
				  <option value="3">Mar</option>
				  <option value="4">Apr</option>
				  <option value="5">May</option>
				  <option value="6">Jun</option>
				  <option value="7">Jul</option>
				  <option value="8">Aug</option>
				  <option value="9">Sep</option>
				  <option value="10">Oct</option>
				  <option value="11">Nov</option>
				  <option value="12">Dec</option>
				  </select>
				</div>
				<div>
					Year &nbsp;&nbsp;
					<input type="text"   name="balanceYear"  size="12" maxlength="4" />
				</div>
			</td>			
		</tr>
	</table>
	<div colspan="2" align="right" class="FORM_DATA"><input type="submit" value="View"></div>
</div>
</form>
