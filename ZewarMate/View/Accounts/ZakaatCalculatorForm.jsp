<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 

<%@ page import="java.util.*,javax.naming.*,java.sql.*,javax.sql.*,java.text.DateFormat,java.util.Date,java.util.Locale;"%>
<form action="ZakaatCalculator.do" method="post">
<div><span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE">Zakaat Calculator</span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
<% 	    Connection connection;
		Statement st1, st2, st3, st4, st5;
		ResultSet rs1, rs2, rs3, rs4, rs5;
		
		// Get JNDI Data Source from Context
		Context initContext = new InitialContext();
		Context ctx = (Context)initContext.lookup("java:/comp/env");

		// Pool Connection 
		connection = ((DataSource)ctx.lookup("jdbc/ZewarShaikhaJeweller")).getConnection();

		//Create Statements 
		st1 = connection.createStatement();
		st2 = connection.createStatement();
		st3 = connection.createStatement();
		st4 = connection.createStatement();
		st5 = connection.createStatement();
		String today = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA_FRENCH).format(new Date());	
		String dateFrom = (request.getParameter("dateFrom")!= null && (request.getParameter("dateFrom")).length()>0)? request.getParameter("dateFrom") : today;
		String dateTo = (request.getParameter("dateTo")!= null && (request.getParameter("dateFrom")).length()>0)? request.getParameter("dateTo") : today;
		int i = 0;
		rs1 = st1.executeQuery (
			"SELECT  IT.ITEM_NAME "
			+"      ,IG.ITEM_GROUP_NAME "
			+"      ,IFNULL(IIN.WEIGHT,0)- IFNULL(IOUT.WEIGHT,0) AS WEIGHT "
			+"FROM	items IT "
			+"LEFT OUTER JOIN "
			+" ( SELECT	  ITEM_ID "
			+"          , IFNULL(SUM(WEIGHT),0) AS WEIGHT "
			+"  FROM      inventory_metal_entries "
			+"  WHERE     ENTRY_DATE BETWEEN '"+dateFrom+"' AND '"+dateTo+"' "
			+"  AND       IN_OUT_STATUS=1 "
			+"  GROUP BY  ITEM_ID ) IIN ON IIN.ITEM_ID=IT.ITEM_ID "
			+"LEFT OUTER JOIN "
			+" ( SELECT	ITEM_ID "
			+"        , IFNULL(SUM(WEIGHT),0) AS WEIGHT "
			+"   FROM      inventory_metal_entries "
			+"   WHERE     ENTRY_DATE BETWEEN '"+dateFrom+"' AND '"+dateTo+"' "
			+"   AND       IN_OUT_STATUS=0 "
			+"   GROUP BY  ITEM_ID ) IOUT ON IOUT.ITEM_ID=IT.ITEM_ID "
			+"INNER JOIN item_groups IG ON IT.ITEM_GROUP_ID=IG.ITEM_GROUP_ID "
			+"WHERE	     IT.ITEM_GROUP_ID=1 "
			+"AND        IT.ZAKAAT_APPLY='Y' "
			+"ORDER BY ITEM_NAME"
			
		);		
		rs2 = st2.executeQuery (
				"SELECT  IT.ITEM_NAME "
				+"      ,IG.ITEM_GROUP_NAME "
				+"      ,IFNULL(IIN.WEIGHT,0)- IFNULL(IOUT.WEIGHT,0) AS WEIGHT "
				+"      ,IFNULL(IIN.QUANTITY,0)- IFNULL(IOUT.QUANTITY,0) AS QUANTITY "
				+"FROM	items IT "
				+"LEFT OUTER JOIN "
				+" ( SELECT	  ITEM_ID "
				+"          , IFNULL(SUM(WEIGHT),0) AS WEIGHT "
				+"          , IFNULL(SUM(QUANTITY),0) AS QUANTITY "
				+"  FROM      inventory_gem_entries "
				+"  WHERE     ENTRY_DATE BETWEEN '"+dateFrom+"' AND '"+dateTo+"' "
				+"  AND       IN_OUT_STATUS=1 "
				+"  GROUP BY  ITEM_ID ) IIN ON IIN.ITEM_ID=IT.ITEM_ID "
				+"LEFT OUTER JOIN "
				+" ( SELECT	ITEM_ID "
				+"        , IFNULL(SUM(WEIGHT),0) AS WEIGHT "
				+"        , IFNULL(SUM(QUANTITY),0) AS QUANTITY "
				+"   FROM      inventory_gem_entries "
				+"   WHERE     ENTRY_DATE BETWEEN '"+dateFrom+"' AND '"+dateTo+"' "
				+"   AND       IN_OUT_STATUS=0 "
				+"   GROUP BY  ITEM_ID ) IOUT ON IOUT.ITEM_ID=IT.ITEM_ID "
				+"INNER JOIN item_groups IG ON IT.ITEM_GROUP_ID=IG.ITEM_GROUP_ID "
				+"WHERE	     IT.ITEM_GROUP_ID=2 "
				+"AND        IT.ZAKAAT_APPLY='Y' "
				+"ORDER BY ITEM_NAME"
				
			);		
		
		rs4 = st4.executeQuery (		
		 "SELECT  concat(LA.ACCOUNT_CODE_PREFIX, ' ', LPAD(LA.ACCOUNT_CODE_POSTFIX,6,0),' ',LA.TITLE) AS ACCOUNT_TITLE "
		+"		  ,(IFNULL(LADR2.AMOUNT_DEBIT,0.0) - IFNULL( LACR2.AMOUNT_CREDIT,0.0))-(IFNULL(LADR.AMOUNT_DEBIT,0.0) - IFNULL( LACR.AMOUNT_CREDIT,0.0) + (OPENING_BALANCE * ENTRY_DEBIT_CREDIT) )   AS BALANCE_CURRENT "
		+"  FROM LEDGER_ACCOUNTS LA  "
		+"  LEFT OUTER JOIN "
		+"	 	( SELECT  LEDGER_ACCOUNT_ID_DEBIT "
		+"				 ,SUM(AMOUNT) AS AMOUNT_DEBIT "
		+"		  FROM   LEDGER_ENTRIES  "
		+"		  WHERE  ENTRY_DATE  < '"+dateFrom+"' "
		+"		  GROUP BY LEDGER_ACCOUNT_ID_DEBIT ) AS LADR ON LA.LEDGER_ACCOUNT_ID=LADR.LEDGER_ACCOUNT_ID_DEBIT "
		+"  LEFT OUTER JOIN  "
		+"		( SELECT  LEDGER_ACCOUNT_ID_CREDIT "
		+"				 ,SUM(AMOUNT) AS AMOUNT_CREDIT "
		+"		  FROM   LEDGER_ENTRIES  "
		+"	      WHERE  ENTRY_DATE < '"+dateFrom+"' "
		+"		  GROUP BY LEDGER_ACCOUNT_ID_CREDIT) AS LACR ON LA.LEDGER_ACCOUNT_ID=LACR.LEDGER_ACCOUNT_ID_CREDIT  "
		+"  LEFT OUTER JOIN "
		+"	 	( SELECT  LEDGER_ACCOUNT_ID_DEBIT "
		+"				 ,SUM(AMOUNT) AS AMOUNT_DEBIT "
		+"		  FROM   LEDGER_ENTRIES  "
		+"		  WHERE  ENTRY_DATE < '"+dateTo+"' "
		+"		  GROUP BY LEDGER_ACCOUNT_ID_DEBIT ) AS LADR2 ON LA.LEDGER_ACCOUNT_ID=LADR2.LEDGER_ACCOUNT_ID_DEBIT "
		+"  LEFT OUTER JOIN  "
		+"		( SELECT  LEDGER_ACCOUNT_ID_CREDIT "
		+"				 ,SUM(AMOUNT) AS AMOUNT_CREDIT "
		+"		  FROM   LEDGER_ENTRIES  "
		+"	      WHERE  ENTRY_DATE < '"+dateTo+"' "
		+"		  GROUP BY LEDGER_ACCOUNT_ID_CREDIT) AS LACR2 ON LA.LEDGER_ACCOUNT_ID=LACR2.LEDGER_ACCOUNT_ID_CREDIT  "
		+" WHERE	(LA.ACCOUNT_CODE_PREFIX='CA')	  "
		+" ORDER BY LA.ACCOUNT_CODE_PREFIX, LA.ACCOUNT_CODE_POSTFIX ");
		
		rs5 = st5.executeQuery (		
		 "SELECT    concat(LA.ACCOUNT_CODE_PREFIX, ' ', LPAD(LA.ACCOUNT_CODE_POSTFIX,6,0),' ',LA.TITLE) AS ACCOUNT_TITLE "
		+"		 ,(IFNULL(LADR.AMOUNT_DEBIT,0.0) - IFNULL( LACR.AMOUNT_CREDIT,0.0) + (OPENING_BALANCE * ENTRY_DEBIT_CREDIT) )    AS AMOUNT_PAID "
		+"  FROM LEDGER_ACCOUNTS LA  "
		+"  LEFT OUTER JOIN "
		+"	 	( SELECT  LEDGER_ACCOUNT_ID_DEBIT "
		+"				 ,SUM(AMOUNT) AS AMOUNT_DEBIT "
		+"		  FROM   LEDGER_ENTRIES  "
		+"		  WHERE  ENTRY_DATE BETWEEN '"+dateFrom+"' AND '"+dateTo+"' "
		+"		  GROUP BY LEDGER_ACCOUNT_ID_DEBIT ) AS LADR ON LA.LEDGER_ACCOUNT_ID=LADR.LEDGER_ACCOUNT_ID_DEBIT "
		+"  LEFT OUTER JOIN  "
		+"		( SELECT  LEDGER_ACCOUNT_ID_CREDIT "
		+"				 ,SUM(AMOUNT) AS AMOUNT_CREDIT "
		+"		  FROM   LEDGER_ENTRIES  "
		+"	      WHERE  ENTRY_DATE BETWEEN '"+dateFrom+"' AND '"+dateTo+"' "
		+"		  GROUP BY LEDGER_ACCOUNT_ID_CREDIT) AS LACR ON LA.LEDGER_ACCOUNT_ID=LACR.LEDGER_ACCOUNT_ID_CREDIT  "
		+" WHERE	(LA.ACCOUNT_CODE_PREFIX='EX' AND  LA.ACCOUNT_CODE_POSTFIX='6' )	  "
		+" ORDER BY LA.ACCOUNT_CODE_PREFIX, LA.ACCOUNT_CODE_POSTFIX ");
