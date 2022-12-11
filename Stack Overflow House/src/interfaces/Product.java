package interfaces;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import logic.SQL_Manager;

public interface Product {

	public boolean existProduct(SQL_Manager connection);
	
	public void substractStockMenu(SQL_Manager connection, int amount, int product_id);
	
	public String seeDescription(SQL_Manager connection, int product_id);
	
	public JTable updateTableProductOrder(SQL_Manager connection, String category, String asc);

	public JTable searchProduct(SQL_Manager connection, String category, String search);
	
	public JTable updateTableProduct(SQL_Manager connection);
	
	public JScrollPane showProducts(JTable table);
	
	public int countProducts(SQL_Manager connection, String user_rut);
	
	public int selectRemainingStock(SQL_Manager connection,int product_id);
	
	public void substractStock(SQL_Manager connection, int amount, int product_id);
	
	public void addStock(SQL_Manager connection, int amount, int product_id) ;
	
	public void substractProductStock(SQL_Manager connection, int cart_id, int product_id, int amount);
	
	public int productPrice(SQL_Manager connection, String user_rut, int product_id);
	
	public int totalPrice(SQL_Manager connection, String user_rut);
	
	public String selectProductName (SQL_Manager connection, int product_id);
	
	public int selectProductID(SQL_Manager connection, String product_name);
	
	public void deleteProduct(SQL_Manager connection, int id);
	
	public JTable updateTableProductDelete(SQL_Manager connection);
	
	public JScrollPane showProductsDelete (JTable table);
	
	public void editProduct(String rut, SQL_Manager connection, String product_name, String name, float price,
			int stock, String description);
	
	public String[] getProducts(SQL_Manager connection, String[] list);
	
	public int countProductsEdit(SQL_Manager connection);
	
	public boolean existProductWithName(SQL_Manager connection, String newName);
	
	public int countProductsID(SQL_Manager connection);
	
	public void insertProduct(SQL_Manager connection, int id, String name, String description, float price,
			int stock, int category_id);

}
