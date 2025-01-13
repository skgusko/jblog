package jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jblog.service.BlogService;
import jblog.service.CategoryService;
import jblog.service.PostService;

@Controller
@RequestMapping("/blog")
public class BlogController {
	private final BlogService blogService;
	private final CategoryService categoryService;
	private final PostService postService;
	public BlogController(BlogService blogService, CategoryService categoryService, PostService postService) {
		this.blogService = blogService;
		this.categoryService = categoryService;
		this.postService = postService;
	}
	
	@GetMapping("/{id}")
	public String index(@PathVariable("id") String id,
						Model model) {
		model.addAttribute("blogVo", blogService.getContents(id)); // title, profile
		model.addAttribute("categoryVo", categoryService.getContents(id)); // category list
		
		// default category's post
		model.addAttribute("postVo", postService.getContents(id)); //가장 최신 카테고리의 게시글들
		
		return "blog/main";
	}
}
