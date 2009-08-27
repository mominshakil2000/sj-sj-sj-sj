<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>

	<sql:query var="rsUser" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
		SELECT  		us.user_id
							,	us.name_title 
							, us.first_name 
							, us.last_name
							, us.middle_name
							, rl.role_name
		FROM 	  		users 	us 
		LEFT OUTER JOIN	roles rl  ON us.role_id=rl.role_id
		<%= (request.getParameter("searchString")==null || request.getParameter("searchString").trim().length()==0) ? (" ") : (" WHERE us.first_name like '%"+request.getParameter("searchString")+"%' or us.middle_name like '%"+request.getParameter("searchString")+"%' or us.last_name like '%"+request.getParameter("searchString")+"%'") %> 		
		ORDER BY us.name_title , us.first_name, us.middle_name, us.last_name, rl.role_id
	</sql:query>

 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;">User List </span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
 </div>
 <div class="BOX" style="width:920;">
 		<!-- Search Form -->
		<form action="../User/ListUser.do" method="post">
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
				<td width="300" class="LIST_HEADER">Roles</td>
				<td width="130" class="LIST_HEADER">Action</td>
			</tr>
		</table>
 		<div style="overflow:auto; width:900px; height:400px; padding:0px; margin:0px">
			<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="870">
				<COL WIDTH=470><COL WIDTH=300><COL WIDTH=100>
				<c:forEach var="rowUser" items="${rsUser.rows}">
					<tr class="LIST_DATA" onmouseover="this.className='HIGHLIGHT_DATA';" onmouseout="this.className='LIST_DATA'">
						<td><c:out value="${rowUser.name_title}"/> <c:out value="${rowUser.first_name}"/> <c:out value="${rowUser.middle_name}"/> <c:out value="${rowUser.last_name}"/></td> 
						<td><c:out value="${rowUser.role_name}"/></td>
						<td><a href="<html:rewrite page='/User/UpdateProfileInit.do?'/>userId=<c:out value='${rowUser.user_id}'/>">Update</a> </td>
					</tr>
				</c:forEach>
			</table>
		</div>
 </div>