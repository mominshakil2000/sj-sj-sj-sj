<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import=" java.util.*;"%>


<html:form action="/Item/CreateItemGroup.do" method="post">
 <html:hidden property="hasFormInitialized" />
 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;"> New Item Group</span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
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
				<td width="150" class="FORM_CAPTION">Group Name</td>
				<td class="FORM_CONTROL"><html:text property="itemGroupName" size="50" maxlength="50"/></td>			
			</tr>
			<tr>
				<td width="150" class="FORM_CAPTION">Description</td>
				<td class="FORM_CONTROL"><html:textarea property="itemGroupDescription" cols="50" rows="4"></html:textarea> </td>			
			</tr>
		</table>
		<div align="right"><html:submit property="submitAction" value="  Save  " /> <html:submit property="submitAction" value="  Save & Add Another "  /> </div>
	</div>
</html:form>