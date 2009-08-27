<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ page import="java.util.*,javax.naming.*,java.sql.*,javax.sql.*;"%>
<%		
			Connection connection;
			Statement st1, st2;
			ResultSet rs1, rs2;

			// Get JNDI Data Source from Context
			Context initContext = new InitialContext();
			Context ctx = (Context)initContext.lookup("java:/comp/env");

			// Pool Connection 
			connection = ((DataSource)ctx.lookup("jdbc/ZewarShaikhaJeweller")).getConnection();

			//Create Statements 
			st1 = connection.createStatement();
			st2 = connection.createStatement();

			int ledgerAccountId=0;
			int parentLedgerAccountId=0;
			int accountCodePostfix=0;

			//Get user whose ledger account has not created yet
			rs1 = st1.executeQuery (
					 " Select"
					+"  vendor_id" 
					+" ,concat(NAME_TITLE,' ', FIRST_NAME, ' ', MIDDLE_NAME, ' ', LAST_NAME) AS vendor_NAME"
					+" ,COMPANY_NAME" 
					+" ,LEDGER_ACCOUNT_ID"
					+" From"
					+"  vendors"
			 +" WHERE ledger_account_id In ('0, null')"
			);
			
			// get vendor Parent Ledger Account Id
			rs2 = st2.executeQuery ( "Select ledger_account_id From ledger_accounts WHERE account_code_prefix ='VN' AND account_code_postfix=0"  );
			if (rs2.next()) {
				parentLedgerAccountId = rs2.getInt(1);
			} else {
					st2.execute (
						" INSERT INTO ledger_accounts ("
					 +"   ledger_account_type_id"
					 +" , parent_ledger_account_id"
					 +" , title"
					 +" , description"
					 +" , account_code_prefix"
					 +" , account_code_postfix"
					 +" , account_active"
					 +" , opening_balance"
					 +" , opening_balance_type"
					 +" , account_description_level"
					 +" , account_create_date) "
					 +" VALUES ("
					 +"    2"
					 +" ,  0"
					 +" , 'Vendor Account'"				 
					 +" , 'Head of all vendor account'"
					 +" , 'VN'"
					 +" ,  0"
					 +" , 'Y'"
					 +" ,  0"
					 +" , 'D'"
					 +" ,  1"
					 +" ,  curDate()"
					 +")"			
					);
				 rs2 = st2.executeQuery ( "Select ledger_account_id From ledger_accounts WHERE account_code_prefix ='VN'");
		 		 if (rs2.next()) {
						parentLedgerAccountId = rs2.getInt(1);
				 }
				 else {
				 	connection.close();
				 	out.print("Problem in creating vendor Control Account (VN 000000).");
				 	out.close();
				 }
			}
			rs2 = st2.executeQuery ( "Select max(account_code_postfix)+1 From ledger_accounts WHERE account_code_prefix ='VN'");
			if (rs2.next()) {
				accountCodePostfix = rs2.getInt(1);
			}

			//Add Ledger Account
			int count=0;
		 while (rs1.next()) {
	 	  ++count;
			st2.execute (
				" INSERT INTO ledger_accounts ("
			 +"   ledger_account_type_id"
			 +" , parent_ledger_account_id"
			 +" , title"
			 +" , description"
			 +" , account_code_prefix"
			 +" , account_code_postfix"
			 +" , account_active"
			 +" , opening_balance"
			 +" , opening_balance_type"
			 +" , account_description_level"
			 +" , account_create_date) "
			 +" VALUES ("
			 +"   2   "
			 +" , "+ parentLedgerAccountId
			 +" , concat('"+rs1.getString("VENDOR_NAME")+"')" 
			 +" , concat('***Vendor Account "+rs1.getString("VENDOR_NAME")+"*** ***Company Name "+rs1.getString("COMPANY_NAME")+"***')"
			 +" ,'VN'"
			 +" , "+ accountCodePostfix++
			 +" ,'Y' "
			 +" , 0  "
			 +" ,'D' "
			 +" , 2  "
			 +" ,curDate() )"
			);
			rs2 = st2.executeQuery ( "Select max(ledger_account_id) From ledger_accounts ");
			if (rs2.next()) {
				ledgerAccountId = rs2.getInt(1);
			}
			st2.execute ("UPDATE vendors Set ledger_account_id ="+ledgerAccountId+" WHERE vendor_id="+rs1.getInt("vendor_id"));
		 }
			rs1.beforeFirst();
			rs2 = st2.executeQuery (
					 " Select"
					+"  concat(NAME_TITLE,' ', FIRST_NAME, ' ', MIDDLE_NAME, ' ', LAST_NAME) AS VENDOR_NAME"
					+" ,COMPANY_NAME" 
					+" ,LEDGER_ACCOUNT_ID"
					+" From"
					+"  vendors"
					+" ORDER BY ledger_account_id desc"
			);			

%>
		<div class="BOX">Attention Mr.Saeed : Please copy below result and email me. </div><br>
		<div class="BOX">Note : Status 0 shows that vendor has no ledger account </div><br>
		<div class="BOX">
		<table border="0" class="LIST_AREA" cellpadding="0" celspacing="1" width="900">
					<tr>
						<td width="400" class="LIST_HEADER">Vendor Name</td>
						<td width="400" class="LIST_HEADER">Company </td>
						<td width="100" class="LIST_HEADER">Status</td>
					</tr>
		  	  <%	while(rs1.next()) { %>
								<tr>
								  <td class="LIST_DATA"><%=rs1.getString("VENDOR_NAME")%></td>
								  <td class="LIST_DATA"><%=rs1.getString("COMPANY_NAME")%></td>
									<td class="LIST_DATA"><%=rs1.getString("LEDGER_ACCOUNT_ID")%></td>
								</tr>
					<%	 }  %>
		</table>
		<br>

		<table border="0" class="LIST_AREA" cellpadding="0" celspacing="1" width="900">
					<tr>
						<td width="400" class="LIST_HEADER">Vendor Name</td>
						<td width="400" class="LIST_HEADER">Company </td>
						<td width="100" class="LIST_HEADER">Status</td>
					</tr>
		  	  <%	while(rs2.next()) { %>
								<tr>
								  <td class="LIST_DATA"><%=rs2.getString("VENDOR_NAME")%></td>
								  <td class="LIST_DATA"><%=rs2.getString("COMPANY_NAME")%></td>
									<td class="LIST_DATA"><%=rs2.getString("LEDGER_ACCOUNT_ID")%></td>
								</tr>
					<%	 }  %>
					<tr>
						<td colspan="2" class="LIST_DATA" align="left"><b>Total New Ledger Account Created</b></td>
						<td class="LIST_DATA"><b><%=count%></b></td>
					</tr>
		</table>
	</div>
<%	//Release database access objects
		rs1.close();
		st1.close();

		rs2.close();
		st2.close();
		connection.close();			%>