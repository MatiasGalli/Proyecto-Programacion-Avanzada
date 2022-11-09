package logica;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import interfaz.LoginSOH;

public class Users {	
	public  void getUsers(SQL_Manager connection) {
		String sql;
		Statement st;
		ResultSet rs;
		sql = "Select rut,nombre_completo from usuario order by rut";
		
		try {
			st = connection.getConnection().createStatement();
			rs = st.executeQuery(sql);
			displayUsers(rs);
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		
		
	}
	
	
	public  void displayUsers(ResultSet rs) throws SQLException{
		while(rs.next()) {
			System.out.println(rs.getString("rut") + "\t"
					+ rs.getString("nombre_completo"));
		}
	}

	public void insertUser(SQL_Manager connection,String rut, String nombre_completo,String nombre_usuario,String direccion,String correo,String contraseña) throws SQLException {
		try {
			String sql = "insert into usuario(rut,nombre_completo,nombre_usuario,direccion,correo,administrador,contraseña) values (?,?,?,?,?,?,?)";
		 
			PreparedStatement st = connection.getConnection().prepareStatement(sql);
		
			st.setString(1, rut);
			st.setString(2,nombre_completo);
			st.setString(3,nombre_usuario);
			st.setString(4,direccion);
			st.setString(5,correo);
			st.setBoolean(6,false);
			st.setString(7,contraseña);
			st.executeUpdate();
		}catch(SQLException ex){
			System.out.println(ex.getMessage());
		}
	
	}	
		
	public void deleteUser(SQL_Manager connection,String rut) throws SQLException {
		 String sql = "delete from usuario where rut = ?";
		 PreparedStatement st =connection.getConnection().prepareStatement(sql);
		 
		 st.setString(1, rut);
		 st.executeUpdate();
		 
	}
	
		
		
	}



