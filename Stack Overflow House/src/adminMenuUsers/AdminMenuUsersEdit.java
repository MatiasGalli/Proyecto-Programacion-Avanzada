package adminMenuUsers;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import adminMenu.AdminMenu;
import impl.CartImpl;
import impl.CategoryImpl;
import impl.CommentaryImpl;
import impl.ProductImpl;
import impl.PurchaseImpl;
import impl.UserImpl;
import logic.SQL_Manager;

import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AdminMenuUsersEdit extends JFrame {

	private JPanel contentPane;
	private JTextField textField_name;
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuUsersEdit frame = new AdminMenuUsersEdit("",null,null,null,null,null,null,null);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AdminMenuUsersEdit(String rut, SQL_Manager connection,CartImpl cartImpl,CategoryImpl categoryImpl,CommentaryImpl commentaryImpl,ProductImpl productImpl,
			PurchaseImpl purchaseImpl, UserImpl userImpl) {
		setBackground(new Color(255, 250, 205));
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenuUsersEdit.class.getResource("/assets/SOH_logo.png")));
		setTitle("Editar Usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(AdminMenuUsersEdit.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 287, 36, 26);
		contentPane.add(lbl_SOHlogo);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu v4 = new AdminMenu(rut, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(AdminMenuUsersEdit.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JComboBox comboBox_users = new JComboBox();
		comboBox_users.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox_users.setBounds(65, 76, 495, 26);
		int cant = 10;
		try {
			cant = userImpl.countUsersNoAdmin(connection);
		} catch (NumberFormatException e3) {
			e3.printStackTrace();
		}
		String[] list = new String[cant];
		String[] values = null;
		values = userImpl.getUsersNoAdmin(connection, list);
		comboBox_users.setModel(new DefaultComboBoxModel(values));
		contentPane.add(comboBox_users);
		
		
		
		JLabel lbl_select = new JLabel("Seleccione un usuario de la lista:");
		lbl_select.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_select.setBounds(65, 40, 359, 26);
		contentPane.add(lbl_select);
		
		JLabel lbl_name = new JLabel("Nombre:");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name.setBounds(65, 159, 150, 26);
		contentPane.add(lbl_name);
		
		textField_name = new JTextField();
		textField_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_name.setColumns(10);
		textField_name.setBounds(157, 159, 403, 26);
		contentPane.add(textField_name);
		
		JLabel lbl_password = new JLabel("Contrase\u00F1a:");
		lbl_password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_password.setBounds(65, 195, 150, 26);
		contentPane.add(lbl_password);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(180, 195, 380, 26);
		contentPane.add(passwordField);
		
		JRadioButton rdbtn_user = new JRadioButton("Rol Usuario");
		rdbtn_user.setSelected(true);
		rdbtn_user.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtn_user.setBounds(172, 248, 126, 21);
		rdbtn_user.setBackground(null);
		contentPane.add(rdbtn_user);
		
		JRadioButton rdbtn_admin = new JRadioButton("Rol Administrador");
		rdbtn_admin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtn_admin.setBackground((Color) null);
		rdbtn_admin.setBounds(312, 248, 175, 21);
		contentPane.add(rdbtn_admin);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtn_user);
		group.add(rdbtn_admin);
		
		JButton btn_edit = new JButton("EDITAR");
		btn_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean admin;
				String user_dni = (String) comboBox_users.getSelectedItem();
				String name = textField_name.getText();
				String password = String.valueOf(passwordField.getPassword());
				group.getSelection().getSelectedObjects();
				Object[] ascbtn = rdbtn_user.getSelectedObjects();
				if (ascbtn == null) {
					admin = true;
				}else {
					admin = false;
				}
				if (name.equals("")) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame,"Debes ingresar un nombre para editar");
				}else if (password.equals("")) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame,"Debes ingresar una contraseña para editar");
				}else {
					userImpl.editUsers(connection,user_dni,name,password,admin);
					AdminMenu v4 = new AdminMenu(rut, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
					v4.setLocationRelativeTo(null);
					v4.setVisible(true);
					dispose();
				}			
			}
		});
		btn_edit.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btn_edit.setBackground(Color.WHITE);
		btn_edit.setBounds(228, 280, 150, 30);
		contentPane.add(btn_edit);
		
		JButton btn_seeData = new JButton("VER DATOS");
		btn_seeData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_dni = (String) comboBox_users.getSelectedItem();
				try {
					seeData(connection,user_dni);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_seeData.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btn_seeData.setBackground(Color.WHITE);
		btn_seeData.setBounds(210, 112, 196, 30);
		contentPane.add(btn_seeData);
	}
	
	public void seeData(SQL_Manager connection, String rut) throws SQLException {
		PreparedStatement st;
		ResultSet rs;
		
		String sql = "select username, password from users where rut = ?";
		st = connection.getConnection().prepareStatement(sql);
		st.setString(1, rut);
		rs = st.executeQuery();
		connection.getConnection().prepareStatement(sql);
		rs.next();
		String name = rs.getString("username");
		String password = rs.getString("password");
		
		textField_name.setText(name);
		passwordField.setText(password);
		
	}
}
