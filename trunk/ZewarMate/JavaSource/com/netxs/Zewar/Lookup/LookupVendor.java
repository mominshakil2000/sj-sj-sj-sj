package com.netxs.Zewar.Lookup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Lookup.LookupDO;

public class LookupVendor {
	private static ArrayList list = new ArrayList();

	static {
		LookupVendor.refreshCache();
	}

	public static Collection getListByType(String vendorTypes) {
		ArrayList list = new ArrayList();
		try {
			Connection connection = (Connection) new DBConnection()
					.getMyPooledConnection();
			Statement stmt = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.TYPE_FORWARD_ONLY);
			String query = "SELECT	LA.LEDGER_ACCOUNT_ID, LA.ACCOUNT_CODE_PREFIX, LA.ACCOUNT_CODE_POSTFIX, LA.TITLE FROM ledger_accounts AS LA INNER JOIN  vendors VN ON VN.LEDGER_ACCOUNT_ID=LA.LEDGER_ACCOUNT_ID  INNER JOIN vendor_types_relation tr ON VN.VENDOR_ID=TR.VENDOR_ID WHERE TR.VENDOR_TYPE_ID IN ("+ vendorTypes + ")";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				LookupDO obj = new LookupDO();
				obj.setId(rs.getInt("LEDGER_ACCOUNT_ID"));
				obj.setLabel(rs.getString("LA.ACCOUNT_CODE_PREFIX") + " "
						+ rs.getString("LA.ACCOUNT_CODE_POSTFIX") + " "
						+ rs.getString("TITLE"));
				list.add(obj);
			}
			stmt.close();
			connection.close();
		} catch (Exception e) {

		}
		return list;
	}

	public static Collection getList() {
		return list;
	}

	private static void refreshCache() {
		list.clear();
		try {
			Connection connection = (Connection) new DBConnection()
					.getMyPooledConnection();
			Statement stmt = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.TYPE_FORWARD_ONLY);
			String query = "SELECT vn.vendor_id, vn.name_title, vn.first_name, vn.last_name, vn.middle_name FROM vendors vn";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				LookupDO obj = new LookupDO();
				obj.setId(rs.getInt("vendor_id"));
				obj.setLabel(rs.getString("name_title") + " "
						+ rs.getString("first_name") + " "
						+ rs.getString("last_name"));
				list.add(obj);
			}
			stmt.close();
			connection.close();
		} catch (Exception e) {

		}
	}
}