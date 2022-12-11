package interfaces;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import logic.SQL_Manager;

public interface Purchase {
	
	public int countHistoryID(SQL_Manager connection);

	public void insertHistoryID(SQL_Manager connection, int history_id, String user_rut);
	
	public boolean purchaseHistoryExists(SQL_Manager connection, int history_id);
	
	public int selectHistoryId(SQL_Manager connection, String username);
	
	public int selectHistoryIDRut(SQL_Manager connection, String username);
	
	public void insertHistoryProduct(SQL_Manager connection, int id, int history_id, String product_name, int amount);
	
	public int countHistoryProductID(SQL_Manager connection);
	
	public JTable updateTablePurchase(SQL_Manager connection, String user_rut);

	public JScrollPane showHistory(JTable table);
}
