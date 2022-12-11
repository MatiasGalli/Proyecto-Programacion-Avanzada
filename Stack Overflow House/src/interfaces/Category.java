package interfaces;

import logic.SQL_Manager;

public interface Category {
	
	public boolean existCategory(SQL_Manager connection);
	
	public String[] getCategories(SQL_Manager connection, String[] list);
	
	public int countCategories(SQL_Manager connection);
	
	public boolean deleteCategory(String rutAdmin, SQL_Manager connection, String name);
	
	public boolean existCategoryName(SQL_Manager connection, String newName);
	
	public void editCategory(String rut, SQL_Manager connection, String category_name, String newName);
	
	public int selectCategoriesID(SQL_Manager connection);
	
	public String insertCategories(String rut, SQL_Manager connection, int id, String name);
	
}
