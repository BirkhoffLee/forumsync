package me.sisko.phpbb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ForumSQL {
	private Connection conn;

	public ForumSQL(String adress, String port, String database, String user, String pass) {
		String url = "jdbc:mysql://" + adress + ":" + port + "/" + database + "?verifyServerCertificate=false&useSSL=true";
		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String[] querySelect(String query, String column) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		ResultSet rs = conn.createStatement().executeQuery(query);
		while(rs.next()) values.add(rs.getString(column));
		return (String[]) values.toArray(new String[values.size()]);
	}

	public void queryUpdate(String query) throws SQLException {
		conn.createStatement().executeUpdate(query);
	}
}