<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, dto.Bookmark"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
</head>
<body>
	<h1>북마크 목록</h1>
	<%@ include file="header.jsp" %>
	<table class="bookmark-list">
		<thead>
			<tr>
				<th style="width: 5%">ID</th>
				<th style="width: 25%">북마크 이름</th>
				<th style="width: 25%">와이파이명</th>
				<th style="width: 35%">등록일자</th>
				<th style="width: 10%">비고</th>
			</tr>
		</thead>
		<tbody>
			<%
				List<Bookmark> bookmarkList = (List<Bookmark>) request.getAttribute("bookmarkList");
				if (bookmarkList == null || bookmarkList.isEmpty()) {
			%>
			<tr>
				<td colspan="5" style="text-align: center; font-size: 16px; font-weight: bold;">
					북마크 목록이 비어있습니다.
				</td>
			</tr>
			<%
				} else {
					for (Bookmark bookmark : bookmarkList) {
			%>
			<tr>
				<td style="text-align: center;"><%= bookmark.getID() %></td>
				<td><%= bookmark.getNAME() %></td>
				<td>
					<a href="/detail?mgrNo=<%= bookmark.getX_SWIFI_MGR_NO() %>">
						<%= bookmark.getX_SWIFI_MAIN_NM() %>
					</a>
				</td>
				<td><%= bookmark.getREGISTER_DTTM() %></td>
				<td style="text-align: center;">
					<a href="/bookmark/delete?id=<%= bookmark.getID() %>">삭제</a>
				</td>
			</tr>
			<%
					}
				}
			%>
		</tbody>
	</table>
</body>
</html>