package com.netxs.Zewar.Lookup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.netxs.Zewar.DataSources.DBConnection;
import com.netxs.Zewar.Lookup.LookupDO;

public class LookupCountry {
	private static ArrayList list = new ArrayList();

	static {
		LookupCountry.refreshCache();
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
			String query = "SELECT * FROM countries ORDER BY country_name";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {

				LookupDO countryDTO = new LookupDO();

				countryDTO.setId(rs.getInt("country_id"));
				countryDTO.setLabel(rs.getString("country_name"));
				list.add(countryDTO);
			}
			stmt.close();
			connection.close();
		} catch (Exception e) {

		}
	}
}