%>
<div class="BOX" style="width:990;">
	<div>
		<div>
			From
			<input type="text"   name="dateFrom"  size="12" readonly="true"  value="<%=dateFrom%>" />
			<input type="button" name="buttonDateFrom" value=" ... " />
			<script type="text/javascript">
				var cal = new Zapatec.Calendar.setup({
						inputField:document.getElementsByName("dateFrom")[0],
						ifFormat:"%Y-%m-%d",
						button:document.getElementsByName("buttonDateFrom")[0],
						showsTime:false
				});
			</script>
			&nbsp;&nbsp;&nbsp; To &nbsp;
			<input type="text"   name="dateTo"  size="12" readonly="true"  value="<%=dateTo%>" />
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
	</div>
	<table class="LIST_AREA" cellpadding="0" cellspacing="1" border="0" width="960">
		<tr>
			<td class="LIST_HEADER" width="380">Item</td>
			<td class="LIST_HEADER" width="100">Item Group</td>
			<td class="LIST_HEADER" width="100">Quantity</td>
			<td class="LIST_HEADER" width="120">Weight</td>
			<td class="LIST_HEADER" width="80">Calc By</td>
			<td class="LIST_HEADER" width="90">Current Rate</td>
			<td class="LIST_HEADER" width="90">Current Value</td>
		</tr>
		<% while (rs1.next()) { 
			if(rs1.getFloat("Weight") > 0.0) {
				i++;	
		%>		<tr>
					<td class="LIST_DATA"><%=rs1.getString("Item_Name")%></td>
					<td class="LIST_DATA"><%=rs1.getString("Item_Group_Name")%></td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA"><input type="text" name="weight_<%=i%>" value="<%=rs1.getString("WEIGHT")%>" size="12">gms</td>
					<td class="LIST_DATA">&nbsp;</td>
					<td class="LIST_DATA"><input type="text" name="rate_<%=i%>" value="" size="11"></td>
					<td class="LIST_DATA"><input type="text" name="value_<%=i%>" value="" size="11"></td>
				</tr>
		<% }} %>
		<% while (rs2.next()) { 
			if(rs2.getFloat("Weight") > 0.0 || rs2.getFloat("Quantity") > 0.0) {
				i++;	
		%>		<tr>
					<td class="LIST_DATA"><%=rs2.getString("Item_Name")%></td>
					<td class="LIST_DATA"><%=rs2.getString("Item_Group_Name")%></td>
					<td class="LIST_DATA"><input type="text" name="quantity_<%=i%>" value="<%=rs2.getString("Quantity")%>" size="12"></td>
					<td class="LIST_DATA"><input type="text" name="weight_<%=i%>" value="<%=rs2.getString("Weight")%>" size="12">gms</td>
					<td class="LIST_DATA"><select name="calculateBy_<%=i%>"><option>Quantity</option><option>Weight</option></select></td>
					<td class="LIST_DATA"><input type="text" name="rate_<%=i%>" value="" size="11"></td>
					<td class="LIST_DATA"><input type="text" name="value_<%=i%>"  value="" size="11"></td>
				</tr>
		<% }} %>
		<tr>
			<td class="LIST_DATA">Earnings</td>
			<td class="LIST_DATA">Jewellery</td>
			<td class="LIST_DATA">12</td>
			<td class="LIST_DATA">15.00 gms</td>
			<td class="LIST_DATA"><select><option>Quantity</option><option>Weight</option></select></td>
			<td class="LIST_DATA"><input type="text" value="" size="11"></td>
			<td class="LIST_DATA"><input type="text" value="" size="11"></td>
		</tr>
		<% while (rs4.next()) { 
			i++;	
		%>
		<tr>
			<td class="LIST_DATA" colspan="6">Add : <%=rs4.getString("Account_Title")%></td>
			<td class="LIST_DATA"><input type="text" name="value_<%=i%>"  value="<%=rs4.getString("BALANCE_CURRENT")%>" size="11"></td>
		</tr>
		<% } %>
		<% while (rs5.next()) { 
		%>
		<tr>
			<td class="LIST_DATA" colspan="6">Less: <%=rs5.getString("Account_Title")%></td>
			<td class="LIST_DATA"><input type="text" name="zakaatpaid"  value="<%=rs5.getString("AMOUNT_PAID")%>" size="11"></td>
		</tr>
		<% } %>
		<tr>
			<td class="LIST_DATA" colspan="6">Zakkat Due</td>
			<td class="LIST_DATA"><input type="text" value="" size="11"></td>
		</tr>
	</table>
	<div align="right"  style="width:900px;"><input type="submit" value="Calculate" ></div>
</div>
</form>
