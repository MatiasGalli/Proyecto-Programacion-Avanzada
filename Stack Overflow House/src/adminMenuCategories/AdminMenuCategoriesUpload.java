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
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
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
					AdminMenuCategoriesUpload frame = new AdminMenuCategoriesUpload("",null,null,null,null,null,null,null);
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
	public AdminMenuCategoriesUpload(String rut, SQL_Manager connection,CartImpl cartImpl,CategoryImpl categoryImpl,CommentaryImpl commentaryImpl,ProductImpl productImpl,
			PurchaseImpl purchaseImpl, UserImpl userImpl) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenuCategoriesUpload.class.getResource("/assets/SOH_logo.png")));
		setAlwaysOnTop(true);
		setTitle("Subir Categoría");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 260);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_name = new JLabel("Nombre de la categor\u00EDa que desea crear");
		lbl_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_name.setBounds(35, 55, 380, 26);
		contentPane.add(lbl_name);

		textField_name = new JTextField();
		textField_name.setBounds(35, 91, 380, 26);
		contentPane.add(textField_name);
		textField_name.setColumns(10);

		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(AdminMenuCategoriesUpload.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 187, 36, 26);
		contentPane.add(lbl_SOHLogo);

		JButton btn_upload = new JButton("Crear");
		btn_upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean nameEmpty = textField_name.getText().equals("");

				if (!nameEmpty) {
					int id = -1;
					id = categoryImpl.selectCategoriesID(connection);
					String name = textField_name.getText();
					String categoryName = categoryImpl.insertCategories(rut,connection, (id + 1), name);
					if (categoryName.equals("-1")){
						AdminMenu v4 = new AdminMenu(rut,connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
						v4.setLocationRelativeTo(null);
						v4.setVisible(true);
						dispose();
					}
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "No se le ha asignado ningún nombre a la categoría.");
				}
			}

		});
		btn_upload.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btn_upload.setBackground(new Color(255, 255, 255));
		btn_upload.setBounds(172, 149, 100, 26);
		contentPane.add(btn_upload);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu v4 = new AdminMenu(rut,connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
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
	}
}