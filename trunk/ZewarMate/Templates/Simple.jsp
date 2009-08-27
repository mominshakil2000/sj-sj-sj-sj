<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<html:html>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="-1">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 	
	<title>ZEWAR - Jeweller's Mate</title>
	<link rel="stylesheet" type="text/css" href="<html:rewrite page='/Styles/Application.css' />">
	<!-- import the calendar scripts and Style-->
	<script type="text/javascript" src="<html:rewrite page='/Scripts/Calendar.js' />"></script>
	<script type="text/javascript" src="<html:rewrite page='/Scripts/CalendarHelper.js' />"></script>
	<script type="text/javascript" src="<html:rewrite page='/Scripts/Common.js' />"></script>
	<script type="text/javascript" src="<html:rewrite page='/Scripts/Lookup.js' />"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<html:rewrite page='/Styles/Calendar.css' />" title="win2k">
	<script language="javascript">
		window.history.forward(1);
	</script>
</head>
<body>
<table width="980">
	<tr>
		<td width="10">&nbsp;</td>
		<td>
			<table>
				<tr>
					<td width="970"><span class="APPLICATION_H1">ZEWAR</span> <span class="APPLICATION_H2"> - Jeweller's Mate</span></td>
				</tr>
				<tr>
					<td height="50">&nbsp;</td>
				</tr>
				<tr>
					<td>
						<br>
						<table>
							<tr>
								<td width="10">&nbsp;</td>
								<td><tiles:insert attribute="body"/></td>
							</tr>
						</table>
					<br>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html:html>

