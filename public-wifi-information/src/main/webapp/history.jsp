<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, dto.History"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
	<script src="/resources/js/index.js" defer></script>
	<script src="/resources/js/history.js" defer></script>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<%@ include file="header.jsp" %>
	<table class="wifi-history">
		<thead>
			<tr>
				<th style="width: 5%;">ID</th>
				<th style="width: 25%;">X 좌표</th>
				<th style="width: 25%;">Y 좌표</th>
				<th style="width: 40%;">조회일자</th>
				<th style="width: 5%;">비고</th>
			</tr>
		</thead>
		<tbody>
			<%
				List<History> historyList = (List<History>) request.getAttribute("historyList");
				if (historyList == null || historyList.isEmpty()) {
			%>
			<tr>
				<td colspan="5" style="text-align: center; font-size: 16px; font-weight: bold;">
					히스토리 목록이 비어있습니다.
				</td>
			</tr>
			<%
				} else {
					for (History history : historyList) {
			%>
			<tr>
				<td style="text-align: center;"><%= history.getID() %></td>
				<td><%= history.getLAT() %></td>
				<td><%= history.getLNT() %></td>
				<td><%= history.getINQUIRY_TIME() %></td>
				<td style="text-align: center;">
					<button onclick="deleteHistory(<%= history.getID() %>)">
						<span>삭제</span>
					</button>
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