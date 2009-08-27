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
	  String today = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date());	
%>

<form action="../View/Accounts/GeneralJournalViewResult.jsp" method="get" target="_blank">
<div><span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE">General Journal View </span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
<div class="BOX" style="width:620;">
	<br>
	<table class="FORM_AREA">
		<tr>
			<td class="FORM_CAPTION">Ledger Account</td>
			<td class="FORM_CONTROL">
				<div>
				  From
					<select name="accountCodeFrom" >
						<%	while (rsCashBookAccounts.next()) 
							 		out.print("<option value=\""+rsCashBookAccounts.getString("account_code_prefix")+rsCashBookAccounts.getString("account_code_postfix")+"\">"+rsCashBookAccounts.getString("account_code_prefix")+" "+rsCashBookAccounts.getString("account_code_postfix")+" "+rsCashBookAccounts.getString("title")+"</option>\n");
						%>
					</select>
				</div>
				<div>
					To &nbsp;&nbsp;&nbsp;
					<select name="accountCodeTo" >
						<% rsCashBookAccounts.beforeFirst();
							 while (rsCashBookAccounts.next())
				 			 		out.print("<option value=\""+rsCashBookAccounts.getString("account_code_prefix")+rsCashBookAccounts.getString("account_code_postfix")+"\" selected >"+rsCashBookAccounts.getString("account_code_prefix")+" "+rsCashBookAccounts.getString("account_code_postfix")+" "+rsCashBookAccounts.getString("title")+"</option>\n");
				 		%>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td class="FORM_CAPTION">Date </td>
			<td class="FORM_CONTROL">
				<div>
				  From
					<input type="text"   name="dateFrom"  size="12" readonly="true"  value="<%=today%>" />
					<input type="button" name="buttonDateFrom" value=" ... " />
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("dateFrom")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonDateFrom")[0],
								showsTime:false
						});
					</script>
				</div>
				<div>
					To &nbsp;&nbsp;&nbsp;
					<input type="text"   name="dateTo"  size="12" readonly="true"  value="<%=today%>" />
					<input type="button" name="buttonDateTo" value=" ... "/>
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("dateTo")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonDateTo")[0],
								showsTime:false
						});
					</script>					
				</div>
			</td>			
		</tr>
	</table>
	<div colspan="2" align="right" class="FORM_DATA"><input type="submit" value="View"></div>
</div>
</form>
