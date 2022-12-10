package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.SQL_Manager;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class UserMenuAccount extends JFrame {

	private JPanel contentPane;
	private JTextField textField_fullname;
	private JTextField textField_email;
	private JTextField textField_address;
	private JTextField textField_username;
	private JTextField textField_rut;
	private String user_rut;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMenuAccount frame = new UserMenuAccount("", null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public UserMenuAccount(String user, SQL_Manager connection) {
		setTitle("Cuenta de usuario");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserMenuAccount.class.getResource("/assets/SOH_logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String fullname = "";
		try {
			fullname = selectUserFullname(connection, user);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		textField_fullname = new JTextField();
		textField_fullname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_fullname.setColumns(10);
		textField_fullname.setText(fullname);
		textField_fullname.setBounds(236, 164, 337, 26);
		contentPane.add(textField_fullname);

		try {
			user_rut = selectUserRut(connection, user);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		textField_rut = new JTextField();
		textField_rut.setEditable(false);
		textField_rut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_rut.setColumns(10);
		textField_rut.setText(user_rut);
		textField_rut.setBounds(118, 90, 187, 26);
		contentPane.add(textField_rut);

		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(UserMenuAccount.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 407, 36, 26);
		contentPane.add(lbl_SOHlogo);

		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = user;
				try {
					username = selectUsername(connection,user_rut);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				UserMenu v4 = new UserMenu(username, connection);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(UserMenuAccount.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);

		JLabel lbl_name = new JLabel("Nombre Completo:");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name.setBounds(45, 162, 204, 26);
		contentPane.add(lbl_name);

		JLabel lbl_email = new JLabel("Correo:");
		lbl_email.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_email.setBounds(45, 198, 150, 26);
		contentPane.add(lbl_email);

		String email = "";
		try {
			email = selectUserEmail(connection, user);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		textField_email = new JTextField();
		textField_email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_email.setColumns(10);
		textField_email.setText(email);
		textField_email.setBounds(118, 198, 455, 26);

		contentPane.add(textField_email);

		JLabel lbl_address = new JLabel("Direcci\u00F3n:");
		lbl_address.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_address.setBounds(45, 234, 150, 26);
		contentPane.add(lbl_address);

		textField_address = new JTextField();
		textField_address.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_address.setColumns(10);
		textField_address.setBounds(148, 234, 425, 26);
		String address = "";
		try {
			address = selectUserAddress(connection, user);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		textField_address.setText(address);
		contentPane.add(textField_address);

		JLabel lbl_username = new JLabel("Nombre de usuario:");
		lbl_username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_username.setBounds(45, 126, 204, 26);
		contentPane.add(lbl_username);

		JLabel lbl_rut = new JLabel("RUT:");
		lbl_rut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_rut.setBounds(45, 90, 150, 26);
		contentPane.add(lbl_rut);

		textField_username = new JTextField();
		textField_username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_username.setColumns(10);
		textField_username.setText(user);
		textField_username.setBounds(236, 126, 337, 26);
		contentPane.add(textField_username);

		JButton btn_purchaseHistory = new JButton("REGISTRO DE COMPRAS");
		btn_purchaseHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int history_id = -1;
				boolean exist = false;
				try {
					history_id = selectHistoryId(connection, user);
					exist = purchaseHistoryExists(connection, history_id);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (exist) {
					UserMenuPurchaseHistory v13 = new UserMenuPurchaseHistory(user, connection);
					v13.setLocationRelativeTo(null);
					v13.setVisible(true);
					dispose();
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "No hay registros de compra para este usuario.");
				}
			}
		});
		btn_purchaseHistory.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_purchaseHistory.setBackground(Color.WHITE);
		btn_purchaseHistory.setBounds(254, 376, 273, 26);
		contentPane.add(btn_purchaseHistory);

		JButton btn_editAddress = new JButton("Editar");
		btn_editAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = textField_address.getText();
				try {
					editAddress(user_rut,connection,address);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_editAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_editAddress.setBackground(Color.WHITE);
		btn_editAddress.setBounds(583, 234, 115, 26);
		contentPane.add(btn_editAddress);

		JButton btn_editEmail = new JButton("Editar");
		btn_editEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = textField_email.getText();
				try {
					boolean exist = selectEmails(connection,email);
					if (exist) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "Este correo electrónico ya existe. Por favor, escoge otro.");
					}else {
						editEmail(user_rut,connection,email);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_editEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_editEmail.setBackground(Color.WHITE);
		btn_editEmail.setBounds(583, 198, 115, 26);
		contentPane.add(btn_editEmail);

		JButton btn_changePassword = new JButton("CAMBIAR CONTRASE\u00D1A");
		btn_changePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMenuChangePassword v13 = new UserMenuChangePassword(user, connection);
				v13.setLocationRelativeTo(null);
				v13.setVisible(true);
				dispose();
			}
		});
		btn_changePassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_changePassword.setBackground(Color.WHITE);
		btn_changePassword.setBounds(254, 297, 273, 26);
		contentPane.add(btn_changePassword);

		JButton btn_editUsername = new JButton("Editar");
		btn_editUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField_username.getText();
				try {
					boolean exist = selectUsernames(connection,username);
					if(exist) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "Este nombre de usuario ya existe. Por favor, escoge otro.");
					}else {
						editUsername(user_rut,connection,username);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_editUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_editUsername.setBackground(Color.WHITE);
		btn_editUsername.setBounds(583, 126, 115, 26);
		contentPane.add(btn_editUsername);

		JButton btn_editFullname = new JButton("Editar");
		btn_editFullname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fullname = textField_fullname.getText();
				try {
					editFullname(user_rut,connection,fullname);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_editFullname.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_editFullname.setBackground(Color.WHITE);
		btn_editFullname.setBounds(583, 162, 115, 26);
		contentPane.add(btn_editFullname);

		JLabel lbl_address_1 = new JLabel("* Deber\u00E1 ingresar su contrese\u00F1a actual para modificarla");
		lbl_address_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_address_1.setBounds(236, 320, 330, 26);
		contentPane.add(lbl_address_1);

		JLabel lbl_yourData = new JLabel("Tus datos:");
		lbl_yourData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_yourData.setBounds(76, 34, 150, 26);
		contentPane.add(lbl_yourData);
	}

	public String selectUserRut(SQL_Manager connection, String username) throws SQLException {
		String rut = "";
		String sql = "select rut from users where username = ?";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			rut = rs.getString("rut");
		}
		return rut;
	}

	public String selectUserEmail(SQL_Manager connection, String username) throws SQLException {
		String email = "";
		String sql = "select email from users where username = ?";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			email = rs.getString("email");
		}
		return email;
	}

	public String selectUserAddress(SQL_Manager connection, String username) throws SQLException {
		String address = "";
		String sql = "select address from users where username = ?";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			address = rs.getString("address");
		}
		return address;
	}

	public String selectUserFullname(SQL_Manager connection, String username) throws SQLException {
		String fullname = "";
		String sql = "select fullname from users where username = ?";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			fullname = rs.getString("fullname");
		}
		return fullname;
	}
	
	public String selectUsername(SQL_Manager connection, String rut) throws SQLException {
		String username = "";
		String sql = "select username from users where rut = ?";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, rut);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			username = rs.getString("username");
		}
		return username;
	}

	public void editUsername(String rut, SQL_Manager connection, String username) throws SQLException {

		if (!username.equals("")) {
			String sql = "update users set username = ? where rut = ?";

			PreparedStatement st = connection.getConnection().prepareStatement(sql);
			st.setString(1, username);
			st.setString(2, rut);
			st.executeUpdate();

			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Nombre de usuario actualizado.");
		} else {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No hay nombre de usuario para actualizar");
		}
	}

	public void editFullname(String rut, SQL_Manager connection, String fullname) throws SQLException {

		if (!fullname.equals("")) {
			String sql = "update users set fullname = ? where rut = ?";

			PreparedStatement st = connection.getConnection().prepareStatement(sql);
			st.setString(1, fullname);
			st.setString(2, rut);
			st.executeUpdate();

			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Nombre completo actualizado.");
		} else {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No hay nombre completo para actualizar");
		}
	}
	
	public void editEmail(String rut, SQL_Manager connection, String email) throws SQLException {

		if (!email.equals("")) {
			String sql = "update users set email = ? where rut = ?";

			PreparedStatement st = connection.getConnection().prepareStatement(sql);
			st.setString(1, email);
			st.setString(2, rut);
			st.executeUpdate();

			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Correo electrónico actualizado.");
		} else {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No hay correo electrónico para actualizar");
		}
	}
	
	public void editAddress(String rut, SQL_Manager connection, String address) throws SQLException {

		if (!address.equals("")) {
			String sql = "update users set address = ? where rut = ?";

			PreparedStatement st = connection.getConnection().prepareStatement(sql);
			st.setString(1, address);
			st.setString(2, rut);
			st.executeUpdate();

			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Dirección actualizada.");
		} else {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No hay dirección para actualizar");
		}
	}
	
	public boolean selectUsernames(SQL_Manager connection, String newUsername) throws SQLException {
		boolean exist = false;
		String sql = "select username from users";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			if(rs.getString("username").equals(newUsername)) {
				exist = true;
			}
		}
		return exist;
	}
	
	public boolean selectEmails(SQL_Manager connection, String newEmail) throws SQLException {
		boolean exist = false;
		String sql = "select email from users";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			if(rs.getString("email").equals(newEmail)) {
				exist = true;
			}
		}
		return exist;
	}
	
	public int selectHistoryId(SQL_Manager connection, String username) throws SQLException {
		int cart_id = -1;
		String sql = "select history_id from shopping_history where shopping_history.user_rut = (select rut from users where username = ?)";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		rs.next();
		cart_id = rs.getInt("history_id");
		return cart_id;
	}
	
	public boolean purchaseHistoryExists(SQL_Manager connection, int history_id) throws SQLException {

		boolean exist = false;
		String sql = "select * from history_product where history_id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, history_id);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			exist = true;
		}
		return exist;
	}
}
