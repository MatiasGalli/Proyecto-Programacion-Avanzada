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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;

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
	private JTextField textField_commentary;
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
					if (product_stock < Integer.parseInt(textField_units.getText())) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "No hay stock suficiente para las unidades indicadas.");
					}
					/*
					 * try { //addCart(connection, product_stock); String prueba =""; } catch
					 * (SQLException e1) { e1.printStackTrace(); }
					 */
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
				UserMenuCart v8 = new UserMenuCart(user, connection);
				v8.setLocationRelativeTo(null);
				v8.setVisible(true);
				dispose();
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

		JLabel lbl_category = new JLabel("en la categor\u00EDa:");
		lbl_category.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_category.setBounds(565, 82, 144, 26);
		contentPane.add(lbl_category);

		JComboBox comboBox_categorySearch = new JComboBox();
		comboBox_categorySearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_categorySearch.setBounds(680, 82, 216, 26);
		comboBox_categorySearch.setModel(new DefaultComboBoxModel(new String[] { "Nombre del producto", "Categoría" }));
		contentPane.add(comboBox_categorySearch);

		JPanel panel_search1 = new JPanel();
		panel_search1.setBorder(null);
		panel_search1.setBackground(new Color(240, 230, 140));
		panel_search1.setBounds(36, 74, 880, 38);
		contentPane.add(panel_search1);
		panel_search1.setLayout(null);

		JPanel panel_search2 = new JPanel();
		panel_search2.setBorder(null);
		panel_search2.setBackground(new Color(240, 230, 140));
		panel_search2.setBounds(36, 110, 180, 38);
		contentPane.add(panel_search2);
		panel_search2.setLayout(null);

		JButton btn_search = new JButton("BUSCAR");
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
		btn_search.setBounds(20, 7, 140, 26);
		panel_search2.add(btn_search);
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

		JLabel lbl_commentary = new JLabel("Comentario:");
		lbl_commentary.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_commentary.setBounds(47, 517, 116, 26);
		contentPane.add(lbl_commentary);

		textField_commentary = new JTextField();
		textField_commentary.setEditable(false);
		textField_commentary.setText((String) null);
		textField_commentary.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_commentary.setColumns(10);
		textField_commentary.setBounds(141, 518, 755, 26);
		contentPane.add(textField_commentary);

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
		panel_info.setBounds(36, 417, 880, 136);
		contentPane.add(panel_info);

		JLabel lbl_advice = new JLabel(
				"* Para ver los comentarios de un producto, primero debes presionar VER DATOS seleccionando dicho producto y luego seleccionar el usuario desde la caja correspondiente");
		lbl_advice.setBounds(61, 39, 809, 26);
		panel_info.add(lbl_advice);
		lbl_advice.setFont(new Font("Tahoma", Font.ITALIC, 10));

		JComboBox comboBox_commentary = new JComboBox();
		comboBox_commentary.setBounds(372, 11, 235, 26);
		panel_info.add(comboBox_commentary);
		comboBox_commentary.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JButton btn_seeComment = new JButton("VER COMENTARIO");
		btn_seeComment.setBounds(617, 10, 210, 26);
		panel_info.add(btn_seeComment);
		btn_seeComment.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_seeComment.setBackground(Color.WHITE);

		JLabel lbl_users = new JLabel("Usuarios:\r\n");
		lbl_users.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_users.setBounds(298, 12, 116, 26);
		panel_info.add(lbl_users);

		JRadioButton rdbtn_asc = new JRadioButton("Ascendente");
		rdbtn_asc.setSelected(true);
		rdbtn_asc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtn_asc.setBackground((Color) null);
		rdbtn_asc.setBounds(302, 155, 140, 15);
		contentPane.add(rdbtn_asc);

		JRadioButton rdbtn_desc = new JRadioButton("Descendente");
		rdbtn_desc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtn_desc.setBackground((Color) null);
		rdbtn_desc.setBounds(444, 156, 150, 15);
		contentPane.add(rdbtn_desc);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtn_asc);
		group.add(rdbtn_desc);

		JLabel lbl_orderBy = new JLabel("Ordenar por:");
		lbl_orderBy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_orderBy.setBounds(267, 123, 115, 26);
		contentPane.add(lbl_orderBy);

		JComboBox comboBox_categoryOrder = new JComboBox();
		comboBox_categoryOrder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_categoryOrder
				.setModel(new DefaultComboBoxModel(new String[] { "Nombre del producto", "Precio", "Stock" }));
		comboBox_categoryOrder.setBounds(392, 124, 230, 26);
		contentPane.add(comboBox_categoryOrder);

		JButton btn_order = new JButton("ORDENAR");
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
		btn_order.setBounds(661, 135, 132, 26);
		contentPane.add(btn_order);

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

		JButton btn_seeData = new JButton("VER DATOS");
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
		btn_seeData.setBounds(10, 10, 210, 26);
		panel_info.add(btn_seeData);
		btn_seeData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_seeData.setBackground(Color.WHITE);

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
			String sql, category;
			PreparedStatement st;
			ResultSet rs;
			// EDITAR CONSULTA (INNER JOIN)
			sql = "Select * from product where stock <> 0 order by id asc";
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
				category = getCategories(connection, rs.getInt("category_id"));
				row[4] = category;
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
		sql = "Select * from product where " + category + " = ? and stock <> 0;";
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
				category = getCategories(connection, rs.getInt("category_id"));
				row[4] = category;
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
			sql = "Select * from product where stock <> 0 order by " + category + " " + asc;
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
				category = getCategories(connection, rs.getInt("category_id"));
				row[4] = category;
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
		connection.getConnection().prepareStatement(sql);
		rs.next();
		String description = rs.getString("description");

		textField_description.setText(description);

	}

	public void addProduct(SQL_Manager connection, int cart_id, int product_id, int amount) throws SQLException {

		String sql = "Insert into product_cart(cart_id, product_id, amount) values (?,?,?)";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, cart_id);
		st.setInt(2, product_id);
		st.setInt(3, amount);
		st.executeUpdate();

	}
}
