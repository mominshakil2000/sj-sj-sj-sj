<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>

	<sql:query var="rsVendorTypes" dataSource="jdbc/ZewarShaikhaJeweller" scope="page" >
		SELECT  	vendor_type_id
						, vendor_type_name
						, vendor_type_description
						, account_prefix
						, gold_account
						, money_account
						, gem_account
		FROM 	  		vendor_types 	 
	</sql:query>

 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;"> Vendor Group List </span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
 </div>
 <div class="BOX" style="width:920;">
		<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="900">
			<tr>
				<td width="470" class="LIST_HEADER">Name</td> 
				<td width="300" class="LIST_HEADER">Description</td>
				<td width="130" class="LIST_HEADER">Action</td>
			</tr>
		</table>
 		<div style="overflow:auto; width:900px; height:400px; padding:0px; margin:0px">
			<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="870">
				<COL WIDTH=290><COL WIDTH=480><COL WIDTH=100>
					<c:forEach var="rowVendorType" items="${rsVendorTypes.rows}">
						<tr class="LIST_DATA" onmouseover="this.className='HIGHLIGHT_DATA';" onmouseout="this.className='LIST_DATA'">
							<td><c:out value="${rowVendorType.vendor_type_name}"/></td> 
							<td><c:out value="${rowVendorType.vendor_type_description}"/></td> 
							<td><a href="<html:rewrite page='/Vendor/UpdateVendorGroupInit.do?'/>vendorTypeId=<c:out value='${rowVendorType.vendor_type_id}'/>">Update</a> </td>
						</tr>
					</c:forEach>
			</table>
		</div>
	</div>