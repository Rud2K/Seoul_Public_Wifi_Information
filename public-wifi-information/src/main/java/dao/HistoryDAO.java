package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.History;

public class HistoryDAO implements HistoryDAOImpl {
	private static final String DB_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String DB_URL = "jdbc:mariadb://localhost:3306/public_wifi_information";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "1111";
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	@Override
	public void addHistoryData(Double lat, Double lnt) {
		String query = "INSERT INTO history"
				+ "(LAT, LNT)"
				+ "VALUES"
				+ "(?, ?)";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setDouble(1, lat);
			pstmt.setDouble(2, lnt);
			
			pstmt.executeUpdate();
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<History> getHistoryData() {
		List<History> historyList = new ArrayList<>();
		
		String query = "SELECT * "
				+ "FROM history "
				+ "ORDER BY INQUIRY_TIME DESC";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				History history = new History();
				
				history.setID(rs.getInt("ID"));
				history.setLAT(rs.getDouble("LAT"));
				history.setLNT(rs.getDouble("LNT"));
				history.setINQUIRY_TIME(rs.getString("INQUIRY_TIME"));
				
				historyList.add(history);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return historyList;
	}
	
	@Override
	public boolean deleteHistoryData(int id) {
		boolean isDeleted = false;
		String query = "DELETE FROM history WHERE id = ?";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setInt(1, id);
			
			int rowAffected = pstmt.executeUpdate();
			
			if (rowAffected > 0) {
				isDeleted = true;
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return isDeleted;
	}
}
