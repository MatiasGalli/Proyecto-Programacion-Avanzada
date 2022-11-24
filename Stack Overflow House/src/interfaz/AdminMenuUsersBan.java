package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import logica.SQL_Manager;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AdminMenuUsersBan extends JFrame {

	private JPanel contentPane;
	private JTextField textField_search;
	private JLabel lbl_category;
	private JTable table_userInfo;
	private JLabel lbl_SOHlogo;
	private JButton btn_ban;
	private JButton btn_unban;
	private JLabel lbl_orderBy;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_categoryOrder;
	private JRadioButton rdbtn_asc;
	private JRadioButton rdbtn_desc;
	private JButton btn_search;
	private JButton btn_order;
	private JScrollPane scrollPane_userInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuUsersBan frame = new AdminMenuUsersBan(null);
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
	public AdminMenuUsersBan(SQL_Manager connection) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenuUsersBan.class.getResource("/assets/SOH_logo.png")));
		setTitle("Bloquear Usuario");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 640);
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
		btn_back.setIcon(new ImageIcon(AdminMenuUsersBan.class.getResource("/assets/back.png")));
		btn_back.setBounds(10, 10, 30, 30);
		btn_back.setBackground(null);
		btn_back.setBorder(null);
		contentPane.add(btn_back);

		JLabel lbl_search = new JLabel("B\u00FAsqueda por t\u00E9rmino:");
		lbl_search.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_search.setBounds(60, 50, 204, 26);
		contentPane.add(lbl_search);

		textField_search = new JTextField();
		textField_search.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_search.setBounds(270, 50, 300, 26);
		contentPane.add(textField_search);
		textField_search.setColumns(10);

		lbl_category = new JLabel("en la categor\u00EDa:");
		lbl_category.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_category.setBounds(580, 50, 144, 26);
		contentPane.add(lbl_category);

		JComboBox comboBox_categorySearch = new JComboBox();
		comboBox_categorySearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_categorySearch.setModel(new DefaultComboBoxModel(
				new String[] { "RUT", "Nombre de usuario", "Nombre Completo", "Correo Electr\u00F3nico" }));
		comboBox_categorySearch.setBounds(730, 50, 230, 26);
		contentPane.add(comboBox_categorySearch);

		table_userInfo = new JTable();
		scrollPane_userInfo = new JScrollPane();
		
		//Show the table and add the first JScrollPane to the Main Pane.
		
		table_userInfo = updateTable(connection,"fullname","asc");
		scrollPane_userInfo = showUsers(table_userInfo);
		contentPane.add(scrollPane_userInfo);

		lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(AdminMenuUsersBan.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 567, 36, 26);
		contentPane.add(lbl_SOHlogo);

		btn_ban = new JButton("BLOQUEAR");
		btn_ban.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rut = (String) table_userInfo.getValueAt(table_userInfo.getSelectedRow(), 0);
				banUser(connection,rut,true);
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame,"El usuario ha sido bloqueado exitosamente.");
			}
		});
		btn_ban.setBackground(Color.WHITE);
		btn_ban.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_ban.setBounds(140, 480, 180, 26);
		contentPane.add(btn_ban);

		btn_unban = new JButton("DESBLOQUEAR");
		btn_unban.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rut = (String) table_userInfo.getValueAt(table_userInfo.getSelectedRow(), 0);
				banUser(connection,rut,false);
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame,"El usuario ha sido desbloqueado exitosamente.");
			}
		});
		btn_unban.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_unban.setBackground(Color.WHITE);
		btn_unban.setBounds(140, 530, 180, 26);
		contentPane.add(btn_unban);

		lbl_orderBy = new JLabel("Ordenar por:");
		lbl_orderBy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_orderBy.setBounds(475, 480, 115, 26);
		contentPane.add(lbl_orderBy);

		comboBox_categoryOrder = new JComboBox();
		comboBox_categoryOrder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_categoryOrder.setModel(new DefaultComboBoxModel(new String[] { "RUT", "Nombre de usuario",
				"Nombre Completo", "Correo Electr\u00F3nico", "Bloqueado" }));
		comboBox_categoryOrder.setBounds(600, 481, 230, 26);
		contentPane.add(comboBox_categoryOrder);

		rdbtn_asc = new JRadioButton("Ascendente");
		rdbtn_asc.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtn_asc.setSelected(true);
		rdbtn_asc.setBounds(510, 512, 140, 21);
		rdbtn_asc.setBackground(null);
		contentPane.add(rdbtn_asc);

		rdbtn_desc = new JRadioButton("Descendente");
		rdbtn_desc.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtn_desc.setBounds(652, 513, 150, 21);
		rdbtn_desc.setBackground(null);
		contentPane.add(rdbtn_desc);
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtn_asc);
		group.add(rdbtn_desc);

		btn_search = new JButton("BUSCAR");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Here the table does not only updates itself, but it deletes the previous one.
				
				table_userInfo = updateTable(connection,"fullname","asc");
				scrollPane_userInfo = showUsers(table_userInfo);
				remove(contentPane.getComponentAt(60, 120));
				contentPane.add(scrollPane_userInfo);
			}
		});
		btn_search.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_search.setBackground(Color.WHITE);
		btn_search.setBounds(421, 84, 140, 26);
		contentPane.add(btn_search);
		
		btn_order = new JButton("ORDENAR");
		btn_order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String order = (String) comboBox_categoryOrder.getSelectedItem();
				if (order.equals("RUT")) {
					order = "rut";
				}else if (order.equals("Nombre de usuario")) {
					order = "username";
				}else if (order.equals("Nombre Completo")) {
					order = "fullname";
				}else if (order.equals("Correo Electrónico")) {
					order = "email";
				}else if (order.equals("Bloqueado")) {
					order = "banned";
				}
				group.getSelection().getSelectedObjects();
				String asc = null;
				Object[] ascbtn = rdbtn_asc.getSelectedObjects();
				if (ascbtn == null) {
					asc = "DESC";
				}else {
					asc = "ASC";
				}
				//LA TABLA SE ACTUALIZA, SE CARGA AL SCROLLPANE, LA TABLA ANTERIOR SE ELIMINA Y SE AGREGA LA NUEVA A LA VENTANA.
				
				table_userInfo = updateTable(connection,order,asc);
				scrollPane_userInfo = showUsers(table_userInfo);
				remove(contentPane.getComponentAt(60, 120));
				contentPane.add(scrollPane_userInfo);
			}
		});
		btn_order.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_order.setBackground(Color.WHITE);
		btn_order.setBounds(580, 550, 132, 26);
		contentPane.add(btn_order);
	}
	
	/*
	public Object[][] getUsers(SQL_Manager connection, Object[][] values) {

		Object[][] list = values;
		String sql, ban;
		Statement st;
		ResultSet rs;
		sql = "Select * from users order by fullname";

		try {
			st = connection.getConnection().createStatement();
			rs = st.executeQuery(sql);
			int cant = 0;
			while (rs.next()) {
				if (!rs.getBoolean("admin")) {
					list[cant][0] = rs.getString("rut");
					list[cant][1] = rs.getString("username");
					list[cant][2] = rs.getString("fullname");
					list[cant][3] = rs.getString("email");
					if (rs.getBoolean("banned"))
						ban = "Si";
					else
						ban = "No";
					list[cant][4] = ban;
					cant++;
				}
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}
	
	*/
	
	public int countUsers(SQL_Manager connection) throws SQLException {

		int cant = 0;
		String sql = "select count(*) from users where admin = ?";
		PreparedStatement st;
		st = connection.getConnection().prepareStatement(sql);
		st.setBoolean(1, false);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			cant = rs.getInt(1);
			return cant;
		}
		return 0;
	}

	public void banUser(SQL_Manager connection, String rut, boolean ban) {
		String sql = "update users set banned = ? where rut = ?";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setBoolean(1, ban);
			st.setString(2, rut);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Código Legacy. No usar. Por ahora.
	 * 
	public JScrollPane showUsers(SQL_Manager connection, JTable table, String category, String asc){
		try{
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql, ban;
			PreparedStatement st;
			ResultSet rs;
			sql = "Select * from users order by " + category + " " + asc;
			st = connection.getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			String titles[]={"RUT", "Nombre de usuario","Nombre Completo", "Correo Electr\u00F3nico", "Bloqueado"};
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
				if (!rs.getBoolean("admin")) {
					row[0]=rs.getString("rut");
					row[1]=rs.getString("username");
					row[2]=rs.getString("fullname");
					row[3]=rs.getString("email");
					if (rs.getBoolean("banned"))
						ban = "Si";
					else
						ban = "No";
					row[4]= ban;
					model.addRow(row);
				}
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(69);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(83);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(213);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(172);
			table.getColumnModel().getColumn(4).setPreferredWidth(15);
			table.setRowHeight(26);
			table.setBounds(60, 110, 900, 310);
			JScrollPane scrollPane_userInfo = new JScrollPane(table);
			scrollPane_userInfo.setLocation(60, 120);
			scrollPane_userInfo.setSize(900, 330);
			scrollPane_userInfo.setViewportView(table);
			return scrollPane_userInfo;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		return null;
	}
	
	*/
	
	//Crea un JScrollPane a partir de una tabla.
	public JScrollPane showUsers (JTable table){
		try{
			JScrollPane scrollPane_userInfo = new JScrollPane(table);
			scrollPane_userInfo.setLocation(60, 120);
			scrollPane_userInfo.setSize(900, 330);
			scrollPane_userInfo.setViewportView(table);
			return scrollPane_userInfo;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		return null;
	}
	
	//Crea una tabla nueva dado ciertos parámetros.
	public JTable updateTable(SQL_Manager connection, String category, String asc){
		try{
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.setBorder(null);
			String sql, ban;
			PreparedStatement st;
			ResultSet rs;
			sql = "Select * from users order by " + category + " " + asc;
			st = connection.getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			String titles[]={"RUT", "Nombre de usuario","Nombre Completo", "Correo Electr\u00F3nico", "Bloqueado"};
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
				if (!rs.getBoolean("admin")) {
					row[0]=rs.getString("rut");
					row[1]=rs.getString("username");
					row[2]=rs.getString("fullname");
					row[3]=rs.getString("email");
					if (rs.getBoolean("banned"))
						ban = "Si";
					else
						ban = "No";
					row[4]= ban;
					model.addRow(row);
				}
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(69);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(83);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(213);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(172);
			table.getColumnModel().getColumn(4).setPreferredWidth(15);
			table.setRowHeight(26);
			table.setBounds(60, 110, 900, 310);
			return (table);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		return null;
	}
}
