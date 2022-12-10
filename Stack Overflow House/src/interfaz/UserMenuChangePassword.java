package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.SQL_Manager;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class UserMenuChangePassword extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField_current;
	private JPasswordField passwordField_new;
	private JPasswordField passwordField_confirm;
	private String user_rut;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMenuChangePassword frame = new UserMenuChangePassword("",null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserMenuChangePassword(String user, SQL_Manager connection) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserMenuChangePassword.class.getResource("/assets/SOH_logo.png")));
		setTitle("Cambiar Contrase\u00F1a");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
			user_rut = selectUserRut(connection,user);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(UserMenuChangePassword.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 287, 36, 26);
		contentPane.add(lbl_SOHLogo);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMenuAccount v12 = new UserMenuAccount(user, connection);
				v12.setLocationRelativeTo(null);
				v12.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(UserMenuChangePassword.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JLabel lbl_currentPassword = new JLabel("Contrase\u00F1a actual:");
		lbl_currentPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_currentPassword.setBounds(50, 55, 204, 26);
		contentPane.add(lbl_currentPassword);
		
		JLabel lbl_newPassword = new JLabel("Nueva contrase\u00F1a:");
		lbl_newPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_newPassword.setBounds(50, 104, 204, 26);
		contentPane.add(lbl_newPassword);
		
		JLabel lbl_search_1_1 = new JLabel("Confirmar contrase\u00F1a:");
		lbl_search_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_search_1_1.setBounds(50, 152, 204, 26);
		contentPane.add(lbl_search_1_1);
		
		passwordField_current = new JPasswordField();
		passwordField_current.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField_current.setBounds(231, 55, 345, 26);
		contentPane.add(passwordField_current);
		
		passwordField_new = new JPasswordField();
		passwordField_new.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField_new.setBounds(231, 104, 345, 26);
		contentPane.add(passwordField_new);
		
		passwordField_confirm = new JPasswordField();
		passwordField_confirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField_confirm.setBounds(264, 152, 312, 26);
		contentPane.add(passwordField_confirm);
		
		JButton btn_changePassword = new JButton("CAMBIAR CONTRASE\u00D1A");
		btn_changePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentPassword = String.valueOf(passwordField_current.getPassword());
				String newPassword = String.valueOf(passwordField_new.getPassword());
				String confirmPassword = String.valueOf(passwordField_confirm.getPassword());
				boolean exist = false;
				boolean compare = false;
				try {
					exist = selectCurrentPassword(connection,user_rut,currentPassword);
					compare = comparePassword(connection);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (currentPassword.equals("")) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar algo en el campo de Contraseña actual");
				}else if(newPassword.equals("")) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar algo en el campo de Contraseña nueva");
				}else if(confirmPassword.equals("")) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar algo en el campo de Confirmar contraseña");
				}else if(newPassword.equals(currentPassword)) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar una contraseña nueva diferente a la actual");
				}else if(!exist) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Contraseña actual incorrecta");
				}else if(!compare) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Las contraseñas nuevas no coinciden");
				}else {
					try {
						editPassword(user_rut,connection,newPassword,user);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_changePassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_changePassword.setBackground(Color.WHITE);
		btn_changePassword.setBounds(180, 221, 273, 26);
		contentPane.add(btn_changePassword);
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
	
	public boolean selectCurrentPassword(SQL_Manager connection, String user_rut, String current) throws SQLException {
		boolean exist = false;
		String sql = "select password from users where rut = ? and password = ?";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, user_rut);
		st.setString(2, current);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			exist = true;
		}
		return exist;
	}
	
	public boolean comparePassword(SQL_Manager connection) throws SQLException {
		boolean equal = false;
		String newPassword = String.valueOf(passwordField_new.getPassword());
		String confirmPassword = String.valueOf(passwordField_confirm.getPassword());
		if (newPassword.equals(confirmPassword)) {
			equal = true;
		}
		return equal;
	}
	
	public void editPassword(String rut, SQL_Manager connection, String newPassword, String user) throws SQLException {
		String sql;
		PreparedStatement st;

		sql = "update users set password = ? where rut = ?";

		st = connection.getConnection().prepareStatement(sql);
		st.setString(1, newPassword);
		st.setString(2, rut);
		st.executeUpdate();

		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jFrame, "Contraseña actualizada.");

		UserMenuAccount v12 = new UserMenuAccount(user, connection);
		v12.setLocationRelativeTo(null);
		v12.setVisible(true);
		dispose();

	}
}
