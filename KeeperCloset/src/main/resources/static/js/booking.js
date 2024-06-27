	$(function(){
		var today = new Date();
		var dd=today.getDate();
		var mm=today.getMonth()+1;
		var yyyy=today.getFullYear();
		if(dd<10){
			dd='0'+dd
		}
		if(mm<10){
			mm='0'+mm
		}
		today=yyyy+'-'+mm+'-'+dd;
		document.getElementById('startdate').setAttribute("min",today);
	})

	function inSdate(){
		var start=document.getElementById('startdate').value;
		if(start==null&&start==""){
			alert('예약 시작할 날짜를 입력해주세요');
			$('#startdate').focus();
			return false;
		}
	}
	
	function setend(end){
		document.getElementById('enddate').setAttribute("min",end);
	}
	
	function inEdate(){
		var end=document.getElementById('enddate').value;
		if(end==null&&end==""){
			alert('예약 종료할 날짜를 입력해주세요');
			$('#enddate').focus();
			return false;
		}
	}
	
	function expName(){
		var name = $('#rv_name').val();
		var ntest = /^[가-힣a-zA-Z]+$/;
		
		if(ntest.test(name)==false){
			document.getElementById("namecheck").innerHTML="한글 또는 영문만 입력해주세요";
			name.focus();
			return true;
		} else {
			document.getElementById("namecheck").remove();
			return false;
		}
	}

/* 	
	window.onload = function () {
		const boxsize = document.getElementById('boxsize');
		boxsize.addEventListener("change", function(e){
			var start = document.getElementById('startdate').value;
			var end = document.getElementById('enddate').value;
			
			const edate=new Date(end);
			const sdate=new Date(start);
			
			var diff=Math.abs((edate.getTime()-sdate.getTime())/(1000*60*60*24));
			if(start&&end){
				var price =diff*boxsize.value;
				console.log(price);
				document.getElementById('rv_price').setAttribute("value",price);
			} else {
				document.getElementById('rv_price').setAttribute("value",0);
			}
			
			
			
		});
		
		const cnt = document.getElementById('rv_cnt');
		cnt.addEventListener("change", function(e){
			var price=document.getElementById('rv_price').value;	
			
			document.getElementById('rv_price').value=total;
			
		});

			
	}
	
	 */
	
	
	
	function total(){
		 
		var start = document.getElementById('startdate').value;
		var end = document.getElementById('enddate').value;
		var cnt = document.getElementById('rv_cnt');
		var boxsize = document.getElementById('boxsize');
		
		const edate=new Date(end);
		const sdate=new Date(start);
		
		var diff=Math.abs((edate.getTime()-sdate.getTime())/(1000*60*60*24));
		if(start&&end){
			var price =diff*boxsize.value*cnt.value;
			console.log(price);
			document.getElementById('rv_price').setAttribute("value",price);
			if(boxsize){
				boxsize.addEventListener("click", function(e){
					document.getElementById('rv_price').setAttribute("value",0);
					cnt.value=0;
				})
			}
			if(cnt){
				cnt.addEventListener("click", function(e){
					var price =diff*boxsize.value*cnt.value;
					document.getElementById('rv_price').setAttribute("value",price);
				})
			}
		} else {
			document.getElementById('rv_price').setAttribute("value",0);
		}	
	 }
	 
	 

	function booking(){
		
		var tp=document.getElementById('rv_price').value;
		var name=document.getElementById('rv_name').value;
		var storage=document.getElementById('storage').value;
		
		
		if(name==""&&name==null){
			alert('예약자명을 입력해주세요');
			return false;
		}
		
		if(expName()){
			alert('예약자명은 한글 또는 영문만 입력가능합니다');
			return false;
		}
		
		if(storage==0&&storage==null){
			alert('예약할 지점을 선택해주세요');
			return false;
		}
		
		if(tp==0){
			alert('예약확인을 다시 한번 더 해주세요');
			return false;
		}
		
		
	}
