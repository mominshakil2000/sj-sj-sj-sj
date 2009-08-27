<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<html:html>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="-1">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 	
	<title>ZEWAR - Jeweller's Mate</title>


	<!-- Calendar Common JS files -->
	<script type='text/javascript' src='<html:rewrite page='/Scripts/Calendar/utils/zapatec.js' />'></script>
	<script type="text/javascript" src="<html:rewrite page='/Scripts/Calendar/src/calendar.js' />"></script>
	<script type="text/javascript" src="<html:rewrite page='/Scripts/Calendar/lang/calendar-en.js' />"></script>
	<link href="<html:rewrite page='/Scripts/Calendar/website/css/zpcal.css' />" rel="stylesheet" type="text/css">
	<link href="<html:rewrite page='/Scripts/Calendar/website/css/template.css' />" rel="stylesheet" type="text/css">
	<link href="<html:rewrite page='/Scripts/Calendar/themes/aqua.css' />" rel="stylesheet" type="text/css">


	<link rel="stylesheet" type="text/css" href="<html:rewrite page='/Styles/Application.css' />">
	<!-- import the calendar scripts and Style
	<script type="text/javascript" src="<html:rewrite page='/Scripts/Calendar.js' />"></script>
	<script type="text/javascript" src="<html:rewrite page='/Scripts/CalendarHelper.js' />"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<html:rewrite page='/Styles/Calendar.css' />" title="win2k">
	-->
	<script type="text/javascript" src="<html:rewrite page='/Scripts/Common.js' />"></script>
	<script type="text/javascript" src="<html:rewrite page='/Scripts/Lookup.js' />"></script>

	<script language="javascript">
//		window.history.forward(1);
	</script>
</head>
<body leftmargin="5" topmargin="5">
<table width="100%" cellspacing="0" cellpadding="0" align="left" border="0" >
	<tr>
		<td width="100%">
			<div><span class="APPLICATION_H1">ZEWAR</span> <span class="APPLICATION_H2"> - Jeweller's Mate</span></div>
			<div><br><tiles:insert attribute="menu"/><br></div>			
			<div><tiles:insert attribute="body"/></div>
		</td>
	</tr>
</table>
</body>
</html:html>

