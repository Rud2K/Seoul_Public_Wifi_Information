function deleteHistory(id) {
	if (confirm('정말로 해당 히스토리를 삭제하시겠습니까?')) {
		fetch(`/history/delete?id=${id}`, {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json'
			}
		})
		.then(response => {
			if (!response.ok) {
				throw new Error("네트워크 응답 오류");
			}
			return response.json();
		})
		.then(data => {
			alert(data.message);
			location.reload();
		})
		.catch(error => {
			alert(error);
		})
	}
}