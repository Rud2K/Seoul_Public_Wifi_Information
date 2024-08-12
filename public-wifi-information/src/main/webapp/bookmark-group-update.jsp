<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="dto.BookmarkGroup, service.BookmarkService"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
	<script src="/resources/js/bookmark.js" defer></script>
</head>
<body>
	<h1>북마크 그룹 수정</h1>
	<%@ include file="header.jsp" %>
	<%
		BookmarkGroup bookmarkGroup = (BookmarkGroup) request.getAttribute("bookmarkGroup");
	%>
	<form id="bookmarkGroupEditForm">
		<table class="bookmark-group-add">
			<tr>
				<th style="width: 15%;">북마크 이름</th>
				<td style="width: 50%;"><input type="text" id="name" name="name" value="<%= bookmarkGroup.getNAME() %>" /></td>
			</tr>
			<tr>
				<th style="width: 15%;">순서</th>
				<td style="width: 50%;"><input type="text" id="orderNo" name="orderNo" value="<%= bookmarkGroup.getORDER_NO() %>" /></td>
			</tr>
		</table>
		<input type="hidden" id="id" name="id" value="<%= bookmarkGroup.getID() %>" />
		<div class="bookmark-group-add-buttons">
			<button type="button" onclick="window.location.href = '/bookmark/group';">돌아가기</button>
			<button type="submit">수정</button>
		</div>
	</form>
</body>
</html>