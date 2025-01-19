<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	var el = $("#btn-checkemail")
	el.click(function() {
		var id = $("#blog-id").val();
		console.log(id);
		if (id == "") {
			return;
		}
		
		$.ajax({
			url: "${pageContext.request.contextPath}/api/user/checkid?id=" + id,
			type: "get",
			dataType: "json",
			success: function(response) { 
				if (response.result != "success") {
					console.error(response.message);
					return;
				}
				
				if (response.data.exist) {
					alert("ID가 존재합니다. 다른 ID를 사용해 주세요.");
					$("#blog-id").val("");
					$("#blog-id").focus();
					
					return;
				}	
				
				$("#img-checkemail").show();
				$("#btn-checkemail").hide();
			},
			error: function(xhr, status, err) {
				console.error(err);
			}
		});
	});
});
</script>
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<form:form 
					modelAttribute="userVo"
					class="join-form"
					id="join-form" 
					method="POST" 
					action="${pageContext.request.contextPath}/user/join">
			<label class="block-label" for="name">이름</label>
			<form:input id="name" path="name" />
			<p style="padding: 5px 0; margin: 0; color:#f00">
		    	<form:errors path="name" />
		   	</p>
			
			<label class="block-label" for="blog-id">아이디</label>
			<form:input id="blog-id" path="id" type="text" />
			<input id="btn-checkemail" type="button" value="id 중복체크" style="display:;"> 
			<img id="img-checkemail" style="display: none" src="${pageContext.request.contextPath}/assets/images/check.png">
			<p style="padding: 5px 0; margin: 0; color:#f00" >
		    	<form:errors path="id" />
		   	</p>

			<label class="block-label" for="password">패스워드</label>
			<form:password id="password" path="password" />
			<p style="padding: 5px 0; margin: 0; color:#f00">
		    	<form:errors path="password" />	
		   	</p>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form:form>
	</div>
</body>
</html>