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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class AdminMenuProductsEdit extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

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
		btn_seeData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_seeData.setBackground(Color.WHITE);
		btn_seeData.setBounds(291, 86, 180, 26);
		contentPane.add(btn_seeData);
		
		JLabel lbl_name = new JLabel("Nombre:");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name.setBounds(52, 144, 84, 26);
		contentPane.add(lbl_name);
		
		JLabel lbl_name_1 = new JLabel("Precio: $");
		lbl_name_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name_1.setBounds(52, 180, 93, 26);
		contentPane.add(lbl_name_1);
		
		JLabel lbl_name_1_1 = new JLabel("Stock:");
		lbl_name_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name_1_1.setBounds(364, 180, 69, 26);
		contentPane.add(lbl_name_1_1);
		
		JLabel lbl_name_1_1_1 = new JLabel("Descripci\u00F3n:");
		lbl_name_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name_1_1_1.setBounds(52, 216, 118, 26);
		contentPane.add(lbl_name_1_1_1);
		
		JLabel lbl_name_1_1_1_1 = new JLabel("Categor\u00EDa:");
		lbl_name_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name_1_1_1_1.setBounds(190, 264, 118, 26);
		contentPane.add(lbl_name_1_1_1_1);
		
		textField = new JTextField();
		textField.setBounds(146, 144, 561, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(146, 180, 130, 26);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(427, 180, 101, 26);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(175, 216, 532, 26);
		contentPane.add(textField_3);
		
		JComboBox comboBox_products_1 = new JComboBox();
		comboBox_products_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox_products_1.setBounds(291, 266, 280, 26);
		contentPane.add(comboBox_products_1);
		
		JLabel lbl_name_1_1_1_1_1 = new JLabel("Imagen:");
		lbl_name_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_name_1_1_1_1_1.setBounds(77, 350, 93, 26);
		contentPane.add(lbl_name_1_1_1_1_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AdminMenuProductsEdit.class.getResource("/assets/no_image.png")));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(175, 300, 130, 130);
		contentPane.add(lblNewLabel);
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
		String sql = "select name from product";
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
}
