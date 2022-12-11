package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import interfaces.Category;
import logic.SQL_Manager;

public class CategoryImpl implements Category {
	
	@Override
	public boolean existCategory(SQL_Manager connection){
		boolean exist = false;
		String sql = "select * from category";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	@Override
	public String[] getCategories(SQL_Manager connection, String[] list){

		String[] values = list;
		String sql = "select name from category order by name asc";
		Statement st;
		try {
			st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			int cant = 0;
			while (rs.next()) {
				values[cant] = rs.getString("name");
				cant++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return values;
	}
	
	@Override
	public int countCategories(SQL_Manager connection) {

		String sql = "select count(*) as id from category";
		int id = 0;
		Statement st;
		try {
			st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			id = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public boolean deleteCategory(String rutAdmin, SQL_Manager connection, String name) {
		boolean error = false;
		String sql = "delete from category where name = ?";
		PreparedStatement st = null;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			st.executeUpdate();
		} catch (SQLException e1) {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame,"No se puede eliminar la categoría. Aún existen productos que pertenecen a: " + name);
			error = true;
		}
		return error;
	}
	
	@Override
	public boolean existCategoryName(SQL_Manager connection, String newName){
		boolean exist = false;
		String sql = "select name from category";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (rs.getString("name").equals(newName)) {
					exist = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	@Override
	public void editCategory(String rut, SQL_Manager connection, String category_name, String newName){
		String sql = "select id from category where name = ?";
		PreparedStatement st;
		ResultSet rs;
		int category_id = -1;

		try {
			st = connection.getConnection().prepareStatement(sql);

			st.setString(1, category_name);
			rs = st.executeQuery();
			rs.next();
			category_id = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = "update category set name = ? where id = ?";

		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, newName);
			st.setInt(2, category_id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jFrame, "Categoría actualizada.");

	}
	
	@Override
	public int selectCategoriesID(SQL_Manager connection) {
		
		String sql = "select id from category order by id desc limit 1";
		
		Statement st;
		try {
			st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				int id = rs.getInt("id");
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public String insertCategories(String rut, SQL_Manager connection, int id, String name) {
		String categoryName = "";
		String sql = "select name from category where name = ?";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				categoryName = rs.getString("name");
			}else {
				categoryName = "-1";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (categoryName.equals("-1")) {
			sql = "Insert into category(id,name) values(?,?)";
			PreparedStatement st1;
			try {
				st1 = connection.getConnection().prepareStatement(sql);
				st1.setInt(1, id);
				st1.setString(2, name);
				st1.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame, "¡Categoría creada exitosamente!");

		}else {
			JFrame jFrame = new JFrame();
			jFrame.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jFrame,"El nombre de categoría " + categoryName + " ya existe");
		}
		return categoryName;
	}
}
