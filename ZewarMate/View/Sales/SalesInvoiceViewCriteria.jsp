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
		 +" WHERE la.account_description_level=2 AND la.account_code_prefix='CS'"
		 +" ORDER BY la.title"
		);		
	  String today = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date());			
%>
<form action="../View/Sales/SalesInvoiceViewResult.jsp" method="get" target="_blank">
<div><span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE">Sales Invoice Report</span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
<div class="BOX" style="width:620;">
	<br>
	<table class="FORM_AREA">
		<tr>
			<td class="FORM_CONTROL" colspan="2"><br><input type="radio" checked="checked" name="criteriaOption" value="1">Option 1</td>
		</tr>
		<tr>
			<td class="FORM_CAPTION" width="150">Customer</td>
			<td class="FORM_CONTROL">
				<div>
					<select name="accountCode">
						<%	while (rsCashBookAccounts.next()) 
							 		out.print("<option value=\""+rsCashBookAccounts.getString("ledger_account_id")+"\">"+rsCashBookAccounts.getString("account_code_prefix")+" "+rsCashBookAccounts.getString("account_code_postfix")+" "+rsCashBookAccounts.getString("title")+"</option>\n");
						%>
					</select> 
				</div>
			</td>
		</tr>
		<tr>
			<td class="FORM_CAPTION">Order Item</td>
			<td class="FORM_CONTROL">
				<div>
					<select name="itemId" multiple="multiple" size="8">
					  <c:forEach var="lookup" items="${applicationScope.lookupItemJewellery}" varStatus="status" >
						  <c:choose>
					        <c:when test='${param.orderItemId == lookup.id}'><option value="<c:out value='${lookup.id}'/>" selected><c:out value='${lookup.label}'/></option></c:when>
					        <c:otherwise><option value="<c:out value='${lookup.id}'/>" ><c:out value='${lookup.label}'/></option></c:otherwise>
						 </c:choose>
						</c:forEach>
					</select><br>(Hint : for multiple selection hold down "Ctrl" Key and click on ledger accounts)
				</div>
			</td>
		</tr>
		<tr>
			<td class="FORM_CONTROL" colspan="2"><br><input  type="radio" name="criteriaOption" value="2">Option 2</td>
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
		<tr>
			<td class="FORM_CONTROL" colspan="2"><br><input type="radio" name="criteriaOption"  value="3">Option 3</td>
		</tr>
		<tr>
			<td class="FORM_CAPTION">Sales Order Id</td>
			<td class="FORM_CONTROL"><input type="text" name="salesOrderId" value="0" ></td>			
		</tr>

	</table>
	<div colspan="2" align="right" class="FORM_DATA"><input type="submit" value="View"></div>
</div>
</form>
