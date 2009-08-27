package com.netxs.Zewar.Struts.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netxs.Zewar.Default;
import com.netxs.Zewar.DataSources.DBConnection;

public class SalesOrderProcessCreateAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int salesOrderId, salesOrderItemId, salesOrderProcessTypeId, ledgerEntryId;
		Connection connection;
		connection = (Connection) new DBConnection().getMyPooledConnection();
		connection.setAutoCommit(false);

		try {
			salesOrderId = Integer.parseInt(request
					.getParameter("salesOrderId"));
			salesOrderItemId = Integer.parseInt(request
					.getParameter("salesOrderItemId"));
			salesOrderProcessTypeId = Integer.parseInt(request
					.getParameter("salesOrderProcessTypeId"));
		} catch (Exception e) {
			salesOrderId = 0;
			salesOrderItemId = 0;
			salesOrderProcessTypeId = 0;
		}

		if (salesOrderId == 0 || salesOrderProcessTypeId == 0
				|| salesOrderItemId == 0) {
			ActionMessages serviceErrors = new ActionMessages();
			serviceErrors.add("error", new ActionMessage("errors",
					"Unknow Sales Order or Order Item or Process Type."));
			saveErrors(request, serviceErrors);
			return (mapping.findForward("FAIL"));
		} else {
			try {

				Statement stmt = connection.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.TYPE_FORWARD_ONLY);
				ResultSet rs;

				float lastProcessReturnBodyWeight = 0, lastProcessReturnBodyMetalItemId = 0;
				rs = stmt
						.executeQuery("SELECT max(SALES_ORDER_PROCESS_ID), RETURN_BODY_WEIGHT, BODY_METAL_ITEM_ID FROM sales_order_processes WHERE SALES_ORDER_ITEM_ID="
								+ salesOrderItemId
								+ "  group by SALES_ORDER_ITEM_ID ");
				if (rs.next()) {
					lastProcessReturnBodyWeight = rs
							.getFloat("RETURN_BODY_WEIGHT");
					lastProcessReturnBodyMetalItemId = rs
							.getInt("BODY_METAL_ITEM_ID");
				}

				stmt.execute("INSERT INTO ledger_entries (AMOUNT ) VALUES (0.0)");
				rs = stmt.getGeneratedKeys();
				ledgerEntryId = (rs.next() ? rs.getInt(1) : 0);
				// Process Detail
				String query = "INSERT INTO sales_order_processes (SALES_ORDER_ID, SALES_ORDER_ITEM_ID, SALES_ORDER_PROCESS_TYPE_ID, LABOUR_LEDGER_ENTRY_ID, ISSUE_BODY_WEIGHT) "
						+ "VALUES( "
						+ salesOrderId
						+ ","
						+ salesOrderItemId
						+ ","
						+ salesOrderProcessTypeId
						+ ","
						+ ledgerEntryId
						+ "," + lastProcessReturnBodyWeight + ")";
				stmt.executeUpdate(query);

				stmt.executeUpdate("UPDATE sales_orders SET SALES_ORDER_STATUS_ID="
								+ Default.SALES_ORDER_STATUS_PROCESS
								+ "  WHERE SALES_ORDER_ID=" + salesOrderId);
				connection.commit();
				connection.close();
			} catch (Exception e) {
				try {
					connection.rollback();
				} catch (SQLException sqle) {
				}
				ActionMessages serviceErrors = new ActionMessages();
				serviceErrors.add("error", new ActionMessage("errors", e
						.getMessage()));
				saveErrors(request, serviceErrors);
				return (mapping.findForward("FAIL"));
			}

		}
		return (mapping.findForward("SUCCESS"));
	}
}