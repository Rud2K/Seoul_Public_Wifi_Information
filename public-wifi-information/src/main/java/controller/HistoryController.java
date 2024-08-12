package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import service.HistoryService;

@SuppressWarnings("serial")
@WebServlet(value = "/history/*")
public class HistoryController extends HttpServlet {
	private HistoryService historyService;
	
	public HistoryController() {
		this.historyService = new HistoryService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("historyList", this.historyService.getHistoryData());
		request.getRequestDispatcher("/history.jsp").forward(request, response);
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonResponse = new JSONObject();
		
		String idParam = request.getParameter("id");
		
		if (idParam != null) {
			try {
				int id = Integer.parseInt(idParam);
				boolean isDeleted = this.historyService.deleteHistoryData(id);
				
				jsonResponse.put("message", isDeleted ? "삭제 성공" : "삭제 실패");
				response.getWriter().write(jsonResponse.toString());
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 ID 형식입니다.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 ID 입니다.");
		}
	}
}
