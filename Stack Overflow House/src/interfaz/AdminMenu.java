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
					AdminMenu frame = new AdminMenu("",null);
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
	public AdminMenu(String rut, SQL_Manager connection) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenu.class.getResource("/assets/SOH_logo.png")));
		setResizable(false);
		setTitle("Menu de administrador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 540);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(AdminMenu.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 467, 36, 26);
		contentPane.add(lbl_SOHLogo);
		
		JLabel lbl_welcome = new JLabel("Te damos la bienvenida al men\u00FA de administrador.");
		lbl_welcome.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_welcome.setBounds(88, 34, 481, 26);
		contentPane.add(lbl_welcome);
		
		JPanel panel_products = new JPanel();
		panel_products.setBorder(new LineBorder(new Color(255, 215, 0), 2, true));
		panel_products.setBackground(new Color(240, 230, 140));
		panel_products.setBounds(88, 70, 180, 386);
		contentPane.add(panel_products);
		panel_products.setLayout(null);
		
		JLabel lbl_products = new JLabel("  PRODUCTOS");
		lbl_products.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_products.setForeground(new Color(65, 105, 225));
		lbl_products.setBackground(new Color(65, 105, 225));
		lbl_products.setBounds(37, 363, 143, 13);
		panel_products.add(lbl_products);
		
		JButton btn_productsUpload = new JButton("SUBIR");
		btn_productsUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuProductsUpload v5 = new AdminMenuProductsUpload(rut,connection);
				v5.setLocationRelativeTo(null);
				v5.setVisible(true);
				dispose();
			}
		});
		btn_productsUpload.setBackground(new Color(255, 255, 255));
		btn_productsUpload.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn_productsUpload.setBounds(10, 45, 160, 40);
		panel_products.add(btn_productsUpload);
		
		JButton btn_productsEdit = new JButton("EDITAR");
		btn_productsEdit.setBackground(new Color(255, 255, 255));
		btn_productsEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuProductsEdit v9 = new AdminMenuProductsEdit(rut,connection);
				v9.setLocationRelativeTo(null);
				v9.setVisible(true);
				dispose();
			}
		});
		btn_productsEdit.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn_productsEdit.setBounds(10, 168, 160, 40);
		panel_products.add(btn_productsEdit);
		
		JButton btn_productsDelete = new JButton("ELIMINAR");
		btn_productsDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuProductsDelete v6 = new AdminMenuProductsDelete(rut,connection);
				v6.setLocationRelativeTo(null);
				v6.setVisible(true);
				dispose();
			}
		});
		btn_productsDelete.setBackground(new Color(255, 255, 255));
		btn_productsDelete.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn_productsDelete.setBounds(10, 287, 160, 40);
		panel_products.add(btn_productsDelete);
		
		JPanel panel_categories = new JPanel();
		panel_categories.setBorder(new LineBorder(new Color(255, 215, 0), 2, true));
		panel_categories.setBackground(new Color(240, 230, 140));
		panel_categories.setBounds(327, 70, 180, 386);
		contentPane.add(panel_categories);
		panel_categories.setLayout(null);
		
		JLabel lbl_categories = new JLabel("  CATEGOR\u00CDAS");
		lbl_categories.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_categories.setForeground(new Color(65, 105, 225));
		lbl_categories.setBackground(new Color(65, 105, 225));
		lbl_categories.setBounds(35, 363, 135, 13);
		panel_categories.add(lbl_categories);
		
		JButton btn_categoriesCreate = new JButton("SUBIR");
		btn_categoriesCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuCategoriesUpload v6 = new AdminMenuCategoriesUpload(rut,connection);
				v6.setLocationRelativeTo(null);
				v6.setVisible(true);
				dispose();
			}
		});
		btn_categoriesCreate.setBackground(new Color(255, 255, 255));
		btn_categoriesCreate.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn_categoriesCreate.setBounds(10, 45, 160, 40);
		panel_categories.add(btn_categoriesCreate);
		
		JButton btn_categoriesEdit = new JButton("EDITAR");
		btn_categoriesEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuCategoriesEdit v10 = new AdminMenuCategoriesEdit(rut,connection);
				v10.setLocationRelativeTo(null);
				v10.setVisible(true);
				dispose();
			}
		});
		btn_categoriesEdit.setBackground(new Color(255, 255, 255));
		btn_categoriesEdit.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn_categoriesEdit.setBounds(10, 168, 160, 40);
		panel_categories.add(btn_categoriesEdit);
		
		JButton btn_categoriesDelete = new JButton("ELIMINAR");
		btn_categoriesDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuCategoriesDelete v7 = new AdminMenuCategoriesDelete(rut,connection);
				v7.setLocationRelativeTo(null);
				v7.setVisible(true);
				dispose();
			}
		});
		btn_categoriesDelete.setBackground(new Color(255, 255, 255));
		btn_categoriesDelete.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn_categoriesDelete.setBounds(10, 288, 160, 40);
		panel_categories.add(btn_categoriesDelete);
		
		JPanel panel_users = new JPanel();
		panel_users.setBorder(new LineBorder(new Color(255, 215, 0), 2, true));
		panel_users.setBackground(new Color(240, 230, 140));
		panel_users.setBounds(560, 70, 180, 386);
		contentPane.add(panel_users);
		panel_users.setLayout(null);
		
		JLabel lbl_users = new JLabel("      USUARIOS");
		lbl_users.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_users.setForeground(new Color(65, 105, 225));
		lbl_users.setBackground(new Color(65, 105, 225));
		lbl_users.setBounds(24, 363, 146, 13);
		panel_users.add(lbl_users);
		
		JButton btn_usersAdd = new JButton("AGREGAR");
		btn_usersAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean admin = true;
				Register v2 = new Register(rut,admin, connection);
				v2.setLocationRelativeTo(null);
				v2.setVisible(true);
				dispose();
			}
		});
		btn_usersAdd.setBackground(new Color(255, 255, 255));
		btn_usersAdd.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn_usersAdd.setBounds(10, 45, 160, 40);
		panel_users.add(btn_usersAdd);
		
		JButton btn_usersEditInfo = new JButton("EDITAR");
		btn_usersEditInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuUsersEdit v11 = new AdminMenuUsersEdit(rut,connection);
				v11.setLocationRelativeTo(null);
				v11.setVisible(true);
				dispose();
			}
		});
		btn_usersEditInfo.setBackground(new Color(255, 255, 255));
		btn_usersEditInfo.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn_usersEditInfo.setBounds(10, 172, 160, 40);
		panel_users.add(btn_usersEditInfo);
		
		JButton btn_usersBan = new JButton("BLOQUEAR");
		btn_usersBan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuUsersBan v7 = new AdminMenuUsersBan(rut,connection);
				v7.setLocationRelativeTo(null);
				v7.setVisible(true);
				dispose();
			}
		});
		btn_usersBan.setBackground(new Color(255, 255, 255));
		btn_usersBan.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btn_usersBan.setBounds(10, 292, 160, 40);
		panel_users.add(btn_usersBan);
		
		JButton btn_adminAccount = new JButton("CUENTA DE ADMINISTRADOR");
		btn_adminAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuInfo v13 = new AdminMenuInfo(connection,rut);
				v13.setLocationRelativeTo(null);
				v13.setVisible(true);
				dispose();
			}
		});
		btn_adminAccount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btn_adminAccount.setBackground(Color.WHITE);
		btn_adminAccount.setBounds(525, 10, 271, 26);
		contentPane.add(btn_adminAccount);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login v1 = new Login(connection);
				v1.setLocationRelativeTo(null);
				v1.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(AdminMenu.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
	}
}
