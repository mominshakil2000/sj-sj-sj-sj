<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import=" java.util.*
				, com.netxs.Zewar.Lookup.*;"%>


<html:form action="/Vendor/CreateVendorProfile.do" method="post">
 <html:hidden property="hasFormInitialized" />
 <div>
	<span class="FORM_TITLE_LEFT">&nbsp</span><span class="FORM_TITLE_MIDDLE" style="width:250;"> New Vendor </span><span class="FORM_TITLE_RIGHT" style="text-align:right;width:360;">&nbsp;</span>
 </div>
 <div class="BOX" style="width:950px;">
 	 	<logic:messagesPresent >
			<div class="FORM_AREA">
				<ul>
		    		<html:messages id="error">
		    			<li><c:out value="${error}" /></li>
		  	  		</html:messages>
  	  			</ul>
	    	</div>
		</logic:messagesPresent>		
		<table border="0" class="FORM_AREA" cellpadding="0" cellspacing="1" width="555">
			<tr>
				<td width="150" class="FORM_CAPTION">Title</td>
				<td width="405" class="FORM_CONTROL">
					<html:select property="nameTitle">
				        <html:options collection="lookupNameTitle" property="label" labelProperty="label"/>
				    </html:select>
				</td>
			</tr>
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
			</tr>
			<tr>
				<td class="FORM_CAPTION">Company</td>
				<td class="FORM_CONTROL"><html:text property="companyName" size="57" maxlength="50"/></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Address</td>
				<td class="FORM_CONTROL">
					<html:text property="addressLine1" size="57" maxlength="50"/><br>
					<html:text property="addressLine2" size="57" maxlength="50"/>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Phone</td>
				<td class="FORM_CONTROL">
					<html:text property="phone1" size="25" maxlength="20"/><br>
					<html:text property="phone2" size="25" maxlength="20"/>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Mobile</td>
				<td class="FORM_CONTROL"><html:text property="mobile" size="25" maxlength="20"/><br></td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Email</td>
				<td class="FORM_CONTROL">
					<html:text property="email1" size="25" maxlength="50"/><br>
					<html:text property="email2" size="25" maxlength="50"/>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Vendor Roles</td>
				<td class="FORM_CONTROL">
					<div style="width:405px;">
						<c:forEach var="item" items="${lookupVendorType}" >
							<span style="width:135px;">
								<html:multibox name="vendorForm" property="vendorTypeId" > 
									<c:out value="${item.id}"/>
								</html:multibox>
								<c:out value="${item.label}"/> &nbsp;
							</span>
			   			</c:forEach>
			   		</div>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Body Making Rate /Stone</td>
				<td class="FORM_CONTROL">
					<div style="width:300px;">
						<span style="width:150px;">Simple</span><span style="width:150px;">Mix</span>
						<span style="width:145px;"><html:text property="bodyMakingRateSimple" size="15" /></span>
						<span style="width:150px;"><html:text property="bodyMakingRateMix" size="15" /></span>
				   	</div>
				</td>
			</tr>
			<tr>
				<td class="FORM_CAPTION">Stone Setting Rate /Stone</td>
				<td class="FORM_CONTROL">
					<div style="width:300px;">
						<span style="width:150px;">Simple</span><span style="width:150px;">Difficult</span>
						<span style="width:145px;"><html:text property="stoneSettingRateSimple" size="15" /></span>
						<span style="width:150px;"><html:text property="stoneSettingRateDifficult" size="15" /></span>
				   	</div>
				</td>
			</tr>
		</table>
		<br>
		<div>
	 		<span style="width:365px; vertical-align:top;">
				<!-- Company Agreed Wastaged Items -->
				<div class="LIST_AREA" class="LIST_HEADER"><b>Metals Agreed Wastage</b></div>
				<div class="LIST_AREA"><span style="width:290px;" class="LIST_HEADER">Item<br><br></span><span style="width:70px;" class="LIST_HEADER">Rate (Raati)</span></div>
		 		<div style="overflow:auto; width:360px; height:300px; padding:0px; margin:0px;border-width:1px; border-color:#736356; border-style:solid; border-collapse:collapse;">
					<table border="0" class="LIST_AREA" cellpadding="0" cellspacing="1" width="340">
						<COL WIDTH=290><COL WIDTH=50>
						<logic:iterate id="metalWastage" name="vendorForm" property="metalWastageList">
							<tr>
								<td class="LIST_DATA" >
									<html:hidden name="metalWastage" property="insertable" indexed="true" styleId="idItemWastageInsertable" />
									<html:hidden name="metalWastage" property="itemId" indexed="true" styleId="idItemWastageItemId" />
									<html:hidden name="metalWastage" property="itemName" indexed="true" styleId="idItemWastageItemName" />
									<bean:write name="metalWastage" property="itemName"/>
								</td>
								<td class="LIST_DATA" ><html:text name="metalWastage" property="agreedWastage" indexed="true" styleId="idItemWastageAgreedWastage" size="4" maxlength="5" />
								</td>
							</tr>
						</logic:iterate>
					</table>
				</div>
			</span>
		</div>
		<div align="right"><br><html:submit property="submitAction" value="  Save  " /> <html:submit property="submitAction" value="Save & Add Another"  /> </div>
	</div>
</html:form>