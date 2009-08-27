<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>

	<sql:query var="rsItemGroups" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
		SELECT  		ig.item_group_id
							, ig.item_group_name
							, ig.item_group_description
		FROM 	  		item_groups	ig 
		ORDER BY 		ig.item_group_name
	</sql:query>

<div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;"> Item Group List </span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
</div>

<div class="BOX" style="width:920;">
	<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="900">
		<tr>
			<td width="300" class="LIST_HEADER">Name</td> 
			<td width="470" class="LIST_HEADER">Description</td>
			<td width="130" class="LIST_HEADER">Action</td>
		</tr>
	</table>
	<div style="overflow:auto; width:900px; height:400px; padding:0px; margin:0px">
		<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="870">
			<COL WIDTH=300><COL WIDTH=470><COL WIDTH=100>
			<c:forEach var="rowItemGroup" items="${rsItemGroups.rows}">
				<tr class="LIST_DATA" onmouseover="this.className='HIGHLIGHT_DATA';" onmouseout="this.className='LIST_DATA'">
					<td><c:out value="${rowItemGroup.item_group_name}"/></td>
					<td><c:out value="${rowItemGroup.item_group_description}"/></td>
					<td><a href="<html:rewrite page='/Item/UpdateItemGroupInit.do?'/>itemGroupId=<c:out value='${rowItemGroup.item_group_id}'/>">Update</a> </td>
				</tr>
			</c:forEach>
		</table>
	</div> 
</div>
