package logica;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Products {
	
	public void getProducts(SQL_Manager connection) {
		
		String sql;
		Statement st;
		ResultSet rs;
		sql = "Select * from productos";
		
		try {
			st = connection.getConnection().createStatement();
			rs = st.executeQuery(sql);
			displayProducts(rs);
					
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	public void displayProducts(ResultSet rs) throws SQLException {
		while(rs.next()) {
			System.out.println(rs.getString("id_producto")+ "\t"
					+ rs.getString("nombre_producto") + "\t"
					+ rs.getString("descripcion") + "\t"
					+ rs.getString ("precio") + "\t"
					+ rs.getString("stock") + "\t"
					+ rs.getString("id_categoria"));
			
		}
	}


	
	public void insertProduct(SQL_Manager connection, String id_producto, String nombre_producto, String descripcion, int precio, int stock, String id_categoria) throws SQLException {
		try {
			String sql;
		
			sql = "INSERT INTO producto (id_producto,nombre_producto,descripcion,precio,stock,id_categoria) values (?,?,?,?,?,?)";
		
			PreparedStatement st = connection.getConnection().prepareStatement(sql);
		
			st.setString(1,id_producto);
			st.setString(2, nombre_producto);
			st.setString(3, descripcion);
			st.setInt(4, precio);
			st.setInt(4, stock);
			st.setString(6, id_categoria);
		
			st.executeUpdate();
		}catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
	}

}
