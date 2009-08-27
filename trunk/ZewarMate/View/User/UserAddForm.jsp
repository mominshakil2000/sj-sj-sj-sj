<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import=" java.util.*
								 , com.netxs.Zewar.Lookup.*;"%>				

<html:form action="/User/CreateProfile.do" method="post">
 <html:hidden property="userId" />
 <html:hidden property="hasFormInitialized" />
 <div>
	<span class="FORM_TITLE_LEFT">&nbsp;</span><span class="FORM_TITLE_MIDDLE" style="width:250;"> New User </span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
 </div>
 <div class="BOX" style="width:820;">
		<logic:messagesPresent >
			<div class="FORM_AREA">
				<ul>
	    		<html:messages id="error">
	    			<li><c:out value="${error}" /></li>
	  	  	</html:messages>
  	  	</ul>
	    </div>
		</logic:messagesPresent>					
		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="800">
			<tr>
				<td width="150" class="FORM_CAPTION">Title</td>
				<td width="650" class="FORM_CONTROL">
					<html:select property="nameTitle">
				        <html:options collection="lookupNameTitle" property="label" labelProperty="label"/>
				    </html:select>
				</td>
			<tr>
			<tr>
				<td class="FORM_CAPTION">Name</td>
				<td class="FORM_CONTROL">
					<div style="width:400;">
						<span style="width:120;">First *</span> 
						<span style="width:120;">Middle</span>  
						<span style="width:120;">Last *</span> <br>
						<span style="width:120;"><html:text property="firstName" size="16" maxlength="20" /></span>  
						<span style="width:120;"><html:text property="middleName" size="16" maxlength="20" /></span>  
						<span style="width:120;"><html:text property="lastName" size="16" maxlength="20" /></span>  
					</div>
				</td>
			<tr>
			<tr>
				<td class="FORM_CAPTION">User Role</td>
				<td class="FORM_CONTROL">
					<html:select property="roleId">
				        <html:options collection="lookupUserRoles" property="id" labelProperty="label"/>
			    </html:select>
				</td>
			<tr>
			<tr>
				<td class="FORM_CAPTION">Login Name *</td>
				<td class="FORM_CONTROL">
					<html:text property="loginName" size="25" maxlength="20"/>
				</td>
			<tr>
			<tr>
				<td class="FORM_CAPTION">Login Password *</td>
				<td class="FORM_CONTROL">
					<html:password property="loginPassword" size="25" maxlength="20"/><br>
				</td>
			<tr>
			<tr>
				<td class="FORM_CAPTION">Confirm Password *</td>
				<td class="FORM_CONTROL">
					<html:password property="confirmPassword" size="25" maxlength="20"/>
				</td>
			<tr>
			<tr>
				<td class="FORM_CAPTION">Description</td>
				<td class="FORM_CONTROL">
					<html:textarea property="description" cols="50" rows="4"/>
				</td>
			<tr>
		</table>
		<div align="right"><html:submit property="submitAction" value="  Save  " /> <html:submit property="submitAction" value="Save & Add Another"  /> </div>
	</div>
</html:form>