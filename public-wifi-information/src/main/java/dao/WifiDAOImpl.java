package dao;

import java.util.List;
import java.util.Map;

import dto.Wifi;

public interface WifiDAOImpl {
	public void addWifiData(List<Wifi> wifiList);
	public List<Wifi> getNearbyWifiData(Double lat, Double lnt, double radius);
	public Map<String, Double> getLatLntByMgrNo(String mgrNo);
	public Wifi getDetailWifiData(String mgrNo, Double lat, Double lnt);
}
