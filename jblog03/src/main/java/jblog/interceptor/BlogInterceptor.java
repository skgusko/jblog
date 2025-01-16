package jblog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jblog.service.BlogService;
import jblog.service.CategoryService;
import jblog.service.PostService;
import jblog.vo.BlogVo;

	public class BlogInterceptor implements HandlerInterceptor {
	
		private final BlogService blogService;
		private final CategoryService categoryService;
		private final PostService postService;
		public BlogInterceptor(BlogService blogService, CategoryService categoryService, PostService postService) {
			this.blogService = blogService;
			this.categoryService = categoryService;
			this.postService = postService;
		}
		
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {	
			// http://localhost:8080/jblog03/dooly
			System.out.println("====== BlogInterceptor");
			String requestURI = request.getRequestURI();
			String[] elements = requestURI.split("/");
			
			if (elements.length < 2) {
				response.sendRedirect(request.getContextPath());
				return false;
			}

			// 존재하는 유저의 블로그인지 확인  
			String blogId = elements[2];
			BlogVo blogVo = blogService.getContents(blogId);
			
			if (blogVo == null) {
				response.sendRedirect(request.getContextPath());
	            return false;
			}
			
			// 특정 유저의 카테고리가 맞는지 확인 
			if (elements.length > 3) {
				String categoryIdString = elements[3];
				if ("admin".equals(categoryIdString)) {
					request.setAttribute("blogVo", blogVo);
					return true;
				}
				
				Long categoryId;
				
				try {
					categoryId = Long.parseLong(categoryIdString);
				} catch (NumberFormatException e) {
					response.sendRedirect(request.getContextPath() + "/" + blogId);
					return false;
				}
				
				int count = categoryService.findByCategoryIdAndBlogId(categoryId, blogId);
				if (count == 0) {   
					response.sendRedirect(request.getContextPath() + "/" + blogId);
					return false;
				}
				
				// 특정 유저의 카테고리 내 존재하는 포스트 번호인지 확인
				Long postId = null;
				if (elements.length > 4) {
					String postIdString = elements[4];
					
					try {
						postId = Long.parseLong(postIdString);
					} catch (NumberFormatException e) {
						response.sendRedirect(request.getContextPath() + "/" + blogId + "/" + categoryId);
						return false;
					}
					
					//postId와 categoryId 같이 보내서 있는지 확인 
					count = postService.findByPostIdAndCategoryId(postId, categoryId);
					if (count == 0) { // 특정 유저의 카테고리 내 존재하지 않는 번호인 경우  
						response.sendRedirect(request.getContextPath() + "/" + blogId + "/" + categoryId);
						return false;
					}
				}
				
				if (elements.length > 5) {
					response.sendRedirect(request.getContextPath() + "/" + blogId + "/" + categoryId + "/" + postId);
					return false;
				}
			}
			
			request.setAttribute("blogVo", blogVo);
			System.out.println("====== BlogInterceptor");
			return true;
	}

}
