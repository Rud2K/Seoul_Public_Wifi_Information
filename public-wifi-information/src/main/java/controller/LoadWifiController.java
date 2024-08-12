package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.WifiService;

@SuppressWarnings("serial")
@WebServlet(value = "/load-wifi")
public class LoadWifiController extends HttpServlet {
	private WifiService wifiService;
	
	public LoadWifiController() {
		this.wifiService = new WifiService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("totalCount", this.wifiService.getTotalCount());
		request.getRequestDispatcher("/load-wifi.jsp").forward(request, response);
	}
}
