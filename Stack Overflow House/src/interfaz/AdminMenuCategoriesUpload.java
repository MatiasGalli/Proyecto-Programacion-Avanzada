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
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AdminMenuCategoriesUpload extends JFrame {

	private JPanel contentPane;
	private JTextField textField_name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuCategoriesUpload frame = new AdminMenuCategoriesUpload(null);
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
	public AdminMenuCategoriesUpload(SQL_Manager connection) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/assets/SOH_logo.png")));
		setAlwaysOnTop(true);
		setTitle("Subir Categoría");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 160);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_name = new JLabel("Nombre de la categor\u00EDa que desea crear");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_name.setBounds(35, 25, 291, 15);
		contentPane.add(lbl_name);

		textField_name = new JTextField();
		textField_name.setBounds(35, 41, 280, 19);
		contentPane.add(textField_name);
		textField_name.setColumns(10);

		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(Register.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 82, 36, 26);
		contentPane.add(lbl_SOHLogo);
		
		JButton btn_back = new JButton("<-\r\n-");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu v4 = new AdminMenu(connection);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setBounds(0, 0, 55, 21);
		contentPane.add(btn_back);

		JButton btn_upload = new JButton("Crear");
		btn_upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean nameEmpty = textField_name.getText().equals("");

				if (!nameEmpty) {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "¡Categoría creada exitosamente!");

					String id = "-1";
					try {
						id = countCategories(connection);
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					String name = textField_name.getText();
					try {
						insertCategories(connection, String.valueOf(Integer.parseInt(id) + 1), name);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					AdminMenu v4 = new AdminMenu(connection);
					v4.setVisible(true);
					dispose();
				} else {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "No se le ha asignado ningún nombre a la categoría.");
				}
			}

		});
		btn_upload.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btn_upload.setBackground(new Color(255, 255, 255));
		btn_upload.setBounds(119, 85, 100, 21);
		contentPane.add(btn_upload);
	}

	public void insertCategories(SQL_Manager connection, String id, String name) throws SQLException {
		
		String sql = "Insert into category(id,name) values(?,?)";
		PreparedStatement st = connection.getConnection().prepareStatement(sql);
		st.setString(1, id);
		st.setString(2, name);
		st.executeUpdate();
		
	}
	
	public String countCategories(SQL_Manager connection) throws SQLException {
		
		String sql = "select id from category order by id desc limit 1";
		
		Statement st = connection.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			String id = rs.getString("id");
			return id;
		}
		return "-1";
	}
}