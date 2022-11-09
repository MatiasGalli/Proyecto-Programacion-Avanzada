package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.SQL_Manager;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField_username;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login(null);
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
	public Login(SQL_Manager connection) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/assets/SOH_logo.png")));
		setResizable(false);
		setTitle("Inicio de sesi\u00F3n");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_username = new JLabel("Usuario");
		lbl_username.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_username.setBounds(70, 40, 45, 13);
		contentPane.add(lbl_username);
		
		textField_username = new JTextField();
		textField_username.setBounds(70, 63, 200, 25);
		contentPane.add(textField_username);
		textField_username.setColumns(10);
		
		JLabel lbl_password = new JLabel("Contrase\u00F1a");
		lbl_password.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_password.setBounds(70, 110, 71, 13);
		contentPane.add(lbl_password);
		
		JLabel lbl_newUser = new JLabel("\u00BFNo tienes una cuenta?");
		lbl_newUser.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbl_newUser.setBounds(80, 167, 129, 15);
		contentPane.add(lbl_newUser);
		
		JButton btn_register = new JButton("Reg\u00EDstrate");
		btn_register.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register v2 = new Register(connection);
				v2.setVisible(true);
				dispose();
			}
		});
		btn_register.setForeground(new Color(0, 102, 255));
		btn_register.setBorder(null);
		btn_register.setContentAreaFilled(false);
		btn_register.setBounds(185, 164, 85, 21);
		contentPane.add(btn_register);
		
		JLabel lbl_SOHLogo = new JLabel("image");
		lbl_SOHLogo.setIcon(new ImageIcon(Login.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 227, 36, 26);
		contentPane.add(lbl_SOHLogo);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(70, 133, 200, 25);
		contentPane.add(passwordField);
		
		JButton btn_signIn = new JButton("Ingresar");
		btn_signIn.setBackground(new Color(255, 255, 255));
		btn_signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean userEmpty = textField_username.getText().equals("");
				boolean passEmpty = passwordField.getPassword().length==0;
				if(userEmpty) {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar un nombre de usuario.");
				}else if (passEmpty) {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar una clave.");
				}else {
					String sql = "select username,password from users where username = ?;";
					PreparedStatement st;
					ResultSet rs;
					String username = null;
					String userPassword = null;
					/*try {
						 FALTA SACAR EL USARIO Y CONTRASEÑA DE LA BASE DE DATOS
						st = connection.getConnection().prepareStatement(sql);
						st.setString(1, textField_username.getText());
						username = st.getString("username");
						userPassword = rs.getString("password");
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					*/
					String pass = String.valueOf(passwordField.getPassword());
					if(username == null){
						JFrame jFrame = new JFrame();
						JOptionPane.showMessageDialog(jFrame, "El usuario ingresado no existe");
					}else if(pass != userPassword) {
						JFrame jFrame = new JFrame();
						JOptionPane.showMessageDialog(jFrame, "La clave no coincide con el nombre de usuario");
					}else {
						String user = textField_username.getText();
						UserMenu v2 = new UserMenu(user, connection);
						v2.setVisible(true);
						dispose();
					}
				}
			}
		});
		btn_signIn.setBounds(135, 210, 85, 21);
		contentPane.add(btn_signIn);
	}
}
