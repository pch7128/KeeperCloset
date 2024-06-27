	
	function deleInq(inq_num){
		
		if(confirm("삭제 하시겠습니까?")){
			$.ajax({
				url : '/user/mypage/inquiry-delete/'+inq_num,
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(res) {
					alert(res.result ? "삭제 성공":"삭제 실패");
					if(res.result) location.href="/user/mypage/inquiry";
				},
				error : function(xhr, status, err) {
					alert(status + ", " + err);
				}
			});
		}
		
	
	}
	
	function backUrl(){
		 window.history.back();
	}