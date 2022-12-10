package interfaz;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.SQL_Manager;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.awt.Font;

@SuppressWarnings("serial")
public class AdminMenuProductsDelete extends JFrame {

	private JPanel contentPane;
	private JTable table_products;
	private JScrollPane scrollPane_products;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuProductsDelete frame = new AdminMenuProductsDelete("",null);
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
	public AdminMenuProductsDelete(String rut, SQL_Manager connection) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenuProductsDelete.class.getResource("/assets/SOH_logo.png")));
		setBackground(new Color(255, 250, 205));
		setTitle("Eliminar Producto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table_products = new JTable();
		scrollPane_products = new JScrollPane();
		
		table_products = updateTable(connection);
		scrollPane_products = showProducts(table_products);
		contentPane.add(scrollPane_products);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu v4 = new AdminMenu(rut,connection);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(AdminMenuProductsDelete.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(AdminMenuProductsDelete.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 467, 36, 26);
		contentPane.add(lbl_SOHlogo);
		
		JButton btn_delete = new JButton("ELIMINAR");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_products.getSelectedRow();
				if (row != -1) {
					int id =  Integer.parseInt((String)table_products.getValueAt(row, 0));
					try {
						deleteProduct(connection,id);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame,"Selecciona un producto.");
				}
			}
		});
		btn_delete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_delete.setBackground(Color.WHITE);
		btn_delete.setBounds(400, 420, 138, 26);
		contentPane.add(btn_delete);
		
		JLabel lbl_select = new JLabel("Seleccione un producto de la lista:");
		lbl_select.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_select.setBounds(60, 35, 359, 26);
		contentPane.add(lbl_select);
	}
	
	//Crea un JScrollPane a partir de una tabla.
		public JScrollPane showProducts (JTable table){
			try{
				JScrollPane scrollPane_userInfo = new JScrollPane(table);
				scrollPane_userInfo.setLocation(60, 70);
				scrollPane_userInfo.setSize(820, 330);
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
				String sql;
				PreparedStatement st;
				ResultSet rs;
				//EDITAR CONSULTA (INNER JOIN)
				sql = "Select p.id, p.name, p.price, p.stock, c.name as category_name from product p inner join category c on c.id = p.category_id order by p.id asc";
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
					row[4]= rs.getString("category_name");
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
				table.setBounds(60, 70, 820, 330);
				return (table);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,e);
			}
			return null;
		}
		
		public void deleteProduct(SQL_Manager connection, int id) throws SQLException {
			String sql = "delete from commentary where product_id = ?";
			PreparedStatement st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
			
			sql = "delete from product_cart where product_id = ?";
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
			
			sql = "delete from product where id = ?";
			st = connection.getConnection().prepareStatement(sql);
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
