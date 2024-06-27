	let oEditors = []
	smartEditor = function(){
		nhn.husky.EZCreator.createInIFrame({
			oAppRef : oEditors,
			elPlaceHolder : "cm_content",
			sSkinURI : "/static/smarteditor/SmartEditor2Skin.html",
			fCreator : "createSEditor2"
		});
	}
	
	$(document).ready(function(){
		smartEditor()
	});
	
	function addComm(inq_num){
		
		oEditors.getById["cm_content"].exec("UPDATE_CONTENTS_FIELD",[])
		let content=document.getElementById("cm_content").value;
		
		var obj={};
		obj.cm_content=content;
		
		$.ajax({
			url : '/keeper/admin/inq/addComment/'+inq_num,
			type : 'post',
			cache : false,
			data : obj,
			dataType : 'json',
			success : function(res) {
				alert(res.ok?"등록 성공":"등록 실패");
				if(res.ok) location.href="/keeper/admin/inq/inq_detail/"+inq_num; 
			},
			error : function(xhr, status, err) {
				alert(status + ", " + err);
			}
		});
	}