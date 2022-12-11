package logica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RedeemCodes {
	
	public void addCode(SQL_Manager  connection, String code, int saldo) throws SQLException {
		
		try {
			Statement st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from redeemCodes where code = ?");
			if (rs.next()) {
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame, "Ese c\u00F3digo ya existe y a\u00FAn no ha sido cobrado.");
			} else {
				PreparedStatement st2 = connection.getConnection().prepareStatement("insert into redeemCodes(code,dinero) values(?,?)");
				st2.setString(1, code);
				st2.setInt(2, saldo);
				st2.executeUpdate();
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(jFrame, "c\u00F3digo de retribuci\u00F3n monetaria virtual creado exitosamente!");
			}
			
		} catch (SQLException ex) {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Hubo un error inesperado. Contactar Stack Overflow House");
			System.out.println("ERROR EN ELIMINACI\u00D3N DE CODIGO. DETALLES: " + ex.getMessage());
		}
					
	}
	
	public void deleteCode(SQL_Manager  connection, String code) throws SQLException {
		
		try {
			String sql = "alter table reedemCodes delete * where code = ?";
			PreparedStatement st = connection.getConnection().prepareStatement(sql);
			st.setString(1, code);
			st.executeUpdate();	
		} catch (SQLException ex) {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "El c\u00F3digo ingresado no existe.");
			System.out.println("ERROR EN ELIMINACI\u00D3N DE CODIGO. DETALLES: " + ex.getMessage());
		}
	}
	
	public int redeemCode(SQL_Manager connection, String code) throws SQLException {
		try {
			Statement st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from redeemCodes where code = ?");
			rs.next();
			int cashFlow = rs.getInt("dinero");
			deleteCode(connection,code);
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "¡El c\u00F3digo ha sido usado exitosamente!");
			return cashFlow;
		} catch (SQLException ex) {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "No es un c\u00F3digo v\u00E1lido");
			System.out.println("ERROR EN RETIRO DE CODIGO. DETALLES: " + ex.getMessage());
			return 0;
		}
	}
}
