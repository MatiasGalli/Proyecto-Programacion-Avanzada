package init;

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
import userMenu.UserMenu;

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
import java.sql.ResultSet;
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
					Register frame = new Register("", false, null,null,null,null,null,null,null);
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
	public Register(String rutAdmin, boolean admin, SQL_Manager connection,CartImpl cartImpl,CategoryImpl categoryImpl,CommentaryImpl commentaryImpl,ProductImpl productImpl,
			PurchaseImpl purchaseImpl, UserImpl userImpl) {
		char[] DV = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'k', };
		setIconImage(Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/assets/SOH_logo.png")));
		setAlwaysOnTop(true);
		setTitle("Registro");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_userName = new JLabel("Nombre de usuario");
		lbl_userName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_userName.setBounds(35, 72, 198, 26);
		contentPane.add(lbl_userName);

		JLabel lbl_fullName = new JLabel("Nombre real completo");
		lbl_fullName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_fullName.setBounds(369, 72, 284, 26);
		contentPane.add(lbl_fullName);

		JLabel lbl_rut = new JLabel("RUT");
		lbl_rut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_rut.setBounds(35, 170, 100, 26);
		contentPane.add(lbl_rut);

		JLabel lbl_email = new JLabel("Correo electr\u00F3nico");
		lbl_email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_email.setBounds(308, 170, 167, 26);
		contentPane.add(lbl_email);

		JLabel lbl_password = new JLabel("Contrase\u00F1a");
		lbl_password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_password.setBounds(35, 274, 100, 26);
		contentPane.add(lbl_password);

		JLabel lbl_confirmPassword = new JLabel("Confirmar contrase\u00F1a");
		lbl_confirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_confirmPassword.setBounds(369, 274, 220, 26);
		contentPane.add(lbl_confirmPassword);

		textField_userName = new JTextField();
		textField_userName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_userName.setBounds(33, 97, 278, 26);
		contentPane.add(textField_userName);
		textField_userName.setColumns(10);

		textField_fullName = new JTextField();
		textField_fullName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_fullName.setColumns(10);
		textField_fullName.setBounds(369, 97, 321, 26);
		contentPane.add(textField_fullName);

		textField_rut = new JTextField();
		textField_rut.setColumns(10);
		textField_rut.setBounds(35, 201, 130, 26);
		contentPane.add(textField_rut);

		textField_email = new JTextField();
		textField_email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_email.setColumns(10);
		textField_email.setBounds(308, 198, 382, 26);
		contentPane.add(textField_email);

		textField_verificationNumber = new JTextField();
		textField_verificationNumber.setColumns(10);
		textField_verificationNumber.setBounds(197, 201, 36, 26);
		contentPane.add(textField_verificationNumber);

		JLabel lbl_hyphen = new JLabel("-");
		lbl_hyphen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_hyphen.setBounds(175, 204, 40, 23);
		contentPane.add(lbl_hyphen);

		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(Register.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 407, 36, 26);
		contentPane.add(lbl_SOHLogo);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setBounds(35, 300, 276, 26);
		contentPane.add(passwordField);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		confirmPasswordField.setBounds(369, 300, 321, 26);
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

				// If 1. Verifica si los espacios de datos no estï¿½n vacï¿½os.
				if (!(userNameEmpty || realNameEmpty || rutEmpty || verNumEmpty || emailEmpty || passEmpty
						|| confirmPassEmpty)) {

					@SuppressWarnings("unused")
					int trueRut = 0;
					try {
						trueRut = Integer.parseInt(textField_rut.getText());
					} catch (Exception p) {
						rutEmpty = true;
					}

					// If 2. Verifica si el espacio de rut tiene valores no numï¿½ricos. De estarlo,
					// envï¿½a una pop-up. Si no, pasa al 3er if.
					if (rutEmpty) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame,
								"El espacio de RUT tiene caracteres inv\u00E1lidos. Coloque s\u00F3lo cifras num\u00E9ricas.");
					} else {

						// If 3. Verifica que haya sï¿½lo un sï¿½mbolo en la casilla de dï¿½gito
						// verificador.
						if (textField_verificationNumber.getText().length() != 1) {
							JFrame jFrame = new JFrame();
							jFrame.setAlwaysOnTop(true);
							JOptionPane.showMessageDialog(jFrame,
									"Ha colocado m\u00E1s de un d\u00EDgito en la casilla de d\u00EDgito verificador.");
						} else {

							// Verifica si el char en el dï¿½gito verificador es un dï¿½gito entre el 0 y el
							// 9, o si es la letra "k".
							verNumEmpty = true;
							for (int i = 0; i < DV.length; i++) {
								if (DV[i] == textField_verificationNumber.getText().charAt(0)) {
									verNumEmpty = false;
									break;
								}
							}

							// If 4. Ve si el digito verificador es un dï¿½gito o la letra k.
							if (verNumEmpty) {
								JFrame jFrame = new JFrame();
								jFrame.setAlwaysOnTop(true);
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

								// If 5: Verifica si el R.U.T. es vï¿½lido dependiendo del dígito verificador.
								if (aux2 != aux1) {
									JFrame jFrame = new JFrame();
									jFrame.setAlwaysOnTop(true);
									JOptionPane.showMessageDialog(jFrame, "El RUT ingresado no es válido.");
								} else {

									// Corroborar que el RUT no se halle la en la database.
									String rut_aux = null;
									boolean banned = true;
									try {
										String sql = "select rut, banned from users where rut = ?";
										PreparedStatement st;
										st = connection.getConnection().prepareStatement(sql);
										st.setString(1,
												textField_rut.getText() + "-" + textField_verificationNumber.getText());
										ResultSet rs = st.executeQuery();
										rs.next();
										rut_aux = rs.getString(1);
										banned = rs.getBoolean(2);
									} catch (SQLException e1) {
										rut_aux = null;
										banned = true;
									}

									// If 5,5: Corrobora que el RUT ingresado NO estï¿½ ya en la base de datos.
									if (rut_aux != null) {
										JFrame jFrame = new JFrame();
										jFrame.setAlwaysOnTop(true);
										if (banned) {
											JOptionPane.showMessageDialog(jFrame,
													"El RUT ingresado ha sido vetado de ingresar al sistema nuevamente.");
										} else {
											JOptionPane.showMessageDialog(jFrame,
													"¡Este RUT ya pertenece a otro usuario!");
										}
									} else {
										// If 6: Revisa si las contraseï¿½as coinciden
										if (!(String.valueOf(confirmPasswordField.getPassword())
												.equals(String.valueOf(passwordField.getPassword())))) {
											JFrame jFrame = new JFrame();
											jFrame.setAlwaysOnTop(true);
											JOptionPane.showMessageDialog(jFrame, "Las contrase\u00F1as no coinciden.");
										} else {

											String[] auxEmail = textField_email.getText().split("@");

											// Ifs 7: Revisa si la casilla de Emails es vï¿½lida
											if (auxEmail.length != 2) {
												JFrame jFrame = new JFrame();
												jFrame.setAlwaysOnTop(true);
												JOptionPane.showMessageDialog(jFrame,
														"El texto en la casilla de E-mail no es un E-mail v\u00E1lido.");
											} else if (!auxEmail[1].contains(".")) {
												JFrame jFrame = new JFrame();
												jFrame.setAlwaysOnTop(true);
												JOptionPane.showMessageDialog(jFrame,
														"El Email no tiene una direcci\u00F3n v\u00E1lida.");
											} else {

												// Finalmente la cuenta es creada.
												String user = textField_userName.getText();
												String fullname = textField_fullName.getText();
												String rut = textField_rut.getText();
												String email = textField_email.getText();
												String verificationNumber = textField_verificationNumber.getText();
												String password = String.valueOf(passwordField.getPassword());
												String fullrut = rut + "-" + verificationNumber;
												boolean userExists = userImpl.existUsername(connection,user);
												boolean emailExists = userImpl.existEmail(connection,email);
												boolean productExist = productImpl.existProduct(connection);
												if (userExists) {
													JFrame jFrame2 = new JFrame();
													jFrame2.setAlwaysOnTop(true);
													JOptionPane.showMessageDialog(jFrame2,
															"El nombre de usuario ya existe.");
												}else if (emailExists) {
													JFrame jFrame2 = new JFrame();
													jFrame2.setAlwaysOnTop(true);
													JOptionPane.showMessageDialog(jFrame2,
															"El correo electrónico ya existe.");
												}else {
													userImpl.insertUser(connection, fullrut, fullname, user, null, email,
															password);
													int id = 0;
													id = cartImpl.countCartID(connection);
													cartImpl.insertCart(connection, id + 1, fullrut);
													int idH = 0;
													idH = purchaseImpl.countHistoryID(connection);
													purchaseImpl.insertHistoryID(connection, idH + 1, fullrut);
													JFrame jFrame = new JFrame();
													jFrame.setAlwaysOnTop(true);
													JOptionPane.showMessageDialog(jFrame, "¡Cuenta creada exitosamente!");
													// If 8. Verifica si el usuario no es admin.
													if (!admin) {
														UserMenu v3 = new UserMenu(user, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
														v3.setLocationRelativeTo(null);
														v3.setVisible(true);
														dispose();
													} else {
														if (!productExist) {
															JFrame jFrame2 = new JFrame();
															jFrame2.setAlwaysOnTop(true);
															JOptionPane.showMessageDialog(jFrame2,
																	"No hay productos que mostrar. Vuelva en otro momento");
															Login v1 = new Login(connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
															v1.setLocationRelativeTo(null);
															v1.setVisible(true);
															dispose();
														}else {
															AdminMenu v4 = new AdminMenu(rutAdmin, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
															v4.setLocationRelativeTo(null);
															v4.setVisible(true);
															dispose();
														}
													}
												}

												

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

					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Los siguientes espacios no tienen datos: " + x + ".");
				}
			}
		});
		btn_register.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btn_register.setBackground(new Color(255, 255, 255));
		btn_register.setBounds(308, 390, 130, 26);
		contentPane.add(btn_register);

		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!admin) {
					Login v1 = new Login(connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
					v1.setLocationRelativeTo(null);
					v1.setVisible(true);
					dispose();
				} else {
					AdminMenu v4 = new AdminMenu(rutAdmin, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
					v4.setLocationRelativeTo(null);
					v4.setVisible(true);
					dispose();
				}
			}
		});
		btn_back.setIcon(new ImageIcon(Register.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
	}
}
