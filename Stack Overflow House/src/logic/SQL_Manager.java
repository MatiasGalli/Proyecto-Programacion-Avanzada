package logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SQL_Manager {
	private static Connection connection;
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String USER = "ikeusjsy";
	private static final String PASSWORD = "1UHViXHTQh0M7Ly4tyM4kp8k6LwRVjUy";
	private static final String URL = "jdbc:postgresql://babar.db.elephantsql.com:5432/ikeusjsy";

	public SQL_Manager() throws ClassNotFoundException, SQLException, IOException {
		connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "Error al iniciar. El servidor no soporta otra conexion o la red no es estable.");
		}

	}

	public Connection getConnection() {
		return connection;
	}

	public void close() {
		connection = null;
	}
}
