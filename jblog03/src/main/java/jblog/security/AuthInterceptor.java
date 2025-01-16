package jblog.security;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jblog.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) { // DefaultServletRequestHandler 타입인 경우 
			return true;
		}
		
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 메서드 
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		if(auth == null) {
			// 클래스 
			auth = handlerMethod
					.getMethod()
					.getDeclaringClass()
					.getAnnotation(Auth.class);
		}
		
		if (auth == null) {
			return true;
		}
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if (authUser == null) { //로그인 안 되어있는 경우
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 로그인 되어있는데 /admin 요청일 때 본인 블로그 아닌 경우
		String requestURI = request.getRequestURI();
		String[] elements = requestURI.split("/");
		String blogId = elements[2];
		
		if ("admin".equals(elements[3]) && !blogId.equals(authUser.getId())) {
			response.sendRedirect(request.getContextPath() + "/" + blogId);
			return false;
		}
		
		return true;
	}

}
