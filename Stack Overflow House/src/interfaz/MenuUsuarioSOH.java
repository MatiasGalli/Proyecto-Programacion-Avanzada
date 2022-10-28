package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class MenuUsuarioSOH extends JFrame {

	private JPanel contentPane;
	private JTextField textField_search;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuUsuarioSOH frame = new MenuUsuarioSOH();
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
	public MenuUsuarioSOH() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuUsuarioSOH.class.getResource("/assets/SOH_logo.png")));
		setTitle("Men\u00FA de usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_welcomeMessage = new JLabel("\u00A1Nos alegra que est\u00E9s de vuelta, [USER]! Estos son los productos que se encuentran disponibles:");
		lbl_welcomeMessage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbl_welcomeMessage.setBounds(10, 25, 566, 13);
		contentPane.add(lbl_welcomeMessage);
		
		textField_search = new JTextField();
		textField_search.setBounds(385, 85, 190, 20);
		contentPane.add(textField_search);
		textField_search.setColumns(10);
		
		JLabel lbl_typeToSearch = new JLabel("Ingrese un t\u00E9rmino para buscar");
		lbl_typeToSearch.setBounds(230, 88, 145, 13);
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
		
		JList list_products = new JList();
		list_products.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_products.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		list_products.setModel(new AbstractListModel() {
			String[] values = new String[] {"[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]", "[Nombre]______[Precio]______[Usuario]______[Categor\u00EDa]"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_products.setToolTipText("");
		list_products.setVisibleRowCount(1000);
		list_products.setBounds(230, 127, 404, 213);
		contentPane.add(list_products);
		
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
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(MenuUsuarioSOH.class.getResource("/assets/SOH_logoMin.png")));
		lblNewLabel.setBounds(10, 377, 36, 26);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(632, 127, 18, 213);
		contentPane.add(scrollPane);
		
	}
}
