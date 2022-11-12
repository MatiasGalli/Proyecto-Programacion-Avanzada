package logica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Users {
	public void getUsers(SQL_Manager connection) {
		String sql;
		Statement st;
		ResultSet rs;
		sql = "Select rut,fullname from users order by rut";

		try {
			st = connection.getConnection().createStatement();
			rs = st.executeQuery(sql);
			displayUsers(rs);

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void displayUsers(ResultSet rs) throws SQLException {
		while (rs.next()) {
			System.out.println(rs.getString("rut") + "\t" + rs.getString("fullname"));
		}
	}

	public void insertUser(SQL_Manager connection, String rut, String fullname, String username, String address,
			String email, String password) throws SQLException {
		try {
			String sql = "insert into users(rut,fullname,username,address,email,admin,password) values (?,?,?,?,?,?,?)";
			PreparedStatement st = connection.getConnection().prepareStatement(sql);
			st.setString(1, rut);
			st.setString(2, fullname);
			st.setString(3, username);
			st.setString(4, address);
			st.setString(5, email);
			st.setBoolean(6, false);
			st.setString(7, password);
			st.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void deleteUser(SQL_Manager connection, String rut) throws SQLException {
		String sql = "delete from users where rut = ?";
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, rut);
		st.executeUpdate();
	}
	
	public void banUser(SQL_Manager connection, String rut) {
		String sql = "update users set banned = ? where rut = ?";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setBoolean(1, true);
			st.setString(2, rut);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
