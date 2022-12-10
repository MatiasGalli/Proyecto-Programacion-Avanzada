package interfaz;

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
import javax.swing.table.DefaultTableModel;

import logica.SQL_Manager;

import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
					UserMenuCart frame = new UserMenuCart("", null);
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
	public UserMenuCart(String user, SQL_Manager connection) {
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
		try {
			user_rut = selectUserRut(connection, user);
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		table_cart = updateTable(connection,user_rut,user);
		scrollPane_cart = showCart(table_cart);
		contentPane.add(scrollPane_cart);

		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMenu v4 = new UserMenu(user, connection);
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

		int totalAmount = totalPrice(connection,user_rut);
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
				try {
					count = countProducts(connection);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				for (int i = 0; i < count; i++) {
					int new_id = -1;
					int product_stock = Integer.parseInt((String) table_cart.getValueAt(i, 3));
					String product_name = (String) table_cart.getValueAt(i, 1);
					try {
						new_id = countHistoryProductID(connection);
						int history_id = selectHistoryID(connection, user);
						insertHistoryProduct(connection, new_id + 1, history_id, product_name, product_stock);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
				int cart_id = 0;
				try {
					cart_id = selectCartId(connection, user);
					deleteCart(connection, cart_id);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame, "\u00A1Compra realizada!");
				UserMenu v4 = new UserMenu(user, connection);
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
						try {
							int cart_id = selectCartId(connection, user);
							updateProduct(connection, cart_id, product_id, units);
							int amount = selectStock(connection, cart_id, product_id);
							if (amount == 0) {
								deleteProduct(connection, cart_id, product_id);
								JFrame jFrame = new JFrame();
								jFrame.setAlwaysOnTop(true);
								JOptionPane.showMessageDialog(jFrame, "Producto eliminado del carrito.");
							}else {
								JFrame jFrame = new JFrame();
								jFrame.setAlwaysOnTop(true);
								JOptionPane.showMessageDialog(jFrame, "Cantidad eliminada.");
							}
							addStock(connection, units, product_id);
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						String user_rut = "";
						try {
							user_rut = selectUserRut(connection, user);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						table_cart = updateTable(connection,user_rut,user);
						scrollPane_cart = showCart(table_cart);
						remove(contentPane.getComponentAt(100, 160));
						contentPane.add(scrollPane_cart);
						int totalAmount = totalPrice(connection,user_rut);
						lbl_totalCart.setText("Precio Total: $" + totalAmount);
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
					try {
						product_remaining_stock = selectRemainingStock(connection,product_id);
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					if (product_remaining_stock < Integer.parseInt(textField_units.getText())) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "Dato no v\u00E1lido. No hay suficiente stock (Stock restante: "+ product_remaining_stock+")");
					} else {
						try {
							int cart_id = selectCartId(connection, user);
							updateProductAdd(connection, cart_id, product_id, units);
							JFrame jFrame = new JFrame();
							jFrame.setAlwaysOnTop(true);
							JOptionPane.showMessageDialog(jFrame, "Cantidad agregada.");
							
							substractStock(connection, units, product_id);
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						String user_rut = "";
						try {
							user_rut = selectUserRut(connection, user);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						table_cart = updateTable(connection,user_rut,user);
						scrollPane_cart = showCart(table_cart);
						remove(contentPane.getComponentAt(100, 160));
						contentPane.add(scrollPane_cart);
						int totalAmount = totalPrice(connection,user_rut);
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

	public JScrollPane showCart(JTable table) {
		try {
			JScrollPane scrollPane_cart = new JScrollPane(table);
			scrollPane_cart.setLocation(100, 160);
			scrollPane_cart.setSize(780, 350);
			scrollPane_cart.setViewportView(table);
			return scrollPane_cart;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	// Crea una tabla nueva dado ciertos parámetros.
	public JTable updateTable(SQL_Manager connection, String user_rut, String user) {
		try {
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql;
			PreparedStatement st;
			ResultSet rs;
			int cant = 0;
			sql = "select p.id, name, price , amount, (p.price * pc.amount) as total from product_cart pc inner join product p on p.id = pc.product_id inner join cart c on c.id = pc.cart_id inner join users u on u.rut = c.user_rut where u.rut = ? order by p.name asc";
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, user_rut);
			rs = st.executeQuery();
			String titles[] = { "ID", "Nombre del producto", "Precio", "Cantidad", "Total" };
			DefaultTableModel model = new DefaultTableModel(null, titles) {
				boolean[] columnEditables = new boolean[] { false, false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			String row[] = new String[5];
			while (rs.next()) {
				row[0] = Integer.toString(rs.getInt("id"));
				row[1] = rs.getString("name");
				row[2] = "$" + rs.getString("price");
				row[3] = rs.getString("amount");
				row[4] = "$" + rs.getString("total");
				model.addRow(row);
				cant++;
			}
			if (cant == 0) {
				UserMenu v4 = new UserMenu(user, connection);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(140);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(90);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setPreferredWidth(120);
			table.setRowHeight(26);
			table.setBounds(100, 160, 780, 350);
			return (table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	public int totalPrice(SQL_Manager connection, String user_rut) {
		int total = 0;
		try {
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "select (p.price * pc.amount) as total from product_cart pc inner join product p on p.id = pc.product_id inner join cart c on c.id = pc.cart_id inner join users u on u.rut = c.user_rut  where u.rut = ?";
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, user_rut);
			rs = st.executeQuery();
			while (rs.next()) {
				total += Integer.parseInt(rs.getString("total"));
			}
			return (total);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return 0;
	}

	public void updateProduct(SQL_Manager connection, int cart_id, int product_id, int amount) throws SQLException {

		String sql = "update product_cart set amount = amount - ? where cart_id = ? and product_id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, amount);
		st.setInt(2, cart_id);
		st.setInt(3, product_id);
		st.executeUpdate();
	}
	
	public void updateProductAdd(SQL_Manager connection, int cart_id, int product_id, int amount) throws SQLException {

		String sql = "update product_cart set amount = amount + ? where cart_id = ? and product_id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, amount);
		st.setInt(2, cart_id);
		st.setInt(3, product_id);
		st.executeUpdate();
	}

	public void addStock(SQL_Manager connection, int amount, int product_id) throws SQLException {

		String sql = "update product set stock = stock + ? where id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, amount);
		st.setInt(2, product_id);
		st.executeUpdate();
	}
	
	public void substractStock(SQL_Manager connection, int amount, int product_id) throws SQLException {

		String sql = "update product set stock = stock - ? where id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, amount);
		st.setInt(2, product_id);
		st.executeUpdate();
	}

	public void deleteProduct(SQL_Manager connection, int cart_id, int product_id) throws SQLException {

		String sql = "delete from product_cart where cart_id = ? and product_id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, cart_id);
		st.setInt(2, product_id);
		st.executeUpdate();
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

	public int selectStock(SQL_Manager connection, int cart_id, int product_id) throws SQLException {
		int cant = -1;
		String sql = "select amount from product_cart where cart_id = ? and product_id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, cart_id);
		st.setInt(2, product_id);
		ResultSet rs = st.executeQuery();
		rs.next();
		cant = rs.getInt("amount");
		return cant;
	}
	
	public int selectRemainingStock(SQL_Manager connection,int product_id) throws SQLException {
		int cant = -1;
		String sql = "select stock from product where id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, product_id);
		ResultSet rs = st.executeQuery();
		rs.next();
		cant = rs.getInt("stock");
		return cant;
	}

	public int countHistoryID(SQL_Manager connection) throws SQLException {

		String sql = "select id from product order by id desc limit 1";
		int id = 0;
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			id = rs.getInt("id");
		}
		return id;
	}

	public int countHistoryProductID(SQL_Manager connection) throws SQLException {

		String sql = "select id from history_product order by id desc limit 1";
		int id = 0;
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			id = rs.getInt("id");
		}
		return id;
	}

	public void insertHistoryProduct(SQL_Manager connection, int id, int history_id, String product_name, int amount)
			throws SQLException {

		String sql = "Insert into history_product(id, history_id, product_name, amount, purchase_date) values (?,?,?,?,current_timestamp)";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, id);
		st.setInt(2, history_id);
		st.setString(3, product_name);
		st.setInt(4, amount);
		st.executeUpdate();
	}

	public int selectHistoryID(SQL_Manager connection, String username) throws SQLException {
		String user_rut = "";
		String sql = "select rut from users where username = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		rs.next();
		user_rut = rs.getString("rut");

		int id = -1;
		sql = "select history_id from shopping_history where user_rut = ?";

		st = connection.getConnection().prepareStatement(sql);
		st.setString(1, user_rut);
		rs = st.executeQuery();
		rs.next();
		id = rs.getInt("history_id");
		return id;
	}

	public int countProducts(SQL_Manager connection) throws SQLException {

		String sql = "select count(*) as id from product_cart";

		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		int id = rs.getInt("id");
		return id;
	}

	public void deleteCart(SQL_Manager connection, int cart_id) throws SQLException {

		String sql = "delete from product_cart where cart_id = ?";

		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, cart_id);
		st.executeUpdate();
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
