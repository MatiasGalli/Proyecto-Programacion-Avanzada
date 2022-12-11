package adminMenu;

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
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AdminMenuInfo extends JFrame {

	private JPanel contentPane;
	private JTextField textField_rut;
	private JTextField textField_username;
	private JTextField textField_fullname;
	private JTextField textField_email;
	private JTextField textField_address;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuInfo frame = new AdminMenuInfo(null,"",null,null,null,null,null,null);
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
	public AdminMenuInfo(SQL_Manager connection, String rut,CartImpl cartImpl,CategoryImpl categoryImpl,CommentaryImpl commentaryImpl,ProductImpl productImpl,
			PurchaseImpl purchaseImpl, UserImpl userImpl) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenuInfo.class.getResource("/assets/SOH_logo.png")));
		setTitle("Cuenta del administrador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(AdminMenuInfo.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 407, 36, 26);
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
		btn_back.setIcon(new ImageIcon(AdminMenuInfo.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JLabel lbl_yourData = new JLabel("Tus datos:");
		lbl_yourData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_yourData.setBounds(76, 66, 150, 26);
		contentPane.add(lbl_yourData);
		
		JLabel lbl_rut = new JLabel("RUT:");
		lbl_rut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_rut.setBounds(45, 122, 150, 26);
		contentPane.add(lbl_rut);
		
		textField_rut = new JTextField();
		textField_rut.setText(rut);
		textField_rut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_rut.setEditable(false);
		textField_rut.setColumns(10);
		textField_rut.setBounds(118, 122, 187, 26);
		contentPane.add(textField_rut);
		
		JLabel lbl_username = new JLabel("Nombre de usuario:");
		lbl_username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_username.setBounds(45, 158, 204, 26);
		contentPane.add(lbl_username);
		
		String user_name ="";
		user_name = userImpl.selectUsername(connection, rut);
		textField_username = new JTextField();
		textField_username.setText(user_name);
		textField_username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_username.setColumns(10);
		textField_username.setBounds(236, 158, 337, 26);
		contentPane.add(textField_username);
		
		JLabel lbl_name = new JLabel("Nombre Completo:");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name.setBounds(45, 194, 204, 26);
		contentPane.add(lbl_name);
		
		String fullname = "";
		fullname = userImpl.selectUserFullname(connection, rut);
		textField_fullname = new JTextField();
		textField_fullname.setText(fullname);
		textField_fullname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_fullname.setColumns(10);
		textField_fullname.setBounds(236, 196, 337, 26);
		contentPane.add(textField_fullname);
		
		JLabel lbl_email = new JLabel("Correo:");
		lbl_email.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_email.setBounds(45, 230, 150, 26);
		contentPane.add(lbl_email);
		
		
		String email = "";
		email = userImpl.selectUserEmail(connection, rut);
		textField_email = new JTextField();
		textField_email.setText(email);
		textField_email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_email.setColumns(10);
		textField_email.setBounds(118, 230, 455, 26);
		contentPane.add(textField_email);
		
		JLabel lbl_address = new JLabel("Direcci\u00F3n:");
		lbl_address.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_address.setBounds(45, 266, 150, 26);
		contentPane.add(lbl_address);
		
		String address = "";
		address = userImpl.selectUserAddress(connection, rut);
		textField_address = new JTextField();
		textField_address.setText(address);
		textField_address.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_address.setColumns(10);
		textField_address.setBounds(148, 266, 425, 26);
		contentPane.add(textField_address);
		
		JButton btn_changePassword = new JButton("CAMBIAR CONTRASE\u00D1A");
		btn_changePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuChangePassword v13 = new AdminMenuChangePassword(rut, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
				v13.setLocationRelativeTo(null);
				v13.setVisible(true);
				dispose();
			}
		});
		btn_changePassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_changePassword.setBackground(Color.WHITE);
		btn_changePassword.setBounds(254, 329, 273, 26);
		contentPane.add(btn_changePassword);
		
		JLabel lbl_advice = new JLabel("* Deber\u00E1 ingresar su contrese\u00F1a actual para modificarla");
		lbl_advice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_advice.setBounds(236, 352, 330, 26);
		contentPane.add(lbl_advice);
		
		JButton btn_editAddress = new JButton("Editar");
		btn_editAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = textField_address.getText();
				userImpl.editAddress(rut,connection,address);
			}
		});
		btn_editAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_editAddress.setBackground(Color.WHITE);
		btn_editAddress.setBounds(583, 266, 115, 26);
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
					userImpl.editEmail(rut,connection,email);
				}
			}
		});
		btn_editEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_editEmail.setBackground(Color.WHITE);
		btn_editEmail.setBounds(583, 230, 115, 26);
		contentPane.add(btn_editEmail);
		
		JButton btn_editFullname = new JButton("Editar");
		btn_editFullname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fullname = textField_fullname.getText();
				userImpl.editFullname(rut,connection,fullname);
			}
		});
		btn_editFullname.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_editFullname.setBackground(Color.WHITE);
		btn_editFullname.setBounds(583, 194, 115, 26);
		contentPane.add(btn_editFullname);
		
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
					userImpl.editUsername(rut,connection,username);
				}
			}
		});
		btn_editUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_editUsername.setBackground(Color.WHITE);
		btn_editUsername.setBounds(583, 158, 115, 26);
		contentPane.add(btn_editUsername);
	}
}
