package interfaz;

import java.awt.BorderLayout;
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
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class UserMenuCart extends JFrame {

	private JPanel contentPane;
	private JTextField textField_units;
	private JTable table_cart;
	private JScrollPane scrollPane_cart;
	private JLabel lbl_totalCart;

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

		table_cart = updateTable(connection);
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
		lbl_cart.setBounds(65, 63, 560, 26);
		contentPane.add(lbl_cart);

		JPanel panel_cart = new JPanel();
		panel_cart.setBorder(null);
		panel_cart.setBackground(new Color(240, 230, 140));
		panel_cart.setBounds(65, 128, 865, 404);
		contentPane.add(panel_cart);
		panel_cart.setLayout(null);

		JPanel panel_totalCart = new JPanel();
		panel_totalCart.setBackground(new Color(240, 230, 140));
		panel_totalCart.setBounds(104, 531, 250, 45);
		contentPane.add(panel_totalCart);
		panel_totalCart.setLayout(null);

		int totalAmount = totalPrice(connection);
		lbl_totalCart = new JLabel("Precio Total: $" + totalAmount);
		lbl_totalCart.setBackground(new Color(255, 215, 0));
		lbl_totalCart.setBounds(10, 10, 230, 25);
		lbl_totalCart.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_totalCart.setBorder(new LineBorder(new Color(0, 0, 128), 2, true));
		panel_totalCart.add(lbl_totalCart);

		textField_units = new JTextField();
		textField_units.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_units.setBounds(557, 542, 96, 26);
		contentPane.add(textField_units);
		textField_units.setColumns(10);

		JButton btn_buy = new JButton("COMPRAR CARRITO");
		btn_buy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_buy.setBackground(Color.WHITE);
		btn_buy.setBounds(634, 63, 215, 26);
		contentPane.add(btn_buy);

		JButton btn_delete = new JButton("BORRAR");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cant = Integer.parseInt(textField_units.getText());
				
			}
		});
		btn_delete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_delete.setBackground(Color.WHITE);
		btn_delete.setBounds(678, 542, 116, 26);
		contentPane.add(btn_delete);

		JLabel lbl_units = new JLabel("Unidades:");
		lbl_units.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_units.setBounds(463, 542, 106, 26);
		contentPane.add(lbl_units);
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
	public JTable updateTable(SQL_Manager connection) {
		try {
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "select name, price , amount, (p.price * pc.amount) as total from product_cart pc inner join product p on p.id = pc.product_id";
			st = connection.getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			String titles[] = { "Nombre del producto", "Precio", "Cantidad", "Total" };
			DefaultTableModel model = new DefaultTableModel(null, titles) {
				boolean[] columnEditables = new boolean[] { false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			String row[] = new String[4];
			while (rs.next()) {
				row[0] = rs.getString("name");
				row[1] = "$" + rs.getString("price");
				row[2] = rs.getString("amount");
				row[3] = "$" + rs.getString("total");
				model.addRow(row);
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(150);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(50);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(130);
			table.setRowHeight(26);
			table.setBounds(100, 160, 780, 350);
			return (table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	public int totalPrice(SQL_Manager connection) {
		int total = 0;
		try {
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "select (p.price * pc.amount) as total from product_cart pc inner join product p on p.id = pc.product_id";
			st = connection.getConnection().prepareStatement(sql);
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
	
	public int deleteProduct(SQL_Manager connection) {
		int total = 0;
		try {
			String sql;
			PreparedStatement st;
			ResultSet rs;
			sql = "update product set amount = ? where ";
			st = connection.getConnection().prepareStatement(sql);
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
}
