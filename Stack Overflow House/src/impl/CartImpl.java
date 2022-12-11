package impl;

import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import interfaces.Cart;
import logic.SQL_Manager;

public class CartImpl implements Cart {

	@Override
	public int countCartID(SQL_Manager connection) {
		String sql = "select id from cart order by id desc limit 1";
		Statement st;
		int id = 0;
		try {
			st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			id = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void insertCart(SQL_Manager connection, int id, String user_rut) {
		String sql = "insert into cart(id, user_rut) values(?,?)";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);

			st.setInt(1, id);
			st.setString(2, user_rut);

			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean existProductInCart(SQL_Manager connection, int cart_id) {
		boolean exist = false;
		String sql = "select product_id from product_cart where cart_id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, cart_id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

	@Override
	public void addProductCart(SQL_Manager connection, int cart_id, int product_id, int amount) {
		String sql = "update product_cart set amount = amount + ? where cart_id = ? and product_id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, amount);
			st.setInt(2, cart_id);
			st.setInt(3, product_id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean existProductCart(SQL_Manager connection, int cart_id, int product_id) {
		boolean exist = false;
		String sql = "select product_id from product_cart where product_id = ? and cart_id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, product_id);
			st.setInt(2, cart_id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

	@Override
	public void addProductInCart(SQL_Manager connection, int cart_id, int product_id, int amount) {
		String sql = "Insert into product_cart(cart_id, product_id, amount) values (?,?,?)";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, cart_id);
			st.setInt(2, product_id);
			st.setInt(3, amount);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int selectCartId(SQL_Manager connection, String username) {
		int cart_id = -1;
		String sql = "select id from cart where cart.user_rut = (select rut from users where username = ?)";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			rs.next();
			cart_id = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cart_id;
	}
	
	@Override
	public void deleteCart(SQL_Manager connection, int cart_id) {

		String sql = "delete from product_cart where cart_id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, cart_id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int selectStockInCart(SQL_Manager connection, int cart_id, int product_id) {
		int cant = -1;
		String sql = "select amount from product_cart where cart_id = ? and product_id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, cart_id);
			st.setInt(2, product_id);
			ResultSet rs = st.executeQuery();
			rs.next();
			cant = rs.getInt("amount");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cant;
	}
	
	@Override
	public void deleteProductInCart(SQL_Manager connection, int cart_id, int product_id) {

		String sql = "delete from product_cart where cart_id = ? and product_id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, cart_id);
			st.setInt(2, product_id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("serial")
	@Override
	public JTable updateTableCart(SQL_Manager connection, String user_rut) {
		try {
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "select p.id, name, price , amount, (p.price * pc.amount) as total from product_cart pc inner join product p on p.id = pc.product_id inner join cart c on c.id = pc.cart_id inner join users u on u.rut = c.user_rut where u.rut = ? order by p.name asc";
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, user_rut);
			rs = st.executeQuery();
			String titles[] = { "ID", "Nombre del producto", "Precio", "Cantidad", "Total" };
			DefaultTableModel model = new DefaultTableModel(null, titles) {
				boolean[] columnEditables = new boolean[] { false, false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			String row[] = new String[5];
			while (rs.next()) {
				row[0] = Integer.toString(rs.getInt("id"));
				row[1] = rs.getString("name");
				row[2] = "$" + rs.getString("price");
				row[3] = rs.getString("amount");
				row[4] = "$" + rs.getString("total");
				model.addRow(row);
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(140);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(90);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setPreferredWidth(120);
			table.setRowHeight(26);
			table.setBounds(100, 160, 780, 350);
			return (table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}
	
	@Override
	public int countProductsInCart(SQL_Manager connection,String user_rut) {
		PreparedStatement st;
		ResultSet rs;
		int cant = 0;
		String sql = "select p.id, name, price , amount, (p.price * pc.amount) as total from product_cart pc inner join product p on p.id = pc.product_id inner join cart c on c.id = pc.cart_id inner join users u on u.rut = c.user_rut where u.rut = ? order by p.name asc";
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, user_rut);
			rs = st.executeQuery();
			while (rs.next()) {
				cant++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cant;
	}
	
	@Override
	public JScrollPane showCart(JTable table) {
		try {
			JScrollPane scrollPane_cart = new JScrollPane(table);
			scrollPane_cart.setLocation(100, 160);
			scrollPane_cart.setSize(780, 350);
			scrollPane_cart.setViewportView(table);
			return scrollPane_cart;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}
}
