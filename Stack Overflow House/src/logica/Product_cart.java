package logica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		
	public void getPCart(SQL_Manager connection) throws SQLException {
		
		String sql = "select p.name, p.price , pc.amount, (p.price * pc.amount) as totalfrom product_cart pc inner join product p on p.id = pc.product_id";
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		displayPCart(rs);
		
	}
	public void displayPCart(ResultSet rs) throws SQLException {
		while(rs.next()) {
			System.out.println(rs.getString("name") + "\t" + rs.getFloat("price") + "\t" + rs.getInt("amount") + "\t" + rs.getFloat("total"));
			
		}
	}
	
	
}
