package logica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cart {

	
	public void insertCart(SQL_Manager  connection, int id, String user_rut) throws SQLException {
		
		String sql = "insert into cart(id, user_rut) values(?,?)";
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, id);
		st.setString(2, user_rut);

		st.executeUpdate();				
	}
	
	public void deleteCart(SQL_Manager connection, int id) throws SQLException {
		
		String sql = "delete from cart where id = ?";
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1,id );
		st.executeUpdate();
		
	}
	
	
	
	
	public int countCartID(SQL_Manager connection) throws SQLException {

		String sql = "select id from cart order by id desc limit 1";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NING�N PRODUCTO
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		int id = rs.getInt("id");
		return id;
	}
	
	
	
	
}
