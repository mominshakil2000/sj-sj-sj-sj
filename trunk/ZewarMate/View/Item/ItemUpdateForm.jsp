<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import=" java.util.*
								 , com.netxs.Zewar.Lookup.*;"%>
								 
<html:form action="/Item/UpdateItem.do" method="post">
 <html:hidden property="hasFormInitialized" />
  <html:hidden property="itemId" />
 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;"> Update Item</span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
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
				<td width="150" class="FORM_CAPTION">Name *</td>
				<td class="FORM_CONTROL"><html:text property="itemName" size="50" maxlength="50"/></td>			
			</tr>
			<tr>
				<td width="150" class="FORM_CAPTION">Description *</td>
				<td class="FORM_CONTROL"><html:textarea property="itemDescription" cols="50" rows="4"></html:textarea> </td>			
			</tr>
			<tr>
				<td class="FORM_CAPTION">Item Group</td>
				<td class="FORM_CONTROL">
					<html:select property="itemGroupId">
				        <html:options collection="lookupItemGroup" property="id" labelProperty="label"/>
			    </html:select>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Zakat Apply</td>
				<td class="FORM_CONTROL"><html:radio property="zakaatApply" value="Y" />Yes <html:radio property="zakaatApply" value="N" />No </td>
			</tr>
		</table>
		<div align="right"><html:submit value="  Save  " /></div>
	</div>
</html:form>