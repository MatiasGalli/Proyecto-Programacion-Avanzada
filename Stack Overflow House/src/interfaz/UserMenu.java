package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import logica.SQL_Manager;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
					UserMenu frame = new UserMenu("", null);
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
	public UserMenu(String user, SQL_Manager connection) {
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

		table_products = updateTable(connection);
		scrollPane_products = showProducts(table_products);
		contentPane.add(scrollPane_products);

		JButton btn_userAccount = new JButton("CUENTA DE USUARIO");
		btn_userAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMenuAccount v12 = new UserMenuAccount(user, connection);
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
						try {
							int cart_id = selectCartId(connection, user);
							boolean exist = productExists(connection, cart_id, product_id);
							if (exist) {
								updateProduct(connection, cart_id, product_id, units);
							} else {
								addProduct(connection, cart_id, product_id, units);
							}
							substractStock(connection, units, product_id);
							JFrame jFrame = new JFrame();
							jFrame.setAlwaysOnTop(true);
							JOptionPane.showMessageDialog(jFrame, "Producto añadido al carrito.");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						table_products = updateTable(connection, "name", "asc");
						scrollPane_products = showProducts(table_products);
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
				try {
					cart_id = selectCartId(connection, user);
					exist = productInCart(connection, cart_id);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (exist) {
					UserMenuCart v8 = new UserMenuCart(user, connection);
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
				Login v1 = new Login(connection);
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
		comboBox_categorySearch.setModel(new DefaultComboBoxModel(new String[] { "Nombre del producto", "Categoría" }));

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
				} else if (categorySearch.equals("Categoría")) {
					categorySearch = "category_id";
				}
				String search = textField_search.getText();
				try {
					table_products = search(connection, categorySearch, search);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				scrollPane_products = showProducts(table_products);
				remove(contentPane.getComponentAt(37, 200));
				contentPane.add(scrollPane_products);
			}
		});
		btn_search.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_search.setBackground(Color.WHITE);

		JLabel lbl_description = new JLabel("Descripción:");
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
					try {
						product_id = Integer.parseInt((String) table_products.getValueAt(row, 0));
						exist = productComment(connection, product_id);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (exist) {
						product_id = Integer.parseInt((String) table_products.getValueAt(row, 0));
						UserMenuComments v14 = new UserMenuComments(user, product_id, connection);
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

		JButton btn_seeData = new JButton("VER DESCRIPCIÓN");
		btn_seeData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_products.getSelectedRow();
				if (row != -1) {
					int product_id = Integer.parseInt((String) table_products.getValueAt(row, 0));
					try {
						seeData(connection, product_id);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
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

				table_products = updateTable(connection, order, asc);
				scrollPane_products = showProducts(table_products);
				remove(contentPane.getComponentAt(37, 200));
				contentPane.add(scrollPane_products);
			}
		});
		btn_order.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_order.setBackground(Color.WHITE);

	}

	public Object[][] getProducts(SQL_Manager connection, Object[][] values) {

		Object[][] list = values;
		String sql, category;
		Statement st;
		ResultSet rs;
		sql = "Select * from product where stock <> 0 ";

		try {
			st = connection.getConnection().createStatement();
			rs = st.executeQuery(sql);
			int cant = 0;
			while (rs.next()) {
				list[cant][0] = "   " + rs.getString("id");
				list[cant][1] = rs.getString("name");
				list[cant][2] = "$" + rs.getString("price");
				list[cant][3] = rs.getString("stock");
				category = getCategories(connection, rs.getInt("category_id"));
				list[cant][4] = category;
				cant++;
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}

	// Crea un JScrollPane a partir de una tabla.
	public JScrollPane showProducts(JTable table) {
		try {
			JScrollPane scrollPane_userInfo = new JScrollPane(table);
			scrollPane_userInfo.setLocation(37, 200);
			scrollPane_userInfo.setSize(880, 204);
			scrollPane_userInfo.setViewportView(table);
			return scrollPane_userInfo;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	// Crea una tabla nueva dado ciertos parámetros.
	public JTable updateTable(SQL_Manager connection) {
		try {
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql;
			PreparedStatement st;
			ResultSet rs;
			// EDITAR CONSULTA (INNER JOIN)
			sql = "Select p.id, p.name, p.price, p.stock, c.name as category_name from product p inner join category c on c.id = p.category_id where stock <> 0 order by name asc";
			st = connection.getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			String titles[] = { "ID", "Nombre del producto", "Precio", "Stock", "Categoría" };
			DefaultTableModel model = new DefaultTableModel(null, titles) {
				boolean[] columnEditables = new boolean[] { false, false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			String row[] = new String[6];
			while (rs.next()) {
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = "$ " + rs.getString("price");
				row[3] = rs.getString("stock");
				row[4] = rs.getString("category_name");
				model.addRow(row);

			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(220);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setPreferredWidth(220);
			table.setRowHeight(26);
			table.setBounds(37, 200, 880, 204);
			return (table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	public String getCategories(SQL_Manager connection, int id) throws SQLException {

		String sql = "select name from category where id = ?";
		PreparedStatement st;
		st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		rs.next();
		String name = rs.getString(1);
		return name;
	}

	public JTable search(SQL_Manager connection, String category, String search) throws SQLException {
		int product_id = 0;
		if (category.equals("category_id")) {
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "Select id from category where name = ?;";
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, search);
			rs = st.executeQuery();
			while (rs.next()) {
				product_id = rs.getInt("id");
			}
		}
		JTable table = new JTable();
		table.setShowVerticalLines(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setBorder(null);
		String sql;
		boolean stBoolean = false;
		PreparedStatement st;
		ResultSet rs = null;
		int cont = 0;
		sql = "Select p.id, p.name, p.price, p.stock, c.name as category_name from product p inner join category c on c.id = p.category_id where p." + category + " = ? and stock <> 0;";
		st = connection.getConnection().prepareStatement(sql);
		if (product_id == 0) {
			st.setString(1, search);
		} else {
			st.setInt(1, product_id);
		}
		try {
			rs = st.executeQuery();
		} catch (Exception e) {
			cont = 0;
			stBoolean = true;
		}
		String titles[] = { "ID", "Nombre del producto", "Precio", "Stock", "Categoría" };
		DefaultTableModel model = new DefaultTableModel(null, titles) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		String row[] = new String[6];
		if (!stBoolean) {
			while (rs.next()) {
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = "$ " + rs.getString("price");
				row[3] = rs.getString("stock");
				row[4] = rs.getString("category_name");
				model.addRow(row);
				cont++;
			}
		}
		if (cont == 0) {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No hay coincidencias en la búsqueda");
		} else {
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(220);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setPreferredWidth(220);
			table.setRowHeight(26);
			table.setBounds(37, 200, 880, 204);
			return (table);
		}
		return table_products;
	}

	public JTable updateTable(SQL_Manager connection, String category, String asc) {
		try {
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "Select p.id, p.name, p.price, p.stock, c.name as category_name from product p inner join category c on c.id = p.category_id where p.stock <> 0 order by " + category + " " + asc;
			st = connection.getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			String titles[] = { "ID", "Nombre del producto", "Precio", "Stock", "Categoría" };
			DefaultTableModel model = new DefaultTableModel(null, titles) {
				boolean[] columnEditables = new boolean[] { false, false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			String row[] = new String[5];
			while (rs.next()) {
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = "$ " + rs.getString("price");
				row[3] = rs.getString("stock");
				row[4] = rs.getString("category_name");
				model.addRow(row);

			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(220);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setPreferredWidth(220);
			table.setRowHeight(26);
			table.setBounds(37, 200, 880, 204);
			return (table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	public void seeData(SQL_Manager connection, int product_id) throws SQLException {

		String sql = "select description from product where id = ?";
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, product_id);
		ResultSet rs = st.executeQuery();
		rs.next();
		String description = rs.getString("description");

		textField_description.setText(description);

	}

	public int selectCartId(SQL_Manager connection, String username) throws SQLException {
		int cart_id = -1;
		String sql = "select id from cart where cart.user_rut = (select rut from users where username = ?)";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		rs.next();
		cart_id = rs.getInt("id");
		return cart_id;
	}

	public void addProduct(SQL_Manager connection, int cart_id, int product_id, int amount) throws SQLException {

		String sql = "Insert into product_cart(cart_id, product_id, amount) values (?,?,?)";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, cart_id);
		st.setInt(2, product_id);
		st.setInt(3, amount);
		st.executeUpdate();
	}

	public void substractStock(SQL_Manager connection, int amount, int product_id) throws SQLException {

		String sql = "update product set stock = stock - ? where id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, amount);
		st.setInt(2, product_id);
		st.executeUpdate();
	}

	public boolean productExists(SQL_Manager connection, int cart_id, int product_id) throws SQLException {

		boolean exist = false;
		String sql = "select product_id from product_cart where product_id = ? and cart_id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, product_id);
		st.setInt(2, cart_id);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			exist = true;
		}
		return exist;
	}

	public void updateProduct(SQL_Manager connection, int cart_id, int product_id, int amount) throws SQLException {

		String sql = "update product_cart set amount = amount + ? where cart_id = ? and product_id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, amount);
		st.setInt(2, cart_id);
		st.setInt(3, product_id);
		st.executeUpdate();
	}

	public boolean productInCart(SQL_Manager connection, int cart_id) throws SQLException {

		boolean exist = false;
		String sql = "select product_id from product_cart where cart_id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, cart_id);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			exist = true;
		}
		return exist;
	}
	
	public boolean productComment(SQL_Manager connection, int product_id) throws SQLException {

		boolean exist = false;
		String sql = "select c.product_id from commentary c inner join product p on p.id = c.product_id  inner join users u on u.rut = c.user_rut where c.product_id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, product_id);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			exist = true;
		}
		return exist;
	}

	public String selectUserRut(SQL_Manager connection, String username) throws SQLException {
		String rut = "";
		String sql = "select rut from users where username = ?";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			rut = rs.getString("rut");
		}
		return rut;
	}
}
