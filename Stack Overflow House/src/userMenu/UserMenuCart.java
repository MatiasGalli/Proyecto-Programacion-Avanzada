package userMenu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import impl.CartImpl;
import impl.CategoryImpl;
import impl.CommentaryImpl;
import impl.ProductImpl;
import impl.PurchaseImpl;
import impl.UserImpl;
import logic.SQL_Manager;
import sendMail.SendMails;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UserMenuCart extends JFrame {

	private JPanel contentPane;
	private JTable table_cart;
	private JScrollPane scrollPane_cart;
	private JTextField textField_units;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMenuCart frame = new UserMenuCart("", null,null,null,null,null,null,null);
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
	 * @param connection
	 */
	public UserMenuCart(String user, SQL_Manager connection,CartImpl cartImpl,CategoryImpl categoryImpl,CommentaryImpl commentaryImpl,ProductImpl productImpl,
			PurchaseImpl purchaseImpl, UserImpl userImpl) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserMenuCart.class.getResource("/assets/SOH_logo.png")));
		setTitle("Carrito");
		setBackground(new Color(255, 250, 205));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 640);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table_cart = new JTable();
		scrollPane_cart = new JScrollPane();

		String user_rut = "";
		user_rut = userImpl.selectUserRut(connection, user);
		table_cart = cartImpl.updateTableCart(connection,user_rut);
		scrollPane_cart = cartImpl.showCart(table_cart);
		contentPane.add(scrollPane_cart);

		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMenu v4 = new UserMenu(user, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(UserMenuCart.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);

		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(UserMenuCart.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 567, 36, 26);
		contentPane.add(lbl_SOHlogo);

		JLabel lbl_cart = new JLabel(user + ", cuentas con los siguientes productos en tu carrito:");
		lbl_cart.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_cart.setBounds(63, 100, 839, 26);
		contentPane.add(lbl_cart);

		int totalAmount = productImpl.totalPrice(connection,user_rut);
		JLabel lbl_totalCart = new JLabel();
		lbl_totalCart.setText("Precio Total: \u0024" + totalAmount);
		lbl_totalCart.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_totalCart.setBorder(new LineBorder(new Color(0, 0, 128), 2, true));
		lbl_totalCart.setBackground(new Color(255, 215, 0));
		lbl_totalCart.setBounds(127, 543, 230, 25);
		contentPane.add(lbl_totalCart);

		JButton btn_buy = new JButton("COMPRAR CARRITO");
		btn_buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = 0;
				String rut ="";
				rut = userImpl.selectUserRut(connection,user);
				count = productImpl.countProducts(connection, rut);
				
				String message = "Has realizado la siguiente compra: <br><br>";
				for (int i = 0; i < count; i++) {
					int new_id = -1;
					int product_stock = Integer.parseInt((String) table_cart.getValueAt(i, 3));
					int product_id = Integer.parseInt((String) table_cart.getValueAt(i, 0));
					int product_total_price = productImpl.productPrice(connection,rut,product_id);
					String product_name = (String) table_cart.getValueAt(i, 1);
					new_id = purchaseImpl.countHistoryProductID(connection);
					int history_id = purchaseImpl.selectHistoryIDRut(connection, user);
					purchaseImpl.insertHistoryProduct(connection, new_id + 1, history_id, product_name, product_stock);
					message += " - " +product_name + " x " + product_stock + " unidades = $" + product_total_price + "<br>";

				}
				
				int totalAmount = productImpl.totalPrice(connection,rut);
				message += "<br>      - Precio Total de la compra: $" + totalAmount + "<br><br><br>Gracias por su preferencia.";
				int cart_id = 0;
				cart_id = cartImpl.selectCartId(connection, user);
				cartImpl.deleteCart(connection, cart_id);
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame, "\u00A1Compra realizada!");
				String user_mail = "";
				user_mail = userImpl.selectUserEmail(connection,user);
				String subject = "Gracias por tu compra en SOH, " + user;
				SendMails mail = new SendMails(user_mail,subject,message);
				mail.createEmail();
				mail.sendEmail();
				UserMenu v4 = new UserMenu(user, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_buy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_buy.setBackground(Color.WHITE);
		btn_buy.setBounds(405, 42, 215, 26);
		contentPane.add(btn_buy);

		JButton btn_substractUnit = new JButton("BORRAR UNIDAD");
		btn_substractUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_cart.getSelectedRow();
				if (row != -1) {
					int product_stock = Integer.parseInt((String) table_cart.getValueAt(row, 3));
					int units = Integer.parseInt(textField_units.getText());
					int product_id = Integer.parseInt((String) table_cart.getValueAt(row, 0));
					if (product_stock < Integer.parseInt(textField_units.getText())) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "Dato no v\u00E1lido. Selecciona un n\u00FAmero menor a los que se encuentran en el carrito.");
					} else {
						int cart_id = cartImpl.selectCartId(connection, user);
						productImpl.substractProductStock(connection, cart_id, product_id, units);
						int amount = cartImpl.selectStockInCart(connection, cart_id, product_id);
						if (amount == 0) {
							cartImpl.deleteProductInCart(connection, cart_id, product_id);
							JFrame jFrame = new JFrame();
							jFrame.setAlwaysOnTop(true);
							JOptionPane.showMessageDialog(jFrame, "Producto eliminado del carrito.");
						}else {
							JFrame jFrame = new JFrame();
							jFrame.setAlwaysOnTop(true);
							JOptionPane.showMessageDialog(jFrame, "Cantidad eliminada.");
						}
						productImpl.addStock(connection, units, product_id);
						String user_rut = "";
						user_rut = userImpl.selectUserRut(connection, user);
						table_cart = cartImpl.updateTableCart(connection,user_rut);
						int productsCant = cartImpl.countProductsInCart(connection,user_rut);
						if (productsCant != 0) {
							scrollPane_cart = cartImpl.showCart(table_cart);
							remove(contentPane.getComponentAt(100, 160));
							contentPane.add(scrollPane_cart);
							int totalAmount = productImpl.totalPrice(connection,user_rut);
							lbl_totalCart.setText("Precio Total: $" + totalAmount);
						}else {
							UserMenu v4 = new UserMenu(user,connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
							v4.setLocationRelativeTo(null);
							v4.setVisible(true);
							dispose();
						}
						
					}
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Selecciona un producto.");
				}
			}
		});
		btn_substractUnit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_substractUnit.setBackground(Color.WHITE);
		btn_substractUnit.setBounds(678, 531, 224, 26);
		contentPane.add(btn_substractUnit);

		textField_units = new JTextField();
		textField_units.setText("1");
		textField_units.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_units.setEditable(false);
		textField_units.setColumns(10);
		textField_units.setBounds(544, 542, 42, 26);
		contentPane.add(textField_units);

		JButton btn_minus = new JButton("-");
		btn_minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int unit = Integer.parseInt(textField_units.getText());
				if (unit != 1) {
					textField_units.setText(Integer.toString(unit - 1));
				}
			}
		});
		btn_minus.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_minus.setBackground(Color.WHITE);
		btn_minus.setBounds(493, 542, 52, 26);
		contentPane.add(btn_minus);

		JButton btn_add = new JButton("+");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int unit = Integer.parseInt(textField_units.getText());
				int max_unit = 100;
				if (unit < max_unit) {
					textField_units.setText(Integer.toString(unit + 1));
				}
			}
		});
		btn_add.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_add.setBackground(Color.WHITE);
		btn_add.setBounds(584, 542, 52, 26);
		contentPane.add(btn_add);
		
		JButton btn_addUnit = new JButton("AGREGAR UNIDAD");
		btn_addUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_cart.getSelectedRow();
				if (row != -1) {
					int units = Integer.parseInt(textField_units.getText());
					int product_id = Integer.parseInt((String) table_cart.getValueAt(row, 0));
					int product_remaining_stock = 0;
					product_remaining_stock = productImpl.selectRemainingStock(connection,product_id);
					if (product_remaining_stock < Integer.parseInt(textField_units.getText())) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "Dato no v\u00E1lido. No hay suficiente stock (Stock restante: "+ product_remaining_stock+")");
					} else {
						int cart_id = cartImpl.selectCartId(connection, user);
						cartImpl.addProductCart(connection, cart_id, product_id, units);
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "Cantidad agregada.");
						
						productImpl.substractStock(connection, units, product_id);
						String user_rut = "";
						user_rut = userImpl.selectUserRut(connection, user);
						
						table_cart = cartImpl.updateTableCart(connection,user_rut);
						scrollPane_cart = cartImpl.showCart(table_cart);
						remove(contentPane.getComponentAt(100, 160));
						contentPane.add(scrollPane_cart);
						int totalAmount = productImpl.totalPrice(connection,user_rut);
						lbl_totalCart.setText("Precio Total: $" + totalAmount);
					}
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Selecciona un producto.");
				}
			}
		});
		btn_addUnit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_addUnit.setBackground(Color.WHITE);
		btn_addUnit.setBounds(678, 563, 224, 26);
		contentPane.add(btn_addUnit);
	}
}