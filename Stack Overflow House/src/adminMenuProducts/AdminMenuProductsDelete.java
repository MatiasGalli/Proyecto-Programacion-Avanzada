package adminMenuProducts;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import adminMenu.AdminMenu;
import impl.CartImpl;
import impl.CategoryImpl;
import impl.CommentaryImpl;
import impl.ProductImpl;
import impl.PurchaseImpl;
import impl.UserImpl;
import logic.SQL_Manager;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.awt.Font;

@SuppressWarnings("serial")
public class AdminMenuProductsDelete extends JFrame {

	private JPanel contentPane;
	private JTable table_products;
	private JScrollPane scrollPane_products;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuProductsDelete frame = new AdminMenuProductsDelete("",null,null,null,null,null,null,null);
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
	public AdminMenuProductsDelete(String rut, SQL_Manager connection,CartImpl cartImpl,CategoryImpl categoryImpl,CommentaryImpl commentaryImpl,ProductImpl productImpl,
			PurchaseImpl purchaseImpl, UserImpl userImpl) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenuProductsDelete.class.getResource("/assets/SOH_logo.png")));
		setBackground(new Color(255, 250, 205));
		setTitle("Eliminar Producto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table_products = new JTable();
		scrollPane_products = new JScrollPane();
		
		table_products = productImpl.updateTableProductDelete(connection);
		scrollPane_products = productImpl.showProductsDelete(table_products);
		contentPane.add(scrollPane_products);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu v4 = new AdminMenu(rut,connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(AdminMenuProductsDelete.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(AdminMenuProductsDelete.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 467, 36, 26);
		contentPane.add(lbl_SOHlogo);
		
		JButton btn_delete = new JButton("ELIMINAR");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_products.getSelectedRow();
				if (row != -1) {
					int id =  Integer.parseInt((String)table_products.getValueAt(row, 0));
					productImpl.deleteProduct(connection,id);
					table_products = productImpl.updateTableProductDelete(connection);
					scrollPane_products = productImpl.showProductsDelete(table_products);
					remove(contentPane.getComponentAt(60, 120));
					contentPane.add(scrollPane_products);
				}else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame,"Selecciona un producto.");
				}
			}
		});
		btn_delete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_delete.setBackground(Color.WHITE);
		btn_delete.setBounds(400, 420, 138, 26);
		contentPane.add(btn_delete);
		
		JLabel lbl_select = new JLabel("Seleccione un producto de la lista:");
		lbl_select.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_select.setBounds(60, 35, 359, 26);
		contentPane.add(lbl_select);
	}

}
