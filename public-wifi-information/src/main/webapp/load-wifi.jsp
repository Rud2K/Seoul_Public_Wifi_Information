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
	<%
		int totalCount = (int) request.getAttribute("totalCount");
	%>
	<p style="font-size: 32px; font-weight: bold; text-align: center"><%= totalCount %>개의 WiFi 정보를 정상적으로 저장하였습니다.</p>
	<a href="http://localhost:8080/" class="home-link">홈으로</a>
</body>
</html>