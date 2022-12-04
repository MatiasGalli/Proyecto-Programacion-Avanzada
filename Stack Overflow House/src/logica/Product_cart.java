package logica;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Product_cart {

	public void addProduct(SQL_Manager connection, int cart_id, String product_id, int amount) throws SQLException {
		
		String sql = "Insert into product_cart(cart_id, product_id, amount) values (?,?,?)";
		
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, cart_id);
		st.setString(2, product_id);
		st.setInt(3, amount);
		st.executeUpdate();
		
	}
	public void deleteProduct(SQL_Manager connection, int cart_id, String product_id) throws SQLException {
		
		String sql = "delete from product_cart where cart_id = ? AND product_id = ?";
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, cart_id);
		st.setString(2, product_id);
		st.executeUpdate();
		
		
	}
}
