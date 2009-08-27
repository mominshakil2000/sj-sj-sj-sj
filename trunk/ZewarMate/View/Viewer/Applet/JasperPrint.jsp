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
//	response.addHeader("Content-Disposition","attachment; filename=StockIssueMetal.pdf");
	JasperPrint jasperPrint = null;
	Object object = session.getAttribute("JASPER_REPORT");
	System.out.println("Jasper-Print:  is null "+(object==null));
	if(object instanceof net.sf.jasperreports.engine.JasperPrint) {
//		System.out.println("Jasper-Print:  is instanceof "+true);

		jasperPrint = (net.sf.jasperreports.engine.JasperPrint) object;
//	    response.getWriter().print(jasperPrint);	    
		ServletOutputStream ouputStream = response.getOutputStream();
		
		ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
		oos.writeObject(jasperPrint);
		oos.flush();
		oos.close();

		ouputStream.flush();
		ouputStream.close();
		// JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	} else {

//		System.out.println("Jasper-Print:  is instanceof "+false);
	}
	
	
%>


