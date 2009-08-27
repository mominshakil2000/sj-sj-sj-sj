package com.netxs.Zewar.Lookup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Lookup.LookupDO;

public class LookupCustomer {
	private static ArrayList list = new ArrayList();

	static {
		LookupCustomer.refreshCache();
	}

	public static Collection getList() {
		return list;
	}

	public static void refreshCache() {
		try {
			list.clear();
			Connection connection = (Connection) new DBConnection()
					.getMyPooledConnection();
			Statement stmt = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.TYPE_FORWARD_ONLY);
			String query = "SELECT CS.CUSTOMER_ID, CONCAT(LA.ACCOUNT_CODE_PREFIX, ' ', LA.ACCOUNT_CODE_POSTFIX, ' ', LA.TITLE) AS LEDGER_ACCOUNT_TITLE, LA.LEDGER_ACCOUNT_ID FROM customers  CS INNER JOIN ledger_accounts LA ON LA.LEDGER_ACCOUNT_ID=CS.LEDGER_ACCOUNT_ID ORDER BY name_title, first_name, middle_name, last_name";
			ResultSet rs = stmt.executeQuery(query);

			LookupDO obj;

			while (rs.next()) {
				obj = new LookupDO();
				obj.setId(rs.getInt("LEDGER_ACCOUNT_ID"));
				obj.setLabel(rs.getString("LEDGER_ACCOUNT_TITLE"));
				list.add(obj);
			}

			stmt.close();
			connection.close();

		} catch (Exception e) {}
	}
}