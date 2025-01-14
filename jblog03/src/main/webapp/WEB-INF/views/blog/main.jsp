<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%pageContext.setAttribute("newLine", "\n");%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/admin-header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<c:choose>
						<c:when test="${mainPostVo.title == null}">
							<h4>첫 번째 글을 작성해주세요!</h4>
						</c:when>
						<c:otherwise>
							<h4>${mainPostVo.title}</h4>
						</c:otherwise>
					</c:choose>
					<p>${fn:replace(mainPostVo.contents, newLine, "<br>")}<p>
				</div>
				<ul class="blog-list">
					<c:forEach var="postVo" items="${postVo }">
						<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/${postVo.categoryId}/${postVo.id}">${postVo.title }</a> <span>${postVo.regDate}</span></li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.profile}">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach var="categoryVo" items="${categoryVo }">
					<li><a href="${pageContext.request.contextPath}/${categoryVo.blogId }/${categoryVo.id }">${categoryVo.name }</a></li>
				</c:forEach>
				</ul>
		</div>
		
		<c:import url="/WEB-INF/views/includes/admin-footer.jsp" />
	</div>
</body>
</html>