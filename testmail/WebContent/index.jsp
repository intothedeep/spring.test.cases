<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String root = request.getContextPath();%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="root" value="${pageContext.request.contextPath}"/>  

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
		
	<title>메일</title>

</head>
<body>
	<div class="container">
		<form id="mailForm" method="post" enctype="multipart/form-data" action="post">
			<input id="modify_fileSeq" type="hidden" name="fileSeq" value="">
			<input type="hidden" name="content" id="content" value="">
				<label class="input-group">
					제목
				</label>
				<input class="form-control" type="text" id="subject" name="subject" value=""/>
			<div id="summernote"></div>
			
			<div class="image-upload">
			    <label for="file_input">
			       <a><span class="glyphicon glyphicon-file"></span>
			       	 파일첨부
			       </a>
			    </label>
				<label id="showFileName"></label>
				<input id="file_input" class="btn btn-default form-control" type="file" name="file">
			</div>
		</form>
		<button class="btn btn-info" type="button" id="mailBtn"> 메일 보내기 </button>
	</div>


<script>
	var root = "<%=root%>";
	$(document).ready(function () {
		
		$('#summernote').summernote({
			  height: 300,                 // set editor height
			  minHeight: null,             // set minimum height of editor
			  maxHeight: null,             // set maximum height of editor
			  focus: true                  // set focus to editable area after initializing summernote
		});

		
		$('#mailBtn').click(function () {
			var summernoteContent = $('#summernote').summernote('code');
			$('#content').val(summernoteContent);
			$('#mailForm').attr("action", root + "/mail/send").submit();
		});
		
	});
	
</script>
</body>
</html>