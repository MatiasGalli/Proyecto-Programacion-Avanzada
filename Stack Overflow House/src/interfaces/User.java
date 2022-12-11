package interfaces;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import logic.SQL_Manager;

public interface User {
	
	public boolean existUsername(SQL_Manager connection, String newUsername);

	public boolean existEmail(SQL_Manager connection, String newEmail);
	
	public void insertUser(SQL_Manager connection, String rut, String fullname, String username, String address,
			String email, String password);
	
	public String selectUserRut(SQL_Manager connection, String username);
	
	public void editAddress(String rut, SQL_Manager connection, String address);
	
	public void editEmail(String rut, SQL_Manager connection, String email);
	
	public void editFullname(String rut, SQL_Manager connection, String fullname);
	
	public void editUsername(String rut, SQL_Manager connection, String username);
	
	public String selectUsername(SQL_Manager connection, String rut);
	
	public String selectUserFullname(SQL_Manager connection, String username);
	
	public String selectUserAddress(SQL_Manager connection, String username);
	
	public String selectUserEmail(SQL_Manager connection, String username);
	
	public void editPassword(String rut, SQL_Manager connection, String newPassword);
	
	public boolean selectCurrentPassword(SQL_Manager connection, String user_rut, String current);
	
	public JTable searchUser(SQL_Manager connection, String category, String search);
	
	public JTable updateTableUsers(SQL_Manager connection, String category, String asc);
	
	public JScrollPane showUsers (JTable table);
	
	public void banUser(SQL_Manager connection, String rut, boolean ban, String order, String asc);
	
	public void editUsers(SQL_Manager connection,String users_dni,String name, String password, boolean admin);
	
	public String[] getUsersNoAdmin(SQL_Manager connection, String[] list);
	
	public int countUsersNoAdmin(SQL_Manager connection);
}
