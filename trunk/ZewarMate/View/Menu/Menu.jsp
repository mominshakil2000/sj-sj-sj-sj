<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<SCRIPT language=JavaScript src="<html:rewrite page='/Scripts/JSCookMenu.js'/>" type=text/javascript></SCRIPT>
<SCRIPT language=JavaScript src="<html:rewrite page='/Scripts/JSCookTheme.js'/>" type=text/javascript></SCRIPT>
<LINK href="<html:rewrite page='/Styles/JSCookMenuStyle.css'/>" type=text/css rel=stylesheet>
<SCRIPT language=JavaScript src="<html:rewrite page='/Scripts/JSCookMenuArray.js'/>" type=text/javascript></SCRIPT>
<DIV ID=myMenuID></DIV>
<SCRIPT LANGUAGE="JavaScript">
<!--
	cmDraw ('myMenuID', myMenu, 'hbr', cmThemeOffice, 'ThemeOffice', '150');
-->
</SCRIPT>