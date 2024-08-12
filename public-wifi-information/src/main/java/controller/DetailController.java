package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BookmarkService;
import service.WifiService;

@SuppressWarnings("serial")
@WebServlet(value = "/detail")
public class DetailController extends HttpServlet {
	private WifiService wifiService;
	private BookmarkService bookmarkService;
	
	public DetailController() {
		this.wifiService = new WifiService();
		this.bookmarkService = new BookmarkService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String mgrNo = request.getParameter("mgrNo");
		String latParam = request.getParameter("lat");
		String lntParam = request.getParameter("lnt");
		
		Double lat = latParam != null ? Double.parseDouble(latParam) : null;
		Double lnt = lntParam != null ? Double.parseDouble(lntParam) : null;
		
		request.setAttribute("detailWifiInfo", this.wifiService.getDetailWifiInfo(mgrNo, lat, lnt));
		request.setAttribute("bookmarkGroupList", this.bookmarkService.getBookmarkGroupList());
		request.getRequestDispatcher("/detail.jsp?mgrNo=" + mgrNo).forward(request, response);
	}
}
