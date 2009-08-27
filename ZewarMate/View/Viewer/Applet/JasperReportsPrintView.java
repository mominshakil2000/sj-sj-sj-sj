import java.io.ObjectOutputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView;

public class JasperReportsPrintView extends
		AbstractJasperReportsSingleFormatView {

	/**
	 * Default Constructor
	 */
	public JasperReportsPrintView() {
		setContentType("application/octet-stream");
	}

	/**
	 * Perform rendering for a single Jasper Reports exporter,
	 * i.e. a pre-defined output format.
	 */
	protected void renderReport(
			JasperReport report, Map parameters, JRDataSource dataSource, HttpServletResponse response)
			throws Exception {

		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);

		ServletOutputStream ouputStream = response.getOutputStream();
			
		ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
		oos.writeObject(jasperPrint);
		oos.flush();
		oos.close();

		ouputStream.flush();
		ouputStream.close();
	}

	/**
	 * @see org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView#createExporter()
	 */
	protected JRAbstractExporter createExporter() {
		return null;
	}

	/**
	 * @see org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView#useWriter()
	 */
	protected boolean useWriter() {
		return false;
	}

}