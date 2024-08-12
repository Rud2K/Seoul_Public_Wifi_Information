<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
</head>
<body>
	<h1>북마크 그룹 추가</h1>
	<%@ include file="header.jsp" %>
	<form action="/bookmark/add/save" method="post">
		<table class="bookmark-group-add">
			<tr>
				<th style="width: 15%;">북마크 이름</th>
				<td style="width: 50%;"><input type="text" name="name" /></td>
			</tr>
			<tr>
				<th style="width: 15%;">순서</th>
				<td style="width: 50%;"><input type="text" name="orderNo" /></td>
			</tr>
		</table>
		<div class="bookmark-group-add-buttons">
			<button type="button" onclick="window.location.href = '/bookmark/group';">돌아가기</button>
			<button type="submit">추가</button>
		</div>
	</form>
</body>
</html>