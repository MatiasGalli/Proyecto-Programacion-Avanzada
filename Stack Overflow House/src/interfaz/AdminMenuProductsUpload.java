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

@SuppressWarnings("serial")
public class AdminMenuProductsUpload extends JFrame {

	private JPanel contentPane;
	private JTextField textField_name;
	private JTextField textField_price;
	private JTextField textField_stock;
	private JTextField textField_category;
	private JTextField textField_description;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuProductsUpload frame = new AdminMenuProductsUpload(null);
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
	public AdminMenuProductsUpload(SQL_Manager connection) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/assets/SOH_logo.png")));
		setAlwaysOnTop(true);
		setTitle("Subir Producto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_name = new JLabel("Nombre del producto");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_name.setBounds(35, 25, 100, 15);
		contentPane.add(lbl_name);

		JLabel lbl_price = new JLabel("Precio");
		lbl_price.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_price.setBounds(185, 25, 100, 15);
		contentPane.add(lbl_price);

		JLabel lbl_stock = new JLabel("Stock inicial");
		lbl_stock.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_stock.setBounds(35, 80, 100, 15);
		contentPane.add(lbl_stock);

		JLabel lbl_category = new JLabel("Categor\u00EDa");
		lbl_category.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_category.setBounds(185, 80, 100, 15);
		contentPane.add(lbl_category);

		JLabel lbl_description = new JLabel("Descripci\u00F3n");
		lbl_description.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_description.setBounds(35, 141, 100, 15);
		contentPane.add(lbl_description);

		textField_name = new JTextField();
		textField_name.setBounds(35, 41, 120, 19);
		contentPane.add(textField_name);
		textField_name.setColumns(10);

		textField_price = new JTextField();
		textField_price.setColumns(10);
		textField_price.setBounds(185, 41, 120, 19);
		contentPane.add(textField_price);

		textField_stock = new JTextField();
		textField_stock.setColumns(10);
		textField_stock.setBounds(35, 97, 50, 19);
		contentPane.add(textField_stock);

		textField_category = new JTextField();
		textField_category.setColumns(10);
		textField_category.setBounds(185, 97, 120, 19);
		contentPane.add(textField_category);

		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(Register.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 227, 36, 26);
		contentPane.add(lbl_SOHLogo);

		JButton btn_upload = new JButton("Crear");
		btn_upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean nameEmpty = textField_name.getText().equals("");
				boolean priceEmpty = textField_price.getText().equals("");
				boolean stockEmpty = textField_stock.getText().equals("");
				boolean categoryEmpty = textField_category.getText().equals("");
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
							JOptionPane.showMessageDialog(jFrame,
									"El espacio de precio tiene caracteres inv\u00E1lidos. Coloque s\u00F3lo cifras num\u00E9ricas.");
						} else {
							JFrame jFrame = new JFrame();
							JOptionPane.showMessageDialog(jFrame, "¡Producto creado exitosamente!");

							String name = textField_name.getText();
							String description = textField_description.getText();
							price = Float.parseFloat(textField_price.getText());
							String category = textField_category.getText();
							String categoryID;
							try {
								String sql = "select id from category where name = ?";
								PreparedStatement st;
								st = connection.getConnection().prepareStatement(sql);
								st.setString(1, category);
								ResultSet rs = st.executeQuery();
								rs.next();
								categoryID = rs.getString(1);
							} catch (SQLException e1) {
								categoryID = null;
							}
							
							String id = "-1";
							try {
								id = countProducts(connection);
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							try {
								insertProduct(connection, String.valueOf(Integer.parseInt(id) + 1), name, description, price, stock, categoryID);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							AdminMenu v4 = new AdminMenu(connection);
							v4.setVisible(true);
							dispose();
						}
					}
				} else {
					JFrame jFrame = new JFrame();
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
		btn_upload.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btn_upload.setBackground(new Color(255, 255, 255));
		btn_upload.setBounds(120, 230, 100, 21);
		contentPane.add(btn_upload);

		textField_description = new JTextField();
		textField_description.setColumns(10);
		textField_description.setBounds(35, 166, 270, 40);
		contentPane.add(textField_description);
	}

	public void insertProduct(SQL_Manager connection, String id, String name, String description, float price,
			int stock, String category_id) throws SQLException {
		try {
			String sql;

			sql = "INSERT INTO product (id,name,description,price,stock,category_id) values (?,?,?,?,?,?)";

			PreparedStatement st = connection.getConnection().prepareStatement(sql);

			st.setString(1, id);
			st.setString(2, name);
			st.setString(3, description);
			st.setFloat(4, price);
			st.setInt(5, stock);
			st.setString(6, category_id);

			st.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}
	
	public String countProducts(SQL_Manager connection) throws SQLException {
		
		String sql = "select id from product order by id desc limit 1";
		//FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		String id = rs.getString("id");
		return id;
	}
}
