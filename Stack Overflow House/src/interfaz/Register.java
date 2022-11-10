package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.SQL_Manager;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class Register extends JFrame {

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
					Register frame = new Register(false, null);
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
	public Register(boolean admin, SQL_Manager connection) {
		char[] DV = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'k', };
		setIconImage(Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/assets/SOH_logo.png")));
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

		JLabel lbl_fullName = new JLabel("Nombre real completo");
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

		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(Register.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 227, 36, 26);
		contentPane.add(lbl_SOHLogo);

		passwordField = new JPasswordField();
		passwordField.setBounds(35, 183, 120, 19);
		contentPane.add(passwordField);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(185, 183, 120, 19);
		contentPane.add(confirmPasswordField);

		JButton btn_register = new JButton("Reg\u00EDstrate");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean userNameEmpty = textField_userName.getText().equals("");
				boolean realNameEmpty = textField_fullName.getText().equals("");
				boolean rutEmpty = textField_rut.getText().equals("");
				boolean emailEmpty = textField_email.getText().equals("");
				boolean verNumEmpty = textField_verificationNumber.getText().equals("");
				boolean passEmpty = passwordField.getPassword().length == 0;
				boolean confirmPassEmpty = confirmPasswordField.getPassword().length == 0;

				if (!(userNameEmpty || realNameEmpty || rutEmpty || verNumEmpty || emailEmpty || passEmpty
						|| confirmPassEmpty)) {

					@SuppressWarnings("unused")
					int trueRut = 0;
					try {
						trueRut = Integer.parseInt(textField_rut.getText());
					} catch (Exception p) {
						rutEmpty = true;
					}

					if (rutEmpty) {
						JFrame jFrame = new JFrame();
						JOptionPane.showMessageDialog(jFrame,
								"El espacio de RUT tiene caracteres inv\u00E1lidos. Coloque s\u00F3lo cifras num\u00E9ricas.");
					} else {
						if (textField_verificationNumber.getText().length() != 1) {
							JFrame jFrame = new JFrame();
							JOptionPane.showMessageDialog(jFrame,
									"Ha colocado m\u00E1s de un d\u00EDgito en la casilla de d\u00EDgito verificador.");
						} else {
							verNumEmpty = true;
							for (int i = 0; i < DV.length; i++) {
								if (DV[i] == textField_verificationNumber.getText().charAt(0)) {
									verNumEmpty = false;
									break;
								}
							}

							if (verNumEmpty) {
								JFrame jFrame = new JFrame();
								JOptionPane.showMessageDialog(jFrame,
										"El d\u00EDgito verificador ingresado no es válido.");
							} else {
								String[] rutVector = textField_rut.getText().split("");
								int sizeRut = rutVector.length;

								int aux1 = 0;
								for (int k = 0; k < sizeRut; k++) {
									aux1 = aux1 + (Integer.parseInt(rutVector[sizeRut - 1 - k]) * ((k % 6) + 2));
								}
								int aux2 = aux1 / 11;
								aux2 = aux2 * 11;
								aux1 = aux1 - aux2;
								if (aux1 < 0)
									aux1 = aux1 * (-1);
								aux1 = 11 - aux1;

								if (textField_verificationNumber.getText().equalsIgnoreCase("k")) {
									aux2 = 10;
								} else if (textField_verificationNumber.getText().equalsIgnoreCase("0")) {
									aux2 = 11;
								} else {
									aux2 = Integer.parseInt(textField_verificationNumber.getText());
								}

								if (aux2 != aux1) {
									JFrame jFrame = new JFrame();
									JOptionPane.showMessageDialog(jFrame, "El RUT ingresado no es válido.");
								} else {

									if (!(String.valueOf(confirmPasswordField.getPassword())
											.equals(String.valueOf(passwordField.getPassword())))) {
										JFrame jFrame = new JFrame();
										JOptionPane.showMessageDialog(jFrame, "Las contrase\u00F1as no coinciden.");
									} else {

										String[] auxEmail = textField_email.getText().split("@");

										if (auxEmail.length != 2) {
											JFrame jFrame = new JFrame();
											JOptionPane.showMessageDialog(jFrame,
													"El texto en la casilla de E-mail no es un E-mail v\u00E1lido.");
										} else if (!auxEmail[1].contains(".")) {
											JFrame jFrame = new JFrame();
											JOptionPane.showMessageDialog(jFrame,
													"El Email no tiene una direcci\u00F3n v\u00E1lida.");
										} else {
											JFrame jFrame = new JFrame();
											JOptionPane.showMessageDialog(jFrame, "¡Cuenta creada exitosamente!");
											String user = textField_userName.getText();
											String fullname = textField_fullName.getText();
											String rut = textField_rut.getText();
											String email = textField_email.getText();
											String verificationNumber = textField_verificationNumber.getText();
											String password = String.valueOf(passwordField.getPassword());
											String fullrut = rut + "-" + verificationNumber;
											try {
												insertUser(connection, fullrut, fullname, user, null, email, password);
											} catch (SQLException e1) {
												e1.printStackTrace();
											}
											if (!admin) {
												UserMenu v3 = new UserMenu(user, connection);
												v3.setVisible(true);
												dispose();
											}else {
												AdminMenu v4 = new AdminMenu(connection);
												v4.setVisible(true);
												dispose();
											}
											
										}
									}
								}
							}
						}
					}
				} else {
					JFrame jFrame = new JFrame();
					String x = "";
					if (userNameEmpty)
						x = x + "Nombre de usuario, ";
					if (realNameEmpty)
						x = x + "Nombre real completo, ";
					if (rutEmpty)
						x = x + "Rut, ";
					if (verNumEmpty)
						x = x + "D\u00EDgito Verificador, ";
					if (emailEmpty)
						x = x + "E-mail, ";
					if (passEmpty)
						x = x + "Contrase\u00F1a, ";
					if (confirmPassEmpty)
						x = x + "Confirmaci\u00F3n de contrase\u00F1a, ";

					x = x.substring(0, x.length() - 2);

					JOptionPane.showMessageDialog(jFrame, "Los siguientes espacios no tienen datos: " + x + ".");
				}
			}
		});
		btn_register.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btn_register.setBackground(new Color(255, 255, 255));
		btn_register.setBounds(122, 218, 100, 21);
		contentPane.add(btn_register);
	}

	public void insertUser(SQL_Manager connection, String rut, String fullname, String username, String address,
			String email, String password) throws SQLException {
		try {
			String sql = "insert into users(rut,fullname,username,address,email,admin,password) values (?,?,?,?,?,?,?)";
			PreparedStatement st = connection.getConnection().prepareStatement(sql);
			st.setString(1, rut);
			st.setString(2, fullname);
			st.setString(3, username);
			st.setString(4, address);
			st.setString(5, email);
			st.setBoolean(6, false);
			st.setString(7, password);
			st.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
