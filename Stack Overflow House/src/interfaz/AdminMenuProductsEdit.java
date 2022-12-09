package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.SQL_Manager;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class AdminMenuProductsEdit extends JFrame {

	private JPanel contentPane;
	private JTextField textField_name;
	private JTextField textField_price;
	private JTextField textField_stock;
	private JTextField textField_description;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuProductsEdit frame = new AdminMenuProductsEdit(null);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AdminMenuProductsEdit(SQL_Manager connection) {
		setBackground(new Color(255, 250, 205));
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenuProductsEdit.class.getResource("/assets/SOH_logo.png")));
		setTitle("Editar Productos\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu v4 = new AdminMenu(connection);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(AdminMenuProductsEdit.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(AdminMenuProductsEdit.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 407, 36, 26);
		contentPane.add(lbl_SOHlogo);
		
		JLabel lbl_select = new JLabel("Seleccione el producto que desea editar:");
		lbl_select.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_select.setBounds(52, 50, 381, 26);
		contentPane.add(lbl_select);
		
		JComboBox comboBox_products = new JComboBox();
		comboBox_products.setFont(new Font("Tahoma", Font.PLAIN, 16));
		int cant = 10;
		try {
			cant = countProducts(connection);
		} catch (NumberFormatException | SQLException e3) {
			e3.printStackTrace();
		}
		String[] list = new String[cant];
		String[] values = null;
		try {
			values = getProducts(connection, list);
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		comboBox_products.setModel(new DefaultComboBoxModel(values));
		comboBox_products.setBounds(427, 51, 280, 26);
		contentPane.add(comboBox_products);
		
		JButton btn_seeData = new JButton("VER DATOS");
		btn_seeData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String product_name = (String) comboBox_products.getSelectedItem();
				try {
					seeData(connection,product_name);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_seeData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_seeData.setBackground(Color.WHITE);
		btn_seeData.setBounds(290, 109, 180, 26);
		contentPane.add(btn_seeData);
		
		JLabel lbl_name = new JLabel("Nombre:");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name.setBounds(52, 177, 84, 26);
		contentPane.add(lbl_name);
		
		JLabel lbl_price = new JLabel("Precio: $");
		lbl_price.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_price.setBounds(52, 231, 93, 26);
		contentPane.add(lbl_price);
		
		JLabel lbl_stock = new JLabel("Stock:");
		lbl_stock.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_stock.setBounds(360, 231, 69, 26);
		contentPane.add(lbl_stock);
		
		JLabel lbl_description = new JLabel("Descripci\u00F3n:");
		lbl_description.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_description.setBounds(52, 282, 118, 26);
		contentPane.add(lbl_description);
		
		textField_name = new JTextField();
		textField_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_name.setText("Nombre del producto");
		textField_name.setBounds(146, 177, 561, 26);
		contentPane.add(textField_name);
		textField_name.setColumns(10);
		
		textField_price = new JTextField();
		textField_price.setText("0");
		textField_price.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_price.setColumns(10);
		textField_price.setBounds(142, 231, 130, 26);
		contentPane.add(textField_price);
		
		textField_stock = new JTextField();
		textField_stock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_stock.setText("0");
		textField_stock.setColumns(10);
		textField_stock.setBounds(427, 231, 101, 26);
		contentPane.add(textField_stock);
		
		textField_description = new JTextField();
		textField_description.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_description.setText("Descripci\u00F3n del producto\r\n");
		textField_description.setColumns(10);
		textField_description.setBounds(175, 282, 532, 26);
		contentPane.add(textField_description);
		
		JButton btn_editProduct = new JButton("EDITAR PRODUCTO");
		btn_editProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String product_name = (String) comboBox_products.getSelectedItem();
				String name = textField_name.getText();
				float price = Float.parseFloat(textField_price.getText());
				int stock = Integer.parseInt(textField_stock.getText());
				String description = (String) textField_description.getText();
				
				try {
					editProduct(connection,product_name,name,price,stock,description);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_editProduct.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_editProduct.setBackground(Color.WHITE);
		btn_editProduct.setBounds(265, 384, 227, 26);
		contentPane.add(btn_editProduct);
	}
	
	public int countProducts(SQL_Manager connection) throws SQLException {

		String sql = "select count(*) as cant from product";

		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		int cant = rs.getInt("cant");
		return cant;
	}

	public String[] getProducts(SQL_Manager connection, String[] list) throws SQLException {

		String[] values = list;
		String sql = "select name from product order by name asc";
		// VERIFICAR EL CASO DE QUE NO EXISTAN CATEGORIAS
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		int cant = 0;
		while (rs.next()) {
			values[cant] = rs.getString("name");
			cant++;
		}
		return values;
	}
	
	public void seeData(SQL_Manager connection, String product_name) throws SQLException {
		String sql = "select id from product where name = ?";
		PreparedStatement st;
		ResultSet rs;
		
		st = connection.getConnection().prepareStatement(sql);
		st.setString(1, product_name);
		rs = st.executeQuery();
		rs.next();
		int product_id = rs.getInt("id");
		
		
		sql = "select price,stock,description from product where id = ?";
		st = connection.getConnection().prepareStatement(sql);
		st.setInt(1, product_id);
		rs = st.executeQuery();
		connection.getConnection().prepareStatement(sql);
		rs.next();
		float price = rs.getFloat("price");
		int stock = rs.getInt("stock");
		String description = rs.getString("description");
		
		textField_name.setText(product_name);
		textField_price.setText(Float.toString(price));
		textField_stock.setText(Integer.toString(stock));
		textField_description.setText(description);
		
	}
	
	public void editProduct(SQL_Manager connection,String product_name,String name,float price, int stock, String description) throws SQLException {
		String sql = "select id from product where name = ?";
		PreparedStatement st;
		ResultSet rs;
		
		st = connection.getConnection().prepareStatement(sql);
		st.setString(1, product_name);
		rs = st.executeQuery();
		rs.next();
		int product_id = rs.getInt("id");
		
		sql = "update product set name = ?, price = ?, stock = ?, description = ? where id = ?";
		
		st = connection.getConnection().prepareStatement(sql);
		st.setString(1, name);
		st.setFloat(2, price);
		st.setInt(3, stock);
		st.setString(4, description);
		st.setInt(5, product_id);
		st.executeUpdate();
		
		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jFrame,"Producto actualizado.");
		
		AdminMenu v4 = new AdminMenu(connection);
		v4.setLocationRelativeTo(null);
		v4.setVisible(true);
		dispose();

	}
}
