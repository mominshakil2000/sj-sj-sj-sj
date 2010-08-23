<%@page import="java.io.*
			   ,java.sql.*
			   ,java.util.*
			   ,java.math.* 
			   ,java.text.*
			   ,java.net.*" %>

<%@ page import="net.sf.jasperreports.engine.*
				,net.sf.jasperreports.engine.fill.*
				,net.sf.jasperreports.engine.xml.*
				,net.sf.jasperreports.engine.design.*
				,net.sf.jasperreports.engine.data.*
				,net.sf.jasperreports.engine.xml.JRXmlLoader
				,net.sf.jasperreports.engine.JasperCompileManager
				,net.sf.jasperreports.engine.JasperFillManager
				,net.sf.jasperreports.engine.JasperExportManager
				,net.sf.jasperreports.engine.JasperPrint
				,net.sf.jasperreports.engine.JasperReport
				,net.sf.jasperreports.engine.design.JasperDesign
				,net.sf.jasperreports.view.JasperViewer" %> 


<%@ page import="com.netxs.Zewar.DataSources.DBConnection" %>

<%
//	response.setContentType( "application/pdf" );
	response.addHeader("Content-Disposition","attachment; filename=VendorStatement.pdf");
	JasperDesign jasperDesign = null;
	if(request.getParameter("itemId")!=null && request.getParameter("itemId").equals("0")) {
		jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/JasperReport/VendorStatementMasterAllMetal.jrxml"));
	} else {
		jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/JasperReport/VendorStatementMaster.jrxml"));
	}
	
	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	JasperPrint jasperPrint;

	Connection connection;
	connection = (Connection) new DBConnection().getMyPooledConnection();

	Map parameterMap = new HashMap();
	parameterMap.put("CRITERIA_DATE_FROM", request.getParameter("dateFrom")); 
	parameterMap.put("CRITERIA_DATE_TO", request.getParameter("dateTo")); 
	parameterMap.put("VENDOR_LEDGER_ACCOUNT_ID", request.getParameter("accountCode")); 
	parameterMap.put("METAL_ITEM_ID", request.getParameter("itemId")); 
    
    jasperPrint = JasperFillManager.fillReport(jasperReport, parameterMap, connection);
//    JasperExportManager.exportReportToPdfFile(jasperPrint, application.getRealPath("/Report/VendorStatement.pdf"));
    JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
%>