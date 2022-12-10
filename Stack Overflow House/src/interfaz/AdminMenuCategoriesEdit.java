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
import javax.swing.JComboBox;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AdminMenuCategoriesEdit extends JFrame {

	private JPanel contentPane;
	private JTextField textField_name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuCategoriesEdit frame = new AdminMenuCategoriesEdit("",null);
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
	public AdminMenuCategoriesEdit(String rut, SQL_Manager connection) {
		setBackground(new Color(255, 250, 205));
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenuCategoriesEdit.class.getResource("/assets/SOH_logo.png")));
		setTitle("Editar Categor\u00EDa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(AdminMenuCategoriesEdit.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 287, 36, 26);
		contentPane.add(lbl_SOHlogo);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu v4 = new AdminMenu(rut,connection);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(AdminMenuCategoriesEdit.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JComboBox comboBox_category = new JComboBox();
		comboBox_category.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox_category.setBounds(65, 96, 495, 26);
		int cant = 0;
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
		contentPane.add(comboBox_category);
		
		JButton btn_delete = new JButton("EDITAR");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String category_name = (String) comboBox_category.getSelectedItem();
				String name = textField_name.getText();
				try {
					boolean exist = selectNames(connection,name);
					if(name.equals("")) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame,"Debes ingresar un nombre para editar");
					}else if (exist) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame,"Este nombre de categoría ya existe");
					}else {
						editCategory(rut, connection,category_name, name);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_delete.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btn_delete.setBackground(Color.WHITE);
		btn_delete.setBounds(235, 220, 150, 30);
		contentPane.add(btn_delete);
		
		JLabel lbl_select = new JLabel("Seleccione una categoria de la lista:");
		lbl_select.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_select.setBounds(65, 60, 359, 26);
		contentPane.add(lbl_select);
		
		JLabel lbl_newName = new JLabel("Nuevo nombre:");
		lbl_newName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_newName.setBounds(65, 159, 150, 26);
		contentPane.add(lbl_newName);
		
		textField_name = new JTextField();
		textField_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_name.setColumns(10);
		textField_name.setBounds(213, 159, 347, 26);
		contentPane.add(textField_name);
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
		String sql = "select name from category order by name asc";
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
	
	
	public void editCategory(String rut, SQL_Manager connection,String category_name, String newName) throws SQLException {
		String sql = "select id from category where name = ?";
		PreparedStatement st;
		ResultSet rs;
		
		st = connection.getConnection().prepareStatement(sql);
		st.setString(1, category_name);
		rs = st.executeQuery();
		rs.next();
		int category_id = rs.getInt("id");
		
		sql = "update category set name = ? where id = ?";
		
		st = connection.getConnection().prepareStatement(sql);
		st.setString(1, newName);
		st.setInt(2, category_id);
		st.executeUpdate();
		
		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jFrame,"Categoría actualizada.");
		
		AdminMenu v4 = new AdminMenu(rut,connection);
		v4.setLocationRelativeTo(null);
		v4.setVisible(true);
		dispose();

	}
	
	public boolean selectNames(SQL_Manager connection, String newName) throws SQLException {
		boolean exist = false;
		String sql = "select name from category";
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
