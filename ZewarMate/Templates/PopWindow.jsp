<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<html:html>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="-1">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 	
	<title>Net Access Internal</title>
	<link rel="stylesheet" type="text/css" href="<html:rewrite page='/Styles/Application.css' />">
	<!-- import the calendar scripts and Style-->
	<script type="text/javascript" src="<html:rewrite page='/Scripts/Calendar.js' />"></script>
	<script type="text/javascript" src="<html:rewrite page='/Scripts/CalendarHelper.js' />"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<html:rewrite page='/Styles/Calendar.css' />" title="win2k">
	<script language="javascript">
		//window.history.forward(1);
	</script>
</head>
<body leftmargin="1" topmargin="10" marginwidth="1" marginheight="10">
<div><tiles:insert attribute="body"/></div>
</body>
</html:html>

