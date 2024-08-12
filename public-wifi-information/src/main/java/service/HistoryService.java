package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.HistoryDAO;
import dto.History;

public class HistoryService implements HistoryServiceImpl {
	private HistoryDAO historyDAO;
	
	public HistoryService() {
		this.historyDAO = new HistoryDAO();
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
	public void addHistoryData(Double lat, Double lnt) {
		this.historyDAO.addHistoryData(lat, lnt);
	}

	@Override
	public List<History> getHistoryData() {
		List<History> historyList = this.historyDAO.getHistoryData();
		
		for (History history : historyList) {
			LocalDateTime INQUIRY_TIME = this.parseDateTime(history.getINQUIRY_TIME());
			history.setINQUIRY_TIME(this.formattedDttm(INQUIRY_TIME));
		}
		
		return historyList;
	}
	
	@Override
	public boolean deleteHistoryData(int id) {
		return this.historyDAO.deleteHistoryData(id);
	}
}
