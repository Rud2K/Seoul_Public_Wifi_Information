<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="dto.Bookmark"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
	<script src="/resources/js/bookmark.js" defer></script>
</head>
<body>
	<h1>북마크 삭제</h1>
	<%@ include file="header.jsp" %>
	<p style="margin-top: 20px; font-weight: bold;">북마크를 삭제하시겠습니까?</p>
	<%
		Bookmark bookmark = (Bookmark) request.getAttribute("bookmark");
	%>
	<table class="bookmark-list-delete">
		<tr>
			<th style="width: 30%;">북마크 이름</th>
			<td id="bookmark-name"><%= bookmark.getNAME() %></td>
		</tr>
		<tr>
			<th style="width: 30%;">와이파이명</th>
			<td id="wifi-name"><%= bookmark.getX_SWIFI_MAIN_NM() %></td>
		</tr>
		<tr>
			<th style="width: 30%;">등록일자</th>
			<td id="register-dttm"><%= bookmark.getREGISTER_DTTM() %></td>
		</tr>
	</table>
	<div class="bookmark-list-delete-buttons">
		<button type="button" onclick="window.location.href = '/bookmark/view';">돌아가기</button>
		<button type="button" onclick="deleteBookmark(<%= bookmark.getID() %>)">삭제</button>
	</div>
</body>
</html>
