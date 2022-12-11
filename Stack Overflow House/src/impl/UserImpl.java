package impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import interfaces.User;
import logic.SQL_Manager;

import java.awt.Font;
import java.sql.PreparedStatement;

public class UserImpl implements User {

	@Override
	public boolean existUsername(SQL_Manager connection, String newUsername) {
		boolean exist = false;
		String sql = "select username from users";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (rs.getString("username").equals(newUsername)) {
					exist = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exist;
	}

	@Override
	public boolean existEmail(SQL_Manager connection, String newEmail) {
		boolean exist = false;
		String sql = "select email from users";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (rs.getString("email").equals(newEmail)) {
					exist = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

	@Override
	public void insertUser(SQL_Manager connection, String rut, String fullname, String username, String address,
			String email, String password) {
		try {
			String sql = "insert into users(rut,fullname,username,address,email,admin,password,banned) values (?,?,?,?,?,?,?,?)";
			PreparedStatement st = connection.getConnection().prepareStatement(sql);
			st.setString(1, rut);
			st.setString(2, fullname);
			st.setString(3, username);
			st.setString(4, address);
			st.setString(5, email);
			st.setBoolean(6, false);
			st.setString(7, password);
			st.setBoolean(8, false);
			st.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public String selectUserRut(SQL_Manager connection, String username) {
		String rut = "";
		String sql = "select rut from users where username = ?";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				rut = rs.getString("rut");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rut;
	}
	
	@Override
	public void editAddress(String rut, SQL_Manager connection, String address) {

		if (!address.equals("")) {
			String sql = "update users set address = ? where rut = ?";

			PreparedStatement st;
			try {
				st = connection.getConnection().prepareStatement(sql);
				st.setString(1, address);
				st.setString(2, rut);
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Dirección actualizada.");
		} else {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No hay dirección para actualizar");
		}
	}
	
	@Override
	public void editEmail(String rut, SQL_Manager connection, String email) {

		if (!email.equals("")) {
			String sql = "update users set email = ? where rut = ?";

			PreparedStatement st;
			try {
				st = connection.getConnection().prepareStatement(sql);
				st.setString(1, email);
				st.setString(2, rut);
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Correo electrónico actualizado.");
		} else {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No hay correo electrónico para actualizar");
		}
	}
	
	@Override
	public void editFullname(String rut, SQL_Manager connection, String fullname) {

		if (!fullname.equals("")) {
			String sql = "update users set fullname = ? where rut = ?";

			PreparedStatement st;
			try {
				st = connection.getConnection().prepareStatement(sql);
				st.setString(1, fullname);
				st.setString(2, rut);
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Nombre completo actualizado.");
		} else {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No hay nombre completo para actualizar");
		}
	}
	
	@Override
	public void editUsername(String rut, SQL_Manager connection, String username) {

		if (!username.equals("")) {
			String sql = "update users set username = ? where rut = ?";

			PreparedStatement st;
			try {
				st = connection.getConnection().prepareStatement(sql);
				st.setString(1, username);
				st.setString(2, rut);
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Nombre de usuario actualizado.");
		} else {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No hay nombre de usuario para actualizar");
		}
	}
	
	@Override
	public String selectUsername(SQL_Manager connection, String rut) {
		String username = "";
		String sql = "select username from users where rut = ?";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, rut);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				username = rs.getString("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return username;
	}

	@Override
	public String selectUserFullname(SQL_Manager connection, String username) {
		String fullname = "";
		String sql = "select fullname from users where username = ?";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				fullname = rs.getString("fullname");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fullname;
	}

	@Override
	public String selectUserAddress(SQL_Manager connection, String username) {
		String address = "";
		String sql = "select address from users where username = ?";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				address = rs.getString("address");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}

	@Override
	public String selectUserEmail(SQL_Manager connection, String username) {
		String email = "";
		String sql = "select email from users where username = ?";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				email = rs.getString("email");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return email;
	}

	@Override
	public void editPassword(String rut, SQL_Manager connection, String newPassword) {
		String sql;
		PreparedStatement st;

		sql = "update users set password = ? where rut = ?";

		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, newPassword);
			st.setString(2, rut);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jFrame, "Contraseña actualizada.");
	}

	@Override
	public boolean selectCurrentPassword(SQL_Manager connection, String user_rut, String current) {
		boolean exist = false;
		String sql = "select password from users where rut = ? and password = ?";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, user_rut);
			st.setString(2, current);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

	@SuppressWarnings("serial")
	@Override
	public JTable searchUser(SQL_Manager connection, String category, String search) {
		try {
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql, ban;
			int cont = 0;
			PreparedStatement st;
			ResultSet rs;
			sql = "Select * from users where " + category + " = ?;";
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, search);
			rs = st.executeQuery();
			String titles[] = { "RUT", "Nombre de usuario", "Nombre Completo", "Correo Electr\u00F3nico", "Bloqueado" };
			DefaultTableModel model = new DefaultTableModel(null, titles) {
				boolean[] columnEditables = new boolean[] { false, false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			String row[] = new String[6];
			while (rs.next()) {
				if (!rs.getBoolean("admin")) {
					row[0] = rs.getString("rut");
					row[1] = rs.getString("username");
					row[2] = rs.getString("fullname");
					row[3] = rs.getString("email");
					if (rs.getBoolean("banned"))
						ban = "Si";
					else
						ban = "No";
					row[4] = ban;
					model.addRow(row);
					cont++;
				}
			}
			if (cont == 0) {
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame, "No hay coincidencias en la búsqueda");
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(69);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(83);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(213);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(172);
			table.getColumnModel().getColumn(4).setPreferredWidth(15);
			table.setRowHeight(26);
			table.setBounds(60, 110, 900, 310);
			return (table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	@SuppressWarnings("serial")
	@Override
	public JTable updateTableUsers(SQL_Manager connection, String category, String asc) {
		try {
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql, ban;
			PreparedStatement st;
			ResultSet rs;
			sql = "Select * from users order by " + category + " " + asc;
			st = connection.getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			String titles[] = { "RUT", "Nombre de usuario", "Nombre Completo", "Correo Electr\u00F3nico", "Bloqueado" };
			DefaultTableModel model = new DefaultTableModel(null, titles) {
				boolean[] columnEditables = new boolean[] { false, false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			String row[] = new String[6];
			while (rs.next()) {
				if (!rs.getBoolean("admin")) {
					row[0] = rs.getString("rut");
					row[1] = rs.getString("username");
					row[2] = rs.getString("fullname");
					row[3] = rs.getString("email");
					if (rs.getBoolean("banned"))
						ban = "Si";
					else
						ban = "No";
					row[4] = ban;
					model.addRow(row);
				}
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(69);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(83);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(213);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(172);
			table.getColumnModel().getColumn(4).setPreferredWidth(15);
			table.setRowHeight(26);
			table.setBounds(60, 110, 900, 310);
			return (table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	@Override
	public JScrollPane showUsers(JTable table) {
		try {
			JScrollPane scrollPane_userInfo = new JScrollPane(table);
			scrollPane_userInfo.setLocation(60, 120);
			scrollPane_userInfo.setSize(900, 330);
			scrollPane_userInfo.setViewportView(table);
			return scrollPane_userInfo;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	@Override
	public void banUser(SQL_Manager connection, String rut, boolean ban, String order, String asc) {
		String sql;
		PreparedStatement st;
		boolean ban_user = false;
		sql = "select banned from users where rut = ?";
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, rut);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				ban_user = rs.getBoolean("banned");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "update users set banned = ? where rut = ?";
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setBoolean(1, ban);
			st.setString(2, rut);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (ban == true) {
			if (ban_user == true) {
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame, "El usuario ya se encuentra bloqueado.");
			} else {
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame, "El usuario ha sido bloqueado exitosamente.");
			}
		} else {
			if (ban_user == false) {
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame, "El usuario ya se encuentra desbloqueado.");
			} else {
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame, "El usuario ha sido desbloqueado exitosamente.");
			}
		}
	}

	@Override
	public void editUsers(SQL_Manager connection, String users_dni, String name, String password, boolean admin) {

		String sql = "update users set username = ?, password = ?, admin = ? where rut = ?";

		PreparedStatement st = null;
		try {
			st = connection.getConnection().prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			st.setString(1, name);
			st.setString(2, password);
			st.setBoolean(3, admin);
			st.setString(4, users_dni);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jFrame, "Usuario actualizado.");
	}
	
	@Override
	public String[] getUsersNoAdmin(SQL_Manager connection, String[] list) {

		String[] values = list;
		String sql = "select rut from users where admin <> TRUE order by rut asc";
		Statement st;
		try {
			st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			int cant = 0;
			while (rs.next()) {
				values[cant] = rs.getString("rut");
				cant++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return values;
	}
	
	@Override
	public int countUsersNoAdmin(SQL_Manager connection) {

		String sql = "select count(*) as id from users where admin <> TRUE ";
		int id = 0;
		Statement st;
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
}
