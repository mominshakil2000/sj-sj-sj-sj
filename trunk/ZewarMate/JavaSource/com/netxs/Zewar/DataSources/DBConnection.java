package com.netxs.Zewar.DataSources;

import javax.naming.*;
import javax.sql.*;
import java.sql.*;


public class DBConnection {

	public DBConnection() {
	}
	
	public Connection getMyPooledConnection() throws  Exception {
		DataSource dataSource;
		Connection connection = null;
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			System.out.println("Searching Datsource: ");
			// get JNDI Data Source from Context
			Context initContext = new InitialContext();
			Context ctx = (Context)initContext.lookup("java:/comp/env");
			System.out.println("\n\t Context : "+ctx.toString());
			dataSource = (DataSource)ctx.lookup("jdbc/ZewarShaikhaJeweller");
			System.out.println("\n\tDatasource : "+(dataSource!=null));
			// get Connection
			connection = dataSource.getConnection();
			System.out.println("\n\t Connected :"+(connection!=null)); 
		}catch(Exception e){
			System.out.println("\n\tPooling Connection .. Fail" + e.getMessage());
			throw e;
		}
		return connection;
	}

	public void closeMyPooledConnection(PooledConnection connection){
		try{
			connection.close();
		}catch(Exception e){
			System.out.println("Closing Pooled Connection .. Fail" + e.getMessage());
		}
	}
}

