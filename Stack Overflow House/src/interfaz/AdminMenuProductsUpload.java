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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class AdminMenuProductsUpload extends JFrame {

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
					AdminMenuProductsUpload frame = new AdminMenuProductsUpload("",null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param rut 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AdminMenuProductsUpload(String rut, SQL_Manager connection) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/assets/SOH_logo.png")));
		setAlwaysOnTop(true);
		setTitle("Subir Producto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_name = new JLabel("Nombre del producto");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_name.setBounds(35, 49, 170, 15);
		contentPane.add(lbl_name);

		JLabel lbl_price = new JLabel("Precio");
		lbl_price.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_price.setBounds(431, 49, 100, 15);
		contentPane.add(lbl_price);

		JLabel lbl_stock = new JLabel("Stock inicial");
		lbl_stock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_stock.setBounds(431, 116, 100, 15);
		contentPane.add(lbl_stock);

		JLabel lbl_category = new JLabel("Categor\u00EDa");
		lbl_category.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_category.setBounds(35, 110, 100, 26);
		contentPane.add(lbl_category);

		JLabel lbl_description = new JLabel("Descripci\u00F3n");
		lbl_description.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_description.setBounds(35, 190, 100, 15);
		contentPane.add(lbl_description);

		textField_name = new JTextField();
		textField_name.setBounds(35, 74, 355, 26);
		contentPane.add(textField_name);
		textField_name.setColumns(10);

		textField_price = new JTextField();
		textField_price.setColumns(10);
		textField_price.setBounds(430, 74, 150, 26);
		contentPane.add(textField_price);

		textField_stock = new JTextField();
		textField_stock.setColumns(10);
		textField_stock.setBounds(431, 138, 149, 26);
		contentPane.add(textField_stock);

		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(Register.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 287, 36, 26);
		contentPane.add(lbl_SOHLogo);

		JComboBox comboBox_category = new JComboBox();
		comboBox_category.setFont(new Font("Tahoma", Font.PLAIN, 16));
		int cant = 10;
		try {
			cant = countCategories(connection);
		} catch (NumberFormatException | SQLException e3) {
			e3.printStackTrace();
		}
		String[] list = new String[cant];
		String[] values = null;
		try {
			values = getCategories(connection, list);
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		comboBox_category.setModel(new DefaultComboBoxModel(values));
		comboBox_category.setBounds(35, 137, 355, 26);
		contentPane.add(comboBox_category);

		JButton btn_upload = new JButton("Crear");
		btn_upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean nameEmpty = textField_name.getText().equals("");
				boolean priceEmpty = textField_price.getText().equals("");
				boolean stockEmpty = textField_stock.getText().equals("");
				boolean categoryEmpty = ((String) comboBox_category.getSelectedItem()).equals("");
				boolean descriptionEmpty = textField_description.getText().equals("");

				if (!(nameEmpty || priceEmpty || stockEmpty || categoryEmpty || descriptionEmpty)) {

					int stock = 0;
					try {
						stock = Integer.parseInt(textField_stock.getText());
					} catch (Exception p) {
						stockEmpty = true;
					}

					if (stockEmpty) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame,
								"El espacio de stock tiene caracteres inv\u00E1lidos. Coloque s\u00F3lo cifras num\u00E9ricas.");
					} else {
						float price = 0;
						try {
							price = Float.parseFloat(textField_price.getText());
						} catch (Exception p) {
							priceEmpty = true;
						}

						if (priceEmpty) {
							JFrame jFrame = new JFrame();
							jFrame.setAlwaysOnTop(true);
							JOptionPane.showMessageDialog(jFrame,
									"El espacio de precio tiene caracteres inv\u00E1lidos. Coloque s\u00F3lo cifras num\u00E9ricas.");
						} else {
							

							String name = textField_name.getText();
							String description = textField_description.getText();
							price = Float.parseFloat(textField_price.getText());
							String category = (String) comboBox_category.getSelectedItem();
							int categoryID = 0;
							try {
								String sql = "select id from category where name = ?";
								PreparedStatement st;
								st = connection.getConnection().prepareStatement(sql);
								st.setString(1, category);
								ResultSet rs = st.executeQuery();
								if (rs.next()) {
									categoryID = rs.getInt(1);
								}
								boolean exist = selectNames(connection,name);
								if (exist) {
									JFrame jFrame = new JFrame();
									jFrame.setAlwaysOnTop(true);
									JOptionPane.showMessageDialog(jFrame, "Este nombre de producto ya existe");
								}else {
									int id = 0;
									id = countProductsID(connection);
									insertProduct(connection, (id + 1), name, description,price, stock, categoryID);
									JFrame jFrame = new JFrame();
									jFrame.setAlwaysOnTop(true);
									JOptionPane.showMessageDialog(jFrame, "¡Producto creado exitosamente!");

									AdminMenu v4 = new AdminMenu(rut, connection);
									v4.setLocationRelativeTo(null);
									v4.setVisible(true);
									dispose();
								}
								
								
							} catch (SQLException e1) {
								categoryID = 0;
							}
						}
					}
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					String x = "";
					if (nameEmpty)
						x = x + "Nombre del producto, ";
					if (priceEmpty)
						x = x + "Precio, ";
					if (stockEmpty)
						x = x + "Stock, ";
					if (categoryEmpty)
						x = x + "Categoría, ";
					if (descriptionEmpty)
						x = x + "Descripción, ";

					x = x.substring(0, x.length() - 2);
					
					JOptionPane.showMessageDialog(jFrame, "Los siguientes espacios no tienen datos: " + x + ".");
				}
			}

		});
		btn_upload.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btn_upload.setBackground(new Color(255, 255, 255));
		btn_upload.setBounds(290, 285, 88, 26);
		contentPane.add(btn_upload);

		textField_description = new JTextField();
		textField_description.setColumns(10);
		textField_description.setBounds(35, 215, 545, 52);
		contentPane.add(textField_description);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu v4 = new AdminMenu(rut,connection);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(AdminMenuProductsUpload.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
	}

	public void insertProduct(SQL_Manager connection, int id, String name, String description, float price,
			int stock, int category_id) throws SQLException {
		try {
			String sql;

			sql = "INSERT INTO product (id,name,description,price,stock,category_id) values (?,?,?,?,?,?)";

			PreparedStatement st = connection.getConnection().prepareStatement(sql);

			st.setInt(1, id);
			st.setString(2, name);
			st.setString(3, description);
			st.setFloat(4, price);
			st.setInt(5, stock);
			st.setInt(6, category_id);

			st.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}

	public int countProductsID(SQL_Manager connection) throws SQLException {

		String sql = "select id from product order by id desc limit 1";
		int id = 0;
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			id = rs.getInt("id");
		}
		return id;
	}
	
	public int countCategories(SQL_Manager connection) throws SQLException {

		String sql = "select count(*) as id from category";

		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		int id = rs.getInt("id");
		return id;
	}

	public String[] getCategories(SQL_Manager connection, String[] list) throws SQLException {

		String[] values = list;
		String sql = "select name from category";
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
	
	public boolean selectNames(SQL_Manager connection, String newName) throws SQLException {
		boolean exist = false;
		String sql = "select name from product";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			if (rs.getString("name").equals(newName)) {
				exist = true;
			}
		}
		return exist;
	}
}
