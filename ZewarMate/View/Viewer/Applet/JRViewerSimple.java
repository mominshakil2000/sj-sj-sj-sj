import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;

import java.awt.BorderLayout;
import javax.swing.*;


/**
* @author Teodor Danciu (teodord@users.sourceforge.net)
* @version $Id: JRViewerSimple.java,v 1.7 2005/06/27 07:14:40 teodord Exp $
*/

public class JRViewerSimple extends JRViewer 
{
	public JRViewerSimple(JasperPrint jrPrint) throws JRException {
		super(jrPrint);
		tlbToolBar.remove(btnSave);
		tlbToolBar.remove(btnReload);
	}
}
