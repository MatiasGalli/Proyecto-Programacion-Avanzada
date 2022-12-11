package adminMenu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AdminMenuChangePassword extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField_current;
	private JPasswordField passwordField_new;
	private JPasswordField passwordField_confirm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenuChangePassword frame = new AdminMenuChangePassword("",null,null,null,null,null,null,null);
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
	public AdminMenuChangePassword(String rut, SQL_Manager connection,CartImpl cartImpl,CategoryImpl categoryImpl,CommentaryImpl commentaryImpl,ProductImpl productImpl,
			PurchaseImpl purchaseImpl, UserImpl userImpl) {
		setTitle("Cambiar Contrase\u00F1a");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenuChangePassword.class.getResource("/assets/SOH_logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_SOHLogo = new JLabel("Logo");
		lbl_SOHLogo.setIcon(new ImageIcon(AdminMenuChangePassword.class.getResource("/assets/SOH_logoMin.png")));
		lbl_SOHLogo.setBounds(10, 287, 36, 26);
		contentPane.add(lbl_SOHLogo);
		
		JButton btn_back = new JButton();
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuInfo v13 = new AdminMenuInfo(connection,rut,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
				v13.setLocationRelativeTo(null);
				v13.setVisible(true);
				dispose();
			}
		});
		btn_back.setIcon(new ImageIcon(AdminMenuChangePassword.class.getResource("/assets/back.png")));
		btn_back.setBorder(null);
		btn_back.setBackground((Color) null);
		btn_back.setBounds(10, 10, 30, 30);
		contentPane.add(btn_back);
		
		JLabel lbl_currentPassword = new JLabel("Contrase\u00F1a actual:");
		lbl_currentPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_currentPassword.setBounds(50, 55, 204, 26);
		contentPane.add(lbl_currentPassword);
		
		JLabel lbl_newPassword = new JLabel("Nueva contrase\u00F1a:");
		lbl_newPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_newPassword.setBounds(50, 104, 204, 26);
		contentPane.add(lbl_newPassword);
		
		JLabel lbl_search_1_1 = new JLabel("Confirmar contrase\u00F1a:");
		lbl_search_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_search_1_1.setBounds(50, 152, 204, 26);
		contentPane.add(lbl_search_1_1);
		
		passwordField_current = new JPasswordField();
		passwordField_current.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField_current.setBounds(231, 55, 345, 26);
		contentPane.add(passwordField_current);
		
		passwordField_new = new JPasswordField();
		passwordField_new.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField_new.setBounds(231, 104, 345, 26);
		contentPane.add(passwordField_new);
		
		passwordField_confirm = new JPasswordField();
		passwordField_confirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField_confirm.setBounds(264, 152, 312, 26);
		contentPane.add(passwordField_confirm);
		
		JButton btn_changePassword = new JButton("CAMBIAR CONTRASE\u00D1A");
		btn_changePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentPassword = String.valueOf(passwordField_current.getPassword());
				String newPassword = String.valueOf(passwordField_new.getPassword());
				String confirmPassword = String.valueOf(passwordField_confirm.getPassword());
				boolean exist = false;
				boolean compare = false;
				try {
					exist = userImpl.selectCurrentPassword(connection,rut,currentPassword);
					compare = comparePassword(connection);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (currentPassword.equals("")) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar algo en el campo de Contraseña actual");
				}else if(newPassword.equals("")) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar algo en el campo de Contraseña nueva");
				}else if(confirmPassword.equals("")) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar algo en el campo de Confirmar contraseña");
				}else if(newPassword.equals(currentPassword)) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Debes ingresar una contraseña nueva diferente a la actual");
				}else if(!exist) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Contraseña actual incorrecta");
				}else if(!compare) {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jFrame, "Las contraseñas nuevas no coinciden");
				}else {
					userImpl.editPassword(rut,connection,newPassword);
					AdminMenuInfo v13 = new AdminMenuInfo(connection,rut,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
					v13.setLocationRelativeTo(null);
					v13.setVisible(true);
					dispose();
				}
			}
		});
		btn_changePassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_changePassword.setBackground(Color.WHITE);
		btn_changePassword.setBounds(180, 221, 273, 26);
		contentPane.add(btn_changePassword);
	}
	
	public boolean comparePassword(SQL_Manager connection) throws SQLException {
		boolean equal = false;
		String newPassword = String.valueOf(passwordField_new.getPassword());
		String confirmPassword = String.valueOf(passwordField_confirm.getPassword());
		if (newPassword.equals(confirmPassword)) {
			equal = true;
		}
		return equal;
	}

}
