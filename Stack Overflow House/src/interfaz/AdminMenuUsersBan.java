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
	private JButton btn_search_1;

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
		comboBox_categorySearch.setModel(new DefaultComboBoxModel(new String[] {"RUT", "Nombre de usuario", "Nombre Completo", "Correo Electr\u00F3nico"}));
		comboBox_categorySearch.setBounds(730, 50, 230, 26);
		contentPane.add(comboBox_categorySearch);

		table_userInfo = new JTable();
		table_userInfo.setShowVerticalLines(false);
		table_userInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table_userInfo.setBorder(null);
		int cant = 0;
		try {
			cant = countUsers(connection);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		Object[][] table = new Object[cant][4];
		Object[][] values = getUsers(connection, table);
		table_userInfo.setModel(new DefaultTableModel(values,
				new String[] { "RUT", "Nombre de usuario", "Nombre Completo", "Correo Electr\u00F3nico" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_userInfo.getColumnModel().getColumn(0).setPreferredWidth(69);
		table_userInfo.getColumnModel().getColumn(1).setResizable(false);
		table_userInfo.getColumnModel().getColumn(1).setPreferredWidth(83);
		table_userInfo.getColumnModel().getColumn(2).setResizable(false);
		table_userInfo.getColumnModel().getColumn(2).setPreferredWidth(213);
		table_userInfo.getColumnModel().getColumn(3).setResizable(false);
		table_userInfo.getColumnModel().getColumn(3).setPreferredWidth(172);
		table_userInfo.setRowHeight(26);
		table_userInfo.setBounds(60, 110, 900, 310);
		JScrollPane scrollPane_userInfo = new JScrollPane(table_userInfo);
		scrollPane_userInfo.setLocation(60, 120);
		scrollPane_userInfo.setSize(900, 330);
		contentPane.add(scrollPane_userInfo);

		lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(AdminMenuUsersBan.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 567, 36, 26);
		contentPane.add(lbl_SOHlogo);

		btn_ban = new JButton("BLOQUEAR");
		btn_ban.setBackground(Color.WHITE);
		btn_ban.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_ban.setBounds(140, 480, 180, 26);
		contentPane.add(btn_ban);

		btn_unban = new JButton("DESBLOQUEAR");
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
		comboBox_categoryOrder.setModel(new DefaultComboBoxModel(new String[] {"RUT", "Nombre de usuario", "Nombre Completo", "Correo Electr\u00F3nico"}));
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
		btn_search.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_search.setBackground(Color.WHITE);
		btn_search.setBounds(405, 84, 140, 26);
		contentPane.add(btn_search);
		
		btn_search_1 = new JButton("ORDENAR");
		btn_search_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_search_1.setBackground(Color.WHITE);
		btn_search_1.setBounds(580, 550, 132, 26);
		contentPane.add(btn_search_1);
	}
	
public Object[][] getUsers(SQL_Manager connection, Object[][] values) {
		
		
		Object[][] list = values;
		String sql;
		Statement st;
		ResultSet rs;
		sql = "Select * from users";

		try {
			st = connection.getConnection().createStatement();
			rs = st.executeQuery(sql);
			int cant = 0;
			while(rs.next()) {
				if (!rs.getBoolean("admin")) {
					list[cant][0] = "   " + rs.getString("rut");
					list[cant][1] = rs.getString("username");
					list[cant][2] = rs.getString("fullname");
					list[cant][3] = rs.getString("email");
					cant++;
				} 
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}
	
	public int countUsers(SQL_Manager connection) throws SQLException {
		
		int cant = 0;
		String sql = "select count(*) from users where admin = ?";
		PreparedStatement st;
		st = connection.getConnection().prepareStatement(sql);
		st.setBoolean(1, false);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			cant = rs.getInt(1);
			return cant;
		}
		return 0;
	}
}
