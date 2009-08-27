<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>

<form action="../System/Authenticate.do" method="post">
	<div><span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE">Login Form &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="FORM_TITLE_RIGHT">&nbsp;</span></div>
	<div class="BOX" style="width:500;">
	
		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="480">
			<tr>
				<td width="150">&nbsp;</td> 
				<td width="330">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;<%= request.getAttribute("error")== null ? " " : request.getAttribute("error") %><br><br></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Login Id</td>
				<td class="FORM_CONTROL"><input type="text" name="loginName" value="<%= (request.getParameter("loginName")==null) ? "" : request.getParameter("loginName") %>" size="25" maxlength="10" /></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Login Password</td>
				<td class="FORM_CONTROL"><input type="password" name="loginPassword" value="" size="25" maxlength="10" /></td>
			</tr>
			<tr>
				<td colspan="2" align="right" ><input type="submit" name="submitAction"  value=" Log In "/></td>
			</tr>
		</table>
	</div>
</form>