package userMenu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import impl.CartImpl;
import impl.CategoryImpl;
import impl.CommentaryImpl;
import impl.ProductImpl;
import impl.PurchaseImpl;
import impl.UserImpl;
import logic.SQL_Manager;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
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
					UserMenuAccount frame = new UserMenuAccount("", null,null,null,null,null,null,null);
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
	public UserMenuAccount(String user, SQL_Manager connection,CartImpl cartImpl,CategoryImpl categoryImpl,CommentaryImpl commentaryImpl,ProductImpl productImpl,
			PurchaseImpl purchaseImpl, UserImpl userImpl) {
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
		fullname = userImpl.selectUserFullname(connection, user);
		textField_fullname = new JTextField();
		textField_fullname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_fullname.setColumns(10);
		textField_fullname.setText(fullname);
		textField_fullname.setBounds(236, 164, 337, 26);
		contentPane.add(textField_fullname);

		user_rut = userImpl.selectUserRut(connection, user);
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
				username = userImpl.selectUsername(connection,user_rut);
				UserMenu v4 = new UserMenu(username, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
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
		email = userImpl.selectUserEmail(connection, user);
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
		address = userImpl.selectUserAddress(connection, user);
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
				history_id = purchaseImpl.selectHistoryId(connection, user);
				exist = purchaseImpl.purchaseHistoryExists(connection, history_id);
				if (exist) {
					UserMenuPurchaseHistory v13 = new UserMenuPurchaseHistory(user, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
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
				userImpl.editAddress(user_rut,connection,address);
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
				boolean exist = userImpl.existEmail(connection,email);
				if (exist) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Este correo electrónico ya existe. Por favor, escoge otro.");
				}else {
					userImpl.editEmail(user_rut,connection,email);
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
				UserMenuChangePassword v13 = new UserMenuChangePassword(user, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
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
				boolean exist = userImpl.existUsername(connection,username);
				if(exist) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Este nombre de usuario ya existe. Por favor, escoge otro.");
				}else {
					userImpl.editUsername(user_rut,connection,username);
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
				userImpl.editFullname(user_rut,connection,fullname);
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
}