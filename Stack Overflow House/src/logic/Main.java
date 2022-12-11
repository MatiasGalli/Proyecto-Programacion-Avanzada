package logic;

import java.io.IOException;
import java.sql.SQLException;

import impl.*;
import init.Login;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		CartImpl cartImpl = new CartImpl();
		CategoryImpl categoryImpl = new CategoryImpl();
		CommentaryImpl commentaryImpl = new CommentaryImpl();
		ProductImpl productImpl = new ProductImpl();
		PurchaseImpl purchaseImpl = new PurchaseImpl();
		UserImpl userImpl = new UserImpl();
		SQL_Manager connection = new SQL_Manager();
		try {
			if (!connection.getConnection().equals(null)) {
				Login v1 = new Login(connection,cartImpl,categoryImpl,commentaryImpl,productImpl,purchaseImpl,userImpl);
				v1.setLocationRelativeTo(null);
				v1.setVisible(true);
			}
		}catch(Exception e) {
			System.exit(0);
		}
		
	}
}
