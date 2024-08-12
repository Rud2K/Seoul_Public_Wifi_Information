package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.WifiDAO;
import dto.Wifi;

public class WifiService implements WifiServiceImpl {
	private static final String API_KEY = ConfigManager.getProperty("api.key.seoul_wifi");
	private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/JSON/TbPublicWifiInfo/1/1000";
	
	private WifiDAO wifiDAO;
	
	public WifiService() {
		this.wifiDAO = new WifiDAO();
	}
	
	@Override
	public String formattedDttm(LocalDateTime date) {
		final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
		return date.format(FORMATTER);
	}
	
	@Override
	public LocalDateTime parseDateTime(String dateTimeStr) {
		return dateTimeStr == null || dateTimeStr.isEmpty()
				? null : LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	@Override
	public int getTotalCount() {
		this.wifiDAO.addWifiData(this.getWifiList());
		return this.callApi().getInt("list_total_count");
	}
	
	@Override
	public List<Wifi> getNearbyWifi(Double lat, Double lnt, double distance) {
		return this.wifiDAO.getNearbyWifiData(lat, lnt, distance);
	}
	
	@Override
	public Wifi getDetailWifiInfo(String mgrNo, Double lat, Double lnt) {
		return this.wifiDAO.getDetailWifiData(mgrNo, lat, lnt);
	}
	
	@Override
	public List<Wifi> getWifiList() {
		JSONArray wifiArr = this.callApi().getJSONArray("row");
		List<Wifi> wifiList = new ArrayList<>();
		
		for (int i = 0; i < wifiArr.length(); i++) {
			Wifi wifi = new Wifi();
			JSONObject wifiObject = wifiArr.getJSONObject(i);
			
			wifi.setX_SWIFI_MGR_NO(wifiObject.getString("X_SWIFI_MGR_NO"));
			wifi.setX_SWIFI_WRDOFC(wifiObject.getString("X_SWIFI_WRDOFC"));
			wifi.setX_SWIFI_MAIN_NM(wifiObject.getString("X_SWIFI_MAIN_NM"));
			wifi.setX_SWIFI_ADRES1(wifiObject.getString("X_SWIFI_ADRES1"));
			wifi.setX_SWIFI_ADRES2(wifiObject.getString("X_SWIFI_ADRES1"));
			wifi.setX_SWIFI_INSTL_FLOOR(wifiObject.getString("X_SWIFI_INSTL_FLOOR"));
			wifi.setX_SWIFI_INSTL_MBY(wifiObject.getString("X_SWIFI_INSTL_MBY"));
			wifi.setX_SWIFI_INSTL_TY(wifiObject.getString("X_SWIFI_INSTL_TY"));
			wifi.setX_SWIFI_SVC_SE(wifiObject.getString("X_SWIFI_SVC_SE"));
			wifi.setX_SWIFI_CMCWR(wifiObject.getString("X_SWIFI_CMCWR"));
			wifi.setX_SWIFI_CNSTC_YEAR(wifiObject.getString("X_SWIFI_CNSTC_YEAR"));
			wifi.setX_SWIFI_INOUT_DOOR(wifiObject.getString("X_SWIFI_INOUT_DOOR"));
			wifi.setX_SWIFI_REMARS3(wifiObject.getString("X_SWIFI_REMARS3"));
			wifi.setLAT(wifiObject.getDouble("LAT"));
			wifi.setLNT(wifiObject.getDouble("LNT"));
			wifi.setWORK_DTTM(wifiObject.getString("WORK_DTTM"));
			
			wifiList.add(wifi);
		}
		
		return wifiList;
	}
	
	@Override
	public JSONObject callApi() {
		JSONObject JsonResponse = null;
		try {
			// API 요청
			URL url = new URL(BASE_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			// 받아온 데이터를 문자열로 저장
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();
			
			// 문자열을 JSON 객체로 파싱
			JsonResponse = new JSONObject(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResponse.getJSONObject("TbPublicWifiInfo");
	}
}
