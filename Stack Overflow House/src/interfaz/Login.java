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
	private JTextField textField_rut;
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
		setBounds(100, 100, 500, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_rut = new JLabel("RUT (Con gui\u00F3n y numero verificador)");
		lbl_rut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_rut.setBounds(98, 40, 480, 26);
		contentPane.add(lbl_rut);

		textField_rut = new JTextField();
		textField_rut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_rut.setBounds(79, 76, 310, 26);
		contentPane.add(textField_rut);
		textField_rut.setColumns(10);

		JLabel lbl_password = new JLabel("Contrase\u00F1a");
		lbl_password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_password.setBounds(205, 128, 116, 26);
		contentPane.add(lbl_password);

		JLabel lbl_newUser = new JLabel("\u00BFNo tienes una cuenta?");
		lbl_newUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_newUser.setBounds(109, 199, 177, 15);
		contentPane.add(lbl_newUser);

		JButton btn_register = new JButton("Reg\u00EDstrate");
		btn_register.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register v2 = new Register("",false, connection);
				v2.setLocationRelativeTo(null);
				v2.setVisible(true);
				dispose();
			}
		});
		btn_register.setForeground(new Color(0, 102, 255));
		btn_register.setBorder(null);
		btn_register.setContentAreaFilled(false);
		btn_register.setBounds(281, 196, 85, 21);
		contentPane.add(btn_register);

		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(Login.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 287, 36, 26);
		contentPane.add(lbl_SOHLogo);

		passwordField = new JPasswordField();
		passwordField.setBounds(79, 164, 310, 25);
		contentPane.add(passwordField);

		JButton btn_signIn = new JButton("Ingresar");
		btn_signIn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btn_signIn.setBackground(new Color(255, 255, 255));
		btn_signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean userEmpty = textField_rut.getText().equals("");
				boolean passEmpty = passwordField.getPassword().length == 0;
				if (userEmpty) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar un RUT.");
				} if (passEmpty) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar una clave.");
				} if (!passEmpty && !userEmpty) {
					String rut = null;
					String password = null;
					boolean admin = false;
					boolean banned = false;
					String name = null;
					try {
						String sql = "select rut, password, admin, username, banned from users where rut = ?";
						PreparedStatement st;
						st = connection.getConnection().prepareStatement(sql);
						st.setString(1, textField_rut.getText());
						ResultSet rs = st.executeQuery();
						rs.next();
						rut = rs.getString(1);
						password = rs.getString(2);
						admin = rs.getBoolean(3);
						name = rs.getString(4);
						banned = rs.getBoolean(5);
					} catch (SQLException e1) {
						rut = null;
						password = null;
					}

					String pass = String.valueOf(passwordField.getPassword());
					if (rut == null) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "El RUT ingresado no existe, o ingresaste algo que no corresponde a un RUT.");
					} else if (!pass.equals(password)) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "Clave incorrecta.");
					} else {
						if (!banned) {
							if (!admin) {
								UserMenu v3 = new UserMenu(name, connection);
								v3.setLocationRelativeTo(null);
								v3.setVisible(true);
								dispose();
							} else {
								AdminMenu v4 = new AdminMenu(rut, connection);
								v4.setLocationRelativeTo(null);
								v4.setVisible(true);
								dispose();
							}
						} else {
							JFrame jFrame = new JFrame();
							jFrame.setAlwaysOnTop(true);
							JOptionPane.showMessageDialog(jFrame, "Este usuario est\u00E1 bloqueado.");
						}
					}
				}
			}
		});
		btn_signIn.setBounds(189, 250, 106, 26);
		contentPane.add(btn_signIn);
	}
}
