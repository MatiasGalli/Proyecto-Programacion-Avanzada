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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class AdminMenuCategoriesDelete extends JFrame {

	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuCategoriesDelete frame = new AdminMenuCategoriesDelete("",null);
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
	public AdminMenuCategoriesDelete(String rut, SQL_Manager connection) {
		setBackground(new Color(255, 250, 205));
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenuCategoriesDelete.class.getResource("/assets/SOH_logo.png")));
		setTitle("Eliminar Categor\u00EDa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(AdminMenuCategoriesDelete.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 287, 36, 26);
		contentPane.add(lbl_SOHlogo);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu v4 = new AdminMenu(rut, connection);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(AdminMenuCategoriesDelete.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JComboBox comboBox_category = new JComboBox();
		comboBox_category.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox_category.setBounds(65, 129, 495, 26);
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
		
		JButton btn_delete = new JButton("ELIMINAR");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = (String) comboBox_category.getSelectedItem();
				try {
					deleteCategory(rut,connection,name);
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
		
		JLabel lbl_note = new JLabel("* No se puede eliminar una categor\u00EDa en la que a\u00FAn existen productos. Para borrarla se deben eliminar todos los productos que pertenecen a esta. ");
		lbl_note.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lbl_note.setBounds(25, 262, 606, 26);
		contentPane.add(lbl_note);
	}
	
	public void deleteCategory(String rutAdmin, SQL_Manager connection, String name) throws SQLException {
		boolean error = false;
		String sql = "delete from category where name = ?";
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, name);
		try {
			st.executeUpdate();
		} catch (SQLException e1) {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame,"No se puede eliminar la categoría. Aún existen productos que pertenecen a: " + name);
			error = true;
		}
		if(error != true) {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame,"Categoría eliminada");

			AdminMenu v4 = new AdminMenu(rutAdmin, connection);
			v4.setLocationRelativeTo(null);
			v4.setVisible(true);
			dispose();
		}
		
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
}
