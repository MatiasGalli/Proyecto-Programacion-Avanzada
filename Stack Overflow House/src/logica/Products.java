package logica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Products {

	public void getProducts(SQL_Manager connection) {

		String sql;
		Statement st;
		ResultSet rs;
		sql = "Select * from product";

		try {
			st = connection.getConnection().createStatement();
			rs = st.executeQuery(sql);
			displayProducts(rs);

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}

	public void displayProducts(ResultSet rs) throws SQLException {
		while (rs.next()) {
			System.out.println(rs.getString("id") + "\t" + rs.getString("name") + "\t" + rs.getString("description")
					+ "\t" + rs.getString("price") + "\t" + rs.getString("stock") + "\t" + rs.getString("category_id"));

		}
	}

	public void insertProduct(SQL_Manager connection, String id, String name, String description, float price, int stock,
			String category_id) throws SQLException {
		try {
			String sql;

			sql = "INSERT INTO product (id,name,description,price,stock,category_id) values (?,?,?,?,?,?)";

			PreparedStatement st = connection.getConnection().prepareStatement(sql);

			st.setString(1, id);
			st.setString(2, name);
			st.setString(3, description);
			st.setFloat(4, price);
			st.setInt(4, stock);
			st.setString(6, category_id);

			st.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}
	
	public String countProducts(SQL_Manager connection) throws SQLException {
		
		String sql = "select id from product order by id desc limit 1";
		//FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		String id = rs.getString("id");
		return id;
	}

}
