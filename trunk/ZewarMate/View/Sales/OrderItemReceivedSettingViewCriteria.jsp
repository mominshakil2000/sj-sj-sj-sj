<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 

<%@ page import="java.util.*,javax.naming.*,java.sql.*,javax.sql.*,java.text.DateFormat,java.util.Date,java.util.Locale;"%>

<% 	Connection connection;
		Statement stVendorAccounts,stCustomerAccounts;
		ResultSet rsVendorAccounts,rsCustomerAccounts;
		
		// Get JNDI Data Source from Context
		Context initContext = new InitialContext();
		Context ctx = (Context)initContext.lookup("java:/comp/env");

		// Pool Connection 
		connection = ((DataSource)ctx.lookup("jdbc/ZewarShaikhaJeweller")).getConnection();

		//Create Statements 
		stVendorAccounts = connection.createStatement();
		stCustomerAccounts = connection.createStatement();

		rsVendorAccounts = stVendorAccounts.executeQuery (
		  " SELECT la.ledger_account_id, la.title, la.account_code_prefix, LPAD(la.account_code_postfix,6,'0') AS account_code_postfix "
		 +" FROM ledger_accounts la "
		 +" WHERE la.account_description_level=2 AND la.account_code_prefix='VN'"
		 +" ORDER BY la.account_code_prefix, la.account_code_postfix"
		);		
		rsCustomerAccounts = stCustomerAccounts.executeQuery (
				  " SELECT la.ledger_account_id, la.title, la.account_code_prefix, LPAD(la.account_code_postfix,6,'0') AS account_code_postfix "
				 +" FROM ledger_accounts la "
				 +" WHERE la.account_description_level=2 AND la.account_code_prefix='CS'"
				 +" ORDER BY la.account_code_prefix, la.account_code_postfix"
				);		

	  String today = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date());			
%>
<form action="../View/Sales/OrderItemReceivedSettingViewResult.jsp" method="post" target="_blank">
<div><span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE" style="width:300px;">Order Item Received After Setting Report </span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
<div class="BOX" style="width:620;">
	<br>
	<table class="FORM_AREA">
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

		<tr>
			<td class="FORM_CAPTION" width="150">Vendor</td>
			<td class="FORM_CONTROL">
				<div>
					<select name="accountCodeVendor" multiple="multiple" size="8">
						<%	while (rsVendorAccounts.next()) 
							 		out.print("<option value=\""+rsVendorAccounts.getString("ledger_account_id")+"\">"+rsVendorAccounts.getString("account_code_prefix")+" "+rsVendorAccounts.getString("account_code_postfix")+" "+rsVendorAccounts.getString("title")+"</option>\n");
						%>
					</select> <br>(Hint : for multiple selection hold down "Ctrl" Key and click on selection item)
				</div>
			</td>
		</tr>
<!--
		<tr>
			<td class="FORM_CAPTION" width="150">Body Maker</td>
			<td class="FORM_CONTROL">
				<div>
					<select name="accountCodeBodyMaker" multiple="multiple" size="8">
						<%	rsVendorAccounts.beforeFirst();
							while (rsVendorAccounts.next()) 
							 		out.print("<option value=\""+rsVendorAccounts.getString("ledger_account_id")+"\">"+rsVendorAccounts.getString("account_code_prefix")+" "+rsVendorAccounts.getString("account_code_postfix")+" "+rsVendorAccounts.getString("title")+"</option>\n");
						%>
					</select> <br>(Hint : for multiple selection hold down "Ctrl" Key and click on selection item)
				</div>
			</td>
		</tr>
-->
		<tr>
			<td class="FORM_CAPTION">Customer</td>
			<td class="FORM_CONTROL">
				<div>
					<select name="accountCodeCustomer" multiple="multiple" size="8">
						<%	while (rsCustomerAccounts.next()) 
							 		out.print("<option value=\""+rsCustomerAccounts.getString("ledger_account_id")+"\">"+rsCustomerAccounts.getString("account_code_prefix")+" "+rsCustomerAccounts.getString("account_code_postfix")+" "+rsCustomerAccounts.getString("title")+"</option>\n");
						%>
					</select> <br>(Hint : for multiple selection hold down "Ctrl" Key and click on selection item)
				</div>
			</td>
		</tr>
		<tr>
			<td class="FORM_CAPTION">Order Item</td>
			<td class="FORM_CONTROL">
				<div>
					<select name="itemId" multiple="multiple" size="8">
					  <c:forEach var="lookup" items="${applicationScope.lookupItemJewellery}" varStatus="status" >
					        <option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option>
						</c:forEach>
					</select><br>(Hint : for multiple selection hold down "Ctrl" Key and click on selection item)
				</div>
			</td>
		</tr>
	</table>
	<div colspan="2" align="right" class="FORM_DATA"><input type="submit" value="View"></div>
</div>
</form>
