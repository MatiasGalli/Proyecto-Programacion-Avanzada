package userMenu;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import impl.CartImpl;
import impl.CategoryImpl;
import impl.CommentaryImpl;
import impl.ProductImpl;
import impl.PurchaseImpl;
import impl.UserImpl;
import logic.SQL_Manager;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UserMenuPurchaseHistory extends JFrame {

	private JPanel contentPane;
	private JTable table_history;
	private JScrollPane scrollPane_history;
	private JTextField textField_commentary;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMenuPurchaseHistory frame = new UserMenuPurchaseHistory("", null,null,null,null,null,null,null);
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
	public UserMenuPurchaseHistory(String user, SQL_Manager connection,CartImpl cartImpl,CategoryImpl categoryImpl,CommentaryImpl commentaryImpl,ProductImpl productImpl,
			PurchaseImpl purchaseImpl, UserImpl userImpl) {
		setTitle("Registro de Compras");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(UserMenuPurchaseHistory.class.getResource("/assets/SOH_logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_SOHlogo = new JLabel("Image");
		lbl_SOHlogo.setIcon(new ImageIcon(UserMenuPurchaseHistory.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHlogo.setBounds(10, 407, 36, 26);
		contentPane.add(lbl_SOHlogo);

		table_history = new JTable();
		scrollPane_history = new JScrollPane();
		String user_rut = userImpl.selectUserRut(connection, user);
		table_history = purchaseImpl.updateTablePurchase(connection, user_rut);
		scrollPane_history = purchaseImpl.showHistory(table_history);
		contentPane.add(scrollPane_history);

		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMenuAccount v4 = new UserMenuAccount(user, connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
				v4.setLocationRelativeTo(null);
				v4.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(UserMenuPurchaseHistory.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);

		JButton btn_comment = new JButton("COMENTAR PRODUCTO");
		btn_comment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_history.getSelectedRow();
				if (row != -1) {
					String product_name = (String) table_history.getValueAt(row, 0);
					int product_id = productImpl.selectProductID(connection, product_name);
					String commentary = textField_commentary.getText();

					if (commentary.equals("")) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "Debes agregar un comentario.");
					} else if (product_id == 0) {
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame,
								"El comentario no se pudo agregar. Puede que el producto no se encuentre en el sistema o tenga otro nombre.");
					} else {
						String user_rut = userImpl.selectUserRut(connection, user);
						int commentary_id = commentaryImpl.countCommentaryID(connection);
						commentaryImpl.addCommentary(connection, commentary_id + 1, user_rut, product_id, commentary);
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jFrame, "Comentario Agregado.");
					}
				} else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Selecciona un producto.");
				}
			}
		});
		btn_comment.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_comment.setBackground(Color.WHITE);
		btn_comment.setBounds(232, 383, 273, 26);
		contentPane.add(btn_comment);

		JLabel lbl_commentary = new JLabel("Comentario:");
		lbl_commentary.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_commentary.setBounds(36, 347, 108, 26);
		contentPane.add(lbl_commentary);

		textField_commentary = new JTextField();
		textField_commentary.setText((String) null);
		textField_commentary.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_commentary.setColumns(10);
		textField_commentary.setBounds(130, 347, 593, 26);
		contentPane.add(textField_commentary);

		JLabel lbl_yourHistory = new JLabel("Esta es la lista de productos que has comprado:");
		lbl_yourHistory.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_yourHistory.setBounds(61, 23, 578, 26);
		contentPane.add(lbl_yourHistory);
	}
}