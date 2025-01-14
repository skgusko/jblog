package jblog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jblog.service.BlogService;
import jblog.vo.BlogVo;

	public class BlogInterceptor implements HandlerInterceptor {
	
		private final BlogService blogService;
		public BlogInterceptor(BlogService blogService) {
			this.blogService = blogService;
		}
		
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			String requestURI = request.getRequestURI();
			String[] elements = requestURI.split("/");
			
			if (elements.length < 2) {
				response.sendRedirect(request.getContextPath());
				return false;
			}

			String blogId = elements[2];
			BlogVo blogVo = blogService.getContents(blogId);
			
			if (blogVo == null) {
				response.sendRedirect(request.getContextPath());
	            return false;
			}
			
			request.setAttribute("blogVo", blogVo);	
			return true;
	}

}
