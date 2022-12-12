package adminMenuCategories;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import adminMenu.AdminMenu;
import impl.CartImpl;
import impl.CategoryImpl;
import impl.CommentaryImpl;
import impl.ProductImpl;
import impl.PurchaseImpl;
import impl.UserImpl;
import logic.SQL_Manager;

import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AdminMenuCategoriesEdit extends JFrame {

	private JPanel contentPane;
	private JTextField textField_name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuCategoriesEdit frame = new AdminMenuCategoriesEdit("", null, null, null, null, null, null,
							null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param rut
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AdminMenuCategoriesEdit(String rut, SQL_Manager connection, CartImpl cartImpl, CategoryImpl categoryImpl,
			CommentaryImpl commentaryImpl, ProductImpl productImpl, PurchaseImpl purchaseImpl, UserImpl userImpl) {
		setBackground(new Color(255, 250, 205));
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(AdminMenuCategoriesEdit.class.getResource("/assets/SOH_logo.png")));
		setTitle("Editar Categor\u00EDa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(AdminMenuCategoriesEdit.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 287, 36, 26);
		contentPane.add(lbl_SOHlogo);

		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu v4 = new AdminMenu(rut, connection, cartImpl, categoryImpl, commentaryImpl, productImpl,
						purchaseImpl, userImpl);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(AdminMenuCategoriesEdit.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);

		JComboBox comboBox_category = new JComboBox();
		comboBox_category.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox_category.setBounds(65, 96, 495, 26);
		int cant = 0;
		try {
			cant = categoryImpl.countCategories(connection);
		} catch (NumberFormatException e3) {
			e3.printStackTrace();
		}
		String[] list = new String[cant];
		String[] values = null;
		values = categoryImpl.getCategories(connection, list);
		comboBox_category.setModel(new DefaultComboBoxModel(values));
		contentPane.add(comboBox_category);

		JButton btn_delete = new JButton("EDITAR");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String category_name = (String) comboBox_category.getSelectedItem();
				String name = textField_name.getText();
				boolean exist = categoryImpl.existCategoryName(connection, name);
				if (name.equals("")) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar un nombre para editar");
				} else if (exist && !name.equals(category_name)) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Este nombre de categoría ya existe");
				} else {
					categoryImpl.editCategory(rut, connection, category_name, name);
					AdminMenu v4 = new AdminMenu(rut, connection, cartImpl, categoryImpl, commentaryImpl,
							productImpl, purchaseImpl, userImpl);
					v4.setLocationRelativeTo(null);
					v4.setVisible(true);
					dispose();
				}
			}
		});
		btn_delete.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btn_delete.setBackground(Color.WHITE);
		btn_delete.setBounds(235, 220, 150, 30);
		contentPane.add(btn_delete);

		JLabel lbl_select = new JLabel("Seleccione una categoria de la lista:");
		lbl_select.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_select.setBounds(65, 60, 359, 26);
		contentPane.add(lbl_select);

		JLabel lbl_newName = new JLabel("Nuevo nombre:");
		lbl_newName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_newName.setBounds(65, 159, 150, 26);
		contentPane.add(lbl_newName);

		textField_name = new JTextField();
		textField_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_name.setColumns(10);
		textField_name.setBounds(213, 159, 347, 26);
		contentPane.add(textField_name);
	}
}
