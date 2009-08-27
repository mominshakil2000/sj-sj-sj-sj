<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>

	<sql:query var="rsItems" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
		SELECT  		it.item_id
							, it.item_group_id
							, it.item_name
							, it.item_description
							, it.zakaat_apply
							, ig.item_group_name
		FROM 	  		items	it 
		INNER JOIN  item_groups ig ON it.item_group_id=ig.item_group_id
		<%= (request.getParameter("searchString")==null || request.getParameter("searchString").trim().length()==0) ? (" ") : (" WHERE it.item_name like '%"+request.getParameter("searchString")+"%'") %> 
		ORDER BY it.item_group_id, it.item_name
	</sql:query>

 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;"> Item List </span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
 </div>
 <div class="BOX" style="width:920;">
 		 <!-- Search Form -->
		<form action="../Item/ListItem.do" method="post">
			<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="580">
				<tr>
					<td class="FORM_CAPTION" width="150">Search String</td>
					<td class="FORM_CONTROL">
						<input type="text" name="searchString"  size="40" value='<%=((request.getParameter("searchString")==null) ? ("") : (request.getParameter("searchString")))%>' /> 
						<input type="submit"  name="Search" value="Search" />
					</td>
				</tr>
			</table>
		</form>
	 	<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="900">
			<tr>
				<td width="470" class="LIST_HEADER">Name</td> 
				<td width="300" class="LIST_HEADER">Item Group</td>
				<td width="130" class="LIST_HEADER">Action</td>
			</tr>
		</table>
 		<div style="overflow:auto; width:900px; height:400px; padding:0px; margin:0px">
			<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="870">
				<COL WIDTH=300><COL WIDTH=470><COL WIDTH=100>
				<c:forEach var="rowItem" items="${rsItems.rows}">
					<tr class="LIST_DATA" onmouseover="this.className='HIGHLIGHT_DATA';" onmouseout="this.className='LIST_DATA'">
						<td><c:out value="${rowItem.item_name}"/></td> 
						<td><c:out value="${rowItem.item_group_name}"/></td>
						<td><a href="<html:rewrite page='/Item/UpdateItemInit.do?'/>itemId=<c:out value='${rowItem.item_id}'/>"> Update </a> </td>
					</tr>
				</c:forEach>
			</table>
		</div> 
	</div>