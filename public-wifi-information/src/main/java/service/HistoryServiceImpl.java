package service;

import java.time.LocalDateTime;
import java.util.List;

import dto.History;

public interface HistoryServiceImpl {
	public String formattedDttm(LocalDateTime date);
	public LocalDateTime parseDateTime(String dateTimeStr);
	public void addHistoryData(Double lat, Double lnt);
	public List<History> getHistoryData();
	boolean deleteHistoryData(int id);
}
