package impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import interfaces.Product;
import logic.SQL_Manager;

import java.awt.Font;
import java.sql.PreparedStatement;

public class ProductImpl implements Product {

	@Override
	public boolean existProduct(SQL_Manager connection) {
		boolean exist = false;
		String sql = "select * from product";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
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
	public void substractStockMenu(SQL_Manager connection, int amount, int product_id) {
		String sql = "update product set stock = stock - ? where id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, amount);
			st.setInt(2, product_id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String seeDescription(SQL_Manager connection, int product_id) {
		String sql = "select description from product where id = ?";
		PreparedStatement st;
		String description = "";
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, product_id);
			ResultSet rs = st.executeQuery();
			rs.next();
			description = rs.getString("description");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return description;
	}

	@SuppressWarnings("serial")
	@Override
	public JTable updateTableProductOrder(SQL_Manager connection, String category, String asc) {
		try {
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "Select p.id, p.name, p.price, p.stock, c.name as category_name from product p inner join category c on c.id = p.category_id where p.stock <> 0 order by " + category + " " + asc;
			st = connection.getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			String titles[] = { "ID", "Nombre del producto", "Precio", "Stock", "Categor\u00EDa" };
			DefaultTableModel model = new DefaultTableModel(null, titles) {
				boolean[] columnEditables = new boolean[] { false, false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			String row[] = new String[5];
			while (rs.next()) {
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = "$ " + rs.getString("price");
				row[3] = rs.getString("stock");
				row[4] = rs.getString("category_name");
				model.addRow(row);

			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(220);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setPreferredWidth(220);
			table.setRowHeight(26);
			table.setBounds(37, 200, 880, 204);
			return (table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	@SuppressWarnings("serial")
	@Override
	public JTable searchProduct(SQL_Manager connection, String category, String search) {
		int product_id = 0;
		if (category.equals("category_id")) {
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "Select id from category where name = ?;";
			try {
				st = connection.getConnection().prepareStatement(sql);
				st.setString(1, search);
				rs = st.executeQuery();
				while (rs.next()) {
					product_id = rs.getInt("id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		JTable table = new JTable();
		table.setShowVerticalLines(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setBorder(null);
		String sql;
		boolean stBoolean = false;
		PreparedStatement st;
		ResultSet rs = null;
		int cont = 0;
		sql = "Select p.id, p.name, p.price, p.stock, c.name as category_name from product p inner join category c on c.id = p.category_id where p." + category + " = ? and stock <> 0;";
		try {
			st = connection.getConnection().prepareStatement(sql);
			if (product_id == 0) {
				st.setString(1, search);
			} else {
				st.setInt(1, product_id);
			}
			try {
				rs = st.executeQuery();
			} catch (Exception e) {
				cont = 0;
				stBoolean = true;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String titles[] = { "ID", "Nombre del producto", "Precio", "Stock", "CategorÃ­a" };
		DefaultTableModel model = new DefaultTableModel(null, titles) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		String row[] = new String[6];
		if (!stBoolean) {
			try {
				while (rs.next()) {
					row[0] = rs.getString("id");
					row[1] = rs.getString("name");
					row[2] = "$ " + rs.getString("price");
					row[3] = rs.getString("stock");
					row[4] = rs.getString("category_name");
					model.addRow(row);
					cont++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (cont == 0) {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No hay coincidencias en la b\u00FAsqueda");
			table = updateTableProductOrder(connection,"name","asc");
		} else {
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(220);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setPreferredWidth(220);
			table.setRowHeight(26);
			table.setBounds(37, 200, 880, 204);
		}
		return table;
	}

	@SuppressWarnings("serial")
	@Override
	public JTable updateTableProduct(SQL_Manager connection) {
		try {
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "Select p.id, p.name, p.price, p.stock, c.name as category_name from product p inner join category c on c.id = p.category_id where stock <> 0 order by name asc";
			st = connection.getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			String titles[] = { "ID", "Nombre del producto", "Precio", "Stock", "Categor\u00EDa" };
			DefaultTableModel model = new DefaultTableModel(null, titles) {
				boolean[] columnEditables = new boolean[] { false, false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			String row[] = new String[6];
			while (rs.next()) {
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = "$ " + rs.getString("price");
				row[3] = rs.getString("stock");
				row[4] = rs.getString("category_name");
				model.addRow(row);

			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(220);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setPreferredWidth(220);
			table.setRowHeight(26);
			table.setBounds(37, 200, 880, 204);
			return (table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	@Override
	public JScrollPane showProducts(JTable table) {
		try {
			JScrollPane scrollPane_userInfo = new JScrollPane(table);
			scrollPane_userInfo.setLocation(37, 200);
			scrollPane_userInfo.setSize(880, 204);
			scrollPane_userInfo.setViewportView(table);
			return scrollPane_userInfo;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}
	
	@Override
	public int countProducts(SQL_Manager connection, String user_rut) {

		String sql = "select count(*) as id from product_cart pc inner join cart c on c.id = pc.cart_id inner join users u on u.rut = c.user_rut where u.rut = ?";

		PreparedStatement st;
		int id = 0;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1,user_rut);
			ResultSet rs = st.executeQuery();
			rs.next();
			id = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public int selectRemainingStock(SQL_Manager connection,int product_id){
		int cant = -1;
		String sql = "select stock from product where id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, product_id);
			ResultSet rs = st.executeQuery();
			rs.next();
			cant = rs.getInt("stock");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cant;
	}
	
	@Override
	public void substractStock(SQL_Manager connection, int amount, int product_id) {

		String sql = "update product set stock = stock - ? where id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, amount);
			st.setInt(2, product_id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addStock(SQL_Manager connection, int amount, int product_id) {

		String sql = "update product set stock = stock + ? where id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, amount);
			st.setInt(2, product_id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void substractProductStock(SQL_Manager connection, int cart_id, int product_id, int amount) {

		String sql = "update product_cart set amount = amount - ? where cart_id = ? and product_id = ?";

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
	public int productPrice(SQL_Manager connection, String user_rut, int product_id) {
		int total = 0;
		try {
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "select (p.price * pc.amount) as total from product_cart pc inner join product p on p.id = pc.product_id inner join cart c on c.id = pc.cart_id inner join users u on u.rut = c.user_rut  where u.rut = ? and p.id = ?";
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, user_rut);
			st.setInt(2, product_id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				total = Integer.parseInt(rs.getString("total"));
			}
			return (total);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return 0;
	}
	
	@Override
	public int totalPrice(SQL_Manager connection, String user_rut) {
		int total = 0;
		try {
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "select (p.price * pc.amount) as total from product_cart pc inner join product p on p.id = pc.product_id inner join cart c on c.id = pc.cart_id inner join users u on u.rut = c.user_rut  where u.rut = ?";
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, user_rut);
			rs = st.executeQuery();
			while (rs.next()) {
				total += Integer.parseInt(rs.getString("total"));
			}
			return (total);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return 0;
	}
	
	@Override
	public String selectProductName (SQL_Manager connection, int product_id){
		String sql;
		PreparedStatement st;
		ResultSet rs;
		String productName = "";
		sql = "select name from product where id = ?";
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, product_id);
			rs = st.executeQuery();
			rs.next();
			productName = rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productName;
	}
	
	@Override
	public int selectProductID(SQL_Manager connection, String product_name){

		String sql = "select id from product where name = ?";
		int id = 0;
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, product_name);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public void deleteProduct(SQL_Manager connection, int id) {
		String sql = "delete from commentary where product_id = ?";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "delete from product_cart where product_id = ?";
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "delete from product where id = ?";
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jFrame,"Producto eliminado");
		
	}
	
	@SuppressWarnings("serial")
	@Override
	public JTable updateTableProductDelete(SQL_Manager connection){
		try{
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "Select p.id, p.name, p.price, p.stock, c.name as category_name from product p inner join category c on c.id = p.category_id order by p.id asc";
			st = connection.getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			String titles[]={"ID", "Nombre del producto","Precio", "Stock", "Categoría"};
			DefaultTableModel model = new DefaultTableModel(null,titles) {
				boolean[] columnEditables = new boolean[] {
						false, false, false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				};
			String row[]=new String[6];
			while(rs.next()){
				row[0]=rs.getString("id");
				row[1]=rs.getString("name");
				row[2]= "$ " + rs.getString("price");
				row[3]=rs.getString("stock");
				row[4]= rs.getString("category_name");
				model.addRow(row);
				
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(220);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setPreferredWidth(220);
			table.setRowHeight(26);
			table.setBounds(60, 70, 820, 330);
			return (table);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		return null;
	}
	
	@Override
	public JScrollPane showProductsDelete (JTable table){
		try{
			JScrollPane scrollPane_userInfo = new JScrollPane(table);
			scrollPane_userInfo.setLocation(60, 70);
			scrollPane_userInfo.setSize(820, 330);
			scrollPane_userInfo.setViewportView(table);
			return scrollPane_userInfo;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		return null;
	}
	
	@Override
	public void editProduct(String rut, SQL_Manager connection, String product_name, String name, float price,
			int stock, String description) {
		String sql = "select id from product where name = ?";
		PreparedStatement st;
		ResultSet rs;
		int product_id = 0;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, product_name);
			rs = st.executeQuery();
			rs.next();
			product_id = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = "update product set name = ?, price = ?, stock = ?, description = ? where id = ?";

		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, name);
			st.setFloat(2, price);
			st.setInt(3, stock);
			st.setString(4, description);
			st.setInt(5, product_id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jFrame, "Producto actualizado.");
	}
	
	@Override
	public String[] getProducts(SQL_Manager connection, String[] list) {

		String[] values = list;
		String sql = "select name from product order by name asc";
		Statement st;
		try {
			st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			int cant = 0;
			while (rs.next()) {
				values[cant] = rs.getString("name");
				cant++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return values;
	}
	
	@Override
	public int countProductsEdit(SQL_Manager connection) {

		String sql = "select count(*) as cant from product";
		int cant = 0;
		Statement st;
		try {
			st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			cant = rs.getInt("cant");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cant;
	}
	
	@Override
	public boolean existProductWithName(SQL_Manager connection, String newName) {
		boolean exist = false;
		String sql = "select name from product";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (rs.getString("name").equals(newName)) {
					exist = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	@Override
	public int countProductsID(SQL_Manager connection) {

		String sql = "select id from product order by id desc limit 1";
		int id = 0;
		Statement st;
		try {
			st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public void insertProduct(SQL_Manager connection, int id, String name, String description, float price,
			int stock, int category_id) {
		try {
			String sql;

			sql = "INSERT INTO product (id,name,description,price,stock,category_id) values (?,?,?,?,?,?)";

			PreparedStatement st = connection.getConnection().prepareStatement(sql);

			st.setInt(1, id);
			st.setString(2, name);
			st.setString(3, description);
			st.setFloat(4, price);
			st.setInt(5, stock);
			st.setInt(6, category_id);

			st.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}
}
