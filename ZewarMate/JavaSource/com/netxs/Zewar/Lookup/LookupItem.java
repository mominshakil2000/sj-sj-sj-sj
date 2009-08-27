package com.netxs.Zewar.Lookup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Lookup.LookupDO;

public class LookupItem {
	private static Object[] list = new Object[3];

	static {
		LookupItem.refreshCache();
	}

	public static Collection getList(int itemGroup) {
		return (ArrayList) LookupItem.list[itemGroup - 1];
	}

	public static Collection getList(String itemGroup) {
		return (ArrayList) LookupItem.list[Integer.parseInt(itemGroup) - 1];

	}

	public static void refreshCache() {
		
		try {
			
			Connection connection = (Connection) new DBConnection()
					.getMyPooledConnection();
			Statement stmt = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.TYPE_FORWARD_ONLY);
			String query = "SELECT * FROM items WHERE item_group_id BETWEEN 1 and 3 ORDER BY item_group_id, item_name";
			ResultSet rs = stmt.executeQuery(query);

			int itemGroupId = -1;
			int index = -1;
			LookupDO obj;
			while (rs.next()) {
				if (rs.getInt("item_group_id") != itemGroupId) {
					itemGroupId = rs.getInt("item_group_id");
					index += 1;
					if (((ArrayList) list[index]) != null)
						((ArrayList) list[index]).clear(); 
					else 
						list[index] = new ArrayList();
				}
				obj = new LookupDO();
				obj.setId(rs.getInt("item_id"));
				obj.setLabel(rs.getString("item_name"));
				((ArrayList) list[index]).add(obj);
			}
			stmt.close();
			connection.close();
		} catch (Exception e) {

		}
	}

}