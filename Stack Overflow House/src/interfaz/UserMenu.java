package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;

import logica.SQL_Manager;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class UserMenu extends JFrame {

	private JPanel contentPane;
	private JTextField textField_search;
	private JTable table_products;

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
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_welcomeMessage = new JLabel("\u00A1Nos alegra que est\u00E9s de vuelta, "+user+"! Estos son los productos que se encuentran disponibles:");
		lbl_welcomeMessage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbl_welcomeMessage.setBounds(10, 25, 566, 13);
		contentPane.add(lbl_welcomeMessage);
		
		textField_search = new JTextField();
		textField_search.setBounds(385, 85, 190, 20);
		contentPane.add(textField_search);
		textField_search.setColumns(10);
		
		JLabel lbl_typeToSearch = new JLabel("Ingrese un t\u00E9rmino");
		lbl_typeToSearch.setBounds(274, 88, 145, 13);
		contentPane.add(lbl_typeToSearch);
		
		JButton btn_search = new JButton("Buscar");
		btn_search.setBackground(new Color(255, 255, 255));
		btn_search.setBounds(585, 84, 80, 21);
		contentPane.add(btn_search);
		
		JButton btn_userAccount = new JButton("CUENTA DE USUARIO");
		btn_userAccount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn_userAccount.setBackground(new Color(255, 255, 255));
		btn_userAccount.setBounds(521, 17, 155, 25);
		contentPane.add(btn_userAccount);
		
		JList list_productInfo = new JList();
		list_productInfo.setModel(new AbstractListModel() {
			String[] values = new String[] {"[NOMBRE PRODUCTO]", "[NOMBRE DEL USUARIO]", "[PRECIO]", ".", ".", ".", "[DESCRIPCI\u00D3N", "----------------", "----------------", "----------------", "----------------", "----------------", "----------------]"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_productInfo.setVisibleRowCount(1000);
		list_productInfo.setToolTipText("");
		list_productInfo.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		list_productInfo.setBounds(21, 80, 180, 260);
		contentPane.add(list_productInfo);
		
		JButton btn_addToTheCart = new JButton("A\u00F1adir al carrito");
		btn_addToTheCart.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn_addToTheCart.setBackground(Color.WHITE);
		btn_addToTheCart.setBounds(48, 350, 130, 25);
		contentPane.add(btn_addToTheCart);
		
		JLabel lbl_logoSOH = new JLabel("Logo");
		lbl_logoSOH.setIcon(new ImageIcon(UserMenu.class.getResource("/assets/SOH_logoMin.png")));
		lbl_logoSOH.setBounds(10, 377, 36, 26);
		contentPane.add(lbl_logoSOH);
		
		JScrollPane scrollPane_products = new JScrollPane();
		scrollPane_products.setBounds(632, 127, 18, 213);
		contentPane.add(scrollPane_products);
		
		table_products = new JTable();
		table_products.setShowGrid(false);
		table_products.setShowVerticalLines(false);
		int cant = 10;
		try {
			cant = Integer.parseInt(countProducts(connection));
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		Object[][] table = new Object[cant+1][5];
		Object[][] values = getProducts(connection, table);
		table_products.setModel(new DefaultTableModel(
			values,
			new String[] {
				"ID", "Nombre", "Precio", "Stock", "Categor\u00EDa"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_products.getColumnModel().getColumn(0).setResizable(false);
		table_products.getColumnModel().getColumn(0).setPreferredWidth(25);
		table_products.getColumnModel().getColumn(1).setPreferredWidth(169);
		table_products.getColumnModel().getColumn(2).setResizable(false);
		table_products.getColumnModel().getColumn(3).setResizable(false);
		table_products.getColumnModel().getColumn(4).setResizable(false);
		table_products.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		table_products.setBounds(230, 127, 403, 213);
		contentPane.add(table_products);
		
		JLabel lbl_ID = new JLabel("ID");
		lbl_ID.setBounds(240, 111, 18, 13);
		contentPane.add(lbl_ID);
		
		JLabel lbl_name = new JLabel("Nombre");
		lbl_name.setBounds(307, 111, 46, 13);
		contentPane.add(lbl_name);
		
		JLabel lbl_price = new JLabel("Precio");
		lbl_price.setBounds(413, 111, 46, 13);
		contentPane.add(lbl_price);
		
		JLabel lbl_stock = new JLabel("Stock");
		lbl_stock.setBounds(490, 111, 62, 13);
		contentPane.add(lbl_stock);
		
		JLabel lbl_category = new JLabel("Categor\u00EDa (ID)");
		lbl_category.setBounds(545, 111, 88, 13);
		contentPane.add(lbl_category);
		
	}
	
	public Object[][] getProducts(SQL_Manager connection, Object[][] values) {
		
		
		Object[][] list = values;
		String sql;
		Statement st;
		ResultSet rs;
		sql = "Select * from product";

		try {
			st = connection.getConnection().createStatement();
			rs = st.executeQuery(sql);
			int cant = 0;
			while(rs.next()) {
				list[cant][0] = "   " + rs.getString("id");
				list[cant][1] = rs.getString("name");
				list[cant][2] = "$" + rs.getString("price");
				list[cant][3] = rs.getString("stock");
				list[cant][4] = rs.getString("category_id");
				cant++;
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
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
