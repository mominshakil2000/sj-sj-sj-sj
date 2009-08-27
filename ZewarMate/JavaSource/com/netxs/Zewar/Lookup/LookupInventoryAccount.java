package com.netxs.Zewar.Lookup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Lookup.LookupDO;

public class LookupInventoryAccount {
	private static ArrayList list = new ArrayList();

	static {
		refreshCache();
	}

	public static Collection getList() {
		return list;
	}

	public static Collection getList(int level) {
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
			String query = "SELECT la.ledger_account_id	, la.ledger_account_type_id, la.parent_ledger_account_id, la.title, la.description, la.account_code_prefix, LPAD(la.account_code_postfix,6,'0') AS account_code_postfix, la.account_active, la.opening_balance, la.entry_debit_credit, la.account_description_level, la.account_create_date FROM ledger_accounts la  WHERE  IS_INVENTORY_ACCOUNT=1 ORDER BY account_code_prefix, account_code_postfix ";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				LookupDO obj = new LookupDO();
				obj.setId(rs.getInt("ledger_account_id"));
				obj.setLabel(rs.getString("account_code_prefix") + " "
						+ rs.getString("account_code_postfix") + " "
						+ rs.getString("title"));
				list.add(obj);
			}
			stmt.close();
			connection.close();

		} catch (Exception e) {
		}
	}
}