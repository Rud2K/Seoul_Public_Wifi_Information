package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Wifi;

public class WifiDAO implements WifiDAOImpl{
	private static final String DB_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String DB_URL = "jdbc:mariadb://localhost:3306/public_wifi_information";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "1111";
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	@Override
	public void addWifiData(List<Wifi> wifiList) {
		String query = "INSERT INTO Wifi"
				+ "(X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, "
				+ "X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, "
				+ "X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(query);
			
			for (Wifi wifi : wifiList) {
				pstmt.setString(1, wifi.getX_SWIFI_MGR_NO());
				pstmt.setString(2, wifi.getX_SWIFI_WRDOFC());
				pstmt.setString(3, wifi.getX_SWIFI_MAIN_NM());
				pstmt.setString(4, wifi.getX_SWIFI_ADRES1());
				pstmt.setString(5, wifi.getX_SWIFI_ADRES2());
				pstmt.setString(6, wifi.getX_SWIFI_INSTL_FLOOR());
				pstmt.setString(7, wifi.getX_SWIFI_INSTL_TY());
				pstmt.setString(8, wifi.getX_SWIFI_INSTL_MBY());
				pstmt.setString(9, wifi.getX_SWIFI_SVC_SE());
				pstmt.setString(10, wifi.getX_SWIFI_CMCWR());
				pstmt.setString(11, wifi.getX_SWIFI_CNSTC_YEAR());
				pstmt.setString(12, wifi.getX_SWIFI_INOUT_DOOR());
				pstmt.setString(13, wifi.getX_SWIFI_REMARS3());
				pstmt.setDouble(14, wifi.getLAT());
				pstmt.setDouble(15, wifi.getLNT());
				pstmt.setTimestamp(16, Timestamp.valueOf(wifi.getWORK_DTTM()));
				
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Wifi> getNearbyWifiData(Double lat, Double lnt, double distance) {
		List<Wifi> wifiList = new ArrayList<>();
		
		String query = "SELECT *,"
				+ "(6371 * ACOS("
				+ "COS(RADIANS(?)) * COS(RADIANS(LAT)) *"
				+ "COS(RADIANS(LNT) - RADIANS(?)) +"
				+ "SIN(RADIANS(?)) * SIN(RADIANS(LAT)))) "
				+ "AS distance "
				+ "FROM wifi "
				+ "HAVING distance <= ? "
				+ "LIMIT 20";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setDouble(1, lat);
			pstmt.setDouble(2, lnt);
			pstmt.setDouble(3, lat);
			pstmt.setDouble(4, distance);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Wifi wifi = new Wifi();
				
				wifi.setDistance(rs.getDouble("distance"));
				wifi.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
				wifi.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
				wifi.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
				wifi.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
				wifi.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
				wifi.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
				wifi.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
				wifi.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
				wifi.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
				wifi.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
				wifi.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
				wifi.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
				wifi.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
				wifi.setLAT(rs.getDouble("LAT"));
				wifi.setLNT(rs.getDouble("LNT"));
				wifi.setWORK_DTTM(rs.getString("WORK_DTTM"));
				
				wifiList.add(wifi);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		Collections.sort(wifiList, Comparator.comparingDouble(Wifi::getDistance));
		
		return wifiList;
	}
	
	@Override
	public Map<String, Double> getLatLntByMgrNo(String mgrNo) {
		Map<String, Double> map = new HashMap<>();
		
		String query = "SELECT LAT, LNT FROM Wifi WHERE X_SWIFI_MGR_NO = ?";
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setString(1, mgrNo);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				map.put("LAT", rs.getDouble("LAT"));
				map.put("LNT", rs.getDouble("LNT"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	@Override
	public Wifi getDetailWifiData(String mgrNo, Double lat, Double lnt) {
		Wifi wifi = new Wifi();
		
		String query = "SELECT *,"
				+ "(6371 * ACOS("
				+ "COS(RADIANS(?)) * COS(RADIANS(LAT)) *"
				+ "COS(RADIANS(LNT) - RADIANS(?)) +"
				+ "SIN(RADIANS(?)) * SIN(RADIANS(LAT)))) "
				+ "AS distance "
				+ "FROM wifi "
				+ "WHERE wifi.X_SWIFI_MGR_NO = ?";
		
		if (lat == null || lnt == null) {
			if (mgrNo != null && !mgrNo.isEmpty()) {
				Map<String, Double> latLntMap = this.getLatLntByMgrNo(mgrNo);
				lat = latLntMap.get("LAT");
				lnt = latLntMap.get("LNT");
			}
		}
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			pstmt.setDouble(1, lat);
			pstmt.setDouble(2, lnt);
			pstmt.setDouble(3, lat);
			pstmt.setString(4, mgrNo);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				wifi.setDistance(rs.getDouble("distance"));
				wifi.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
				wifi.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
				wifi.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
				wifi.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
				wifi.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
				wifi.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
				wifi.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
				wifi.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
				wifi.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
				wifi.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
				wifi.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
				wifi.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
				wifi.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
				wifi.setLAT(rs.getDouble("LAT"));
				wifi.setLNT(rs.getDouble("LNT"));
				wifi.setWORK_DTTM(rs.getString("WORK_DTTM"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return wifi;
	}
}
