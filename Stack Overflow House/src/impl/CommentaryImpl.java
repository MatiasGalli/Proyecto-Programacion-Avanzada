package impl;

import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import interfaces.Commentary;
import logic.SQL_Manager;

public class CommentaryImpl implements Commentary {

	@Override
	public boolean existProductComment(SQL_Manager connection, int product_id) {
		boolean exist = false;
		String sql = "select c.product_id from commentary c inner join product p on p.id = c.product_id  inner join users u on u.rut = c.user_rut where c.product_id = ?";

		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, product_id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	@SuppressWarnings("serial")
	@Override
	public JTable updateTableComment(SQL_Manager connection, int product_id){
		JTable table = new JTable();
		table.setShowVerticalLines(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setBorder(null);
		String sql;
		PreparedStatement st;
		ResultSet rs = null;
		//EDITAR CONSULTA (INNER JOIN)
		sql = "select u.username, comment from commentary inner join users u on user_rut = rut where product_id = ?";
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, product_id);
			rs = st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String titles[]={"Nombre del Usuario", "Comentario"};
		DefaultTableModel model = new DefaultTableModel(null,titles) {
			boolean[] columnEditables = new boolean[] {
					false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		String row[]=new String[2];
		try {
			while(rs.next()){
				row[0]=rs.getString("username");
				row[1]=rs.getString("comment");
				model.addRow(row);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(520);
		table.setRowHeight(26);
		table.setBounds(60, 60, 640, 320);
		return (table);
	}
	
	@Override
	public JScrollPane showComments (JTable table){
		try{
			JScrollPane scrollPane_comments = new JScrollPane(table);
			scrollPane_comments.setLocation(60, 60);
			scrollPane_comments.setSize(640, 320);
			scrollPane_comments.setViewportView(table);
			return scrollPane_comments;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		return null;
	}
	
	@Override
	public int countCommentaryID(SQL_Manager connection){

		String sql = "select commentary_id from commentary order by commentary_id desc limit 1";
		int id = 0;
		Statement st;
		try {
			st = connection.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				id = rs.getInt("commentary_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public void addCommentary(SQL_Manager connection, int commentary_id, String user_rut, int product_id,
			String commentary) {
		String sql = "insert into commentary(commentary_id,user_rut,product_id,comment) values(?,?,?,?)";
		PreparedStatement st;
		try {
			st = connection.getConnection().prepareStatement(sql);
			st.setInt(1, commentary_id);
			st.setString(2, user_rut);
			st.setInt(3, product_id);
			st.setString(4, commentary);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
