<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>



<html:form action="/System/Login" method="post">
	<div><span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE">Login Form &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
	<div class="BOX" style="width:500;">
		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="500">
			<tr>
				<td width="150">&nbsp;</td> 
				<td width="350">&nbsp;</td>
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
				<td class="FORM_CAPTION">Login Name</td>
				<td class="FORM_CONTROL"><html:text property="loginName" size="25" maxlength="10" /></td>
			<tr>
			<tr>
				<td class="FORM_CAPTION">Login Password</td>
				<td class="FORM_CONTROL"><html:password property="loginPassword" size="25" maxlength="10" /></td>
			<tr>
			<tr>
				<td colspan="2" align="right"><html:submit value="  Login  " /></td>
			<tr>
		</table>
	</div>
</html:form>