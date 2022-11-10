package logica;

import java.io.IOException;
import java.sql.SQLException;

import interfaz.Login;

//STACK OVERFLOW HOUSE

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		SQL_Manager connection = new SQL_Manager();
		Login v1 = new Login(connection);
		v1.setVisible(true);
	}

}
