package logica;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Categories {
	
	
	public void getCategories(SQL_Manager connection) throws SQLException {
		
		String sql = "select name from category";
		
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		displayCategories(rs);
		
		
		
	}
	public void displayCategories(ResultSet rs) throws SQLException {
		while(rs.next()) {
			System.out.println(rs.getString("name"));
		}
	}
	
	
	public void insertCategories(SQL_Manager connection, String id, String name) throws SQLException {
		
		String sql = "Insert into category(id,name) values(?,?)";
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, id);
		st.setString(2, name);
		st.executeUpdate();
		
	}

}
