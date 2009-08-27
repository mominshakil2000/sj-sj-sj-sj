<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>

<%@ page import=" java.util.*
				, com.netxs.Zewar.Lookup.DTS.LookupDTSAccounts,com.netxs.Zewar.Lookup.DTO.LookupDTOAccounts;"%>

<% // Initialze Lookup Elements
	application.setAttribute("lookupAccountParent", (new LookupDTSAccounts()).getList() );
%>


<html:form action="/CashBook/View" method="post">
	<div class="FORM_TITLE">View Cash</div>
	<div class="BOX" style="width:620; ">
		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="600">
			<tr>
				<td width="150">&nbsp;</td> 
				<td width="450">&nbsp;</td>
			<tr>
			<logic:messagesPresent >
				<tr>
					<td colspan="2">
						<ul>
				    	<html:messages id="error">
			    	  	<li><c:out value="${error}" /></li>
				    	</html:messages>
			    	</ul>
					</td> 
				<tr>
			</logic:messagesPresent>
			<tr>
				<td class="FORM_CAPTION">Accounts</td>
				<td class="FORM_CONTROL" valign="top">
					From&nbsp;&nbsp;
					<html:select property="accountIdFrom">
						<html:options collection="lookupAccountParent" property="accountId" labelProperty="label"/>
					</html:select>
					<br>
					To&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<html:select property="accountIdTo">
		        <html:options collection="lookupAccountParent" property="accountId" labelProperty="label"/>
					</html:select>
				</td>
			<tr>
			<tr>
				<td class="FORM_CAPTION">Date</td>
				<td class="FORM_CONTROL"  valign="top">
					From&nbsp;&nbsp;
					<html:text property="dateFrom"  size="12" readonly="true" />
					<html:button property="buttonDateFrom" value="... " onclick="return showCalendar('dateFrom', 'dd/mm/y');" />
					<br>
					To&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<html:text property="dateTo"  size="12" readonly="true" />
					<html:button property="buttonDateTo" value="... " onclick="return showCalendar('dateTo', 'dd/mm/y');" />
				</td>
			<tr>
			<tr>
				<td colspan="2" align="right"><html:submit value=" View " /></td>
			<tr>
		</table>
	</div>
</html:form>