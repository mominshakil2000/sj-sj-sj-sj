<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>

<%@ page import=" java.util.*
								, com.netxs.Zewar.Lookup.*;"%>

<html:form action="/Vendor/UpdateVendorGroup.do" method="post">
 <html:hidden property="hasFormInitialized" />
 <html:hidden property="vendorTypeId" />
 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;"> Update Vendor Group </span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
 </div>
 <div class="BOX" style="width:820;">
		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="800">
			<logic:messagesPresent >
				<tr>
					<td colspan="2">
						<ul>
				    	<html:messages id="error">
			    	  	<li><c:out value="${error}" /></li>
				    	</html:messages>
			    	</ul>
					</td> 
				</tr>
			</logic:messagesPresent>			
			<tr>
				<td width="150" class="FORM_CAPTION">Name</td>
				<td width="650" class="FORM_CONTROL"><html:text property="vendorTypeName" size="30" maxlength="20" /></td>
			</tr>
			<tr>
				<td width="150" class="FORM_CAPTION">Description</td>
				<td width="650" class="FORM_CONTROL"><html:textarea property="vendorTypeDescription" cols="30" rows="4"></html:textarea></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Account Prefix</td>
				<td class="FORM_CONTROL"><html:text property="accountPrefix" size="30" maxlength="2"/></td>
			</tr>
			<%--
			Shakil :  I think so there is no need to mention type account vendor need every vendor has by default money, all gems & metals acoounts
			<tr>
				<td class="FORM_CAPTION">Account Info</td>
				<td class="FORM_CONTROL">
					<html:checkbox property="moneyAccount" value="Y" /> Money
					<html:checkbox property="goldAccount" value="Y"/> Gold 
					<html:checkbox property="gemAccount" value="Y"/> Gem
				</td>
			</tr>
			 --%>
		</table>
		<div align="right"><html:submit value=" Save " /></div>
	</div>
</html:form>