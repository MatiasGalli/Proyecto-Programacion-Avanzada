package logica;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class cart {

	
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
	
	
	
	
	
	
	
	
	
	
}
