package service;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;

import dto.Wifi;

public interface WifiServiceImpl {
	public String formattedDttm(LocalDateTime date);
	public LocalDateTime parseDateTime(String dateTimeStr);
	public int getTotalCount();
	public List<Wifi> getNearbyWifi(Double lat, Double lnt, double radius);
	public Wifi getDetailWifiInfo(String mgrNo, Double lat, Double lnt);
	public List<Wifi> getWifiList();
	public JSONObject callApi();
}
