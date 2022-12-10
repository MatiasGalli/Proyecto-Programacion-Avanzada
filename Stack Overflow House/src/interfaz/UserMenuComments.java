package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.SQL_Manager;

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class UserMenuComments extends JFrame {

	private JPanel contentPane;
	private JTable table_comments;
	private JScrollPane scrollPane_comments;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMenuComments frame = new UserMenuComments("",0,null);
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
	public UserMenuComments(String user, int product_id, SQL_Manager connection) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserMenuComments.class.getResource("/assets/SOH_logo.png")));
		setTitle("Comentarios del producto");
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
				UserMenu v4 = new UserMenu(user, connection);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(UserMenuComments.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		table_comments = new JTable();
		scrollPane_comments = new JScrollPane();
		
		try {
			table_comments = updateTable(connection, product_id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		scrollPane_comments = showComments(table_comments);
		contentPane.add(scrollPane_comments);
		
		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(UserMenuComments.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 407, 36, 26);
		contentPane.add(lbl_SOHlogo);
		
		JLabel lbl_commentary_1 = new JLabel("Comentarios del producto:");
		lbl_commentary_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_commentary_1.setBounds(58, 29, 253, 26);
		contentPane.add(lbl_commentary_1);
	}
	
	public JScrollPane showComments (JTable table){
		try{
			JScrollPane scrollPane_comments = new JScrollPane(table);
			scrollPane_comments.setLocation(60, 60);
			scrollPane_comments.setSize(640, 320);
			scrollPane_comments.setViewportView(table);
			return scrollPane_comments;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		return null;
	}
	
	//Crea una tabla nueva dado ciertos parámetros.
	public JTable updateTable(SQL_Manager connection, int product_id) throws SQLException{
			JTable table = new JTable();
			table.setShowVerticalLines(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 12));
			table.setBorder(null);
			String sql;
			PreparedStatement st;
			ResultSet rs;
			//EDITAR CONSULTA (INNER JOIN)
			sql = "select u.username, comment from commentary inner join users u on user_rut = rut where product_id = ?";
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, product_id);
			rs = st.executeQuery();
			String titles[]={"Nombre del Usuario", "Comentario"};
			DefaultTableModel model = new DefaultTableModel(null,titles) {
				boolean[] columnEditables = new boolean[] {
						false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				};
			String row[]=new String[2];
			while(rs.next()){
				row[0]=rs.getString("username");
				row[1]=rs.getString("comment");
				model.addRow(row);
				
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(520);
			table.setRowHeight(26);
			table.setBounds(60, 60, 640, 320);
			return (table);
	}
}
