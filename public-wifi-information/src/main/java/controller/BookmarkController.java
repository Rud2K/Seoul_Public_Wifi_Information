package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import service.BookmarkService;
import service.WifiService;

@SuppressWarnings("serial")
@WebServlet(value = "/bookmark/*")
public class BookmarkController extends HttpServlet {
	private BookmarkService bookmarkService;
	private WifiService wifiService;
	
	public BookmarkController() {
		this.bookmarkService = new BookmarkService();
		this.wifiService = new WifiService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		if (request.getPathInfo() == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		switch (request.getPathInfo()) {
			case "/view":
				request.setAttribute("bookmarkList", this.bookmarkService.getBookmarkList());
				request.getRequestDispatcher("/bookmark-view.jsp").forward(request, response);
				break;
			case "/group":
				request.setAttribute("bookmarkGroupList", this.bookmarkService.getBookmarkGroupList());
				request.getRequestDispatcher("/bookmark-group.jsp").forward(request, response);
				break;
			case "/add":
				request.getRequestDispatcher("/bookmark-group-add.jsp").forward(request, response);
				break;
			case "/update":
				int id = Integer.parseInt(request.getParameter("id"));
				request.setAttribute("bookmarkGroup", this.bookmarkService.getBookmarkGroup(id));
				request.getRequestDispatcher("/bookmark-group-update.jsp").forward(request, response);
				break;
			case "/delete":
				int bookmarkId = Integer.parseInt(request.getParameter("id"));
				request.setAttribute("bookmark", this.bookmarkService.getBookmark(bookmarkId));
				request.getRequestDispatcher("/bookmark-view-delete.jsp").forward(request, response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		if (request.getPathInfo() == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		switch (request.getPathInfo()) {
			case "/add/save":
				String name = request.getParameter("name");
				int orderNo = Integer.parseInt(request.getParameter("orderNo"));
				
				if (this.bookmarkService.addBookmarkGroup(name, orderNo)) {
					response.sendRedirect(request.getContextPath() + "/bookmark/group");
				} else {
					request.setAttribute("errorMessage", "북마크 그룹 추가에 실패했습니다.");
					request.getRequestDispatcher("/bookmark-group-add.jsp").forward(request, response);
				}
				break;
			case "/update/save":
				int updateId = Integer.parseInt(request.getParameter("id"));
				String updateName = request.getParameter("name");
				int updateOrderNo = Integer.parseInt(request.getParameter("orderNo"));
				
				if (this.bookmarkService.updateBookmarkGroup(updateId, updateName, updateOrderNo)) {
					response.sendRedirect(request.getContextPath() + "/bookmark/group");
				} else {
					request.setAttribute("errorMessage", "북마크 그룹 수정에 실패했습니다.");
					request.setAttribute("bookmarkGroup", this.bookmarkService.getBookmarkGroup(updateId));
					request.getRequestDispatcher("/bookmark-group-update.jsp?id=" + updateId).forward(request, response);
				}
				break;
			case "/include":
				Integer groupId = Integer.parseInt(request.getParameter("bookmarkGroup"));
				String mgrNo = request.getParameter("mgrNo");
				Double lat = Double.parseDouble(request.getParameter("lat"));
				Double lnt = Double.parseDouble(request.getParameter("lnt"));
				
				JSONObject jsonResponse = new JSONObject();
				response.setContentType("application/json");
				
				if (groupId != null && this.bookmarkService.addBookmark(groupId, mgrNo)) {
					jsonResponse.put("status", "success");
					jsonResponse.put("message", "북마크 추가에 성공했습니다.");
				} else {
					jsonResponse.put("status", "error");
					jsonResponse.put("message", "북마크 추가에 실패했습니다.");
				}
				
				request.setAttribute("detailWifiInfo", this.wifiService.getDetailWifiInfo(mgrNo, lat, lnt));
				response.getWriter().write(jsonResponse.toString());
				response.getWriter().flush();
				break;
			default:
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				break;
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		if (request.getPathInfo() == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		int id;
		
		switch (request.getPathInfo()) {
			case "/delete/group":
				id = Integer.parseInt(request.getParameter("id"));
				if (this.bookmarkService.deleteBookmarkGroup(id)) {
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				} else {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
				break;
			case "/delete/list":
				id = Integer.parseInt(request.getParameter("id"));
				if (this.bookmarkService.deleteBookmark(id)) {
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				} else {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
				break;
			default:
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				break;
		}
	}
}
