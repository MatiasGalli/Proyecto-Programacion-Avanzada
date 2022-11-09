package logica;

import java.io.IOException;
import java.sql.SQLException;

import interfaz.LoginSOH;

//STACK OVERFLOW HOUSE

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		LoginSOH v1 = new LoginSOH();
		v1.setVisible(true);
		SQL_Manager connection = new SQL_Manager();
	}

}
