package impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import interfaces.Purchase;
import logic.SQL_Manager;

import java.awt.Font;
import java.sql.PreparedStatement;

public class PurchaseImpl implements Purchase {

	@Override
	public int countHistoryID(SQL_Manager connection) {
		String sql = "select history_id from shopping_history order by history_id desc limit 1";
		Statement st;
		int id = 0;
		try {
			st = connection.getConnection().createStatement();

			ResultSet rs = st.executeQuery(sql);
			rs.next();
			id = rs.getInt("history_id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void insertHistoryID(SQL_Manager connection, int history_id, String user_rut) {
		String sql = "insert into shopping_history(history_id,user_rut) values(?,?)";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, history_id);
			st.setString(2, user_rut);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean purchaseHistoryExists(SQL_Manager connection, int history_id) {
		boolean exist = false;
		String sql = "select * from history_product where history_id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, history_id);
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
	public int selectHistoryId(SQL_Manager connection, String username){
		int cart_id = -1;
		String sql = "select history_id from shopping_history where shopping_history.user_rut = (select rut from users where username = ?)";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			rs.next();
			cart_id = rs.getInt("history_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cart_id;
	}
	
	@Override
	public int selectHistoryIDRut(SQL_Manager connection, String username){
		String user_rut = "";
		String sql = "select rut from users where username = ?";
		int id = -1;
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			rs.next();
			user_rut = rs.getString("rut");
			
			sql = "select history_id from shopping_history where user_rut = ?";
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, user_rut);
			rs = st.executeQuery();
			rs.next();
			id = rs.getInt("history_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public void insertHistoryProduct(SQL_Manager connection, int id, int history_id, String product_name, int amount){

		String sql = "Insert into history_product(id, history_id, product_name, amount, purchase_date) values (?,?,?,?,current_timestamp)";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, id);
			st.setInt(2, history_id);
			st.setString(3, product_name);
			st.setInt(4, amount);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int countHistoryProductID(SQL_Manager connection){

		String sql = "select id from history_product order by id desc limit 1";
		int id = 0;
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		Statement st;
		try {
			st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@SuppressWarnings("serial")
	@Override
	public JTable updateTablePurchase(SQL_Manager connection, String user_rut) {
		JTable table = new JTable();
		table.setShowVerticalLines(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setBorder(null);
		String sql;
		PreparedStatement st;
		ResultSet rs = null;
		sql = "select hp.product_name, hp.amount, hp.purchase_date from history_product hp inner join shopping_history sh on sh.history_id = hp.history_id inner join users u on u.rut = sh.user_rut where u.rut = ? order by hp.purchase_date asc";
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, user_rut);
			rs = st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String titles[] = { "Nombre del producto", "Cantidad", "Fecha de compra" };
		DefaultTableModel model = new DefaultTableModel(null, titles) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		String row[] = new String[3];
		try {
			while (rs.next()) {
				row[0] = rs.getString("product_name");
				row[1] = Integer.toString(rs.getInt("amount"));
				row[2] = rs.getString("purchase_date");
				model.addRow(row);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.setRowHeight(26);
		table.setBounds(60, 60, 640, 280);
		return (table);
	}
	
	@Override
	public JScrollPane showHistory(JTable table) {
		try {
			JScrollPane scrollPane_userInfo = new JScrollPane(table);
			scrollPane_userInfo.setLocation(60, 60);
			scrollPane_userInfo.setSize(640, 280);
			scrollPane_userInfo.setViewportView(table);
			return scrollPane_userInfo;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}
}
