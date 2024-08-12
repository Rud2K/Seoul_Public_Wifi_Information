package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.HistoryService;
import service.WifiService;

@SuppressWarnings("serial")
@WebServlet(value = "/search")
public class SearchWifiController extends HttpServlet {
	private WifiService wifiService;
	private HistoryService historyService;
	
	public SearchWifiController() {
		this.wifiService = new WifiService();
		this.historyService = new HistoryService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Double lat = Double.parseDouble(request.getParameter("lat").trim());
		Double lnt = Double.parseDouble(request.getParameter("lnt").trim());
		final double distance = 1.0;
		
		this.historyService.addHistoryData(lat, lnt);
		request.setAttribute("wifiList", this.wifiService.getNearbyWifi(lat, lnt, distance));
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}
