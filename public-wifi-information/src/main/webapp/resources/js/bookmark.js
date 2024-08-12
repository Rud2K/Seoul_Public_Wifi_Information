document.getElementById('bookmarkGroupEditForm').addEventListener('submit', function(event) {
	event.preventDefault();

	const id = document.getElementById('id').value;
	const name = document.getElementById('name').value;
	const orderNo = document.getElementById('orderNo').value;
	
	fetch(`/bookmark/update/save?id=${id}&name=${name}&orderNo=${orderNo}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		}
	})
	.then(response => {
		if (response.ok) {
			alert('수정이 완료되었습니다');
			window.location.href = '/bookmark/group';
		}
	})
	.catch(error => {
		alert('수정에 실패했습니다');
	})
});

function deleteBookmark(id) {
	if (confirm("정말로 삭제하시겠습니까?")) {
		fetch(`/bookmark/delete/list?id=${id}`, {
			method: 'DELETE'
		})
		.then(response => {
			if (!response.ok) {
				throw new Error('삭제 요청에 실패했습니다.');
			}
			alert('삭제가 완료되었습니다.');
			window.location.href = '/bookmark/view';
		})
		.catch(error => {
			alert('오류가 발생했습니다: ' + error.message);
		});
	}
}

function deleteBookmarkGroup(id) {
	if (confirm('정말로 삭제하시겠습니까?')) {
		fetch(`/bookmark/delete/group?id=${id}`, {
			method: 'DELETE'
		})
		.then(response => {
			if (response.ok) {
				alert('삭제가 완료되었습니다');
				window.location.href = '/bookmark/group';
			}
		})
		.catch(error => {
			alert('삭제에 실패했습니다. ' + error);
		});
	}
}
