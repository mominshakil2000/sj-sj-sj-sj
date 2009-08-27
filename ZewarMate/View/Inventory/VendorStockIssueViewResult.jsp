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
//out.println(com.netxs.Zewar.CommonUtil.arrayToString(request.getParameterValues("accountCode"),","));

//	response.setContentType( "application/pdf" );
//	response.addHeader("Content-Disposition","attachment; filename=StockIssueMetal.pdf");
	JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/JasperReport/StockIssueMetal.jrxml"));
	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	JasperPrint jasperPrint;

	Connection connection;
	connection = (Connection) new DBConnection().getMyPooledConnection();

	Map parameterMap = new HashMap();
	parameterMap.put("CRITERIA_DATE_FROM", request.getParameter("dateFrom")); 
	parameterMap.put("CRITERIA_DATE_TO", request.getParameter("dateTo")); 
	parameterMap.put("CRITERIA_ACCOUNT", com.netxs.Zewar.CommonUtil.arrayToString(request.getParameterValues("accountCode"),","));     
	parameterMap.put("METAL_ITEM_ID", request.getParameter("metalItemId")); 

    jasperPrint = JasperFillManager.fillReport(jasperReport, parameterMap, connection);
//    JasperExportManager.exportReportToPdfFile(jasperPrint, application.getRealPath("/Report/StockIssueMetal.pdf"));
    JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//	session.setAttribute("JASPER_REPORT", jasperPrint);
//	response.sendRedirect("../Viewer/JasperAppletViewer.jsp");
//	response.sendRedirect("../Viewer/JasperPrint.jsp");
%>

