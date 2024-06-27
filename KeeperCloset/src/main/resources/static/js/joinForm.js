	
	var nameck=0;
	function nameCheck(){
		var name=$('#u_name').val();
		var ntest = /^[가-힣a-zA-Z]+$/;
		
		if(ntest.test(name)==false){
			document.getElementById("namecheck").innerHTML="한글 또는 영문만 입력해주세요.";
			name.focus();
			
		} else{
			document.getElementById("namecheck").remove();
			nameck=1;
		}
	}
	
	var idck=0;
	function idCheck(){
		var id=$('#u_id').val();
		var idtest= /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
		if(idtest.test(id)==false){
			document.getElementById("idcheck").innerHTML="올바른 이메일 형식이 아닙니다.";
			document.getElementById("u_id").focus();
			
		} else{
			var idcheckElement = document.getElementById("idcheck");
			if(idcheckElement){
				idcheckElement.remove();	
			}
			idck=1;
		}
	}
	
	var iddp=0;
	function duplication(){
		var id=$('#u_id').val();
		var obj={};
		obj.id=id;
	
		if(idck==1){
			$.ajax({
				url:'/user/signup/idcheck',
				type:'post',
				cache:false,
				data: obj,
				dataType:'json',
				success:function(res){
					if(res.result){
						alert('가입이 가능한 아이디입니다.');
						iddp=1;
					} else {
						alert('이미 가입된 아이디입니다.');
						$('#u_id').focus();
						
					}				
				},
				error:function(xhr, status, err){
					alert(err);
				}
			});
		}
		
	}
	
	var pwdck=0;
	function pwdCheck(){
		var pwd=$('#u_pwd').val();
		var ptest= /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{7,}$/;
		
		if(ptest.test(pwd)==false){
			document.getElementById("pwdcheck").innerHTML="영문,숫자,특수문자 모두 포함해야 허용하며 2개 이상 조합";
			document.getElementById("u_pwd").focus();
		
		} else{
			var pwdcheckElement = document.getElementById("pwdcheck");
			if(pwdcheckElement){
				pwdcheckElement.remove();
			}
			pwdck=1;
		}
	}
	
	var pwdcf=0;
	function pwdConfirm(){
		var pwd=$('#u_pwd').val();
		var re=$('#u_pwd2').val();
		
		if(pwd==re){
			if( pwd=="" && re=="") {
				alert('비밀번호를 입력해주세요.');
			} else {
				alert('비밀번호가 확인되었습니다');
				pwdcf=1;
			}
		} else {
			alert('비밀번호가 일치하지 않습니다');
		}

	}
	
	
	

	
	function DaumPostcode() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

	            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	            var addr = ''; // 주소 변수
	            var extraAddr = ''; // 참고항목 변수

	            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                addr = data.roadAddress;
	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                addr = data.jibunAddress;
	            }

	            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	            if(data.userSelectedType === 'R'){
	                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                    extraAddr += data.bname;
	                }
	                // 건물명이 있고, 공동주택일 경우 추가한다.
	                if(data.buildingName !== '' && data.apartment === 'Y'){
	                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                if(extraAddr !== ''){
	                    extraAddr = ' (' + extraAddr + ')';
	                }
	                // 조합된 참고항목을 해당 필드에 넣는다.
	                document.getElementById("add_ref").value = extraAddr;
	            
	            } else {
	                document.getElementById("add_ref").value = '';
	            }

	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
	            document.getElementById('add_zipcode').value = data.zonecode;
	            document.getElementById("addr").value = addr;
	            // 커서를 상세주소 필드로 이동한다.
	            document.getElementById("add_detail").focus();
	        }
	    }).open();
	}
	
	function autoHypenPhone(str){
        str = str.replace(/[^0-9]/g, '');
        var tmp = '';
        if( str.length < 4){
            return str;
        }else if(str.length < 7){
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3);
            return tmp;
        }else if(str.length < 11){
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3, 3);
            tmp += '-';
            tmp += str.substr(6);
            return tmp;
        }else{              
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3, 4);
            tmp += '-';
            tmp += str.substr(7);
            return tmp;
        }
        return str;
    }

	function cell(){
	
		var cellPhone = document.getElementById('u_tel');
		cellPhone.onkeyup = function(event){
		        event = event || window.event;
		        var _val = this.value.trim();
		        this.value = autoHypenPhone(_val);
		};
	}

	
		
	function join(){
		
		var post = document.getElementById('add_zipcode');
		var phone = document.getElementById('u_tel');
		
		//이름 유효성 검사
		if(nameck==0){
			alert('이름을 확인해주세요.');
			document.getElementById('u_name').focus();
			return false;
		}
		
		//아이디 유효성 중복 검사
		if(idck==0){
			alert('아이디를 확인해주세요.');
			document.getElementById('u_id').focus();
			return false;
		}
		
		if(iddp==0){
			alert('아이디를 확인해주세요.');
			document.getElementById('u_id').focus();
			return false;
		}
		
		//비밀번호 유효성 검사 및 확인 검사
		if(pwdck==0){
			alert('비밀번호를 확인해주세요.');
			return false;
		}
		
		if(pwdcf==0){
			alert('비밀번호를 확인해주세요.');
			return false;
		}
		
		if(post==""&&post==null){
			alert('주소를 입력해주세요.');
			return false;
		}
		
		if(phone==""&&phone==null){
			alert('번호를 입력해주세요.');
			return false;
		}
		
		
	}