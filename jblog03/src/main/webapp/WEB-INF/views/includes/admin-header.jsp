<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<div id="header">
			<h1>${blogVo.title }</h1>
			<ul>
				<c:choose>
					<c:when test="${authUser == null }">
						<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>	
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
						<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin">블로그 관리</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>