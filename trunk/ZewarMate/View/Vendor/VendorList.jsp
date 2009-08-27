<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>

<sql:query var="rsVendors" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
		SELECT  		vn.vendor_id
							, vn.name_title 
							, vn.first_name 
							, vn.middle_name
							, vn.last_name
		FROM 	  		vendors 	vn 
		<%= (request.getParameter("searchString")==null || request.getParameter("searchString").trim().length()==0) ? (" ") : (" WHERE vn.first_name like '%"+request.getParameter("searchString")+"%' or vn.middle_name like '%"+request.getParameter("searchString")+"%' or vn.last_name like '%"+request.getParameter("searchString")+"%'") %> 		
		ORDER BY  vn.first_name
</sql:query>

 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;"> Vendor List </span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
 </div>
 <div class="BOX" style="width:920;">
 		<!-- Search Form -->
		<form action="../Vendor/ListVendor.do" method="post">
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
				<COL WIDTH=290><COL WIDTH=480><COL WIDTH=100> 
					<c:forEach var="rowVendor" items="${rsVendors.rows}" >
						<sql:query var="rsVendorRoles" dataSource="jdbc/ZewarShaikhaJeweller"  >
							SELECT  		vr.vendor_id
												, vt.vendor_type_name 
							FROM 	  		vendor_types_relation vr
							INNER JOIN  vendor_types 	vt ON vr.vendor_type_id=vt.vendor_type_id
							WHERE 			vr.vendor_id=<c:out value="${rowVendor.vendor_id}"/> 
							ORDER BY vt.vendor_type_id
						</sql:query>
						<tr class="LIST_DATA" onmouseover="this.className='HIGHLIGHT_DATA';" onmouseout="this.className='LIST_DATA'">
							<td><c:out value="${rowVendor.name_title}"/> <c:out value="${rowVendor.first_name}"/> <c:out value="${rowVendor.middle_name}"/> <c:out value="${rowVendor.last_name}"/></td> 
							<td>
								<c:forEach var="rowVendorRoles" items="${rsVendorRoles.rows}" >
									<c:out value="${rowVendorRoles.vendor_type_name}"/>,
								</c:forEach>
								&nbsp;
							</td>
							<td><a href="<html:rewrite page='/Vendor/UpdateVendorProfileInit.do?'/>vendorId=<c:out value='${rowVendor.vendor_id}'/>">Update</a> </td>
						</tr>
					</c:forEach>
				</table>
		</div>
	</div>