package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Bookmark;
import dto.BookmarkGroup;

public class BookmarkDAO implements BookmarkDAOImpl {
	private static final String DB_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String DB_URL = "jdbc:mariadb://localhost:3306/public_wifi_information";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "1111";
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	@Override
	public List<Bookmark> getBookmarkList() {
		List<Bookmark> bookmarkList = new ArrayList<>();
		
		String query = "SELECT * "
				+ "FROM BookmarkList "
				+ "ORDER BY REGISTER_DTTM DESC";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Bookmark bookmark = new Bookmark();
				
				bookmark.setID(rs.getInt("ID"));
				bookmark.setGROUP_ID(rs.getInt("GROUP_ID"));
				bookmark.setNAME(rs.getString("NAME"));
				bookmark.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
				bookmark.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
				bookmark.setREGISTER_DTTM(rs.getString("REGISTER_DTTM"));
				
				bookmarkList.add(bookmark);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return bookmarkList;
	}
	
	@Override
	public List<BookmarkGroup> getBookmarkGroupList() {
		List<BookmarkGroup> bookmarkGroupList = new ArrayList<>();
		
		String query = "SELECT * "
				+ "FROM BookmarkGroup "
				+ "ORDER BY REGISTER_DTTM DESC";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BookmarkGroup bookmarkGroup = new BookmarkGroup();
				
				bookmarkGroup.setID(rs.getInt("ID"));
				bookmarkGroup.setNAME(rs.getString("NAME"));
				bookmarkGroup.setORDER_NO(rs.getInt("ORDER_NO"));
				bookmarkGroup.setREGISTER_DTTM(rs.getString("REGISTER_DTTM"));
				bookmarkGroup.setUPDATE_DTTM(rs.getString("UPDATE_DTTM"));
				
				bookmarkGroupList.add(bookmarkGroup);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return bookmarkGroupList;
	}
	
	@Override
	public BookmarkGroup getBookmarkGroup(int id) {
		BookmarkGroup bookmarkGroup = new BookmarkGroup();
		
		String query = "SELECT * "
				+ "FROM BookmarkGroup "
				+ "WHERE id = ?";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				bookmarkGroup.setID(rs.getInt("ID"));
				bookmarkGroup.setNAME(rs.getString("NAME"));
				bookmarkGroup.setORDER_NO(rs.getInt("ORDER_NO"));
				bookmarkGroup.setREGISTER_DTTM(rs.getString("REGISTER_DTTM"));
				bookmarkGroup.setUPDATE_DTTM(rs.getString("UPDATE_DTTM"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return bookmarkGroup;
	}
	
	@Override
	public boolean addBookmarkGroup(String name, int orderNo) {
		boolean isSuccess = false;
		
		String query = "INSERT INTO BookmarkGroup "
				+ "(NAME, ORDER_NO, REGISTER_DTTM) "
				+ "VALUES (?, ?, NOW())";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setString(1, name);
			pstmt.setInt(2, orderNo);
			
			isSuccess = (pstmt.executeUpdate() > 0);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	@Override
	public boolean updateBookmarkGroup(int id, String name, int orderNo) {
		boolean isSuccess = false;
		
		String query = "UPDATE BookmarkGroup "
				+ "SET NAME = ?, ORDER_NO = ? , UPDATE_DTTM = NOW()"
				+ "WHERE ID = ?";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setString(1, name);
			pstmt.setInt(2, orderNo);
			pstmt.setInt(3, id);
			
			isSuccess = (pstmt.executeUpdate() > 0);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	@Override
	public boolean deleteBookmarkGroup(int groupId) {
		boolean isDeleted = false;
		
		String query = "DELETE FROM BookmarkGroup WHERE ID = ?";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setInt(1, groupId);
			
			isDeleted = (pstmt.executeUpdate() > 0);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return isDeleted;
	}
	
	@Override
	public boolean addBookmark(int groupId, String wifiMgrNo) {
		boolean isSuccess = false;
		
		String query = "INSERT INTO BookmarkList "
				+ "(GROUP_ID, X_SWIFI_MGR_NO, X_SWIFI_MAIN_NM, NAME, REGISTER_DTTM) "
				+ "SELECT ?, ?, "
				+ "(SELECT X_SWIFI_MAIN_NM FROM Wifi WHERE X_SWIFI_MGR_NO = ?), "
				+ "(SELECT NAME FROM BookmarkGroup WHERE ID = ?), "
				+ "NOW()";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setInt(1, groupId);
			pstmt.setString(2, wifiMgrNo);
			pstmt.setString(3, wifiMgrNo);
			pstmt.setInt(4, groupId);
			
			isSuccess = (pstmt.executeUpdate() > 0);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	@Override
	public boolean deleteBookmark(int groupId) {
		boolean isDeleted = false;
		
		String query = "DELETE FROM BookmarkList WHERE ID = ?";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setInt(1, groupId);
			
			isDeleted = (pstmt.executeUpdate() > 0);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return isDeleted;
	}
	
	@Override
	public Bookmark getBookmark(int bookmarkId) {
		Bookmark bookmark = new Bookmark();
		
		String query = "SELECT * "
				+ "FROM BookmarkList "
				+ "WHERE id = ?";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setInt(1, bookmarkId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				bookmark.setID(rs.getInt("ID"));
				bookmark.setGROUP_ID(rs.getInt("GROUP_ID"));
				bookmark.setNAME(rs.getString("NAME"));
				bookmark.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
				bookmark.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
				bookmark.setREGISTER_DTTM(rs.getString("REGISTER_DTTM"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return bookmark;
	}
}
