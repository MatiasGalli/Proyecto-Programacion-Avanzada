package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import logica.SQL_Manager;

@SuppressWarnings("serial")
public class AdminMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenu frame = new AdminMenu(null);
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
	public AdminMenu(SQL_Manager connection) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenu.class.getResource("/assets/SOH_logo.png")));
		setResizable(false);
		setTitle("Menu de administrador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(AdminMenu.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 327, 36, 26);
		contentPane.add(lbl_SOHLogo);
		
		JLabel lbl_welcome = new JLabel("Te damos la bienvenida al men\u00FA de administrador.");
		lbl_welcome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_welcome.setBounds(25, 30, 481, 13);
		contentPane.add(lbl_welcome);
		
		JPanel panel_products = new JPanel();
		panel_products.setBorder(new LineBorder(new Color(255, 215, 0), 2, true));
		panel_products.setBackground(new Color(240, 230, 140));
		panel_products.setBounds(36, 70, 120, 247);
		contentPane.add(panel_products);
		panel_products.setLayout(null);
		
		JLabel lbl_products = new JLabel("  PRODUCTOS");
		lbl_products.setForeground(new Color(65, 105, 225));
		lbl_products.setBackground(new Color(65, 105, 225));
		lbl_products.setBounds(10, 224, 100, 13);
		panel_products.add(lbl_products);
		
		JButton btn_productsUpload = new JButton("SUBIR");
		btn_productsUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuProductsUpload v5 = new AdminMenuProductsUpload(connection);
				v5.setLocationRelativeTo(null);
				v5.setVisible(true);
				dispose();
			}
		});
		btn_productsUpload.setBackground(new Color(255, 255, 255));
		btn_productsUpload.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_productsUpload.setBounds(10, 45, 100, 21);
		panel_products.add(btn_productsUpload);
		
		JButton btn_productsEdit = new JButton("EDITAR");
		btn_productsEdit.setBackground(new Color(255, 255, 255));
		btn_productsEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuProductsEdit v9 = new AdminMenuProductsEdit(connection);
				v9.setLocationRelativeTo(null);
				v9.setVisible(true);
				dispose();
			}
		});
		btn_productsEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_productsEdit.setBounds(10, 107, 100, 21);
		panel_products.add(btn_productsEdit);
		
		JButton btn_productsDelete = new JButton("ELIMINAR");
		btn_productsDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuProductsDelete v6 = new AdminMenuProductsDelete(connection);
				v6.setLocationRelativeTo(null);
				v6.setVisible(true);
				dispose();
			}
		});
		btn_productsDelete.setBackground(new Color(255, 255, 255));
		btn_productsDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_productsDelete.setBounds(10, 165, 100, 21);
		panel_products.add(btn_productsDelete);
		
		JPanel panel_categories = new JPanel();
		panel_categories.setBorder(new LineBorder(new Color(255, 215, 0), 2, true));
		panel_categories.setBackground(new Color(240, 230, 140));
		panel_categories.setBounds(203, 70, 120, 247);
		contentPane.add(panel_categories);
		panel_categories.setLayout(null);
		
		JLabel lbl_categories = new JLabel("  CATEGOR\u00CDAS");
		lbl_categories.setForeground(new Color(65, 105, 225));
		lbl_categories.setBackground(new Color(65, 105, 225));
		lbl_categories.setBounds(10, 224, 100, 13);
		panel_categories.add(lbl_categories);
		
		JButton btn_categoriesCreate = new JButton("SUBIR");
		btn_categoriesCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuCategoriesUpload v6 = new AdminMenuCategoriesUpload(connection);
				v6.setLocationRelativeTo(null);
				v6.setVisible(true);
				dispose();
			}
		});
		btn_categoriesCreate.setBackground(new Color(255, 255, 255));
		btn_categoriesCreate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_categoriesCreate.setBounds(10, 45, 100, 21);
		panel_categories.add(btn_categoriesCreate);
		
		JButton btn_back = new JButton("<-\r\n-");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login v1 = new Login(connection);
				v1.setLocationRelativeTo(null);
				v1.setVisible(true);
				dispose();
			}
		});
		btn_back.setBounds(0, 0, 55, 21);
		contentPane.add(btn_back);
		
		JButton btn_categoriesEdit = new JButton("EDITAR");
		btn_categoriesEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuCategoriesEdit v10 = new AdminMenuCategoriesEdit(connection);
				v10.setLocationRelativeTo(null);
				v10.setVisible(true);
				dispose();
			}
		});
		btn_categoriesEdit.setBackground(new Color(255, 255, 255));
		btn_categoriesEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_categoriesEdit.setBounds(10, 108, 100, 21);
		panel_categories.add(btn_categoriesEdit);
		
		JButton btn_categoriesDelete = new JButton("ELIMINAR");
		btn_categoriesDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuCategoriesDelete v7 = new AdminMenuCategoriesDelete(connection);
				v7.setLocationRelativeTo(null);
				v7.setVisible(true);
				dispose();
			}
		});
		btn_categoriesDelete.setBackground(new Color(255, 255, 255));
		btn_categoriesDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_categoriesDelete.setBounds(10, 165, 100, 21);
		panel_categories.add(btn_categoriesDelete);
		
		JPanel panel_users = new JPanel();
		panel_users.setBorder(new LineBorder(new Color(255, 215, 0), 2, true));
		panel_users.setBackground(new Color(240, 230, 140));
		panel_users.setBounds(367, 70, 120, 247);
		contentPane.add(panel_users);
		panel_users.setLayout(null);
		
		JLabel lbl_users = new JLabel("      USUARIOS");
		lbl_users.setForeground(new Color(65, 105, 225));
		lbl_users.setBackground(new Color(65, 105, 225));
		lbl_users.setBounds(10, 224, 100, 13);
		panel_users.add(lbl_users);
		
		JButton btn_usersAdd = new JButton("AGREGAR");
		btn_usersAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean admin = true;
				Register v2 = new Register(admin, connection);
				v2.setLocationRelativeTo(null);
				v2.setVisible(true);
				dispose();
			}
		});
		btn_usersAdd.setBackground(new Color(255, 255, 255));
		btn_usersAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_usersAdd.setBounds(10, 45, 100, 21);
		panel_users.add(btn_usersAdd);
		
		JButton btn_usersEditInfo = new JButton("EDITAR");
		btn_usersEditInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuUsersEdit v11 = new AdminMenuUsersEdit(connection);
				v11.setLocationRelativeTo(null);
				v11.setVisible(true);
				dispose();
			}
		});
		btn_usersEditInfo.setBackground(new Color(255, 255, 255));
		btn_usersEditInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_usersEditInfo.setBounds(10, 108, 100, 21);
		panel_users.add(btn_usersEditInfo);
		
		JButton btn_usersBan = new JButton("BLOQUEAR");
		btn_usersBan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuUsersBan v7 = new AdminMenuUsersBan(connection);
				v7.setLocationRelativeTo(null);
				v7.setVisible(true);
				dispose();
			}
		});
		btn_usersBan.setBackground(new Color(255, 255, 255));
		btn_usersBan.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btn_usersBan.setBounds(10, 164, 100, 21);
		panel_users.add(btn_usersBan);
		
		JButton btn_adminAccount = new JButton("CUENTA DE ADMINISTRADOR");
		btn_adminAccount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn_adminAccount.setBackground(Color.WHITE);
		btn_adminAccount.setBounds(316, 25, 190, 25);
		contentPane.add(btn_adminAccount);
	}
}
