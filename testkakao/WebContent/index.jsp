<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String root = request.getContextPath();%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="root" value="${pageContext.request.contextPath}"/>  

<%
response.addHeader( "Access-Control-Allow-Origin", "*" ); 
response.addHeader( "Access-Control-Allow-Methods", "POST" ); 
response.addHeader( "Access-Control-Max-Age", "1000" ); 
%>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />

	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  	
  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<!-- include summernote css/js-->
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/summernote.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/summernote.js"></script>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js"></script>
	<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
		
	<title>kakao test</title>
	
<style>
div {
	margin: 20px;
}
</style>

<script>
var root = '<%=root%>';
</script>

</head>
<body>

<template id="loginInfo_template">

		<p class="atoken" data-atoken="{{access_token}}">access_token: {{access_token}}</p>
		<p class="tokenType" data-tokenType="{{token_type}}">token_type: {{token_type}}</p>
		<p>refresh_token: {{refresh_token}}</p>
	</form>
</template>

<template id="personInfo_template">
		<p>kaccount_email: {{kaccount_email}}</p>
		<p>id: {{id}}</p>
		
		<p>profile_image: {{properties.profile_image}}</p>
		<div>
			<img src="{{properties.profile_image}}" width="200">
		</div>
		
		<p>nickname: {{properties.nickname}}</p>
		
		<p>thumbnail_image: {{properties.thumbnail_image}}</p>
		<div>
			<img src="{{properties.thumbnail_image}}">
		</div>
</template>

<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 col-sm-12">
	<div class="container-fluid" style="text-align: center;">
	  <button class="btn btn-default" id="kakao-login-btn" type="button"></button>
	</div>
	
	<div id="kakao_token">

	</div>
	
	<div id="person_info">
	
	</div>
	
	
	<div class="container-fluid" style="text-align: center;">
		<button type="button" class="btn btn-warning btn_pay btn_pay_hover" id="web" onclick="payment('web');">
			PC 결제
		</button>
		<button type="button" class="btn btn-warning btn_pay" id="mobile" onclick="payment('mobile');" disabled="disabled" style="cursor: no-drop;">
			모바일 결제
		</button>
	</div>
	
	<div>
		<form id="kakaoPay_form" method="post" action="">
			<div class="form-group">
				<label for="access_token" class="control-label">접속토큰 : </label>
				<input type="text" disabled class="form-control" id="access_token" name="access_token" value="" placeholder="로긴 하세요!">
			</div>
			<div class="form-group">
				<label for="item_name" class="control-label">상품명 : </label>
				<input type="text" class="form-control" id="item_name" name="item_name" value="게스트">
			</div>
		</form>
	</div>
</div>
	
<!-- 카카오 -->
<script src="${root}/js/kakaologin.js"></script>
<script src="${root}/js/kakaopay.js"></script>

</body>
</html>