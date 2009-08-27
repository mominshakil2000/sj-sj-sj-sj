package com.netxs.Zewar.Lookup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Lookup.LookupDO;

public class LookupSalesOrderProcessStatus {

	private static ArrayList list = new ArrayList();

	static {
		refreshCache();
	}

	public static Collection getList() {
		return list;
	}

	public static void refreshCache() {
		list.clear();
		try {
			Connection connection = (Connection) new DBConnection()
					.getMyPooledConnection();
			Statement stmt = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.TYPE_FORWARD_ONLY);
			String query = "SELECT * FROM sales_order_process_status ";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				LookupDO obj = new LookupDO();
				obj.setId(rs.getInt("sales_order_process_status_id"));
				obj.setLabel(rs.getString("sales_order_process_status"));
				list.add(obj);
			}
			stmt.close();
			connection.close();
		} catch (Exception e) {
		}
	}
}