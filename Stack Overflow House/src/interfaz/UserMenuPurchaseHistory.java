package interfaz;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.SQL_Manager;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UserMenuPurchaseHistory extends JFrame {

	private JPanel contentPane;
	private JTable table_history;
	private JScrollPane scrollPane_history;
	private JTextField textField_commentary;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMenuPurchaseHistory frame = new UserMenuPurchaseHistory("",null);
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
	public UserMenuPurchaseHistory(String user, SQL_Manager connection) {
		setTitle("Registro de Compras");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserMenuPurchaseHistory.class.getResource("/assets/SOH_logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(UserMenuPurchaseHistory.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 407, 36, 26);
		contentPane.add(lbl_SOHlogo);
		
		table_history = new JTable();
		scrollPane_history = new JScrollPane();
		try {
			String user_rut = selectUserRut(connection,user);
			table_history = updateTable(connection, user_rut);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		scrollPane_history = showHistory(table_history);
		contentPane.add(scrollPane_history);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMenuAccount v4 = new UserMenuAccount(user, connection);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(UserMenuPurchaseHistory.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JButton btn_delete_1_2 = new JButton("COMENTAR PRODUCTO");
		btn_delete_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_history.getSelectedRow();
				if (row != -1) {
					try {
						String product_name = (String) table_history.getValueAt(row, 0);
						int product_id = selectProductID(connection,product_name);
						if (product_id != 0) {
							String user_rut = selectUserRut(connection,user);
							int commentary_id = countCommentaryID(connection);
							addCommentary(connection,commentary_id+1, user_rut,product_id);
							JFrame jFrame = new JFrame();
							jFrame.setAlwaysOnTop(true);
							JOptionPane.showMessageDialog(jFrame, "Comentario Agregado.");
						}else {
							JFrame jFrame = new JFrame();
							jFrame.setAlwaysOnTop(true);
							JOptionPane.showMessageDialog(jFrame, "El comentario no se pudo agregar. Puede que el producto no se encuentre en el sistema o tenga otro nombre.");
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Selecciona un producto.");
				}
			}
		});
		btn_delete_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_delete_1_2.setBackground(Color.WHITE);
		btn_delete_1_2.setBounds(232, 383, 273, 26);
		contentPane.add(btn_delete_1_2);
		
		JLabel lbl_commentary = new JLabel("Comentario:");
		lbl_commentary.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_commentary.setBounds(36, 347, 108, 26);
		contentPane.add(lbl_commentary);
		
		textField_commentary = new JTextField();
		textField_commentary.setText((String) null);
		textField_commentary.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_commentary.setColumns(10);
		textField_commentary.setBounds(130, 347, 593, 26);
		contentPane.add(textField_commentary);
		
		JLabel lbl_commentary_1 = new JLabel("Esta es la lista de productos que has comprado:");
		lbl_commentary_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_commentary_1.setBounds(61, 23, 578, 26);
		contentPane.add(lbl_commentary_1);
	}
	
	public JScrollPane showHistory (JTable table){
		try{
			JScrollPane scrollPane_userInfo = new JScrollPane(table);
			scrollPane_userInfo.setLocation(60, 60);
			scrollPane_userInfo.setSize(640, 280);
			scrollPane_userInfo.setViewportView(table);
			return scrollPane_userInfo;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		return null;
	}
	
	//Crea una tabla nueva dado ciertos parámetros.
	public JTable updateTable(SQL_Manager connection, String user_rut) throws SQLException{
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql;
			PreparedStatement st;
			ResultSet rs;
			//EDITAR CONSULTA (INNER JOIN)
			sql = "select hp.product_name, hp.amount, hp.purchase_date from history_product hp inner join shopping_history sh on sh.history_id = hp.history_id inner join users u on u.rut = sh.user_rut where u.rut = ? order by hp.purchase_date asc";
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, user_rut);
			rs = st.executeQuery();
			String titles[]={"Nombre del producto", "Cantidad", "Fecha de compra"};
			DefaultTableModel model = new DefaultTableModel(null,titles) {
				boolean[] columnEditables = new boolean[] {
						false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				};
			String row[]=new String[3];
			while(rs.next()){
				row[0]=rs.getString("product_name");
				row[1]=Integer.toString(rs.getInt("amount"));
				row[2]=rs.getString("purchase_date");
				model.addRow(row);
				
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(250);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(50);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(200);
			table.setRowHeight(26);
			table.setBounds(60, 60, 640, 280);
			return (table);
	}
	
	public void addCommentary(SQL_Manager connection,int commentary_id, String user_rut, int product_id) throws SQLException {
		String commentary = textField_commentary.getText();
		
		if (!commentary.equals("")) {
			String sql = "insert into commentary(commentary_id,user_rut,product_id,comment) values(?,?,?,?)";
			PreparedStatement st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, commentary_id);
			st.setString(2, user_rut);
			st.setInt(3, product_id);
			st.setString(4, commentary);
			st.executeUpdate();
		}else {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Debes agregar un comentario.");
		}
		
		
	}
	
	public int countCommentaryID(SQL_Manager connection) throws SQLException {

		String sql = "select commentary_id from commentary order by commentary_id desc limit 1";
		int id = 0;
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			id = rs.getInt("commentary_id");
		}
		return id;
	}
	
	public int selectProductID(SQL_Manager connection, String product_name) throws SQLException {

		String sql = "select id from product where name = ?";
		int id = 0;
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, product_name);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			id = rs.getInt("id");
		}
		return id;
	}
	
	public String selectUserRut(SQL_Manager connection, String username) throws SQLException {
		String rut = "";
		String sql = "select rut from users where username = ?";
		// FUNCIONALIDAD VERIFICAR EN CASO DE NO EXISTIR NINGÚN PRODUCTO
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			rut = rs.getString("rut");
		}
		return rut;
	}
}
