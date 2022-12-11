package userMenu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.SQLException;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import logic.SQL_Manager;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import impl.CartImpl;
import impl.CategoryImpl;
import impl.CommentaryImpl;
import impl.ProductImpl;
import impl.PurchaseImpl;
import impl.UserImpl;
import init.Login;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class UserMenu extends JFrame {

	private JPanel contentPane;
	private JTable table_products;
	private JScrollPane scrollPane_products;
	private JTextField textField_search;
	private JTextField textField_description;
	private JTextField textField_units;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMenu frame = new UserMenu("", null,null,null,null,null,null,null);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserMenu(String user, SQL_Manager connection,CartImpl cartImpl,CategoryImpl categoryImpl,CommentaryImpl commentaryImpl,ProductImpl productImpl,
			PurchaseImpl purchaseImpl, UserImpl userImpl) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserMenu.class.getResource("/assets/SOH_logo.png")));
		setTitle("Men\u00FA de usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 640);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_welcomeMessage = new JLabel("\u00A1Nos alegra que est\u00E9s de vuelta, " + user
				+ "! Estos son los productos que se encuentran disponibles:");
		lbl_welcomeMessage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_welcomeMessage.setBounds(36, 38, 788, 26);
		contentPane.add(lbl_welcomeMessage);

		table_products = new JTable();
		scrollPane_products = new JScrollPane();

		table_products = productImpl.updateTableProduct(connection);
		scrollPane_products = productImpl.showProducts(table_products);
		contentPane.add(scrollPane_products);

		JButton btn_userAccount = new JButton("CUENTA DE USUARIO");
		btn_userAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMenuAccount v12 = new UserMenuAccount(user, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
				v12.setLocationRelativeTo(null);
				v12.setVisible(true);
				dispose();
			}
		});
		btn_userAccount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btn_userAccount.setBackground(new Color(255, 255, 255));
		btn_userAccount.setBounds(717, 10, 203, 26);
		contentPane.add(btn_userAccount);

		JButton btn_addToTheCart = new JButton("A\u00F1adir al carrito");
		btn_addToTheCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_products.getSelectedRow();
				if (row != -1) {
					int product_stock = Integer.parseInt((String) table_products.getValueAt(row, 3));
					int units = Integer.parseInt(textField_units.getText());
					int product_id = Integer.parseInt((String) table_products.getValueAt(row, 0));
					if (product_stock < Integer.parseInt(textField_units.getText())) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "No hay stock suficiente para las unidades indicadas.");
					} else {
						int cart_id = cartImpl.selectCartId(connection, user);
						boolean exist = cartImpl.existProductCart(connection, cart_id, product_id);
						if (exist) {
							cartImpl.addProductCart(connection, cart_id, product_id, units);
						} else {
							cartImpl.addProductInCart(connection, cart_id, product_id, units);
						}
						productImpl.substractStockMenu(connection, units, product_id);
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "Producto a\u00F1adido al carrito.");
						table_products = productImpl.updateTableProductOrder(connection, "name", "asc");
						scrollPane_products = productImpl.showProducts(table_products);
						remove(contentPane.getComponentAt(37, 200));
						contentPane.add(scrollPane_products);
					}
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Selecciona un producto.");
				}
			}
		});
		btn_addToTheCart.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_addToTheCart.setBackground(Color.WHITE);
		btn_addToTheCart.setBounds(362, 563, 186, 26);
		contentPane.add(btn_addToTheCart);

		JLabel lbl_logoSOH = new JLabel("Logo");
		lbl_logoSOH.setIcon(new ImageIcon(UserMenu.class.getResource("/assets/SOH_logoMin.png")));
		lbl_logoSOH.setBounds(10, 567, 36, 26);
		contentPane.add(lbl_logoSOH);

		JButton btn_seeCart = new JButton("Ver carrito");
		btn_seeCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cart_id = -1;
				boolean exist = false;
				cart_id = cartImpl.selectCartId(connection, user);
				exist = cartImpl.existProductInCart(connection, cart_id);
				if (exist) {
					UserMenuCart v8 = new UserMenuCart(user, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
					v8.setLocationRelativeTo(null);
					v8.setVisible(true);
					dispose();
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "No hay productos en el carrito.");
				}

			}
		});
		btn_seeCart.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn_seeCart.setBackground(Color.WHITE);
		btn_seeCart.setBounds(674, 558, 150, 35);
		contentPane.add(btn_seeCart);

		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login v1 = new Login(connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
				v1.setLocationRelativeTo(null);
				v1.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(UserMenu.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);

		JLabel lbl_search = new JLabel("B\u00FAsqueda por t\u00E9rmino:");
		lbl_search.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_search.setBounds(50, 82, 204, 26);
		contentPane.add(lbl_search);

		textField_search = new JTextField();
		textField_search.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_search.setColumns(10);
		textField_search.setBounds(223, 82, 325, 26);
		contentPane.add(textField_search);

		JPanel panel_search1 = new JPanel();
		panel_search1.setBorder(null);
		panel_search1.setBackground(new Color(240, 230, 140));
		panel_search1.setBounds(36, 74, 519, 69);
		contentPane.add(panel_search1);
		panel_search1.setLayout(null);

		JLabel lbl_category = new JLabel("en la categor\u00EDa:");
		lbl_category.setBounds(96, 37, 144, 26);
		panel_search1.add(lbl_category);
		lbl_category.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JComboBox comboBox_categorySearch = new JComboBox();
		comboBox_categorySearch.setBounds(211, 37, 216, 26);
		panel_search1.add(comboBox_categorySearch);
		comboBox_categorySearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_categorySearch.setModel(new DefaultComboBoxModel(new String[] { "Nombre del producto", "Categor\u00EDa" }));

		JPanel panel_search2 = new JPanel();
		panel_search2.setBorder(null);
		panel_search2.setBackground(new Color(240, 230, 140));
		panel_search2.setBounds(204, 141, 180, 38);
		contentPane.add(panel_search2);
		panel_search2.setLayout(null);

		JButton btn_search = new JButton("BUSCAR");
		btn_search.setBounds(25, 5, 140, 26);
		panel_search2.add(btn_search);
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String categorySearch = (String) comboBox_categorySearch.getSelectedItem();
				if (categorySearch.equals("Nombre del producto")) {
					categorySearch = "name";
				} else if (categorySearch.equals("Categor\u00EDa")) {
					categorySearch = "category_id";
				}
				String search = textField_search.getText();
				table_products = productImpl.searchProduct(connection, categorySearch, search);
				scrollPane_products = productImpl.showProducts(table_products);
				remove(contentPane.getComponentAt(37, 200));
				contentPane.add(scrollPane_products);
			}
		});
		btn_search.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_search.setBackground(Color.WHITE);

		JLabel lbl_description = new JLabel("Descripci\u00F3n:");
		lbl_description.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_description.setBounds(47, 482, 116, 26);
		contentPane.add(lbl_description);

		textField_description = new JTextField();
		textField_description.setEditable(false);
		textField_description.setText((String) null);
		textField_description.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_description.setColumns(10);
		textField_description.setBounds(141, 482, 755, 26);
		contentPane.add(textField_description);

		textField_units = new JTextField();
		textField_units.setText("1");
		textField_units.setEditable(false);
		textField_units.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_units.setColumns(10);
		textField_units.setBounds(192, 563, 42, 26);
		contentPane.add(textField_units);

		JPanel panel_info = new JPanel();
		panel_info.setLayout(null);
		panel_info.setBorder(null);
		panel_info.setBackground(new Color(240, 230, 140));
		panel_info.setBounds(36, 417, 880, 113);
		contentPane.add(panel_info);

		JButton btn_seeComment = new JButton("VER COMENTARIOS");
		btn_seeComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_products.getSelectedRow();
				if (row != -1) {
					boolean exist = false;
					int product_id = 0;
					product_id = Integer.parseInt((String) table_products.getValueAt(row, 0));
					exist = commentaryImpl.existProductComment(connection, product_id);
					if (exist) {
						product_id = Integer.parseInt((String) table_products.getValueAt(row, 0));
						UserMenuComments v14 = new UserMenuComments(user, product_id, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
						v14.setLocationRelativeTo(null);
						v14.setVisible(true);
						dispose();
					}else {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "No hay comentarios de este producto");
					}
					
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Selecciona un producto.");
				}
				
			}
		});
		btn_seeComment.setBounds(481, 23, 219, 26);
		panel_info.add(btn_seeComment);
		btn_seeComment.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_seeComment.setBackground(Color.WHITE);

		JRadioButton rdbtn_asc = new JRadioButton("Ascendente");
		rdbtn_asc.setSelected(true);
		rdbtn_asc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtn_asc.setBackground(new Color(240, 230, 140));
		rdbtn_asc.setBounds(600, 114, 140, 15);
		contentPane.add(rdbtn_asc);

		JRadioButton rdbtn_desc = new JRadioButton("Descendente");
		rdbtn_desc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtn_desc.setBackground(new Color(240, 230, 140));
		rdbtn_desc.setBounds(742, 115, 150, 15);
		contentPane.add(rdbtn_desc);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtn_asc);
		group.add(rdbtn_desc);

		JComboBox comboBox_categoryOrder = new JComboBox();
		comboBox_categoryOrder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_categoryOrder
				.setModel(new DefaultComboBoxModel(new String[] { "Nombre del producto", "Precio", "Stock" }));
		comboBox_categoryOrder.setBounds(690, 83, 214, 26);
		contentPane.add(comboBox_categoryOrder);

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
		btn_minus.setBounds(141, 563, 52, 26);
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
		btn_add.setBounds(232, 563, 52, 26);
		contentPane.add(btn_add);

		JButton btn_seeData = new JButton("VER DESCRIPCI\u00D3N");
		btn_seeData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_products.getSelectedRow();
				if (row != -1) {
					int product_id = Integer.parseInt((String) table_products.getValueAt(row, 0));
					String description = productImpl.seeDescription(connection, product_id);
					textField_description.setText(description);
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Selecciona un producto.");
				}

			}
		});
		btn_seeData.setBounds(183, 23, 219, 26);
		panel_info.add(btn_seeData);
		btn_seeData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_seeData.setBackground(Color.WHITE);

		JPanel panel_order = new JPanel();
		panel_order.setLayout(null);
		panel_order.setBorder(null);
		panel_order.setBackground(new Color(240, 230, 140));
		panel_order.setBounds(581, 74, 339, 69);
		contentPane.add(panel_order);

		JLabel lbl_orderBy = new JLabel("Ordenar por:");
		lbl_orderBy.setBounds(10, 10, 115, 26);
		panel_order.add(lbl_orderBy);
		lbl_orderBy.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JPanel panel_order2 = new JPanel();
		panel_order2.setLayout(null);
		panel_order2.setBorder(null);
		panel_order2.setBackground(new Color(240, 230, 140));
		panel_order2.setBounds(661, 141, 180, 38);
		contentPane.add(panel_order2);

		JButton btn_order = new JButton("ORDENAR");
		btn_order.setBounds(26, 5, 132, 26);
		panel_order2.add(btn_order);
		btn_order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String order = (String) comboBox_categoryOrder.getSelectedItem();
				if (order.equals("Nombre del producto")) {
					order = "name";
				} else if (order.equals("Precio")) {
					order = "price";
				} else if (order.equals("Stock")) {
					order = "stock";
				}
				group.getSelection().getSelectedObjects();
				String asc = null;
				Object[] ascbtn = rdbtn_asc.getSelectedObjects();
				if (ascbtn == null) {
					asc = "DESC";
				} else {
					asc = "ASC";
				}
				// LA TABLA SE ACTUALIZA, SE CARGA AL SCROLLPANE, LA TABLA ANTERIOR SE ELIMINA Y
				// SE AGREGA LA NUEVA A LA VENTANA.

				table_products = productImpl.updateTableProductOrder(connection, order, asc);
				scrollPane_products = productImpl.showProducts(table_products);
				remove(contentPane.getComponentAt(37, 200));
				contentPane.add(scrollPane_products);
			}
		});
		btn_order.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_order.setBackground(Color.WHITE);

	}
}