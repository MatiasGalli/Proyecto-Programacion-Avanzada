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
		
		JLabel lbl_welcomeMessage = new JLabel("\u00A1Nos alegra que est\u00E9s de vuelta, "+user+"! Estos son los productos que se encuentran disponibles:");
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
				UserMenuCart v8 = new UserMenuCart(user,connection);
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
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField.setColumns(10);
		textField.setBounds(223, 82, 325, 26);
		contentPane.add(textField);
		
		JLabel lbl_category = new JLabel("en la categor\u00EDa:");
		lbl_category.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_category.setBounds(565, 82, 144, 26);
		contentPane.add(lbl_category);
		
		JComboBox comboBox_categorySearch = new JComboBox();
		comboBox_categorySearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_categorySearch.setBounds(680, 82, 216, 26);
		contentPane.add(comboBox_categorySearch);
		
		JPanel panel_cart = new JPanel();
		panel_cart.setBorder(null);
		panel_cart.setBackground(new Color(240, 230, 140));
		panel_cart.setBounds(36, 74, 880, 38);
		contentPane.add(panel_cart);
		panel_cart.setLayout(null);
		
		JPanel panel_cart_1 = new JPanel();
		panel_cart_1.setBorder(null);
		panel_cart_1.setBackground(new Color(240, 230, 140));
		panel_cart_1.setBounds(36, 110, 180, 38);
		contentPane.add(panel_cart_1);
		panel_cart_1.setLayout(null);
		
		JButton btn_search = new JButton("BUSCAR");
		btn_search.setBounds(20, 7, 140, 26);
		panel_cart_1.add(btn_search);
		btn_search.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_search.setBackground(Color.WHITE);
		
		JButton btn_seeData = new JButton("VER DATOS");
		btn_seeData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_seeData.setBackground(Color.WHITE);
		btn_seeData.setBounds(130, 429, 210, 26);
		contentPane.add(btn_seeData);
		
		JLabel lbl_name = new JLabel("Descripción:");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_name.setBounds(47, 482, 116, 26);
		contentPane.add(lbl_name);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setText((String) null);
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_1.setColumns(10);
		textField_1.setBounds(141, 482, 755, 26);
		contentPane.add(textField_1);
		
		JLabel lbl_name_1 = new JLabel("Comentario:");
		lbl_name_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_name_1.setBounds(47, 517, 116, 26);
		contentPane.add(lbl_name_1);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setText((String) null);
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_2.setColumns(10);
		textField_2.setBounds(141, 518, 755, 26);
		contentPane.add(textField_2);
		
		JButton btn_seeData_1 = new JButton("VER COMENTARIO");
		btn_seeData_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_seeData_1.setBackground(Color.WHITE);
		btn_seeData_1.setBounds(441, 429, 210, 26);
		contentPane.add(btn_seeData_1);
		
		JComboBox comboBox_categorySearch_1 = new JComboBox();
		comboBox_categorySearch_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_categorySearch_1.setBounds(661, 430, 235, 26);
		contentPane.add(comboBox_categorySearch_1);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_3.setColumns(10);
		textField_3.setBounds(245, 563, 96, 26);
		contentPane.add(textField_3);
		
		JLabel lbl_units = new JLabel("Unidades:");
		lbl_units.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_units.setBounds(151, 563, 106, 26);
		contentPane.add(lbl_units);
		
		JPanel panel_cart_2 = new JPanel();
		panel_cart_2.setLayout(null);
		panel_cart_2.setBorder(null);
		panel_cart_2.setBackground(new Color(240, 230, 140));
		panel_cart_2.setBounds(36, 417, 880, 136);
		contentPane.add(panel_cart_2);
		
		JLabel lbl_name_2 = new JLabel("* Para ver los comentarios de un producto, primero debes presionar VER DATOS seleccionando dicho producto y luego seleccionar el usuario desde la caja correspondiente");
		lbl_name_2.setBounds(61, 39, 809, 26);
		panel_cart_2.add(lbl_name_2);
		lbl_name_2.setFont(new Font("Tahoma", Font.ITALIC, 10));
		
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
		
		JLabel lbl_orderBy = new JLabel("Ordenar por:");
		lbl_orderBy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_orderBy.setBounds(267, 123, 115, 26);
		contentPane.add(lbl_orderBy);
		
		JComboBox comboBox_categoryOrder = new JComboBox();
		comboBox_categoryOrder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_categoryOrder.setBounds(392, 124, 230, 26);
		contentPane.add(comboBox_categoryOrder);
		
		JButton btn_order = new JButton("ORDENAR");
		btn_order.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_order.setBackground(Color.WHITE);
		btn_order.setBounds(661, 135, 132, 26);
		contentPane.add(btn_order);
		
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
			while(rs.next()) {
				list[cant][0] = "   " + rs.getString("id");
				list[cant][1] = rs.getString("name");
				list[cant][2] = "$" + rs.getString("price");
				list[cant][3] = rs.getString("stock");
				category = getCategories(connection,rs.getInt("category_id"));
				list[cant][4] = category;
				cant++;
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}
	
	public String countProducts(SQL_Manager connection) throws SQLException {
		
		String sql = "select id from product order by id desc limit 1";
		//FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NING�N PRODUCTO
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			String id = rs.getString("id");
			return id;
		}
		return "-1";
	}
	
	
	//Crea un JScrollPane a partir de una tabla.
			public JScrollPane showProducts (JTable table){
				try{
					JScrollPane scrollPane_userInfo = new JScrollPane(table);
					scrollPane_userInfo.setLocation(37, 200);
					scrollPane_userInfo.setSize(880, 204);
					scrollPane_userInfo.setViewportView(table);
					return scrollPane_userInfo;
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
				}
				return null;
			}
			
			//Crea una tabla nueva dado ciertos parámetros.
			public JTable updateTable(SQL_Manager connection){
				try{
					JTable table = new JTable();
					table.setShowVerticalLines(false);
					table.setFont(new Font("Tahoma", Font.PLAIN, 16));
					table.setBorder(null);
					String sql, category;
					PreparedStatement st;
					ResultSet rs;
					//EDITAR CONSULTA (INNER JOIN)
					sql = "Select * from product order by id asc";
					st = connection.getConnection().prepareStatement(sql);
					rs = st.executeQuery();
					String titles[]={"ID", "Nombre del producto","Precio", "Stock", "Categoría"};
					DefaultTableModel model = new DefaultTableModel(null,titles) {
						boolean[] columnEditables = new boolean[] {
								false, false, false, false, false
							};
							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						};
					String row[]=new String[6];
					while(rs.next()){
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]= "$ " + rs.getString("price");
						row[3]=rs.getString("stock");
						category = getCategories(connection,rs.getInt("category_id"));
						row[4]= category;
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
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
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
			
			public void deleteProduct(SQL_Manager connection, int id) throws SQLException {
				String sql = "delete from product where id = ?";
				PreparedStatement st = connection.getConnection().prepareStatement(sql);
				st.setInt(1, id);
				st.executeUpdate();
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame,"Producto eliminado");
				table_products = updateTable(connection);
				scrollPane_products = showProducts(table_products);
				remove(contentPane.getComponentAt(60, 120));
				contentPane.add(scrollPane_products);
			}
}
