package interfaces;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import logic.SQL_Manager;

public interface Commentary {
	
	public boolean existProductComment(SQL_Manager connection, int product_id);
	
	public JTable updateTableComment(SQL_Manager connection, int product_id);
	
	public JScrollPane showComments (JTable table);
	
	public int countCommentaryID(SQL_Manager connection);
	
	public void addCommentary(SQL_Manager connection, int commentary_id, String user_rut, int product_id,
			String commentary);
}
