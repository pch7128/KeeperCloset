	$(document).ready(function(){
	    $('.board_content').hide();
	
	    // 제목을 클릭하면 해당 행의 숨겨진 내용이 토글됩니다.
	    $('.inq_content').click(function(){
	        // 해당 행의 .board_content 요소를 찾습니다.
	        $(this).closest('tr').next('.board_content').toggle();
	
	    });
	});