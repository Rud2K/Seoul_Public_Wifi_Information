package dao;

import java.util.List;

import dto.History;

public interface HistoryDAOImpl {
	public void addHistoryData(Double lat, Double lnt);
	public List<History> getHistoryData();
	boolean deleteHistoryData(int id);
}
