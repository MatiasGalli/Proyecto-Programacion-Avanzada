package interfaces;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import logic.SQL_Manager;

public interface Cart {

	
	public int countCartID(SQL_Manager connection);

	public void insertCart(SQL_Manager connection, int id, String user_rut);
	
	public boolean existProductInCart(SQL_Manager connection, int cart_id);
	
	public void addProductCart(SQL_Manager connection, int cart_id, int product_id, int amount);
	
	public boolean existProductCart(SQL_Manager connection, int cart_id, int product_id);
	
	public void addProductInCart(SQL_Manager connection, int cart_id, int product_id, int amount);
	
	public int selectCartId(SQL_Manager connection, String username);
	
	public void deleteCart(SQL_Manager connection, int cart_id);
	
	public int selectStockInCart(SQL_Manager connection, int cart_id, int product_id);
	
	public void deleteProductInCart(SQL_Manager connection, int cart_id, int product_id);
	
	public JTable updateTableCart(SQL_Manager connection, String user_rut);
	
	public int countProductsInCart(SQL_Manager connection,String user_rut);
	
	public JScrollPane showCart(JTable table);

}