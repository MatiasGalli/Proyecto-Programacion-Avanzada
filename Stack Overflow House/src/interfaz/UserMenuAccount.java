package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserMenuAccount extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_email;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMenuAccount frame = new UserMenuAccount();
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
	public UserMenuAccount() {
		setTitle("Cuenta de usuario");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserMenuAccount.class.getResource("/assets/SOH_logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(UserMenuAccount.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 407, 36, 26);
		contentPane.add(lbl_SOHlogo);
		
		JButton btn_back = new JButton();
		btn_back.setIcon(new ImageIcon(UserMenuAccount.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JLabel lbl_name = new JLabel("Nombre Completo:");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name.setBounds(45, 162, 204, 26);
		contentPane.add(lbl_name);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(236, 164, 337, 26);
		contentPane.add(textField);
		
		JLabel lbl_email = new JLabel("Correo:");
		lbl_email.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_email.setBounds(45, 198, 150, 26);
		contentPane.add(lbl_email);
		
		textField_email = new JTextField();
		textField_email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_email.setColumns(10);
		textField_email.setBounds(118, 198, 455, 26);
		contentPane.add(textField_email);
		
		JLabel lbl_address = new JLabel("Direcci\u00F3n:");
		lbl_address.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_address.setBounds(45, 234, 150, 26);
		contentPane.add(lbl_address);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_1.setColumns(10);
		textField_1.setBounds(148, 234, 425, 26);
		contentPane.add(textField_1);
		
		JLabel lbl_username = new JLabel("Nombre de usuario:");
		lbl_username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_username.setBounds(45, 126, 204, 26);
		contentPane.add(lbl_username);
		
		JLabel lbl_rut = new JLabel("RUT:");
		lbl_rut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_rut.setBounds(45, 90, 150, 26);
		contentPane.add(lbl_rut);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_2.setColumns(10);
		textField_2.setBounds(236, 126, 337, 26);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_3.setColumns(10);
		textField_3.setBounds(118, 90, 187, 26);
		contentPane.add(textField_3);
		
		JButton btn_delete = new JButton("REGISTRO DE COMPRAS");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_delete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_delete.setBackground(Color.WHITE);
		btn_delete.setBounds(254, 376, 273, 26);
		contentPane.add(btn_delete);
		
		JButton btn_delete_1 = new JButton("Editar");
		btn_delete_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_delete_1.setBackground(Color.WHITE);
		btn_delete_1.setBounds(583, 234, 115, 26);
		contentPane.add(btn_delete_1);
		
		JButton btn_delete_1_1 = new JButton("Editar");
		btn_delete_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_delete_1_1.setBackground(Color.WHITE);
		btn_delete_1_1.setBounds(583, 198, 115, 26);
		contentPane.add(btn_delete_1_1);
		
		JButton btn_delete_1_2 = new JButton("CAMBIAR CONTRASE\u00D1A");
		btn_delete_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_delete_1_2.setBackground(Color.WHITE);
		btn_delete_1_2.setBounds(254, 297, 273, 26);
		contentPane.add(btn_delete_1_2);
		
		JButton btn_delete_1_3 = new JButton("Editar");
		btn_delete_1_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_delete_1_3.setBackground(Color.WHITE);
		btn_delete_1_3.setBounds(583, 126, 115, 26);
		contentPane.add(btn_delete_1_3);
		
		JButton btn_delete_1_5 = new JButton("Editar");
		btn_delete_1_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_delete_1_5.setBackground(Color.WHITE);
		btn_delete_1_5.setBounds(583, 162, 115, 26);
		contentPane.add(btn_delete_1_5);
		
		JLabel lbl_address_1 = new JLabel("* Deber\u00E1 ingresar su contrese\u00F1a actual para modificarla");
		lbl_address_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_address_1.setBounds(236, 320, 330, 26);
		contentPane.add(lbl_address_1);
	}
}
