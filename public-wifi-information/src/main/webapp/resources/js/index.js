document.getElementById('btn_cur_position').addEventListener('click', function() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			let lat = position.coords.latitude;
			let lnt = position.coords.longitude;
			
			document.getElementById('lat').value = lat;
			document.getElementById('lnt').value = lnt;
		}, function(error) {
			alert('위치 정보를 가져오는 데 실패했습니다: ' + error.message);
		});
	} else {
		alert('해당 브라우저는 Geolocation API를 지원하지 않습니다.');
	}
});
