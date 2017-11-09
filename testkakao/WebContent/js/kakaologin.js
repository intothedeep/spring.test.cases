$(document).ready(function() {
	$('.loginId').click(function(event) {
    $(location).attr("href", root + "/member/loginid.html");
  	
	});
  
	Kakao.init('e7ca5d8e2c9d5c4f27b2965d6004e5d7');
	// 카카오 로그인 버튼을 생성합니다.
	Kakao.Auth.createLoginButton({
	  container: '#kakao-login-btn',
	  success: function(authObj) {
	    
		  // 로그인 성공시, API를 호출합니다.
	    Kakao.API.request({
	      url: '/v1/user/me',
	      success: function(res) {
	    	  var obj = JSON.stringify(res);
	    	  //alert(JSON.stringify(res));
	    	  showPersonInfo(res);
	      },
	      fail: function(error) {
	        alert(JSON.stringify(error));
	      }
	    });
	    
		  //alert(JSON.stringify(authObj));
		  var form_accessToken_input = $('#access_token');
		  form_accessToken_input.val(authObj.access_token);
	  	  showTokenInfo(authObj);
	    
	  },
	  fail: function(err) {
	    alert(JSON.stringify(err));
	  }
	});
	
	
});

function showTokenInfo(authObj) {
	var body = $('#kakao_token');
	body.empty();
	var template = $('#loginInfo_template').html();
	body.append(Mustache.render(template, authObj));
}

function showPersonInfo(res) {
	var body = $('#person_info');
	body.empty();
	var template = $('#personInfo_template').html();
	body.append(Mustache.render(template, res));
}