<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, dto.Wifi"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
	<script src="/resources/js/index.js" defer></script>
	<script src="/resources/js/bookmark.js" defer></script>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<%@ include file="header.jsp" %>
	<div style="margin-bottom: 10px;">
		<form id="search-form" action="/search" method="get">
			<label for="lat">LAT : </label>
			<input type="text" id="lat" name="lat" value="0.0">
			
			<label for="lnt">LNT : </label>
			<input type="text" id="lnt" name="lnt" value="0.0">
			
			<button type="button" id="btn_cur_position"><span>내 위치 가져오기</span></button>
			<button type="submit" id="btn_nearest_wifi"><span>근처 WiFi 정보 보기</span></button>
		</form>
	</div>
	<table class="wifi-list">
		<thead>
			<tr>
				<th style="width: 4%;">거리</th>
				<th style="width: 4.5%;">관리번호</th>
				<th style="width: 4%;">자치구</th>
				<th style="width: 12%;">와이파이명</th>
				<th style="width: 7%;">도로명주소</th>
				<th style="width: 7%;">상세주소</th>
				<th style="width: 4%;">설치위치(층)</th>
				<th style="width: 10%;">설치유형</th>
				<th style="width: 5%;">설치기관</th>
				<th style="width: 5%;">서비스구분</th>
				<th style="width: 8%;">망종류</th>
				<th style="width: 4%;">설치년도</th>
				<th style="width: 3%;">실내외<br>구분</th>
				<th style="width: 4%;">WiFi<br>접속환경</th>
				<th style="width: 5%;">X 좌표</th>
				<th style="width: 5%;">Y 좌표</th>
				<th style="width: 6%;">작업일자</th>
			</tr>
		</thead>
		<tbody>
			<%
				List<Wifi> wifiList = (List<Wifi>) request.getAttribute("wifiList");
				if (wifiList == null || wifiList.isEmpty()) {
			%>
			<tr>
				<td colspan="17" style="text-align: center; font-size: 16px; font-weight: bold;">
					위치 정보를 입력한 후에 조회해 주세요.
				</td>
			</tr>
			<%
				} else {
					for (Wifi wifi : wifiList) {
			%>
			<tr>
				<%
					Double distanceValue = wifi.getDistance();
					String distanceDisplay = "";
					
					if (distanceValue < 1) {
						distanceDisplay = String.format("%.0fm", distanceValue * 1000);
					}
				%>
				<td style="text-align: center;"><%= distanceDisplay %></td>
				<td style="text-align: center;"><%= wifi.getX_SWIFI_MGR_NO() %></td>
				<td style="text-align: center;"><%= wifi.getX_SWIFI_WRDOFC() %></td>
				<td>
					<a href="http://localhost:8080/detail?mgrNo=<%= wifi.getX_SWIFI_MGR_NO() %>">
						<%= wifi.getX_SWIFI_MAIN_NM() %>
					</a>
				</td>
				<td><%= wifi.getX_SWIFI_ADRES1() %></td>
				<td><%= wifi.getX_SWIFI_ADRES2() %></td>
				<td style="text-align: center;"><%= wifi.getX_SWIFI_INSTL_FLOOR() %></td>
				<td style="text-align: center;"><%= wifi.getX_SWIFI_INSTL_TY() %></td>
				<td style="text-align: center;"><%= wifi.getX_SWIFI_INSTL_MBY() %></td>
				<td style="text-align: center;"><%= wifi.getX_SWIFI_SVC_SE() %></td>
				<td style="text-align: center;"><%= wifi.getX_SWIFI_CMCWR() %></td>
				<td style="text-align: center;"><%= wifi.getX_SWIFI_CNSTC_YEAR() %></td>
				<td style="text-align: center;"><%= wifi.getX_SWIFI_INOUT_DOOR() %></td>
				<td style="text-align: center;"><%= wifi.getX_SWIFI_REMARS3() %></td>
				<td style="text-align: center;"><%= wifi.getLAT() %></td>
				<td style="text-align: center;"><%= wifi.getLNT() %></td>
				<td style="text-align: center;"><%= wifi.getWORK_DTTM() %></td>
			</tr>
			<%
					}
				}
			%>
		</tbody>
	</table>
</body>
</html>