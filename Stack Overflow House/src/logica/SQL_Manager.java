package logica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL_Manager {
	private static Connection connection;
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String USER = "postgres";
	private static final String PASSWORD = "geminianoucn2021";
	private static final String URL = "jdbc:postgresql://localhost:5432/StackOverflowHouse";

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
