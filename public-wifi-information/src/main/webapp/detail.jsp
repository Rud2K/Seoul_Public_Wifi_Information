<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, dto.BookmarkGroup, dto.Wifi, service.ConfigManager"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<%@ include file="header.jsp" %>
	<%
		Wifi wifi = (Wifi) request.getAttribute("detailWifiInfo");
		List<BookmarkGroup> bookmarkGroupList = (List<BookmarkGroup>) request.getAttribute("bookmarkGroupList");
	%>
	<div class="bookmark-list-container">
		<form id="bookmarkGroupForm">
			<select id="bookmarkGroup" name="bookmarkGroup" required>
				<option value="" disabled selected>북마크 그룹 이름 선택</option>
				<%
					if (bookmarkGroupList != null) {
						for (BookmarkGroup bookmarkGroup : bookmarkGroupList) {
				%>
				<option value="<%= bookmarkGroup.getID() %>"><%= bookmarkGroup.getNAME() %></option>
				<%
						}
					}
				%>
			</select>
			<input type="hidden" id="mgrNo" name="mgrNo" value="<%= wifi.getX_SWIFI_MGR_NO() %>" />
			<input type="hidden" id="lat" name="lat" value="<%= wifi.getLAT() %>" />
			<input type="hidden" id="lnt" name="lnt" value="<%= wifi.getLNT() %>" />
			<button type="submit">북마크 추가하기</button>
		</form>
	</div>
	<div class="content-container">
		<table class="wifi-detail">
			<tr>
				<th>거리</th>
				<%
					Double distanceValue = wifi.getDistance();
					String distanceDisplay = "";
					
					if (distanceValue < 1) {
						distanceDisplay = String.format("%.0fm", distanceValue * 1000);
					}
				%>
				<td><%= distanceDisplay %></td>
			</tr>
			<tr>
				<th>관리번호</th>
				<td><%= wifi.getX_SWIFI_MGR_NO() %></td>
			</tr>
			<tr>
				<th>자치구</th>
				<td><%= wifi.getX_SWIFI_WRDOFC() %></td>
			</tr>
			<tr>
				<th>와이파이명</th>
				<td><%= wifi.getX_SWIFI_MAIN_NM() %></td>
			</tr>
			<tr>
				<th>도로명주소</th>
				<td><%= wifi.getX_SWIFI_ADRES1() %></td>
			</tr>
			<tr>
				<th>상세주소</th>
				<td><%= wifi.getX_SWIFI_ADRES2() %></td>
			</tr>
			<tr>
				<th>설치위치(층)</th>
				<td><%= wifi.getX_SWIFI_INSTL_FLOOR() %></td>
			</tr>
			<tr>
				<th>설치유형</th>
				<td><%= wifi.getX_SWIFI_INSTL_TY() %></td>
			</tr>
			<tr>
				<th>설치기관</th>
				<td><%= wifi.getX_SWIFI_INSTL_MBY() %></td>
			</tr>
			<tr>
				<th>서비스구분</th>
				<td><%= wifi.getX_SWIFI_SVC_SE() %></td>
			</tr>
			<tr>
				<th>망종류</th>
				<td><%= wifi.getX_SWIFI_CMCWR() %></td>
			</tr>
			<tr>
				<th>설치년도</th>
				<td><%= wifi.getX_SWIFI_CNSTC_YEAR() %></td>
			</tr>
			<tr>
				<th>실내외구분</th>
				<td><%= wifi.getX_SWIFI_INOUT_DOOR() %></td>
			</tr>
			<tr>
				<th>WiFi 접속환경</th>
				<td><%= wifi.getX_SWIFI_REMARS3() %></td>
			</tr>
			<tr>
				<th>X 좌표</th>
				<td><%= wifi.getLAT() %></td>
			</tr>
			<tr>
				<th>Y 좌표</th>
				<td><%= wifi.getLNT() %></td>
			</tr>
			<tr>
				<th>작업일자</th>
				<td><%= wifi.getWORK_DTTM() %></td>
			</tr>
		</table>
		<div class="map" id="map"></div>
	</div>
	<script 
		type="text/javascript" 
		src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=<%= ConfigManager.getProperty("api.key.naver_map") %>">
	</script>
	<script type="text/javascript">
		function initMap() {
			var lat = parseFloat("<%= wifi.getLAT() %>");
			var lng = parseFloat("<%= wifi.getLNT() %>");
			var wifiName = "<%= wifi.getX_SWIFI_MAIN_NM() %>";
			
			var map = new naver.maps.Map('map', {
				center: new naver.maps.LatLng(lat, lng),
				zoom: 17
			});
			
			var marker = new naver.maps.Marker({
				position: new naver.maps.LatLng(lat, lng),
				map: map
			});
		}
		
		window.onload = function() {
			initMap();
		}
		
		document.getElementById('bookmarkGroupForm').addEventListener('submit', function(event) {
			event.preventDefault();
			
			const selectElement = document.getElementById('bookmarkGroup');
			const selectedOptionValue = selectElement.options[selectElement.selectedIndex].value;
			const mgrNo = document.getElementById('mgrNo').value;
			const lat = document.getElementById('lat').value;
			const lnt = document.getElementById('lnt').value;
			
			fetch(`/bookmark/include`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				},
				body: new URLSearchParams({
					bookmarkGroup: selectedOptionValue,
					mgrNo: mgrNo,
					lat: lat,
					lnt: lnt
				})
			})
			.then(response => {
				if (response.ok) {
					alert('북마크 추가에 성공했습니다');
					window.location.reload();
				} else {
					return response.json().then(data => {
						throw new Error(data.message || '북마크 추가에 실패했습니다');
					});
				}
			})
			.catch(error => {
				alert(error.message);
			});
		});
	</script>
</body>
</html>