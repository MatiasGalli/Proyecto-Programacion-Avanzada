package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class RegistroSOH extends JFrame {

	private JPanel contentPane;
	private JTextField textField_userName;
	private JTextField textField_fullName;
	private JTextField textField_rut;
	private JTextField textField_email;
	private JTextField textField_verificationNumber;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroSOH frame = new RegistroSOH();
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
	public RegistroSOH() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegistroSOH.class.getResource("/assets/SOH_logo.png")));
		setAlwaysOnTop(true);
		setTitle("Registro");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_userName = new JLabel("Nombre de usuario");
		lbl_userName.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_userName.setBounds(35, 41, 100, 15);
		contentPane.add(lbl_userName);
		
		JLabel lbl_fullName = new JLabel("Nombre completo");
		lbl_fullName.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_fullName.setBounds(185, 41, 100, 15);
		contentPane.add(lbl_fullName);
		
		JLabel lbl_rut = new JLabel("RUT");
		lbl_rut.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_rut.setBounds(35, 100, 100, 15);
		contentPane.add(lbl_rut);
		
		JLabel lbl_email = new JLabel("Correo electr\u00F3nico");
		lbl_email.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_email.setBounds(185, 100, 100, 15);
		contentPane.add(lbl_email);
		
		JLabel lbl_password = new JLabel("Contrase\u00F1a");
		lbl_password.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_password.setBounds(35, 158, 100, 15);
		contentPane.add(lbl_password);
		
		JLabel lbl_confirmPassword = new JLabel("Confirmar contrase\u00F1a");
		lbl_confirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_confirmPassword.setBounds(185, 158, 113, 15);
		contentPane.add(lbl_confirmPassword);
		
		JButton btn_register = new JButton("Reg\u00EDstrate");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_register.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btn_register.setBackground(new Color(255, 255, 255));
		btn_register.setBounds(122, 218, 100, 21);
		contentPane.add(btn_register);
		
		textField_userName = new JTextField();
		textField_userName.setBounds(35, 66, 120, 19);
		contentPane.add(textField_userName);
		textField_userName.setColumns(10);
		
		textField_fullName = new JTextField();
		textField_fullName.setColumns(10);
		textField_fullName.setBounds(185, 66, 120, 19);
		contentPane.add(textField_fullName);
		
		textField_rut = new JTextField();
		textField_rut.setColumns(10);
		textField_rut.setBounds(35, 125, 85, 19);
		contentPane.add(textField_rut);
		
		textField_email = new JTextField();
		textField_email.setColumns(10);
		textField_email.setBounds(185, 125, 120, 19);
		contentPane.add(textField_email);
		
		textField_verificationNumber = new JTextField();
		textField_verificationNumber.setColumns(10);
		textField_verificationNumber.setBounds(130, 125, 25, 19);
		contentPane.add(textField_verificationNumber);
		
		JLabel lbl_hyphen = new JLabel("-");
		lbl_hyphen.setBounds(122, 125, 40, 23);
		contentPane.add(lbl_hyphen);
		
		JLabel lbl_SOHLogo = new JLabel("image");
		lbl_SOHLogo.setIcon(new ImageIcon(RegistroSOH.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 227, 36, 26);
		contentPane.add(lbl_SOHLogo);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(35, 183, 120, 19);
		contentPane.add(passwordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(185, 183, 120, 19);
		contentPane.add(confirmPasswordField);
	}
}
