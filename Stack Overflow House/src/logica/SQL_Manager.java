package logica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Connection established!");

	}

	public Connection getConnection() {
		return connection;
	}

	public void close() {
		connection = null;
		if (connection == null) {
			System.out.println("Connection terminated..");
		}
	}
}
