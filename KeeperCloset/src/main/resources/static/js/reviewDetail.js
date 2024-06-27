	function deleteReview(r_bnum){
		
		$.ajax({
			url : '/review/delete/'+r_bnum,
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(res) {
				alert(res.deleted ? "삭제 성공":"삭제 실패");
				if(res.deleted) location.href="/user/mypage/checkdetails";
			},
			error : function(xhr, status, err) {
				alert(status + ", " + err);
			}
		});
	}
	function backUrl(){
		 window.history.back();
	}