<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, dto.BookmarkGroup"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
	<script src="/resources/js/bookmark.js" defer></script>
</head>
<body>
	<h1>북마크 그룹</h1>
	<%@ include file="header.jsp" %>
	<button onclick="window.location.href = '/bookmark/add';" style="margin-bottom: 10px;">북마크 그룹 이름 추가</button>
	<table class="bookmark-group">
		<thead>
			<tr>
				<th style="width: 5%;">ID</th>
				<th style="width: 25%;">북마크 이름</th>
				<th style="width: 5%;">순서</th>
				<th style="width: 25%;">등록일자</th>
				<th style="width: 25%;">수정일자</th>
				<th style="width: 10%;">비고</th>
			</tr>
		</thead>
		<tbody>
			<%
				List<BookmarkGroup> bookmarkGroupList = (List<BookmarkGroup>) request.getAttribute("bookmarkGroupList");
				if (bookmarkGroupList == null || bookmarkGroupList.isEmpty()) {
			%>
			<tr>
				<td colspan="6" style="text-align: center; font-size: 16px; font-weight: bold;">
					북마크 그룹 목록이 비어있습니다.
				</td>
			</tr>
			<%
				} else {
					for (BookmarkGroup bookmarkGroup : bookmarkGroupList) {
			%>
			<tr>
				<td style="text-align: center;"><%= bookmarkGroup.getID() %></td>
				<td><%= bookmarkGroup.getNAME() %></td>
				<td style="text-align: center;"><%= bookmarkGroup.getORDER_NO() %></td>
				<td><%= bookmarkGroup.getREGISTER_DTTM() %></td>
				<td><%= bookmarkGroup.getUPDATE_DTTM() != null ? bookmarkGroup.getUPDATE_DTTM() : "" %></td>
				<td style="text-align: center;">
					<a href="/bookmark/update?id=<%= bookmarkGroup.getID() %>">수정</a>
					<a href="javascript:void(0);" onclick="deleteBookmarkGroup(<%= bookmarkGroup.getID() %>)">삭제</a>
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