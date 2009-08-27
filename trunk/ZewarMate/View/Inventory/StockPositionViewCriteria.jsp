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
		  " SELECT * "
		 +" FROM items "
		);
	  String today = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date());			
%>
<form action="../View/Inventory/StockPositionViewResult.jsp" method="get" target="_blank">
<div><span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE">Stock Position Report</span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
<div class="BOX" style="width:620;">
	<br>
	<table class="FORM_AREA">
		<tr>
			<td class="FORM_CAPTION" width="150">Date </td>
			<td class="FORM_CONTROL" width="450">
					<input type="text"   name="dateOn"  size="12" readonly="true"  value="<%=today%>" />
					<input type="button" name="buttonDateOn" value=" ... " />
					<script type="text/javascript">
						var cal = new Zapatec.Calendar.setup({
								inputField:document.getElementsByName("dateOn")[0],
								ifFormat:"%Y-%m-%d",
								button:document.getElementsByName("buttonDateOn")[0],
								showsTime:false
						});
					</script>
			</td>			
		</tr>
	</table>
	<div colspan="2" align="right" class="FORM_DATA"><input type="submit" value="View"></div>
</div>
</form>
