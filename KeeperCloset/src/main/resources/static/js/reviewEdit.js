	function editReview(r_bnum){
		
		var form = $('#editForm')[0];
	    var data = new FormData(form);
	    
		
			$.ajax({
				url : '/review/review-editform/'+r_bnum,
				type : 'post',
				enctype: 'multipart/form-data',
				cache : false,
				processData: false,   
	            contentType: false, 
				data : data,
				dataType : 'json',
				success : function(res) {
					alert(res.ok?"수정 성공":"수정 실패");
					if(res.ok) location.href="/review/review-detail/"+r_bnum;
				},
				error : function(xhr, status, err) {
					alert(status + ", " + err);
				}
			});
		
	}
    function editImg(input) {
    	var max = 3;
		var cur=input.files.length;
		
		if(cur>max){
			alert('첨부파일은 3개까지 첨부가능합니다.');
		} else{
			if(input.files){
				var divImg=document.getElementById('display-image');
				
				Array.from(input.files).forEach(file =>{
					if(divImg.querySelectorAll('.preview').length>max){
						alert('최대 3개의 이미지만 선택 가능합니다.');
						return;
					}
					var reader = new FileReader();
					reader.onload=function(event){
						var img = document.createElement('img');
						img.src=event.target.result;
						img.className='preview';
						img.onclick = function() {
	                        deleteImg(this);
	                    };
						divImg.appendChild(img);
					};
					reader.readAsDataURL(file);
				})
			}
		}
    }
    
    
    function deleteImg(img) {
        img.parentNode.removeChild(img); // 클릭된 이미지를 부모 요소에서 삭제
    }
    